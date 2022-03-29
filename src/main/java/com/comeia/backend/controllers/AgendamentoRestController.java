package com.comeia.backend.controllers;

import com.comeia.backend.model.Agendamento;
import com.comeia.backend.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AgendamentoRestController {

    @Autowired
    AgendamentoService agendamentoService;

    @PostMapping("/save-scheduling")
    ResponseEntity<Agendamento> save(@RequestBody Agendamento agendamento) {
        try {
            return new ResponseEntity<Agendamento>(agendamentoService.save(agendamento), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Agendamento>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-scheduling")
    ResponseEntity<List<Agendamento>> getAll() {
        System.out.println("RequestAll");
        try {
            List<Agendamento> agendamentoList = this.agendamentoService.getAll();
            return new ResponseEntity<List<Agendamento>>( agendamentoList, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Agendamento>>( HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find-scheduling/cpf={cpf}")
    ResponseEntity<List<Agendamento>> findAll(@PathVariable String cpf) {
        try {
            List<Agendamento> agendamentoList = this.agendamentoService.findAllByCpf(cpf);
            return new ResponseEntity<List<Agendamento>>( agendamentoList, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Agendamento>>( HttpStatus.NOT_FOUND);
        }
    }


}
