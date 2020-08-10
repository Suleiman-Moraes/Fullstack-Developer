package br.com.suleimanmoraes.authserver.api.security;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.suleimanmoraes.authserver.api.model.Usuario;
import br.com.suleimanmoraes.authserver.api.service.UsuarioService;

@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Usuario usuario = usuarioService.findByLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}
		return org.springframework.security.core.userdetails.User//
				.withUsername(username)//
				.password(usuario.getSenha())//
				.authorities(mapToGrantedAthorities(usuario))//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	}

	private static List<GrantedAuthority> mapToGrantedAthorities(Usuario usuario) {
		List<GrantedAuthority> listaGrantedAuthority = new LinkedList<>();
		usuario.getPermissoes().forEach(p -> listaGrantedAuthority.add(new SimpleGrantedAuthority(p.getNome())));
		return listaGrantedAuthority;
	}
}
