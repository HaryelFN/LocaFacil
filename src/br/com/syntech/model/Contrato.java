package br.com.syntech.model;

import java.io.Serializable;
import java.util.Calendar;

public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Calendar dataLocacao;
	private Calendar fimLocacao;
	private Float valorAluguel;
	private int diaVencimento;
	private int duracao;
	private String obs;
	private Imovel imovel;
	private Locatario locatario;
	private Locador locador;

	public Contrato() {
		super();
	}

	public Contrato(Calendar dataLocacao, Calendar fimLocacao, Float valorAluguel, int diaVencimento, int duracao,String obs, Imovel imovel, Locatario locatario) {
		super();
		this.dataLocacao = dataLocacao;
		this.fimLocacao = fimLocacao;
		this.valorAluguel = valorAluguel;
		this.diaVencimento = diaVencimento;
		this.duracao = duracao;
		this.obs = obs;
		this.imovel = imovel;
		this.locatario = locatario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(Calendar dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public Calendar getFimLocacao() {
		return fimLocacao;
	}

	public void setFimLocacao(Calendar fimLocacao) {
		this.fimLocacao = fimLocacao;
	}

	public Float getValorAluguel() {
		return valorAluguel;
	}

	public void setValorAluguel(Float valorAluguel) {
		this.valorAluguel = valorAluguel;
	}

	public int getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(int diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Locatario getLocatario() {
		return locatario;
	}

	public void setLocatario(Locatario locatario) {
		this.locatario = locatario;
	}

	public Locador getLocador() {
		return locador;
	}

	public void setLocador(Locador locador) {
		this.locador = locador;
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
		Contrato other = (Contrato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
