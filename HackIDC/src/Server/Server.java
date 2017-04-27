package Server;

import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static final int PORT = 5555;
	public static int usernum = 1;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			new Server().runServer();
		} catch (Exception e) {
			System.out.println("Cant make server. maybe port taken by other server? ");
			System.exit(1);
		}
	}

	public void runServer() throws IOException {
		System.out.println("Setting up server at port :" + PORT + "\n");
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server ready for connections at port:" + PORT + "\n");
		while (true) {
			Socket socket = serverSocket.accept();
			new ServerThread(socket).start();
		}
	}
}
