import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;

public class JGenerator 
{
	public static DatagramSocket clientSocketUDP;
	public static DatagramPacket sendPacket;
	public static byte[] sendData = new byte[1024];
	
	public static Socket clientSocketTCP;
	public static PrintWriter pWriter;
	
	public static void main(String[] args) throws IOException 
	{
		generateGUI();
	}
		
	public static void generateTraffic()
	{
		double totalTime = 0;
		try
		{
			if(Resource.TYPE.equals("UDP"))
			{
				clientSocketUDP = new DatagramSocket(Integer.parseInt(Resource.PORT));
				while(totalTime/1000 < Resource.DURATION)
				{
					sendData = "NOISE: AHGFHADFYGTN#^RGFNQIYAHUNYGRCBNUGXYNRFJFGXH JYFGDK UFYXDFXUNGDSKFUXNGSKYUFGXASNDFXHASNGXADSKNFXGADNFXHGADNFXJADGFXBAJDFXNGBDANFXAKSBYGXNJHGBADXBFNAFJHAKFJXNHAKUFXHNAKYUGFXNAYUGUFX".getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(Resource.IP), Integer.parseInt(Resource.PORT));
					clientSocketUDP.send(sendPacket);
					totalTime++;
				}
				
				clientSocketUDP.close();
			}
			else
			{
				while(totalTime/1000 < Resource.DURATION)
				{
					clientSocketTCP = new Socket(Resource.IP, Integer.parseInt(Resource.PORT));
					pWriter = new PrintWriter(clientSocketTCP.getOutputStream(), true);
					
					pWriter.println("NOISE: AHGFHADFYGTN#^RGFNQIYAHUNYGRCBNUGXYNRFJFGXH JYFGDK UFYXDFXUNGDSKFUXNGSKYUFGXASNDFXHASNGXADSKNFXGADNFXHGADNFXJADGFXBAJDFXNGBDANFXAKSBYGXNJHGBADXBFNAFJHAKFJXNHAKUFXHNAKYUGFXNAYUGUFX");
					totalTime++;
				}
			}
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void generateGUI()
	{
		GUI go = new GUI();

		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setSize(440, 185);
		go.setResizable(false);
		go.setVisible(true);
	}
	
	public static void GUIExecute(Object[] parameters) throws IOException
	{
		if(((String)parameters[0]).contains(":"))
		{
			Resource.IP	= "http://" + ((String)parameters[0]).split(":")[0];
			Resource.PORT	= ((String)parameters[0]).split(":")[1];
		}
		else
			Resource.IP = "http://" + (String)parameters[0];
		
		Resource.TYPE = (String)parameters[1];
		Resource.DURATION	= (int)parameters[2];
		
		generateTraffic();
	}
}