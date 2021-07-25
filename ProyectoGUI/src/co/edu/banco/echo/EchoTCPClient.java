package co.edu.banco.echo;

import java.io.IOException;
import java.net.Socket;

public class EchoTCPClient {
	public static final int PORT = 3400;
	public static final String SERVER = "localhost";

	private Socket clientSideSocket;
	private EchoTCPClientProtocol clientProtocol;

	public EchoTCPClient() throws Exception {
		init();
	}

	public void init() throws Exception {
		clientSideSocket = new Socket(SERVER, PORT);
		clientProtocol = new EchoTCPClientProtocol();
		clientProtocol.protocol(clientSideSocket);
	}
	
	public String realizarTransaccion(String comando) throws IOException {
		String respuesta;
		
		if(comando.startsWith("CARGA"))
			respuesta = clientProtocol.cargaAutomatica(comando);
		else
			respuesta = clientProtocol.procesarTransaccion(comando);
		
		return respuesta;
	}
	
	public void cerrarConexion() throws IOException {
		clientSideSocket.close();
	}
}
