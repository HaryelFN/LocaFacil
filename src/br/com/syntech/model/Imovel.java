package br.com.syntech.model;

import java.io.Serializable;

import br.com.syntech.model.enums.Estado;
import br.com.syntech.model.enums.SituacaoImovel;
import br.com.syntech.model.enums.TipoImovel;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class Imovel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String endereco;
	private String bairro;
	private String cidade;
	private Estado uf;
	private String cep;
	private String referencia;
	private String largura;
	private String comprimento;
	private String valorIptu;
	private int qtdQuartos;
	private int qtdBanheiros;
	private int vagasCaragem;
	private TipoImovel tipoImovel;
	private SituacaoImovel situacaoImovel;
	private String obs;

	public Imovel() {
		super();
	}

	public Imovel(String endereco, String bairro, String cidade, String uf, String cep, String referencia,
			String largura, String comprimento, String valorIptu, int qtdQuartos, int qtdBanheiros, int vagasCaragem,
			String tipoImovel, String situacaoImovel, String obs) {
		super();
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.setUf(uf);
		this.cep = cep;
		this.referencia = referencia;
		this.largura = largura;
		this.comprimento = comprimento;
		this.valorIptu = valorIptu;
		this.qtdQuartos = qtdQuartos;
		this.qtdBanheiros = qtdBanheiros;
		this.vagasCaragem = vagasCaragem;
		this.tipoImovel = TipoImovel.toEnum(tipoImovel);
		this.situacaoImovel = SituacaoImovel.toEnum(situacaoImovel);
		this.obs = obs;
	}

	public Imovel(Long id, String endereco, String bairro, String cidade, String uf, String cep, String referencia,
			String largura, String comprimento, String valorIptu, int qtdQuartos, int qtdBanheiros, int vagasCaragem,
			String tipoImovel, String situacaoImovel, String obs) {
		super();
		this.id = id;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.setUf(uf);
		this.cep = cep;
		this.referencia = referencia;
		this.largura = largura;
		this.comprimento = comprimento;
		this.valorIptu = valorIptu;
		this.qtdQuartos = qtdQuartos;
		this.qtdBanheiros = qtdBanheiros;
		this.vagasCaragem = vagasCaragem;
		this.tipoImovel = TipoImovel.toEnum(tipoImovel);
		this.situacaoImovel = SituacaoImovel.toEnum(situacaoImovel);
		this.obs = obs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf.getDescricao();
	}

	public void setUf(String uf) {
		this.uf = Estado.toEnum(uf);
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getLargura() {
		return largura;
	}

	public void setLargura(String largura) {
		this.largura = largura;
	}

	public String getComprimento() {
		return comprimento;
	}

	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}

	public String getValorIptu() {
		return valorIptu;
	}

	public void setValorIptu(String valorIptu) {
		this.valorIptu = valorIptu;
	}

	public int getQtdQuartos() {
		return qtdQuartos;
	}

	public void setQtdQuartos(int qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}

	public int getQtdBanheiros() {
		return qtdBanheiros;
	}

	public void setQtdBanheiros(int qtdBanheiros) {
		this.qtdBanheiros = qtdBanheiros;
	}

	public int getVagasCaragem() {
		return vagasCaragem;
	}

	public void setVagasCaragem(int vagasCaragem) {
		this.vagasCaragem = vagasCaragem;
	}

	public String getTipoImovel() {
		return tipoImovel.getDescricao();
	}

	public void setTipoImovel(String descricao) {
		this.tipoImovel = tipoImovel.toEnum(descricao);
	}

	public String getSituacaoImovel() {
		return situacaoImovel.getDescricao();
	}

	public void setSituacaoImovel(String descricao) {
		this.situacaoImovel = situacaoImovel.toEnum(descricao);
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
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
		Imovel other = (Imovel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
