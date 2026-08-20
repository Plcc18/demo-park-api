package com.mballen.demo_park_api.web.controller;

import com.mballen.demo_park_api.entity.Usuario;
import com.mballen.demo_park_api.service.UsuarioService;
import com.mballen.demo_park_api.web.dto.UsuarioCreateDTO;
import com.mballen.demo_park_api.web.dto.UsuarioResponseDTO;
import com.mballen.demo_park_api.web.dto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    public final UsuarioService usuarioService;

    //Mapeamento do spring
    @PostMapping
    //Usuario como argumento pra ter os mesmos campos do cliente
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioCreateDTO createDTO) {
        //executa o metodo de salvar um usuario no banco
       Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));

       //retorna um uma resposta HTTP e coloca o usuario no corpo de resposta
       return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        //passa o usuario diretamente no corpo de resposta
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    //RequestBody porque a senha fica no corpo da resposta
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario user = usuarioService.editarSenha(id, usuario.getPassword());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> user = usuarioService.buscarTodos();
        return ResponseEntity.ok(user);
    }

}
