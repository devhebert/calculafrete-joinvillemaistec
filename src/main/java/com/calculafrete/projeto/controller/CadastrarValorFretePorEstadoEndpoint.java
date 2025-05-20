package com.calculafrete.projeto.controller;

import com.calculafrete.projeto.dto.CadastrarValorFreteEstadoRequestDTO;
import com.calculafrete.projeto.service.CriarCalcularFrete;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CadastrarValorFretePorEstadoEndpoint extends BaseCalculaFreteEndpoint {

    private final CriarCalcularFrete criarCalcularFrete;

    public CadastrarValorFretePorEstadoEndpoint(CriarCalcularFrete criarCalcularFrete) {
        this.criarCalcularFrete = criarCalcularFrete;
    }

    @PostMapping("/estado")
    public ResponseEntity<?> execute(@Valid @RequestBody CadastrarValorFreteEstadoRequestDTO cadastrarValorFreteEstadoRequestDTO) {
        CriarCalcularFrete.InputPort inputPort = new CriarCalcularFrete.InputPort(cadastrarValorFreteEstadoRequestDTO);
        CriarCalcularFrete.OutputPort outputPort = criarCalcularFrete.execute(inputPort);

        return switch (outputPort) {
            case CriarCalcularFrete.OutputPort.Ok ok -> ResponseEntity.status(201).body(ok);
            case CriarCalcularFrete.OutputPort.BadRequest badRequest -> ResponseEntity.status(400).body(badRequest);
            case CriarCalcularFrete.OutputPort.Error error -> ResponseEntity.status(500).body(error);
        };
    }
}
