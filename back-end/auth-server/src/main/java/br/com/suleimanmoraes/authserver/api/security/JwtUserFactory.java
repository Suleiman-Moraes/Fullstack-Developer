package br.com.suleimanmoraes.authserver.api.security;

import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.suleimanmoraes.authserver.api.model.Usuario;
import br.com.suleimanmoraes.authserver.api.service.UsuarioService;

public class JwtUserFactory {
	private JwtUserFactory() {}
	
	public static JwtUser create(Usuario objeto, UsuarioService usuarioService) {
		return new JwtUser(objeto.getId() + "", objeto.getLogin(), objeto.getSenha(), mapToGrantedAthorities(objeto, usuarioService));
	}
	
	private static List<GrantedAuthority> mapToGrantedAthorities(Usuario usuario, UsuarioService usuarioService) {
		List<GrantedAuthority> listaGrantedAuthority = new LinkedList<>();
		usuario.getPermissoes().forEach(p -> listaGrantedAuthority.add(new SimpleGrantedAuthority(p.getNome())));
		return listaGrantedAuthority;
	}
}
