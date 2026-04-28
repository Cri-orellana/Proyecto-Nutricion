package com.macrofit.rutinaejercicio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macrofit.rutinaejercicio.model.Entity.RutinaUsuarioEntity;

@Repository
public interface RutinaUsuarioRepository extends JpaRepository<RutinaUsuarioEntity,Integer>{

    List<RutinaUsuarioEntity> findByIdUsuario(Integer id_usuario);

    List<RutinaUsuarioEntity> findByIdUsuarioAndActivoTrue(Integer id_usuario);

    List<RutinaUsuarioEntity> findByIdUsuarioAndActivoFalse(Integer id_usuario);

    List<RutinaUsuarioEntity> findByIdRutina(Integer id_rutina);
}
