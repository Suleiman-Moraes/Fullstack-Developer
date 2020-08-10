package br.com.suleimanmoraes.lojaservice.api.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import br.com.suleimanmoraes.lojaservice.api.response.Response;

public interface IControlador<T> {
    ResponseEntity<Response<List<T>>> findAll(HttpServletRequest request);
 
    ResponseEntity<Response<T>> findById(HttpServletRequest request, Long id);
 
    ResponseEntity<Response<T>> newObject(HttpServletRequest request, T objeto);
 
    ResponseEntity<Response<T>> update(HttpServletRequest request, T objeto);
 
    ResponseEntity<Response<Boolean>> deleteById(HttpServletRequest request, Long id);
    
    CrudPadraoService<T> getService();
}
