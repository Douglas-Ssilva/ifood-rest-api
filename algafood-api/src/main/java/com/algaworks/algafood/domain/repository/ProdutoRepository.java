package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {
	
    Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

    @Query("FROM Produto p WHERE p.ativo = true AND p.restaurante = :restaurante")
	List<Produto> findByProdutosAtivos(Restaurante restaurante);

    @Query("SELECT f FROM FotoProduto f JOIN f.produto p WHERE p.restaurante.id = :restauranteId AND p.id = :produtoId")
	Optional<FotoProduto> findFotoProduto(Long produtoId, Long restauranteId);
	
}
