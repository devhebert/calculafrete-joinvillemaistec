package com.calculafrete.projeto.service;

import com.calculafrete.projeto.dto.ObterValorFreteEstadoResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ObterValorFretePortEstado {
    record InputPort(String cep) {
    }

    sealed interface OutputPort permits OutputPort.Ok,  OutputPort.BadRequest, OutputPort.NotFound, OutputPort.Error {
        record Ok(ObterValorFreteEstadoResponseDTO valor) implements OutputPort {
        }

        record BadRequest(String message) implements OutputPort {
        }

        record NotFound(String message) implements OutputPort {
        }

        record Error(String message) implements OutputPort {
        }
    }

    OutputPort execute(InputPort inputPort) throws JsonProcessingException;
}
