package com.macrofit.rutinaejercicio.model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Zona")
public class ZonaEntity {

    @Id
    private Integer id_zona;

    private String nombre_zona;
    
}
