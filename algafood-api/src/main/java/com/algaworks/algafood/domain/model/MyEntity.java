package com.algaworks.algafood.domain.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * Criada para resolver problema do cache, pois ao deletar um registro iremos atualizar a coluna situacao 
 */

@MappedSuperclass
@Getter
@Setter
public class MyEntity {
	
	@Enumerated(EnumType.STRING)
	private Situacao situacao = Situacao.ATIVO;

}
