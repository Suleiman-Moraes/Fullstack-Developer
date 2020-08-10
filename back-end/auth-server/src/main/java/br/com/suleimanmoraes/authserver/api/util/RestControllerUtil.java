package br.com.suleimanmoraes.authserver.api.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.suleimanmoraes.authserver.api.response.Response;

/**
 * 
 * @author suleiman-am
 *
 */
public abstract class RestControllerUtil {
	
	public static String CONTEXT = "";
	
	private static final Log logger = LogFactory.getLog(RestControllerUtil.class);
    
    /**
     * 
     * @param response
     * @param erros
     * @return
     */
	public static <T> Response<T> mostrarErroPadraoObject(Response<T> response, String... erros) {
    	logger.error("Error no retorno da requisição");
		response.setData(null);
		List<String> listErro = new LinkedList<>();
		for (String erro : erros) {
			listErro.add(erro);
			logger.error(erro);
		}
		response.setErros(listErro);
		return response;
	}
    
    public static <T> ResponseEntity<Response<T>> mostrarErroPadraoObject(Class<?> classe, Response<T> response, String... erros) {
    	logger.error("Error no retorno da requisição");
		response.setData(null);
		List<String> listErro = new LinkedList<>();
		for (String erro : erros) {
			listErro.add(erro);
			logarError(classe, erro);
		}
		response.setErros(listErro);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
    
    public static void logarError(Class<?> classe) {
    	logger.error("Error no retorno do controlador: " + classe.getName());
    }
    
    public static void logarError(Class<?> classe, String erro) {
    	logger.error("Error no retorno do controlador: " + classe.getName());
    	logger.error("Error no retorno do controlador: " + erro);
    }
    
    public static void logarInfo(Class<?> classe, String msg) {
    	logger.info("Informação no controlador: " + classe.getName());
    	logger.info(msg);
    }
    
    public static void logarInfo(String msg) {
    	logger.info(msg);
    }
    
    public static void logarError(Class<?> classe, String erro, String metodo) {
    	logger.error("Error no retorno do controlador: " + classe.getName());
    	logger.error("Error no retorno do controlador: " + erro);
    	logger.error("Error no retorno do controlador/metodo: " + metodo);
    }
}
