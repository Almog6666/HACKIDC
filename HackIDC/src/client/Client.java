package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import dataTypes.Job;

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
		Object recived=null;
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);										// to be able to respond to server
		BufferedReader UserInput = new BufferedReader(new InputStreamReader(System.in));								// to get input from *******THE CLIENT IT SELF
		//BufferedReader ServerReciver = new BufferedReader(new InputStreamReader(socket.getInputStream()));		//to get messages from *****THE SERVER
		while (true) {
			
			String readerInput = UserInput.readLine();//get what to send to server
			printWriter.println(readerInput); // send to server
			
			
			
			  ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());//to get response from inputstream
			  try {
				recived = objectInput.readObject();//read response
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("NORMAL reciver = "+recived);//print to check
			if(recived instanceof ArrayList)//check instancce of response
			{
				ArrayList<Job> res;
				res=(ArrayList<Job>)recived;
				for(Job x : res)
					System.out.println(x);
			}else System.out.println(recived);//print as string if not found anything else.
			
			

		}
	}

}
