package br.com.syntech.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public enum EstatoCivil {

	SOLTEIRO("Solteiro(a)"), CASADO("Casado(a)"), DIVORCIADO("Divorciado(a)"), VIUVO("Viuvo(a)"), SEPARADO(
			"Separado(a)");

	private String descricao;

	private EstatoCivil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstatoCivil toEnum(String descricao) {

		if (descricao.isEmpty()) {
			return null;
		}

		for (EstatoCivil x : EstatoCivil.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Descricão inválida!: " + descricao);
	}

	public static List<String> getAll() {

		List<String> list = new ArrayList<>();

		for (EstatoCivil x : EstatoCivil.values()) {
			list.add(x.getDescricao());
		}

		return list;
	}
}
