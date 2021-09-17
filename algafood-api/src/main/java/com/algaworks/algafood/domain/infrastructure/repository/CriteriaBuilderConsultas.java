package com.algaworks.algafood.domain.infrastructure.repository;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.Usuario;

/**
 * https://github.com/algaworks/curso-especialista-jpa/blob/master/17.09-publicando-o-projeto-no-jboss-wildfly/src/test/java/com/algaworks/ecommerce/criteria/ExpressoesCondicionaisCriteriaTest.java
 * Parei nessa: JoinCriteriaTest.java
 * @author dougl
 *
 */
@Repository
public class CriteriaBuilderConsultas {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Cozinha> usarDistinct() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.distinct(true);
		return manager.createQuery(query).getResultList();
	}
	
	public List<Cozinha> buscarCozinhasOrdenadas() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.orderBy(builder.asc(root.get("nome")));
		return manager.createQuery(query).getResultList();
	}
	
	public List<CozinhaDTO> buscarCozinhasProjetadasDTO() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(CozinhaDTO.class);
		var root = query.from(Cozinha.class);
		var selection = builder.construct(CozinhaDTO.class, root.get("id"), root.get("nome"));
		query.select(selection);
		return manager.createQuery(query).getResultList();
	}
	
//	public List<CozinhaDTO> buscarCozinhasProjetadasTuple() {
//		var builder = manager.getCriteriaBuilder();
//		var query = builder.createQuery(Tuple.class);
//		var root = query.from(Cozinha.class);
//		query.select(builder.tuple(root.get("nome").alias("nome")));
//		List<Tuple> tuples = manager.createQuery(query).getResultList();
//		return tuples.stream().map(t -> new CozinhaDTO(((String)t.get("nome")))).collect(Collectors.toList());
//	}
	
	public List<Object[]> projetarOResultado() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Cozinha.class);
		query.multiselect(root.get("id"), root.get("nome"));
		return manager.createQuery(query).getResultList();
	}
	
	public List<String> buscarSomenteNome(Long id) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(String.class);
		var root = query.from(Cozinha.class);
		query.select(root.get("nome"));
		query.where(builder.equal(root.get("id"), id));
		return manager.createQuery(query).getResultList();
	}
	
	//Expressões condicionais
	
	public List<Cozinha> buscarCozinhasExpressionIn(List<Long> ids) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.where(builder.and(root.get("id").in(ids)));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 *   //The equivalent JPQL
  	 *		SELECT p.id, p.codigo,
     *              CASE p.stauts
     *              WHEN 'CRIADO' THEN 'Pedido foi criado'
     *              WHEN 'ENTREGUE' THEN 'Pedido foi entregue'
     *              ELSE 'Não identificado' or p.stauts
     *              END
     *              FROM Pedido p
	 */
	public List<Object[]> buscarCozinhasExpressionCase() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		
		query.multiselect(root.get("id"), root.get("codigo"), 
			builder.selectCase(root.get("status"))
			.when("CRIADO", "Pedido foi criado")
			.when("CONFIRMADO", "Pedido foi entregue")
			.otherwise("Não identificado"));
			
		return manager.createQuery(query).getResultList();
	}
	
	
	public List<Cozinha> buscarCozinhasDiferentesDe(Cozinha cozinha) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.where(builder.notEqual(root.get("id"), cozinha.getId()));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * select cozinha0_.id as id1_1_, cozinha0_.nome as nome2_1_ from cozinha cozinha0_ where cozinha0_.id not in  (? , ?)
	 */
	public List<Cozinha> buscarCozinhasDiferentesDe(List<Cozinha> cozinhas) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.where(builder.not(root.in(cozinhas)));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * WHERE pedido0_.data_criacao BETWEEN ? AND ?
	 */
	public List<Pedido> buscarPedidosEntre(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Pedido.class);
		var root = query.from(Pedido.class);
		query.select(root);
		query.where(builder.between(root.get("dataCriacao"), dataInicio, dataFim));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * WHERE pedido0_.data_criacao BETWEEN ? AND ?
	 */
	public List<Pedido> buscarPedidosGreaterOrLessThanOrEqualTo(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Pedido.class);
		var root = query.from(Pedido.class);
		query.select(root);
		query.where(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicio), builder.lessThanOrEqualTo(root.get("dataCriacao"), dataFim));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * Retorna as cozinhas que nao existem no restaurante
	 * SELECT cozinha0_.nome FROM cozinha cozinha0_
	 *	WHERE NOT EXISTS (SELECT restaurant1_.id FROM restaurante restaurant1_ WHERE cozinha0_.id=restaurant1_.cozinha_id)
	 */
	public List<Cozinha> buscarCozinhasSemRestauranteIsEmpty() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.where(builder.isEmpty(root.get("restaurantes")));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * select cozinha0_.nome from cozinha cozinha0_ where cozinha0_.nome is null
	 */
	public List<Cozinha> buscarCozinhasComNomeNull() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.where(builder.isNull(root.get("nome")));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * Faz a consulta independente se a String está em maiúscula ou minúscula
	 * select cozinha0_.id from cozinha cozinha0_ where cozinha0_.nome like '%SU%'
	 */
	public List<Cozinha> buscarCozinhasComNomeContaining(String str) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Cozinha.class);
		var root = query.from(Cozinha.class);
		query.select(root);
		query.where(builder.like(root.get("nome"), "%" + str + "%"));
		return manager.createQuery(query).getResultList();
	}

	//Funções de agregação
	
	/**
	 * select count(pedido0_.id) , sum(pedido0_.valor_total) , avg(pedido0_.valor_total), max(pedido0_.valor_total) , min(pedido0_.valor_total) from pedido pedido0_
	 */
	public List<Object[]> aplicarFuncoesAgregacao() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		query.multiselect(
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")),	
				builder.avg(root.get("valorTotal")), 
				builder.max(root.get("valorTotal")), 
				builder.min(root.get("valorTotal")));	
		
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * select dayname(date(convert_tz(pedido0_.data_criacao, '+00:00', '-03:00'))) , count(pedido0_.id) from pedido pedido0_ group by date(convert_tz(pedido0_.data_criacao, '+00:00', '-03:00'))
	 */
	public List<Object[]> aplicarFuncoesNativas() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		Expression<String> timeZoneUTC = builder.literal("+00:00");
		Expression<String> timeZoneBRT = builder.literal("-03:00");
		Expression<Date> functionConvertTz = builder.function("convert_tz", Date.class, root.get("dataCriacao"), timeZoneUTC, timeZoneBRT);
		Expression<Date> functionDate = builder.function("date", Date.class, functionConvertTz);
		Expression<String> functionDayName = builder.function("dayname", String.class, functionDate);
		query.multiselect(functionDayName, builder.count(root.get("id")));	
		query.groupBy(functionDate);
		return manager.createQuery(query).getResultList();
	}
	
	
	/**
	 * select pedido0_.codigo, 
	 * 		(select count(itens1_.pedido_id) from item_pedido itens1_ where pedido0_.id = itens1_.pedido_id) 
	 * from pedido pedido0_ where
	 * 		(select count(itens2_.pedido_id) from item_pedido itens2_ where pedido0_.id = itens2_.pedido_id) > 1
	 */
	public List<Object[]> aplicarFuncaoColecao() {
		final var TOTAL_ELEMENTOS = 1;
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		Expression<Integer> expressionSize = builder.size(root.get("itens"));
		query.multiselect(root.get("codigo"), expressionSize);	
		query.where(builder.greaterThan(expressionSize, TOTAL_ELEMENTOS));
		return manager.createQuery(query).getResultList();
	}
	
	public List<Object[]> aplicarFuncaoNumero() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		query.multiselect(root.get("id"), 
				builder.abs(builder.prod(root.get("id"), -1)), //converte números negativos em números positivo
				builder.mod(root.get("id"), 2), //para testar se é possível fazer a divisão exata entre dois números ou para isolar o resto de um cálculo de divisão
				builder.sqrt(root.get("valorTotal"))); // retornam a raiz quadrada
		query.where(builder.greaterThan(builder.sqrt(root.get("valorTotal")), 10.0));
		return manager.createQuery(query).getResultList();
	}
	
	public List<Object[]> aplicarFuncaoData() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		query.multiselect(root.get("codigo"), builder.currentDate(), builder.currentTime(), builder.currentTimestamp());	
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * select cozinha0_.nome as, concat('Nome da cozinha: ', cozinha0_.nome) , length(cozinha0_.nome), locate('a', cozinha0_.nome), substring(cozinha0_.nome, 1, 3), lower(cozinha0_.nome), upper(cozinha0_.nome), 
	 * 	trim(BOTH ' ' from cozinha0_.nome) from cozinha cozinha0_
	 * 
	 * Tailandesa 
	 * Concat: Nome da cozinha: Tailandesa 
	 * Length: 10 
	 * Locate: 2 
	 * Substring: Tai 
	 * Lower: tailandesa 
	 * Upper: TAILANDESA 
	 * Trim: Tailandesa
	 */
	public List<Object[]> aplicarFuncaoString() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Cozinha.class);
		query.multiselect(root.get("nome"),
				builder.concat("Nome da cozinha: ", root.get("nome")),
				builder.length(root.get("nome")),
				builder.locate(root.get("nome"), "a"),
				builder.substring(root.get("nome"), 1, 3),
				builder.lower(root.get("nome")),
				builder.upper(root.get("nome")),
				builder.trim(root.get("nome")));	
		return manager.createQuery(query).getResultList();
	}

	/**
	 * SELECT  grupo2_.nome , permissao4_.nome, COUNT(DISTINCT usuario0_.id) FROM usuario usuario0_ 
	 * INNER JOIN usuario_grupo grupos1_ ON usuario0_.id = grupos1_.usuario_id 
	 * INNER JOIN grupo grupo2_ ON grupos1_.grupo_id = grupo2_.id 
	 * INNER JOIN grupo_permissao permissoes3_ ON grupo2_.id = permissoes3_.grupo_id 
	 * INNER JOIN permissao permissao4_ ON permissoes3_.permissao_id = permissao4_.id 
	 * GROUP BY grupo2_.nome HAVING permissao4_.nome LIKE ?
	 */
	public List<Object[]> aplicarGroupByAndHaving() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Usuario.class);
		Join<Usuario, Grupo> pathGrupo = root.join("grupos");
		Join<Grupo, Permissao> pathPermissoes = pathGrupo.join("permissoes");
		
		query.multiselect(pathGrupo.get("nome"),
				pathPermissoes.get("nome"),
				builder.countDistinct(root.get("id")));
		
		query.groupBy(pathGrupo.get("nome"));
		query.having(builder.like(pathPermissoes.get("nome"), "%a%"));
		return manager.createQuery(query).getResultList();
	}

	/**
	 * select concat(concat(cast(year(pedido0_.data_criacao) as char), '/'), monthname(pedido0_.data_criacao)), sum(pedido0_.valor_total) from pedido pedido0_ 
	 * group by year(pedido0_.data_criacao) , month(pedido0_.data_criacao)
	 */
	public List<Object[]> agruparVendasPorMesAno() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		
		Expression<Integer> functionYear = builder.function("year", Integer.class, root.get("dataCriacao"));
		Expression<Integer> functionMonth = builder.function("month", Integer.class, root.get("dataCriacao"));
		Expression<String> functionMonthName = builder.function("monthname", String.class, root.get("dataCriacao"));
		
		Expression<String> anoMesConcat = builder.concat(builder.concat(functionYear.as(String.class), "/"), functionMonthName);
		
		query.multiselect(anoMesConcat, builder.sum(root.get("valorTotal")));
		
		query.groupBy(functionYear, functionMonth);
		return manager.createQuery(query).getResultList();
	}
	
	public List<Object[]> agruparTotalVendasPorCliente() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		
		Join<Pedido, Usuario> joinCliente = root.join("cliente");	
		query.multiselect(joinCliente.get("nome"), builder.sum(root.get("valorTotal")));
		
		query.groupBy(joinCliente.get("nome"));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * select restaurant1_.nome, sum(pedido0_.valor_total) from pedido pedido0_ inner join restaurante restaurant1_ on pedido0_.restaurante_id=restaurant1_.id group by restaurant1_.nome 
	 */
	public List<Object[]> agruparTotalVendasPorRestaurante() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(Pedido.class);
		
		Join<Pedido, Restaurante> joinRestaurante = root.join("restaurante");	
		query.multiselect(joinRestaurante.get("id"), builder.sum(root.get("valorTotal")));
		
		query.groupBy(joinRestaurante.get("nome"));
		return manager.createQuery(query).getResultList();
	}
	
	/**
	 * select produto1_.nome, pedido2_.status, sum(itempedido0_.quantidade) from item_pedido itempedido0_ 
	 * inner join produto produto1_ on itempedido0_.produto_id=produto1_.id 
	 * inner join pedido pedido2_ on itempedido0_.pedido_id=pedido2_.id 
	 * group by produto1_.nome , pedido2_.status 
	 * having pedido2_.status not in  (? , ?) 
	 */
	public List<Object[]> agruparTotalProdutosVendidos() {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(Object[].class);
		var root = query.from(ItemPedido.class);

		Join<ItemPedido, Produto> joinProduto = root.join("produto");
		Join<ItemPedido, Pedido> joinPedido = root.join("pedido");
		query.multiselect(joinProduto.get("nome"), joinPedido.get("status"), builder.sum(root.get("quantidade")));
		
		query.groupBy(joinProduto.get("nome"), joinPedido.get("status"));
		query.having(builder.not(joinPedido.get("status").in(Arrays.asList(StatusPedido.CANCELADO, StatusPedido.CRIADO))));
		return manager.createQuery(query).getResultList();
	}

	
}






