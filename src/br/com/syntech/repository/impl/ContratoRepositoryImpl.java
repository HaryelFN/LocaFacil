package br.com.syntech.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.syntech.model.Contrato;
import br.com.syntech.repository.ContratoRepository;
import br.com.syntech.repository.ImovelRepository;
import br.com.syntech.repository.LocadorRepository;
import br.com.syntech.repository.LocatarioRepository;
import br.com.syntech.service.AluguelService;
import br.com.syntech.util.ConnectionFactory;
import br.com.syntech.util.DAOException;
import br.com.syntech.util.MyExeption;

public class ContratoRepositoryImpl implements ContratoRepository {

	static ImovelRepository REPO_IMOVEL = new ImovelRepositoryImpl();

	static LocadorRepository REPO_LOCADOR = new LocadorRepositoryImpl();

	static LocatarioRepository REPO_LOCATARIO = new LocatarioRepositoryImpl();

	static AluguelService SERVICE_ALUGEUL = new AluguelService();

	@Override
	public Contrato findOne(Long id) throws MyExeption {
		Connection con = null;
		Contrato contrato = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select contrato.* from contrato where contrato.id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {

				contrato = new Contrato();

				contrato.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_locacao"));
				contrato.setDataLocacao(c1);

				Calendar c2 = Calendar.getInstance();
				c2.setTime(rs.getDate("data_fim_locacao"));
				contrato.setFimLocacao(c2);

				contrato.setValorAluguel(rs.getFloat("valor_aluguel"));
				contrato.setDiaVencimento(rs.getInt("dia_vencimento"));
				contrato.setDuracao(rs.getInt("duracao"));
				contrato.setObs(rs.getString("obs"));

				if (rs.getLong("id_imovel") != 0) {
					contrato.setImovel(REPO_IMOVEL.findOne(rs.getLong("id_imovel")));
				}

				if (rs.getLong("id_locatario") != 0) {
					contrato.setLocatario(REPO_LOCATARIO.findOne(rs.getLong("id_locatario")));
				}

				if (rs.getLong("id_locador") != 0) {
					contrato.setLocador(REPO_LOCADOR.findOne(rs.getLong("id_locador")));
				}

			}

		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar imóvel pelo ID." + e.getMessage(), e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return contrato;
	}

	@Override
	public List<Contrato> findAll() throws MyExeption {
		Connection con = null;
		List<Contrato> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select * from contrato";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			result = new ArrayList<Contrato>();

			while (rs.next()) {

				Contrato contrato = new Contrato();

				contrato.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_locacao"));
				contrato.setDataLocacao(c1);

				Calendar c2 = Calendar.getInstance();
				c2.setTime(rs.getDate("data_fim_locacao"));
				contrato.setFimLocacao(c2);

				contrato.setValorAluguel(rs.getFloat("valor_aluguel"));
				contrato.setDiaVencimento(rs.getInt("dia_vencimento"));
				contrato.setDuracao(rs.getInt("duracao"));
				contrato.setObs(rs.getString("obs"));

				if (rs.getLong("id_imovel") != 0) {
					contrato.setImovel(REPO_IMOVEL.findOne(rs.getLong("id_imovel")));
				}

				if (rs.getLong("id_locatario") != 0) {
					contrato.setLocatario(REPO_LOCATARIO.findOne(rs.getLong("id_locatario")));
				}

				if (rs.getLong("id_locador") != 0) {
					contrato.setLocador(REPO_LOCADOR.findOne(rs.getLong("id_locador")));
				}

				result.add(contrato);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Imóveis.", e);
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
	public boolean save(Contrato obj) throws MyExeption {
		Connection con = null;
		boolean inserido = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "INSERT INTO contrato (data_locacao, data_fim_locacao, valor_aluguel, dia_vencimento, duracao, obs, id_imovel, id_locatario, id_locador) values (?,?,?,?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setDate(1, new java.sql.Date(obj.getDataLocacao().getTimeInMillis()));
			pst.setDate(2, new java.sql.Date(obj.getFimLocacao().getTimeInMillis()));
			pst.setFloat(3, obj.getValorAluguel());
			pst.setInt(4, obj.getDiaVencimento());
			pst.setInt(5, obj.getDuracao());
			pst.setString(6, obj.getObs());
			pst.setLong(7, obj.getImovel().getId());
			pst.setLong(8, obj.getLocatario().getId());
			pst.setLong(9, 1);

			int aux = pst.executeUpdate();

			if (aux >= 0) {

				Long idContrato = getLastIdContrato();

				obj.setId(idContrato);

				// INSERT ALUGUEIS;
				SERVICE_ALUGEUL.saveAluguel(obj);

				inserido = true;
			}

		} catch (Exception e) {
			throw new DAOException("Error ao inserir Imóvel", e);
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
	public boolean update(Contrato obj) throws MyExeption {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) throws MyExeption {
		Connection con = null;
		boolean excluido = true;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "DELETE FROM contrato WHERE contrato.id = ?";

			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);
			excluido = pst.execute();

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			throw new MyExeption("Contrato está ativo, impossivel excluir!");
		} catch (SQLException e) {
			throw new DAOException("Error ao deletar contrato.", e);
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
	public List<Contrato> findByImovel(String endereco) throws MyExeption {
		Connection con = null;
		List<Contrato> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "SELECT contrato.* FROM contrato INNER JOIN imovel ON contrato.id_imovel = imovel.id WHERE imovel.endereco LIKE ?;";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%" + endereco + "%");
			ResultSet rs = pst.executeQuery();

			result = new ArrayList<Contrato>();
			while (rs.next()) {

				Contrato contrato = new Contrato();

				contrato.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_locacao"));
				contrato.setDataLocacao(c1);

				Calendar c2 = Calendar.getInstance();
				c2.setTime(rs.getDate("data_fim_locacao"));
				contrato.setFimLocacao(c2);

				contrato.setValorAluguel(rs.getFloat("valor_aluguel"));
				contrato.setDiaVencimento(rs.getInt("dia_vencimento"));
				contrato.setDuracao(rs.getInt("duracao"));
				contrato.setObs(rs.getString("obs"));

				if (rs.getLong("id_imovel") != 0) {
					contrato.setImovel(REPO_IMOVEL.findOne(rs.getLong("id_imovel")));
				}

				if (rs.getLong("id_locatario") != 0) {
					contrato.setLocatario(REPO_LOCATARIO.findOne(rs.getLong("id_locatario")));
				}

				if (rs.getLong("id_locador") != 0) {
					contrato.setLocador(REPO_LOCADOR.findOne(rs.getLong("id_locador")));
				}

				result.add(contrato);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Imóvel.", e);
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
	public List<Contrato> finByLocatario(String nome) throws MyExeption {

		Connection con = null;
		List<Contrato> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "SELECT contrato.* FROM contrato INNER JOIN locatario ON contrato.id_locatario = locatario.id WHERE locatario.nome LIKE ?;";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%" + nome + "%");
			ResultSet rs = pst.executeQuery();

			result = new ArrayList<Contrato>();
			while (rs.next()) {

				Contrato contrato = new Contrato();

				contrato.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_locacao"));
				contrato.setDataLocacao(c1);

				Calendar c2 = Calendar.getInstance();
				c2.setTime(rs.getDate("data_fim_locacao"));
				contrato.setFimLocacao(c2);

				contrato.setValorAluguel(rs.getFloat("valor_aluguel"));
				contrato.setDiaVencimento(rs.getInt("dia_vencimento"));
				contrato.setDuracao(rs.getInt("duracao"));
				contrato.setObs(rs.getString("obs"));

				if (rs.getLong("id_imovel") != 0) {
					contrato.setImovel(REPO_IMOVEL.findOne(rs.getLong("id_imovel")));
				}

				if (rs.getLong("id_locatario") != 0) {
					contrato.setLocatario(REPO_LOCATARIO.findOne(rs.getLong("id_locatario")));
				}

				if (rs.getLong("id_locador") != 0) {
					contrato.setLocador(REPO_LOCADOR.findOne(rs.getLong("id_locador")));
				}

				result.add(contrato);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar Imóvel.", e);
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
	public List<Contrato> getVencidos() throws MyExeption {
		Connection con = null;
		List<Contrato> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "SELECT contrato.* FROM contrato WHERE contrato.data_fim_locacao <= CURDATE();";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			result = new ArrayList<Contrato>();

			while (rs.next()) {

				Contrato contrato = new Contrato();

				contrato.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_locacao"));
				contrato.setDataLocacao(c1);

				Calendar c2 = Calendar.getInstance();
				c2.setTime(rs.getDate("data_fim_locacao"));
				contrato.setFimLocacao(c2);

				contrato.setValorAluguel(rs.getFloat("valor_aluguel"));
				contrato.setDiaVencimento(rs.getInt("dia_vencimento"));
				contrato.setDuracao(rs.getInt("duracao"));
				contrato.setObs(rs.getString("obs"));

				if (rs.getLong("id_imovel") != 0) {
					contrato.setImovel(REPO_IMOVEL.findOne(rs.getLong("id_imovel")));
				}

				if (rs.getLong("id_locatario") != 0) {
					contrato.setLocatario(REPO_LOCATARIO.findOne(rs.getLong("id_locatario")));
				}

				if (rs.getLong("id_locador") != 0) {
					contrato.setLocador(REPO_LOCADOR.findOne(rs.getLong("id_locador")));
				}

				result.add(contrato);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao buscar notificações na base de dados.", e);
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

	private Long getLastIdContrato() {
		Connection con = null;
		Long id = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "SELECT contrato.id from contrato ORDER BY id DESC LIMIT 1;";
			Statement stm = con.prepareStatement(sql);

			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				id = rs.getLong("id");
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar imóvel pelo ID." + e.getMessage(), e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return id;
	}

}
