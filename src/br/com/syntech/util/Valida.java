package br.com.syntech.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class Valida {

	// VALIDANDO CPF:
	public static boolean isCpf(String cpf) {

		String dv1, dv2;

		// REMOVENDO CARACTERES ESPECIAIS:
		cpf = cpf.replace('.', ' ');
		cpf = cpf.replace('-', ' ');
		cpf = cpf.replaceAll(" ", "");

		// VERIFICANDO SE O CPF NÃO UMA SEGUENCIA DE NUMEROS IGUAIS
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {

			return false;
		}

		// VERIFICA O TAMANHO DO CPF:
		if (!cpf.substring(0, 1).equals("")) {
			try {
				int d1 = 0, d2 = 0;
				int digito1, digito2, resto;
				int digitoCPF;
				String nDigResult;

				for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
					digitoCPF = Integer.parseInt(cpf.substring(nCount - 1, nCount));

					// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por
					// diante.
					d1 = d1 + (11 - nCount) * digitoCPF;

					// para o segundo digito repita o procedimento incluindo o primeiro digito
					// calculado no passo anterior.
					d2 = d2 + (12 - nCount) * digitoCPF;
				}

				// Primeiro resto da divisão por 11.
				resto = (d1 % 11);

				// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos
				// o resultado anterior.
				if (resto < 2) {
					digito1 = 0;
				} else {
					digito1 = 11 - resto;
				}

				d2 += 2 * digito1;

				dv1 = Integer.toString(digito1);

				// Segundo resto da divisão por 11.
				resto = (d2 % 11);

				// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos
				// o resultado anterior.
				if (resto < 2) {
					digito2 = 0;
				} else {
					digito2 = 11 - resto;
				}

				dv2 = Integer.toString(digito2);

				// Digito verificador do CPF que está sendo validado.
				String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

				// Concatenando o primeiro resto com o segundo.
				nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

				// comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
				return nDigVerific.equals(nDigResult);

			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isCnpj(String cnpj) {

		if (!cnpj.substring(0, 1).equals("")) {

			cnpj = cnpj.replace('.', ' ');
			cnpj = cnpj.replace('/', ' ');
			cnpj = cnpj.replace('-', ' ');
			cnpj = cnpj.replaceAll(" ", "");
			try {
				int soma = 0, dig;
				String cnpj_calc = cnpj.substring(0, 12);
				if (cnpj.length() != 14) {
					return false;
				}
				char[] chr_cnpj = cnpj.toCharArray();
				/* Primeira parte */
				for (int i = 0; i < 4; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
						soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				/* Segunda parte */
				soma = 0;
				for (int i = 0; i < 5; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
						soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				return cnpj.equals(cnpj_calc);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isEmailValid(String email) {
		if ((email == null) || (email.trim().length() == 0)) {
			return false;
		}

		String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
