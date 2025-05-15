package com.calculafrete.projeto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sigla;
    private BigDecimal valorFrete;

    public Estado(String nome, String sigla, BigDecimal valorFrete) {
        this.nome = nome;
        this.sigla = sigla;
        this.valorFrete = valorFrete;
    }
}
