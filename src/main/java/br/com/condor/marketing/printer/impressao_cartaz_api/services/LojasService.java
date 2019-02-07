package br.com.condor.marketing.printer.impressao_cartaz_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.condor.marketing.printer.impressao_cartaz_api.model.Loja;
import br.com.condor.marketing.printer.impressao_cartaz_api.repositories.LojasRepository;

@Service
public class LojasService {

	@Autowired
	private LojasRepository lojasRepository;
	
	public List<Loja> getLojas() {
		return lojasRepository.findAllByOrderByCodigoLojaAsc();
	}
	
}
