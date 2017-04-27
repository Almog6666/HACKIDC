package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static final int PORT = 5555;
	public static final String ServerAddress = "localhost";

	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket;
		try {
			socket = new Socket(ServerAddress, PORT);
			Start(socket);
		} catch (Exception e) {
			System.out.println("Cant establish connection to server..shutting down.\n");
			System.exit(1);
		}

	}

	public static void Start(Socket socket) throws IOException {

		String name = null;
		Scanner reader = new Scanner(System.in); // Reading from System.in
		  
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);										// to be able to respond to server
		BufferedReader UserInput = new BufferedReader(new InputStreamReader(System.in));								// to get input from *******THE CLIENT IT SELF
		BufferedReader ServerReciver = new BufferedReader(new InputStreamReader(socket.getInputStream()));		//to get messages from *****THE SERVER
		while (true) {
			String readerInput = UserInput.readLine();
			printWriter.println(readerInput); // send to server : FORMAT = "DST,MSG"
		
			System.out.println(ServerReciver.readLine());//get msg from server

		}
	}

}
