package br.com.suleimanmoraes.lojaservice.api.util;

public class StringUtil {

	public static final String STR_BARRA = "/";
	public static final String STR_BARRA_INVERTIDA = "\\";
	public static final String ASPAS_STR = "'%s'";
	public static final String VALUE_DESCRICAO = "%s - %s";

	/**
	 * Adiciona o character a direita até completar o tamanho especificado
	 * 
	 * @param str
	 * @param size
	 * @param character
	 * @return
	 */
	public static String rightPad(String str, int size, String character) {
		while (str.length() < size) {
			str = str + character;
		}
		return str;
	}

	/**
	 * Adiciona o character a esquerda até completar o tamanho especificado
	 * 
	 * @param str
	 * @param size
	 * @param character
	 * @return
	 */
	public static String leftPad(String str, int size, String character) {
		while (str.length() < size) {
			str = character + str;
		}
		return str;
	}

	/**
	 * Remove a esquerda enquanto existir o character
	 * 
	 * @param str
	 * @param character
	 * @return
	 */
	public static String stripLeft(String str, Character character) {
		String c = character.toString();
		while (str.startsWith(c)) {
			str = str.substring(1, str.length());
		}
		return str;
	}

	/**
	 * Remove a esquerda enquanto existir o character até o tamanho especificado
	 * 
	 * @param str
	 * @param maxLength
	 * @param character
	 * @return
	 */
	public static String stripLeft(String str, int maxLength, Character character) {
		String c = character.toString();
		while (str.startsWith(c) && str.length() > maxLength) {
			str = str.substring(1, str.length());
		}
		return str;
	}

	/**
	 * Remove a direita enquanto existir o character
	 * 
	 * @param str
	 * @param character
	 * @return
	 */
	public static String stripRight(String str, Character character) {
		String c = character.toString();
		while (str.endsWith(c)) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * Remove a direita enquanto existir o character até o tamanho especificado
	 * 
	 * @param str
	 * @param maxLength
	 * @param character
	 * @return
	 */
	public static String stripRight(String str, int maxLength, Character character) {
		String c = character.toString();
		while (str.endsWith(c) && str.length() > maxLength) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static String formataEmailRecuperacao(String email) {
		String a = email.split("@")[0];
		String b = email.split("@")[1];
		String c = "";
		for (int i = 0; i != a.length(); i++) {
			if (i == 0 || i == a.length() - 1) {
				c += a.substring(i, i + 1);
			} else {
				c += "*";
			}
		}
		return c + "@" + b;
	}


	/**
	 * Retorna a string com a primeira letra Maiúscula
	 * 
	 * @param string
	 * @return
	 */
	public static String getStringComPrimeiraLetraMaiuscula(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	/**
	 * Verifica se o texto é printable
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAsciiPrintable(String str) {
		if (str == null) {
			return false;
		}

		int sz = str.length();

		for (int i = 0; i < sz; i++) {
			if (isAsciiPrintable(str.charAt(i)) == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * <p>
	 * Checks whether the character is ASCII 7 bit printable.
	 * </p>
	 *
	 * <pre>
	 *   CharUtils.isAsciiPrintable('a')  = true
	 *   CharUtils.isAsciiPrintable('A')  = true
	 *   CharUtils.isAsciiPrintable('3')  = true
	 *   CharUtils.isAsciiPrintable('-')  = true
	 *   CharUtils.isAsciiPrintable('\n') = false
	 *   CharUtils.isAsciiPrintable('&copy;') = false
	 * </pre>
	 * 
	 * @param ch
	 *            the character to check
	 * @return true if between 32 and 126 inclusive
	 */
	public static boolean isAsciiPrintable(char ch) {
		return ch >= 32 && ch < 127;
	}

	/**
	 * Remove todas as ocorrências da regex pela replacement
	 * 
	 * @param texto
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceAll(String texto, String regex, String replacement) {
		while (texto.contains(regex)) {
			texto = texto.replaceAll(regex, replacement);
		}

		return texto;
	}

	/**
	 * Retorna um texto com valor - descricação, exemplo: 1 - Sim, 2 - Nâo...
	 * 
	 * @param value
	 * @param descricao
	 * @return
	 */
	public static String getValueDescricao(Object value, Object descricao) {
		return String.format(VALUE_DESCRICAO, new Object[] { value, descricao });
	}

	public static Boolean isEmpity(String texto) {
		return texto.trim().equals("");
	}

	public static Boolean isNull(String texto) {
		return texto == null;
	}

	public static Boolean isNotEmpity(String texto) {
		return !isEmpity(texto);
	}

	public static Boolean isNotNull(String texto) {
		return !isNull(texto);
	}

	public static Boolean isNullOrEmpity(String texto) {
		return isNull(texto) || isEmpity(texto);
	}

	public static Boolean isNotNullAndEmpity(String texto) {
		return isNotNull(texto) && isNotEmpity(texto);
	}
	
	public static String isNotNullAndEmpityReturnArgOrThis(String texto, String arg) {
		return isNotNullAndEmpity(texto) ? texto : arg;
	}
	
	public static String isNotNullAndEmpityReturnVazioOrThis(String texto) {
		return isNotNullAndEmpity(texto) ? texto : "";
	}

	public static String isNotNullAndEmpityReturnTracoOrThis(String texto) {
		return isNotNullAndEmpity(texto) ? texto : "-";
	}

	public static String tratarObjectStringNotNull(Object object) {
		return object != null ? object.toString() : "-";
	}
	
	public static String getValueOrTrace(Object[] vet, Integer pos) {
		return vet[pos] != null ? vet[pos].toString() : "-";
	}
	
	public static String unMaskCpfOrCnpj(String cpfOrCnpj) {
		return isNotNullAndEmpity(cpfOrCnpj) ? cpfOrCnpj.replaceAll("[./-]", "") : "";
	}
	
	public static String maskCpfOrCnpj(String cpfOrCnpj) {
		cpfOrCnpj = unMaskCpfOrCnpj(cpfOrCnpj);
		if(cpfOrCnpj.length() == 11) {
			cpfOrCnpj = cpfOrCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
		}
		else if(cpfOrCnpj.length() == 14) {
			cpfOrCnpj = cpfOrCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
		}
		return cpfOrCnpj;
	}
}