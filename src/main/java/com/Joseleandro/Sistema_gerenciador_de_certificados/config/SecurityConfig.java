package com.Joseleandro.Sistema_gerenciador_de_certificados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa proteção CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas as requisições sem login
                )
                .httpBasic(Customizer.withDefaults()) // Usa configuração padrão (pode deixar ou remover)
                .formLogin(form -> form.disable()); // Desativa tela de login

        return http.build();
    }
}
