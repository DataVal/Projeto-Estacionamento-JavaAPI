package com.valerio.demo_park_api.service;
import java.util.List;

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

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
            () -> new RuntimeException("usuário não encontrado.")
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new RuntimeException("Nova senha não conferiu com a confirmação!");
        }
        Usuario user = buscarPorId(id);
        if (!user.getPassword().equals(senhaAtual)) {
            throw new RuntimeException("Senha atual não confere.");
        }
        user.setPassword(novaSenha);
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
