package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String descricaoField;
	private String taxaField;
	private String descricaoRequired;

	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		this.descricaoField = constraintAnnotation.descricaoField();
		this.taxaField = constraintAnnotation.taxaField();
		this.descricaoRequired = constraintAnnotation.descricaoRequired();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean isValid = true;
		
		try {
			BigDecimal taxaFrete = (BigDecimal) BeanUtils.getPropertyDescriptor(value.getClass(), this.taxaField).getReadMethod().invoke(value);
			String descricaoRestaurante = (String) BeanUtils.getPropertyDescriptor(value.getClass(), this.descricaoField).getReadMethod().invoke(value);
			if (Objects.nonNull(taxaFrete) && StringUtils.isNotBlank(descricaoRestaurante) && taxaFrete.compareTo(BigDecimal.ZERO) == 0) {
				isValid = descricaoRestaurante.toLowerCase().contains(this.descricaoRequired.toLowerCase());
			}
			return isValid;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
		
	}

}
