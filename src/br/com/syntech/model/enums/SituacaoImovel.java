package br.com.syntech.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public enum SituacaoImovel {

	LIVRE("Livre"), OCUPADO("Ocupado");

	private String descricao;

	private SituacaoImovel(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoImovel toEnum(String descricao) {

		if (descricao.isEmpty()) {
			return null;
		}

		for (SituacaoImovel x : SituacaoImovel.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Descricão inválida!: " + descricao);
	}

	public static List<String> getAll() {

		List<String> list = new ArrayList<>();

		for (SituacaoImovel x : SituacaoImovel.values()) {
			list.add(x.getDescricao());
		}

		return list;
	}
}
