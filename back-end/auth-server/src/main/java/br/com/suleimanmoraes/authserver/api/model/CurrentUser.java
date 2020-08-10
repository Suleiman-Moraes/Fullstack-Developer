package br.com.suleimanmoraes.authserver.api.model;

import java.util.Collection;
import java.util.TreeSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentUser {
	
	private String token;
	private Usuario user;
	private Collection<String> roles;
	
	public CurrentUser(String token, Usuario user) {
		roles = new TreeSet<>();
		user.getPermissoes().forEach(per -> roles.add(per.toString()));
		this.token = token;
		this.user = user;
	}
}
