package com.comeia.backend.repository;

import com.comeia.backend.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    public Optional<List<Agendamento>> findByCpf(String cpf);

}
