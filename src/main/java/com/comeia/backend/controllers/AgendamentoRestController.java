package com.comeia.backend.controllers;

import com.comeia.backend.model.Agendamento;
import com.comeia.backend.model.User;
import com.comeia.backend.services.AgendamentoService;
import com.comeia.backend.services.UserService;
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

    @Autowired
    UserService userService;

    @PostMapping("/save-scheduling")
    ResponseEntity<Agendamento> save(@RequestBody Agendamento agendamento) {
        try {
            agendamento = agendamentoService.save(agendamento);
            return new ResponseEntity<Agendamento>(agendamento, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Agendamento>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-scheduling")
    ResponseEntity<List<Agendamento>> getAll() {
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
            User user = userService.findByCpf(cpf);
            List<Agendamento> agendamentoList = this.agendamentoService.findAllByUser(user);

            return new ResponseEntity<List<Agendamento>>( agendamentoList, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Agendamento>>( HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update-scheduling")
    ResponseEntity<Agendamento> update(@RequestBody Agendamento agendamento) {
        try {

            Agendamento databaseSnapshot = agendamentoService.findById(agendamento.getId());
            agendamento.setUser(databaseSnapshot.getUser());
            agendamentoService.update(agendamento);
            return new ResponseEntity<Agendamento>(HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<Agendamento>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete-scheduling")
    ResponseEntity<Agendamento> delete(@RequestBody Agendamento agendamento) {
        try {
            agendamento = this.agendamentoService.delete(agendamento.getId());
            return new ResponseEntity<Agendamento>( HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<Agendamento>(agendamento, HttpStatus.NOT_FOUND);
        }
    }


}
