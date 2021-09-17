package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Habilitando CORS para o projeto inteiro 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")//Todas URIs do projeto
			.allowedMethods("*"); //Todos verbos HTTP
//			.allowedOrigins("*") //qualquer origem. Default = *
//			.maxAge(30);
			
	}
	
	/**
	 * Filtra as requisições e add um cabeçalho chamado ETag com um valor hash. Assim na próxima request, o browser add o header 
	 * If-None-Match com esse valor pra fazer a comparação se houve ou não alteração na entidade.
	 * If-None-Match é add quando o cache se torna Stale
	 */
	@Bean
	public ShallowEtagHeaderFilter filter() {
		return new ShallowEtagHeaderFilter();
	}
}
