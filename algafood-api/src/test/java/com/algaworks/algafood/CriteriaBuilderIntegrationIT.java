package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algafood.domain.infrastructure.repository.CriteriaBuilderConsultas;
import com.algaworks.algafood.domain.model.Cozinha;

@SpringBootTest
class CriteriaBuilderIntegrationIT {
	
	@Autowired
	private CriteriaBuilderConsultas criteriaBuilderConsultas;
	
	@Test
	void deveRetornarCozinhasDistintas() {
		//cenario
		//acao
		var cozinhas = criteriaBuilderConsultas.usarDistinct();
		//validacao
		assertThat(cozinhas).isNotEmpty();
	}
	
	@Test
	void deveRetornarCozinhasOrdenadas() {
		assertThat(criteriaBuilderConsultas.buscarCozinhasOrdenadas()).isNotEmpty();
	}
	
	@Test
	void deveRetornarCozinhasProjetadasDTO() {
		var dtos = criteriaBuilderConsultas.buscarCozinhasProjetadasDTO();
		assertThat(dtos).isNotEmpty();
	}
	
//	@Test
//	void deveRetornarCozinhasProjetadasTuple() {
//		var dtos = criteriaBuilderConsultas.buscarCozinhasProjetadasTuple();
//		assertThat(dtos).isNotEmpty();
//	}
	
	@Test
	void projetarOResultado() {
		//[[1, Tailandesa], [2, Indiana], [3, Argentina], [4, Brasileira], [5, Brasuca], [6, Brasileia], null, null, null, null]
		var objects = criteriaBuilderConsultas.projetarOResultado();
		objects.forEach(p ->  System.out.println("ID: " + p[0] + " NOme: " + p[1]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void deveRetornarSomenteNome() {
		var id = 1L;
		var dtos = criteriaBuilderConsultas.buscarSomenteNome(id);
		assertThat(dtos).isNotEmpty();
	}
	
	@Test
	void deveRetornarDeterminadasCozinhas() {
		var dtos = criteriaBuilderConsultas.buscarCozinhasExpressionIn(Arrays.asList(1L, 2L));
		assertThat(dtos).isNotEmpty();
	}
	
	@Test
	void deveRetornarCozinhasCase() {
		var objects = criteriaBuilderConsultas.buscarCozinhasExpressionCase();
		objects.forEach(p ->  System.out.println("Código: " + p[1] + " Status: " + p[2]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void deveRetornarCozinhasDiferentesDe() {
		var cozinhas = criteriaBuilderConsultas.buscarCozinhasDiferentesDe(new Cozinha(1L));
		assertThat(cozinhas).isNotEmpty();
	}
	
	@Test
	void deveRetornarCozinhasDiferentesDeCollection() {
		var cozinhas = criteriaBuilderConsultas.buscarCozinhasDiferentesDe(Arrays.asList(new Cozinha(1L), new Cozinha(2L)));
		cozinhas.forEach(p ->  System.out.println("Nome: " + p.getNome()));
		assertThat(cozinhas).isNotEmpty();
	}
	
	@Test
	void deveRetornarPedidosEntre() {
		OffsetDateTime dataInicio = OffsetDateTime.of(LocalDate.of(2021, 8, 12), LocalTime.of(22, 42, 37, 0), ZoneOffset.UTC);
		OffsetDateTime dataFim = OffsetDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(22, 42, 37, 0), ZoneOffset.UTC);
		var cozinhas = criteriaBuilderConsultas.buscarPedidosEntre(dataInicio, dataFim);
		assertThat(cozinhas.size()).isEqualTo(2);
	}
	
	@Test
	void deveRetornarPedidosGreaterLessThan() {
		OffsetDateTime dataInicio = OffsetDateTime.of(LocalDate.of(2021, 8, 12), LocalTime.of(22, 42, 37, 0), ZoneOffset.UTC);
		OffsetDateTime dataFim = OffsetDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(22, 42, 37, 0), ZoneOffset.UTC);
		var cozinhas = criteriaBuilderConsultas.buscarPedidosEntre(dataInicio, dataFim);
		assertThat(cozinhas.size()).isEqualTo(2);
	}
	
	@Test
	void deveRetornarCozinhasSemRestaurantesIsEmpty() {
		var cozinhas = criteriaBuilderConsultas.buscarCozinhasSemRestauranteIsEmpty();
		cozinhas.forEach(p ->  System.out.println("Nome: " + p.getNome()));
		assertThat(cozinhas).isNotEmpty();
	}
	
	@Test
	void deveRetornarCozinhasComNomeNull() {
		var cozinhas = criteriaBuilderConsultas.buscarCozinhasComNomeNull();
		cozinhas.forEach(p ->  System.out.println("Nome: " + p.getNome()));
		assertThat(cozinhas).isEmpty();
	}
	
	@Test
	void deveRetornarCozinhasComNomeContaining() {
		var cozinhas = criteriaBuilderConsultas.buscarCozinhasComNomeContaining("RA");
		cozinhas.forEach(p ->  System.out.println("Nome: " + p.getNome()));
		assertThat(cozinhas).isNotEmpty();
	}
	
	@Test
	void aplicarFuncaoAgregacao() {
		var objects = criteriaBuilderConsultas.aplicarFuncoesAgregacao();
		objects.forEach(o ->  {
			System.out.println("Count: " + o[0]);
			System.out.println("Sum: " + o[1]);
			System.out.println("Avg: " + o[2]);
			System.out.println("Max: " + o[3]);
			System.out.println("Min: " + o[4]);
		});
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void aplicarFuncaoDayName() {
		var objects = criteriaBuilderConsultas.aplicarFuncoesNativas();
		objects.forEach(o -> System.out.println("Data: " + o[0] + " Total vendas: " + o[1]));
		assertThat(objects).isNotEmpty();
	}

	@Test
	void aplicarFuncaoColecao() {
		var objects = criteriaBuilderConsultas.aplicarFuncaoColecao();
		objects.forEach(o -> System.out.println("Codigo: " + o[0] + " Itens: " + o[1]));
		assertThat(objects).isNotEmpty();
	}
	@Test
	void aplicarFuncaoNumeros() {
		var objects = criteriaBuilderConsultas.aplicarFuncaoNumero();
		objects.forEach(o -> System.out.println("Id: " + o[0] + " Abs: " + o[1] + " Mod: " + o[2] + " Sqrt: " + o[3]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void aplicarFuncaoDatas() {
		var objects = criteriaBuilderConsultas.aplicarFuncaoData();
		objects.forEach(o -> System.out.println(" current date: " + o[1] + " current time: " + o[2] + " current timestamp: " + o[3]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void aplicarFuncaoString() {
		var objects = criteriaBuilderConsultas.aplicarFuncaoString();
		objects.forEach(o -> System.out.println("Nome: " + o[0] + " Concat: " + o[1] + " Length: " + o[2] + " Locate: " + o[3]+ " Substring: " + o[4]+ " Lower: " + o[5]+ " Upper: " + o[6]+ " Trim: " + o[7]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void aplicarGroupByAndHaving() {
		var objects = criteriaBuilderConsultas.aplicarGroupByAndHaving();
		objects.forEach(o -> System.out.println("Nome grupo: " + o[0] + "Permissao: " + o[1] + " qtde: " + o[2]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void agruparVendasPorMesAno() {
		var objects = criteriaBuilderConsultas.agruparVendasPorMesAno();
		objects.forEach(o -> System.out.println("Ano/mês: " +  o[0] + " Total: " + o[1]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void agruparTotalVendasPorCliente() {
		var objects = criteriaBuilderConsultas.agruparTotalVendasPorCliente();
		objects.forEach(o -> System.out.println("Cliente: " +  o[0] + " Total: " + o[1]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void agruparTotalVendasPorRestaurante() {
		var objects = criteriaBuilderConsultas.agruparTotalVendasPorRestaurante();
		objects.forEach(o -> System.out.println("Restaurante: " +  o[0] + " Total: " + o[1]));
		assertThat(objects).isNotEmpty();
	}
	
	@Test
	void agruparTotalProdutosVendidos() {
		var objects = criteriaBuilderConsultas.agruparTotalProdutosVendidos();
		objects.forEach(o -> System.out.println(o[0]  + " Status: " + o[1]+ " Total: " + o[2]));
		assertThat(objects).isNotEmpty();
	}

}
