package br.com.syntech.service;

import java.util.List;

import br.com.syntech.model.Imovel;
import br.com.syntech.repository.ImovelRepository;
import br.com.syntech.repository.impl.ImovelRepositoryImpl;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class ImovelService {

	static ImovelRepository REPOSITORY = new ImovelRepositoryImpl();

	public Imovel getImovelByID(Long id) throws MyExeption {
		return REPOSITORY.findOne(id);
	}

	public List<Imovel> getImovelByRegistro(String registro) throws MyExeption {
		return REPOSITORY.findByRegistro(registro);
	}

	public List<Imovel> getImovelByEnd(String end, boolean isAlocar) throws MyExeption {
		return REPOSITORY.findByEnd(end, isAlocar);
	}

	public List<Imovel> getAll() throws MyExeption {
		return REPOSITORY.findAll();
	}

	public boolean saveImovel(String endereco, String bairro, String cidade, String uf, String cep, String referencia,
			String largura, String comprimento, String valorIptu, int qtdQuartos, int qtdBanheiros, int vagasCaragem,
			String tipoImovel, String obs) throws MyExeption {

		Imovel imovel = new Imovel(endereco, bairro, cidade, uf, cep, referencia, largura, comprimento, valorIptu,
				qtdQuartos, qtdBanheiros, vagasCaragem, tipoImovel, "Livre", obs);

		return REPOSITORY.save(imovel);
	}

	public boolean updateImovel(Long id, String endereco, String bairro, String cidade, String uf, String cep,
			String referencia, String largura, String comprimento, String valorIptu, int qtdQuartos, int qtdBanheiros,
			int vagasCaragem, String tipoImovel, String situacaoImovel, String obs) throws MyExeption {

		Imovel imovel = new Imovel(id, endereco, bairro, cidade, uf, cep, referencia, largura, comprimento, valorIptu, qtdQuartos, qtdBanheiros, vagasCaragem, tipoImovel, situacaoImovel, obs);

		return REPOSITORY.update(imovel);
	}

	public boolean delete(Long id) throws MyExeption {
		return REPOSITORY.delete(id);
	}

}
