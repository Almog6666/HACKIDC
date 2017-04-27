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
				String[] dummy = message.split("_");
				try{
					tempMsg.op = dummy[0];
					tempMsg.dataString = dummy[1];
					System.out.println("op = " + tempMsg.op + " " + "dataString = " + tempMsg.dataString);
					temp.println(HandleUser(tempMsg));//Sending the output to the user
				}catch(Exception e)
				{
					System.out.println("String not fine");
				}
				temp.println(message);
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
		case "NEWORDER":
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
			//  [2] - time_from,[3] - time_to [4] - error code, [5] - error description, [6] - error fix
			int user_id = Integer.parseInt(temp[0]);
			String date = temp[1];
			String hour_from = temp[2];
			String hour_to = temp[3];
			String errorCode = temp[4];
			String errorDescription = temp[5];
			String errorFix = temp[6];
			stmt.executeUpdate("INSERT INTO jobs (user_id,date,time_from,time_to,error_code,error_disc,error_fix) "
					+ "values("+user_id+",'"+date+"','"+hour_from+"','"+hour_to+"','"+errorCode+"','"+errorDescription+"','"+errorFix+"');");
			return "SUCCESS";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED";
		}
	}
}