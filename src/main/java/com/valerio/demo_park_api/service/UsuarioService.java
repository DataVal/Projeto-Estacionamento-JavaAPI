package com.valerio.demo_park_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valerio.demo_park_api.entity.Usuario;
import com.valerio.demo_park_api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    //essa notação significa que o Spring vai cuidar de abrir, gerenciar e fechar a transação
    @Transactional
    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}
