package br.com.suleimanmoraes.lojaservice.api.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.suleimanmoraes.lojaservice.api.integration.model.Usuario;
import br.com.suleimanmoraes.lojaservice.api.response.Response;

//@FeignClient(value = "loja", url = "http://API-GATEWAY-SERVICE/api/auth")
@FeignClient(name="api-gateway-service")
public interface IAuthenticationService {

	@GetMapping(value = "/api/auth/me")
	Response<Usuario> findByToken();
}