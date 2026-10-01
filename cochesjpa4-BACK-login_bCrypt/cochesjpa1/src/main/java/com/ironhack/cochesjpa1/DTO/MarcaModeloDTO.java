package com.ironhack.cochesjpa1.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class MarcaModeloDTO {

    private String marca;
    private String modelo;
   

    public MarcaModeloDTO(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

}
