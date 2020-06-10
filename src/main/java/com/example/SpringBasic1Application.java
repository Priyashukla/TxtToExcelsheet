package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringBasic1Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringBasic1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application Started");
		
	}

}
