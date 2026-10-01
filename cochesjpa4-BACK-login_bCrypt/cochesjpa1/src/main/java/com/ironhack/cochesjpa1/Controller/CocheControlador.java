package com.ironhack.cochesjpa1.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ironhack.cochesjpa1.DTO.MarcaModeloDTO;
import com.ironhack.cochesjpa1.Entity.Coche;
import com.ironhack.cochesjpa1.Services.CocheServicio;


//aqui le dices las rutas (ENDPOINTS) para POSTMAN o internet
// le dices qué rutas son para los servicios creados
// @Crossorigin(origins = "*") //esto simplificado, tambien funciona

@RestController
@RequestMapping("/coches")
public class CocheControlador {

    // mismo tema de instanciar una de servicios con @Autowired
    @Autowired
    private CocheServicio servicioCoche;

    // pillamos los servicios uno a uno y le damos una ruta

    // este pilla la ruta general (/coches) para mostra todo: método HTTP GET
    @GetMapping
    public List<Coche> listarCoches() {
        return servicioCoche.consultarCoches();
    }

    // leer un coche por su id (el id seria variable {id} ): método HTTP GET
    // Optional para que pueda devolver null
    @GetMapping("/{id}")
    public Optional<Coche> consultarUnCoche(@PathVariable Long id) {
        return servicioCoche.buscarPorId(id);
    }

    // añadir un coche:Va a leer el JSON que le decimos. método HTTP POST.
    @PostMapping
    public Coche guardarCoche(@RequestBody Coche cocheJSON) {
        return servicioCoche.registCoche(cocheJSON);
    }

    // borrar uno por su id (el id seria variable {id} ): método HTTP DELETE
    // como lo borra no devuelve nada 8auqnue podria devolver una HTTP request
    // entity
    @DeleteMapping("/{id}")
    public void eliminarCoche(@PathVariable Long id) {
        servicioCoche.borrarCoche(id);
    }

    // modificar un coche por su id: método HTTP PUT
    @PutMapping("/{id}")
    public Coche actualizarCoche(@PathVariable Long id, @RequestBody Coche cocheJSON) {
        return servicioCoche.actualizarCoche(id, cocheJSON);
    }

    
    // añadimos rutas para las consultas especificas
    // Native SQL
    @GetMapping("/modelo-marca-native")
    public List<Object[]> obtenerModeloYMarcaNative() {
        return servicioCoche.obtenerModeloYMarcaNative();
    }

    // JPQL sin DTO
    @GetMapping("/modelo-marca-jpql")
    public List<Object[]> obtenerModeloYMarcaJPQL() {
        return servicioCoche.obtenerModeloYMarcaJPQL();
    }

    // JPQL con DTO
    @GetMapping("/modelo-marca-dto/{id}")
    public MarcaModeloDTO obtenerMarcaModeloDTO(@PathVariable Long id) {
        return servicioCoche.obtenerMarcaModeloDTO(id);
    }

}
