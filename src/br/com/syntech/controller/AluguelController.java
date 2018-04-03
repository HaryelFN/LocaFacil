package br.com.syntech.controller;

import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.DocumentException;

import br.com.syntech.model.Aluguel;
import br.com.syntech.model.Contrato;
import br.com.syntech.model.Imovel;
import br.com.syntech.model.Locatario;
import br.com.syntech.service.AluguelService;
import br.com.syntech.service.ContratoService;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.Convert;
import br.com.syntech.util.GeradorPDF;
import br.com.syntech.util.MyExeption;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class AluguelController implements Initializable {

	public List<Aluguel> listAlugueis = new ArrayList<>();

	static Aluguel SELECT_ALUGUEL;

	public List<Imovel> listImovel = new ArrayList<>();

	static Imovel SELECT_IMOVEL;

	public List<Locatario> listLocatario = new ArrayList<>();

	static Locatario SELECT_LOCATARIO;

	static Contrato SELECT_CONTRATO;

	private AluguelService service = new AluguelService();

	private ContratoService contratoService = new ContratoService();

	@FXML
	private TabPane tab;

	@FXML
	private Tab tabPesContrato;

	@FXML
	private TextField txfPesAlugueis;

	@FXML
	private RadioButton rdbImovel;

	@FXML
	private ToggleGroup grupo;

	@FXML
	private RadioButton rdbLocatario;

	@FXML
	private TableView<Aluguel> tbwAlugueis;

	@FXML
	private TableColumn<Aluguel, String> tbcID;

	@FXML
	private TableColumn<Aluguel, String> tbcImovel;

	@FXML
	private TableColumn<Aluguel, String> tbcLocatario;

	@FXML
	private TableColumn<Aluguel, Calendar> tbcDtVencimento;

	@FXML
	private TableColumn<Aluguel, Calendar> tbcDtPagemento;

	@FXML
	private TableColumn<Aluguel, String> tbcValor;

	@FXML
	private ListView<Contrato> listView;

	@FXML
	void onCancelPesImovel(ActionEvent event) {
		closeView();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		eventOpenListView();
		changerRadioBtn();
		selectContratolListView();
		selectAluguelTableView();
	}

	private void changerRadioBtn() {
		rdbImovel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 1) {
						txfPesAlugueis.setText("");
						try {
							populaListView(contratoService.getAll());
						} catch (MyExeption e) {
							CaixaDialogo.msgError("Error", e.getMessage(), "");
						}

					}
				}
			}
		});

		rdbLocatario.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 1) {
						txfPesAlugueis.setText("");
						try {
							populaListView(contratoService.getAll());
						} catch (MyExeption e) {
							CaixaDialogo.msgError("Error", e.getMessage(), "");
						}

					}
				}
			}
		});
	}

	// EVENTO LISTVIEW:
	private void eventOpenListView() {
		txfPesAlugueis.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 1) {

						try {
							populaListView(contratoService.getAll());
						} catch (MyExeption e) {
							CaixaDialogo.msgError("Error", e.getMessage(), "");
						}

					}
				}
			}
		});
	}

	private void closeView() {
		HomeController.STAGE_ALUGUEL.close();
		HomeController.STAGE_ALUGUEL = null;
	}

	// SOBRESCREVENDO METODO DO LISTVIEW:
	private void populaListView(List<Contrato> contratos) throws MyExeption {

		listView.setVisible(true);

		findListViewAluguel();

		ObservableList<Contrato> itens = FXCollections.observableArrayList(contratos);

		this.listView.setItems(itens);

		this.listView.setCellFactory(new Callback<ListView<Contrato>, ListCell<Contrato>>() {

			@Override
			public ListCell<Contrato> call(ListView<Contrato> p) {

				ListCell<Contrato> cell = new ListCell<Contrato>() {

					@Override
					protected void updateItem(Contrato c, boolean bln) {
						super.updateItem(c, bln);
						if (c != null) {
							setText("Locatário: " + c.getLocatario().getNome() + " / Endereço:  "
									+ c.getImovel().getEndereco());
						}
					}

				};
				return cell;
			}
		});
	}

	// EVENTO FIND LISTVIEW LOCATARIO:
	private void findListViewAluguel() {
		txfPesAlugueis.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (rdbImovel.isSelected()) {
					try {
						populaListView(contratoService.getByImovel(txfPesAlugueis.getText().toString()));
					} catch (MyExeption e) {
						CaixaDialogo.msgError("Error", e.getMessage(), "");
					}
				} else {
					try {
						populaListView(contratoService.getByLocatario(txfPesAlugueis.getText().toString()));
					} catch (MyExeption e) {
						CaixaDialogo.msgError("Error", e.getMessage(), "");
					}
				}
			}
		});
	}

	private void selectContratolListView() {
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						SELECT_CONTRATO = listView.getSelectionModel().getSelectedItem();

						if (SELECT_CONTRATO != null) {
							listView.setVisible(false);
							try {
								populandoTable(service.findAluguelByIDContrato(SELECT_CONTRATO.getId()));
							} catch (MyExeption e) {
								CaixaDialogo.msgError("Error", e.getMessage(), "");
							}
						}
					}
				}
			}
		});
	}

	private void selectAluguelTableView() {
		tbwAlugueis.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						SELECT_ALUGUEL = tbwAlugueis.getSelectionModel().getSelectedItem();

						if (SELECT_ALUGUEL != null) {
							if (SELECT_ALUGUEL.getDtPagamento() == null) {
								if ((SELECT_ALUGUEL = service.calcValorAluguel(SELECT_ALUGUEL)) != null) {

									try {
										if (service.update(SELECT_ALUGUEL)) {
											
											HomeController.STAGE_HOME.initialize(null, null);

											Aluguel auxAluguel = new Aluguel();
											auxAluguel = service.findOne(SELECT_ALUGUEL.getId());

											CaixaDialogo.msgInformacao("","Aluguel recebido com sucesso!\nEmitindo recibo...", "");

											try {
												GeradorPDF geradorPDF = new GeradorPDF();
												geradorPDF.gerar(auxAluguel);
											} catch (DocumentException e) {
												CaixaDialogo.msgError("Error", "Error ao gerar recibo de aluguel.", e.getMessage());
											}

											try {
												populandoTable(
														service.findAluguelByIDContrato(SELECT_CONTRATO.getId()));
												tbwAlugueis.refresh();
											} catch (MyExeption e) {
												CaixaDialogo.msgError("Error", e.getMessage(), "");
											}
										}
									} catch (MyExeption e) {
										CaixaDialogo.msgError("Error", e.getMessage(), "");
									}

								}
							} else {
								CaixaDialogo.msgInformacao("Aluguel Pago",
										"Informações do pagamento aluguel:\n\n" + "Locatário: "
												+ SELECT_ALUGUEL.getContrato().getLocatario().getNome() + "\t CPF: "
												+ SELECT_ALUGUEL.getContrato().getLocatario().getCpf() + "\nImóvel: "
												+ SELECT_ALUGUEL.getContrato().getImovel().getEndereco() + "\t Bairro: "
												+ SELECT_ALUGUEL.getContrato().getImovel().getBairro()
												+ "\n\nData pagamento: "
												+ Convert.CalendarToStr(SELECT_ALUGUEL.getDtPagamento())
												+ "\n\nValor aluguel: " + Convert.floatToStr(SELECT_ALUGUEL.getValor())
												+ "\nMulta(10%): " + Convert.floatToStr(SELECT_ALUGUEL.getMulta())
												+ "\nJuros ao mês(5%): " + Convert.floatToStr(SELECT_ALUGUEL.getJuros())
												+ "\n\nValor Total: " + Convert.floatToStr((SELECT_ALUGUEL.getValor()
														+ SELECT_ALUGUEL.getMulta() + SELECT_ALUGUEL.getJuros())),
										"");
							}
						}
					}
				}
			}
		});
	}

	private void populandoTable(List<Aluguel> alugueis) {

		this.tbcID.setCellValueFactory(new PropertyValueFactory("id"));

		this.tbcImovel.setCellValueFactory(new Callback<CellDataFeatures<Aluguel, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Aluguel, String> a) {
				if (a.getValue() != null && a.getValue().getContrato().getImovel() != null) {
					return new SimpleStringProperty(a.getValue().getContrato().getImovel().getEndereco());
				} else {
					return new SimpleStringProperty("Sem endereço");
				}
			}
		});

		this.tbcLocatario
				.setCellValueFactory(new Callback<CellDataFeatures<Aluguel, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Aluguel, String> a) {
						if (a.getValue() != null && a.getValue().getContrato().getLocatario() != null) {
							return new SimpleStringProperty(a.getValue().getContrato().getLocatario().getNome());
						} else {
							return new SimpleStringProperty("Sem nome");
						}
					}
				});

		this.tbcDtVencimento.setCellValueFactory(new PropertyValueFactory<Aluguel, Calendar>("dtVencimento"));
		final DateFormat dateFormat = DateFormat.getDateInstance();

		this.tbcDtVencimento.setCellFactory(col -> new TableCell<Aluguel, Calendar>() {
			@Override
			public void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
				} else {
					setText(dateFormat.format(item.getTime()));
				}
			}
		});

		this.tbcDtPagemento.setCellValueFactory(new PropertyValueFactory<Aluguel, Calendar>("dtPagamento"));
		this.tbcDtPagemento.setCellFactory(col -> new TableCell<Aluguel, Calendar>() {
			@Override
			public void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
				} else {
					setText(dateFormat.format(item.getTime()));
				}
			}
		});

		this.tbcValor.setCellValueFactory(new PropertyValueFactory("valor"));

		this.tbwAlugueis.setItems(FXCollections.observableArrayList(alugueis));
	}
}
