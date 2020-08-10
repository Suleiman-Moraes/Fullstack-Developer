package br.com.suleimanmoraes.lojaservice.api.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.suleimanmoraes.lojaservice.api.integration.model.Usuario;
import br.com.suleimanmoraes.lojaservice.api.model.dto.ClienteNovoDto;
import br.com.suleimanmoraes.lojaservice.api.response.Response;

//@FeignClient(value = "loja", url = "http://API-GATEWAY-SERVICE/api/auth")
@FeignClient(name="api-gateway-service/api/auth")
public interface IUsuarioService {

	@PostMapping(value = "/usuario/cliente")
	Response<Usuario> newObject(@RequestBody ClienteNovoDto objeto);
}