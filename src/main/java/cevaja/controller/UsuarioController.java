package cevaja.controller;

import cevaja.model.Usuario;
import cevaja.model.dto.UsuarioRequestDTO;
import cevaja.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> adicionarUsuario (@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        usuarioService.adicionar(usuarioRequestDTO);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios(){
        return ResponseEntity.ok(this.usuarioService.buscarTodos());
    }

    @DeleteMapping("/{usernameLogin}")
    public ResponseEntity<Usuario> removerUsuarioPeloLogin (@PathVariable("usernameLogin") String usernameLogin){
        Usuario usuarioRemovido = usuarioService.removerPorLogin(usernameLogin);
        return new ResponseEntity<>(usuarioRemovido, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> alterarNomeESobrenome(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioAlterado = usuarioService.alterarNomeESobrenome(id, usuario);
        return new ResponseEntity<>(usuarioAlterado, HttpStatus.NO_CONTENT);
    }

}
