package br.com.suleimanmoraes.authserver.api.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.suleimanmoraes.authserver.api.model.Permissao;
import br.com.suleimanmoraes.authserver.api.model.Usuario;
import br.com.suleimanmoraes.authserver.api.model.dto.ClienteNovoDto;
import br.com.suleimanmoraes.authserver.api.repository.UsuarioRepository;
import br.com.suleimanmoraes.authserver.api.security.JwtTokenProvider;
import br.com.suleimanmoraes.authserver.api.service.UsuarioService;
import br.com.suleimanmoraes.authserver.api.util.ValidacaoComumUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	private static final Log logger = LogFactory.getLog(UsuarioService.class);
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private void validar(ClienteNovoDto objeto) throws Exception {
		ValidacaoComumUtil.validarNotNullAndMaiorZero(objeto.getId(), "Cliente", 'o');
	}
	
	private void validar(Usuario objeto) throws Exception {
		ValidacaoComumUtil.validarString(objeto.getLogin(), "Login", 'o', 255);
		ValidacaoComumUtil.validarString(objeto.getSenha(), "Senha", 'a', 20);
		ValidacaoComumUtil.validarString(objeto.getNome(), "Nome", 'o', 255);
		if(repository.existsByLoginAndIdNot(objeto.getLogin(), objeto.getId() == null ? 0l : objeto.getId())) {
			throw new Exception("Login se encontra uso");
		}
	}
	
	@Transactional
	private Usuario save(HttpServletRequest request, Usuario objeto) throws Exception {
		try {
			validar(objeto);
			if(CollectionUtils.isEmpty(objeto.getPermissoes())) {
				objeto.addPermissao(new Permissao(1l));
			}
			if(objeto.getSenha().length() <= 20) {
				objeto.setSenha(passwordEncoder.encode(objeto.getSenha()));
			}
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
	public Usuario findByLogin(String login) {
		try {
			return repository.findTopByLogin(login);
		} catch (Exception e) {
			logger.warn("findByLogin " + e.getMessage());
			return null;
		}
	}

	@Override
	public Usuario save(HttpServletRequest request, ClienteNovoDto objeto) throws Exception {
		try {
			validar(objeto);
			return save(request, new Usuario(objeto, new Permissao(1l)));
		} catch (Exception e) {
			logger.warn("save " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Usuario findByToken(HttpServletRequest request) {
		try {
			String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
			final Usuario user = findByLogin(username);
			return user;
		} catch (Exception e) {
			logger.warn("findByToken " + e.getMessage());
		}
		return null;
		
	}
}
