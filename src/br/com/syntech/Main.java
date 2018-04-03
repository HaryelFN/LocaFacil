package br.com.syntech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class Main extends Application {

	public static Scene SCENE_LOGIN;
	public static Stage STAGE_LOGIN;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/login.fxml"));

		Scene scene = new Scene(root);

		SCENE_LOGIN = scene;

		primaryStage.initStyle(StageStyle.TRANSPARENT);

		primaryStage.setTitle("Login - Project Manager");

		primaryStage.setScene(scene);

		STAGE_LOGIN = primaryStage;

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
