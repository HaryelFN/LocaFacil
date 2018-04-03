package br.com.syntech.service;

import br.com.syntech.model.Locador;
import br.com.syntech.repository.LocadorRepository;
import br.com.syntech.repository.impl.LocadorRepositoryImpl;
import br.com.syntech.util.MyExeption;

public class LocadorService {

	static LocadorRepository REPOSITORY = new LocadorRepositoryImpl();

	public Locador getLocador() throws MyExeption {
		return REPOSITORY.getLocador();
	}

	public boolean update(String cpf, String rg, String nome, String cep, String uf, String cidade, String bairro,
			String endereco, String nacionalidade, String estadoCivil, String profissao) throws MyExeption {

		Long aux = (long) 1;
		Locador locador = new Locador(aux, cpf, rg, nome, cep, uf, cidade, bairro, endereco, nacionalidade, estadoCivil,
				profissao);

		return REPOSITORY.update(locador);
	}
}
