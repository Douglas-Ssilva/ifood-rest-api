package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number>{

	private int number;

	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.number = constraintAnnotation.number();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valid = true;
		
		if (Objects.nonNull(value)) {
			BigDecimal valueDecimal = BigDecimal.valueOf(value.doubleValue());
			BigDecimal numberDecimal = BigDecimal.valueOf(this.number);
			valid = valueDecimal.remainder(numberDecimal).compareTo(BigDecimal.ZERO) == 0;
		}

		return valid;
	}

}
