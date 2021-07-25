package co.edu.banco.echo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoTCPClientProtocol {

	private PrintWriter toNetwork;
	private BufferedReader fromNetwork;

	public void protocol(Socket socket) throws Exception {
		createStreams(socket);
	}

	private void createStreams(Socket socket) throws IOException {
		toNetwork = new PrintWriter(socket.getOutputStream(), true);
		fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public String procesarTransaccion(String comando) throws IOException {
		toNetwork.println(comando);
			
		String fromServer = fromNetwork.readLine();
		
		return fromServer;
	}
	
	public String cargaAutomatica(String rutaArchivo) throws IOException {
		String linea;
		File archivo = new File(rutaArchivo);
		String respuesta = "";
		
		if(!archivo.exists())
			throw new FileNotFoundException("El archivo no fue encontrado.");
		
		BufferedReader reader = new BufferedReader(new FileReader(archivo));
		while((linea = reader.readLine()) != null) {
			respuesta += procesarTransaccion(linea) + "\n";
		}
		
		reader.close();
		
		return respuesta;
	}
}