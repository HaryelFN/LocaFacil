package br.com.syntech.repository;

import java.util.List;

import br.com.syntech.model.Aluguel;
import br.com.syntech.util.MyExeption;

public interface AluguelRepository extends BaseRepository<Aluguel>{

	public List<Aluguel> findByIdContrato(Long trecho) throws MyExeption;
	
	public List<Aluguel> getAlugueisVencido() throws MyExeption;
	
	public List<Aluguel> getLastYear() throws MyExeption;
	
}
