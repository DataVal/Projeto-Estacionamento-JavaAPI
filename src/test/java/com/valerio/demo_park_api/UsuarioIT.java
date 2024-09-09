package com.valerio.demo_park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.valerio.demo_park_api.web.dto.UsuarioCreateDto;
import com.valerio.demo_park_api.web.dto.UsuarioResponseDto;
import com.valerio.demo_park_api.web.exception.ErrorMessage;

// IT significa teste de integração, mesmo o objetivo sendo fazer um teste ponta-a-ponta ele é considerado IT
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SuppressWarnings("null")
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){
        UsuarioResponseDto responseBody = testClient
            .post()
            .uri("api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDto("tobias@gmail.com","123333"))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(UsuarioResponseDto.class)
            .returnResult().getResponseBody();

        
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tobias@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");



    }

    @Test
    public void createUsuario_ComUsernameInvalido_RetornarErrorMessage422(){
        ErrorMessage responseBody = testClient
            .post()
            .uri("api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDto("","123333"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
            .post()
            .uri("api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDto("tobias@","123333"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
        .post()
        .uri("api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("tobias@gmail","123333"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();
    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComPasswordInvalido_RetornarErrorMessage422(){
        ErrorMessage responseBody = testClient
            .post()
            .uri("api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDto("tobias@gmail.com",""))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
            .post()
            .uri("api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDto("tobias@gmail.com","12333"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
        .post()
        .uri("api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("tobias@gmail.com","1233233"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();
    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void buscarUsuario_ComIdExistente_RetornarUsuarioComStatus200(){
        UsuarioResponseDto responseBody = testClient
            .get()
            .uri("api/v1/usuarios/100")
            .exchange()
            .expectStatus().isOk()
            .expectBody(UsuarioResponseDto.class)
            .returnResult().getResponseBody();

        
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("jpvc@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");



    }


}
