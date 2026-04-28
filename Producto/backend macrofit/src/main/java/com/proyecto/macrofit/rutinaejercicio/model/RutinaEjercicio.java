package com.macrofit.rutinaejercicio.model;

import lombok.Data;

@Data
public class RutinaEjercicio {

    private Integer id_rutina_ejercicio;
    private Integer id_ejercicio;
    private Integer id_rutina;
    private Integer orden;
    private Integer series;
    private Integer tiempo_seg;
    private Integer repeticiones;
    private Float peso_referencia;
    
}
