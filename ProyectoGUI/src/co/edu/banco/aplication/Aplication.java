package co.edu.banco.aplication;

import java.io.IOException;

import co.edu.banco.echo.EchoTCPClient;
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
	private EchoTCPClient cliente;
	
	public static void main(String[] args) {
		launch(args);
	}

	public Aplication() {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		cliente = new EchoTCPClient();
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Banco");
		initRootLayout();
		showPersonOverview();
		primaryStage.setResizable(false);
		
		primaryStage.setOnCloseRequest(event -> {if(cliente != null)
			try {
				cliente.realizarTransaccion("SALIR");
				cliente.cerrarConexion();
			} catch (IOException e) {
				mostrarMensaje("", AlertType.ERROR, "Error conexion", "", "Error cerrando la conexión con el servidor: " + e.getMessage(), null);
			}}
		);
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
	
	public void realizarTransaccion(String comando) {
		String respuesta;
		boolean terminadaExito = true;
		try {
			respuesta = cliente.realizarTransaccion(comando);
			
			terminadaExito = !respuesta.toLowerCase().contains("error");
		} catch (IOException e) {
			respuesta = e.getMessage();
			terminadaExito = false;
		}
		
		mostrarResultado(respuesta, terminadaExito);
	}
	
	public void mostrarResultado(String mensaje, boolean terminadaExito) {
		String mensajeMultilinea = mensaje.replace(" - ", "\n");
		if(terminadaExito)
			mostrarMensaje("", AlertType.INFORMATION, "Resultado de transaccion", "Informe de la solicitud de la transaccion", mensajeMultilinea, null);
		else
			mostrarMensaje("", AlertType.ERROR, "Error de transaccion", "Error durante la solicitud de la transaccion", mensajeMultilinea, null);
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
