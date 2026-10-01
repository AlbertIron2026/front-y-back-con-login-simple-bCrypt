package com.ironhack.cochesjpa1.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ironhack.cochesjpa1.Entity.Marca;
import com.ironhack.cochesjpa1.Services.MarcaServicio;

//aqui le dices las rutas (ENDPOINTS) para POSTMAN o internet
// le dices qué rutas son para los servicios creados
@RestController
@RequestMapping("/marcas")
public class MarcaControlador {
    
    // mismo tema de instanciar una de servicios con @Autowired
    @Autowired 
    private MarcaServicio servicioMarca;

    //pillamos los servicios uno a uno y le damos una ruta

    // este pilla la ruta general (/marcas) para mostra todo: método HTTP GET
    @GetMapping
    public List<Marca> listarMarcas(){
        return servicioMarca.consultarMarcas();
    }

    // leer un marca por su id (el id seria variable {id} ): método HTTP  GET
    // Optional para que pueda devolver null
    @GetMapping("/{id}")
    public Optional<Marca> consultarUnaMarca(@PathVariable Long id){
        return servicioMarca.buscarPorId(id);
    }


    // añadir un marca:Va a leer el JSON que le decimos.  método HTTP POST. 
    @PostMapping
    public Marca guardarMarca(@RequestBody Marca marcaJSON){
        return servicioMarca.registMarca(marcaJSON);
    }

    // borrar uno por su id (el id seria variable {id} ): método HTTP DELETE
    // como lo borra no devuelve nada 8auqnue podria devolver una HTTP request entity
    @DeleteMapping("/{id}")
    public void eliminarMarca(@PathVariable Long id){
        servicioMarca.borrarMarca(id);
    }
}
