package br.com.suleimanmoraes.lojaservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.lojaservice.api.controller.abstracts.ControllerBasic;
import br.com.suleimanmoraes.lojaservice.api.model.Produto;
import br.com.suleimanmoraes.lojaservice.api.service.ProdutoService;
import lombok.Getter;

@RestController
@RequestMapping("/produto")
public class ProdutoController extends ControllerBasic<Produto>{

	@Getter
	@Autowired
	private ProdutoService service;
	
}
