package br.com.syntech.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.syntech.model.Imovel;
import br.com.syntech.model.enums.Estado;
import br.com.syntech.model.enums.TipoImovel;
import br.com.syntech.service.ImovelService;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.MaskField;
import br.com.syntech.util.MyExeption;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class ImovelController implements Initializable {

	ImovelService service = new ImovelService();

	private List<Imovel> listImoveis;

	private static Imovel SELECT_IMOVEL;

	@FXML
	private ImageView imgLogo;

	@FXML
	private TabPane tab;

	@FXML
	private Tab tabPesImoveis;

	@FXML
	private TableView<Imovel> tbwImoveis;

	@FXML
	private TableColumn<Imovel, String> tbcID;

	@FXML
	private TableColumn<Imovel, String> tbcEnd;

	@FXML
	private TableColumn<Imovel, String> tbcCep;

	@FXML
	private TableColumn<Imovel, String> tbcUf;

	@FXML
	private TableColumn<Imovel, String> tbcBairro;

	@FXML
	private TableColumn<Imovel, String> tbcCidade;

	@FXML
	private TableColumn<Imovel, String> tbcNumEscritura;

	@FXML
	private TableColumn<Imovel, String> tbcTipo;

	@FXML
	private TableColumn<Imovel, String> tcbSituacao;

	@FXML
	private TableColumn<Imovel, String> tbcVagaCaragem;

	@FXML
	private TableColumn<Imovel, String> tbcQtdQuarto;

	@FXML
	private TextField txfPesEnd;

	@FXML
	private Tab tabCadEditImoveis;

	@FXML
	private Label lblCadEditImovel;

	@FXML
	private Button btnCadEdit;

	@FXML
	private TextField txfCep;

	@FXML
	private TextField txfEnd;

	@FXML
	private TextField txfLargura;

	@FXML
	private ComboBox<String> cmbQtdQuarto;

	@FXML
	private TextField txfCidade;

	@FXML
	private ComboBox<String> cmbUf;

	@FXML
	private TextArea txaObs;

	@FXML
	private ComboBox<String> cmbTipo;

	@FXML
	private TextField txfBairro;

	@FXML
	private TextField txfRef;

	@FXML
	private TextField txfComprimento;

	@FXML
	private ComboBox<String> cmbQtdBanheiro;

	@FXML
	private ComboBox<String> cmbQtdCaragem;

	@FXML
	private TextField txfValorIPTU;

	@FXML
	private Button btnDelete;

	@FXML
	void onCancelCad(ActionEvent event) {
		closeView();
	}

	@FXML
	void onCancelPes(ActionEvent event) {
		closeView();
	}

	@FXML
	void onDelete(ActionEvent event) {
		if (CaixaDialogo.msgConfirmacao("Atenção", "Dejesa realmente excluir imóvel?", "")) {
			try {
				if (!service.delete(SELECT_IMOVEL.getId())) {
					CaixaDialogo.msgInformacao("", "Imóvel deletar com sucesso.", "");
					tab.getSelectionModel().select(0);
				}
			} catch (MyExeption e) {
				CaixaDialogo.msgError("Error", e.getMessage(), "");
			}
		}
	}

	@FXML
	void onSaveEdit(ActionEvent event) {
		if (txfCep.getText().isEmpty()) {
			txfCep.setPromptText("Digite o CEP corretamente!");
		} else if (cmbUf.getSelectionModel().getSelectedItem() == null) {
			CaixaDialogo.msgAlerta("Atenção", "Selecione o UF corretamente", "");
		} else if (txfCidade.getText().isEmpty()) {
			txfCidade.setPromptText("Digite a cidade corretamente");
		} else if (txfBairro.getText().isEmpty()) {
			txfBairro.setPromptText("Digite a bairro corretamente");
		} else if (txfEnd.getText().isEmpty()) {
			txfEnd.setPromptText("Digite o endereço corretamente.");
		} else if (cmbTipo.getSelectionModel().getSelectedItem() == null) {
			CaixaDialogo.msgAlerta("Atenção", "Selecione categoria do imóvel corretamente", "");
		} else {
			// CADASTRANDO IMOVEL:
			if (btnCadEdit.getText().equals("Salvar")) {

				try {
					if (service.saveImovel(txfEnd.getText(), txfBairro.getText(), txfCidade.getText(),
							cmbUf.getSelectionModel().getSelectedItem(), txfCep.getText(), txfRef.getText(),
							txfLargura.getText(), txfComprimento.getText(), txfValorIPTU.getText(),
							Integer.parseInt(cmbQtdQuarto.getSelectionModel().getSelectedItem()),
							Integer.parseInt(cmbQtdBanheiro.getSelectionModel().getSelectedItem()),
							Integer.parseInt(cmbQtdCaragem.getSelectionModel().getSelectedItem()),
							cmbTipo.getSelectionModel().getSelectedItem(), txaObs.getText())) {
						CaixaDialogo.msgInformacao("", "Imóvel cadastrado com sucesso", "");
						cleanFields();
					}
				} catch (MyExeption e) {
					CaixaDialogo.msgError("Atenção", e.getMessage(), "");
				}

			} else
			// ATUALIZANDO IMOVEL:
			if (btnCadEdit.getText().equals("Editar")) {
				try {
					if (service.updateImovel(SELECT_IMOVEL.getId(), txfEnd.getText(), txfBairro.getText(),
							txfCidade.getText(), cmbUf.getSelectionModel().getSelectedItem(), txfCep.getText(),
							txfRef.getText(), txfLargura.getText(), txfComprimento.getText(), txfValorIPTU.getText(),
							Integer.parseInt(cmbQtdQuarto.getSelectionModel().getSelectedItem()),
							Integer.parseInt(cmbQtdBanheiro.getSelectionModel().getSelectedItem()),
							Integer.parseInt(cmbQtdCaragem.getSelectionModel().getSelectedItem()),
							cmbTipo.getSelectionModel().getSelectedItem(), SELECT_IMOVEL.getSituacaoImovel(),
							txaObs.getText())) {
						CaixaDialogo.msgInformacao("", "Imóvel Atualizado com sucesso", "");
						tab.getSelectionModel().select(0);
					}
				} catch (MyExeption e) {
					CaixaDialogo.msgAlerta("Atenção", e.getMessage(), "");
				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		MaskField.numericField(txfCep);
		MaskField.monetaryField(txfLargura);
		MaskField.monetaryField(txfComprimento);
		MaskField.monetaryField(txfValorIPTU);

		initComboBox();
		cleanFields();

		try {
			populandoTable(service.getAll());
		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		}

		eventFindImovelByEnd();

		selectImovelTable();
		clickTabPes();
	}

	private void eventFindImovelByEnd() {
		txfPesEnd.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				try {
					listImoveis = service.getImovelByEnd(txfPesEnd.getText().toString(), false);
				} catch (MyExeption e) {
					CaixaDialogo.msgError("Error", e.getMessage(), "");
				}
				populandoTable(listImoveis);
			}
		});
	}

	private void selectImovelTable() {
		tbwImoveis.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						SELECT_IMOVEL = tbwImoveis.getSelectionModel().getSelectedItem();

						if (SELECT_IMOVEL != null) {

							txfCep.setText(SELECT_IMOVEL.getCep());
							cmbUf.getSelectionModel().select(SELECT_IMOVEL.getUf());
							txfCidade.setText(SELECT_IMOVEL.getCidade());
							txfBairro.setText(SELECT_IMOVEL.getBairro());
							txfEnd.setText(SELECT_IMOVEL.getEndereco());
							txfRef.setText(SELECT_IMOVEL.getReferencia());
							cmbTipo.getSelectionModel().select(SELECT_IMOVEL.getTipoImovel());
							cmbQtdQuarto.getSelectionModel().select(SELECT_IMOVEL.getQtdQuartos());
							cmbQtdBanheiro.getSelectionModel().select(SELECT_IMOVEL.getQtdBanheiros());
							cmbQtdCaragem.getSelectionModel().select(SELECT_IMOVEL.getVagasCaragem());
							txfLargura.setText(String.valueOf(SELECT_IMOVEL.getLargura()));
							txfComprimento.setText(String.valueOf(SELECT_IMOVEL.getComprimento()));
							txfValorIPTU.setText(String.valueOf(SELECT_IMOVEL.getValorIptu()));
							txaObs.setText(SELECT_IMOVEL.getObs());

							btnDelete.setVisible(true);
							tabCadEditImoveis.setText("Editar Imóvel");
							lblCadEditImovel.setText("Editar Imóvel");
							btnCadEdit.setText("Editar");

							tab.getSelectionModel().select(1);
						}

					}
				}
			}
		});
	}

	private void populandoTable(List<Imovel> imoveis) {

		this.tbcID.setCellValueFactory(new PropertyValueFactory("id"));
		this.tbcEnd.setCellValueFactory(new PropertyValueFactory("endereco"));
		this.tbcCep.setCellValueFactory(new PropertyValueFactory("cep"));
		this.tbcUf.setCellValueFactory(new PropertyValueFactory("uf"));
		this.tbcBairro.setCellValueFactory(new PropertyValueFactory("bairro"));
		this.tbcCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
		this.tbcTipo.setCellValueFactory(new PropertyValueFactory("tipoImovel"));
		this.tcbSituacao.setCellValueFactory(new PropertyValueFactory("situacaoImovel"));
		this.tbcQtdQuarto.setCellValueFactory(new PropertyValueFactory("qtdQuartos"));
		this.tbcVagaCaragem.setCellValueFactory(new PropertyValueFactory("vagasCaragem"));

		this.tbwImoveis.setItems(FXCollections.observableArrayList(imoveis));
	}

	private void clickTabPes() {

		tabPesImoveis.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event t) {
				if (tabPesImoveis.isSelected()) {

					SELECT_IMOVEL = null;
					cleanFields();

					try {
						tabCadEditImoveis.setText("Cadastrar Imóvel");
						lblCadEditImovel.setText("Cadastrar Imóvel");
						btnCadEdit.setText("Salvar");
						btnDelete.setVisible(false);

						populandoTable(service.getAll());

						tbwImoveis.refresh();

					} catch (MyExeption e) {
						CaixaDialogo.msgError("Error", e.getMessage(), "");
					}
				}
			}
		});
	}

	private void closeView() {
		HomeController.STAGE_IMOVEL.close();
		HomeController.STAGE_IMOVEL = null;
	}

	private void cleanFields() {
		txfCep.setText("75460-000");
		cmbUf.getSelectionModel().select(8);
		txfCidade.setText("Nerópolis");
		txfBairro.setText("");
		txfEnd.setText("");
		txfRef.setText("");
		cmbTipo.getSelectionModel().select(1);
		cmbQtdQuarto.getSelectionModel().select(3);
		cmbQtdBanheiro.getSelectionModel().select(2);
		cmbQtdCaragem.getSelectionModel().select(1);
		txfLargura.setText("1200");
		txfComprimento.setText("3000");
		txfValorIPTU.setText("0,00");
		txaObs.setText("");
	}

	private void initComboBox() {

		cmbUf.getItems().addAll(Estado.getAll());
		cmbUf.getSelectionModel().select(8);

		cmbTipo.getItems().addAll(TipoImovel.getAll());
		cmbTipo.getSelectionModel().select(1);

		cmbQtdQuarto.getItems().clear();
		cmbQtdQuarto.getItems().addAll("0", "1", "2", "3", "4", "5");
		cmbQtdQuarto.getSelectionModel().select(3);

		cmbQtdBanheiro.getItems().clear();
		cmbQtdBanheiro.getItems().addAll("0", "1", "2", "3", "4", "5");
		cmbQtdBanheiro.getSelectionModel().select(2);

		cmbQtdCaragem.getItems().clear();
		cmbQtdCaragem.getItems().addAll("0", "1", "2", "3", "4", "5");
		cmbQtdCaragem.getSelectionModel().select(1);
	}

}
