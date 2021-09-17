package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  Para armazenar os binários dos arquivos o ideal é um serviço próprio, como disco local ou serviços na nuvem. 
 * Não em tabela no banco de dados O que deixaria o banco de dados mais pesado e mais lento.
 */

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produto_id")//Assim ao tentar salvar 2 fotos para um mesmo produto teremos exception
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId//Informo que a entidade produto é mapeada por meio do id da entidade FotoProduto. Ou seja, getProduto usará o this.getId
	private Produto produto;
	
	private String nome;
	private String descricao;
	private String contentType;
	private Long length;

}
