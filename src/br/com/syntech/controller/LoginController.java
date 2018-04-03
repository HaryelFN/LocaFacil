package br.com.syntech.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.com.syntech.Main;
import br.com.syntech.repository.impl.UsuarioRepositoryImpl;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.MyExeption;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class LoginController implements Initializable {

	@FXML
	private TextField txfLogin;

	@FXML
	private PasswordField txfSenha;

	@FXML
	private Label lblLoginInvalido;

	UsuarioRepositoryImpl repository = new UsuarioRepositoryImpl();

	@FXML
	void onCancelLogin(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void onEntrarLogin(ActionEvent event) throws SQLException, IOException {
		if (txfLogin.getText().isEmpty()) {
			lblLoginInvalido.setText("Digite o login corretamente!");
			lblLoginInvalido.setVisible(true);
		} else if (txfSenha.getText().isEmpty()) {
			lblLoginInvalido.setText("Digite a senha corretamente!");
			lblLoginInvalido.setVisible(true);
		} else {
			try {
				if (repository.isUsuario(txfLogin.getText(), txfSenha.getText()) != null) {
					
					Main.STAGE_LOGIN.close();
					Main.STAGE_LOGIN = null;
					
					Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/home.fxml"));

					Stage stage = new Stage();
					
					// MAXIMINIMIZA A TELA:
					javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
					Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

					stage.setScene(scene);
					stage.initStyle(StageStyle.DECORATED);
					stage.setTitle("LocaFácil - Gestão de Imóveis");
					stage.setResizable(true);

					stage.show();

				} else {
					lblLoginInvalido.setText("Login o senha inváido. Tente novamente.");
					lblLoginInvalido.setVisible(true);
				}
			} catch (MyExeption e) {
				CaixaDialogo.msgError("Error", e.getMessage(), "");
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblLoginInvalido.setVisible(false);
	}
}
