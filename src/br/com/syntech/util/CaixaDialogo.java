package br.com.syntech.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class CaixaDialogo {

	public static void msgAlerta(String titulo, String cabecalho, String texto) {
		Alert msg = new Alert(Alert.AlertType.WARNING);

		msg.setTitle(titulo);
		msg.setHeaderText(cabecalho);
		msg.setContentText(texto);

		msg.setResizable(false);
		msg.show();
	}

	public static void msgError(String msgTitulo, String msg, String msgContexto) {

		Alert dialogoAviso = new Alert(Alert.AlertType.ERROR);
		dialogoAviso.setTitle(msgTitulo);
		dialogoAviso.setHeaderText(msg);
		dialogoAviso.setContentText(msgContexto);

		dialogoAviso.showAndWait();

	}

	public static void msgInformacao(String msgTitulo, String msg, String msgContexto) {

		Alert dialogoAviso = new Alert(Alert.AlertType.INFORMATION);
		dialogoAviso.setTitle(msgTitulo);
		dialogoAviso.setHeaderText(msg);
		dialogoAviso.setContentText(msgContexto);

		dialogoAviso.showAndWait();

	}

	public static boolean msgConfirmacao(String msgTitulo, String msg, String msgContexto) {

		Alert dialogoConfirmacao = new Alert(AlertType.CONFIRMATION);
		dialogoConfirmacao.setTitle(msgTitulo);
		dialogoConfirmacao.setHeaderText(msg);
		dialogoConfirmacao.setContentText(msgContexto);

		dialogoConfirmacao.getButtonTypes().clear();
		dialogoConfirmacao.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

		// Deactivate Defaultbehavior for yes-Button:
		Button yesButton = (Button) dialogoConfirmacao.getDialogPane().lookupButton(ButtonType.YES);
		yesButton.setDefaultButton(false);

		// Activate Defaultbehavior for no-Button:
		Button noButton = (Button) dialogoConfirmacao.getDialogPane().lookupButton(ButtonType.NO);
		noButton.setDefaultButton(true);

		final Optional<ButtonType> result = dialogoConfirmacao.showAndWait();
		return result.get() == ButtonType.YES;
	}
}
