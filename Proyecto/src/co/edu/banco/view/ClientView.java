package co.edu.banco.view;

import java.util.Scanner;

public class ClientView {
	public static final Scanner SCANNER = new Scanner(System.in);
	
	public static void mostrarMenu() {
		System.out.println(""
				+ "**********************************************************\n"
				+ "*                   BANCO DIGITAL                        *\n"
				+ "**********************************************************\n"
				+ "* Ingrese una opcion:                                    *\n"
				+ "*  1. Crear cuenta.                                      *\n"
				+ "*  2. Crear bolsillo.                                    *\n"
				+ "*  3. Cancelar bolsillo.                                 *\n"
				+ "*  4. Cancelar cuenta de ahorros.                        *\n"
				+ "*  5. Depositar dinero en una cuenta.                    *\n"
				+ "*  6. Retirar dinero de una cuenta.                      *\n"
				+ "*  7. Trasladar dinero a un bolsillo.                    *\n"
				+ "*  8. Consultar saldo.                                   *\n"
				+ "*  9. Carga automatica.                                  *\n"
				+ "*  0. Salir.                                             *\n"
				+ "**********************************************************");
	}
	
	public static String leerOpcion() {
		System.out.print("Opcion: ");
		return SCANNER.nextLine();
	}
	
	public static String crearComando(String opcion) {
		String comando = "";
		
		if(opcion.equals("1")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                    Crear Cuenta                        *\n"
					+ "**********************************************************");
			
			comando = "ABRIR_CUENTA,";
			System.out.print("Escriba el nombre del titular: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("2")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                   Crear Bolsillo                       *\n"
					+ "**********************************************************");
			
			comando = "ABRIR_BOLSILLO,";
			System.out.print("Escriba el numero de la cuenta principal: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("3")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                  Cancelar Bolsillo                     *\n"
					+ "**********************************************************");
			
			comando = "CANCELAR_BOLSILLO,";
			System.out.print("Escriba el numero de la cuenta bolsillo: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("4")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                    Cancelar Cuenta                     *\n"
					+ "**********************************************************");
			
			comando = "CANCELAR_CUENTA,";
			System.out.print("Escriba el numero de la cuenta de ahorros: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("5")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                   Depositar Dinero                     *\n"
					+ "**********************************************************");
			
			comando = "DEPOSITAR,";
			System.out.print("Escriba el numero de la cuenta de ahorros: ");
			comando += SCANNER.nextLine() + ",";
			System.out.print("Escriba el valor a depositar: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("6")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                    Retirar Dinero                      *\n"
					+ "**********************************************************");
			
			comando = "RETIRAR,";
			System.out.print("Escriba el numero de la cuenta de ahorros: ");
			comando += SCANNER.nextLine() + ",";
			System.out.print("Escriba el valor a retirar: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("7")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                   Trasladar Dinero                     *\n"
					+ "**********************************************************");
			
			comando = "TRASLADAR,";
			System.out.print("Escriba el numero de la cuenta de ahorros: ");
			comando += SCANNER.nextLine() + ",";
			System.out.print("Escriba el valor a trasladar: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("8")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                   Consultar Saldo                      *\n"
					+ "**********************************************************");
			
			comando = "CONSULTAR,";
			System.out.print("Escriba el numero de la cuenta: ");
			comando += SCANNER.nextLine();
			
		} else if(opcion.equals("9")) {
			
			System.out.println(""
					+ "**********************************************************\n"
					+ "*                   Carga automatica                     *\n"
					+ "**********************************************************");
			
			System.out.print("Escriba el nombre/ruta del archivo: ");
			comando = SCANNER.nextLine();
			
		} else if(opcion.equals("0")) {
			
			comando = "SALIR";
			
		}
		
		return comando;
	}
}
