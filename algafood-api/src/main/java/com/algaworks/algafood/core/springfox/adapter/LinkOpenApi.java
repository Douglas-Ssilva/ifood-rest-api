package com.algaworks.algafood.core.springfox.adapter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Links")
public class LinkOpenApi {
	
	private Link rel;
	
	@Setter
	@Getter
	@ApiModel("Link")
	private class Link {
		private String href;
		private boolean templated;
	}

}
