package com.algaworks.algafood.core.springfox;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.controller.openapi.model.PageCozinhaDTOOpenApi;
import com.algaworks.algafood.api.controller.openapi.model.PagePedidoDTOOpenApi;
import com.algaworks.algafood.api.controller.openapi.model.PageableRequestOpenApi;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)//pegará as anotações do bean validation e usará na documentação p informar se o param é null ou não
public class SpringFoxConfig implements WebMvcConfigurer {
	
	/**
	 *  Docket representa a configuração da API para gerar a definição usando a especificação openAPI
	 *  desenvolvimento:8080/v2/api-docs
	 */
	
	@Bean
	public Docket getDocket() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood"))
//							RequestHandlerSelectors.basePackage("com.algaworks.other")))//Uso Predicates.and() especifico quais controladores serão gerados a definição
//					.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
				.apiInfo(buildApiInfo())
				.tags(buscarTags()[0], buscarTags())
				.useDefaultResponseMessages(false)//desabilitando códigos de status gerados automaticamente na doc (401,403,404)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
				.directModelSubstitute(Pageable.class, PageableRequestOpenApi.class)//alterando somente para fins de doc
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaDTO.class), PageCozinhaDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, PedidoResumoDTO.class), PagePedidoDTOOpenApi.class))
				.ignoredParameterTypes(ServletWebRequest.class, Optional.class)//estava aparecendo na doc
				//Adicionando parametros do Sqquigly que não aparece na doc(config global)
//				.globalOperationParameters(Arrays.asList(
//						new ParameterBuilder().name(SquigglyConfig.NAME_QUERY_PARAMETER_CAMPOS)
//						.description("Nomes das propriedades para filtrar na resposta. Exemplo: nome,telefone")
//						.parameterType("query")
//						.modelRef(new ModelRef("string"))
//						.build()))
				.additionalModels(typeResolver.resolve(Problem.class));//como a representação do problema não está em nenhum controller, ele não consegue pegar
	}


	/**
	 * Servindo arquivos do springfox-swagger-ui.jar
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")//pegue subpasta dentro de subpasta tambem
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	public ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood")
				.description("API aberta para clientes e restaurantes")
				.contact(new Contact("Douglas", "www.douglas.com", "douglas@email.com"))
				.version("001")
				.build();
	}
	
	private Tag[] buscarTags() {
		return Arrays.asList(
				new Tag("Cidades", "Gerencia as cidades"),
				new Tag("Cozinhas", "Gerencia as cozinhas"), 
				new Tag("Estados", "Gerencia os Estados"), 
				new Tag("Estatísticas", "Relatório de vendas"), 
				new Tag("Formas de pagamento", "Gerencia formas de pagamento"),
				new Tag("Grupos", "Gerencia os grupos de usuários"),
				new Tag("Pedidos", "Gerencia pedidos"),
				new Tag("Produtos", "Gerencia produtos"),
				new Tag("Restaurantes", "Gerencia os restaurantes"),
				new Tag("Usuários", "Gerencia os clientes")).toArray(new Tag[0]);
				
	}
	
	/**
	 * Os status 2xx são sobrescritos pelo SpringFox, pois ele dá preferência aos métodos.
	 * Configuramos aqui apenas status 4xx e 5xx 
	 */
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Erro interno do servidor")
				.responseModel(new ModelRef("Problema")) //referenciado esse status com o modelo do problema retornado
				.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value()).message("Recurso não possui representação que poderia ser aceita pelo consumidor").build());
	}
	
	private List<ResponseMessage> globalPostResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Erro interno do servidor").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value()).message("Recurso não possui representação que poderia ser aceita pelo consumidor").build(),
				//caso tente mandar no contentType um xml, ou outra representação que a API não suporte, por exemplo.
				new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).message("Requisição recusada porque o corpo está em um formato não suportado").responseModel(new ModelRef("Problema")).build(), 
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("Requisição inválida (erro do cliente)").responseModel(new ModelRef("Problema")).build());
	}
	
	private List<ResponseMessage> globalPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Erro interno do servidor").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value()).message("Recurso não possui representação que poderia ser aceita pelo consumidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).message("Requisição recusada porque o corpo está em um formato não suportado").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("Requisição inválida (erro do cliente)").responseModel(new ModelRef("Problema")).build());
	}
	
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Erro interno do servidor").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.CONFLICT.value()).message("Recurso não possui pode ser deletado").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("Requisição inválida (erro do cliente)").responseModel(new ModelRef("Problema")).build());
	}

}
