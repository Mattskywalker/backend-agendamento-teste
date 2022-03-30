package com.comeia.backend.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.comeia.backend.data.UserDTO;
import com.comeia.backend.model.User;
import com.comeia.backend.security.JWTAutenticationFilter;
import com.comeia.backend.services.UserService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8080/swagger-ui.html#/

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@Api(value = "Api dos usuários")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/user-signup")
    @ApiOperation(value = "Salva/ Cadastra um novo usuário normal")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        user.setAdmin(false);
        user.setSenha(encoder.encode(user.getSenha()));
        User userReturn = userService.save(user);
        return new ResponseEntity<User>(userReturn, HttpStatus.OK);
    }

    @PostMapping("/admin-signup")
    @ApiOperation(value = "Salva/cadastra um novo administrador de sistema")
    public ResponseEntity<User> saveAdmin(@RequestBody User user) {

        if(!userService.findByLogin(user.getEmail()).get().isAdmin()) {
            return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
        }

        user.setAdmin(true);
        user.setSenha(encoder.encode(user.getSenha()));
        User userReturn = userService.save(user);
        return new ResponseEntity<User>(userReturn, HttpStatus.OK);
    }

    @GetMapping("/user-auth")
    @ApiOperation(value = "pega os dados de um usuário logado",
            notes = "serve para casoso onde o usuário sai da aplicação mas retorna e ainda existe um token valido num cookie")
    public ResponseEntity<String> getUserData(@RequestHeader("Authorization") String token) {

        try{
            token = token.replace("Bearer ", "");

            String userLogin = JWT.require(Algorithm.HMAC512(JWTAutenticationFilter.TOKEN_SENHA))
                    .build()
                    .verify(token).getSubject();

            org.springframework.security.core.userdetails.User userDetails
                    = (org.springframework.security.core.userdetails.User)userDetailsService.loadUserByUsername(userLogin);
            User user = userService.findByLogin(userLogin).get();

            UserDTO userDTO = new UserDTO(userDetails,token,user);

            System.out.println(user.getEmail());

            return new ResponseEntity<String>(new Gson().toJson(userDTO), HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/users")
    @ApiOperation(value = "retorna uma lista contendo todos os usuários")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

}
