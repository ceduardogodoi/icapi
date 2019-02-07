package br.com.condor.marketing.printer.impressao_cartaz_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.condor.marketing.printer.impressao_cartaz_api.model.Produto;
import br.com.condor.marketing.printer.impressao_cartaz_api.repositories.ProdutoOrgaoRepository;
import br.com.condor.marketing.printer.impressao_cartaz_api.repositories.ProdutosRepository;

@Service
public class ProdutosService {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private ProdutoOrgaoRepository produtoOrgaoRepository;

	public List<Produto> getProdutosById(List<Long> ids) {
		return produtosRepository.findAllById(ids);
	}

	public List<Long> getOnlyProdutosFromLojaMix(String loja, List<Long> produtosIds) {
		return produtoOrgaoRepository.findProdutosFromLojaMix(Long.valueOf(loja), produtosIds);
	}

}
