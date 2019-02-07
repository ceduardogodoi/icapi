package br.com.condor.marketing.printer.impressao_cartaz_api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "CAD", name = "ORGAO")
public class Loja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODLOJA")
	private Long codigoLoja;

	@Column(name = "DSCORGAO")
	private String descricaoLoja;

	@OneToMany(mappedBy = "loja")
	private List<ProdutoOrgao> produtoOrgao;

	public Long getCodigoLoja() {
		return codigoLoja;
	}

	public void setCodigoLoja(Long codigoLoja) {
		this.codigoLoja = codigoLoja;
	}

	public String getDescricaoLoja() {
		return descricaoLoja;
	}

	public void setDescricaoLoja(String descricaoLoja) {
		this.descricaoLoja = descricaoLoja;
	}

	public List<ProdutoOrgao> getProdutoOrgao() {
		return produtoOrgao;
	}

	public void setProdutoOrgao(List<ProdutoOrgao> produtoOrgao) {
		this.produtoOrgao = produtoOrgao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoLoja == null) ? 0 : codigoLoja.hashCode());
		result = prime * result + ((descricaoLoja == null) ? 0 : descricaoLoja.hashCode());
		result = prime * result + ((produtoOrgao == null) ? 0 : produtoOrgao.hashCode());
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
		Loja other = (Loja) obj;
		if (codigoLoja == null) {
			if (other.codigoLoja != null)
				return false;
		} else if (!codigoLoja.equals(other.codigoLoja))
			return false;
		if (descricaoLoja == null) {
			if (other.descricaoLoja != null)
				return false;
		} else if (!descricaoLoja.equals(other.descricaoLoja))
			return false;
		if (produtoOrgao == null) {
			if (other.produtoOrgao != null)
				return false;
		} else if (!produtoOrgao.equals(other.produtoOrgao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Loja [codigoLoja=" + codigoLoja + ", descricaoLoja=" + descricaoLoja + "]";
	}

}
