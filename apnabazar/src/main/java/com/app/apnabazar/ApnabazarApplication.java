package com.app.apnabazar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApnabazarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApnabazarApplication.class, args);
	}

}
