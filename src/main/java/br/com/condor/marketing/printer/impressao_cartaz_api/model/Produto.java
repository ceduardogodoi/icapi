package br.com.condor.marketing.printer.impressao_cartaz_api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "CAD", name = "PRODUTO")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODPRODUTO")
	private Long codigoProduto;

	@Column(name = "DSCPRODUTO")
	private String descricao;

	@OneToMany(mappedBy = "produto")
	private List<ProdutoOrgao> produtoOrgao;

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<ProdutoOrgao> getProdutoOrgao() {
		return produtoOrgao;
	}

	public void setProdutoOrgao(List<ProdutoOrgao> produtoOrgao) {
		this.produtoOrgao = produtoOrgao;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoProduto == null) ? 0 : codigoProduto.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		Produto other = (Produto) obj;
		if (codigoProduto == null) {
			if (other.codigoProduto != null)
				return false;
		} else if (!codigoProduto.equals(other.codigoProduto))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
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
		return "Produto [codigoProduto=" + codigoProduto + ", descricao=" + descricao + "]";
	}

}
