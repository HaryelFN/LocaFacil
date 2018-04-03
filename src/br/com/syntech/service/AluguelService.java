package br.com.syntech.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.syntech.model.Aluguel;
import br.com.syntech.model.Contrato;
import br.com.syntech.model.DataStackedChat;
import br.com.syntech.repository.AluguelRepository;
import br.com.syntech.repository.impl.AluguelRepositoryImpl;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.Calc;
import br.com.syntech.util.Convert;
import br.com.syntech.util.MyExeption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class AluguelService {

	static AluguelRepository REPOSITORY = new AluguelRepositoryImpl();

	public Aluguel findOne(Long id) throws MyExeption {
		return REPOSITORY.findOne(id);
	}

	public List<Aluguel> findAluguelByIDContrato(Long id) throws MyExeption {
		return REPOSITORY.findByIdContrato(id);
	}

	public List<Aluguel> getAll() throws MyExeption {
		return REPOSITORY.findAll();
	}

	public boolean saveAluguel(Contrato contrato) throws MyExeption {

		boolean insert = true;

		for (int i = 0; i < contrato.getDuracao(); i++) {

			contrato.getDataLocacao().add(Calendar.MONTH, 1);

			Aluguel aluguel = new Aluguel(contrato.getDataLocacao(), null, contrato.getValorAluguel(), 0, 0, contrato);

			if (!REPOSITORY.save(aluguel)) {
				insert = false;
				break;
			}
		}
		return insert;
	}

	public boolean update(Aluguel aluguel) throws MyExeption {
		return REPOSITORY.update(aluguel);
	}

	public Aluguel calcValorAluguel(Aluguel aluguel) {

		// CALC MULTA APOS 10 DIAS DE ATRASO DO ALUGUEL:
		float diferencaDias = 0;
		float multa = 0;

		if (aluguel.getDtVencimento().before(Calendar.getInstance())) {
			if ((diferencaDias = Calc.calendarDaysBetween(aluguel.getDtVencimento(), Calendar.getInstance())) > 10) {

				float porcentagem = 10;
				multa = (aluguel.getValor() * porcentagem) / 100;
			}
		}

		// CALC JUROS POR ATRASO NO ALUGUEL:
		float juros = 0;
		int auxMes = 0;
		if (aluguel.getDtVencimento().before(Calendar.getInstance())) {
			if ((diferencaDias = Calc.calendarDaysBetween(aluguel.getDtVencimento(), Calendar.getInstance())) > 15) {

				float porcentagem = 5;

				if (diferencaDias <= 30) {
					juros = (aluguel.getValor() * porcentagem) / 100;
				} else {

					auxMes = (int) (diferencaDias / 30);

					for (int i = 0; i < auxMes; i++) {
						juros += (aluguel.getValor() * porcentagem) / 100;
					}
				}
			}
		}

		aluguel.setMulta(multa);
		aluguel.setJuros(juros);

		if (CaixaDialogo.msgConfirmacao("Pagamento Aluguel",
				"Informações do aluguel:\n\n" + "Locatário: " + aluguel.getContrato().getLocatario().getNome()
						+ "\t CPF: " + aluguel.getContrato().getLocatario().getCpf() + "\nImóvel: "
						+ aluguel.getContrato().getImovel().getEndereco() + "\t Bairro: "
						+ aluguel.getContrato().getImovel().getBairro() + "\n\nData Vencimento: "
						+ Convert.CalendarToStr(aluguel.getDtVencimento()) + "\nDias em atraso: " + (int) diferencaDias
						+ "\n\nValor aluguel: " + Convert.floatToStr(aluguel.getValor()) + "\nMulta(10%): "
						+ Convert.floatToStr(multa) + "\nJuros ao mês(5%): " + Convert.floatToStr(juros)
						+ "\n\nValor Total: " + Convert.floatToStr((aluguel.getValor() + multa + juros))
						+ "\n\nReceber Aluguel?",
				"")) {
			return aluguel;
		}
		return null;
	}

	public List<DataStackedChat> getDataStackedChat() throws MyExeption {

		List<Aluguel> list = REPOSITORY.getLastYear();

		List<DataStackedChat> dataStackedChat = new ArrayList<>();

		for (Aluguel a : list) {

			DataStackedChat d = new DataStackedChat();

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 0) {
				d.setMes(1);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 1) {
				d.setMes(2);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 2) {
				d.setMes(3);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 3) {
				d.setMes(3);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 4) {
				d.setMes(4);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 5) {
				d.setMes(5);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			}

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 6) {
				d.setMes(6);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 7) {
				d.setMes(7);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 8) {
				d.setMes(8);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 9) {
				d.setMes(9);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 10) {
				d.setMes(11);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			} else

			if (a.getDtPagamento().get(GregorianCalendar.MONTH) == 11) {
				d.setMes(12);
				d.setValor(a.getValor() + a.getMulta() + a.getJuros());
			}

			dataStackedChat.add(d);
		}

		return dataStackedChat;
	}

	public static ObservableList<XYChart.Series<Number, Number>> getCountrySeries() {

		XYChart.Series<Number, Number> seriesAlugueis = new XYChart.Series<Number, Number>();
		seriesAlugueis.setName("China");
		seriesAlugueis.getData().add(new XYChart.Data<Number, Number>(1950, 555));
		seriesAlugueis.getData().add(new XYChart.Data<Number, Number>(2000, 1275));
		seriesAlugueis.getData().add(new XYChart.Data<Number, Number>(2050, 1395));
		seriesAlugueis.getData().add(new XYChart.Data<Number, Number>(2100, 1182));
		seriesAlugueis.getData().add(new XYChart.Data<Number, Number>(2150, 1149));

		ObservableList<XYChart.Series<Number, Number>> data = FXCollections.<XYChart.Series<Number, Number>>observableArrayList();
		data.add(seriesAlugueis);
		return data;
	}
}
