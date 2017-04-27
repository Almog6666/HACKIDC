package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataTypes.Job;
import dataTypes.Message;

public class ServerThread extends Thread {
	Socket socket;
	Statement stmt;
	PrintWriter temp = null;
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		
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
					stmt = Server.getConnection().createStatement();
					temp.println(HandleUser(tempMsg));//Sending the output to the user
				}catch(Exception e)
				{
					System.out.println("String not fine = "+message);
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
		case "NEWORDER"://for the client
			return addNewOrder(msg.dataString);
		case "GETJOBS"://for the tech
			return getAvailJobs(msg.dataString);

		default:
			return "DREK";

		}

	}	


	private String getAvailJobs(String data)
	{
		try {
			//get all techs -1
			//make Job for each one
			//add to arraylist
			//send arraylist
			ResultSet rs = stmt.executeQuery("SELECT * FROM jobs WHERE taken_by_tech=-1;");
			ArrayList<Job> res = new ArrayList<Job>();
			ResultSet loc;
			if(rs.next())
			{
				do
				{
				//int job_id, String location, int user_id, String date, String time_from, String time_to, int model,
					//	String ecode, String edisc, String efix
					Statement tester = Server.getConnection().createStatement();
					loc =tester.executeQuery("SELECT location from users where user_id="+rs.getInt(2)+";"); 
					loc.next();
					String location = loc.getString(1);
					loc.close();
					System.out.println("Inside While"  + rs.getInt(1));
					Job job = new Job(rs.getInt(1)
							,location
							,rs.getInt(2)
							,rs.getString(3)
							,rs.getString(4)
							,rs.getString(5)
							,rs.getInt(6)
							,rs.getString(7)
							,rs.getString(8)
							,rs.getString(9));
					System.out.println(job.toString());
					res.add(job);
					
				
				}while (rs.next());
				ObjectOutputStream r = new ObjectOutputStream(socket.getOutputStream());
				r.writeObject(res);
				temp.print(res);
			//r.writeObject(res);
				return "SUCCESS";
			}
			else throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED";
		}


	}














	private String addNewOrder(String data)
	{
		try {
			String[] temp = data.split(",");//[0] - user_id, [1] - requested date,
			//  [2] - time_from,[3] - time_to [4] - model number [5] - error code, [6] - error description, [7] - error fix
			int user_id = Integer.parseInt(temp[0]);
			String date = temp[1];
			String hour_from = temp[2];
			String hour_to = temp[3];
			int model = Integer.parseInt(temp[4]);
			String errorCode = temp[5];
			String errorDescription = temp[6];
			String errorFix = temp[7];
			stmt.executeUpdate("INSERT INTO jobs (user_id,date,time_from,time_to,model,error_code,error_disc,error_fix) "
					+ "values("+user_id+",'"+date+"','"+hour_from+"','"+hour_to+"',"+model+",'"+errorCode+"','"+errorDescription+"','"+errorFix+"');");
			System.out.println("sending succ");
			return "SUCCESS";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED";
		}
	}
}