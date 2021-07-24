package co.edu.banco.echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import co.edu.banco.model.Banco;

public class EchoTCPServer {
	public static final int PORT = 3400;

	private ServerSocket listener;
	private Socket serverSideSocket;
	
	private Banco miBanco;
	
	public EchoTCPServer() {
		miBanco = new Banco();
		System.out.println("Echo TCP server is running on port: " + PORT);
	}
	
	private void init() throws IOException {
		listener = new ServerSocket(PORT);
		
		while (true) {
			try {
				serverSideSocket = listener.accept();
				EchoTCPServerProtocol serverThread = new EchoTCPServerProtocol();
				serverThread.protocol(serverSideSocket, miBanco);
				new Thread(serverThread).start();
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		EchoTCPServer es = new EchoTCPServer();
		try {
			es.init();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
