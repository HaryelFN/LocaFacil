package br.com.syntech.model;

import java.io.Serializable;

public class DataStackedChat implements Serializable {

	private static final long serialVersionUID = 1L;

	private Number mes;
	private Number valor;

	public DataStackedChat() {

	}

	public DataStackedChat(Number mes, Number valor) {
		super();
		this.mes = mes;
		this.valor = valor;
	}

	public Number getMes() {
		return mes;
	}

	public void setMes(Number mes) {
		this.mes = mes;
	}

	public Number getValor() {
		return valor;
	}

	public void setValor(Number valor) {
		this.valor = valor;
	}

}
