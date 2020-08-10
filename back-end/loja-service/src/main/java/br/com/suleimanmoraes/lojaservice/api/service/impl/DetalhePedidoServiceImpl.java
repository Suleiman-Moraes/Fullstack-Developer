package br.com.suleimanmoraes.lojaservice.api.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.suleimanmoraes.lojaservice.api.model.DetalhePedido;
import br.com.suleimanmoraes.lojaservice.api.persistencia.repository.DetalhePedidoRepository;
import br.com.suleimanmoraes.lojaservice.api.service.DetalhePedidoService;
import br.com.suleimanmoraes.lojaservice.api.service.ProdutoService;
import br.com.suleimanmoraes.lojaservice.api.util.ValidacaoComumUtil;
import lombok.Getter;

@Service
public class DetalhePedidoServiceImpl implements DetalhePedidoService {

	private static final Log logger = LogFactory.getLog(DetalhePedidoService.class);

	@Getter
	@Autowired
	private DetalhePedidoRepository repository;
	
	@Autowired
	private ProdutoService produtoService;

	@Override
	public Log getLogger() {
		return logger;
	}

	@Override
	public List<String> validar(DetalhePedido objeto) {
		List<String> erros = new LinkedList<>();
		if (objeto != null) {
			erros = ValidacaoComumUtil.validarNotNullAndMaiorZero(objeto.getQuantidade(), "Quantidade", 'a', erros);
			erros = ValidacaoComumUtil.validarObjectAndId(objeto.getProduto(), "Produto", 'o', erros);
		}
		return erros;
	}

	@Override
	public List<String> validar(List<DetalhePedido> detalhePedidos) {
		List<String> erros = new LinkedList<>();
		detalhePedidos.forEach(objeto -> erros.addAll(validar(objeto)));
		return erros;
	}

	@Override
	public void tratar(DetalhePedido detalhePedido) {
		detalhePedido.setProduto(produtoService.findById(null, detalhePedido.getProduto().getId()));
		detalhePedido.setValorEpoca(detalhePedido.getProduto().getPrecoUnitario());
		detalhePedido.setTotal(detalhePedido.getValorEpoca() * detalhePedido.getQuantidade());
	}
}
