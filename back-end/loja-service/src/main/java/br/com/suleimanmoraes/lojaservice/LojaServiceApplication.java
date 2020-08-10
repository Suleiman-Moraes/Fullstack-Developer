package br.com.suleimanmoraes.lojaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LojaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaServiceApplication.class, args);
	}
}
