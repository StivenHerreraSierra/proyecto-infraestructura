package co.edu.banco.echo;

import java.net.Socket;

public class EchoTCPClient {
	public static final int PORT = 3400;
	public static final String SERVER = "localhost";

	private Socket clientSideSocket;

	public EchoTCPClient() {
		System.out.println("Echo TCP Client ...");
	}

	public void init() {
		try {
			clientSideSocket = new Socket(SERVER, PORT);
			
			EchoTCPClientProtocol.protocol(clientSideSocket);

			clientSideSocket.close();
		} catch (Exception e) {
			System.err.println("Error en el cliente: " + e.getMessage());
		}
	}

	public static void main(String args[]) throws Exception {
		EchoTCPClient ec = new EchoTCPClient();
		ec.init();
	}
}
