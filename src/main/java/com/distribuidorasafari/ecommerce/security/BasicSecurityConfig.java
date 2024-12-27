package com.distribuidorasafari.ecommerce.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    /**
     * Define o serviço de busca de detalhes do usuário.
     * Implementa lógica personalizada para carregar usuários no sistema.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Define o codificador de senhas.
     * Utiliza o BCrypt para criptografar senhas armazenadas.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o provedor de autenticação com o serviço de detalhes do usuário
     * e o codificador de senhas.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Configura o gerenciador de autenticação.
     * Garante que as configurações do Spring Security sejam aplicadas corretamente.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura a cadeia de filtros de segurança.
     * Define regras para autenticação, permissões de endpoints e o filtro JWT.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // Configuração de gerenciamento de sessão
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API Stateless
            .csrf(csrf -> csrf.disable())
            .cors(withDefaults());

        http
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
            	.requestMatchers("/users/register").permitAll()
                .requestMatchers("/users/login").permitAll()
                .requestMatchers("/products/all", "/products/names/{name}").permitAll()
                .requestMatchers("/categories/all", "/categories/name/{name}").permitAll()
                .requestMatchers("/error/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                // Demais endpoints requerem autenticação
                .anyRequest().authenticated())
            // Configuração do provedor de autenticação e filtro JWT
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(withDefaults()); // Adiciona autenticação básica

        return http.build();
    }
}
