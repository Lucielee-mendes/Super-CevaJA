package cevaja.model.dto.converter;

import cevaja.model.Usuario;
import cevaja.model.dto.UsuarioRequestDTO;

public class UsuarioConverter {

    public  static Usuario converterParaEntidade(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuarioEntity = new Usuario();
        usuarioEntity.setNome(usuarioRequestDTO.getNome());
        usuarioEntity.setSobrenome(usuarioRequestDTO.getSobrenome());
        usuarioEntity.setDataNascimento(usuarioRequestDTO.getDataNascimento());
        usuarioEntity.setCpf(usuarioRequestDTO.getCpf());
        usuarioEntity.setUsernameLogin(usuarioRequestDTO.getUsernameLogin());

        return usuarioEntity;
    }
}
