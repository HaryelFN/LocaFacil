package br.com.syntech.repository;

import java.util.List;

import br.com.syntech.model.Imovel;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public interface ImovelRepository extends BaseRepository<Imovel> {

	public List<Imovel> findByRegistro(String registro) throws MyExeption;
	
	public List<Imovel> findByEnd(String end, boolean isAlocar) throws MyExeption;
}
