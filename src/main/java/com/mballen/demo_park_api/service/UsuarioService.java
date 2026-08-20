package com.mballen.demo_park_api.service;

import com.mballen.demo_park_api.entity.Usuario;
import com.mballen.demo_park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    //Anotacao para o Spring gerenciar as transacoes do metodo no banco
    @Transactional
    public Usuario salvar(Usuario usuario) {
       return usuarioRepository.save(usuario);
    }

    //readOnly para indicar que e apenas um metodo de consulta
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        //findById retorna Optional
        return usuarioRepository.findById(id).orElseThrow( //orElseThrow para retornar usuario ou excecao
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String password) {
        Usuario user = buscarPorId(id);

        // Como a entidade está sendo gerenciada pelo JPA dentro da transação,
        // o Hibernate detecta a alteração e gera o UPDATE automaticamente.
        user.setPassword(password);

        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
