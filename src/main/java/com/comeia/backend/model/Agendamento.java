package com.comeia.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dataAgendamento;
    private String cpf;
    private String servico;

    public Agendamento() {
    }

    public Agendamento(Long id, String dataAgendamento, String cpf) {
        this.id = id;
        this.dataAgendamento = dataAgendamento;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }
}
