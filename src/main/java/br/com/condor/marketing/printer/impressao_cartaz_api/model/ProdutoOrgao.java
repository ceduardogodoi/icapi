package br.com.condor.marketing.printer.impressao_cartaz_api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROD_ORGAO")
public class ProdutoOrgao implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProdutoOrgaoPK id;

	@ManyToOne
	@JoinColumn(name = "CODPRODUTO", updatable = false, insertable = false)
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "CODLOJA", updatable = false, insertable = false)
	private Loja loja;

	@Column(name = "SITUACAOVENDA")
	private Boolean situacaoVenda;

	public ProdutoOrgaoPK getId() {
		return id;
	}

	public void setId(ProdutoOrgaoPK id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Boolean getSituacaoVenda() {
		return situacaoVenda;
	}

	public void setSituacaoVenda(Boolean situacaoVenda) {
		this.situacaoVenda = situacaoVenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProdutoOrgao other = (ProdutoOrgao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoOrgao [id=" + id + ", produto=" + produto + ", loja=" + loja + ", situacaoVenda=" + situacaoVenda
				+ "]";
	}

}
