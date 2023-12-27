package cevaja.model.dto;


import lombok.Data;

import java.math.BigDecimal;
@Data
public class TipoCervejaDTO {

    private String nome;
    private BigDecimal valor;
    private int quantidade;

    public TipoCervejaDTO(String nome, BigDecimal valor) {
        this.nome = nome;
        this.valor = valor;
    }
}
