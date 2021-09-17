package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

//@RestController
//@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private CozinhaRepository cozihaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/findByNome")
	public List<Cozinha> findByNome(@RequestParam("nome") String nome){//@RequestParam is optinal
		return cozihaRepository.findByNome(nome);
	}
	
	@GetMapping("/findByRestauranteTaxaFrete")
	public List<Restaurante> findByRestauranteTaxaFreteContaining(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/findByRestauranteAndCozinha")
	public List<Restaurante> findByRestauranteAndCozinha(String nome, Long idCozinha){
		return restauranteRepository.findByNomeContainingAndCozinhaId(nome, idCozinha);
	}
	
	@GetMapping("/findByFirst")
	public Optional<Restaurante> findFirstByNomeContaining(String nome){
		return restauranteRepository.findFirstByNomeContaining(nome);
	}
	
	@GetMapping("/findByTop2")
	public List<Restaurante> findTop2ByNomeContaining(String nome){
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/countByCozinhaId")
	public int countByCozinhaId(Long idCozinha){
		return restauranteRepository.countByCozinhaId(idCozinha);
	}
	
	@GetMapping("/existsByNomeContaining")
	public boolean existsByNomeContaining(String nome){
		return restauranteRepository.existsByNomeContaining(nome);
	}
	
	@GetMapping("/buscarRestaurantesPorNomeNaCozinha")
	public List<Restaurante> buscarRestaurantesPorNomeNaCozinha(String nome, Long idCozinha){
		return restauranteRepository.buscarRestaurantesPorNomeNaCozinha(nome, idCozinha);
	}
	
	@GetMapping("/customRepository")
	public List<Restaurante> buscarRestaurantesPorNomeNaCozinha(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findWithPredicates(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/usingSpecification")
	public List<Restaurante> usingSpecification(String nome){
//		var comTaxaFreteGratis = new RestauranteComFreteGratisSpec();
		return restauranteRepository.comFreteGratis(nome);
	}
	
	@GetMapping("/myRepository")
	public Optional<Restaurante> myRepositoryCustomRepository(){
		return restauranteRepository.findFirst();
	}
	
	@GetMapping("/myRepository2")
	public Optional<Cozinha> myRepositoryCustomRepositoryCozinha(){
		return cozihaRepository.findFirst();
	}

}
