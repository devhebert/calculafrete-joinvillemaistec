package com.calculafrete.projeto.controller;

import com.calculafrete.projeto.service.ObterValorFretePortEstado;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObterValorFretePortEstadoEndpoint extends  BaseCalculaFreteEndpoint {

    private final ObterValorFretePortEstado obterValorFretePortEstado;

    public ObterValorFretePortEstadoEndpoint(ObterValorFretePortEstado obterValorFretePortEstado) {
        this.obterValorFretePortEstado = obterValorFretePortEstado;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<?> execute(@PathVariable String cep) throws JsonProcessingException {
        ObterValorFretePortEstado.InputPort inputPort = new ObterValorFretePortEstado.InputPort(cep);
        ObterValorFretePortEstado.OutputPort outputPort = obterValorFretePortEstado.execute(inputPort);

        return switch (outputPort) {
            case ObterValorFretePortEstado.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case ObterValorFretePortEstado.OutputPort.BadRequest badRequest -> ResponseEntity.status(400).body(badRequest);
            case ObterValorFretePortEstado.OutputPort.NotFound notFound -> ResponseEntity.status(404).body(notFound);
            case ObterValorFretePortEstado.OutputPort.Error error -> ResponseEntity.status(500).body(error);
        };
    }

}
