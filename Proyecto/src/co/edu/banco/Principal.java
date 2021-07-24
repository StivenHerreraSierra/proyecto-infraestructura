package co.edu.banco;

import co.edu.banco.model.Banco;
import co.edu.banco.model.Bolsillo;
import co.edu.banco.model.exception.BolsilloException;
import co.edu.banco.model.exception.CuentaException;

public class Principal {

	public static void main(String[] args) throws BolsilloException, CuentaException {
		Banco banco = new Banco();
		
		try {
			System.out.println(banco.crearCuenta("Stiven"));
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			System.out.println(banco.crearCuenta("Stiven"));
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			System.out.println(banco.crearCuenta("Herrera"));
		} catch (CuentaException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("Prueba crear bolsillo");
		System.out.println(banco.getListaCuentas().get("Herrera").getBolsilloCuenta());
		banco.crearBolsillo(1);
		System.out.println(banco.getListaCuentas());
		System.out.println(banco.getListaCuentas().get("Herrera").getListaTransacciones());
		System.out.println(banco.getListaCuentas().get("Herrera").getBolsilloCuenta());
		
		System.out.println("Prueba depositar");
		banco.depositarDineroCuenta(1, 1000);
		banco.depositarDineroCuenta(1, 2000);
		System.out.println(banco.obtenerCuenta(1));
		System.out.println(banco.getListaCuentas().get("Herrera").getListaTransacciones());
		
		System.out.println("Prueba retirar");
		banco.retirarSaldoCuenta(1, 1500);
		System.out.println(banco.obtenerCuenta(1));
		System.out.println(banco.getListaCuentas().get("Herrera").getListaTransacciones());
		
		System.out.println("Prueba trasladar");
		Bolsillo b = banco.obtenerCuenta(1).getBolsilloCuenta(); 
		banco.obtenerCuenta(1).trasladarDinero(1500);
		System.out.println(banco.obtenerCuenta(1));
		System.out.println(banco.getListaCuentas().get("Herrera").getListaTransacciones());
		System.out.println(banco.obtenerCuenta(1).getBolsilloCuenta());
		System.out.println(b.getListaTransacciones());
		
		System.out.println("Prueba Consultar");
		double saldo = banco.consultarSaldo("1b");
		System.out.println(saldo);
		System.out.println(banco.getListaCuentas().get("Herrera").getListaTransacciones());
		System.out.println(banco.getListaCuentas().get("Herrera").getBolsilloCuenta().getListaTransacciones());
		
		System.out.println("Prueba cancelar bolsillo");
		banco.cancelarBolsillo("1b");
		System.out.println(banco.getListaCuentas().get("Herrera").getListaTransacciones());
		System.out.println(banco.obtenerCuenta(1).getBolsilloCuenta());
		
		System.out.println("Prueba cancelar cuenta");
		System.out.println(banco.obtenerCuenta(1));
		banco.obtenerCuenta(1).setSaldo(0);
		banco.cancelarCuenta(1);
		System.out.println(banco.obtenerCuenta(1));
		System.out.println(banco.getListaCuentas());
	}

}
