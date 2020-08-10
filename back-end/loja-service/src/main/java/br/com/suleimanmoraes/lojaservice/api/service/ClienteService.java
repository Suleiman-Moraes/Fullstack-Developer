package br.com.suleimanmoraes.lojaservice.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import br.com.suleimanmoraes.lojaservice.api.interfaces.CrudPadraoService;
import br.com.suleimanmoraes.lojaservice.api.model.Cliente;
import br.com.suleimanmoraes.lojaservice.api.model.dto.ClienteNovoDto;

@Component
public interface ClienteService extends CrudPadraoService<Cliente>{

	Cliente save(HttpServletRequest httpServletRequest, ClienteNovoDto clienteNovoDto) throws Exception;

	List<String> validar(ClienteNovoDto clienteNovoDto);
}
