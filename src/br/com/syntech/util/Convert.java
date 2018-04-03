package br.com.syntech.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.com.caelum.stella.inwords.FormatoDeReal;
import br.com.caelum.stella.inwords.NumericToWordsConverter;

public class Convert {

	public static float srtToFloat(String arg) throws ParseException {
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		float number = nf.parse(arg).floatValue();
		return number;
	}

	public static String floatToStr(float number) {
		Locale ptBr = new Locale("pt", "BR");
		String str = NumberFormat.getCurrencyInstance(ptBr).format(number);
		return str;
	}

	public static String CalendarToStr(Calendar calendar) {
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
		String a = s.format(calendar.getTime());
		return a;
	}

	public static String toNumberExtense(float num) {
		NumericToWordsConverter converter;
		converter = new NumericToWordsConverter(new FormatoDeReal());
		String extenso = converter.toWords(num);
		return extenso;
	}
}
