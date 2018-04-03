package br.com.syntech.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public enum Estado {

	AC("AC"), AL("AL"), AP("AP"), AM("AM"), BA("BA"), CE("CE"), DF("DF"), ES("ES"), GO("GO"), MA("MA"), MT("MT"), MS(
			"MS"), MG("MG"), PA("PA"), PB("PB"), PR("PR"), PE("PE"), PI("PI"), RJ("RJ"), RN("RN"), RS("RS"), RO("RO"), RR("RR"), SC("SC"), SP("SP"), SE("SE"), TO("TO");

	private String descricao;

	private Estado(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Estado toEnum(String descricao) {

		if (descricao.isEmpty()) {
			return null;
		}

		for (Estado x : Estado.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Descricão inválida!: " + descricao);
	}

	public static List<String> getAll() {

		List<String> list = new ArrayList<>();

		for (Estado x : Estado.values()) {
			list.add(x.getDescricao());
		}

		return list;
	}
}
