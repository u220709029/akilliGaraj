package com.example.akilliGaraj;

import com.example.akilliGaraj.entities.User;
import com.example.akilliGaraj.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AkilliGarajApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkilliGarajApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository) {
		return args -> {
			if (userRepository.count() == 0) {
				User testUser = new User();
				testUser.setEmail("hakan@example.com");

				testUser.setPassword("123456");

				userRepository.save(testUser);
				System.out.println("SİSTEME TEST KULLANICISI (ID: 1) OTOMATİK EKLENDİ!");
			}
		};

	}
}