package com.teodor.codingtask.config;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

	@Bean
	public WebClient createWebClient() {
		return WebClient.create();
	}
	
	@Bean
	public Random getRandom() {
		return new Random();
	}
}
