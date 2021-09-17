package com.algaworks.algafood.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluiDescricaoValidator.class })
public @interface ValorZeroIncluiDescricao {
	
	String message() default "Restaurante não está apto a conceder frete grátis";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String taxaField ();
	
	String descricaoField ();
	
	String descricaoRequired ();

}
