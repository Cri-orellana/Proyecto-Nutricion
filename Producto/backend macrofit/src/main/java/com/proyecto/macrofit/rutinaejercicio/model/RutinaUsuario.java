package com.macrofit.rutinaejercicio.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RutinaUsuario {

    private Integer id_rutina_usuario;
    private Integer id_rutina;
    private Integer id_usuario;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Boolean activo;

    
}
