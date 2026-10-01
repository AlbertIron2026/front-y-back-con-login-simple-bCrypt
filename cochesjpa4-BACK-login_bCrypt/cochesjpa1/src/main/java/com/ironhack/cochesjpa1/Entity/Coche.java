// vamos a usar @ ANOTACIONES JPA => lo más comun hoy, e integrarla en SPRING
// JPA ORM => Object relational mapping 
// => convierte: 1- la clase en tabla en la BD 2- las propiedades de la clase en columnas
// los valores pueden insertarse mediante java, mediante HTTP client como POSTMAN, o desde JS (front-end)
// en realidad, todo esto se podria hacer con JDBCclient

package com.ironhack.cochesjpa1.Entity;



import jakarta.persistence.Entity; // esta importa @Entity. Sin @Entity, no va a la BD

import jakarta.persistence.Table; //no es obligatorio. 
//permite cambiar el nombre de la tabla que se genera en la base de datos

import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column; //permite cambiar el nombre de la columna en la BD

import jakarta.persistence.GeneratedValue; // para crear id autoincremental
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// con lombok solo hay que poner las anotaciones para getters, setters y contructores
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
@Table(name="coche")
public class Coche {
    //id, marca, modelo, matricula, precio
    
    //para crear id autoincrement se usa generatedvalue. Identity para mySQL

    @Id
    @Column(name="idcoche")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Long con mayúscula permite NULL. Se corresponde en SQL con BigInt

    // si no cambio el nombre de la columna, lo dejo tal cual
    private String marca;


    private String modelo;

    // la matricula por seguridad la habiamos puesto como UNIQUE
    //puedes decirle que no sea null (nullable = false)
    // @JsonIgnore para que no esté en el JSON, ni de entrada ni de salida
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) -> no mostrar la matricula
    // @JsonProperty(access = JsonProperty.Access.READ_ONLY) -> no cambiar la matricula
    @Column(unique=true, nullable = false)
    private String matricula;

    private double precio;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    @JsonBackReference
    private Marca marcaId;
}
