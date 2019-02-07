package br.com.condor.marketing.printer.impressao_cartaz_api.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FileUtils {

	private static final String REGIOES_TXT = DirUtils.MKT_PRINTER_PATH_DIR + "\\Regioes.txt";

	public static List<String> getLojasByRegiao(String regiao) {
		if (regiao != null && !regiao.isEmpty()) {
			try (BufferedReader br = new BufferedReader(new FileReader(REGIOES_TXT))) {
				String currentLine;
				while ((currentLine = br.readLine()) != null) {
					String substr = currentLine.contains(regiao) ? currentLine.substring(currentLine.indexOf(",") - 3)
							: null;

					return substr == null ? null : Arrays.asList(substr.split(", "));
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return null;
	}

	public static String getRegiaoByLoja(String loja) {
		if (loja != null && !loja.isEmpty()) {
			String paddedLoja = StringUtils.leftPad(loja, 3, '0');
			try (BufferedReader br = new BufferedReader(new FileReader(REGIOES_TXT))) {
				String currentLine;
				while ((currentLine = br.readLine()) != null) {
					if (currentLine.contains(paddedLoja)) {
						return currentLine.substring(0, currentLine.indexOf(" "));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return null;
	}

}
