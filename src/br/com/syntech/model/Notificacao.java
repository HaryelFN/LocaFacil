package br.com.syntech.model;

import java.io.Serializable;
import java.util.Calendar;

public class Notificacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String tipo;
	private Long idObj;
	private String assunto;
	private Calendar data;

	public Notificacao() {
	}

	public Notificacao(int id, String tipo, Long idObj, String assunto, Calendar data) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.idObj = idObj;
		this.assunto = assunto;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdObj() {
		return idObj;
	}

	public void setIdObj(Long idObj) {
		this.idObj = idObj;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Notificacao other = (Notificacao) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
