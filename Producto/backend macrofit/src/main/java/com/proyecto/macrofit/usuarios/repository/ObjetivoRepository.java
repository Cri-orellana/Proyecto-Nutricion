package com.proyecto.macrofit.usuarios.repository;

import com.proyecto.macrofit.usuarios.model.Entity.ObjetivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjetivoRepository extends JpaRepository<ObjetivoEntity, Integer> {
}