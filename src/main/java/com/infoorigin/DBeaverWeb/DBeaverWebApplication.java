package com.infoorigin.DBeaverWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.infoorigin.DBeaverWeb.repositories.jpa")

public class DBeaverWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DBeaverWebApplication.class, args);
	}

}
