package br.com.suleimanmoraes.calculofreteservice.api.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.calculofreteservice.api.response.Response;
import br.com.suleimanmoraes.calculofreteservice.api.util.RestControllerUtil;

@RestController
@RequestMapping("/")
public class CalculoFreteController {

	@GetMapping(value = "calculo/{qtd}")
	public ResponseEntity<Response<Double>> calcularFrete(HttpServletRequest request, @PathVariable int qtd) {
		Response<Double> response = new Response<>();
		try {
			qtd = qtd >= 0 ? qtd : 0;
			Random random = new Random();
			final int val = random.nextInt(6) + 5;
			response.setData(val * qtd * 1.0);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return RestControllerUtil.mostrarErroPadraoObject(this.getClass(), response, e.getMessage());
		}
	}
}
