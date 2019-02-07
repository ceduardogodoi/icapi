package br.com.condor.marketing.printer.impressao_cartaz_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.condor.marketing.printer.impressao_cartaz_api.model.ProdutoOrgao;

public interface ProdutoOrgaoRepository extends JpaRepository<ProdutoOrgao, Long> {

	@Query("SELECT po.produto.codigoProduto FROM ProdutoOrgao po WHERE po.produto.codigoProduto IN(?2) AND po.loja.codigoLoja = ?1 AND po.situacaoVenda = 1")
	List<Long> findProdutosFromLojaMix(@Param(value = "loja") Long loja, List<Long> produtosId);

}
