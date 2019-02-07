package br.com.condor.marketing.printer.impressao_cartaz_api.model;

import java.io.Serializable;

public class Documento implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoTabloide; // Tabloide Geral
	private String codigoTabloide; // 365 - 3 dígitos
	private String loja; // 019 - 3 digítos
	private String papel; // A5
	private String dataInicial; // 29112018 - 8 dígitos
	private String dataFinal; // 12122018 - 8 dígitos
	private String ean; // 07896534401528 - 14 dígitos
	private String host; // 0000582205 - 10 dígitos
	private String regiao;
	private boolean individual;
	private boolean geral;

	private String descricao; // será preenchido por consulta ao banco

	public Documento() {
	}

	public Documento(String tipoTabloide, String codigoTabloide, String loja, String papel, String dataInicial,
			String dataFinal, String ean, String host, String regiao, boolean individual, boolean geral) {
		this.tipoTabloide = tipoTabloide;
		this.codigoTabloide = codigoTabloide;
		this.loja = loja;
		this.papel = papel;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.ean = ean;
		this.host = host;
		this.descricao = "Host não encontrado";
		this.regiao = regiao;
		this.individual = individual;
		this.geral = geral;
	}

	public String getTipoTabloide() {
		return tipoTabloide;
	}

	public String getCodigoTabloide() {
		return codigoTabloide;
	}

	public String getLoja() {
		return loja;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public String getEan() {
		return ean;
	}

	public String getHost() {
		return host;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public boolean isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isGeral() {
		return geral;
	}

	public void setGeral(boolean geral) {
		this.geral = geral;
	}

	@Override
	public String toString() {
		return "Documento [tipoTabloide=" + tipoTabloide + ", codigoTabloide=" + codigoTabloide + ", loja=" + loja
				+ ", papel=" + papel + ", dataInicial=" + dataInicial + ", dataFinal=" + dataFinal + ", ean=" + ean
				+ ", host=" + host + ", regiao=" + regiao + ", individual=" + individual + ", descricao=" + descricao
				+ ", geral=]" + geral;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoTabloide == null) ? 0 : codigoTabloide.hashCode());
		result = prime * result + ((dataFinal == null) ? 0 : dataFinal.hashCode());
		result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((ean == null) ? 0 : ean.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + (individual ? 1231 : 1237);
		result = prime * result + ((loja == null) ? 0 : loja.hashCode());
		result = prime * result + ((papel == null) ? 0 : papel.hashCode());
		result = prime * result + ((regiao == null) ? 0 : regiao.hashCode());
		result = prime * result + ((tipoTabloide == null) ? 0 : tipoTabloide.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Documento other = (Documento) obj;
		if (codigoTabloide == null) {
			if (other.codigoTabloide != null)
				return false;
		} else if (!codigoTabloide.equals(other.codigoTabloide))
			return false;
		if (dataFinal == null) {
			if (other.dataFinal != null)
				return false;
		} else if (!dataFinal.equals(other.dataFinal))
			return false;
		if (dataInicial == null) {
			if (other.dataInicial != null)
				return false;
		} else if (!dataInicial.equals(other.dataInicial))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (ean == null) {
			if (other.ean != null)
				return false;
		} else if (!ean.equals(other.ean))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (individual != other.individual)
			return false;
		if (loja == null) {
			if (other.loja != null)
				return false;
		} else if (!loja.equals(other.loja))
			return false;
		if (papel == null) {
			if (other.papel != null)
				return false;
		} else if (!papel.equals(other.papel))
			return false;
		if (regiao == null) {
			if (other.regiao != null)
				return false;
		} else if (!regiao.equals(other.regiao))
			return false;
		if (tipoTabloide == null) {
			if (other.tipoTabloide != null)
				return false;
		} else if (!tipoTabloide.equals(other.tipoTabloide))
			return false;
		return true;
	}

}
