package com.macrofit.rutinaejercicio.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macrofit.rutinaejercicio.model.RutinaEjercicio;
import com.macrofit.rutinaejercicio.model.Entity.RutinaEjercicioEntity;
import com.macrofit.rutinaejercicio.repository.RutinaEjercicioRepository;

@Service
public class RutinaEjercicioService {

    @Autowired
    private RutinaEjercicioRepository repositorioRutinaEjercicio;

     public List<RutinaEjercicio> obtenerTodos() {
        return repositorioRutinaEjercicio.findAll().stream()
                .map(this::convertirARutinaEjercicio)
                .collect(Collectors.toList());
    }

    public RutinaEjercicio obtenerPorId(Integer id) {
        Optional<RutinaEjercicioEntity> entidad = repositorioRutinaEjercicio.findById(id);
        return entidad.map(this::convertirARutinaEjercicio).orElse(null);
    }

    public List<RutinaEjercicio> obtenerPorRutina(Integer id_rutina) {
        return repositorioRutinaEjercicio.findAll().stream()
                .filter(re -> re.getId_rutina() != null && re.getId_rutina().equals(id_rutina))
                .sorted(Comparator.comparing(RutinaEjercicioEntity::getOrden,
                        Comparator.nullsLast(Integer::compareTo)))
                .map(this::convertirARutinaEjercicio)
                .collect(Collectors.toList());
    }

    public RutinaEjercicio crearRutinaEjercicio(RutinaEjercicio rutinaEjercicio) {
        RutinaEjercicioEntity guardada = repositorioRutinaEjercicio.save(convertirAEntidad(rutinaEjercicio));
        return convertirARutinaEjercicio(guardada);
    }

    public RutinaEjercicio modificarRutinaEjercicio(Integer id, RutinaEjercicio actualizado) {
        return repositorioRutinaEjercicio.findById(id).map(entidad -> {

            if (actualizado.getId_rutina() != null)
                entidad.setId_rutina(actualizado.getId_rutina());

            if (actualizado.getId_ejercicio() != null)
                entidad.setId_ejercicio(actualizado.getId_ejercicio());

            if (actualizado.getOrden() != null)
                entidad.setOrden(actualizado.getOrden());

            if (actualizado.getSeries() != null)
                entidad.setSeries(actualizado.getSeries());

            if (actualizado.getRepeticiones() != null)
                entidad.setRepeticiones(actualizado.getRepeticiones());

            if (actualizado.getTiempo_seg() != null)
                entidad.setTiempo_seg(actualizado.getTiempo_seg());

            if (actualizado.getPeso_referencia() != null)
                entidad.setPeso_referencia(actualizado.getPeso_referencia());

            RutinaEjercicioEntity guardada = repositorioRutinaEjercicio.save(entidad);
            return convertirARutinaEjercicio(guardada);

        }).orElse(null);
    }

    public boolean eliminarRutinaEjercicio(Integer id) {
        if (repositorioRutinaEjercicio.existsById(id)) {
            repositorioRutinaEjercicio.deleteById(id);
            return true;
        }
        return false;
    }

    public int eliminarPorRutina(Integer id_rutina) {
        List<RutinaEjercicioEntity> lista = repositorioRutinaEjercicio.findAll().stream()
                .filter(re -> re.getId_rutina() != null && re.getId_rutina().equals(id_rutina))
                .collect(Collectors.toList());

        repositorioRutinaEjercicio.deleteAll(lista);
        return lista.size();
    }

    //Conversiones

    private RutinaEjercicio convertirARutinaEjercicio(RutinaEjercicioEntity entidad) {
        if (entidad == null) return null;

        RutinaEjercicio rutinaEjercicio = new RutinaEjercicio();
        rutinaEjercicio.setId_rutina_ejercicio(entidad.getId_rutina_ejercicio());
        rutinaEjercicio.setId_rutina(entidad.getId_rutina());
        rutinaEjercicio.setId_ejercicio(entidad.getId_ejercicio());
        rutinaEjercicio.setOrden(entidad.getOrden());
        rutinaEjercicio.setSeries(entidad.getSeries());
        rutinaEjercicio.setRepeticiones(entidad.getRepeticiones());
        rutinaEjercicio.setTiempo_seg(entidad.getTiempo_seg());
        rutinaEjercicio.setPeso_referencia(entidad.getPeso_referencia());
        return rutinaEjercicio;
    }

    private RutinaEjercicioEntity convertirAEntidad(RutinaEjercicio rutinaEjercicio) {
        if (rutinaEjercicio == null) return null;

        RutinaEjercicioEntity entidad = new RutinaEjercicioEntity();
        entidad.setId_rutina_ejercicio(rutinaEjercicio.getId_rutina_ejercicio());
        entidad.setId_rutina(rutinaEjercicio.getId_rutina());
        entidad.setId_ejercicio(rutinaEjercicio.getId_ejercicio());
        entidad.setOrden(rutinaEjercicio.getOrden());
        entidad.setSeries(rutinaEjercicio.getSeries());
        entidad.setRepeticiones(rutinaEjercicio.getRepeticiones());
        entidad.setTiempo_seg(rutinaEjercicio.getTiempo_seg());
        entidad.setPeso_referencia(rutinaEjercicio.getPeso_referencia());
        return entidad;
    }
}