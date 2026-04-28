package com.macrofit.rutinaejercicio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macrofit.rutinaejercicio.model.Entity.RutinaEjercicioEntity;

@Repository
public interface RutinaEjercicioRepository extends JpaRepository<RutinaEjercicioEntity,Integer>{

    List<RutinaEjercicioEntity> findByIdRutinaOrderByOrdenAsc(Integer id_rutina);

    List<RutinaEjercicioEntity> findByIdEjercicio(Integer id_ejercicio);

    void deleteByIdRutina(Integer id_rutina);
    
}
