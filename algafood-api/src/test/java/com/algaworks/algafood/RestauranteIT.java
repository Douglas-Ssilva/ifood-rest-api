package com.algaworks.algafood;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.api.exceptionhandler.ProblemType;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestauranteIT {
	
	private String jsonRestauranteInez;
	private Restaurante restaurantePizzarella;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CadastroRestauranteService restauranteService; 
	
	@Autowired
	private CadastroCozinhaService cozinhaService;
	private String jsonRestauranteInezCozinhaInexistente;
	private String jsonRestauranteInezSemFrete;
	private String jsonRestauranteInezCozinhaNull; 
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		jsonRestauranteInezSemFrete = ResourceUtils.getContentFromResource("/json/restaurante-inez-sem-taxa-frete.json");
		jsonRestauranteInez = ResourceUtils.getContentFromResource("/json/restaurante-inez.json");
		jsonRestauranteInezCozinhaInexistente = ResourceUtils.getContentFromResource("/json/restaurante-inez-com-cozinha-inexistente.json");
		jsonRestauranteInezCozinhaNull = ResourceUtils.getContentFromResource("/json/restaurante-inez-com-cozinha-null.json");

		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	void deveRetornar200AoConsultarRestaurantes() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	void deveRetornar201AoInserirCozinha() {

		RestAssured.given()
			.body(jsonRestauranteInez)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
			
	}
	
	@Test
	void deveRetornar200AoConsultarRestauranteExistente() {
		RestAssured.given()
			.pathParam("restauranteId", restaurantePizzarella.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value());
		
	}
	
	@Test
	void deveRetornar400AoPersistirRestauranteComCozinhaInexistente() {
		
		RestAssured.given()
		.body(jsonRestauranteInezCozinhaInexistente)
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.when()
		.post()
		.then()
		.statusCode(HttpStatus.BAD_REQUEST.value())
		.body("title", Matchers.equalTo(ProblemType.ERROR_BUSINESS.getTitle()));
	}
	
	@Test
	void deveRetornar400AoPersistirRestauranteComCozinhaNull() {
		
		RestAssured.given()
			.body(jsonRestauranteInezCozinhaNull)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", Matchers.equalTo(ProblemType.INVALID_DATAS.getTitle()));
	}
	
	@Test
	void deveRetornar400AoPersistirRestauranteSemFrete() {
		
		RestAssured.given()
			.body(jsonRestauranteInezSemFrete)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
		 	.body("title", Matchers.equalTo(ProblemType.INVALID_DATAS.getTitle()));
	}

	private void prepararDados() {
		var cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaService.save(cozinhaBrasileira);

        var cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaService.save(cozinhaAmericana);
		
        var comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteService.save(comidaMineiraRestaurante);
        
        restaurantePizzarella = new Restaurante();
        restaurantePizzarella.setNome("Pizzarela");
        restaurantePizzarella.setCozinha(cozinhaBrasileira);
        restaurantePizzarella.setTaxaFrete(BigDecimal.valueOf(300));
        restaurantePizzarella = restauranteService.save(restaurantePizzarella);
	}
	
}
