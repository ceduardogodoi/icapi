package br.com.condor.marketing.printer.impressao_cartaz_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.condor.marketing.printer.impressao_cartaz_api.model.Loja;
import br.com.condor.marketing.printer.impressao_cartaz_api.services.LojasService;

@RestController
@RequestMapping("/lojas")
public class LojasController {

	@Autowired
	private LojasService lojasService;
	
	// http://localhost:8080/lojas
	@Timed
	@GetMapping
	public List<Loja> getLojas() {
		return lojasService.getLojas();
	}
	
}
