package br.com.suleimanmoraes.lojaservice.api.interfaces;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;

import br.com.suleimanmoraes.lojaservice.api.response.Response;

public interface CrudPadraoService<T> extends ICrudPadraoService<T>{
	
	JpaRepository<T, Long> getRepository();
	
	Log getLogger();
	
	@Override
	default List<T> findAll(HttpServletRequest request) {
		try {
			List<T> objetos = getRepository().findAll();
			getLogger().info("findAll " + objetos.size());
			return objetos;
		} catch (Exception e) {
			getLogger().warn("findAll " + e.getMessage());
			return null;
		}
	}

	@Override
	default T findById(HttpServletRequest request, Long id) {
		try {
			T objeto = getRepository().findById(id).get();
			getLogger().info("findById " + id);
			return objeto;
		} catch (Exception e) {
			getLogger().warn("findById " + e.getMessage());
			return null;
		}
	}

	@Override
	default T save(HttpServletRequest request, T objeto) throws Exception {
		try {
			objeto = getRepository().save(objeto);
			return objeto;
		} catch (DataIntegrityViolationException e) {
			getLogger().error("save " + e.getMessage());
			throw new Exception("Dados inválidos");
		} catch (Exception e) {
			getLogger().error("save " + e.getMessage());
			throw e;
		}
	}

	@Override
	default Boolean deleteById(HttpServletRequest request, Long id) {
		getLogger().warn("Não pode ser excluído");
		return Boolean.FALSE;
	}

	@Override
	default Boolean existsByField(HttpServletRequest request, String fieldName, Object value) throws Exception {
		getLogger().error("existsByField Método não Implementado");
		return Boolean.FALSE;
	}

	@Override
	default T findByField(HttpServletRequest request, String field, Object value) {
		getLogger().error("findByField Método não Implementado");
		return null;
	}
	
	@Override
	default Page<T> paginarComParemetros(HttpServletRequest request, Integer page, Integer count) {
		try {
			Page<T> pagina = getRepository().findAll(PageRequest.of(page, count, Sort.by(Sort.Direction.DESC, "id")));
			getLogger().info(String.format("paginarComParemetros(%s, %s) == pagina.getContent().size() = ", 
					page, count, pagina.getContent().size()));
			return pagina;
		} catch (Exception e) {
			getLogger().warn("paginarComParemetros" + e.getMessage());
			return null;
		}
	}
	
	@Override
	default List<String> validar(T objeto){
		return new LinkedList<>();
	}
	
	default HttpHeaders getHeadersComToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		return headers;
	}

	default void validarRespose(Response<?> response) throws Exception {
		if(response.getErros() != null && !response.getErros().isEmpty()) {
			throw new Exception(response.getErros().get(0));
		}
	}
}
