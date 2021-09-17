package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FotoSize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Criado para não termos que anotar o método com @RequestParam pra cada parâmetro a ser enviado 
 */

@Getter
@Setter
public class FotoProdutoInput {
	
	@NotNull
	@FotoSize(max = "500KB")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	@ApiModelProperty(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = false, hidden = true)//mesmo acrescendo isso nao parou de funcionar a validação(atualizar foto Swagger)
	private MultipartFile arquivo;
	
	@NotBlank
	@ApiModelProperty(value = "Foto da formatura", required = true)
	private String descricao;
}
