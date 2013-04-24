import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.JFrame;

public class JGenerator 
{
	private static String IP				= "http://192.168.1.100";
	private static String PORT				= "8080";
	private static String URL				= IP + ":" + PORT;

	private static int DURATION				= 30;

	private static int rate;
	
	private static boolean GUI = false;
	
	public static Socket clientSocket;
	public static PrintWriter pWriter;
	
	public static void main(String[] args) throws IOException 
	{
		generateGUI();
	}
		
	public static void generateTraffic()
	{
		URL = IP + ":" + PORT;
		
		double totalTime = 0;
		try
		{
			clientSocket = new Socket(Resource.IP, Integer.parseInt(Resource.PORT));
			pWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			
			while(totalTime/1000 < DURATION)
			{
				pWriter.println("TRAFFIC!!!!");
				totalTime++;
			}
			
			pWriter.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void generateGUI()
	{
		GUI go = new GUI();

		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setSize(440, 155);
		go.setResizable(false);
		go.setVisible(true);
	}
	
	public static void GUIExecute(Object[] parameters) throws IOException
	{
		GUI			= true;
		if(((String)parameters[0]).contains(":"))
		{
			IP		= "http://" + ((String)parameters[0]).split(":")[0];
			PORT	= ((String)parameters[0]).split(":")[1];
		}
		else
			IP = "http://" + (String)parameters[0];
		
		rate		= (int)parameters[1];
		DURATION	= (int)parameters[2];
		
		generateTraffic();
	}
}