package com.calculafrete.projeto.service;

import com.calculafrete.projeto.client.ViaCepRestClient;
import com.calculafrete.projeto.dto.ObterValorFreteEstadoResponseDTO;
import com.calculafrete.projeto.model.Estado;
import com.calculafrete.projeto.model.TipoFrete;
import com.calculafrete.projeto.repository.EstadoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class ObterValorFretePortEstadoImpl implements ObterValorFretePortEstado {
    private final ViaCepRestClient cepRestClient;
    private final EstadoRepository estadoRepository;

    public ObterValorFretePortEstadoImpl(ViaCepRestClient cepRestClient, EstadoRepository estadoRepository) {
        this.cepRestClient = cepRestClient;
        this.estadoRepository = estadoRepository;
    }

    @Transactional
    public OutputPort execute(InputPort inputPort) throws JsonProcessingException {

        try {
            String cep = inputPort.cep();

            OutputPort resultadoValidacao = validarCep(cep);
            if (resultadoValidacao != null) {
                return resultadoValidacao;
            }

            String viaCepResponse = cepRestClient.buscarEnderecoPorCep(cep);
            if (viaCepResponse == null || viaCepResponse.isEmpty()) {
                return new OutputPort.NotFound("CEP não encontrado");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> viaCepData = objectMapper.readValue(viaCepResponse, Map.class);

            String sigla = viaCepData.get("uf");
            if(sigla == null || sigla.isEmpty()) {
                return new OutputPort.NotFound("UF não encontrada na resposta do ViaCep");
            }

            Optional<Estado> estadoOptional = obterEstadoPelaSigla(sigla);
            if (estadoOptional.isEmpty()) {
                return new OutputPort.BadRequest("Estado não encontrado com a sigla: " + sigla);
            }

            ObterValorFreteEstadoResponseDTO responseDTO = criarResposta(estadoOptional.get());

            return new OutputPort.Ok(responseDTO);
        } catch (Exception e) {
            return new OutputPort.Error("Erro ao processar a requisição: " + e.getMessage());
        }
    }

    private OutputPort validarCep(String cep) {
        if(cep == null || cep.isEmpty() || cep.matches("\\d(8)")) {
            return new OutputPort.BadRequest("CEP inválido");
        }

        return null;
    }

    private Optional<Estado> obterEstadoPelaSigla(String sigla) {
        return estadoRepository.findBySigla(sigla.toUpperCase());
    }

    private ObterValorFreteEstadoResponseDTO criarResposta(Estado estado) {
        BigDecimal valorFreNormal = estado.getValorFrete().add(TipoFrete.NORMAL.getCusto());
        BigDecimal valorFreExpresso = estado.getValorFrete().add(TipoFrete.EXPRESS.getCusto());

        return new ObterValorFreteEstadoResponseDTO(
                estado.getNome(),
                estado.getSigla(),
                valorFreNormal,
                TipoFrete.NORMAL.getTempoEntrega(),
                valorFreExpresso,
                TipoFrete.EXPRESS.getTempoEntrega()
        );
    }

}
