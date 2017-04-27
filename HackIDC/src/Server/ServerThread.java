package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		PrintWriter temp = null;
		try {
			String message = null;

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			temp = new PrintWriter(socket.getOutputStream(), true);
			while ((message = in.readLine()) != null) {
				HandleUser(message);
			}
			socket.close();
		} catch (IOException e) {
			System.out.println("socket : "+socket + " disconnected");
		}
	}// run
		// end



public void HandleUser(String msg)
	{//server response maker
		
	}	
}