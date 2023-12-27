package cevaja.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Entity
@Table(name = "tipo_cerveja")
@Data
public class TipoCerveja {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nome;

    @Column
    private BigDecimal valor;
    @Column
    private int quantidade;

}
