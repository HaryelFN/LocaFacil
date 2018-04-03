package br.com.syntech.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import br.com.syntech.model.Contrato;
import br.com.syntech.model.Imovel;
import br.com.syntech.model.Locatario;
import br.com.syntech.service.ContratoService;
import br.com.syntech.service.ImovelService;
import br.com.syntech.service.LocatarioService;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.Convert;
import br.com.syntech.util.MaskField;
import br.com.syntech.util.MyExeption;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ContratoController implements Initializable {

	public List<Contrato> listContrato = new ArrayList<>();

	static Contrato SELECT_CONTRATO;

	public List<Imovel> listImovel = new ArrayList<>();

	static Imovel SELECT_IMOVEL;

	public List<Locatario> listLocatario = new ArrayList<>();

	static Locatario SELECT_LOCATARIO;

	private ContratoService service = new ContratoService();

	private ImovelService serviceImovel = new ImovelService();

	private LocatarioService serviceLocatario = new LocatarioService();

	@FXML
	private TabPane tab;

	@FXML
	private Tab tabPesContrato;

	@FXML
	private TableView<Contrato> tbwContratos;

	@FXML
	private TableColumn<Contrato, String> tbcID;

	@FXML
	private TableColumn<Contrato, String> tbcImovel;

	@FXML
	private TableColumn<Contrato, String> tbcLocatario;

	@FXML
	private TableColumn<Contrato, String> tbcValor;

	@FXML
	private TableColumn<Contrato, String> tbcDataPag;

	@FXML
	private TableColumn<Contrato, Calendar> tbcInicioContrato;

	@FXML
	private TableColumn<Contrato, Calendar> tbcFimContrato;

	@FXML
	private TextField txfPesContrato;

	@FXML
	private ToggleGroup grupo;

	@FXML
	private RadioButton rdbImovel;

	@FXML
	private RadioButton rdbLocatario;

	@FXML
	private Tab tabCadEditContrato;

	@FXML
	private Label lblCadEditContrato;

	@FXML
	private Button btnCadEditContrato;

	@FXML
	private TextField txfPesImovel;

	@FXML
	private TextField txfPesLocatario;

	@FXML
	private TextField txfDuracao;

	@FXML
	private TextField txfDiaPagamento;

	@FXML
	private TextArea txaObs;

	@FXML
	private DatePicker dtpDataInicio;

	@FXML
	private DatePicker dtpDataFinal;

	@FXML
	private TextField txfValorPagamento;

	@FXML
	private ListView<Imovel> listImovelFind;

	@FXML
	private ListView<Locatario> listLocatarioFind;

	@FXML
	private Button btnDeleteContrato;

	@FXML
	void onCancelCadContrato(ActionEvent event) {
		closeView();
	}

	@FXML
	void onCancelPesImovel(ActionEvent event) {
		closeView();
	}

	@FXML
	void onDelete(ActionEvent event) {
		if (CaixaDialogo.msgConfirmacao("Atenção!!",
				"Ao deletar o contrado desse imóvel, todo o histórico de aluguéis serão excluidos.",
				"Não recuperando mais!.")) {
			try {
				if (!service.delete(SELECT_CONTRATO.getId())) {
					CaixaDialogo.msgInformacao("", "Contrado deletar com sucesso.", "");
					tab.getSelectionModel().select(0);
					
					HomeController.STAGE_HOME.initialize(null, null);
				}
			} catch (MyExeption e) {
				CaixaDialogo.msgError("Error", e.getMessage(), "");
			}
		}
	}

	@FXML
	void onSaveEditContrato(ActionEvent event) throws ParseException {
		if (SELECT_IMOVEL == null) {
			CaixaDialogo.msgAlerta("Atenção", "Selecione o imóvel corretamente!", "");
		} else if (SELECT_LOCATARIO == null) {
			CaixaDialogo.msgAlerta("Atenção", "Selecione o locatário corretamente!", "");
		} else if (txfDuracao.getText().isEmpty()) {
			txfDuracao.setPromptText("Informe a duração");
		} else if (txfValorPagamento.getText().isEmpty()) {
			CaixaDialogo.msgAlerta("Atenção", "Informe o valor do aluguel corretamente!", "");
		} else {

			LocalDate localDate1 = dtpDataInicio.getValue();
			Calendar c1 = GregorianCalendar.from(localDate1.atStartOfDay(ZoneId.of("America/New_York")));

			LocalDate localDate2 = dtpDataFinal.getValue();
			Calendar c2 = GregorianCalendar.from(localDate2.atStartOfDay(ZoneId.of("America/New_York")));

			try {
				if (service.saveContrato(c1, c2, Convert.srtToFloat(txfValorPagamento.getText()),
						Integer.parseInt(txfDiaPagamento.getText()), Integer.parseInt(txfDuracao.getText()),
						txaObs.getText(), SELECT_IMOVEL, SELECT_LOCATARIO)) {
					CaixaDialogo.msgInformacao("", "Contrato cadastrado com sucesso", "");
					txfDuracao.setText("");
					tab.getSelectionModel().select(0);
					HomeController.STAGE_HOME.initialize(null, null);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (MyExeption e) {
				CaixaDialogo.msgError("Error", e.getMessage(), "");
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		MaskField.numericField(txfValorPagamento);
		MaskField.numericField(txfDuracao);
		MaskField.monetaryField(txfValorPagamento);
		dtpDataInicio.setValue(LocalDate.now());

		eventFindContrato();

		selectContratoTable();

		try {
			populandoTable(service.getAll());
		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		}

		clickTabPes();

		eventOpenListView(txfPesImovel);
		eventOpenListView(txfPesLocatario);

		calcDatasContrato();

		eventClickDtpDataini();

	}

	private void selectContratoTable() {
		tbwContratos.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						SELECT_CONTRATO = tbwContratos.getSelectionModel().getSelectedItem();

						if (SELECT_CONTRATO != null) {

							disableFileds(true);

							txfPesImovel.setText(SELECT_CONTRATO.getImovel().getEndereco());
							txfPesLocatario.setText(SELECT_CONTRATO.getLocatario().getNome());

							// =========================================================================================================================
							// dtpDataInicio.setValue(value);
							// dtpDataFinal.setValue(value);

							txfDuracao.setText(String.valueOf(SELECT_CONTRATO.getDuracao()));
							txfDiaPagamento.setText(String.valueOf(SELECT_CONTRATO.getDiaVencimento()));
							txfValorPagamento.setText(String.valueOf(SELECT_CONTRATO.getValorAluguel()));
							txaObs.setText(SELECT_CONTRATO.getObs());

							tabCadEditContrato.setText("Editar Contrato");
							lblCadEditContrato.setText("Editar Contrato");
							btnDeleteContrato.setVisible(true);
							btnCadEditContrato.setVisible(false);

							tab.getSelectionModel().select(1);
						}
					}
				}
			}
		});
	}

	private void eventClickDtpDataini() {
		dtpDataInicio.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

					txfDuracao.setText("");
					dtpDataFinal.setValue(null);
					txfDiaPagamento.setText("");
				}
			}
		});
	}

	// EVENTO CONTABILIZAR DATAS:
	private void calcDatasContrato() {
		txfDuracao.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				try {
					dtpDataFinal.setValue(dtpDataInicio.getValue()
							.plusMonths((long) Float.parseFloat(txfDuracao.getText().toString())).plusDays(-1));
					txfDiaPagamento.setText(dtpDataInicio.getValue().toString().substring(8, 10));
				} catch (NumberFormatException e) {
					dtpDataFinal.setValue(null);
					txfDiaPagamento.setText("");
				}
			}
		});
	}

	// EVENTO LISTVIEW:
	private void eventOpenListView(TextField textField) {
		textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 1) {

						try {

							if (textField.getId().equals("txfPesImovel")) {

								populaListViewImovel(txfPesImovel.getText().toString());

								listImovelFind.setVisible(true);

								findListViewImovel();
								selectListViewImovel();
							}

							if (textField.getId().equals("txfPesLocatario")) {

								populaListViewLocatario(txfPesLocatario.getText().toString());

								listLocatarioFind.setVisible(true);

								findListViewLocatario();
								selectListViewLocatario();
							}

						} catch (MyExeption e) {
							CaixaDialogo.msgError("Error", e.getMessage(), "");
						}
					}
				}
			}
		});
	}

	// EVENTO FIND LISTVIEW IMOVEL:
	private void findListViewImovel() {
		txfPesImovel.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				try {
					populaListViewImovel(txfPesImovel.getText().toString());
				} catch (MyExeption e) {
					CaixaDialogo.msgError("Error", e.getMessage(), "");
				}
			}
		});
	}

	// EVENTO FIND LISTVIEW LOCATARIO:
	private void findListViewLocatario() {
		txfPesLocatario.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				try {
					populaListViewLocatario(txfPesLocatario.getText().toString());
				} catch (MyExeption e) {
					CaixaDialogo.msgError("Error", e.getMessage(), "");
				}
			}
		});
	}

	// SOBRESCREVENDO METODO DO LISTVIEW IMOVEL:
	private void populaListViewImovel(String end) throws MyExeption {

		listImovel = serviceImovel.getImovelByEnd(end, true);

		ObservableList<Imovel> itens = FXCollections.observableArrayList(listImovel);

		this.listImovelFind.setItems(itens);

		this.listImovelFind.setCellFactory(new Callback<ListView<Imovel>, ListCell<Imovel>>() {

			@Override
			public ListCell<Imovel> call(ListView<Imovel> p) {

				ListCell<Imovel> cell = new ListCell<Imovel>() {

					@Override
					protected void updateItem(Imovel i, boolean bln) {
						super.updateItem(i, bln);
						if (i != null) {
							setText(i.getId() + " : " + i.getEndereco());
						}
					}

				};

				return cell;
			}
		});
	}

	// SOBRESCREVENDO METODO DO LISTVIEW LOCATARIO:
	private void populaListViewLocatario(String nome) throws MyExeption {

		listLocatario = serviceLocatario.getByNome(nome);

		ObservableList<Locatario> itens = FXCollections.observableArrayList(listLocatario);

		this.listLocatarioFind.setItems(itens);

		this.listLocatarioFind.setCellFactory(new Callback<ListView<Locatario>, ListCell<Locatario>>() {

			@Override
			public ListCell<Locatario> call(ListView<Locatario> p) {

				ListCell<Locatario> cell = new ListCell<Locatario>() {

					@Override
					protected void updateItem(Locatario i, boolean bln) {
						super.updateItem(i, bln);
						if (i != null) {
							setText(i.getId() + " : " + i.getNome() + " / CPF: " + i.getCpf());
						}
					}

				};

				return cell;
			}
		});
	}

	// EVENTO LISTVIEW SELECT IMOVEL:
	private void selectListViewImovel() {

		listImovelFind.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						if (listImovelFind.getSelectionModel().getSelectedItem() != null) {

							SELECT_IMOVEL = listImovelFind.getSelectionModel().getSelectedItem();

							txfPesImovel.setText(SELECT_IMOVEL.getEndereco());

							listImovelFind.setVisible(false);

							txfPesLocatario.setFocusTraversable(true);
							txfPesLocatario.requestFocus();
						}
					}
				}
			}
		});
	}

	// EVENTO LISTVIEW SELECT LOCATARIO:
	private void selectListViewLocatario() {

		listLocatarioFind.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						if (listLocatarioFind.getSelectionModel().getSelectedItem() != null) {

							SELECT_LOCATARIO = listLocatarioFind.getSelectionModel().getSelectedItem();

							txfPesLocatario.setText(SELECT_LOCATARIO.getNome());

							listLocatarioFind.setVisible(false);

							dtpDataInicio.setFocusTraversable(true);
							dtpDataInicio.requestFocus();
						}
					}
				}
			}
		});
	}

	private void eventFindContrato() {
		txfPesContrato.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {

				try {
					if (rdbImovel.isSelected()) {
						listContrato = service.getByImovel(txfPesContrato.getText().toString());
					} else {
						listContrato = service.getByLocatario(txfPesContrato.getText().toString());
					}

				} catch (MyExeption e) {
					CaixaDialogo.msgError("Error", e.getMessage(), "");
				}
				populandoTable(listContrato);
			}
		});
	}

	private void clickTabPes() {

		tabPesContrato.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event t) {
				if (tabPesContrato.isSelected()) {

					SELECT_CONTRATO = null;
					cleanFields();

					try {

						disableFileds(false);

						tabCadEditContrato.setText("Cadastrar Contrato");
						lblCadEditContrato.setText("Cadastrar Contrato");
						btnCadEditContrato.setText("Salvar");
						btnDeleteContrato.setVisible(false);
						btnCadEditContrato.setVisible(true);

						populandoTable(service.getAll());

						tbwContratos.refresh();

					} catch (MyExeption e) {
						CaixaDialogo.msgError("Error", e.getMessage(), "");
					}
				}
			}
		});
	}

	private void populandoTable(List<Contrato> contratos) {

		this.tbcID.setCellValueFactory(new PropertyValueFactory("id"));

		this.tbcImovel.setCellValueFactory(new Callback<CellDataFeatures<Contrato, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Contrato, String> c) {
				// p.getValue() returns the PersonType instance for a particular TableView row

				if (c.getValue() != null && c.getValue().getImovel() != null) {
					return new SimpleStringProperty(c.getValue().getImovel().getEndereco());
				} else {
					return new SimpleStringProperty("Sem endereço");
				}
			}
		});

		this.tbcLocatario
				.setCellValueFactory(new Callback<CellDataFeatures<Contrato, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Contrato, String> c) {
						if (c.getValue() != null && c.getValue().getLocatario() != null) {
							return new SimpleStringProperty(c.getValue().getLocatario().getNome());
						} else {
							return new SimpleStringProperty("Sem nome");
						}
					}
				});

		this.tbcValor.setCellValueFactory(new PropertyValueFactory("valorAluguel"));
		this.tbcDataPag.setCellValueFactory(new PropertyValueFactory("diaVencimento"));

		this.tbcInicioContrato.setCellValueFactory(new PropertyValueFactory<Contrato, Calendar>("dataLocacao"));
		final DateFormat dateFormat = DateFormat.getDateInstance();
		this.tbcInicioContrato.setCellFactory(col -> new TableCell<Contrato, Calendar>() {
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

		this.tbcFimContrato.setCellValueFactory(new PropertyValueFactory<Contrato, Calendar>("fimLocacao"));
		this.tbcFimContrato.setCellFactory(col -> new TableCell<Contrato, Calendar>() {
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

		this.tbwContratos.setItems(FXCollections.observableArrayList(contratos));
	}

	private void cleanFields() {
		txfPesImovel.setText("");
		txfPesLocatario.setText("");
		dtpDataInicio.setValue(LocalDate.now());
		txfDiaPagamento.setText("");
		txfValorPagamento.setText("");
		txaObs.setText("");
	}

	private void disableFileds(boolean aux) {
		txfPesImovel.setDisable(aux);
		txfPesLocatario.setDisable(aux);
		dtpDataInicio.setDisable(aux);
		txfDuracao.setDisable(aux);
		txfValorPagamento.setDisable(aux);
		txaObs.setDisable(aux);
	}

	private void closeView() {
		HomeController.STAGE_CONTRATO.close();
		HomeController.STAGE_CONTRATO = null;
	}
}
