package br.com.syntech.model;

import java.io.Serializable;

import br.com.syntech.model.enums.Estado;
import br.com.syntech.model.enums.EstatoCivil;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class Locador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String cpf;
	private String rg;
	private String nome;
	private String cep;
	private Estado uf;
	private String cidade;
	private String bairro;
	private String endereco;
	private String nacionalidade;
	private EstatoCivil estadoCivil;
	private String profissao;

	public Locador() {

	}

	public Locador(Long id, String cpf, String rg, String nome, String cep, String uf, String cidade, String bairro,
			String endereco, String nacionalidade, String estadoCivil, String profissao) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.rg = rg;
		this.nome = nome;
		this.cep = cep;
		this.uf = Estado.toEnum(uf);
		this.cidade = cidade;
		this.bairro = bairro;
		this.endereco = endereco;
		this.nacionalidade = nacionalidade;
		this.estadoCivil = EstatoCivil.toEnum(estadoCivil);
		this.profissao = profissao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf.getDescricao();
	}

	public void setUf(String descricao) {
		this.uf = Estado.toEnum(descricao);
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getEstadoCivil() {
		return estadoCivil.getDescricao();
	}

	public void setEstadoCivil(String descricao) {
		this.estadoCivil = EstatoCivil.toEnum(descricao);
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
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
		Locador other = (Locador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
