package br.com.syntech.repository;

import br.com.syntech.model.Usuario;
import br.com.syntech.util.MyExeption;

public interface UsuarioRepository extends BaseRepository<Usuario>{

	public Usuario isUsuario(String login, String senha) throws MyExeption;
}
