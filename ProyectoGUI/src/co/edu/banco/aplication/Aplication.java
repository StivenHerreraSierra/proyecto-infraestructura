package co.edu.banco.aplication;

import java.io.IOException;

import co.edu.banco.view.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Aplication extends Application{

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	public static void main(String[] args) {
		launch(args);
	}

	public Aplication() {
		
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Banco");
		initRootLayout();
		showPersonOverview();
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplication.class.getResource("/co/edu/banco/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplication.class.getResource("/co/edu/banco/view/VistaBanco.fxml"));
			AnchorPane inicio = (AnchorPane) loader.load();
			rootLayout.setCenter(inicio);
			ViewController controller= loader.getController();
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void mostrarMensaje(String mensaje, AlertType miA, String titulo, String cabecera, String contenido, Stage escenarioPrincipal )
	{
	Alert alert = new Alert(miA);
	alert.initOwner(escenarioPrincipal);
	alert.setTitle(titulo);
	alert.setHeaderText(cabecera);
	alert.setContentText(contenido);
	alert.showAndWait();
	}
	
}
