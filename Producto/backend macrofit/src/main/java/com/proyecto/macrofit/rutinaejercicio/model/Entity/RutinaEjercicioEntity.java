package com.macrofit.rutinaejercicio.model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Rutina_ejercicio")
public class RutinaEjercicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rutina_ejercicio;

    private Integer id_ejercicio;
    private Integer id_rutina;

    private Integer orden;
    private Integer series;
    private Integer tiempo_seg;
    private Integer repeticiones;
    private Float peso_referencia;
    
}
