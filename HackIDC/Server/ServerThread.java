package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import dataTypes.Message;

public class ServerThread extends Thread {
	Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
     
		PrintWriter temp = null;
		Message tempMsg = new Message();
		try {
			String message = null;
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			temp = new PrintWriter(socket.getOutputStream(), true);
			while ((message = in.readLine()) != null) {
				String[] dummy = message.split(" ");
				tempMsg.op = dummy[0];
				tempMsg.dataString = dummy[1];
				System.out.println("op = " + tempMsg.op + " " + "dataString = " + tempMsg.dataString);
				temp.println(message);
				HandleUser(tempMsg);
			}
			socket.close();
		} catch (IOException e) {
			System.out.println("socket : "+socket + " disconnected");
		}
	}// run
		// end       



public void HandleUser(Message msg)
	{
		switch(msg.op)
		{
		case "newOrder":
			break;
		default:
			break;
				
		}
		
	}	
}