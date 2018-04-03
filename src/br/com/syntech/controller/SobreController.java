package br.com.syntech.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class SobreController implements Initializable {

	@FXML
	private ImageView imgSyntech;

	@FXML
	void onCancelLogin(ActionEvent event) {
		HomeController.STAGE_SOBRE.close();
		HomeController.STAGE_SOBRE = null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		imgSyntech.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/Logo.png")));

	}

}
