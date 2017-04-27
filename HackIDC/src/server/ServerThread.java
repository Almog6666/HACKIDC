package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;

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
				temp.println(HandleUser(tempMsg));//Sending the output to the user
			}
			socket.close();
		} catch (IOException e) {
			System.out.println("socket : "+socket + " disconnected");
		}
	}// run
	// end       



	public String HandleUser(Message msg)
	{
		switch(msg.op)
		{
		case "newOrder":
			return addNewOrder(msg.dataString);
		default:
			return "DREK";

		}

	}	

	private String addNewOrder(String data)
	{
		try {
			Statement stmt = Server.getConnection().createStatement();
			String[] temp = data.split(",");//[0] - user_id, [1] - requested date,
			// [2] - error code, [3] - error description, [4] - error fix
			int user_id = Integer.parseInt(temp[0]);
			@SuppressWarnings("deprecation")
			java.util.Date myDate = new java.util.Date(temp[1]);
			java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
			String errorCode = temp[2];
			String errorDescription = temp[3];
			String errorFix = temp[4];
			stmt.executeUpdate("insert into jobs (user_id,date_time,error_code,error_disc,error_fix) "
				+ "values("+user_id+",'"+sqlDate+"','"+errorCode+"','"+errorDescription+"','"+errorFix+");");
			return "SUCCESS";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED";
		}
	}
}