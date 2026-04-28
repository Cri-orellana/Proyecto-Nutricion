package com.macrofit.rutinaejercicio.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Ejercicio")
public class EjercicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ejercicio;

    private Integer id_zona;
    private Integer id_implemento;

    private String nombre_ejercicio;

    @Column(length = 500)
    private String descripcion;

    private String imagen_ejercicio;
    private String video_ejercicio;

    private Boolean activo_catalogo;

    private String id_externo;
    private String fuente;
    private Boolean original;
    
}
