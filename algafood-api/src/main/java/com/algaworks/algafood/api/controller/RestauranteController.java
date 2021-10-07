package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOResumoAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOResumoListAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.RestauranteControllerOpenApi;
import com.algaworks.algafood.api.exceptionhandler.ValidatorException;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.RestauranteDTOResumo;
import com.algaworks.algafood.api.model.RestauranteDTOResumoList;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.exception.CidadeNotFoundException;
import com.algaworks.algafood.domain.exception.CozinhaNotFoundException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteDTOResumoListAssembler restauranteDTOResumoListAssembler;
	
	@Autowired
	private RestauranteInputDTODisassembler restauranteInputDTODisassembler;
	
	@Autowired
	private RestauranteDTOResumoAssembler restauranteDTOResumoAssembler;
	
//	@JsonView(RestauranteView.Resumo.class) Não funciona corretamente com HATEOAS
	@Override
	@GetMapping
	public CollectionModel<RestauranteDTOResumoList> findAll() {
		return restauranteDTOResumoListAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
//	@JsonView(RestauranteView.ApenasNomeEId.class)
	@Override
	@GetMapping(params = "projecao=apenas-nome")//desenvolvimento:8080/restaurantes?projecao=apenas-nome
	public CollectionModel<RestauranteDTOResumo> findAllApenasNome() {
		return restauranteDTOResumoAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@Override
	@GetMapping("/{restauranteId}")
	public RestauranteDTO findById(@PathVariable Long restauranteId) {
		return restauranteDTOAssembler.toModel(cadastroRestauranteService.buscar(restauranteId));
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO save(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		try {
			var restaurante = restauranteInputDTODisassembler.toDomainObject(restauranteInputDTO);
			return restauranteDTOAssembler.toModel(cadastroRestauranteService.save(restaurante));
		} catch (CozinhaNotFoundException | CidadeNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable Long restauranteId){
		cadastroRestauranteService.delete(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{restauranteId}")
	public RestauranteDTO udpate(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		var restauranteBD = cadastroRestauranteService.buscar(restauranteId);
//		BeanUtils.copyProperties(restaurante, restauranteBD, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		
		restauranteInputDTODisassembler.copyToDomainObject(restauranteInputDTO, restauranteBD);
		try {
			return restauranteDTOAssembler.toModel(cadastroRestauranteService.save(restauranteBD));
		} catch (CozinhaNotFoundException | CidadeNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PatchMapping("/{restauranteId}")
	public RestauranteDTO partialMerge(@PathVariable Long restauranteId, @RequestBody Map<String, Object> fieldsRestaurante, HttpServletRequest httpServletRequest) {
		var restauranteBD = cadastroRestauranteService.buscar(restauranteId);
		merge(fieldsRestaurante, restauranteBD, httpServletRequest);
		validate(restauranteBD, "restaurante");
		
		BeanUtils.copyProperties(restauranteBD, restauranteBD, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		try {
			return restauranteDTOAssembler.toModel(cadastroRestauranteService.save(restauranteBD));
		} catch (CozinhaNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping("/{restauranteId}/ativar")//PUT pois é idempotente
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarAll(@RequestBody List<Long> ids) {//payload json [1,2,3]
		try {
			cadastroRestauranteService.ativar(ids);
		} catch (RestauranteNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@DeleteMapping("/{restauranteId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.desativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desativarAll(@RequestBody List<Long> ids) {
		try {
			cadastroRestauranteService.desativar(ids);
		} catch (RestauranteNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{restauranteId}/abertura")//usando a linguagem de negócio para criar recurso
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		var bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		this.validator.validate(restaurante, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new ValidatorException(bindingResult);//Nossa ExceptionHandler irá tratar
		}
	}
	
	private void merge(Map<String, Object> fieldsRestaurante, Restaurante restauranteBD, HttpServletRequest httpServletRequest) {
		var servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
		try {
			var objectMapper = new ObjectMapper();			
			var restauranteSource = objectMapper.convertValue(fieldsRestaurante, RestauranteInputDTO.class);
			
			fieldsRestaurante.forEach((namePropertie, valuePropertie) -> {
				var fieldDTO = ReflectionUtils.findField(RestauranteInputDTO.class, namePropertie);//private java.lang.String com.algaworks.algafood.api.model.input.RestauranteInputDTO.nome
				fieldDTO.setAccessible(true);
				Object valueField = ReflectionUtils.getField(fieldDTO, restauranteSource);
				var field = ReflectionUtils.findField(Restaurante.class, namePropertie);//private java.lang.String com.algaworks.algafood.domain.model.Restaurante.nome
				field.setAccessible(true);
				ReflectionUtils.setField(field, restauranteBD, valueField);
			});
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, servletServerHttpRequest);
		}
	}
	
	
//	private void merge(Map<String, Object> fieldsRestaurante, Restaurante restauranteBD, HttpServletRequest httpServletRequest) {
//		var servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
//		try {
//			var objectMapper = new ObjectMapper();// SpringBoot nao autoconfigura esse objeto, pois estamos instanciando. Assim temos que fazer a configuração
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//			var restauranteSource = objectMapper.convertValue(fieldsRestaurante, Restaurante.class);
//
//			fieldsRestaurante.forEach((namePropertie, valuePropertie) -> {
//				var field = ReflectionUtils.findField(Restaurante.class, namePropertie);
//				field.setAccessible(true);
//				Object valueField = ReflectionUtils.getField(field, restauranteSource);
//				ReflectionUtils.setField(field, restauranteBD, valueField);
//			});
//		} catch (IllegalArgumentException ex) {
//			ex.printStackTrace();
//			Throwable rootCause = ExceptionUtils.getRootCause(ex);
//			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, servletServerHttpRequest);
//		}
//	}
	
	
//	@GetMapping
//	public ResponseEntity<?> findAll() {
//		var dtos = restauranteDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
//		return ResponseEntity.ok()
//				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")//permitindo todas origens acessarem
//				.body(dtos);
//	}
	
	
//	@GetMapping
//	public MappingJacksonValue findAll(@RequestParam(required = false) String campos) {//desenvolvimento:8080/restaurantes?campos=id,nome
//		var dtos = restauranteDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
//		var mappingJacksonValue = new MappingJacksonValue(dtos);
//		var simpleFilterProvider = new SimpleFilterProvider();
//		var sbpf = StringUtils.isNotBlank(campos) ? SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")) : SimpleBeanPropertyFilter.serializeAll(); 
//		simpleFilterProvider.addFilter("restauranteFilter", sbpf);
//		mappingJacksonValue.setFilters(simpleFilterProvider);
//		return mappingJacksonValue;
//	}
	
	
	//Dinâmico
//	@GetMapping
//	public MappingJacksonValue findAllApenasNomeEndereco(@RequestParam(required = false) String projecao) {
//		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(restauranteDTOAssembler.toCollectionDTO(restauranteRepository.findAll()));
//		
//		if ("apenas-nome".equals(projecao)) {
//			mappingJacksonValue.setSerializationView(RestauranteView.ApenasNomeEId.class);
//		}else if("resumo".equals(projecao)) {
//			mappingJacksonValue.setSerializationView(RestauranteView.Resumo.class);
//		}
//		
//		return mappingJacksonValue;
//	}
}





