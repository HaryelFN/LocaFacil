package br.com.syntech.util;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class MyExeption extends Exception {

	private static final long serialVersionUID = 1L;

	public MyExeption(String msg) {
		super(msg);
	}

	public MyExeption(String msg, Throwable cause) {
		super(msg, cause);
	}

}
