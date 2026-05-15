package com.proyecto.macrofit.usuarios.service;

import com.proyecto.macrofit.usuarios.model.ComidaRecomendada;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SpoonacularService {

    @Value("${spoonacular.api.key}")
    private String claveApi;

    @Value("${spoonacular.api.url}")
    private String urlBase;

    private final RestTemplate restTemplate;

    public SpoonacularService() {
        this.restTemplate = new RestTemplate();
    }

    // TRADUCTOR DE DIETAS (Español a Inglés para que Spoonacular entienda)
    private String traducirFiltroDieta(String dietaEspanol) {
        if (dietaEspanol == null || dietaEspanol.trim().isEmpty()) {
            return "";
        }
        String dietaLimpia = dietaEspanol.toLowerCase();
        if (dietaLimpia.contains("vegetariana"))
            return "vegetarian";
        if (dietaLimpia.contains("vegana"))
            return "vegan";
        if (dietaLimpia.contains("keto") || dietaLimpia.contains("cetog"))
            return "ketogenic";
        if (dietaLimpia.contains("paleo"))
            return "paleo";
        return "";
    }

    // TRADUCTOR DE TÍTULOS (Inglés a Español para tu Android)
    private String traducirTituloAlEspanol(String textoIngles) {
        try {
            String textoCodificado = URLEncoder.encode(textoIngles, StandardCharsets.UTF_8.toString());
            String urlTraductor = "https://api.mymemory.translated.net/get?q=" + textoCodificado + "&langpair=en|es";

            Map<String, Object> respuestaTraduccion = restTemplate.getForObject(urlTraductor, Map.class);
            if (respuestaTraduccion != null && respuestaTraduccion.containsKey("responseData")) {
                Map<String, Object> datosRespuesta = (Map<String, Object>) respuestaTraduccion.get("responseData");
                return (String) datosRespuesta.get("translatedText");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error traduciendo texto: " + e.getMessage());
        }
        return textoIngles;
    }

    public List<ComidaRecomendada> buscarRecetasPersonalizadas(
            String tipoDieta,
            String ingredientes,
            Float maxCarbohidratos,
            Float minProteina,
            Float maxGrasa) {
        List<ComidaRecomendada> listaRecomendaciones = new ArrayList<>();

        try {
            String dietaTraducida = traducirFiltroDieta(tipoDieta);

            String urlFinal = UriComponentsBuilder.fromUriString(urlBase)
                    .queryParam("apiKey", claveApi)
                    .queryParam("diet", dietaTraducida)
                    .queryParam("includeIngredients", ingredientes)
                    .queryParam("maxCarbs", maxCarbohidratos)
                    .queryParam("minProtein", minProteina)
                    .queryParam("maxFat", maxGrasa)
                    .queryParam("number", 5)
                    .queryParam("addRecipeNutrition", true)
                    .queryParam("fillIngredients", true)
                    .queryParam("addRecipeInformation", true)
                    .toUriString();

            Map<String, Object> respuestaSpoonacular = restTemplate.getForObject(urlFinal, Map.class);

            if (respuestaSpoonacular != null && respuestaSpoonacular.containsKey("results")) {
                List<Map<String, Object>> resultados = (List<Map<String, Object>>) respuestaSpoonacular.get("results");

                for (Map<String, Object> recetaIngles : resultados) {
                    ComidaRecomendada nuevaComida = new ComidaRecomendada();

                    nuevaComida.setId_comida((Integer) recetaIngles.get("id"));
                    nuevaComida.setFoto_comida((String) recetaIngles.get("image"));
                    nuevaComida.setDescripcion_comida("Recomendación personalizada.");

                    // Traducir y asignar el nombre
                    String tituloIngles = (String) recetaIngles.get("title");
                    nuevaComida.setNombre_comida(traducirTituloAlEspanol(tituloIngles));

                    // Extraer Ingredientes
                    List<String> listaIng = new ArrayList<>();
                    if (recetaIngles.containsKey("extendedIngredients")) {
                        List<Map<String, Object>> extIng = (List<Map<String, Object>>) recetaIngles
                                .get("extendedIngredients");
                        for (Map<String, Object> ing : extIng) {
                            listaIng.add((String) ing.get("original"));
                        }
                    }
                    nuevaComida.setIngredientes_lista(listaIng);

                    // Extraer Preparación
                    List<String> listaPasos = new ArrayList<>();
                    if (recetaIngles.containsKey("analyzedInstructions")) {
                        List<Map<String, Object>> instructions = (List<Map<String, Object>>) recetaIngles
                                .get("analyzedInstructions");
                        if (!instructions.isEmpty()) {
                            List<Map<String, Object>> steps = (List<Map<String, Object>>) instructions.get(0)
                                    .get("steps");
                            for (Map<String, Object> step : steps) {
                                listaPasos.add((String) step.get("step"));
                            }
                        }
                    }
                    nuevaComida.setPreparacion_lista(listaPasos);

                    // Extraer Macros, Calorías y el Peso de la Porción
                    Map<String, Object> nutricion = (Map<String, Object>) recetaIngles.get("nutrition");
                    if (nutricion != null) {

                        // PESO PROPORCIONAL A LOS MACROS
                        if (nutricion.containsKey("weightPerServing")) {
                            Map<String, Object> pesoPorcion = (Map<String, Object>) nutricion.get("weightPerServing");
                            if (pesoPorcion != null) {
                                Number cantidadPeso = (Number) pesoPorcion.get("amount");
                                String unidadMedida = (String) pesoPorcion.get("unit");
                                if (cantidadPeso != null && unidadMedida != null) {
                                    nuevaComida.setCantidad_porcion(cantidadPeso.intValue() + unidadMedida);
                                }
                            }
                        }

                        if (nutricion.containsKey("nutrients")) {
                            List<Map<String, Object>> nutrientes = (List<Map<String, Object>>) nutricion
                                    .get("nutrients");

                            for (Map<String, Object> nutriente : nutrientes) {
                                String nombreNutriente = (String) nutriente.get("name");
                                Float cantidad = ((Number) nutriente.get("amount")).floatValue();

                                switch (nombreNutriente) {
                                    case "Calories":
                                        nuevaComida.setCalorias_porcion(cantidad);
                                        break;
                                    case "Protein":
                                        nuevaComida.setProteina_porcion(cantidad);
                                        break;
                                    case "Carbohydrates":
                                        nuevaComida.setCarbohidratos_porcion(cantidad);
                                        break;
                                    case "Fat":
                                        nuevaComida.setGrasa_porcion(cantidad);
                                        break;
                                }
                            }
                        }
                    }
                    listaRecomendaciones.add(nuevaComida);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error crítico al consultar Spoonacular: " + e.getMessage());
        }

        return listaRecomendaciones;
    }
}