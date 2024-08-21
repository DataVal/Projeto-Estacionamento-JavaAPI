package com.valerio.demo_park_api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Criando a tabela Usuario
@Getter @Setter @NoArgsConstructor @ToString @Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{
    //Aqui eu estou gerando o campo ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    //Coluna username deve ser única pra cada um e não pode ser nula com tamanho max de 100 carach
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
    //Coluna password tem que ter length maior por conta da criptografia
    @Column(name = "password", nullable = false, unique = false, length = 200 )
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;


    //Sistema de auditoria aplicado
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    @Column(name = "criado_por")
    private String criadoPor;
    @Column(name = "modificado_por")
    private String modificadoPor;

// Iremos gerar agora os getters e setters com o lombok

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + "]";
    }




}
