package com.algaworks.algafood;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * TEstes de API
 * @author dougl
 *
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaRestAssuredIT {
	
	private static final int ID_COZINHA_INEXISTENTE = 12003;
	private Cozinha cozinhaTailandesa;
	private Cozinha cozinhaIndiana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCozinhaJaponesa;

	@LocalServerPort
	private int port;
	
//	@Autowired
//	private Flyway flyway;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@BeforeEach//executado antes de cada teste
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
//		flyway.migrate();
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	void deveRetornar200AoConsultarCozinhas() {
		RestAssured.given()
			.contentType(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	void deveConter2Cozinhas() {
		RestAssured.given()
			.contentType(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("nome", Matchers.hasItems(this.cozinhaTailandesa.getNome(), this.cozinhaIndiana.getNome()))
			.body("", Matchers.hasSize(this.quantidadeCozinhasCadastradas));
			
	}
	
	@Test
	void deveRetornar201AoSalvar() {
		this.jsonCozinhaJaponesa = ResourceUtils.getContentFromResource("/json/cozinha-japonesa.json"); 
		System.out.println(jsonCozinhaJaponesa);
		
		RestAssured.given()
			.body(jsonCozinhaJaponesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void mustReturnStatusAndBodyCorrectsWhenConsultCozinhaExisting() {
		RestAssured.given()
			.pathParam("cozinhaId", this.cozinhaTailandesa.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(this.cozinhaTailandesa.getNome()));
	}
	
	@Test
	void mustReturnStatusCorrectWhenConsultCozinhaNonexisting() {
		RestAssured.given()
			.pathParam("cozinhaId", ID_COZINHA_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		this.cozinhaTailandesa = cadastroCozinhaService.save(new Cozinha("Tailandesa"));
		this.cozinhaIndiana = cadastroCozinhaService.save(new Cozinha("Indiana"));
		this.quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
}
