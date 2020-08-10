package br.com.suleimanmoraes.lojaservice.api.controller.abstracts;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.suleimanmoraes.lojaservice.api.interfaces.IControlador;
import br.com.suleimanmoraes.lojaservice.api.response.Response;
import br.com.suleimanmoraes.lojaservice.api.util.RestControllerUtil;

public abstract class ControllerBasic<T> implements IControlador<T> {

	@GetMapping
	@Override
	public ResponseEntity<Response<List<T>>> findAll(HttpServletRequest request) {
		Response<List<T>> response = new Response<>();
		try {
			List<T> listObject = getService().findAll(request);
			response.setData(listObject);
			RestControllerUtil.logarInfo("findAll com sucesso");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}

	@GetMapping(value = "{id}")
	@Override
	public ResponseEntity<Response<T>> findById(HttpServletRequest request, @PathVariable("id") Long id) {
		Response<T> response = new Response<>();
		try {
			T objeto = getService().findById(request, id);
			response.setData(objeto);
			RestControllerUtil.logarInfo("findById com sucesso");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}

	@PostMapping
	@Override
	public ResponseEntity<Response<T>> newObject(HttpServletRequest request, @RequestBody T objeto) {
		Response<T> response = new Response<>();
		try {
			List<String> erros = getService().validar(objeto);
			if (erros.size() > 0) {
				response.setErros(erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			T objetoNovo = getService().save(request, objeto);
			response.setData(objetoNovo);
			RestControllerUtil.logarInfo("save com sucesso");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}

	@PutMapping
	@Override
	public ResponseEntity<Response<T>> update(HttpServletRequest request, @RequestBody T objeto) {
		Response<T> response = new Response<>();
		try {
			List<String> erros = getService().validar(objeto);
			if (erros.size() > 0) {
				response.setErros(erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			T objetoNovo = getService().save(request, objeto);
			response.setData(objetoNovo);
			RestControllerUtil.logarInfo("update com sucesso");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}

	@DeleteMapping(value = "{id}")
	@Override
	public ResponseEntity<Response<Boolean>> deleteById(HttpServletRequest request, @PathVariable("id") Long id) {
		Response<Boolean> response = new Response<>();
		try {
			Boolean retorno = getService().deleteById(request, id);
			response.setData(retorno);
			RestControllerUtil.logarInfo("deleteById com sucesso");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(Boolean.FALSE);
			List<String> listErro = new LinkedList<>();
			listErro.add(e.getMessage());
			response.setErros(listErro);
			RestControllerUtil.logger.error("Error no retorno da requisição");
			RestControllerUtil.logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping(value = "/findbyparamssingle")
	public ResponseEntity<Response<Page<?>>> findByParams(HttpServletRequest request,
			@RequestParam("page") Integer page, @RequestParam("count") Integer count) {
		Response<Page<?>> response = new Response<>();
		try {
			Page<?> pagina = getService().paginarComParemetros(request, page, count);
			response.setData(pagina);
			RestControllerUtil.logarInfo("findByParams com sucesso");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}
}
