package br.com.syntech.model;

import java.io.Serializable;
import java.util.Calendar;

public class Aluguel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Calendar dtVencimento;
	private Calendar dtPagamento;
	private float valor;
	private float multa;
	private float juros;
	private Contrato contrato;

	public Aluguel() {

	}

	public Aluguel(Calendar dtVencimento, Calendar dtPagamento, float valor, float multa, float juros, Contrato contrato) {
		super();
		this.dtVencimento = dtVencimento;
		this.dtPagamento = dtPagamento;
		this.valor = valor;
		this.multa = multa;
		this.juros = juros;
		this.contrato = contrato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Calendar dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Calendar getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Calendar dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getMulta() {
		return this.multa;
	}

	public void setMulta(float multa) {
		this.multa = multa;
	}

	public float getJuros() {
		return juros;
	}

	public void setJuros(float juros) {
		this.juros = juros;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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
		Aluguel other = (Aluguel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
