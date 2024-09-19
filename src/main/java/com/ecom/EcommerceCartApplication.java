package com.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ecom.model")
public class EcommerceCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceCartApplication.class, args);
	}

}
