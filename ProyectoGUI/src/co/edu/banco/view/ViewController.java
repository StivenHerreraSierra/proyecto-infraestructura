package co.edu.banco.view;

import java.io.File;

import com.jfoenix.controls.JFXTextField;
import co.edu.banco.aplication.Aplication;
import co.edu.banco.model.exception.CampoVacioException;
import co.edu.banco.model.exception.NumeroCuentaBolsilloException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ViewController {

	private Aplication aplicacion;
	
	public ViewController() {
		
	}

	@FXML
	private void initialize() {
		
	}
	
	public void setMain(Aplication aplicacion) {
		this.setAplicacion(aplicacion);
		
	}
	public Aplication getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplication aplicacion) {
		this.aplicacion = aplicacion;
	}
	
	@FXML
	private Pane logo1;
	@FXML
	private ImageView logo2;
	@FXML
	private Label logo3;
	@FXML
	private AnchorPane AnchorAccount;
	@FXML
	private AnchorPane AnchorPocket;
	@FXML
	private AnchorPane AnchorTransaction;
	@FXML
	private AnchorPane AnchorCharge;
	
	@FXML
	public void crudCuenta() {
		logo1.setVisible(false);
		logo2.setVisible(false);
		logo3.setVisible(false);
		AnchorPocket.setVisible(false);
		AnchorTransaction.setVisible(false);
		AnchorCharge.setVisible(false);
		AnchorAccount.setVisible(true);

	}
	@FXML
	public void crudBolsillo() {
		logo1.setVisible(false);
		logo2.setVisible(false);
		logo3.setVisible(false);
		AnchorPocket.setVisible(true);
		AnchorTransaction.setVisible(false);
		AnchorCharge.setVisible(false);
		AnchorAccount.setVisible(false);

	}
	@FXML
	public void crudTransaccion() {
		logo1.setVisible(false);
		logo2.setVisible(false);
		logo3.setVisible(false);
		AnchorPocket.setVisible(false);
		AnchorTransaction.setVisible(true);
		AnchorCharge.setVisible(false);
		AnchorAccount.setVisible(false);

	}
	@FXML
	public void crudCargar() {
		logo1.setVisible(false);
		logo2.setVisible(false);
		logo3.setVisible(false);
		AnchorPocket.setVisible(false);
		AnchorTransaction.setVisible(false);
		AnchorCharge.setVisible(true);
		AnchorAccount.setVisible(false);

	}
	
	///////Crud cliente///////
	
	@FXML
	private JFXTextField nombreCrearC;
	@FXML
	private JFXTextField numeroCancelarC;
	
	@FXML
	public void abrirCuenta() {
		
		String comando="ABRIR_CUENTA,";
		try {
			String nombre= nombreCrearC.getText();
			if(nombre.equals("")) {
				throw new CampoVacioException("No ingreso el nombre para crear la cuenta");
			}
			else {
				comando+=nombre;
				aplicacion.realizarTransaccion(comando);
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacío", "Error durante la transaccion",  "No ingreso el nombre para crear la cuenta", null);
		}
		limpiarCamposText();
	}
	
	@FXML
	public void cancelarCuenta() {
		
		String comando="CANCELAR_CUENTA,";
		try {
			String numeroCuenta= numeroCancelarC.getText();
			if(numeroCuenta.equals("")) {
				throw new CampoVacioException("No ingreso el numero de la cuenta a cancelar");
			}
			else {
				comando+=numeroCuenta;
				aplicacion.realizarTransaccion(comando);
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacío", "Error durante la transaccion",  "No ingreso el numero de la cuenta a cancelar", null);
		}
		limpiarCamposText();
	}
	
	///////Crud bolsillo///////
	
	@FXML
	private JFXTextField numeroCrearCB;
	@FXML
	private JFXTextField numeroCancelarBB;
	
	@FXML
	public void abrirBolsillo() {
		
		String comando="ABRIR_BOLSILLO,";
		try {
			String numeroBolsilloCuenta= numeroCrearCB.getText();
			if(numeroBolsilloCuenta.equals("")) {
				throw new CampoVacioException("No ingreso el numero de la cuenta para abrir el bolsillo");
			}
			else {
				comando+=numeroBolsilloCuenta;
				aplicacion.realizarTransaccion(comando);
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacío", "Error durante la transaccion",  "No ingreso el numero de la cuenta para abrir el bolsillo", null);
		}
		limpiarCamposText();
	}
	
	@FXML
	public void cancelarBolsillo() {
		
		String comando="CANCELAR_BOLSILLO,";
		try {
			String numeroBolsilloCuenta= numeroCancelarBB.getText();
			if(numeroBolsilloCuenta.equals("")) {
				throw new CampoVacioException("No ingreso el numero del bolsillo a cancelar");
			}
			else {
				comando+=numeroBolsilloCuenta;
				aplicacion.realizarTransaccion(comando);
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacío", "Error durante la transaccion",  "No ingreso el numero del bolsillo a cancelar", null);
		}
		limpiarCamposText();
	}
	
	///////Crud transaccion///////
	
	@FXML
	private JFXTextField numeroCT;
	@FXML
	private JFXTextField valorT;
	@FXML
	private JFXTextField numeroCT2;
	
	@FXML
	public void depositar() {
		
		String comando="DEPOSITAR,";
		try {
			String numeroCuenta= numeroCT.getText();
			String valor= valorT.getText();
			if(numeroCuenta.equals("")||valor.equals("")) {
				throw new CampoVacioException("No ingreso el numero de la cuenta o el valor a depositar");
			}
			else {
				if(numeroCuenta.contains("b")==true) {
					throw new NumeroCuentaBolsilloException("El numero que ingreso es de una cuenta de bolsillo");
				}
				else {
					comando+=numeroCuenta+","+valor;
					aplicacion.realizarTransaccion(comando);
				}
				
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacío", "Error durante la transaccion",  "No ingreso el numero de la cuenta o el valor a depositar", null);
		}catch(NumeroCuentaBolsilloException e) {
			Aplication.mostrarMensaje("", AlertType.ERROR, "Error en la transaccion", "Despositar solo es para cuentas de ahorros",  "El numero que ingreso es de una cuenta de bolsillo", null);
		}
		limpiarCamposText();
	}
	
	@FXML
	public void retirar() {
		
		String comando="RETIRAR,";
		try {
			String numeroCuenta= numeroCT.getText();
			String valor= valorT.getText();
			if(numeroCuenta.equals("")||valor.equals("")) {
				throw new CampoVacioException("No ingreso el numero de la cuenta o el valor a retirar");
			}
			else {
				if(numeroCuenta.contains("b")==true) {
					throw new NumeroCuentaBolsilloException("El numero que ingreso es de una cuenta de bolsillo");
				}
				else {
					comando+=numeroCuenta+","+valor;
					aplicacion.realizarTransaccion(comando);
				}
				
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacio", "Error durante la transaccion",  "No ingreso el numero de la cuenta o el valor a retirar", null);
		}catch(NumeroCuentaBolsilloException e) {
			Aplication.mostrarMensaje("", AlertType.ERROR, "Error en la transaccion", "Retirar solo es para cuentas de ahorros",  "El numero que ingreso es de una cuenta de bolsillo", null);
		}
		limpiarCamposText();
	}
	
	@FXML
	public void trasladar() {
		
		String comando="TRASLADAR,";
		try {
			String numeroCuenta= numeroCT.getText();
			String valor= valorT.getText();
			if(numeroCuenta.equals("")||valor.equals("")) {
				throw new CampoVacioException("No ingreso el numero del bolsillo o el valor a trasladar");
			}
			else {
				if(numeroCuenta.contains("b")==true) {
					throw new NumeroCuentaBolsilloException("El numero que ingreso es de una cuenta de bolsillo");
				}
				else {
					comando+=numeroCuenta+","+valor;
					aplicacion.realizarTransaccion(comando);
				}
				
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacio", "Error durante la transaccion",  "No ingreso el numero del bolsillo o el valor a trasladar", null);
		}catch(NumeroCuentaBolsilloException e) {
			Aplication.mostrarMensaje("", AlertType.ERROR, "Error en la transaccion", "Trasladar solo es para cuentas de ahorros",  "El numero que ingreso es de una cuenta de bolsillo", null);
		}
		
		limpiarCamposText();
	}
	
	@FXML
	public void consultarCuentaBolsillo() {
		
		String comando="CONSULTAR,";
		try {
			String numero= numeroCT2.getText();

			if(numero.equals("")) {
				throw new CampoVacioException("No ingreso el numero del bolsillo");
			}
			else {
				if(numero.contains("b")==false) {
					throw new NumeroCuentaBolsilloException("El numero que ingreso es de una cuenta de ahorros");
				}
				else {
					comando+=numero;
					aplicacion.realizarTransaccion(comando);
				}
				
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacio", "Error durante la transaccion",  "No ingreso el numero del bolsillo", null);
		}catch(NumeroCuentaBolsilloException e) {
			Aplication.mostrarMensaje("", AlertType.ERROR, "Error en la transaccion", "Consultar bolsillo",  "El numero que ingreso es de una cuenta de ahorros", null);
		}
		
		limpiarCamposText();
	}
	
	@FXML
	public void consultarCuentaAhorros() {
		
		String comando="CONSULTAR,";
		try {
			String numero= numeroCT2.getText();

			if(numero.equals("")) {
				throw new CampoVacioException("No ingreso el numero de la cuenta de ahorros");
			}
			else {
				if(numero.contains("b")==true) {
					throw new NumeroCuentaBolsilloException("El numero que ingreso es de un bolsillo");
				}
				else {
					comando+=numero;
					aplicacion.realizarTransaccion(comando);
				}
				
				
			}
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacio", "Error durante la transaccion",  "No ingreso el numero de la cuenta de ahorros", null);
		}catch(NumeroCuentaBolsilloException e) {
			Aplication.mostrarMensaje("", AlertType.ERROR, "Error en la transaccion", "Consultar cuenta de ahorros",  "El numero que ingreso es de un bolsillo", null);
		}
		
		limpiarCamposText();
	}
	

	///////Crud cargar///////
	
	@FXML
	private JFXTextField rutaCargar;
	
	@FXML
	public void cargarArchivo() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Selecciona el archivo a cargar");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = chooser.showOpenDialog(null);
		
		try {
			if(selectedFile == null)
				throw new CampoVacioException("No selecciono el archivo a cargar");

			String ruta= selectedFile.getAbsolutePath();
			aplicacion.realizarTransaccion("CARGA," + ruta);
			
		}catch(CampoVacioException e){
			Aplication.mostrarMensaje("", AlertType.ERROR, "Campo vacio", "Error durante la transaccion",  "No selecciono el archivo a cargar.", null);
		}
		limpiarCamposText();
	}
	
	@FXML
	public void limpiarCamposText() {
		numeroCT.setText("");
		valorT.setText("");
		numeroCT2.setText("");
		numeroCrearCB.setText("");
		numeroCancelarBB.setText("");
		nombreCrearC.setText("");
		numeroCancelarC.setText("");
		
	}
}
