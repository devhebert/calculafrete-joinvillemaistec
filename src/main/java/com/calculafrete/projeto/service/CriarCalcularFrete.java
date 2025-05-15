package com.calculafrete.projeto.service;

import com.calculafrete.projeto.dto.CadastrarValorFreteEstadoRequestDTO;
import com.calculafrete.projeto.dto.CadastrarValorFreteEstadoResponseDTO;

public interface CriarCalcularFrete {
    record InputPort(CadastrarValorFreteEstadoRequestDTO requestDTO) {
    }

    sealed  interface OutputPort permits OutputPort.Ok, OutputPort.BadRequest, OutputPort.Error {
        record Ok(CadastrarValorFreteEstadoResponseDTO estado) implements OutputPort {
        }

        record BadRequest(String message) implements OutputPort {
        }

        record Error(String message) implements OutputPort {
        }
    }

    OutputPort execute(InputPort inputPort);
}
