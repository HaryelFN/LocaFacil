package br.com.syntech.model;

import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class Usuario {

	private Long id;
	private String nome;
	private String login;
	private String senha;

	public Usuario() {
		super();
	}

	public Usuario(Long id, String nome, String login, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws MyExeption {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
