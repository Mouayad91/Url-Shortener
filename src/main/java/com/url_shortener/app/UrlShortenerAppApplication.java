package com.url_shortener.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UrlShortenerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerAppApplication.class, args);
	

		System.out.println("**********************************");
		System.out.println("Welcome to URL Shortener Application");
		System.out.println("**********************************");
	}

}
