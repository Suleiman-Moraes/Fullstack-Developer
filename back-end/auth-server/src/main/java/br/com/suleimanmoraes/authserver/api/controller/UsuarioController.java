package br.com.suleimanmoraes.authserver.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.authserver.api.model.Usuario;
import br.com.suleimanmoraes.authserver.api.model.dto.ClienteNovoDto;
import br.com.suleimanmoraes.authserver.api.response.Response;
import br.com.suleimanmoraes.authserver.api.service.UsuarioService;
import br.com.suleimanmoraes.authserver.api.util.RestControllerUtil;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@PostMapping(value = "cliente")
	public ResponseEntity<Response<Usuario>> newObject(HttpServletRequest request, @RequestBody ClienteNovoDto objeto) {
		Response<Usuario> response = new Response<>();
		try {
			Usuario objetoNovo = service.save(request, objeto);
			response.setData(objetoNovo);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}
}
