package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNotFoundException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}
	
	public Page<Pedido> findAll(Specification<Pedido> specification, Pageable pageable) {
		return pedidoRepository.findAll(specification, pageable);
	}
	
	public Pedido findByCodigo(String codigo) {
		return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNotFoundException(codigo));
	}

	@Transactional
	public Pedido emitirPedido(Pedido pedido) {
		// TODO pegar usuário autenticado
		pedido.setCliente(new Usuario());
		pedido.getCliente().setId(1L);
		
		validarPedido(pedido); 
		validarItens(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
	    pedido.calcularValorTotal();

		
		return pedidoRepository.save(pedido);
	}
	
	private void validarPedido(Pedido pedido) {
	    var cidade = cadastroCidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());
	    var cliente = cadastroUsuarioService.findById(pedido.getCliente().getId());
	    var restaurante = cadastroRestauranteService.buscar(pedido.getRestaurante().getId());
	    var formaPagamento = cadastroFormaPagamentoService.findById(pedido.getFormaPagamento().getId());

	    pedido.getEnderecoEntrega().setCidade(cidade);
	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.setFormaPagamento(formaPagamento);
	    
	    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
	        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.", formaPagamento.getDescricao()));
	    }
	}
	

	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			var produto = cadastroProdutoService.findById(item.getProduto().getId(), pedido.getRestaurante().getId());

			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
}
