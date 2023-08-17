package com.reno.reno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class RenoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenoApplication.class, args);
	}

}
