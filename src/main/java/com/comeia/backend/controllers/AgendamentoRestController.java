package com.comeia.backend.controllers;

import com.comeia.backend.model.Agendamento;
import com.comeia.backend.model.User;
import com.comeia.backend.services.AgendamentoService;
import com.comeia.backend.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//http://localhost:8080/swagger-ui.html#/

@RestController
@RequestMapping("/api")
@Api(value = "Api de agendamento, é necessario estar cadastrado para acessar!")
@CrossOrigin("*")
public class AgendamentoRestController {

    @Autowired
    AgendamentoService agendamentoService;

    @Autowired
    UserService userService;

    @PostMapping("/save-scheduling")
    @ApiOperation(value = "cadastra um agendamento")
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
    @ApiOperation(value = "retorna uma lista contendo todos os agendamentos")
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
    @ApiOperation(value = "retorna uma lista de todos os agendamentos de um CPF")
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
    @ApiOperation(value = "atualiza um agendamento existente")
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
    @ApiOperation(value = "deleta um agendamento")
    ResponseEntity<Agendamento> delete(@RequestBody Agendamento agendamento) {
        try {
            agendamento = this.agendamentoService.delete(agendamento.getId());
            return new ResponseEntity<Agendamento>( HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<Agendamento>(agendamento, HttpStatus.NOT_FOUND);
        }
    }


}
