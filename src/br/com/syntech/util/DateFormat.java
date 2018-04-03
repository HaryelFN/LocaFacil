package br.com.syntech.util;

import java.text.SimpleDateFormat;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class DateFormat {

	public static String data(SimpleDateFormat entrada) {

		String result;

		entrada = new SimpleDateFormat("yyyy/MM/dd");

		result = String.valueOf(entrada);

		return result;
	}

	public static java.sql.Date getDataAtual() {
		java.util.Date dataUtil = new java.util.Date();
		java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
		return dataSql;
	}
}
