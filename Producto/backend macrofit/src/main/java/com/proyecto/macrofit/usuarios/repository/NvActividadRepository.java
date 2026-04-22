package com.proyecto.macrofit.usuarios.repository;

import com.proyecto.macrofit.usuarios.model.Entity.NvActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvActividadRepository extends JpaRepository<NvActividadEntity, Integer> {
}