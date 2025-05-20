package com.calculafrete.projeto.dto;

import java.math.BigDecimal;

public record ObterValorFreteEstadoResponseDTO(
        String nome,
        String sigla,
        BigDecimal valorFreteNormal,
        String tempoEntregaNormal,
        BigDecimal valorFreteExpress,
        String tempoEntregaExpress
) {
}
