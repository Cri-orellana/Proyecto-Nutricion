package com.proyecto.macrofit.usuarios.repository;

import com.proyecto.macrofit.usuarios.model.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

}