package co.edu.banco.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import co.edu.banco.model.Banco;
import co.edu.banco.model.Bolsillo;
import co.edu.banco.model.Cuenta;
import co.edu.banco.model.exception.BolsilloException;
import co.edu.banco.model.exception.CuentaException;

public class EchoTCPServerProtocol implements Runnable{
	
	private PrintWriter toNetwork;
	private BufferedReader fromNetwork;
	
	private Banco miBanco;

	@Override
	public void run() {
		String message = "";
		try {
			do {
				message = fromNetwork.readLine();
				System.out.println(" [Server] From client: " + message);
		
				toNetwork.println(mostrarMenu(message));			
			} while(!message.equalsIgnoreCase("Salir"));
		} catch(IOException e) {
			System.err.println("Error: " + e.getMessage());
		} catch(NullPointerException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void protocol(Socket socket, Banco banco) throws IOException {
		createStreams(socket);
		miBanco = banco;
	}

	private void createStreams(Socket socket) throws IOException {

		toNetwork = new PrintWriter(socket.getOutputStream(), true);
		fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public String mostrarMenu(String msj) throws NullPointerException {		
		if(msj == null)
			throw new NullPointerException("Error, el mensaje capturado es null.");
		
		String res = "";
		String[] operacion = msj.split(",");

		if (operacion[0].equals("ABRIR_CUENTA")) {
			String nombre = operacion[1];
			res = abrirCuenta(nombre);
		} else {
			if (operacion[0].equals("ABRIR_BOLSILLO")) {
				String numeroCuenta = operacion[1];
				res = abrirBolsillo(numeroCuenta);
			} else {
				if (operacion[0].equals("CANCELAR_BOLSILLO")) {
					String numeroBolsillo = operacion[1];
					res = cancelarBolsillo(numeroBolsillo);
				} else {
					if (operacion[0].equals("CANCELAR_CUENTA")) {
						String numeroCuenta = operacion[1];
						res = cancelarCuenta(numeroCuenta);
					} else {
						if (operacion[0].equals("DEPOSITAR")) {
							String numeroCuenta = operacion[1];
							String valor = operacion[2];
							res = depositar(numeroCuenta, valor);
						} else {
							if (operacion[0].equals("RETIRAR")) {
								String numeroCuenta = operacion[1];
								String valor = operacion[2];
								res = retirar(numeroCuenta, valor);
							} else {
								if (operacion[0].equals("TRASLADAR")) {
									String numeroCuenta = operacion[1];
									String valor = operacion[2];
									res = trasladar(numeroCuenta, valor);
								} else {
									if (operacion[0].equals("CONSULTAR")) {
										String numero = operacion[1];
										res = consultar(numero);
									} else {
										if (operacion[0].equals("SALIR")) {
											res = "¡Adios!";
										} else {
											res = "No se pudo procesar su solicitud";
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return res;

	}
	
	private String abrirCuenta(String nombreUsuario) {
		String res;
		
		try {
			Cuenta cuenta = miBanco.crearCuenta(nombreUsuario);
			res = "Cuenta creada con exito -> " + cuenta;
		} catch (CuentaException e) {
			res = "Error, " + e.getMessage();
		}
		
		return res;
	}
	
	private String abrirBolsillo(String numeroCuenta) {
		String res;
		
		try {
			Bolsillo bolsillo = miBanco.crearBolsillo(Integer.parseInt(numeroCuenta));
			res = "Bolsillo creado con exito -> " + bolsillo;
		} catch (NumberFormatException e) {
			res = "Error, el número de cuenta contiene caracteres invalidos.";
		} catch (BolsilloException | CuentaException e) {
			res = "Error, " +  e.getMessage();
		}
		
		return res;
	}
	
	private String cancelarBolsillo(String numeroBolsillo) {
		String res;
		
		try {
			miBanco.cancelarBolsillo(numeroBolsillo);
			res = "Bolsillo cancelado correctamente";
		} catch (BolsilloException | CuentaException e) {
			res = "Error, " + e.getMessage();
		}
		
		return res;
	}
	
	private String cancelarCuenta(String numeroCuenta) {
		String res;
		
		try {
			miBanco.cancelarCuenta(Integer.parseInt(numeroCuenta));
			res = "Cuenta cancelada correctamente";
		} catch (NumberFormatException e) { 
			res = "Error, el número de cuenta contiene caracteres invalidos.";
		} catch (CuentaException e) {
			res = "Error, " + e.getMessage();
		}
		
		return res;
	}
	
	private String depositar(String numeroCuenta, String valor) {
		String res;
		
		try {
			miBanco.depositarDineroCuenta(Integer.parseInt(numeroCuenta),
					Double.parseDouble(valor));
			res = "Deposito realizado correctamente";
		} catch (NumberFormatException e) {
			res = "Error, el número de cuenta o valor contiene caracteres invalidos.";
		} catch (CuentaException e) {
			res = "Error, " + e.getMessage();
		}
		
		return res;
	}
	
	private String retirar(String numeroCuenta, String valor) {
		String res;
		
		try {
			miBanco.retirarSaldoCuenta(Integer.parseInt(numeroCuenta),
					Double.parseDouble(valor));
			res = "Retiro realizado satisfactoriamente";
		} catch (NumberFormatException e) {
			res = "Error, l número de cuenta o valor contiene caracteres invalidos.";
		} catch(CuentaException e) {
			res = "Error, " + e.getMessage();
		}
		
		return res;
	}
	
	private String trasladar(String numeroCuenta, String valor) {
		String res;
		
		try {
			miBanco.trasladarDineroCuenta_Bolsillo(Integer.parseInt(numeroCuenta),
					Double.parseDouble(valor));
			res = "Traslado exitoso";
		} catch (NumberFormatException e) {
			res = "Error, el número de cuenta o valor contiene caracteres invalidos.";
		} catch (CuentaException | BolsilloException e) {
			res = "Error, " + e.getMessage();
		}
		
		return res;
	}
	
	private String consultar(String numero) {
		String res;

		try {
			res = "Saldo disponible en la cuenta " + numero + ": " + miBanco.consultarSaldo(numero);
		} catch (NumberFormatException e) {
			res = "Error, el número de cuenta contiene caracteres invalidos.";
		} catch (CuentaException | BolsilloException e) {
			res = "Error, " + e.getMessage();
		}

		return res;
	}
}