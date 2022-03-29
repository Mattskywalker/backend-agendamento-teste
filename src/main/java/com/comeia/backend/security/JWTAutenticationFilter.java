package com.comeia.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.comeia.backend.data.UserDetails;
import com.comeia.backend.data.UserDTO;
import com.comeia.backend.model.User;

import com.comeia.backend.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class JWTAutenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_TIME_EXPIRE = 1_800_000;
    public static final String TOKEN_SENHA = "7f109385-0b42-4cca-b1dd-daf8b2c3b249";

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public JWTAutenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken credential =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSenha(),
                            this.userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());

            return authenticationManager.authenticate(credential);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuário", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) authResult.getPrincipal();


        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_TIME_EXPIRE))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));


        User user = userService.findByLogin(userDetails.getUsername()).get();
        Gson json = new Gson();

        UserDTO userDTO = new UserDTO(userDetails, token, user);

        String jsonObject = json.toJson(userDTO);

        response.getWriter().write(jsonObject);
        response.getWriter().flush();
    }
}
