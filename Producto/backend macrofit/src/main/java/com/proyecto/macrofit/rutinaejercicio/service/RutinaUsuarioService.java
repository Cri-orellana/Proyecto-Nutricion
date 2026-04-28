package com.macrofit.rutinaejercicio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macrofit.rutinaejercicio.model.RutinaUsuario;
import com.macrofit.rutinaejercicio.model.Entity.RutinaUsuarioEntity;
import com.macrofit.rutinaejercicio.repository.RutinaUsuarioRepository;

@Service
public class RutinaUsuarioService {
    
    @Autowired
    private RutinaUsuarioRepository repositorioRutinaUsuario;

    public List<RutinaUsuario> obtenerTodasLasAsignaciones() {
        return repositorioRutinaUsuario.findAll().stream()
                .map(this::convertirARutinaUsuario)
                .collect(Collectors.toList());
    }

    public RutinaUsuario obtenerAsignacionPorId(Integer id) {
        Optional<RutinaUsuarioEntity> entidad = repositorioRutinaUsuario.findById(id);
        return entidad.map(this::convertirARutinaUsuario).orElse(null);
    }

    public List<RutinaUsuario> obtenerRutinasPorUsuario(Integer id_usuario) {
        return repositorioRutinaUsuario.findAll().stream()
                .filter(ru -> ru.getId_usuario() != null && ru.getId_usuario().equals(id_usuario))
                .map(this::convertirARutinaUsuario)
                .collect(Collectors.toList());
    }

    public List<RutinaUsuario> obtenerHistorialRutinas(Integer id_usuario) {
        return repositorioRutinaUsuario.findAll().stream()
                .filter(ru -> ru.getId_usuario() != null && ru.getId_usuario().equals(id_usuario))
                .filter(ru -> Boolean.FALSE.equals(ru.getActivo()))
                .map(this::convertirARutinaUsuario)
                .collect(Collectors.toList());
    }

    public RutinaUsuario obtenerRutinaActivaUsuario(Integer id_usuario) {
        return repositorioRutinaUsuario.findAll().stream()
                .filter(ru -> ru.getId_usuario() != null && ru.getId_usuario().equals(id_usuario))
                .filter(ru -> Boolean.TRUE.equals(ru.getActivo()))
                .map(this::convertirARutinaUsuario)
                .findFirst()
                .orElse(null);
    }

    public RutinaUsuario asignarRutinaUsuario(RutinaUsuario rutinaUsuario) {
        if (rutinaUsuario.getActivo() == null) {
            rutinaUsuario.setActivo(true);
        }

        RutinaUsuarioEntity guardada = repositorioRutinaUsuario.save(convertirAEntidad(rutinaUsuario));
        return convertirARutinaUsuario(guardada);
    }

    public RutinaUsuario modificarAsignacion(Integer id, RutinaUsuario actualizada) {
        return repositorioRutinaUsuario.findById(id).map(entidad -> {

            if (actualizada.getId_rutina() != null)
                entidad.setId_rutina(actualizada.getId_rutina());

            if (actualizada.getId_usuario() != null)
                entidad.setId_usuario(actualizada.getId_usuario());

            if (actualizada.getFecha_inicio() != null)
                entidad.setFecha_inicio(actualizada.getFecha_inicio());

            if (actualizada.getFecha_fin() != null)
                entidad.setFecha_fin(actualizada.getFecha_fin());

            if (actualizada.getActivo() != null)
                entidad.setActivo(actualizada.getActivo());

            RutinaUsuarioEntity guardada = repositorioRutinaUsuario.save(entidad);
            return convertirARutinaUsuario(guardada);

        }).orElse(null);
    }

    public RutinaUsuario desactivarAsignacion(Integer id) {
        return repositorioRutinaUsuario.findById(id).map(entidad -> {
            entidad.setActivo(false);
            RutinaUsuarioEntity guardada = repositorioRutinaUsuario.save(entidad);
            return convertirARutinaUsuario(guardada);
        }).orElse(null);
    }

    public boolean eliminarAsignacion(Integer id) {
        if (repositorioRutinaUsuario.existsById(id)) {
            repositorioRutinaUsuario.deleteById(id);
            return true;
        }
        return false;
    }

    //Conversiones

    private RutinaUsuario convertirARutinaUsuario(RutinaUsuarioEntity entidad) {
        if (entidad == null) return null;

        RutinaUsuario rutinaUsuario = new RutinaUsuario();
        rutinaUsuario.setId_rutina_usuario(entidad.getId_rutina_usuario());
        rutinaUsuario.setId_rutina(entidad.getId_rutina());
        rutinaUsuario.setId_usuario(entidad.getId_usuario());
        rutinaUsuario.setFecha_inicio(entidad.getFecha_inicio());
        rutinaUsuario.setFecha_fin(entidad.getFecha_fin());
        rutinaUsuario.setActivo(entidad.getActivo());
        return rutinaUsuario;
    }

    private RutinaUsuarioEntity convertirAEntidad(RutinaUsuario rutinaUsuario) {
        if (rutinaUsuario == null) return null;

        RutinaUsuarioEntity entidad = new RutinaUsuarioEntity();
        entidad.setId_rutina_usuario(rutinaUsuario.getId_rutina_usuario());
        entidad.setId_rutina(rutinaUsuario.getId_rutina());
        entidad.setId_usuario(rutinaUsuario.getId_usuario());
        entidad.setFecha_inicio(rutinaUsuario.getFecha_inicio());
        entidad.setFecha_fin(rutinaUsuario.getFecha_fin());
        entidad.setActivo(rutinaUsuario.getActivo());
        return entidad;
    }
}
