package com.ironhack.cochesjpa1.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ironhack.cochesjpa1.Entity.Marca;
import com.ironhack.cochesjpa1.Repository.MarcaRepositorio;

@Service
public class MarcaServicio {
    // del repositorio de métodos de JPA, esta capa es la que dice cuáles usas
    // tiene que ver con la  lógica del negocio, y tienes que preguntar al cliente
    // qué necesita que haga su aplicación
    // ejemplo basico ver todos/ ver uno por id/ añadir uno/ borrar uno por id
    // modificar por id es más complicado pero también se puede

    //lo primero es instanciar el repositorio. Para ello, 
    // en vez de crear un constructor e instanciar, se usa el atajo @Autowired

    @Autowired
    private MarcaRepositorio marcaRepositorioServicio ;

    // luego ya puedes crear los metodos:

    // consultar todos: devuelve una lista de todos los marcas
    public List<Marca> consultarMarcas(){
        return (List<Marca>) marcaRepositorioServicio.findAll();
    }

    //para buscar uno por id
    //lo mismo usando el metodo JPA findById,
    //y recibiendo el id del marca a consultar
    // el tipo Optional nos permite devolver NULL
    public Optional<Marca> buscarPorId(Long id){
        return marcaRepositorioServicio.findById(id);
    }

    //guardar un marca. retornas al JPA repository el marca que estas guardando
    public Marca registMarca(Marca marca){
        return marcaRepositorioServicio.save(marca);
    }

    //borrar uno por id
    public void borrarMarca(Long id){
        marcaRepositorioServicio.deleteById(id);
    }


}
