package br.com.suleimanmoraes.calculofreteservice.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Allow swagger to be accessed without authentication
		web.ignoring().antMatchers(
				"/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui.html**/**",
                "/swagger-ui.html**/**/**",
                "/webjars/**"
				);//
	}
}
