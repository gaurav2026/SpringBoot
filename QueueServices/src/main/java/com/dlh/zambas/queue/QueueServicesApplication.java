package com.dlh.zambas.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

/**
 * 
 * @author singhg
 *
 */
@SpringBootApplication
public class QueueServicesApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(QueueServicesApplication.class, args);
	}

}
