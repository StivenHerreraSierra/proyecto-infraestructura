package co.edu.banco.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import co.edu.banco.model.exception.BolsilloException;
import co.edu.banco.model.exception.CuentaException;

public class Bolsillo {
	private String numeroCuenta;
	private double saldo;
	private ArrayList<Transaccion> listaTransacciones;
	
	public Bolsillo(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
		this.saldo = 0.0;
		this.listaTransacciones = new ArrayList<>();
	}
	
	
	public boolean depositarDinero(double valor) throws BolsilloException {
		if(valor <= 0)
			throw new BolsilloException("Error, el dinero a depositar en el bolsillo es menor o igual a cero.");
		
		setSaldo(saldo + valor);
		agregarTransaccion(TipoTransaccion.DEPOSITAR);
		
		return true;
	}
	
	/**
	 * Agrega una transacción al bolsillo.
	 * @param tipo Tipo de transacción realizada.
	 * @throws CuentaException Error generado por una transacción que ya está registrada.
	 */
	public void agregarTransaccion(TipoTransaccion tipo) throws BolsilloException {
		LocalDateTime date = LocalDateTime.now();
		Transaccion transaccion = new Transaccion(tipo, Transaccion.DATE_FORMAT.format(date));
		
		if(existeTransaccion(transaccion))
			throw new BolsilloException("Error, la transacción ya esta registrada.");
		
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
	/**
	 * Vacia el saldo del bolsillo
	 * @return saldo, deja el bolsillo con saldo 0 para ser cancelada.
	 */
	public double vaciarBolsillo() {
		double saldo=getSaldo();
		setSaldo(0);
		return saldo;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	private void setSaldo(double valor) {
		saldo = valor;
	}
	
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	
	public boolean agregarTransaccion(Transaccion transaccion) {
		//TODO
		return true;
	}
	
	public double consultarSaldo() throws BolsilloException {
		double saldo = getSaldo();
		
		agregarTransaccion(TipoTransaccion.CONSULTAR);
		
		return saldo;
	}
	
	@Override
	public String toString() {
		return "Numero de bolsillo: " + numeroCuenta + " - Saldo: " + saldo;
	}

	public ArrayList<Transaccion> getListaTransacciones() {
		return listaTransacciones;
	}

	public void setListaTransacciones(ArrayList<Transaccion> listaTransacciones) {
		this.listaTransacciones = listaTransacciones;
	}
	
	
}
