package br.com.suleimanmoraes.lojaservice.api.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.suleimanmoraes.lojaservice.api.integration.IUsuarioService;
import br.com.suleimanmoraes.lojaservice.api.model.Cliente;
import br.com.suleimanmoraes.lojaservice.api.model.dto.ClienteNovoDto;
import br.com.suleimanmoraes.lojaservice.api.persistencia.repository.ClienteRepository;
import br.com.suleimanmoraes.lojaservice.api.service.ClienteService;
import br.com.suleimanmoraes.lojaservice.api.util.FeignExceptionUtil;
import br.com.suleimanmoraes.lojaservice.api.util.ValidacaoComumUtil;
import feign.FeignException;
import lombok.Getter;

@Service
public class ClienteServiceImpl implements ClienteService{

	private static final Log logger = LogFactory.getLog(ClienteService.class);

	@Getter
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private IUsuarioService iUsuarioService;
	
	@Override
	public Log getLogger() {
		return logger;
	}
	
	@Override
	public Cliente save(HttpServletRequest request, Cliente objeto) throws Exception {
		try {
			objeto.setCodigo(objeto.getCodigo() == null ? "146bbaf1-f5c3-440b-b60e-52d0a29bc837" : objeto.getCodigo());
			objeto = repository.save(objeto);
			return objeto;
		} catch (DataIntegrityViolationException e) {
			logger.error("save " + e.getMessage());
			throw new Exception("Dados inválidos");
		} catch (Exception e) {
			logger.error("save " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class, FeignException.class})
	public Cliente save(HttpServletRequest request, ClienteNovoDto objeto) throws Exception {
		try {
			Cliente cliente = save(request, new Cliente(objeto));
			objeto.setId(cliente.getId());
			iUsuarioService.newObject(objeto);
			return cliente;
		} catch (FeignException f) {
			FeignExceptionUtil.tratar(f);
			throw f;
		} catch (Exception e) {
			logger.warn("save " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<String> validar(ClienteNovoDto objeto){
		List<String> erros = new LinkedList<>();
		erros = ValidacaoComumUtil.validarString(objeto.getLogin(), "Login", 'o', erros, 255);
		erros = ValidacaoComumUtil.validarString(objeto.getSenha(), "Senha", 'a', erros, 20);
		erros = ValidacaoComumUtil.validarString(objeto.getNome(), "Nome", 'o', erros, 255);
		return erros;
	}

	@Override
	public List<String> validar(Cliente objeto) {
		List<String> erros = new LinkedList<>();
		erros = ValidacaoComumUtil.validarString(objeto.getNome(), "Nome", 'o', erros, 255);
		return erros;
	}
}
