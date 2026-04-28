package com.macrofit.rutinaejercicio.model;

import lombok.Data;

@Data
public class Rutina {

    private Integer id_rutina;
    private String nombre_rutina;
    private String descripcion;
    private Boolean activo_catalogo;
    
}
