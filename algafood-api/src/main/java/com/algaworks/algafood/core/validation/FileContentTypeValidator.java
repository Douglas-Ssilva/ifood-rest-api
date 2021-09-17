package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile>{

	//representa um tamanho de dado
	private List<String> tiposFiles;

	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.tiposFiles = Arrays.asList(constraintAnnotation.allowed());
	}
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		return file == null || tiposFiles.contains(file.getContentType());
	}

}
