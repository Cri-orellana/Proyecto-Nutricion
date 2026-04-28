package com.macrofit.rutinaejercicio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macrofit.rutinaejercicio.model.Rutina;
import com.macrofit.rutinaejercicio.model.Entity.RutinaEntity;
import com.macrofit.rutinaejercicio.repository.RutinaRepository;

@Service
public class RutinaService {
    
    @Autowired
    private RutinaRepository repositorioRutina;

    public List<Rutina> obtenerRutinas(){
        return repositorioRutina.findAll().stream()
        .map(this::convertirARutina).collect(Collectors.toList());
    }

    public List<Rutina> obtenerRutinasActivasCatalogo() {
        return repositorioRutina.findAll().stream()
                .filter(r -> Boolean.TRUE.equals(r.getActivo_catalogo()))
                .map(this::convertirARutina)
                .collect(Collectors.toList());
    }

    public Rutina obtenerRutinaPorId(Integer id) {
        Optional<RutinaEntity> entidad = repositorioRutina.findById(id);
        return entidad.map(this::convertirARutina).orElse(null);
    }

    public List<Rutina> buscarRutinasPorNombre(String nombre) {
        return repositorioRutina.findAll().stream()
                .filter(r -> r.getNombre_rutina() != null &&
                        r.getNombre_rutina().toLowerCase().contains(nombre.toLowerCase()))
                .map(this::convertirARutina)
                .collect(Collectors.toList());
    }

    public Rutina crearRutina(Rutina rutina) {
        if (rutina.getActivo_catalogo() == null) {
            rutina.setActivo_catalogo(true);
        }

        RutinaEntity guardada = repositorioRutina.save(convertirAEntidad(rutina));
        return convertirARutina(guardada);
    }

    public Rutina modificarRutina(Integer id, Rutina rutinaActualizada) {
        return repositorioRutina.findById(id).map(entidad -> {

            if (rutinaActualizada.getNombre_rutina() != null)
                entidad.setNombre_rutina(rutinaActualizada.getNombre_rutina());

            if (rutinaActualizada.getDescripcion() != null)
                entidad.setDescripcion(rutinaActualizada.getDescripcion());

            if (rutinaActualizada.getActivo_catalogo() != null)
                entidad.setActivo_catalogo(rutinaActualizada.getActivo_catalogo());

            RutinaEntity guardada = repositorioRutina.save(entidad);
            return convertirARutina(guardada);

        }).orElse(null);
    }

    public boolean eliminarRutina(Integer id) {
        if (repositorioRutina.existsById(id)) {
            repositorioRutina.deleteById(id);
            return true;
        }
        return false;
    }
    

    //Conversiones
    private Rutina convertirARutina(RutinaEntity entidad){
        if (entidad == null) return null;
        
        Rutina rutina = new Rutina();
        rutina.setId_rutina(entidad.getId_rutina());
        rutina.setNombre_rutina(entidad.getNombre_rutina());
        rutina.setDescripcion(entidad.getDescripcion());
        rutina.setActivo_catalogo(entidad.getActivo_catalogo());
        return rutina;
    }

    private RutinaEntity convertirAEntidad(Rutina rutina){
        if (rutina == null) return null;

        RutinaEntity entidad = new RutinaEntity();
        entidad.setId_rutina(rutina.getId_rutina());
        entidad.setNombre_rutina(rutina.getNombre_rutina());
        entidad.setDescripcion(rutina.getDescripcion());
        entidad.setActivo_catalogo(rutina.getActivo_catalogo());
        return entidad;
    }
}
