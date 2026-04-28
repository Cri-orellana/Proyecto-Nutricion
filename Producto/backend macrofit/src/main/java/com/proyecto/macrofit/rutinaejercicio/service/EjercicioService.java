package com.macrofit.rutinaejercicio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macrofit.rutinaejercicio.model.Ejercicio;
import com.macrofit.rutinaejercicio.model.Entity.EjercicioEntity;
import com.macrofit.rutinaejercicio.repository.EjercicioRepository;

@Service
public class EjercicioService {
    
    @Autowired
    private EjercicioRepository repositorioEjercicio;

    public List<Ejercicio> obtenerTodosLosEjercicios() {
        return repositorioEjercicio.findAll().stream()
                .map(this::convertirAEjercicio)
                .collect(Collectors.toList());
    }

    public List<Ejercicio> obtenerEjerciciosActivos() {
        return repositorioEjercicio.findAll().stream()
                .filter(e -> Boolean.TRUE.equals(e.getActivo_catalogo()))
                .map(this::convertirAEjercicio)
                .collect(Collectors.toList());
    }

    public Ejercicio obtenerEjercicioPorId(Integer id) {
        Optional<EjercicioEntity> entidad = repositorioEjercicio.findById(id);
        return entidad.map(this::convertirAEjercicio).orElse(null);
    }

    public List<Ejercicio> obtenerEjerciciosPorZona(Integer id_zona) {
        return repositorioEjercicio.findAll().stream()
                .filter(e -> e.getId_zona() != null && e.getId_zona().equals(id_zona))
                .map(this::convertirAEjercicio)
                .collect(Collectors.toList());
    }

    public List<Ejercicio> buscarPorNombre(String nombre) {
        return repositorioEjercicio.findAll().stream()
                .filter(e -> e.getNombre_ejercicio() != null &&
                        e.getNombre_ejercicio().toLowerCase().contains(nombre.toLowerCase()))
                .map(this::convertirAEjercicio)
                .collect(Collectors.toList());
    }

    public Ejercicio crearEjercicio(Ejercicio ejercicio) {
        if (ejercicio.getActivo_catalogo() == null) {
            ejercicio.setActivo_catalogo(true);
        }
        if (ejercicio.getOriginal() == null) {
            ejercicio.setOriginal(false);
        }

        EjercicioEntity guardado = repositorioEjercicio.save(convertirAEntidad(ejercicio));
        return convertirAEjercicio(guardado);
    }

    public Ejercicio modificarEjercicio(Integer id, Ejercicio ejercicioActualizado) {
        return repositorioEjercicio.findById(id).map(entidad -> {

            if (ejercicioActualizado.getId_zona() != null)
                entidad.setId_zona(ejercicioActualizado.getId_zona());

            if (ejercicioActualizado.getId_implemento() != null)
                entidad.setId_implemento(ejercicioActualizado.getId_implemento());

            if (ejercicioActualizado.getId_externo() != null)
                entidad.setId_externo(ejercicioActualizado.getId_externo());

            if (ejercicioActualizado.getFuente() != null)
                entidad.setFuente(ejercicioActualizado.getFuente());

            if (ejercicioActualizado.getOriginal() != null)
                entidad.setOriginal(ejercicioActualizado.getOriginal());

            if (ejercicioActualizado.getDescripcion() != null)
                entidad.setDescripcion(ejercicioActualizado.getDescripcion());

            if (ejercicioActualizado.getNombre_ejercicio() != null)
                entidad.setNombre_ejercicio(ejercicioActualizado.getNombre_ejercicio());

            if (ejercicioActualizado.getImagen_ejercicio() != null)
                entidad.setImagen_ejercicio(ejercicioActualizado.getImagen_ejercicio());

            if (ejercicioActualizado.getVideo_ejercicio() != null)
                entidad.setVideo_ejercicio(ejercicioActualizado.getVideo_ejercicio());

            if (ejercicioActualizado.getActivo_catalogo() != null)
                entidad.setActivo_catalogo(ejercicioActualizado.getActivo_catalogo());

            EjercicioEntity guardado = repositorioEjercicio.save(entidad);
            return convertirAEjercicio(guardado);

        }).orElse(null);
    }

    public boolean eliminarEjercicio(Integer id) {
        if (repositorioEjercicio.existsById(id)) {
            repositorioEjercicio.deleteById(id);
            return true;
        }
        return false;
    }

    private Ejercicio convertirAEjercicio(EjercicioEntity entidad) {
        if (entidad == null) return null;

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId_ejercicio(entidad.getId_ejercicio());
        ejercicio.setId_zona(entidad.getId_zona());
        ejercicio.setId_implemento(entidad.getId_implemento());
        ejercicio.setId_externo(entidad.getId_externo());
        ejercicio.setFuente(entidad.getFuente());
        ejercicio.setOriginal(entidad.getOriginal());
        ejercicio.setDescripcion(entidad.getDescripcion());
        ejercicio.setNombre_ejercicio(entidad.getNombre_ejercicio());
        ejercicio.setImagen_ejercicio(entidad.getImagen_ejercicio());
        ejercicio.setVideo_ejercicio(entidad.getVideo_ejercicio());
        ejercicio.setActivo_catalogo(entidad.getActivo_catalogo());
        return ejercicio;
    }

    private EjercicioEntity convertirAEntidad(Ejercicio ejercicio) {
        if (ejercicio == null) return null;

        EjercicioEntity entidad = new EjercicioEntity();
        entidad.setId_ejercicio(ejercicio.getId_ejercicio());
        entidad.setId_zona(ejercicio.getId_zona());
        entidad.setId_implemento(ejercicio.getId_implemento());
        entidad.setId_externo(ejercicio.getId_externo());
        entidad.setFuente(ejercicio.getFuente());
        entidad.setOriginal(ejercicio.getOriginal());
        entidad.setDescripcion(ejercicio.getDescripcion());
        entidad.setNombre_ejercicio(ejercicio.getNombre_ejercicio());
        entidad.setImagen_ejercicio(ejercicio.getImagen_ejercicio());
        entidad.setVideo_ejercicio(ejercicio.getVideo_ejercicio());
        entidad.setActivo_catalogo(ejercicio.getActivo_catalogo());
        return entidad;
    }
    
}
