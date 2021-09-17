package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FotoSizeValidator implements ConstraintValidator<FotoSize, MultipartFile>{

	//representa um tamanho de dado
	private DataSize dataSize;

	@Override
	public void initialize(FotoSize constraintAnnotation) {
		this.dataSize = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		return file == null || file.getSize() <= this.dataSize.toBytes();
	}

}
