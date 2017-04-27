package Server;

import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.*;

public class Server {
	public static final int PORT = 5555;
	public static int usernum = 1;
	private Connection conn;

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
		//Connecting to the DB
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception arg2) {
			;
		}
   
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://10.10.19.229:3306/hackidc", "root2", "Braude");
			System.out.println("SQL connection succeed");
		} catch (SQLException arg1) {
			System.out.println("HI");
			System.out.println("SQLException: " + arg1.getMessage());
			System.out.println("SQLState: " + arg1.getSQLState());
			System.out.println("VendorError: " + arg1.getErrorCode());
		}
		while (true) {
			Socket socket = serverSocket.accept();
			new ServerThread(socket).start();
		}
	}
}
