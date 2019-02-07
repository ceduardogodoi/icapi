package br.com.condor.marketing.printer.impressao_cartaz_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.condor.marketing.printer.impressao_cartaz_api.model.Produto;

@Repository
public interface ProdutosRepository extends JpaRepository<Produto, Long> {

}
