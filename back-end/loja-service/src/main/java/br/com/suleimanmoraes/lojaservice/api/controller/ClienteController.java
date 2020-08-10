package br.com.suleimanmoraes.lojaservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.lojaservice.api.controller.abstracts.ControllerBasic;
import br.com.suleimanmoraes.lojaservice.api.model.Cliente;
import br.com.suleimanmoraes.lojaservice.api.service.ClienteService;
import lombok.Getter;

@RestController
@RequestMapping("/cliente")
public class ClienteController extends ControllerBasic<Cliente>{

	@Getter
	@Autowired
	private ClienteService service;
	

//	@PostMapping
//	public ResponseEntity<Response<Cliente>> newObject(HttpServletRequest request, @RequestBody ClienteNovoDto objeto) {
//		Response<Cliente> response = new Response<>();
//		try {
//			List<String> erros = service.validar(objeto);
//			if (erros.size() > 0) {
//				response.setErros(erros);
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//			}
//			Cliente objetoNovo = service.save(request, objeto);
//			response.setData(objetoNovo);
//			return ResponseEntity.status(HttpStatus.CREATED).body(response);
//		} catch (Exception e) {
//			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
//		}
//	}
}
