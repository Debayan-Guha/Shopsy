package com.ecom.shopsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShopsyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopsyApplication.class, args);
	}

}
