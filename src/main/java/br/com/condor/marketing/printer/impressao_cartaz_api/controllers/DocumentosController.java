package br.com.condor.marketing.printer.impressao_cartaz_api.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.condor.marketing.printer.impressao_cartaz_api.model.Documento;
import br.com.condor.marketing.printer.impressao_cartaz_api.services.DocumentosService;

@RestController
@RequestMapping("/documentos")
public class DocumentosController {

	@Autowired
	private DocumentosService documentosService;

	@Timed
	@GetMapping
	public List<Documento> getDocumentosByFilters(
			@RequestParam(required = false) String loja,
			@RequestParam(required = false) String tipoTabloide,
			@RequestParam(required = false) String codigoTabloide,
			@RequestParam(required = false) String papel,
			@RequestParam(required = false) String hostEan) {
		return documentosService.getDocumentosByFilters(tipoTabloide, codigoTabloide, loja, papel, hostEan);
	}

	@Timed
	@GetMapping("/tabloides")
	public List<String> getTabloides(@RequestParam(required = false) String loja) {
		return documentosService.getTabloides(loja);
	}

	@Timed
	@GetMapping("/papeis")
	public List<String> getPapeis(@RequestParam(required = false) String loja) {
		return documentosService.getPapeis(loja);
	}

	@Timed
	@GetMapping("/download")
	public void download(
			HttpServletResponse response,
			@RequestParam String tipoTabloide,
			@RequestParam String codigoTabloide,
			@RequestParam(required = false) String regiao,
			@RequestParam(required = false) String loja,
			@RequestParam String documento) throws IOException {

		documentosService.download(response, tipoTabloide, codigoTabloide, regiao, loja, documento);
	}

}
