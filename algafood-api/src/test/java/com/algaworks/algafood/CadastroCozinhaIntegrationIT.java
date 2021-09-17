package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIntegrationIT {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	public void deveCadastrarCozinha() {
		//cenario
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Junit");
		
		//acao
		cozinha = cadastroCozinhaService.save(cozinha);
		
		//validacao
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
		
	}
	
	@Test
	public void deveLancarExcecaoAoSalvarCozinhaNull() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);
		assertThrows(ConstraintViolationException.class, () -> cadastroCozinhaService.save(cozinha));
	}
	
	@Test
	public void deveFalharAoExluirCozinhaEmUso() {
		assertThrows(EntityNotDeleteException.class, () -> cadastroCozinhaService.delete(1L));
	}
	
	@Test
	public void deveFalharAoExluirCozinhaInexistente() {
		assertThrows(EntityNotFoundException.class, () -> cadastroCozinhaService.buscar(1000L));
	}
	
	


}
