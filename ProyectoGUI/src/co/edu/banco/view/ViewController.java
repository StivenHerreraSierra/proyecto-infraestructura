package co.edu.banco.view;

import co.edu.banco.aplication.Aplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
	private MenuItem retirar, trasladar, despositar;
	@FXML
	private MenuItem consultarC, consultarBC;
	
	
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
	
	
}
