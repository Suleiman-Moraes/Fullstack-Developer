package br.com.suleimanmoraes.authserver.api.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import br.com.suleimanmoraes.authserver.api.model.Usuario;
import br.com.suleimanmoraes.authserver.api.model.dto.ClienteNovoDto;

@Component
public interface UsuarioService {

	Usuario findByLogin(String login);

	Usuario save(HttpServletRequest request, ClienteNovoDto objeto) throws Exception;

	Usuario findByToken(HttpServletRequest request);
}
