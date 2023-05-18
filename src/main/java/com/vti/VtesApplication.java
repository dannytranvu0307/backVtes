package com.vti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.vti.*" ,"com.vti"})
@EnableFeignClients
public class VtesApplication {

	public static void main(String[] args) {
		SpringApplication.run(VtesApplication.class, args);
	}

}
