package com.comeia.backend.services;

import com.comeia.backend.model.Agendamento;
import com.comeia.backend.model.User;

import java.util.List;

public interface AgendamentoService {

    public Agendamento save(Agendamento agendamento);

    public Agendamento findById(Long id);

    public List<Agendamento> getAll();

    public  List<Agendamento> findAllByUser(User user);

    public Agendamento delete(Long id);

    public Agendamento update(Agendamento agendamento);

}
