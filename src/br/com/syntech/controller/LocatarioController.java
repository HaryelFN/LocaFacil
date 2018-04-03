package br.com.syntech.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.syntech.model.Locatario;
import br.com.syntech.model.enums.EstatoCivil;
import br.com.syntech.service.LocatarioService;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.MaskField;
import br.com.syntech.util.MyExeption;
import br.com.syntech.util.Valida;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class LocatarioController implements Initializable {

	LocatarioService service = new LocatarioService();

	private List<Locatario> listLocatarios;

	private static Locatario SELECT_LOCATARIO;

	@FXML
	private TabPane tab;

	@FXML
	private Tab tabPesLocatarios;

	@FXML
	private Tab tabCadEditLocatarios;

	@FXML
	private TableView<Locatario> tbwLocatarios;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioID;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioNome;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioCPF;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioRG;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioProfissao;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioEstadoCivil;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioTel;

	@FXML
	private TableColumn<Locatario, String> tbcLocatarioEmail;

	@FXML
	private TextField txfPesNome;

	@FXML
	private TextField txfPesCPF;

	@FXML
	private Label lblCadEditLocatarios;

	@FXML
	private Button btnCadEdit;

	@FXML
	private TextField txfCadRG;

	@FXML
	private TextField txfCadNome;

	@FXML
	private TextField txfCadNacionalidade;

	@FXML
	private TextField txfCadEmail;

	@FXML
	private TextField txfCadProfissao;

	@FXML
	private ComboBox<String> cmbCadEstadoCivil;

	@FXML
	private TextField txfCadCPF;

	@FXML
	private TextField txfCadTel;

	@FXML
	private Button btnDelete;

	@FXML
	void onCancel(ActionEvent event) {
		closeView();
	}

	@FXML
	void onCancelCad(ActionEvent event) {
		closeView();
	}

	@FXML
	void onDelete(ActionEvent event) {
		if (CaixaDialogo.msgConfirmacao("Atenção", "Dejesa realmente excluir locatário?", "")) {
			try {
				if (!service.delete(SELECT_LOCATARIO.getId())) {
					CaixaDialogo.msgInformacao("", "Locatário deletar com sucesso.", "");
					tab.getSelectionModel().select(0);
				}
			} catch (MyExeption e) {
				CaixaDialogo.msgError("Error", e.getMessage(), "");
			}
		}
	}

	@FXML
	void onSaveEdit(ActionEvent event) {
		if (txfCadCPF.getText().isEmpty()) {
			txfCadCPF.setPromptText("Digite CPF corretamente!");
		} else if (!Valida.isCpf(txfCadCPF.getText())) {
			txfCadCPF.setText("");
			txfCadCPF.setPromptText("Número CPF inválido!");
		} else if (txfCadRG.getText().isEmpty()) {
			txfCadRG.setPromptText("Digite a RG corretamente");
		} else if (txfCadNacionalidade.getText().isEmpty()) {
			txfCadNacionalidade.setPromptText("Digite a nacionalidade corretamente");
		} else if (cmbCadEstadoCivil.getSelectionModel().getSelectedItem() == null) {
			CaixaDialogo.msgInformacao("Atenção", "Selecione o estado civil corretamente", "");
		} else if (txfCadNome.getText().isEmpty()) {
			txfCadNome.setPromptText("Digite o nome corretamente.");
		} else if (txfCadProfissao.getText().isEmpty()) {
			txfCadProfissao.setPromptText("Digite o profissão corretamente.");
		} else {
			if (btnCadEdit.getText().equals("Salvar")) {
				// INSERINDO IMOVEL:
				try {
					if (service.save(txfCadCPF.getText(), txfCadRG.getText(), txfCadNome.getText(),
							txfCadNacionalidade.getText(), cmbCadEstadoCivil.getSelectionModel().getSelectedItem(),
							txfCadProfissao.getText(), txfCadTel.getText(), txfCadEmail.getText())) {

						CaixaDialogo.msgInformacao("", "Locatário cadastrado com sucesso.", "");
						cleanFields();
					}
				} catch (Exception e) {
					CaixaDialogo.msgError("Atenção", e.getMessage(), "");
				}
			} else if (btnCadEdit.getText().equals("Editar")) {

				// ATUALIZA IMOVEL:
				try {
					if (service.update(SELECT_LOCATARIO.getId(), txfCadCPF.getText(), txfCadRG.getText(),
							txfCadNome.getText(), txfCadNacionalidade.getText(),
							cmbCadEstadoCivil.getSelectionModel().getSelectedItem(), txfCadProfissao.getText(),
							txfCadTel.getText(), txfCadEmail.getText())) {

						CaixaDialogo.msgInformacao("", "Locatário atualizado com sucesso.", "");
						tab.getSelectionModel().select(0);
					}
				} catch (MyExeption e) {
					CaixaDialogo.msgError("Atenção", e.getMessage(), "");
				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		MaskField.numericField(txfCadCPF);
		MaskField.numericField(txfCadRG);
		MaskField.numericField(txfCadTel);

		try {
			populandoTable(service.getAll());
		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initComboBox();

		eventFindByCPF();
		eventFindByNome();

		selectLocatarioTable();
		clickTabPes();

	}

	private void eventFindByCPF() {
		txfPesCPF.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				try {
					listLocatarios = service.getByCpf(txfPesCPF.getText().toString());
				} catch (MyExeption e) {
					CaixaDialogo.msgError("Error", e.getMessage(), "");
				}
				populandoTable(listLocatarios);
			}
		});
	}

	private void eventFindByNome() {
		txfPesNome.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				try {
					listLocatarios = service.getByNome(txfPesNome.getText().toString());
				} catch (MyExeption e) {
					CaixaDialogo.msgError("Error", e.getMessage(), "");
				}
				populandoTable(listLocatarios);
			}
		});
	}

	private void selectLocatarioTable() {
		tbwLocatarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						SELECT_LOCATARIO = tbwLocatarios.getSelectionModel().getSelectedItem();

						if (SELECT_LOCATARIO != null) {

							tabCadEditLocatarios.setText("Editar Locatário");
							lblCadEditLocatarios.setText("Editar Locatário");

							txfCadCPF.setText(SELECT_LOCATARIO.getCpf());
							txfCadRG.setText(SELECT_LOCATARIO.getRg());

							txfCadCPF.setDisable(true);
							txfCadRG.setDisable(true);

							txfCadNacionalidade.setText(SELECT_LOCATARIO.getNacionalidade());
							cmbCadEstadoCivil.getSelectionModel().select(SELECT_LOCATARIO.getEstadoCivil());
							txfCadNome.setText(SELECT_LOCATARIO.getNome());
							txfCadTel.setText(SELECT_LOCATARIO.getTelefone());
							txfCadEmail.setText(SELECT_LOCATARIO.getEmail());
							txfCadProfissao.setText(SELECT_LOCATARIO.getProfissao());

							btnDelete.setVisible(true);
							btnCadEdit.setText("Editar");

							tab.getSelectionModel().select(1);
						}

					}
				}
			}
		});
	}

	private void populandoTable(List<Locatario> locatarios) {

		this.tbcLocatarioID.setCellValueFactory(new PropertyValueFactory("id"));
		this.tbcLocatarioNome.setCellValueFactory(new PropertyValueFactory("nome"));
		this.tbcLocatarioCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
		this.tbcLocatarioRG.setCellValueFactory(new PropertyValueFactory("rg"));
		this.tbcLocatarioProfissao.setCellValueFactory(new PropertyValueFactory("profissao"));
		this.tbcLocatarioEstadoCivil.setCellValueFactory(new PropertyValueFactory("estadoCivil"));
		this.tbcLocatarioTel.setCellValueFactory(new PropertyValueFactory("telefone"));
		this.tbcLocatarioEmail.setCellValueFactory(new PropertyValueFactory("email"));

		this.tbwLocatarios.setItems(FXCollections.observableArrayList(locatarios));
	}

	private void clickTabPes() {

		tabPesLocatarios.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event t) {
				if (tabPesLocatarios.isSelected()) {

					SELECT_LOCATARIO = null;
					cleanFields();

					tabCadEditLocatarios.setText("Cadastrar Locatário");
					lblCadEditLocatarios.setText("Cadastrar Locatário");
					txfCadCPF.setDisable(false);
					txfCadRG.setDisable(false);
					btnCadEdit.setText("Salvar");
					btnDelete.setVisible(false);

					try {

						populandoTable(service.getAll());

						tbwLocatarios.refresh();

					} catch (MyExeption e) {
						CaixaDialogo.msgError("Error", e.getMessage(), "");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void closeView() {
		HomeController.STAGE_LOCATARIO.close();
		HomeController.STAGE_LOCATARIO = null;
	}

	private void cleanFields() {
		txfCadCPF.setText("");
		txfCadRG.setText("");
		txfCadNome.setText("");
		txfCadNacionalidade.setText("Brasileiro(a)");
		cmbCadEstadoCivil.getSelectionModel().clearSelection();
		txfCadTel.setText("");
		txfCadEmail.setText("");
		txfCadProfissao.setText("Autônomo");
	}

	private void initComboBox() {
		cmbCadEstadoCivil.getItems().addAll(EstatoCivil.getAll());
	}
}
