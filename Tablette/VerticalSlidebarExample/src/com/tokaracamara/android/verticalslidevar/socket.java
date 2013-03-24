package com.tokaracamara.android.verticalslidevar;

import java.io.BufferedWriter;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;


import java.net.HttpRetryException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.CharacterCodingException;
import java.util.InvalidPropertiesFormatException;
import java.util.zip.ZipException;

import javax.net.ssl.SSLException;

import org.apache.http.ConnectionClosedException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;

import android.util.Log;

public class socket {
	private Socket requestSocket;
	private PrintWriter out;
 	private ObjectInputStream in;
 	private String message;
    private String serverIpAddress = "192.168.100.197";
    private static final int REDIRECTED_SERVERPORT = 2013;
	
	public boolean seConnecter() throws CharConversionException, CharacterCodingException, ClientProtocolException, ClosedChannelException, ConnectionClosedException, EOFException, FileLockInterruptionException, FileNotFoundException, HttpRetryException, InterruptedIOException, InvalidPropertiesFormatException, MalformedChunkCodingException, MalformedURLException, NoHttpResponseException, ObjectStreamException, SSLException, SocketException, SyncFailedException, UTFDataFormatException, UnknownHostException, UnknownServiceException, UnsupportedEncodingException, ZipException, ProtocolException
	{		
		try{
			
	        InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
	        //Log.d("Client", "serveur trouvé");	
	        requestSocket = new Socket(serverAddr, REDIRECTED_SERVERPORT);
	        Log.d("Client", "Connection établie");
	        //1. creating a socket to connect to the server
			//System.out.println("Connected to localhost in port 2013");
			//2. get Input and Output streams
			//this.out = new ObjectOutputStream(requestSocket.getOutputStream());
			 //this.envoyerMessage("Bonjour je suis Android 4.0.3");
			 //this.out.flush();
			//this.in = new ObjectInputStream(requestSocket.getInputStream());
			 //Log.d("Client", "Client sent message");
			return true;
		}
		catch(UnknownHostException unknownHost){
			//unknownHost.printStackTrace();
			//System.err.println("You are trying to connect to an unknown host!");
			this.seDeconnecter();
			Log.d("Client", "Serveur Introuvable");
			return false;
		}
		catch(IOException ioException){
			ioException.getMessage();
			this.seDeconnecter();
			System.err.println("Erreur");
			Log.d("Client", "Erreur Configuration serveur");
			return false;
		}
		catch(Exception e)
		{
			Log.d("Client", "Errreur boucle thread");
			this.seDeconnecter();
	    	 Log.d("Description Erreur",e.toString());
			return false;
		}
	}
	
	public boolean seDeconnecter()
	{
		try{
			in.close();
			out.close();
			requestSocket.close();
			return true;
		}
		catch(IOException ioException){
			ioException.printStackTrace();
			return false;
		}
	}
	public Runnable run() throws InterruptedException 
	{		
			//3: Communicating with the server
		/*	while(this.seConnecter())
			{
				
				
			}*/
			return null;
	}
	
	public String recevoirMessage() throws IOException, IOException
	{
		try{
		this.message = (String)this.in.readObject();
		System.out.println("server>" + this.message);
		return this.message;
		}
		catch(ClassNotFoundException classNot){
			System.err.println("data received in unknown format");
			return "";
		}
		
	}
	
	public void envoyerMessage(char[] msg)
	{
		try{
			Log.d("elément tableau","1er élément : "+msg[0]
					+"\n2eme élément : "+msg[1]
					+"\n3eme élément : "+msg[2]
					+"\n4eme élément : "+msg[3]
					+"\n5eme élément : "+msg[4]
					+"\n6eme élément : "+msg[5]
					+"\n7eme élément : "+msg[6]
					+"\n8eme élément : "+msg[7]
					+"\n9eme élément : "+msg[8]
					+"\n10eme élément : "+msg[9]
					+"\n11eme élément : "+msg[10]
					+"\n12eme élément : "+msg[11]);
			
			this.out = new PrintWriter(
				 	new BufferedWriter(
				 				new OutputStreamWriter(requestSocket.getOutputStream())),true);
			char[] dmx = new char[512];
			dmx = msg;
			
			
			
 			out.write(dmx, 0, 512);
 			out.flush();
 			Log.d("Client","Element envoyé");
 			/*InetSocketAddress address = new InetSocketAddress("10.1.1.1", 12350);
 	        DatagramPacket request = new DatagramPacket(cmd.getBytes(), cmd.length(), address);
 	        DatagramSocket socket = new DatagramSocket();
 	        socket.send(request);*/
		}
		 catch (UnknownHostException e) 
		 {
			 Log.d("Client", "Error1");
		    e.printStackTrace();
		 } 
		catch (IOException e) {
			Log.d("Client", "Error2");
		    e.printStackTrace();
		 } 
	}
}
