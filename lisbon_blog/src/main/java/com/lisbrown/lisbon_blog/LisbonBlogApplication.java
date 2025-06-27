package com.lisbrown.lisbon_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LisbonBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LisbonBlogApplication.class, args);
	}

}
