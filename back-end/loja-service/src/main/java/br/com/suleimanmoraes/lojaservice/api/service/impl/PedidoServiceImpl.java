package br.com.suleimanmoraes.lojaservice.api.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.suleimanmoraes.lojaservice.api.integration.IAuthenticationService;
import br.com.suleimanmoraes.lojaservice.api.integration.model.Usuario;
import br.com.suleimanmoraes.lojaservice.api.model.DetalhePedido;
import br.com.suleimanmoraes.lojaservice.api.model.Pedido;
import br.com.suleimanmoraes.lojaservice.api.persistencia.repository.PedidoRepository;
import br.com.suleimanmoraes.lojaservice.api.service.DetalhePedidoService;
import br.com.suleimanmoraes.lojaservice.api.service.PedidoService;
import br.com.suleimanmoraes.lojaservice.api.util.FeignExceptionUtil;
import br.com.suleimanmoraes.lojaservice.api.util.ValidacaoComumUtil;
import feign.FeignException;
import lombok.Getter;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Log logger = LogFactory.getLog(PedidoService.class);

	@Getter
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private DetalhePedidoService detalhePedidoService;
	
	@Autowired
	private IAuthenticationService iAuthenticationService;
	
	private void realizarCalculos(Pedido objeto){
		Double valorTotal = 0.0;
		Integer qtd = 0;
		for(DetalhePedido detalhePedido : objeto.getDetalhePedidos()) {
			if(detalhePedido != null) {
				detalhePedidoService.tratar(detalhePedido);
				valorTotal += detalhePedido.getTotal();
				qtd += detalhePedido.getQuantidade();
			}
		}
		objeto.setItensQuantidadeTotal(qtd);
		objeto.setItensValorTotal(valorTotal);
		objeto.setTotal(objeto.getFrete() + valorTotal);
	}

	@Override
	public Log getLogger() {
		return logger;
	}

	@Override
	public List<String> validar(Pedido objeto) {
		List<String> erros = new LinkedList<>();
		erros = ValidacaoComumUtil.validarNotNullAndMaiorZero(objeto.getFrete(), "Frete", 'o', erros);
		erros = ValidacaoComumUtil.validarObjectAndId(objeto.getCliente(), "Cliente", 'o', erros);
		if(CollectionUtils.isNotEmpty(objeto.getDetalhePedidos())) {
			erros.addAll(detalhePedidoService.validar(objeto.getDetalhePedidos()));
		}
		else {
			erros.add("Ao menos um produto deve ser informado.");
		}
		return erros;
	}
	
	@Override
	public Pedido save(HttpServletRequest request, Pedido objeto) throws Exception {
		try {
			if(objeto.getId() != null && objeto.getId() > 0) {
				throw new Exception("Operação não permitida.");
			}
			realizarCalculos(objeto);
			final Usuario usuario = iAuthenticationService.findByToken().getData();
			objeto.setUsuarioId(usuario.getId());
			objeto = repository.save(objeto);
			return objeto;
		} catch (FeignException f) {
			FeignExceptionUtil.tratar(f);
			throw f;
		} catch (DataIntegrityViolationException e) {
			logger.error("save " + e.getMessage());
			throw new Exception("Dados inválidos");
		} catch (Exception e) {
			logger.error("save " + e.getMessage());
			throw e;
		}
	}
}
