package com.valerio.demo_park_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.valerio.demo_park_api.jwt.JwtAuthenticationEntryPoint;
import com.valerio.demo_park_api.jwt.JwtAuthorizationFilter;

@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                    //rotas autorizadas
                    .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                    .requestMatchers(HttpMethod.GET, "/estacionamento.html").permitAll() // Alterado para GET
                    .requestMatchers(HttpMethod.GET, "/estacionamento/**").permitAll() // Alterado para GET
                    .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll() // Alterado para GET
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll() // Alterado para GET
                    .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll() // Adicionado
                    .requestMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll() // Adicionado
                    .requestMatchers(HttpMethod.GET, "/webjars/**").permitAll() // Adicionado
                    .anyRequest().authenticated()
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(ex -> ex.authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
