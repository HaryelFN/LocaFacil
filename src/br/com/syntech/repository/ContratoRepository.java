package br.com.syntech.repository;

import java.util.List;

import br.com.syntech.model.Contrato;
import br.com.syntech.util.MyExeption;

public interface ContratoRepository extends BaseRepository<Contrato> {

	public List<Contrato> findByImovel(String endereco) throws MyExeption;
	
	public List<Contrato> finByLocatario(String nome) throws MyExeption;
	
	public List<Contrato> getVencidos() throws MyExeption;
}
