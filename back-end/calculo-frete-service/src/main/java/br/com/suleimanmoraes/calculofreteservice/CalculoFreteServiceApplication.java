package br.com.suleimanmoraes.calculofreteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CalculoFreteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculoFreteServiceApplication.class, args);
	}

}
