package com.calculafrete.projeto.service;

import com.calculafrete.projeto.dto.CadastrarValorFreteEstadoRequestDTO;
import com.calculafrete.projeto.dto.CadastrarValorFreteEstadoResponseDTO;
import com.calculafrete.projeto.model.Estado;
import com.calculafrete.projeto.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CriarCalcularFreteImpl implements CriarCalcularFrete {

    private final EstadoRepository estadoRepository;

    public CriarCalcularFreteImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public OutputPort execute(InputPort inputPort) {

        CadastrarValorFreteEstadoRequestDTO requestDTO = inputPort.requestDTO();

        OutputPort  resultadoValidacao = validarDados(requestDTO);
        if (resultadoValidacao != null) {
            return resultadoValidacao;
        }

        Estado estado = criarEstado(requestDTO);

        estadoRepository.save(estado);

        CadastrarValorFreteEstadoResponseDTO responseDTO = criarResposta(estado);

        return new OutputPort.Ok(responseDTO);

    }

    private OutputPort validarDados(CadastrarValorFreteEstadoRequestDTO requestDTO) {
        if (requestDTO.nome() == null || requestDTO.valor() == null) {
            return new OutputPort.BadRequest("Nome não pode ser nulo ou vazio");
        }

        if (requestDTO.sigla() == null || requestDTO.sigla().isEmpty() || requestDTO.sigla().length() > 2) {
            return new OutputPort.BadRequest("Sigla não pode ser nula ou vazia");
        }

        if (requestDTO.valor().compareTo(BigDecimal.ZERO) <= 0) {
            return new OutputPort.BadRequest("Valor não pode ser nulo ou menor que zero");
        }

        if (estadoRepository.existsByNomeAndSigla(requestDTO.nome(), requestDTO.sigla())) {
            return new OutputPort.BadRequest("Estado já cadastrado com o mesmo nome e sigla");
        }

        return null;
    }

    private Estado criarEstado(CadastrarValorFreteEstadoRequestDTO requestDTO) {
        return new Estado(requestDTO.nome(), requestDTO.sigla(), requestDTO.valor());
    }

    private CadastrarValorFreteEstadoResponseDTO criarResposta(Estado estado) {
        return new CadastrarValorFreteEstadoResponseDTO(estado.getId(), estado.getNome(), estado.getSigla(), estado.getValorFrete());
    }

}
