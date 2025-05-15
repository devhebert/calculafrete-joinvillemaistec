package com.calculafrete.projeto.model;

import java.math.BigDecimal;

public enum TipoFrete {
    NORMAL(new BigDecimal("0.00"), "5-15 dias"),
    EXPRESS(new BigDecimal("20.00"), "1-5 dias");

    private final BigDecimal custo;
    private final String tempoEntrega;

    TipoFrete(BigDecimal custo, String tempoEntrega) {
        this.custo = custo;
        this.tempoEntrega = tempoEntrega;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public String getTempoEntrega() {
        return tempoEntrega;
    }
}
