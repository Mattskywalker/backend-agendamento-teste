package com.comeia.backend.services.impl;

import com.comeia.backend.model.Agendamento;
import com.comeia.backend.model.User;
import com.comeia.backend.repository.AgendamentoRepository;
import com.comeia.backend.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Override
    public Agendamento findById(Long id) {
        return this.agendamentoRepository.findById(id).get();
    }

    @Override
    public List<Agendamento> getAll() {
        return agendamentoRepository.findAll();
    }

    @Override
    public List<Agendamento> findAllByUser(User user) {
        return this.agendamentoRepository.findAllByUser(user).get();
    }

    @Override
    public Agendamento save(Agendamento agendamento) {
        return this.agendamentoRepository.save(agendamento);
    }

    @Override
    public Agendamento delete(Long id) {
        Agendamento agendamento = agendamentoRepository.getById(id);
        agendamentoRepository.deleteById(id);
        return agendamento;
    }

    @Override
    public Agendamento update(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }
}
