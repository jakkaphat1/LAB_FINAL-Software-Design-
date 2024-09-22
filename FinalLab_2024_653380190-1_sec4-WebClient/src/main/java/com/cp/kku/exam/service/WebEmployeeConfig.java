package com.cp.kku.exam.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebEmployeeConfig {
	@Bean
	public WebClient employeeWebClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8089/api")
				.build();
	}
}
