package com.NoStress.Build;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.NoStress")
public class BuildApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildApplication.class, args);
	}

}
