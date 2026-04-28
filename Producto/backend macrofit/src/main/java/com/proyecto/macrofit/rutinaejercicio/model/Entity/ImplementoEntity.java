package com.macrofit.rutinaejercicio.model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Implemento")
public class ImplementoEntity {

    @Id
    private Integer id_implemento;

    private String desc_implemento;

    
}
