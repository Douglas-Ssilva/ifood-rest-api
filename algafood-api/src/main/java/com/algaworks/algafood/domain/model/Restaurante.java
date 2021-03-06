package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(taxaField = "taxaFrete", descricaoField = "nome", descricaoRequired = "Frete Grátis")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

//	@NotBlank
	@Column(nullable = false)
	private String nome;

//	@PositiveOrZero(message = "{taxa.frete.invalida}")
//	@Multiplo(number = 5)
//	@NotNull
//	@TaxaFrete
	private BigDecimal taxaFrete;

	private Boolean ativo = Boolean.TRUE;

	private Boolean aberto = Boolean.FALSE;

//	@Valid
//	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
//	@NotNull
	@ManyToOne
	private Cozinha cozinha;

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "restaurante_responsaveis", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>(); 
	
	@Embedded
	private Endereco endereco;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	public void ativar() {
		setAtivo(true);
	}

	public void desativar() {
		setAtivo(false);
	}

	public void desassociarFormaPagamento(FormaPagamento formaPagamento) {
		getFormasPagamento().remove(formaPagamento);
	}

	public void associarFormaPagamento(FormaPagamento formaPagamento) {
		getFormasPagamento().add(formaPagamento);
	}

	public void associarProduto(Produto produtoBD) {
		getProdutos().add(produtoBD);
	}

	public void abrir() {
		setAberto(true);
	}

	public void fechar() {
		setAberto(false);
	}
	
	public void addResponsavel(Usuario usuario) {
		getResponsaveis().add(usuario);
	}

	public void removeResponsavel(Usuario usuario) {
		getResponsaveis().remove(usuario);
	}
	
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return getFormasPagamento().contains(formaPagamento);
	}

	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return !aceitaFormaPagamento(formaPagamento);
	}
	
	public boolean podeSerAtivado() {
		return !podeSerDesativado();
	}

	public boolean podeSerDesativado() {
		return getAtivo();
	}
	
	public boolean podeSerFechado() {
		return podeSerDesativado() && getAberto();
	}
	
	public boolean podeSerAberto() {
		return podeSerDesativado() && !podeSerFechado();
	}

}
