package com.example.todomate_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TodomateCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodomateCloneApplication.class, args);
	}

}
