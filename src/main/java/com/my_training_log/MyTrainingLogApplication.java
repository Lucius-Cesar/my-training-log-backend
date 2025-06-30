package com.my_training_log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MyTrainingLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTrainingLogApplication.class, args);
	}

}
