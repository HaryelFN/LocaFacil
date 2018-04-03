package br.com.syntech.service;

import java.util.List;

import br.com.syntech.model.Locatario;
import br.com.syntech.repository.LocatarioRepository;
import br.com.syntech.repository.impl.LocatarioRepositoryImpl;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class LocatarioService {

	static LocatarioRepository REPOSITORY = new LocatarioRepositoryImpl();

	public Locatario getByID(Long id) throws MyExeption {
		return REPOSITORY.findOne(id);
	}

	public List<Locatario> getByNome(String nome) throws MyExeption {
		return REPOSITORY.findByNome(nome);
	}

	public List<Locatario> getByCpf(String cpf) throws MyExeption {
		return REPOSITORY.findByCpf(cpf);
	}

	public List<Locatario> getAll() throws Exception {
		return REPOSITORY.findAll();
	}

	public boolean save(String cpf, String rg, String nome, String nacionalidade, String estadoCivil, String profissao, String telefone, String email) throws MyExeption {

		boolean retorno = false;

		if (REPOSITORY.isCPF(cpf)) {
			throw new MyExeption("CPF já cadastrado na base de dados.\n Digite o CPF corretamente.");
		} else if (REPOSITORY.isRg(rg)) {
			throw new MyExeption("RG já cadastrado na base de dados.\n Digite o RG corretamente.");
		} else {

			Locatario locatario = new Locatario();

			locatario.setCpf(cpf);
			locatario.setRg(rg);
			locatario.setNome(nome);
			locatario.setNacionalidade(nacionalidade);
			locatario.setEstadoCivil(estadoCivil);
			locatario.setProfissao(profissao);
			locatario.setTelefone(telefone);
			locatario.setEmail(email);

			retorno = REPOSITORY.save(locatario);

		}
		return retorno;
	}

	public boolean update(Long id, String cpf, String rg, String nome, String nacionalidade, String estadoCivil, String profissao, String telefone, String email) throws MyExeption {

		Locatario locatario = new Locatario();

		locatario.setId(id);
		locatario.setCpf(cpf);
		locatario.setRg(rg);
		locatario.setNome(nome);
		locatario.setNacionalidade(nacionalidade);
		locatario.setEstadoCivil(estadoCivil);
		locatario.setProfissao(profissao);
		locatario.setTelefone(telefone);
		locatario.setEmail(email);

		return REPOSITORY.update(locatario);
	}

	public boolean delete(Long id) throws MyExeption {
		return REPOSITORY.delete(id);
	}
}
