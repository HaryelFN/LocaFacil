package br.com.syntech.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.DocumentException;

import br.com.syntech.model.Aluguel;
import br.com.syntech.model.Contrato;
import br.com.syntech.model.DataStackedChat;
import br.com.syntech.model.Imovel;
import br.com.syntech.model.Notificacao;
import br.com.syntech.service.AluguelService;
import br.com.syntech.service.ContratoService;
import br.com.syntech.service.ImovelService;
import br.com.syntech.service.NotificacaoService;
import br.com.syntech.util.CaixaDialogo;
import br.com.syntech.util.GeradorPDF;
import br.com.syntech.util.MyExeption;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Haryel F. Nascimento
 * @since 09-02-2018
 */
public class HomeController implements Initializable {

	public static HomeController STAGE_HOME;

	private ImovelService imovelService = new ImovelService();

	private List<Imovel> imoveis;

	private ContratoService contratoService = new ContratoService();

	private List<Contrato> contratos;

	public static Stage STAGE_IMOVEL;

	public static Stage STAGE_LOCADOR;

	public static Stage STAGE_SOBRE;

	public static Stage STAGE_LOCATARIO;

	public static Stage STAGE_CONTRATO;

	public static Stage STAGE_ALUGUEL;

	private static NotificacaoService notificacaoService = new NotificacaoService();

	public static Notificacao SELECT_NOTIFICACAO;

	private AluguelService aluguelService = new AluguelService();

	@FXML
	private ImageView imgLogo;

	@FXML
	private ImageView imgSino;

	@FXML
	private ImageView imgCasa;

	@FXML
	private ImageView imgContrato;

	@FXML
	private ImageView imgSobre;

	@FXML
	private ImageView imgConfig;

	@FXML
	private ImageView imgExit;

	@FXML
	private Label lblTotalimoveis;

	@FXML
	private Label lblTotalContratos;

	@FXML
	private StackPane stackPane;

	@FXML
	private TableView<Notificacao> tbwNotificacao;

	@FXML
	private TableColumn<Notificacao, String> tbcNotificacaoID;

	@FXML
	private TableColumn<Notificacao, String> tbcNotificacaoAssunto;

	@FXML
	private TableColumn<Notificacao, Calendar> tbcNotificacaoData;

	@FXML
	void onBtnAluguels(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/aluguel.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Informações Aluguel");
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.setResizable(false);

		STAGE_ALUGUEL = stage;

		stage.show();
	}

	@FXML
	void onBtnConfig(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/locador.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Informações Locador");
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.setResizable(false);

		STAGE_LOCADOR = stage;

		stage.show();
	}

	@FXML
	void onBtnContratos(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/contrato.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Contratos");
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.setResizable(false);

		STAGE_CONTRATO = stage;

		stage.show();
	}

	@FXML
	void onBtnImoveis(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/imovel.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Imóveis");
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.setResizable(false);

		STAGE_IMOVEL = stage;

		stage.show();
	}

	@FXML
	void onBtnLocatarios(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/locatario.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Locatários");
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.setResizable(false);

		STAGE_LOCATARIO = stage;

		stage.show();
	}

	@FXML
	void onBtnSair(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void onBtnSobre(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/br/com/syntech/view/sobre.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Sobre");
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.setResizable(false);

		STAGE_SOBRE = stage;

		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		imgLogo.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/logo_imovel.png")));
		imgSino.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/sino.png")));
		imgContrato.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/contrato.png")));
		imgCasa.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/house.png")));
		imgSobre.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/help.png")));
		imgConfig.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/settings.png")));
		imgExit.setImage(new Image(this.getClass().getResourceAsStream("/br/com/syntech/img/exit.png")));

		this.STAGE_HOME = this;

		getInfoImoveis();
		getInfoContratos();

		lblTotalimoveis.setText(String.valueOf(imoveis.size()));
		lblTotalContratos.setText(String.valueOf(contratos.size()));

		loadStackedChart();

		try {
			populandoTable(notificacaoService.getAllNotifications());
		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		}

		selectAluguelTabletView();

	}

	private void loadStackedChart() {

		float jan = 0;
		float fev = 0;
		float mar = 0;
		float abr = 0;
		float mai = 0;
		float jun = 0;
		float jul = 0;
		float ago = 0;
		float set = 0;
		float out = 0;
		float nov = 0;
		float dez = 0;

		try {

			List<DataStackedChat> list = aluguelService.getDataStackedChat();

			for (DataStackedChat d : list) {
				if (d.getMes().equals(1)) {
					jan += d.getValor().floatValue();
				} else if (d.getMes().equals(2)) {
					fev += d.getValor().floatValue();
				} else if (d.getMes().equals(3)) {
					mar += d.getValor().floatValue();
				} else if (d.getMes().equals(4)) {
					abr += d.getValor().floatValue();
				} else if (d.getMes().equals(5)) {
					mai += d.getValor().floatValue();
				} else if (d.getMes().equals(8)) {
					jun += d.getValor().floatValue();
				} else if (d.getMes().equals(7)) {
					jul += d.getValor().floatValue();
				} else if (d.getMes().equals(8)) {
					ago += d.getValor().floatValue();
				} else if (d.getMes().equals(9)) {
					set += d.getValor().floatValue();
				} else if (d.getMes().equals(10)) {
					out += d.getValor().floatValue();
				} else if (d.getMes().equals(11)) {
					nov += d.getValor().floatValue();
				} else if (d.getMes().equals(12)) {
					dez += d.getValor().floatValue();
				}
			}

			stackPane.getChildren().clear();

			CategoryAxis xAxis = new CategoryAxis();
			xAxis.setLabel("Meses");

			NumberAxis yAxis = new NumberAxis();
			yAxis.setLabel("Valor R$");

			StackedBarChart<String, Number> chart = new StackedBarChart<>(xAxis, yAxis);

			chart.setTitle("Recebimentos Alugueis");

			XYChart.Series series = new XYChart.Series();

			series.getData().add(new XYChart.Data<>("Jan", jan));
			series.getData().add(new XYChart.Data<>("Fev", fev));
			series.getData().add(new XYChart.Data<>("Mar", mar));
			series.getData().add(new XYChart.Data<>("Abr", abr));
			series.getData().add(new XYChart.Data<>("Mai", mai));
			series.getData().add(new XYChart.Data<>("Jun", jun));
			series.getData().add(new XYChart.Data<>("Jul", jul));
			series.getData().add(new XYChart.Data<>("Ago", ago));
			series.getData().add(new XYChart.Data<>("Set", set));
			series.getData().add(new XYChart.Data<>("Out", out));
			series.getData().add(new XYChart.Data<>("Nov", nov));
			series.getData().add(new XYChart.Data<>("Dez", dez));

			chart.getData().add(series);

			stackPane.getChildren().add(chart);

		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		}
	}

	private void selectAluguelTabletView() {
		tbwNotificacao.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {

						SELECT_NOTIFICACAO = tbwNotificacao.getSelectionModel().getSelectedItem();

						if (SELECT_NOTIFICACAO != null) {
							if (SELECT_NOTIFICACAO.getTipo().equals("ALUGUEL")) {
								try {

									Aluguel auxAluguel;

									if ((auxAluguel = aluguelService.calcValorAluguel(
											aluguelService.findOne(SELECT_NOTIFICACAO.getIdObj()))) != null) {

										if (aluguelService.update(auxAluguel)) {

											HomeController.STAGE_HOME.initialize(null, null);

											auxAluguel = aluguelService.findOne(SELECT_NOTIFICACAO.getIdObj());

											CaixaDialogo.msgInformacao("",
													"Aluguel recebido com sucesso!\nEmitindo recibo...", "");

											try {
												GeradorPDF geradorPDF = new GeradorPDF();
												geradorPDF.gerar(auxAluguel);
											} catch (DocumentException e) {
												CaixaDialogo.msgError("Error", "Error ao gerar recibo de aluguel.",
														e.getMessage());
											}
										}
									}
								} catch (MyExeption e) {
									CaixaDialogo.msgError("Error", e.getMessage(), "");
								}

							} else if (SELECT_NOTIFICACAO.getTipo().equals("CONTRATO")) {
								CaixaDialogo.msgInformacao("Atenção", SELECT_NOTIFICACAO.getAssunto(), "");
							}
						}
					}
				}
			}
		});
	}

	private void populandoTable(List<Notificacao> note) throws MyExeption {

		this.tbcNotificacaoID.setCellValueFactory(new PropertyValueFactory("id"));

		this.tbcNotificacaoAssunto.setCellValueFactory(new PropertyValueFactory("assunto"));

		final DateFormat dateFormat = DateFormat.getDateInstance();

		this.tbcNotificacaoData.setCellValueFactory(new PropertyValueFactory<Notificacao, Calendar>("data"));
		this.tbcNotificacaoData.setCellFactory(col -> new TableCell<Notificacao, Calendar>() {
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

		this.tbwNotificacao.setItems(FXCollections.observableArrayList(note));
	}

	private void getInfoImoveis() {
		try {
			imoveis = imovelService.getAll();
		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		}
	}

	private void getInfoContratos() {
		try {
			contratos = contratoService.getAll();
		} catch (MyExeption e) {
			CaixaDialogo.msgError("Error", e.getMessage(), "");
		}
	}
}
