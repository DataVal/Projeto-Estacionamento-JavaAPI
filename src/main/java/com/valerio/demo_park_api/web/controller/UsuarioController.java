package com.valerio.demo_park_api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valerio.demo_park_api.entity.Usuario;
import com.valerio.demo_park_api.service.UsuarioService;
import com.valerio.demo_park_api.web.dto.UsuarioCreateDto;
import com.valerio.demo_park_api.web.dto.UsuarioResponseDto;
import com.valerio.demo_park_api.web.dto.mapper.UsuarioMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @PostMapping
    //Criando a funcionalidade do POST
    //ResponseEntity encapsula a resposta num objeto JSON
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCreateDto createDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    //Criando a funcionalidade do GET
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    //PutMapping x PatchMapping ? Basicamente o Put faz uma atualização total em um objeto
    //Como queremos alterar apenas a propriedade SENHA usaremos nesse caso o Patch
    //Criando a funcionalidade semelhante ao do GET pois ele irá usar o id para identificar o user a ter a senha alterada
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id, @RequestBody Usuario usuario) {
        //Uso do parâmetro "@RequestBody Usuario usuario" pois a senha irá no corpo da requisição ao invés da url
        Usuario user = usuarioService.editarSenha(id, usuario.getPassword());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    //Criando a funcionalidade da LISTAGEM DE TODOS USUÁRIOS
    //O cliente acessa a funcionalidade usando apenas o GET sem especificar o user
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(users);
    }
}
