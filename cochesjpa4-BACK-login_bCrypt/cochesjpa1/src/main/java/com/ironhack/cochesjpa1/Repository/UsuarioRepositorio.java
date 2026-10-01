package com.ironhack.cochesjpa1.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ironhack.cochesjpa1.Entity.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // busca por nombre
    // normalment el nombre que le das a los metodos no importa
    // pero en este caso sí: findByUsername
    //entiende: SELECT *  FROM usurio WHERE username="usurio_del_input"
    // findByNombreColumna te busca por esa columna y no tienes que escribir el codigo
    Optional<Usuario> findByUsername(String username);
    // optional es un tipo que permite devolver NULL
    // permitiria buscar un nombre de usuario que no existe => return NULL
}