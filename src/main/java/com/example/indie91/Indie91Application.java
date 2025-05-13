package com.example.indie91;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Indie91Application {

	public static void main(String[] args) {
		SpringApplication.run(Indie91Application.class, args);
		System.out.println("Server started!!!");
	}
}
