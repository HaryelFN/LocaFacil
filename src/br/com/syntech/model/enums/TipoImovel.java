package br.com.syntech.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public enum TipoImovel {

	APARTAMENTO("Apartamento"), 
	CASA("Casa"), 
	COMERCIO("Comércio"), 
	FLAT("Flat"),
	KITNET("Kitnet");

	private String descricao;

	private TipoImovel(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoImovel toEnum(String descricao) {

		if (descricao.isEmpty()) {
			return null;
		}

		for (TipoImovel x : TipoImovel.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Descricão inválida!: " + descricao);
	}

	public static List<String> getAll() {

		List<String> list = new ArrayList<>();

		for (TipoImovel x : TipoImovel.values()) {
			list.add(x.getDescricao());
		}

		return list;
	}
}
