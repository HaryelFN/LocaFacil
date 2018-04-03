package br.com.syntech.repository;

import java.util.List;

import br.com.syntech.model.Locatario;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public interface LocatarioRepository extends BaseRepository<Locatario> {

	public List<Locatario> findByNome(String nome) throws MyExeption;

	public List<Locatario> findByCpf(String cpf) throws MyExeption;

	public boolean isCPF(String cpf) throws MyExeption;

	public boolean isRg(String rg) throws MyExeption;

}
