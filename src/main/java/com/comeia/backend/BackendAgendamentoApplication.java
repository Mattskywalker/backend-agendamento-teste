package com.comeia.backend;

import com.comeia.backend.model.User;
import com.comeia.backend.repository.UserRepository;
import com.comeia.backend.services.UserService;
import com.comeia.backend.services.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendAgendamentoApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendAgendamentoApplication.class, args);

	}

	@Bean
	public PasswordEncoder getPassWordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder;
	}

}
