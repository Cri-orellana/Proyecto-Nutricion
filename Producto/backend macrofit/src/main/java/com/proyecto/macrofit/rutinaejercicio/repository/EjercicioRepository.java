package com.macrofit.rutinaejercicio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macrofit.rutinaejercicio.model.Entity.EjercicioEntity;

@Repository
public interface EjercicioRepository extends JpaRepository<EjercicioEntity, Integer>{

    List<EjercicioEntity> findByActivoCatalogoTrue();

    List<EjercicioEntity> findByIdZona(Integer id_zona);

    List<EjercicioEntity> findByIdImplemento(Integer id_implemento);

    List<EjercicioEntity> findByIdZonaAndActivoCatalogoTrue(Integer id_zona);

    List<EjercicioEntity> findByIdImplementoAndActivoCatalogoTrue(Integer id_implemento);

    List<EjercicioEntity> findByNombreEjercicioContainingIgnoreCase(String nombre_ejercicio);

    Optional<EjercicioEntity> findByIdExterno(String id_externo);
    
}
