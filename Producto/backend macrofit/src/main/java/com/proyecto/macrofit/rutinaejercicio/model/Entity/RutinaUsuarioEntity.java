package com.macrofit.rutinaejercicio.model.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Rutina_usuario")
public class RutinaUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rutina_usuario;

    private Integer id_rutina;
    private Integer id_usuario;

    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;

    private Boolean activo;

    
}
