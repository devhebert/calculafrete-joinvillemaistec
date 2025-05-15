package com.calculafrete.projeto.dto;

import java.math.BigDecimal;

public record CadastrarValorFreteEstadoRequestDTO(
         String nome,
         String sigla,
         BigDecimal valor
) {}
