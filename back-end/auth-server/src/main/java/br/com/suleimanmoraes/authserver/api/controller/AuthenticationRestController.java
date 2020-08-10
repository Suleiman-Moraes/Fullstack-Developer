package br.com.suleimanmoraes.authserver.api.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.authserver.api.model.CurrentUser;
import br.com.suleimanmoraes.authserver.api.model.Usuario;
import br.com.suleimanmoraes.authserver.api.response.Response;
import br.com.suleimanmoraes.authserver.api.security.JwtAuthenticationRequest;
import br.com.suleimanmoraes.authserver.api.security.JwtTokenProvider;
import br.com.suleimanmoraes.authserver.api.service.UsuarioService;
import br.com.suleimanmoraes.authserver.api.util.RestControllerUtil;

@RestController
@RequestMapping("/")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UsuarioService userService;

	@PostMapping(value = "/login")
	public ResponseEntity<Response<CurrentUser>> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {
		Response<CurrentUser> response = new Response<>();
		try {
			final String login = authenticationRequest.getLogin();
			final String password = authenticationRequest.getPassword();
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(login, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final Usuario usuario = userService.findByLogin(authenticationRequest.getLogin());
			final String token = jwtTokenProvider.createToken(usuario);
			response.setData(new CurrentUser(token, usuario));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/user")
    public Principal getCurrentLoggedInUser(Principal user) {
        return user;
    }
	
	@GetMapping(value = "/valid")
	public ResponseEntity<Boolean> isTokenIsValid(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization");
		String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
		final Usuario user = userService.findByLogin(username);
		
		if (user != null && user.getId() != null && !jwtTokenProvider.validateToken(token)) {
			return ResponseEntity.ok(Boolean.TRUE);
		} else {
			return ResponseEntity.badRequest().body(Boolean.FALSE);
		}
	}

	@GetMapping(value = "/me")
	public ResponseEntity<Response<Usuario>> findByToken(HttpServletRequest request) {
		Response<Usuario> response = new Response<>();
		try {
			Usuario objeto = userService.findByToken(request);
			response.setData(objeto);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}
}
