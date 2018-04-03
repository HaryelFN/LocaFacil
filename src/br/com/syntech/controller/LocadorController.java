package br.com.syntech.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.syntech.model.Locador;
import br.com.syntech.model.enums.Estado;
import br.com.syntech.model.enums.EstatoCivil;
import br.com.syntech.service.LocadorService;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.MyExeption;
import br.com.syntech.util.Valida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class LocadorController implements Initializable {

	private LocadorService service = new LocadorService();

	private Locador locador;

	@FXML
	private TextField txfCpf;

	@FXML
	private TextField txfRg;

	@FXML
	private TextField txfNome;

	@FXML
	private TextField txfCep;

	@FXML
	private TextField txfCidade;

	@FXML
	private TextField txfEndereco;

	@FXML
	private ComboBox<String> cmbEstadoCivil;

	@FXML
	private TextField txfBairro;

	@FXML
	private ComboBox<String> cmbUf;

	@FXML
	private TextField txfNacionalidade;

	@FXML
	private TextField txfProfissao;

	@FXML
	void onCancel(ActionEvent event) {
		closeView();
	}

	@FXML
	void onSave(ActionEvent event) {
		if (txfCpf.getText().isEmpty()) {
			txfCpf.setPromptText("Insira o numero do CPF!");
		} else if (!Valida.isCpf(txfCpf.getText())) {
			txfCpf.setText("");
			txfCpf.setPromptText("Número CPF inválido!");
		} else if (txfRg.getText().isEmpty()) {
			txfRg.setPromptText("Insira o numero do RG!");
		} else if (txfNome.getText().isEmpty()) {
			txfNome.setPromptText("Insira o nome!");
		} else if (txfCep.getText().isEmpty()) {
			txfCep.setPromptText("Insira a CEP!");
		} else if (cmbUf.getSelectionModel().getSelectedItem() == null) {
			CaixaDialogo.msgAlerta("Atenção", "Selecione o UF corretamente", "");
		} else if (txfCidade.getText().isEmpty()) {
			txfCidade.setPromptText("Insira a cidade!");
		} else if (txfBairro.getText().isEmpty()) {
			txfBairro.setPromptText("Insira o bairro!");
		} else if (txfEndereco.getText().isEmpty()) {
			txfEndereco.setPromptText("Insira o endereço!");
		} else if (txfNacionalidade.getText().isEmpty()) {
			txfNacionalidade.setPromptText("Insira o nacionalidade!");
		} else if (txfProfissao.getText().isEmpty()) {
			txfProfissao.setPromptText("Insira o profissão!");
		} else if (cmbEstadoCivil.getSelectionModel().getSelectedItem() == null) {
			CaixaDialogo.msgAlerta("Atenção", "Selecione o estado civil corretamente", "");
		} else {

			try {
				if (service.update(txfCpf.getText(), txfRg.getText(), txfNome.getText(), txfCep.getText(),
						cmbUf.getSelectionModel().getSelectedItem(), txfCidade.getText(), txfBairro.getText(),
						txfEndereco.getText(), txfNacionalidade.getText(),
						cmbEstadoCivil.getSelectionModel().getSelectedItem(), txfProfissao.getText())) {
					CaixaDialogo.msgInformacao("", "Locador atualizado com sucesso.", "");
					closeView();
				}
			} catch (MyExeption e) {
				CaixaDialogo.msgError("Error", "Erro ao atualizar locador", e.getMessage());
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		iniComboBox();

		try {
			locador = service.getLocador();

			if (locador != null) {
				txfCpf.setText(locador.getCpf());
				txfRg.setText(locador.getRg());
				txfNome.setText(locador.getNome());
				txfCep.setText(locador.getCep());
				cmbUf.getSelectionModel().select(locador.getUf());
				txfCidade.setText(locador.getCidade());
				txfBairro.setText(locador.getBairro());
				txfEndereco.setText(locador.getEndereco());
				txfNacionalidade.setText(locador.getNacionalidade());
				cmbEstadoCivil.getSelectionModel().select(locador.getEstadoCivil());
				txfProfissao.setText(locador.getProfissao());
			}

		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		}
	}

	private void iniComboBox() {
		cmbEstadoCivil.getItems().addAll(EstatoCivil.getAll());
		cmbUf.getItems().addAll(Estado.getAll());
	}

	private void closeView() {
		HomeController.STAGE_LOCADOR.close();
		HomeController.STAGE_LOCADOR = null;
	}
}
