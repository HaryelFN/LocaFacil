package br.com.syntech.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.syntech.model.Locador;
import br.com.syntech.repository.LocadorRepository;
import br.com.syntech.util.ConnectionFactory;
import br.com.syntech.util.DAOException;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class LocadorRepositoryImpl implements LocadorRepository {

	@Override
	public Locador findOne(Long id) throws MyExeption {
		Connection con = null;
		Locador locador = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locador.* from locador where locador.id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				locador = map(rs);
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar locador pelo ID." + e.getMessage(), e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return locador;
	}

	@Override
	public List<Locador> findAll() throws MyExeption {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Locador obj) throws MyExeption {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Locador obj) throws MyExeption {
		Connection con = null;
		boolean update = false;

		if (obj != null) {

			try {
				con = ConnectionFactory.getConnection();

				String sql = "UPDATE locador SET locador.cpf = ?, locador.rg = ?, locador.nome = ?, locador.cep = ?, locador.uf = ?, locador.cidade = ?, locador.bairro = ?,  locador.endereco = ?, locador.nacionalidade = ?,  locador.estado_civil = ?, locador.profissao = ? WHERE locador.id = ?";

				PreparedStatement pStmt = con.prepareStatement(sql);

				pStmt.setString(1, obj.getCpf());
				pStmt.setString(2, obj.getRg());
				pStmt.setString(3, obj.getNome());
				pStmt.setString(4, obj.getCep());
				pStmt.setString(5, obj.getUf());
				pStmt.setString(6, obj.getCidade());
				pStmt.setString(7, obj.getBairro());
				pStmt.setString(8, obj.getEndereco());
				pStmt.setString(9, obj.getNacionalidade());
				pStmt.setString(10, obj.getEstadoCivil());
				pStmt.setString(11, obj.getProfissao());
				pStmt.setLong(12, obj.getId());

				int aux = pStmt.executeUpdate();

				if (aux >= 0) {
					update = true;
				}

			} catch (SQLException e) {
				throw new DAOException("Error ao atualizar locador" + e.getMessage());
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					throw new DAOException("Erro ao fechar conexão", e);
				}
			}
		}
		return update;
	}

	@Override
	public boolean delete(Long id) throws MyExeption {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Locador getLocador() throws MyExeption {
		Connection con = null;
		Locador locador = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locador.* from locador where locador.id = 1";

			Statement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				locador = new Locador();
				locador = map(rs);
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao buscar locador na base de dados.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return locador;
	}

	private Locador map(ResultSet rs) throws SQLException, MyExeption {
		Locador locador = new Locador();

		locador.setId(rs.getLong("id"));
		locador.setRg(rs.getString("rg"));
		locador.setCpf(rs.getString("cpf"));
		locador.setNome(rs.getString("nome"));
		locador.setCep(rs.getString("cep"));
		locador.setUf(rs.getString("uf"));
		locador.setCidade(rs.getString("cidade"));
		locador.setBairro(rs.getString("bairro"));
		locador.setEndereco(rs.getString("endereco"));
		locador.setNacionalidade(rs.getString("nacionalidade"));
		locador.setEstadoCivil(rs.getString("estado_civil"));
		locador.setProfissao(rs.getString("profissao"));

		return locador;
	}

}
