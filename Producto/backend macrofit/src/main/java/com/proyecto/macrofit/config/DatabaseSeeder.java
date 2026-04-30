package com.proyecto.macrofit.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.proyecto.macrofit.usuarios.model.NvActividad;
import com.proyecto.macrofit.usuarios.model.Objetivo;
import com.proyecto.macrofit.usuarios.model.Entity.NvActividadEntity;
import com.proyecto.macrofit.usuarios.model.Entity.ObjetivoEntity;
import com.proyecto.macrofit.usuarios.repository.NvActividadRepository;
import com.proyecto.macrofit.usuarios.repository.ObjetivoRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private ObjetivoRepository objetivoRepo;

    @Autowired
    private NvActividadRepository actividadRepo;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Verificando catálogos de base de datos...");

        if (objetivoRepo.count() == 0) {
            System.out.println("Tabla Objetivo vacía. Insertando datos por defecto...");
            objetivoRepo.saveAll(Arrays.asList(
                    new Objetivo(1, "Bajar peso (Déficit)", -500f),
                    new Objetivo(2, "Mantener peso", 0f),
                    new Objetivo(3, "Subir masa muscular (Volumen)", 500f)));
        }

        if (actividadRepo.count() == 0) {
            System.out.println("Tabla Nv_Actividad vacía. Insertando datos por defecto...");
            actividadRepo.saveAll(Arrays.asList(
                    new NvActividad(1, "Sedentario (Poco o ningún ejercicio)", 1.2f),
                    new NvActividad(2, "Ligero (Ejercicio 1-3 días/sem)", 1.375f),
                    new NvActividad(3, "Moderado (Ejercicio 3-5 días/sem)", 1.55f),
                    new NvActividad(4, "Intenso (Ejercicio 6-7 días/sem)", 1.725f)));
        }

        System.out.println("Catálogos listos y verificados.");
    }
}