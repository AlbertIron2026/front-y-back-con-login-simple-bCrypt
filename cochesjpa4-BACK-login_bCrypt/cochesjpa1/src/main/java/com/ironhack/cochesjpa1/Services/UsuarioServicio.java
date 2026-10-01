package com.ironhack.cochesjpa1.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ironhack.cochesjpa1.Entity.Usuario;
import com.ironhack.cochesjpa1.Repository.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder; // ya viene con security PasswordEncoder 

    public UsuarioServicio(
            UsuarioRepositorio usuarioRepositorio,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }


    public Usuario crearUsuario(Usuario usuario) {

        // Convertimos la contraseña original
        // en un hash BCrypt
        String passwordHasheada =
                passwordEncoder.encode(usuario.getPassword());

        usuario.setPassword(passwordHasheada);

        return usuarioRepositorio.save(usuario);
    }
}