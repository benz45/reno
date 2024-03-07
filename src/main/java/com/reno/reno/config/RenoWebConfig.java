package com.reno.reno.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RenoWebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		// registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowCredentials(true);

		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
	}
}
