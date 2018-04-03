package br.com.syntech.repository;

import java.util.List;

import br.com.syntech.model.Aluguel;
import br.com.syntech.util.MyExeption;

public interface BaseRepository<T> {

	public T findOne(Long id) throws MyExeption;

	public List<T> findAll() throws MyExeption;

	public boolean save(T obj) throws MyExeption;

	public boolean update(T obj) throws MyExeption;

	public boolean delete(Long id) throws MyExeption;
}
