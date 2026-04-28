package com.macrofit.rutinaejercicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macrofit.rutinaejercicio.model.Entity.ImplementoEntity;

@Repository
public interface ImplementoRepository extends JpaRepository<ImplementoEntity, Integer>{
    
}
