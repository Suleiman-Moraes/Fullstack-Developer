package br.com.suleimanmoraes.lojaservice.api.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.suleimanmoraes.lojaservice.api.response.Response;
import feign.FeignException;

public class FeignExceptionUtil {

	public static void tratar(FeignException f) throws Exception {
		try {
			if(f.status() == 400) {
				Gson gson = new Gson();
				Response<String> retorno = gson.fromJson(new String(f.responseBody().get().array()), new TypeToken<Response<String>>() {}.getType());
				throw new Exception(retorno.getErros().get(0));
			}
		} catch (Exception e) {
			throw e;
		}
		throw new Exception("Ocorreu um erro inesperado");
	}
}
