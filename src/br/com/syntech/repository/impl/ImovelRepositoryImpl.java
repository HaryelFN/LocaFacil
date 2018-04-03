package br.com.syntech.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.syntech.model.Imovel;
import br.com.syntech.repository.ImovelRepository;
import br.com.syntech.util.ConnectionFactory;
import br.com.syntech.util.DAOException;
import br.com.syntech.util.MyExeption;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class ImovelRepositoryImpl implements ImovelRepository {

	@Override
	public Imovel findOne(Long id) throws MyExeption {
		Connection con = null;
		Imovel imovel = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select imovel.* from imovel where imovel.id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				imovel = map(rs);
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar im�vel pelo ID." + e.getMessage(), e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conex�o", e);
			}
		}
		return imovel;
	}

	@Override
	public List<Imovel> findByRegistro(String registro) throws MyExeption {
		Connection con = null;
		List<Imovel> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select imovel.* from imovel where imovel.num_escritura like ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%" + registro + "%");
			ResultSet rs = pst.executeQuery();

			result = new ArrayList<Imovel>();
			while (rs.next()) {
				Imovel imovel = map(rs);
				result.add(imovel);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Im�vel.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conex�o", e);
			}
		}
		return result;
	}

	@Override
	public List<Imovel> findByEnd(String end, boolean isAlocar) throws MyExeption {
		Connection con = null;
		List<Imovel> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "";

			if (isAlocar) {
				sql = "SELECT imovel.* FROM imovel WHERE imovel.situacao = 'Livre' AND imovel.endereco LIKE ?;";
			} else {
				sql = "select imovel.* from imovel where imovel.endereco like ?";
			}

			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%" + end + "%");
			ResultSet rs = pst.executeQuery();

			result = new ArrayList<Imovel>();
			while (rs.next()) {
				Imovel imovel = map(rs);
				result.add(imovel);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Im�vel.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conex�o", e);
			}
		}
		return result;
	}

	@Override
	public List<Imovel> findAll() throws MyExeption {
		Connection con = null;
		List<Imovel> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select imovel.* from imovel";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			result = new ArrayList<Imovel>();

			while (rs.next()) {
				Imovel imovel = map(rs);
				result.add(imovel);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Im�veis.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conex�o", e);
			}
		}
		return result;
	}

	@Override
	public boolean save(Imovel obj) throws MyExeption {
		Connection con = null;
		boolean inserido = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "INSERT INTO imovel (endereco, bairro, cidade, uf, cep, referencia, largura, comprimento, valor_iptu, qtd_quartos, qtd_banheiros, vagas_caragem, tipo_imovel, situacao, obs) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, obj.getEndereco());
			pst.setString(2, obj.getBairro());
			pst.setString(3, obj.getCidade());
			pst.setString(4, obj.getUf());
			pst.setString(5, obj.getCep());
			pst.setString(6, obj.getReferencia());
			pst.setString(7, obj.getLargura());
			pst.setString(8, obj.getComprimento());
			pst.setString(9, obj.getValorIptu());
			pst.setInt(10, obj.getQtdQuartos());
			pst.setInt(11, obj.getQtdBanheiros());
			pst.setInt(12, obj.getVagasCaragem());
			pst.setString(13, obj.getTipoImovel());
			pst.setString(14, obj.getSituacaoImovel());
			pst.setString(15, obj.getObs());

			int aux = pst.executeUpdate();

			if (aux >= 0) {
				inserido = true;
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao inserir Im�vel", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar conex�o", e);
			}
		}
		return inserido;
	}

	@Override
	public boolean update(Imovel obj) throws MyExeption {
		Connection con = null;
		boolean update = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "UPDATE imovel SET imovel.endereco = ?, imovel.bairro = ?,  imovel.cidade = ?, imovel.uf = ?, imovel.cep = ?, imovel.referencia = ?, imovel.largura = ?, imovel.comprimento = ?, imovel.valor_iptu = ?, imovel.qtd_quartos = ?, imovel.qtd_banheiros = ?, imovel.vagas_caragem = ?, imovel.tipo_imovel = ?, imovel.situacao = ?, imovel.obs = ?  WHERE imovel.id = ?;";

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, obj.getEndereco());
			pst.setString(2, obj.getBairro());
			pst.setString(3, obj.getCidade());
			pst.setString(4, obj.getUf());
			pst.setString(5, obj.getCep());
			pst.setString(6, obj.getReferencia());
			pst.setString(7, obj.getLargura());
			pst.setString(8, obj.getComprimento());
			pst.setString(9, obj.getValorIptu());
			pst.setInt(10, obj.getQtdQuartos());
			pst.setInt(11, obj.getQtdBanheiros());
			pst.setInt(12, obj.getVagasCaragem());
			pst.setString(13, obj.getTipoImovel());
			pst.setString(14, obj.getSituacaoImovel());
			pst.setString(15, obj.getObs());
			pst.setLong(16, obj.getId());

			int aux = pst.executeUpdate();

			if (aux >= 0) {
				update = true;
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao atualizar Im�vel", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Erro ao fechar conex�o", e);
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

			String sql = "DELETE FROM imovel WHERE imovel.id = ?";

			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);
			excluido = pst.execute();

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			throw new MyExeption("Im�vel est� alugado, impossivel excluir!");
		} catch (SQLException e) {
			throw new DAOException("Error ao deletar im�vel.", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conex�o.", e);
			}
		}
		return excluido;
	}

	private Imovel map(ResultSet rs) throws SQLException, MyExeption {
		Imovel imovel = new Imovel();

		imovel.setId(rs.getLong("id"));
		imovel.setEndereco(rs.getString("endereco"));
		imovel.setBairro(rs.getString("bairro"));
		imovel.setCidade(rs.getString("cidade"));
		imovel.setUf(rs.getString("uf"));
		imovel.setCep(rs.getString("cep"));
		imovel.setReferencia(rs.getString("referencia"));
		imovel.setLargura(rs.getString("largura"));
		imovel.setComprimento(rs.getString("comprimento"));
		imovel.setValorIptu(rs.getString("valor_iptu"));
		imovel.setQtdQuartos(rs.getInt("qtd_quartos"));
		imovel.setQtdBanheiros(rs.getInt("qtd_banheiros"));
		imovel.setVagasCaragem(rs.getInt("vagas_caragem"));
		imovel.setTipoImovel(rs.getString("tipo_imovel"));
		imovel.setSituacaoImovel(rs.getString("situacao"));
		imovel.setObs(rs.getString("obs"));

		return imovel;
	}

}
