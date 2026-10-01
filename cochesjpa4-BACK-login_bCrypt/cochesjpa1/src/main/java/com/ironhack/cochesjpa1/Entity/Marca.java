package com.ironhack.cochesjpa1.Entity;



import jakarta.persistence.Entity; 

import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column; 

import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
@Table(name="marca")
public class Marca {

    @Id
    @Column(name="idmarca")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_marca")
    private String nombreMarca;


    private String pais;

     @OneToMany(mappedBy = "marcaId", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<Coche> coches = new ArrayList<>();

}
