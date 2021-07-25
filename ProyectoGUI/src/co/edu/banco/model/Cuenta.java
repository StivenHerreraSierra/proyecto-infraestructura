package co.edu.banco.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import co.edu.banco.model.exception.BolsilloException;
import co.edu.banco.model.exception.CuentaException;

public class Cuenta {	
	private int numeroCuenta;
	private String nombreCuenta;
	private double saldo;
	private ArrayList<Transaccion> listaTransacciones;
	private Bolsillo bolsilloCuenta;
	
	public Cuenta(int numeroCuenta, String nombreCuenta) {
		
		this.numeroCuenta = numeroCuenta;
		this.nombreCuenta = nombreCuenta;
		saldo = 0;
		listaTransacciones = new ArrayList<Transaccion>();
		bolsilloCuenta=null;
	}
	
	public Cuenta() {
		
	}

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public ArrayList<Transaccion> getListaTransacciones() {
		return listaTransacciones;
	}

	public void setListaTransacciones(ArrayList<Transaccion> listaTransacciones) {
		this.listaTransacciones = listaTransacciones;
	}

	public Bolsillo getBolsilloCuenta() {
		return bolsilloCuenta;
	}

	public void setBolsilloCuenta(Bolsillo bolsilloCuenta) {
		this.bolsilloCuenta = bolsilloCuenta;
	}
	
	public double consultarSaldo() throws CuentaException {
		double saldo = getSaldo();
		
		agregarTransaccion(TipoTransaccion.CONSULTAR);
		
		return saldo;
	}
	
	//================Crear bolsillo de la cuenta================
		/**
		 * Crea el bolsillo de la cuenta y deja de ser null.
		 * @param tipo Tipo de transacción realizada.
		 * @return True si el bolsillo es creado.
		 * @throws CuentaException Error generado cuando se trata de crear otro bolsillo (maximo 1 bolsillo por cuenta).
		 */
	public boolean crearBolsillo() throws BolsilloException, CuentaException{
		if(bolsilloCuenta==null) {
			String numeroBolsillo= String.valueOf(numeroCuenta)+"b";
			bolsilloCuenta=new Bolsillo(numeroBolsillo);
			agregarTransaccion(TipoTransaccion.ABRIR_BOLSILLO);
		}
		else {
			throw new BolsilloException("Error creando el bolsillo: La cuenta ya tiene un bolsillo");
		}
		
		return true;
	}
	//================Cancela bolsillo de la cuenta================
			/**
			 * Cancela el bolsillo de la cuenta y vuelve de ser null, 
			 * ademas el saldo que haya en el bolsillo es retornado a la cuenta.
			 * @param tipo Tipo de transacción realizada.
			 * @return True si el bolsillo es cancelado.
			 * @throws BolsilloException Error generado cuando se trata de cancelar un bolsillo que no existe o no se a inicializado.
			 */
	public boolean cancelarBolsillo() throws BolsilloException, CuentaException {
		if(bolsilloCuenta!=null) {
			double saldoBolsillo=bolsilloCuenta.vaciarBolsillo();
			saldo=saldo+saldoBolsillo;
			setBolsilloCuenta(null);
			agregarTransaccion(TipoTransaccion.CANCELAR_BOLSILLO);
		}
		else {
			throw new BolsilloException("Error al cancelar bolsillo: el bolsillo no existe o no se a inicializado uno");
		}
		return true;
	}
	
	/**
	 * Incrementa el valor del saldo de la cuenta.
	 * @param valor Valor a depositar.
	 * @return True si se hizo el depósito.
	 * @throws CuentaException Error generado por un valor menor o igual a cero.
	 */
	public boolean depositarDinero(double valor) throws CuentaException {
		if(valor <= 0)
			throw new CuentaException("Error, el dinero a depositar es menor o igual a cero.");
		
		this.saldo += valor;
		agregarTransaccion(TipoTransaccion.DEPOSITAR);
		
		return true;
	}
	
	/**
	 * Retira dinero del saldo disponible en la cuenta.
	 * @param valor Valor a retirar.
	 * @return True si se hizo el retiro.
	 * @throws CuentaException Error generado por un valor menor o igual a 0 ó mayor al saldo disponible.
	 */
	public boolean retirarDinero(double valor) throws CuentaException {
		if(valor <= 0)
			throw new CuentaException("Error, el valor a retirar es menor o igual a cero.");
		else if(valor > this.saldo)
			throw new CuentaException("Error, el valor a retirar es mayor al saldo disponible.");
		
		setSaldo(saldo - valor);
		agregarTransaccion(TipoTransaccion.RETIRAR);
		
		return true;
	}
	
	public boolean trasladarDinero(double valor) throws BolsilloException, CuentaException {
		if(bolsilloCuenta == null)
			throw new CuentaException("Error, la cuenta no tiene un bolsillo asociado.");
		else if(valor > this.saldo)
			throw new CuentaException("Error, el dinero a depositar en el bolsillo es mayor al saldo disponible.");
		
		bolsilloCuenta.depositarDinero(valor);
		setSaldo(saldo - valor);
		agregarTransaccion(TipoTransaccion.TRASLADAR);
		
		return true;
	}
	
	public double consultarSaldoBolsillo() throws CuentaException, BolsilloException {
		if(bolsilloCuenta == null)
			throw new CuentaException("Error, la cuenta no tiene un bolsillo asociado.");
		
		return bolsilloCuenta.consultarSaldo();
	}
	
	public String toString() {
		return "Numero de cuenta: " + numeroCuenta + " - Usuario: "+ nombreCuenta+" - Saldo: " + saldo;
	}	
	
	//================Transacción================
	/**
	 * Agrega una transacción a la cuenta.
	 * @param tipo Tipo de transacción realizada.
	 * @throws CuentaException Error generado por una transacción que ya está registrada.
	 */
	public void agregarTransaccion(TipoTransaccion tipo) throws CuentaException {
		LocalDateTime date = LocalDateTime.now();
		Transaccion transaccion = new Transaccion(tipo, Transaccion.DATE_FORMAT.format(date));
		
		if(existeTransaccion(transaccion))
			throw new CuentaException("Error, la transacción ya está registrada.");
		
		this.listaTransacciones.add(transaccion);
	}
	
	/**
	 * Verifica si la transacción especificada ya está registrada en la lista.
	 * @param transaccion Transacción que se va a buscar.
	 * @return True si ya existe, False en caso contrario.
	 */
	public boolean existeTransaccion(Transaccion transaccion) {
		return listaTransacciones.contains(transaccion);
	}
}
