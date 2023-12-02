package cevaja.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioRequestDTO {

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String cpf;
    private String usernameLogin;
}
