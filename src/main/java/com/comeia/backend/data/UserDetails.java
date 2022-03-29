package com.comeia.backend.data;

import com.comeia.backend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final Optional<User> usuario;


    public UserDetails(Optional<User> usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
return null;
    }

    @Override
    public String getPassword() {
        return usuario.orElse(new User()).getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new User()).getEmail();
    }

    public boolean isAdmin() {return usuario.orElse(new User()).isAdmin();}

    public Long getId() {
        return usuario.orElse(new User()).getId();
    }

    public String getName() {
        return usuario.orElse(new User()).getNome();
    }

    public String getTelefone() {
        return usuario.orElse(new User()).getTelefone();
    }

    public String getCpf() {
        return usuario.orElse(new User()).getCpf();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
