package com.ironhack.cochesjpa1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ironhack.cochesjpa1.Entity.Marca;

// antiguamente se usaba CRUDJpa, pero NO usarlo hoy dia
public interface MarcaRepositorio extends JpaRepository<Marca, Long> {
    
  

}
