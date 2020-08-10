package br.com.suleimanmoraes.lojaservice.api.service;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.suleimanmoraes.lojaservice.api.interfaces.CrudPadraoService;
import br.com.suleimanmoraes.lojaservice.api.model.DetalhePedido;

@Component
public interface DetalhePedidoService extends CrudPadraoService<DetalhePedido>{

	List<String> validar(List<DetalhePedido> detalhePedidos);

	void tratar(DetalhePedido detalhePedido);
}
