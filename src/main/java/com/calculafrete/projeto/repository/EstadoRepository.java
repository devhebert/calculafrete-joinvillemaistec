package com.calculafrete.projeto.repository;

import com.calculafrete.projeto.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByNome(String nome);
    Optional<Estado> findBySigla(String sigla);
    Boolean existsByNomeAndSigla(String nome, String sigla);
}
