package br.com.suleimanmoraes.authserver.api.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
	private T data;
	private List<String> erros;
	
	public List<String> getErros() {
		if(this.erros == null) {
			this.erros = new ArrayList<>();
		}
		return erros;
	}
	
	public Response<T> setData(T data) {
		this.data = data;
		return this;
	}
}