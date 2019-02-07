package br.com.condor.marketing.printer.impressao_cartaz_api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProdutoOrgaoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CODPRODUTO")
	private Long codProduto;

	@Column(name = "CODLOJA")
	private Long codLoja;

	public Long getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(Long codProduto) {
		this.codProduto = codProduto;
	}

	public Long getCodLoja() {
		return codLoja;
	}

	public void setCodLoja(Long codLoja) {
		this.codLoja = codLoja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codLoja == null) ? 0 : codLoja.hashCode());
		result = prime * result + ((codProduto == null) ? 0 : codProduto.hashCode());
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
		ProdutoOrgaoPK other = (ProdutoOrgaoPK) obj;
		if (codLoja == null) {
			if (other.codLoja != null)
				return false;
		} else if (!codLoja.equals(other.codLoja))
			return false;
		if (codProduto == null) {
			if (other.codProduto != null)
				return false;
		} else if (!codProduto.equals(other.codProduto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoOrgaoPK [codProduto=" + codProduto + ", codLoja=" + codLoja + "]";
	}

}
