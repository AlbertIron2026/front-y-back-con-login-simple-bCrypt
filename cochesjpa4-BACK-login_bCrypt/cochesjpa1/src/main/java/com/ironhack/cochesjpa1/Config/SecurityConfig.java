package com.ironhack.cochesjpa1.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ironhack.cochesjpa1.Repository.UsuarioRepositorio;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final UsuarioRepositorio usuarioRepositorio;

    public SecurityConfig(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Le decimos a Spring Security cómo buscar nuestros usuarios
    @Bean
    public UserDetailsService userDetailsService() {

        return username -> usuarioRepositorio
                .findByUsername(username)
                .map(usuario -> User
                        .withUsername(usuario.getUsername())
                        .password(usuario.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));
    }

    // AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    // Reglas de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            // Para este proyecto sencillo, desactivamos CSRF.
            // Más adelante podemos hacerlo de forma más estricta.
            .csrf(csrf -> csrf.disable())

            .cors(cors -> {})

            .authorizeHttpRequests(auth -> auth

                // Login público con POSTMAN o web
                .requestMatchers("/login", "/usuarios").permitAll()

                // Todo lo demás necesita login
                .anyRequest().authenticated()
            )

            // Login mediante POST /login
            .formLogin(form -> form

                .loginProcessingUrl("/login")

                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"mensaje\":\"Login correcto\"}"
                    );
                })

                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"mensaje\":\"Usuario o contraseña incorrectos\"}"
                    );
                })
            )

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"mensaje\":\"Logout correcto\"}"
                    );
                })
            );

        return http.build();
    }
}