package cevaja.service;


import cevaja.model.Usuario;
import cevaja.model.dto.UsuarioRequestDTO;
import cevaja.model.dto.converter.UsuarioConverter;
import cevaja.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public void adicionar(UsuarioRequestDTO usuarioRequestDTO) {
        String loginUsuarioAdicionado = usuarioRequestDTO.getUsernameLogin();
        Usuario usuarioExistente = buscarPeloLogin(loginUsuarioAdicionado);

        if(usuarioExistente != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel adicionar um usuario repetido. O usuario " +
                                      loginUsuarioAdicionado + " ja existe no banco de dados.");
        } else {
            Usuario usuarioEntity = UsuarioConverter.converterParaEntidade(usuarioRequestDTO);
            usuarioRepository.save(usuarioEntity);
        }
    }


    public Usuario buscarPeloNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }
    public Usuario buscarPeloLogin(String usernameLogin) {
        return usuarioRepository.findByUsernameLogin(usernameLogin);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario removerPorLogin(String usernameLogin) {
        Usuario usuarioRemoverPorLogin = usuarioRepository.findByUsernameLogin(usernameLogin);
        if(usuarioRemoverPorLogin == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel remover um usuario inexistente. O usuario " + usernameLogin + " não existe no banco de dados.");
        } else {
            usuarioRepository.delete(usuarioRemoverPorLogin);
        }
        return usuarioRemoverPorLogin;
    }

    public Usuario alterarNomeESobrenomeo(Long id, Usuario usuario) {
        Usuario usuarioParaAlterar = usuarioRepository.findById(id).get();
        if (usuarioParaAlterar == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel alterar um usuario inexistente. O usuario " + usuario.getNome() + " não existe no banco de dados.");

        } else {
            usuarioParaAlterar.setNome(usuario.getNome());
            usuarioParaAlterar.setSobrenome(usuario.getSobrenome());
            usuarioRepository.save(usuarioParaAlterar);

            return usuarioParaAlterar;
        }

    }
}
