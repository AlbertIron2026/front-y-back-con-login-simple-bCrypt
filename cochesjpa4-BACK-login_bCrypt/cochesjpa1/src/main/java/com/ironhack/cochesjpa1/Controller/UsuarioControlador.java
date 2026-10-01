package com.ironhack.cochesjpa1.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ironhack.cochesjpa1.Entity.Usuario;
import com.ironhack.cochesjpa1.Services.UsuarioServicio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }


    // ResponseEntity no es obligatorio, es información extra dobre la respuesta de HTTP
    @PostMapping
    public ResponseEntity<String> crearUsuario(
            @RequestBody Usuario usuario) {

        usuarioServicio.crearUsuario(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Usuario creado correctamente");
    }
}