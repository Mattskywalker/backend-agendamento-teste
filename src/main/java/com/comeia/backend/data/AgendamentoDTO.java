package com.comeia.backend.data;

import com.comeia.backend.model.Agendamento;
import com.comeia.backend.model.User;

public class AgendamentoDTO {

    private Agendamento agendamento;
    private User user;

    public AgendamentoDTO(Agendamento agendamento, User user) {
        this.agendamento = agendamento;
        user.setSenha(null);
        this.user = user;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
