package com.comeia.backend.services.impl;

import com.comeia.backend.model.User;
import com.comeia.backend.repository.UserRepository;
import com.comeia.backend.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userService.findByLogin(username);
        User userData = user.get();

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário [" + user + "] não encontrado");
        }

        String[] roles = userData.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(userData.getEmail())
                .password(userData.getSenha())
                .roles(roles)
                .build();
    }
}
