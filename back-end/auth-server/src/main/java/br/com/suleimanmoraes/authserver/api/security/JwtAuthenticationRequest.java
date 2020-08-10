package br.com.suleimanmoraes.authserver.api.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String password;

	public JwtAuthenticationRequest() {}
	public JwtAuthenticationRequest(String login, String password) {
		this.login = login;
		this.password = password;
	}
}
