package com.comeia.backend.services;

import com.comeia.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

        public List<User> findAll();

        public User findById(Long id);

        public Optional<User> findByLogin(String login);

        public User findUser(User user);

        public User save(User user);

        public void deleteAll();

        public User delete(User user);
}
