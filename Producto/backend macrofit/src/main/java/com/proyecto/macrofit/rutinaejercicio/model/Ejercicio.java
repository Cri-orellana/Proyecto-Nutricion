package com.macrofit.rutinaejercicio.model;

import lombok.Data;

@Data
public class Ejercicio {

    private Integer id_ejercicio;
    private Integer id_zona;
    private Integer id_implemento;
    private String nombre_ejercicio;
    private String descripcion;
    private String imagen_ejercicio;
    private String video_ejercicio;
    private Boolean activo_catalogo;
    private String id_externo;
    private String fuente;
    private Boolean original;
    
}
