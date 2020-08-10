package br.com.suleimanmoraes.lojaservice.api.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.suleimanmoraes.lojaservice.api.model.Produto;
import br.com.suleimanmoraes.lojaservice.api.persistencia.repository.ProdutoRepository;
import br.com.suleimanmoraes.lojaservice.api.service.ProdutoService;
import br.com.suleimanmoraes.lojaservice.api.util.ValidacaoComumUtil;
import lombok.Getter;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private static final Log logger = LogFactory.getLog(ProdutoService.class);

	@Getter
	@Autowired
	private ProdutoRepository repository;

	@Override
	public Log getLogger() {
		return logger;
	}

	@Override
	public List<String> validar(Produto objeto) {
		List<String> erros = new LinkedList<>();
		erros = ValidacaoComumUtil.validarString(objeto.getImagemUrl(), "Imagem", erros, 255);
		erros = ValidacaoComumUtil.validarString(objeto.getNome(), "Nome", 'o', erros, 255);
		erros = ValidacaoComumUtil.validarString(objeto.getCodigo(), "Código", 'o', erros, 255);
		erros = ValidacaoComumUtil.validarNotNullAndMaiorIgualZero(objeto.getPrecoUnitario(), "Preço unitário", 'o',
				erros);
		return erros;
	}
}
