package br.com.suleimanmoraes.lojaservice.api.service;

import org.springframework.stereotype.Component;

import br.com.suleimanmoraes.lojaservice.api.interfaces.CrudPadraoService;
import br.com.suleimanmoraes.lojaservice.api.model.Pedido;

@Component
public interface PedidoService extends CrudPadraoService<Pedido>{

}
