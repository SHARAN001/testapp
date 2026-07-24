package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ItemRepository repository) {
		return args -> {
			Random random = new Random();
			String[] statuses = {"ACTIVE", "INACTIVE", "PENDING", "COMPLETED"};
			for (int i = 1; i <= 100; i++) {
				String name = "Item-" + (1000 + random.nextInt(9000));
				String status = statuses[random.nextInt(statuses.length)];
				repository.save(new Item(name, status));
			}
		};
	}

}

