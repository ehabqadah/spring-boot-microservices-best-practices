package com.qadah.demo;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Ehab Qadah
 */
@SpringBootApplication
public class SpringBootMicroservicesBestPracticesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroservicesBestPracticesApplication.class, args);
	}


}
