package br.com.suleimanmoraes.lojaservice.api.util;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.suleimanmoraes.lojaservice.api.response.Response;

public class ConsumidorServicosUtil {
	
private static final Log logger = LogFactory.getLog(ConsumidorServicosUtil.class);
	
	public static HttpComponentsClientHttpRequestFactory requestFactory = null;
	
	/**
	 * 
	 * @param url String
	 * @param httpMethod HttpMethod
	 * @param object Object
	 * @param headers HttpHeaders
	 * @return String
	 */
	public static String consumir(String url, HttpMethod httpMethod, Object object, HttpHeaders headers) {
		try {
			return consumirRetResponse(url, httpMethod, object, headers).getBody();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param httpMethod
	 * @param object
	 * @param headers
	 * @return ResponseEntity<String>
	 */
	public static ResponseEntity<String> consumirRetResponse(String url, HttpMethod httpMethod, Object object, HttpHeaders headers) {
		try {
			ResponseEntity<String> response = new RestTemplate(getRequestFactory()).exchange(url, httpMethod, new HttpEntity<>(object, headers), String.class);
			logger.info(String.format("Reaquisção %s realizada na URL == \"%s\" com Status == %s", httpMethod, url, response.getStatusCode()));
			return response;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param url String
	 * @param httpMethod HttpMethod
	 * @param type Type
	 * @param object Object
	 * @param headers HttpHeaders
	 * @return Object<T>
	 */
	public static <T> T consumir(String url, HttpMethod httpMethod, Type type, Object object, HttpHeaders headers) {
		String result = consumir(url, httpMethod, object, headers);
		
		return consumir(result, type);
	}
	
	/**
	 * 
	 * @param result
	 * @param type
	 * @return Object<T>
	 */
	public static <T> T consumir(String result, Type type) {
		try {
			Gson gson = new Gson();
			T retorno = gson.fromJson(result, type);
			return retorno;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param url String 
	 * @param httpMethod HttpMethod
	 * @param headers HttpHeaders
	 * @param type Type
	 * @return Object<T>
	 */
	public static <T> T consumir(String url, HttpMethod httpMethod, HttpHeaders headers, Type type) {
		return consumir(url, httpMethod, type, null, headers);
	}
	
	/**
	 * 
	 * @param url String
	 * @param httpMethod HttpMethod
	 * @param headers HttpHeaders
	 * @return String
	 */
	public static String consumir(String url, HttpMethod httpMethod, HttpHeaders headers) {
		return consumir(url, httpMethod, null, headers);
	}
	
	/**
	 * 
	 * @param url String
	 * @param httpMethod HttpMethod
	 * @param headers HttpHeaders
	 * @return Map<String, Object>
	 */ 
	public static Map<String, Object> consumirRetMap(String url, HttpMethod httpMethod, HttpHeaders headers) {
		return consumirRetMap(url, httpMethod, headers, null);
	}
	
	/**
	 * 
	 * @param url
	 * @param httpMethod
	 * @param headers
	 * @param objeto
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> consumirRetMap(String url, HttpMethod httpMethod, HttpHeaders headers, Object objeto) {
		return consumir(url, httpMethod, new TypeToken<Map<String, Object>>() {}.getType(), objeto, headers);
	}
	
	/**
	 * 
	 * @param url
	 * @param httpMethod
	 * @param headers
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> consumirRetMap(String url, HttpMethod httpMethod) {
		return consumirRetMap(url, httpMethod, new HttpHeaders());
	}
	
	/**
	 * 
	 * @param url
	 * @param httpMethod
	 * @param object
	 * @return
	 */
	public static String consumir(HttpMethod httpMethod, Object object, String url) {
		return consumir(url, httpMethod, object, new HttpHeaders());
	}
	
	public static void validarRespose(Response<?> response) throws Exception {
		if(response.getErros() != null && !response.getErros().isEmpty()) {
			throw new Exception(response.getErros().get(0));
		}
	}
	
	public static HttpHeaders getHeadersComToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		return headers;
	}
	
	private static HttpComponentsClientHttpRequestFactory getRequestFactory() {
		if(requestFactory == null) {
			try {
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				} };
				
				SSLContext sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, trustAllCerts, new SecureRandom());
				SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

				CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			    requestFactory = new HttpComponentsClientHttpRequestFactory();
			    requestFactory.setHttpClient(httpClient);
				
				requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setHttpClient(httpClient);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}
		return requestFactory;
	}
}
