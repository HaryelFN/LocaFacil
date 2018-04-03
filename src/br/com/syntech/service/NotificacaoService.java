package br.com.syntech.service;

import java.util.ArrayList;
import java.util.List;

import br.com.syntech.model.Aluguel;
import br.com.syntech.model.Contrato;
import br.com.syntech.model.Notificacao;
import br.com.syntech.repository.AluguelRepository;
import br.com.syntech.repository.ContratoRepository;
import br.com.syntech.repository.impl.AluguelRepositoryImpl;
import br.com.syntech.repository.impl.ContratoRepositoryImpl;
import br.com.syntech.util.MyExeption;

public class NotificacaoService {

	static ContratoRepository CONTRATO_REPO = new ContratoRepositoryImpl();

	static AluguelRepository ALUGUEL_REPO = new AluguelRepositoryImpl();

	public List<Notificacao> getAllNotifications() throws MyExeption {

		int aux = 0;

		List<Notificacao> notificacoes = new ArrayList<>();

		List<Contrato> contratos = new ArrayList<>();
		contratos = CONTRATO_REPO.getVencidos();

		List<Aluguel> alugueis = new ArrayList<>();
		alugueis = ALUGUEL_REPO.getAlugueisVencido();

		if (!contratos.isEmpty()) {
			for (Contrato c : contratos) {
				aux++;
				Notificacao n = new Notificacao(aux, "CONTRATO", c.getId(),
						"CONTRATO ENCERROU!\nContrato ID:" + c.getId(), c.getFimLocacao());
				notificacoes.add(n);
			}
		}

		if (!alugueis.isEmpty()) {
			for (Aluguel a : alugueis) {
				if (a.getDtPagamento() == null) {
					aux++;
					Notificacao n = new Notificacao(aux, "ALUGUEL", a.getId(), "ALUGUEL ATRASADO!\nAluguel ID: " + a.getId(), a.getDtVencimento());
					notificacoes.add(n);
				}
			}
		}

		return notificacoes;
	}

}
