package com.comeia.backend.services.impl;

import com.comeia.backend.model.User;
import com.comeia.backend.repository.UserRepository;
import com.comeia.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return this.userRepository.findByEmail(login);
    }

    @Override
    public User findUser(User user) {
        return this.findById(user.getId());
    }

    @Override
    public User save(User user) {
        User userReturn = userRepository.save(user);
        System.out.println(userReturn);
        return userReturn;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User delete(User user) {
        return delete(user);
    }
}
