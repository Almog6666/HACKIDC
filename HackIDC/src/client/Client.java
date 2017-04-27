package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
		BufferedReader ServerReciver = new BufferedReader(new InputStreamReader(socket.getInputStream()));		//to get messages from *****THE SERVER
		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
		Scanner scanner = new Scanner(System.in);
		//ObjectOutputStream sender = new ObjectOutputStream(socket.getOutputStream());
		while (true) {
			//System.out.println("HEY");
			//String readerInput = UserInput.readLine();//get what to send to server
			String readerInput = scanner.nextLine();//get what to send to server
			///printWriter.println(readerInput);//Sending the message to the server
			objectOutput.writeObject(readerInput);
			//recived = ServerReciver.readLine();//Readingt
			//sender.w
			
			try
			{	
				recived = objectInput.readObject();//read response
				//System.out.println("HE3Y");

				if(recived instanceof ArrayList)//check instancce of response
				{
					//System.out.println("in instance");
					ArrayList<Job> res;
					res=(ArrayList<Job>)recived;
				//	for(Job x : res)
					//	System.out.println(x);
				}else System.out.println(recived);//print as string if not found anything else.
			}catch (Exception e1) {
				e1.printStackTrace();
				}

			//System.out.println(recived);//print to check
		}
	}

}
