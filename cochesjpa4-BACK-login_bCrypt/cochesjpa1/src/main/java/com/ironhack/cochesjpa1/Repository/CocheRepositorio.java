package com.ironhack.cochesjpa1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ironhack.cochesjpa1.DTO.MarcaModeloDTO;
import com.ironhack.cochesjpa1.Entity.Coche;

// antiguamente se usaba CRUDJpa, pero NO usarlo hoy dia
public interface CocheRepositorio extends JpaRepository<Coche, Long> {

        // aqui dentro se podrian añadir métodos extra personalizados
        // incluso mediante consultas directas (queries mySQL)
        // ejemplo, mostrar marca y sus coches:

        // text block de Java: """codigo"""
        // con native query, usar nombres columans de la BD:
        @Query(value = """
                        SELECT c.modelo, m.nombre_marca
                        FROM coche c
                        JOIN marca m ON c.marca_id = m.idmarca
                        """, nativeQuery = true)
        List<Object[]> obtenerModeloYMarcaNative();

        // con JPQL (sin DTO). Trabaja con entidades *************************
        // usar los nombres de los atributos java, no los de la BD
        // los JOIN no llevan ON aqui!

        @Query("""
                        SELECT c.modelo, c.marcaId.nombreMarca
                        FROM Coche c
                        JOIN c.marcaId
                        """)
        List<Object[]> obtenerModeloYMarca();

        // lo mismo que escribir: @Query("SELECT c.modelo, c.marcaId.nombreMarca FROM
        // Coche c JOIN c.marcaId")

        // JPQL con DTO:
        // WHERE c.id = :id significa 'parametro llamado id'
        @Query("""
                        SELECT new com.ironhack.cochesjpa1.DTO.MarcaModeloDTO(
                            m.nombreMarca,
                            c.modelo
                        )
                        FROM Coche c
                        JOIN c.marcaId m
                        WHERE c.id = :id
                        """)
        MarcaModeloDTO MarcaModeloDTO(@Param("id") Long id);

}
