package br.com.condor.marketing.printer.impressao_cartaz_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.condor.marketing.printer.impressao_cartaz_api.model.Loja;

@Repository
public interface LojasRepository extends JpaRepository<Loja, Long> {
	
	List<Loja> findAllByOrderByCodigoLojaAsc();

}
