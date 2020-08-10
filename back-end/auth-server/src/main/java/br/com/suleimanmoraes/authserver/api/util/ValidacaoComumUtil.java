package br.com.suleimanmoraes.authserver.api.util;

import java.util.List;

/**
 * 
 * @author Suleiman Moraes
 *
 */
public abstract class ValidacaoComumUtil {
	
	public static List<String> validarString(String texto, String nome, char fim, List<String> erros){
		if(StringUtil.isNullOrEmpity(texto)) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		return erros;
	}
	
	public static List<String> validarString(String texto, String nome, char fim, List<String> erros, Integer qtdChar){
		if(StringUtil.isNullOrEmpity(texto)) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(texto.length() > qtdChar){
			erros.add(String.format("\"%s\" excedeu a quantidade máxima de caracteres de %s.", nome, qtdChar));
		}
		return erros;
	}
	
	public static List<String> validarString(String texto, String nome, List<String> erros, Integer qtdChar){
		if(StringUtil.isNotNullAndEmpity(texto) && texto.length() > qtdChar){
			erros.add(String.format("\"%s\" excedeu a quantidade máxima de caracteres de %s.", nome, qtdChar));
		}
		return erros;
	}
	
	public static void validarString(String texto, String nome, char fim) throws Exception {
		if(StringUtil.isNullOrEmpity(texto)) {
			throw new Exception(String.format("%s deve ser informad%s.", nome, fim));
		}
	}
	
	public static void validarString(String texto, String nome, char fim, Integer qtdChar) throws Exception {
		if(StringUtil.isNullOrEmpity(texto)) {
			throw new Exception(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(texto.length() > qtdChar){
			throw new Exception(String.format("\"%s\" excedeu a quantidade máxima de caracteres de %s.", nome, qtdChar));
		}
	}
	
	public static List<String> validarNotNullAndMaiorZero(Integer objeto, String nome, char fim, List<String> erros){
		if(objeto == null) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(objeto <= 0){
			erros.add(String.format("%s deve ser maior que zero.", nome));
		}
		return erros;
	}
	
	public static List<String> validarNotNullAndMaiorZero(Double objeto, String nome, char fim, List<String> erros){
		if(objeto == null) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(objeto <= 0){
			erros.add(String.format("%s deve ser maior que zero.", nome));
		}
		return erros;
	}
	
	public static List<String> validarNotNullAndMaiorIgualZero(Double objeto, String nome, char fim, List<String> erros){
		if(objeto == null) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(objeto < 0){
			erros.add(String.format("%s deve ser maior ou igual zero.", nome));
		}
		return erros;
	}
	
	public static List<String> validarObjectAndId(Object objeto, String nome, char fim, List<String> erros){
		try {
			if(objeto == null) {
				erros.add(String.format("%s deve ser informad%s.", nome, fim));
			}
			else if(Double.valueOf(objeto.getClass().getMethod("getId").invoke(objeto).toString()) <= 0){
				erros.add(String.format("%s deve ser maior que zero.", nome));
			}
		} catch (Exception e) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		return erros;
	}
	
	public static List<String> validarNotNullAndMaiorZero(Long objeto, String nome, char fim, List<String> erros){
		if(objeto == null) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(objeto <= 0){
			erros.add(String.format("%s deve ser maior que zero.", nome));
		}
		return erros;
	}
	
	public static List<String> validarBoolean(boolean objeto, String nome, char fim, List<String> erros){
		if(!objeto) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		return erros;
	}
	
	public static List<String> validarNotNull(Object objeto, String nome, char fim, List<String> erros){
		if(objeto == null) {
			erros.add(String.format("%s deve ser informad%s.", nome, fim));
		}
		return erros;
	}

	public static void validarNotNull(Object objeto, String nome, char fim) throws Exception {
		if(objeto == null) {
			throw new Exception(String.format("%s deve ser informad%s.", nome, fim));
		}
	}
	
	public static void validarNotNullAndMaiorZero(Integer objeto, String nome, char fim) throws Exception{
		if(objeto == null) {
			throw new Exception(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(objeto <= 0){
			throw new Exception(String.format("%s deve ser maior que zero.", nome));
		}
	}
	
	public static void validarNotNullAndMaiorZero(Double objeto, String nome, char fim) throws Exception{
		if(objeto == null) {
			throw new Exception(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(objeto <= 0){
			throw new Exception(String.format("%s deve ser maior que zero.", nome));
		}
	}
	
	public static void validarNotNullAndMaiorZero(Long objeto, String nome, char fim) throws Exception{
		if(objeto == null) {
			throw new Exception(String.format("%s deve ser informad%s.", nome, fim));
		}
		else if(objeto <= 0){
			throw new Exception(String.format("%s deve ser maior que zero.", nome));
		}
	}
}
