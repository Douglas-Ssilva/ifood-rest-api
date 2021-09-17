package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_ENTITY_NOT_DELETE = "Restaurante de id %d não pode ser deletado!";
	
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@Transactional
	public Restaurante save(Restaurante restaurante) {
		var cozinha = buscarCozinha(restaurante.getCozinha().getId());
		var cidade = cadastroCidadeService.buscar(restaurante.getEndereco().getCidade().getId());
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);//evitando que retorne null p atributos de cidade que não estão no RestauranteInputDTO
		return restauranteRepository.save(restaurante);
	}

	private Cozinha buscarCozinha(Long id) {
		return cadastroCozinhaService.buscar(id);
	}

	@Transactional
	public void delete(Long id) {
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotDeleteException(String.format(MSG_ENTITY_NOT_DELETE, id));
		}
	}
	
	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNotFoundException(id));
	}
	
	@Transactional
	public void ativar(Long id) {
		var restaurante = buscar(id);
		restaurante.ativar(); //entity no estado managed
	}
	
	@Transactional
	public void ativar(List<Long> ids) {
		ids.forEach(this::ativar);
	}
	
	@Transactional
	public void desativar(Long id) {
		var restaurante = buscar(id);
		restaurante.desativar();
	}
	
	@Transactional
	public void desativar(List<Long> ids) {
		ids.forEach(this::desativar);
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscar(restauranteId);
		var formaPagamento = cadastroFormaPagamentoService.findById(formaPagamentoId);
		
		restaurante.desassociarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscar(restauranteId);
		var formaPagamento = cadastroFormaPagamentoService.findById(formaPagamentoId);
		
		restaurante.associarFormaPagamento(formaPagamento);
	}

	@Transactional
	public Produto associarProduto(Long restauranteId, Produto produto) {
		var restaurante = buscar(restauranteId);
		produto.setRestaurante(restaurante);
		var produtoBD = produtoRepository.save(produto);
		
		restaurante.associarProduto(produtoBD);
		return produtoBD;
	}

	@Transactional
	public void abrir(Long restauranteId) {
		var restaurante = buscar(restauranteId);
		restaurante.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
		var restaurante = buscar(restauranteId);
		restaurante.fechar();
	}
	
	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		var restaurante = buscar(restauranteId);
		var usuario = cadastroUsuarioService.findById(usuarioId);
		
		restaurante.addResponsavel(usuario);
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		var restaurante = buscar(restauranteId);
		var usuario = cadastroUsuarioService.findById(usuarioId);
		
		restaurante.removeResponsavel(usuario);
	}

}












