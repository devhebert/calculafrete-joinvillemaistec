package com.calculafrete.projeto.dto;

import java.math.BigDecimal;

public record CadastrarValorFreteEstadoResponseDTO(
        Long id,
        String nome,
        String sigla,
        BigDecimal valor
) {
}
