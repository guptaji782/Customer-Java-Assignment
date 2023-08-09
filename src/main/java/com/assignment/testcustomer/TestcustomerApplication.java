package com.assignment.testcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.assignment.testcustomer")
public class TestcustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcustomerApplication.class, args);
	}

}
