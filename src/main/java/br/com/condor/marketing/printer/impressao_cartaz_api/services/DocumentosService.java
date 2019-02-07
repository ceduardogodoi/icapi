package br.com.condor.marketing.printer.impressao_cartaz_api.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import br.com.condor.marketing.printer.impressao_cartaz_api.io.DirUtils;
import br.com.condor.marketing.printer.impressao_cartaz_api.io.FileUtils;
import br.com.condor.marketing.printer.impressao_cartaz_api.model.Documento;
import br.com.condor.marketing.printer.impressao_cartaz_api.model.Produto;

@Transactional(readOnly = true)
@Service
public class DocumentosService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentosService.class);

	@Autowired
	private ProdutosService produtosService;

	private List<String> getAllDocumentosNames() {
		try {
			return Files.walk(DirUtils.MKT_PRINTER_PATH_DIR)
					.filter(path -> !path.toString().contains("._")) // filtra arquivos que não são ocultos
					.filter(path -> path.toString().endsWith(".pdf")) // filtra somente arquivos .pdf
					.map(path -> path.toString().substring(DirUtils.MKT_PRINTER_STR_LEN)) // remove o caminho da impressora.
					.collect(Collectors.toList());
		} catch (IOException e) {
			logger.error("Erro '{}'. Stacktrace: {}", e.getMessage(), ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	private List<Documento> getAllDocumentos() {
		List<Documento> documentos = new ArrayList<>();

		// Criando o objeto Documento a partir do seu Nome.
		for (String documentoName : getAllDocumentosNames()) {
			String[] split = documentoName.replace("\\", "__").split("__");

			// Documentos de Região
			if (split[0].toLowerCase().contains("reg")) {
				String dataFinal = split[4]; // 12122018

				if (isVencido(dataFinal))
					continue;

				String regiao = split[0];
				String tipoTabloide = split[1].substring(0, split[1].lastIndexOf(" ")); // Tabloide Geral
				String codigoTabloide = split[1].substring(split[1].length() - 3); // 365
				String papel = split[2]; // A5
				String dataInicial = split[3]; // 28112018
				String ean = split[5]; // 07896534401528
				String host = split[6]; // 0000582205

				// Documento está na pasta da Região então loja = null e geral = false
				documentos.add(new Documento(tipoTabloide, codigoTabloide, null, papel, dataInicial, dataFinal, ean, host, regiao, false, false));
				continue;
			} else if (split[0].toLowerCase().contains("geral")) {
				// Documentos Geral
				String dataFinal = split[4]; // 15012019

				if (isVencido(dataFinal))
					continue;

				String tipoTabloide = split[1].substring(0, split[1].lastIndexOf(" ")); // Tabloide Guerrilha
				String codigoTabloide = split[1].substring(split[1].length() - 3); // 012
				String papel = split[2]; // A5
				String dataInicial = split[3]; // 14012019
				String ean = split[5]; // 07891027193201
				String host = split[6]; // 0000686725

				// Documento está na pasta da Loja, então geral = true
				documentos.add(new Documento(tipoTabloide, codigoTabloide, null, papel, dataInicial, dataFinal, ean, host, null, false, true));
				continue;
			} else {
				// Documentos Individuais
				String dataFinal = split[5]; // 15012019

				if (isVencido(dataFinal))
					continue;

				String tipoTabloide = split[1].substring(0, split[1].lastIndexOf(" ")); // Tabloide Guerrilha
				String codigoTabloide = split[1].substring(split[1].length() - 3); // 012
				String loja = split[2]; // 003
				String papel = split[3]; // A5
				String dataInicial = split[4]; // 14012019
				String ean = split[6]; // 07891027193201
				String host = split[7]; // 0000686725

				// Documento está na pasta da Loja, então regiao = null
				documentos.add(new Documento(tipoTabloide, codigoTabloide, loja, papel, dataInicial, dataFinal, ean, host, null, true, false));
			}
		}

		return documentos;
	}

	private boolean isVencido(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate dtVencimento = LocalDate.parse(data, dtf);

		return LocalDate.now().isEqual(dtVencimento) || LocalDate.now().isAfter(dtVencimento);
	}

	private List<Documento> filterByTabloide(String tipoTabloide, String codigoTabloide, List<Documento> documentos) {
		return documentos.stream()
			.filter(documento -> documento.getTipoTabloide().toLowerCase().contains(tipoTabloide.toLowerCase()))
			.filter(documento -> documento.getCodigoTabloide().toLowerCase().contains(codigoTabloide))
			.collect(Collectors.toList());
	}

	private List<Documento> filterByLoja(String loja) {
		String paddedLoja = StringUtils.leftPad(loja, 3, '0');

		return getAllDocumentos().stream()
			.filter(Documento::isIndividual)
			.filter(doc -> doc.getLoja().equalsIgnoreCase(paddedLoja))
			.distinct()
			.collect(Collectors.toList());
	}

	private List<Documento> filterByRegiao(String regiao, List<Documento> documentos) {
		if (regiao != null) {
			List<Documento> documentosRegiao = getAllDocumentos().stream()
					.filter(doc -> doc.getRegiao() != null && doc.getRegiao().equalsIgnoreCase(regiao))
					.collect(Collectors.toList());

			for (Documento docRegiao : documentosRegiao) {
				boolean noneMatch = documentos.stream().noneMatch(doc -> docRegiao.getHost().equals(doc.getHost()));

				if (noneMatch) {
					documentos.add(docRegiao);
				}
			}
		}

		return documentos;
	}

	private List<Documento> filterByGeral(List<Documento> documentos) {
		List<Documento> documentosGeral = getAllDocumentos().stream()
				.filter(Documento::isGeral)
				.collect(Collectors.toList());

		for (Documento docGeral : documentosGeral) {
			boolean noneMatch = documentos.stream().noneMatch(doc -> docGeral.getHost().equals(doc.getHost()));

			if (noneMatch) {
				documentos.add(docGeral);
			}
		}

		return documentos;
	}

	private List<Documento> filterByPapel(String papel, List<Documento> documentos) {
		return documentos.stream()
			.filter(documento -> documento.getPapel().equalsIgnoreCase(papel))
			.collect(Collectors.toList());
	}

	private List<Documento> filterByHostEan(String hostEan, List<Documento> documentos) {
		List<Documento> allMatches = new ArrayList<>();

		boolean containHost = documentos.stream()
				.anyMatch(documento -> documento.getHost().contains(hostEan));
		if (containHost) {
			List<Documento> hostMatches = documentos.stream()
				.filter(documento -> documento.getHost().contains(hostEan))
				.collect(Collectors.toList());

			allMatches.addAll(hostMatches);
		}

		if (hostEan.length() > 3) {
			boolean containEan = documentos.stream()
					.anyMatch(documento -> documento.getEan().contains(hostEan));
			if (containEan) {
				List<Documento> eanMatches = documentos.stream()
					.filter(documento -> documento.getEan().contains(hostEan))
					.collect(Collectors.toList());

				allMatches.addAll(eanMatches);
			}
		}

		return allMatches.stream().distinct().collect(Collectors.toList());
	}

	private List<Documento> assignPaperSizes(List<Documento> documentos) {
		return documentos.stream()
			.collect(Collectors.groupingBy(doc -> {
				return new ArrayList<>(Arrays.asList(doc.getTipoTabloide(), doc.getCodigoTabloide(), doc.getHost()));
			}))
			.values().stream().map(docs -> {
				if (docs.size() > 1) {
					return docs.stream().map(doc -> {
						doc.setPapel("A3/A5");

						return doc;
					}).collect(Collectors.toList());
				}

				return docs;
		})
		.flatMap(Collection::stream)
		.distinct()
		.collect(Collectors.toList());
	}

	private List<Documento> assignDescricao(List<Documento> documentos) {
		List<Long> ids = documentos.stream()
			.map(documento -> Long.valueOf(documento.getHost()))
			.collect(Collectors.toList());

		List<Produto> produtos = produtosService.getProdutosById(ids);

		for (Documento documento : documentos) {
			for (Produto produto : produtos) {
				Long host = Long.valueOf(documento.getHost());
				Long id = Long.valueOf(produto.getCodigoProduto());

				if (host.equals(id)) {
					documento.setDescricao(produto.getDescricao());
				}
			}
		}

		return documentos;
	}

	public List<String> getTabloides(String loja) {
		if (loja != null) {
			String regiao = FileUtils.getRegiaoByLoja(loja);
			List<String> tabloides = new ArrayList<>();

			List<Documento> documentos = getOnlyDocumentosFromLojaMix(loja, getAllDocumentos());
			documentos.stream()
				.filter(doc -> !isVencido(doc.getDataFinal()))
				.filter(doc -> doc.isGeral())
				.map(doc -> doc.getTipoTabloide() + " " + doc.getCodigoTabloide())
				.forEach(tabloides::add);

			documentos.stream()
				.filter(doc -> !isVencido(doc.getDataFinal()))
				.filter(doc -> doc.getRegiao() != null && doc.getRegiao().equalsIgnoreCase(regiao))
				.map(doc -> doc.getTipoTabloide() + " " + doc.getCodigoTabloide())
				.forEach(tabloides::add);

			String paddedLoja = StringUtils.leftPad(loja, 3, '0');
			documentos.stream()
				.filter(doc -> !isVencido(doc.getDataFinal()))
				.filter(doc -> doc.isIndividual() && doc.getLoja().equalsIgnoreCase(paddedLoja))
				.map(doc -> doc.getTipoTabloide() + " " + doc.getCodigoTabloide())
				.forEach(tabloides::add);

			return tabloides.stream().sorted().distinct().collect(Collectors.toList());
		}

		return null;
	}

	public List<String> getPapeis(String loja) {
		if (loja != null) {
			String regiao = FileUtils.getRegiaoByLoja(loja);
			List<String> papeis = new ArrayList<>();

			getAllDocumentos().stream()
				.filter(doc -> doc.getRegiao() != null && doc.getRegiao().equalsIgnoreCase(regiao))
				.map(Documento::getPapel)
				.forEach(papeis::add);

			String paddedLoja = StringUtils.leftPad(loja, 3, '0');
			getAllDocumentos().stream()
				.filter(doc -> doc.isIndividual() && doc.getLoja().equalsIgnoreCase(paddedLoja))
				.map(Documento::getPapel)
				.forEach(papeis::add);

			return papeis.stream().distinct().collect(Collectors.toList());
		}

		return null;
	}

	public List<Documento> getOnlyDocumentosFromLojaMix(String loja, List<Documento> documentos) {
		List<Long> produtosIds = documentos.stream()
				.collect(Collectors.mapping(doc -> Long.valueOf(doc.getHost()), Collectors.toList()));

		List<Long> produtosMixIds = produtosService.getOnlyProdutosFromLojaMix(loja, produtosIds);

		List<Documento> docs = new ArrayList<>();
		for (Long produtoMixId : produtosMixIds) {
			for (Documento documento : documentos) {
				Long host = Long.valueOf(documento.getHost());
				if (host.equals(produtoMixId)) {
					docs.add(documento);
				}
			}
		}

		return docs;
	}

	private List<Documento> truncate(List<Documento> documentos) {
		if (documentos.size() > 1) {
			Set<Documento> truncated = new TreeSet<>(Comparator.comparing(Documento::getHost));
//			System.out.println("Antes: ");
//			documentos.stream().sorted(Comparator.comparing(Documento::getHost)).forEach(d -> System.out.println(d.getHost() + ", " + d.getPapel() + ", " + d.getTipoTabloide() + " " + d.getCodigoTabloide()));
//			System.out.println();

			documentos.forEach(documento -> {
				documentos.forEach(doc -> {
					if (documento.getHost().equals(doc.getHost())) {
						String tabloide = documento.getTipoTabloide() + " " + documento.getCodigoTabloide();
						String anotherTabloide = doc.getTipoTabloide() + " " + doc.getCodigoTabloide();

						if (!tabloide.equals(anotherTabloide)) {
							truncated.add(documento);
						}
					} else {
						truncated.add(documento);
					}
				});
			});

//			System.out.println("Depois: ");
//			truncated.stream().sorted(Comparator.comparing(Documento::getHost)).forEach(d -> System.out.println(d.getHost() + ", " + d.getPapel() + ", " + d.getTipoTabloide() + " " + d.getCodigoTabloide()));
			return new ArrayList<>(truncated);
		}

		return documentos;
	}

	public List<Documento> getDocumentosByFilters(
			String tipoTabloide, String codigoTabloide, String loja,
			String papel, String hostEan) {

		List<Documento> documentos = filterByLoja(loja);

		String reg = FileUtils.getRegiaoByLoja(loja);
		documentos = filterByRegiao(reg, documentos);
		documentos = filterByGeral(documentos);

		if (tipoTabloide != null && codigoTabloide != null)
			documentos = filterByTabloide(tipoTabloide, codigoTabloide, documentos);

		if (papel != null)
			documentos = filterByPapel(papel, documentos);

		if (hostEan != null)
			documentos = filterByHostEan(hostEan, documentos);

		if (!documentos.isEmpty())
			documentos = getOnlyDocumentosFromLojaMix(loja, documentos);
		else
			return documentos;

		documentos = assignPaperSizes(documentos);

		documentos = truncate(documentos);

		documentos = assignDescricao(documentos);

		return documentos.stream()
				.sorted(Comparator.comparing(Documento::getDescricao))
				.distinct()
				.collect(Collectors.toList());
	}

	// Método para Download
	public void download(HttpServletResponse response, String tipoTabloide,
			String codigoTabloide, String regiao, String loja, String documento) throws IOException {

		String filePath = "";
		if (loja != null && !loja.isEmpty()) {
			filePath = DirUtils.MKT_PRINTER_STR_DIR + "Individual" + File.separator + tipoTabloide + " " + codigoTabloide + File.separator + loja + File.separator + documento;
		} else if (regiao != null && !regiao.isEmpty()) {
			filePath = DirUtils.MKT_PRINTER_STR_DIR + regiao + File.separator + tipoTabloide + " " + codigoTabloide + File.separator + documento;
		} else {
			filePath = DirUtils.MKT_PRINTER_STR_DIR + "Geral" + File.separator + tipoTabloide + " " + codigoTabloide + File.separator + documento;
		}
		
		File file = new File(filePath);
		
		if (!file.exists()) {
			String errorMsg = "Desculpe. O arquivo que você está procurando não existe.";
			System.out.println(errorMsg);
			
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMsg.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			
			return;
		}
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			System.out.println("mimetype não detectavel, o padrão será usado");
			mimeType = "application/octet-stream";
		}
		
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		response.setContentLength((int) file.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

}