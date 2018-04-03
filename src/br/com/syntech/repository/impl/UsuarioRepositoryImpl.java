package br.com.syntech.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.syntech.model.Usuario;
import br.com.syntech.repository.UsuarioRepository;
import br.com.syntech.util.ConnectionFactory;
import br.com.syntech.util.DAOException;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class UsuarioRepositoryImpl implements UsuarioRepository {

	@Override
	public Usuario findOne(Long id) throws MyExeption {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() throws MyExeption {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Usuario obj) throws MyExeption {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Usuario obj) throws MyExeption {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) throws MyExeption {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Usuario isUsuario(String login, String senha) throws MyExeption {

		Connection con = null;
		Usuario usuario = null;

		if (!login.isEmpty() && !senha.isEmpty()) {
			try {
				con = ConnectionFactory.getConnection();
				String sql = "select usuario.* from usuario where usuario.login = ? and usuario.senha = ?";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, login);
				pst.setString(2, senha);

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					if (rs.getString("login").equals(login) & rs.getString("senha").equals(senha)) {

						usuario = new Usuario();
						usuario = map(rs);
					}
				}

			} catch (SQLException e) {
				throw new DAOException("Error ao buscar usuário na base de dados.", e);
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					throw new DAOException("Error ao fechar conexão", e);
				}
			}
		}
		return usuario;
	}

	private Usuario map(ResultSet rs) throws SQLException, MyExeption {
		Usuario user = new Usuario();
		user.setId(rs.getLong("id"));
		user.setNome(rs.getString("nome"));
		user.setLogin(rs.getString("login"));
		user.setSenha(rs.getString("senha"));
		return user;
	}
}
