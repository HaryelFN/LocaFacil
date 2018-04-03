package br.com.syntech.service;

import java.util.Calendar;
import java.util.List;

import br.com.syntech.model.Contrato;
import br.com.syntech.model.Imovel;
import br.com.syntech.model.Locatario;
import br.com.syntech.repository.ContratoRepository;
import br.com.syntech.repository.impl.ContratoRepositoryImpl;
import br.com.syntech.util.MyExeption;

public class ContratoService {

	static ContratoRepository REPOSITORY = new ContratoRepositoryImpl();

	public List<Contrato> getAll() throws MyExeption {
		return REPOSITORY.findAll();
	}

	public List<Contrato> getByImovel(String endereco) throws MyExeption {
		return REPOSITORY.findByImovel(endereco);
	}

	public List<Contrato> getByLocatario(String nome) throws MyExeption {
		return REPOSITORY.finByLocatario(nome);
	}

	public boolean saveContrato(Calendar dataLocacao, Calendar fimLocacao, float valorAluguel, int diaVencimento,
			int duracao, String obs, Imovel imovel, Locatario locatario) throws MyExeption {

		Contrato contrato = new Contrato(dataLocacao, fimLocacao, valorAluguel, diaVencimento, duracao, obs, imovel,
				locatario);

		return REPOSITORY.save(contrato);
	}

	public boolean delete(Long id) throws MyExeption {
		return REPOSITORY.delete(id);
	}
}
