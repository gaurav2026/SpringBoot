package com.dlh.zambas.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class SwaggerParserApplication extends SpringBootServletInitializer implements WebApplicationInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SwaggerParserApplication.class, args);
	}
}
