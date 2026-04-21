package com.proyecto.macrofit.usuarios.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.macrofit.usuarios.repository.UsuarioRepository;
import com.proyecto.macrofit.usuarios.model.Usuario;
import com.proyecto.macrofit.usuarios.model.Entity.UsuarioEntity;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repositorioUsuario;

    // Listar todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return repositorioUsuario.findAll().stream()
                .map(this::convertirAUsuario)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public Usuario obtenerUsuarioPorId(Integer id) {
        Optional<UsuarioEntity> entidad = repositorioUsuario.findById(id);
        return entidad.map(this::convertirAUsuario).orElse(null);
    }

    // Crear nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getRol() == null)
            usuario.setRol("USER");
        UsuarioEntity entidadGuardada = repositorioUsuario.save(convertirAEntidad(usuario));
        return convertirAUsuario(entidadGuardada);
    }

    // Modificar usuario existente
    public Usuario modificarUsuario(Integer id, Usuario usuarioActualizado) {
        return repositorioUsuario.findById(id).map(entidadExistente -> {
            entidadExistente.setNom_usuario(usuarioActualizado.getNom_usuario());
            entidadExistente.setCorreo(usuarioActualizado.getCorreo());
            entidadExistente.setContrasena(usuarioActualizado.getContrasena());
            entidadExistente.setPeso(usuarioActualizado.getPeso());
            entidadExistente.setAltura(usuarioActualizado.getAltura());
            entidadExistente.setId_objetivo(usuarioActualizado.getId_objetivo());
            entidadExistente.setId_nv_act(usuarioActualizado.getId_nv_act());
            entidadExistente.setTmb_objetivo(usuarioActualizado.getTmb_objetivo());
            entidadExistente.setCal_diaria(usuarioActualizado.getCal_diaria());

            UsuarioEntity guardado = repositorioUsuario.save(entidadExistente);
            return convertirAUsuario(guardado);
        }).orElse(null);
    }

    // Eliminar usuario por ID
    public boolean eliminarUsuario(Integer id) {
        if (repositorioUsuario.existsById(id)) {
            repositorioUsuario.deleteById(id);
            return true;
        }
        return false;
    }

    // Conversión entre Usuario y UsuarioEntity

    private Usuario convertirAUsuario(UsuarioEntity entidad) {
        if (entidad == null)
            return null;
        Usuario usuario = new Usuario();
        usuario.setId_usuario(entidad.getId_usuario());
        usuario.setNom_usuario(entidad.getNom_usuario());
        usuario.setCorreo(entidad.getCorreo());
        usuario.setContrasena(entidad.getContrasena());
        usuario.setRol(entidad.getRol());
        usuario.setAltura(entidad.getAltura());
        usuario.setPeso(entidad.getPeso());
        usuario.setId_objetivo(entidad.getId_objetivo());
        usuario.setId_nv_act(entidad.getId_nv_act());
        usuario.setTmb_objetivo(entidad.getTmb_objetivo());
        usuario.setCal_diaria(entidad.getCal_diaria());
        return usuario;
    }

    private UsuarioEntity convertirAEntidad(Usuario usuario) {
        if (usuario == null)
            return null;
        UsuarioEntity entidad = new UsuarioEntity();
        entidad.setId_usuario(usuario.getId_usuario());
        entidad.setNom_usuario(usuario.getNom_usuario());
        entidad.setCorreo(usuario.getCorreo());
        entidad.setContrasena(usuario.getContrasena());
        entidad.setRol(usuario.getRol());
        entidad.setAltura(usuario.getAltura());
        entidad.setPeso(usuario.getPeso());
        entidad.setId_objetivo(usuario.getId_objetivo());
        entidad.setId_nv_act(usuario.getId_nv_act());
        entidad.setTmb_objetivo(usuario.getTmb_objetivo());
        entidad.setCal_diaria(usuario.getCal_diaria());
        return entidad;
    }
}