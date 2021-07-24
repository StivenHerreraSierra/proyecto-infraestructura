package co.edu.banco.echo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import co.edu.banco.view.ClientView;

public class EchoTCPClientProtocol {

	private static final Scanner SCANNER = new Scanner(System.in);

	private static PrintWriter toNetwork;
	private static BufferedReader fromNetwork;

	public static void protocol(Socket socket) throws Exception {
		createStreams(socket);
		
		mostrarMenu();
	}

	private static void createStreams(Socket socket) throws IOException {
		toNetwork = new PrintWriter(socket.getOutputStream(), true);
		fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}
	
	public static void mostrarMenu() throws Exception {
		String fromUser = ""; 
		String comando = "";
		while(!fromUser.equals("0")) {
			fromUser = comando = "";
			
			ClientView.mostrarMenu();
			
			fromUser = ClientView.leerOpcion();
			
			comando = ClientView.crearComando(fromUser);
			
			if(!fromUser.equals("9"))
				procesarTransaccion(comando);
			else
				cargaAutomatica(comando);
			
			if(!comando.equals("SALIR"))
				pause();
		}
	}
	public static void procesarTransaccion(String comando) throws IOException {
		toNetwork.println(comando);
		
		String fromServer = fromNetwork.readLine();
		
		imprimirRespuesta(fromServer);
	}
	
	public static void imprimirRespuesta(String respuesta) {
		System.out.print(" [Client] From server: ");
		
		if(respuesta != null && !respuesta.contains("/"))
			System.out.println(respuesta);
		else {
			System.out.println();
			Arrays.asList(respuesta.split("/")).forEach(System.out::println);
		}
	}
	
	public static void pause() {
		System.out.println("\nPresione ENTER para continuar");
		SCANNER.nextLine();
	}
	
	private static void cargaAutomatica(String rutaArchivo) throws IOException {
		String linea;
		File archivo = new File(rutaArchivo);
		
		if(!archivo.exists())
			throw new FileNotFoundException("El archivo no fue encontrado.");
		
		BufferedReader reader = new BufferedReader(new FileReader(archivo));
		while((linea = reader.readLine()) != null) {
			procesarTransaccion(linea);
		}
		
		reader.close();
	}
}