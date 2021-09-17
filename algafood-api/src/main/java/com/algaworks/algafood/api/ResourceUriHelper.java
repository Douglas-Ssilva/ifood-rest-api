package com.algaworks.algafood.api;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

/**
 * É uma boa prática add no cabeçalho location a URI do recurso que acabou de ser criado, independente de usarmos HATEOAS ou nao
 */

@UtilityClass
public class ResourceUriHelper {
	
	public static void addLocationInResponse(Object resourceId) {
		var uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(resourceId).toUri();
		
		var httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		httpServletResponse.setHeader(HttpHeaders.LOCATION, uri.toString());
	}
}
