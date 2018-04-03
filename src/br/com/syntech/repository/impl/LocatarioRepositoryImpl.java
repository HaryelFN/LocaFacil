package br.com.syntech.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.syntech.model.Locatario;
import br.com.syntech.repository.LocatarioRepository;
import br.com.syntech.util.ConnectionFactory;
import br.com.syntech.util.DAOException;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class LocatarioRepositoryImpl implements LocatarioRepository {

	@Override
	public Locatario findOne(Long id) throws MyExeption {
		Connection con = null;
		Locatario locatario = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locatario.* from locatario where locatario.id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				locatario = map(rs);
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao buscar Locatário pelo ID.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return locatario;
	}

	@Override
	public List<Locatario> findAll() throws MyExeption {
		Connection con = null;

		List<Locatario> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locatario.* from locatario";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			result = new ArrayList<Locatario>();

			while (rs.next()) {
				Locatario locatario = map(rs);
				result.add(locatario);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao buscar locatários.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return result;
	}

	@Override
	public boolean save(Locatario obj) {
		Connection con = null;
		boolean inserido = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "INSERT INTO locatario (cpf, rg, nome, nacionalidade, estadoCivil, profissao, telefone, email) values (?,?,?,?,?,?,?,?);";

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, obj.getCpf());
			pst.setString(2, obj.getRg());
			pst.setString(3, obj.getNome());
			pst.setString(4, obj.getNacionalidade());
			pst.setString(5, obj.getEstadoCivil());
			pst.setString(6, obj.getProfissao());
			pst.setString(7, obj.getTelefone());
			pst.setString(8, obj.getEmail());

			int aux = pst.executeUpdate();

			if (aux >= 0) {

				inserido = true;
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao inserir locatário", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar conexão", e);
			}
		}
		return inserido;
	}

	@Override
	public boolean update(Locatario obj) {
		Connection con = null;
		boolean update = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "UPDATE locatario SET locatario.cpf = ?, locatario.rg = ?,  locatario.nome = ?, locatario.nacionalidade = ?, locatario.estadoCivil = ?, locatario.profissao = ?, locatario.telefone = ?, locatario.email = ? WHERE locatario.id = ?;";

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, obj.getCpf());
			pst.setString(2, obj.getRg());
			pst.setString(3, obj.getNome());
			pst.setString(4, obj.getNacionalidade());
			pst.setString(5, obj.getEstadoCivil());
			pst.setString(6, obj.getProfissao());
			pst.setString(7, obj.getTelefone());
			pst.setString(8, obj.getEmail());
			pst.setLong(9, obj.getId());

			int aux = pst.executeUpdate();

			if (aux >= 0) {

				update = true;
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao atualizar Locatário", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar conexão", e);
			}
		}

		return update;
	}

	@Override
	public boolean delete(Long id) throws MyExeption {
		Connection con = null;
		boolean excluido = true;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "DELETE FROM locatario WHERE locatario.id = ?";

			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);
			excluido = pst.execute();

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			throw new MyExeption("Locatário está em um contrato em aberto, impossivel excluir!");
		} catch (SQLException e) {
			throw new DAOException("Error ao deletar imóvel.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão.", e);
			}
		}
		return excluido;
	}

	@Override
	public List<Locatario> findByNome(String nome) throws MyExeption {
		Connection con = null;
		List<Locatario> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locatario.* from locatario where locatario.nome like ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%" + nome + "%");
			ResultSet rs = pst.executeQuery();

			result = new ArrayList<Locatario>();
			while (rs.next()) {
				Locatario locatario = map(rs);
				result.add(locatario);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Locatário.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return result;
	}

	@Override
	public List<Locatario> findByCpf(String cpf) throws MyExeption {
		Connection con = null;
		List<Locatario> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locatario.* from locatario where locatario.cpf like ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%" + cpf + "%");
			ResultSet rs = pst.executeQuery();

			result = new ArrayList<Locatario>();
			while (rs.next()) {
				Locatario locatario = map(rs);
				result.add(locatario);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Locatário.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return result;
	}

	@Override
	public boolean isCPF(String cpf) throws MyExeption {
		Connection con = null;
		boolean result = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locatario.cpf from locatario where locatario.cpf = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, cpf);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao verificar CPF Locatário.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return result;
	}

	@Override
	public boolean isRg(String rg) throws MyExeption {
		Connection con = null;
		boolean result = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select locatario.rg from locatario where locatario.rg = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, rg);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao verificar RG Locatário.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return result;
	}

	private Locatario map(ResultSet rs) throws SQLException, MyExeption {
		Locatario locatario = new Locatario();

		locatario.setId(rs.getLong("id"));
		locatario.setRg(rs.getString("rg"));
		locatario.setCpf(rs.getString("cpf"));
		locatario.setNome(rs.getString("nome"));
		locatario.setNacionalidade(rs.getString("nacionalidade"));
		locatario.setEstadoCivil(rs.getString("estadoCivil"));
		locatario.setProfissao(rs.getString("profissao"));
		locatario.setTelefone(rs.getString("telefone"));
		locatario.setEmail(rs.getString("email"));

		return locatario;
	}
}
