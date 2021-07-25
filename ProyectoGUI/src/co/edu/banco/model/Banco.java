package co.edu.banco.model;

import java.util.HashMap;
import java.util.Iterator;

import co.edu.banco.model.exception.BolsilloException;
import co.edu.banco.model.exception.CuentaException;

public class Banco {
	private HashMap<String, Cuenta> listaCuentas= new HashMap<String, Cuenta>();
	private int totalCuentasRegistradas;
	
	public Banco() {
		totalCuentasRegistradas = 0;
	}
	
	
	public HashMap<String, Cuenta> getListaCuentas() {
		return listaCuentas;
	}


	public void setListaCuentas(HashMap<String, Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}
	
	//================Crear Bolsillo================
	/**
	 * Crea el bolsillo de la cuenta actual.
	 * Si ya existe un bolsillo en la cuenta  no crea el bolsillo.
	 * @param numeroCuenta Numero de ceunta del usaurio titular.
	 * @return Bolsillo creado.
	 * @throws BolsilloException Error generado cuando se trata de crear un bolsillo de una cuenta que no esta registrada.
	 */
	public Bolsillo crearBolsillo(int numeroCuenta) throws BolsilloException, CuentaException{
		Cuenta cuenta = obtenerCuenta(numeroCuenta);
		
		if(cuenta == null)
			throw new BolsilloException("Error creando el bolsillo: el usuario no esta registrado.");
		
		cuenta.crearBolsillo();
		return cuenta.getBolsilloCuenta();
	}
	//================Cancelar bolsillo================
	/**
	* Cancela el bolsillo de una cuenta existente.
	* Si el numero de bolsillo no coincide con algun numero de cuenta existente, cancela la transaccion.
	* @param numeroBolsillo Numero del bolsillo de una cuenta.
	* @return True si se cancela el bolsillo.
	* @throws BolsilloException Error generado por un numero de bolsillo que no coincida con un numero de cuenta existente.
	*/
	public boolean cancelarBolsillo(String numeroBolsillo) throws BolsilloException, CuentaException {
		String numeroCuentaString=numeroBolsillo.replace("b", "");
		try {
			int numeroCuenta= Integer.valueOf(numeroCuentaString);
			Cuenta cuenta=obtenerCuenta(numeroCuenta);
			if(cuenta == null)
				throw new BolsilloException("Error al cancelar bolsillo: el numero de bolsillo no coincide con el numero de una cuenta existente");
			
			cuenta.cancelarBolsillo();
		}catch(NumberFormatException e) {
			throw new BolsilloException("Error al cancelar bolsillo: el numero de bolsillo es incosistente al formato original");
		}
		
		
		
		return true;
	}
	
	//================Cancelar cuenta================
		/**
		 * Cancelar una cuenta de ahorros y la elimina de la lista.
		 * Si el numero de cuenta no existe, cancela la transaccion.
		 * @param numeroCuenta Numero de cuenta del usuario titiular.
		 * @return True si la cuenta se cancela.
		 * @throws CuentaException Error generado cuando el numero de cuenta no esta registrado (la cuenta no existe).
		 */
	public boolean cancelarCuenta(int numeroCuenta) throws CuentaException {
		Cuenta cuenta= obtenerCuenta(numeroCuenta);
		
		if(cuenta == null) {
			throw new CuentaException("Error, el numero de cuenta no esta registrado.");
		}
		else {
			double saldo=cuenta.getSaldo();
			Bolsillo bolsillo=cuenta.getBolsilloCuenta();
			String nombre=cuenta.getNombreCuenta();
			if(saldo==0.0&&bolsillo==null) {
				this.listaCuentas.remove(nombre,cuenta);
				cuenta.agregarTransaccion(TipoTransaccion.CANCELAR_CUENTA);
			}
			else {
				throw new CuentaException("Error, la cuenta a cancelar cuenta con un bolsillo o su saldo es superior a $0.0");
			}
			
		}
		
		return true;
	}

	//================Crear cuenta================
	/**
	 * Crea una cuenta de ahorros y la agrega a la lista.
	 * Si el nombre de usuario ya está registrado, cancela la transacción.
	 * @param nombre Nombre del usuario titiular.
	 * @return Cuenta creada.
	 * @throws CuentaException Error generado por un usuario repetido.
	 */
	public Cuenta crearCuenta(String nombre) throws CuentaException {
		Cuenta cuenta;
		
		if(!existeUsuario(nombre)) {
			cuenta = new Cuenta(this.totalCuentasRegistradas, nombre);
			this.listaCuentas.put(nombre, cuenta);
			this.totalCuentasRegistradas++;
			
			cuenta.agregarTransaccion(TipoTransaccion.ABRIR_CUENTA);
			return cuenta;
		} else {
			throw new CuentaException("Error creando la cuenta: el usuario " + nombre + " ya esta registrado.");
		}
	}
	
	//================Depositar dinero================
	/**
	 * Deposita un valor mayor a 0 en el saldo de la cuenta especificada.
	 * @param numeroCuenta Número de cuenta a depositar.
	 * @param valorDepositar Valor a depositar.
	 * @return True si se hizo el depósito.
	 * @throws CuentaException Error generado por depositar en una cuenta inexistente.
	 */
	public boolean depositarDineroCuenta(int numeroCuenta, double valorDepositar) throws CuentaException {
		Cuenta cuenta = obtenerCuenta(numeroCuenta);
		
		if(cuenta == null)
			throw new CuentaException("Error, el numero de cuenta no esta registrado.");
		
		cuenta.depositarDinero(valorDepositar);
		
		return true;	
	}

	//================Retirar dinero================
	public boolean retirarSaldoCuenta(int numeroCuenta, double valorRetirar) throws CuentaException {
		Cuenta cuenta = obtenerCuenta(numeroCuenta);
		
		if(cuenta == null)
			throw new CuentaException("Error, el numero de cuenta no esta registrado.");
		
		cuenta.retirarDinero(valorRetirar);
		
		return true;
	}
	
	//================Trasladar dinero================
	
	public boolean trasladarDineroCuenta_Bolsillo(int numeroCuenta, double valorTrasladar) throws CuentaException, BolsilloException {
		Cuenta cuenta = obtenerCuenta(numeroCuenta);
		
		if(cuenta == null)
			throw new CuentaException("Error, el numero de cuenta no esta registrado.");
		
		cuenta.trasladarDinero(valorTrasladar);
		
		return true;
	}
	
	//================Consultar saldo================
	public double consultarSaldo(String numeroCuenta) throws NumberFormatException, CuentaException, BolsilloException {
		double saldo;
		int numeroCuentaAhorro = Integer.parseInt(numeroCuenta.replace("b", ""));
		Cuenta cuenta = obtenerCuenta(numeroCuentaAhorro);
		
		if(cuenta == null)
			throw new CuentaException("Error, el numero de cuenta "+ numeroCuenta + " no esta registrado.");
		
		if(numeroCuenta.endsWith("b"))
			saldo = cuenta.consultarSaldoBolsillo();
		else
			saldo = cuenta.consultarSaldo();
		
		return saldo;
	}

	/**
	 * Verifica si la clave especificada ya está registrada en la lista.
	 * @param clave Clave a buscar.
	 * @return True si ya existe, False en caso contrario.
	 */
	public boolean existeUsuario(String clave) {
		return this.listaCuentas.containsKey(clave);
	}
	
	/**
	 * Verifica si la el numero de cuenta especificado ya está registrada en la lista.
	 * @param numeroCuenta Numero de cuenta a buscar.
	 * @return True si ya existe, False en caso contrario.
	 */
	public Bolsillo obtenerBolsilloCuenta(String numeroBolsillo) {
		
		Iterator<String> lista=listaCuentas.keySet().iterator();
		String clave;
		Cuenta valor=null;
		Bolsillo bolsillo=null;
		Bolsillo bolsilloAux=null;
		while(lista.hasNext()) {
			clave=lista.next();
			valor=listaCuentas.get(clave);
			bolsillo=valor.getBolsilloCuenta();
			if(bolsillo!=null&&bolsillo.getNumeroCuenta().equals(numeroBolsillo)) {
				bolsilloAux= bolsillo;
			}
			
		}
		return bolsilloAux;
	}
	
	/**
	 * Busca y retorna la cuenta asociada con un número de cuenta especificado.
	 * @param numeroCuenta Número de la cuenta a buscar.
	 * @return Cuenta asociada o null.
	 */
	public Cuenta obtenerCuenta(int numeroCuenta) {
		Cuenta cuenta = null;
		Cuenta cuentaAux = null;
		String clave;
		Iterator<String> lista=listaCuentas.keySet().iterator();
		
		while(lista.hasNext() && cuenta == null) {
			clave = lista.next();
			cuentaAux = listaCuentas.get(clave);
			if(cuentaAux.getNumeroCuenta() == numeroCuenta) {
				cuenta = cuentaAux;
			}	
		}
		
		return cuenta;
	}
}
