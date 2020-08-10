package br.com.suleimanmoraes.lojaservice.api.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

public interface ICrudPadraoService<T>{
	List<T> findAll(HttpServletRequest request);

	T findById(HttpServletRequest request, Long id);

	T save(HttpServletRequest request, T objeto) throws Exception;

	Boolean deleteById(HttpServletRequest request, Long id);

	Boolean existsByField(HttpServletRequest request, String fieldName, Object value) throws Exception;

	T findByField(HttpServletRequest request, String field, Object value);

	Page<T> paginarComParemetros(HttpServletRequest request, Integer page, Integer count);
	
	List<String> validar(T objeto);
}
