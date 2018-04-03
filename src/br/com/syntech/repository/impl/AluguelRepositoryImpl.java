package br.com.syntech.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.syntech.model.Aluguel;
import br.com.syntech.repository.AluguelRepository;
import br.com.syntech.repository.ContratoRepository;
import br.com.syntech.repository.ImovelRepository;
import br.com.syntech.util.ConnectionFactory;
import br.com.syntech.util.DAOException;
import br.com.syntech.util.MyExeption;

public class AluguelRepositoryImpl implements AluguelRepository {

	static ImovelRepository REPO_IMOVEL = new ImovelRepositoryImpl();

	static ContratoRepository REPO_CONTRATO = new ContratoRepositoryImpl();

	@Override
	public Aluguel findOne(Long id) throws MyExeption {
		Connection con = null;
		Aluguel aluguel = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select aluguel.* from aluguel where aluguel.id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {

				aluguel = new Aluguel();

				aluguel.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_vencimento"));
				aluguel.setDtVencimento(c1);

				if (rs.getDate("data_pagamento") != null) {
					Calendar c2 = Calendar.getInstance();
					c2.setTime(rs.getDate("data_pagamento"));
					aluguel.setDtPagamento(c2);
				}

				aluguel.setValor(rs.getFloat("valor"));
				aluguel.setMulta(rs.getInt("multa"));
				aluguel.setJuros(rs.getInt("juros"));

				if (rs.getLong("id_contrato") != 0) {
					aluguel.setContrato(REPO_CONTRATO.findOne(rs.getLong("id_contrato")));
				}

			}

		} catch (SQLException e) {
			throw new DAOException("Error ao pesquisar aluguel pelo ID." + e.getMessage(), e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return aluguel;
	}

	@Override
	public List<Aluguel> findAll() throws MyExeption {
		Connection con = null;
		List<Aluguel> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "select * from aluguel";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			result = new ArrayList<Aluguel>();

			while (rs.next()) {

				Aluguel aluguel = new Aluguel();

				aluguel.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_vencimento"));
				aluguel.setDtVencimento(c1);

				if (rs.getDate("data_pagamento") != null) {
					Calendar c2 = Calendar.getInstance();
					c2.setTime(rs.getDate("data_pagamento"));
					aluguel.setDtPagamento(c2);
				}

				aluguel.setValor(rs.getFloat("valor"));
				aluguel.setMulta(rs.getInt("multa"));
				aluguel.setJuros(rs.getInt("juros"));

				if (rs.getLong("id_contrato") != 0) {
					aluguel.setContrato(REPO_CONTRATO.findOne(rs.getLong("id_contrato")));
				}

				result.add(aluguel);
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
	public boolean save(Aluguel obj) throws MyExeption {
		Connection con = null;
		boolean inserido = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "INSERT INTO aluguel (data_vencimento, data_pagamento, valor, multa, juros, id_contrato) values (?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setDate(1, new java.sql.Date(obj.getDtVencimento().getTimeInMillis()));
			pst.setDate(2, null);
			pst.setFloat(3, obj.getValor());
			pst.setFloat(4, 0);
			pst.setFloat(5, 0);
			pst.setLong(6, obj.getContrato().getId());

			int aux = pst.executeUpdate();

			if (aux >= 0) {
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
	public boolean update(Aluguel obj) throws MyExeption {
		Connection con = null;
		boolean update = false;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "UPDATE aluguel SET aluguel.data_pagamento = ?, aluguel.valor = ?, aluguel.multa = ?, aluguel.juros = ? WHERE aluguel.id = ?;";

			PreparedStatement pst = con.prepareStatement(sql);
			pst.setDate(1, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			pst.setFloat(2, obj.getValor());
			pst.setFloat(3, obj.getMulta());
			pst.setFloat(4, obj.getJuros());
			pst.setLong(5, obj.getId());

			int aux = pst.executeUpdate();

			if (aux >= 0) {
				update = true;
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao atualizar status pagamaneto aluguel", e);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Aluguel> findByIdContrato(Long id) throws MyExeption {

		Connection con = null;
		List<Aluguel> alugueis = new ArrayList<>();

		try {
			con = ConnectionFactory.getConnection();

			String sql = "SELECT aluguel.* FROM aluguel WHERE aluguel.id_contrato = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				Aluguel aluguel = new Aluguel();

				aluguel.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_vencimento"));
				aluguel.setDtVencimento(c1);

				if (rs.getDate("data_pagamento") != null) {
					Calendar c2 = Calendar.getInstance();
					c2.setTime(rs.getDate("data_pagamento"));
					aluguel.setDtPagamento(c2);
				}

				aluguel.setValor(rs.getFloat("valor"));
				aluguel.setMulta(rs.getInt("multa"));
				aluguel.setJuros(rs.getInt("juros"));

				if (rs.getLong("id_contrato") != 0) {
					aluguel.setContrato(REPO_CONTRATO.findOne(rs.getLong("id_contrato")));
				}

				alugueis.add(aluguel);
			}

		} catch (SQLException e) {
			throw new DAOException("Error ao buscar aluguéis na base de dados." + e.getMessage(), e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new DAOException("Error ao fechar conexão", e);
			}
		}
		return alugueis;
	}

	@Override
	public List<Aluguel> getAlugueisVencido() throws MyExeption {
		Connection con = null;
		List<Aluguel> result = null;

		try {
			con = ConnectionFactory.getConnection();

			String sql = "SELECT aluguel.* FROM aluguel WHERE aluguel.data_vencimento <= CURDATE();";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			result = new ArrayList<Aluguel>();

			while (rs.next()) {

				Aluguel aluguel = new Aluguel();

				aluguel.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_vencimento"));
				aluguel.setDtVencimento(c1);

				if (rs.getDate("data_pagamento") != null) {
					Calendar c2 = Calendar.getInstance();
					c2.setTime(rs.getDate("data_pagamento"));
					aluguel.setDtPagamento(c2);
				}

				aluguel.setValor(rs.getFloat("valor"));
				aluguel.setMulta(rs.getInt("multa"));
				aluguel.setJuros(rs.getInt("juros"));

				if (rs.getLong("id_contrato") != 0) {
					aluguel.setContrato(REPO_CONTRATO.findOne(rs.getLong("id_contrato")));
				}

				result.add(aluguel);
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

	@Override
	public List<Aluguel> getLastYear() throws MyExeption {
		Connection con = null;
		List<Aluguel> result = null;

		try {
			con = ConnectionFactory.getConnection();

			//String sql = "SELECT * FROM aluguel WHERE aluguel.data_pagamento BETWEEN CURDATE() - INTERVAL 12 MONTH AND CURDATE();";
			
			String sql = "SELECT * FROM aluguel WHERE YEAR(aluguel.data_pagamento) = YEAR(NOW()) ORDER BY aluguel.data_pagamento;";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			result = new ArrayList<Aluguel>();

			while (rs.next()) {

				Aluguel aluguel = new Aluguel();

				aluguel.setId(rs.getLong("id"));

				Calendar c1 = Calendar.getInstance();
				c1.setTime(rs.getDate("data_vencimento"));
				aluguel.setDtVencimento(c1);

				if (rs.getDate("data_pagamento") != null) {
					Calendar c2 = Calendar.getInstance();
					c2.setTime(rs.getDate("data_pagamento"));
					aluguel.setDtPagamento(c2);
				}

				aluguel.setValor(rs.getFloat("valor"));
				aluguel.setMulta(rs.getInt("multa"));
				aluguel.setJuros(rs.getInt("juros"));

				if (rs.getLong("id_contrato") != 0) {
					aluguel.setContrato(REPO_CONTRATO.findOne(rs.getLong("id_contrato")));
				}

				result.add(aluguel);
			}
		} catch (SQLException e) {
			throw new DAOException("Error ao buscar alugueis dos ultimos 6 meses na base de dados.", e);
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

}
