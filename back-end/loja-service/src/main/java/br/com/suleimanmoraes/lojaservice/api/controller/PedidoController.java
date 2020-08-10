package br.com.suleimanmoraes.lojaservice.api.controller;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.lojaservice.api.controller.abstracts.ControllerBasic;
import br.com.suleimanmoraes.lojaservice.api.model.Pedido;
import br.com.suleimanmoraes.lojaservice.api.response.Response;
import br.com.suleimanmoraes.lojaservice.api.service.PedidoService;
import lombok.Getter;

@RestController
@RequestMapping("/pedido")
public class PedidoController extends ControllerBasic<Pedido>{

	@Getter
	@Autowired
	private PedidoService service;
	
	@Override
	public ResponseEntity<Response<Pedido>> update(HttpServletRequest request, Pedido objeto) {
		Response<Pedido> response = new Response<>();
		response.setErros(new LinkedList<String>());
		response.getErros().add("Operação não permitida.");
		return ResponseEntity.badRequest().body(response);
	}
}
