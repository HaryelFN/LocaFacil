package br.com.syntech.repository;

import br.com.syntech.model.Locador;
import br.com.syntech.util.MyExeption;

public interface LocadorRepository extends BaseRepository<Locador>{

	public Locador getLocador() throws MyExeption;
}
