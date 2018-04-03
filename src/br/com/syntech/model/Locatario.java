package br.com.syntech.model;

import java.io.Serializable;

import br.com.syntech.model.enums.EstatoCivil;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class Locatario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String rg;
	private String cpf;
	private String nome;
	private String nacionalidade;
	private EstatoCivil estadoCivil;
	private String profissao;
	private String telefone;
	private String email;

	public Locatario() {

	}

	public Locatario(Long id, String rg, String cpf, String nome, String nacionalidade, EstatoCivil estadoCivil,
			String profissao, String telefone, String email) {
		super();
		this.id = id;
		this.rg = rg;
		this.cpf = cpf;
		this.nome = nome;
		this.nacionalidade = nacionalidade;
		this.estadoCivil = estadoCivil;
		this.profissao = profissao;
		this.telefone = telefone;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		Locatario other = (Locatario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
