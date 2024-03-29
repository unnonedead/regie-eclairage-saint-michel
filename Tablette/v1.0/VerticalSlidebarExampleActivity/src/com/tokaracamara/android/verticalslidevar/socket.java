package com.tokaracamara.android.verticalslidevar;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;

public class socket {
	private Socket requestSocket;
    private String serverIpAddress = "192.168.100.157";
    private int portCom = 2013;
    private InetAddress address;
    public byte[] tabEntier;
    private boolean etatConnection;
	
    public socket()
    {
    	this.etatConnection = true;
    	tabEntier = new byte[512];
    	for(int i =0;i<512; i++)
    	{
    		this.tabEntier[i] = 0;
    	}
    	
    }
    
    public boolean isConnected()
    {  	
    	return this.etatConnection ;
    }
    
    public boolean testConnection()
    {
    	try {
			if(InetAddress.getByName(serverIpAddress) == null)
			{
				return false;
			}
			else
				return true;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Connection", "Erreur g�n�rale");
			return false;
		}
    }
    
    public boolean seConnecter() 
	{		
		try{
				address = InetAddress.getByName(serverIpAddress);
		        requestSocket = new Socket(address, portCom);
		        if(requestSocket.isConnected()){
		        Log.d("Client", "Connection �tablie");
		        return true;}
		        else{
		        	return false;
			        }
		}
		catch(IOException ioException){
			ioException.getMessage();
			if(requestSocket.isConnected())
				this.seDeconnecter();
			System.err.println("Erreur");
			Log.d("Client", "Erreur Configuration serveur");
			return false;
		}
		catch(Exception e){
			if(requestSocket.isConnected())
				this.seDeconnecter();
	    	 Log.d("Description Erreur",e.toString());
			return false;
		}
	}
	
	public boolean seDeconnecter()
	{
		try{
			if(requestSocket.isConnected()){	       
				tabEntier[0]= (byte) 1;
				this.envoyerMessage();
				requestSocket.close();
				this.etatConnection =false;
				return true;
			}
			else{
				Log.d("Deconnection","Vous etes d�j� plus connecte");
				return false;
			}
		}
		catch(IOException ioException){
			ioException.printStackTrace();
			return false;
		}
	}
	
	public String recevoirMessage()
	{
		return "A coder !";
		
	}
	
	public void envoyerMessage(byte msg)
	{
		try{
			if(requestSocket.isConnected()){
			OutputStream out = requestSocket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);
			byte[] tab = new byte[1];
			tab[0]=msg;
			dos.writeInt(1);
			dos.write(tab, 0, 1);
			}
		}
		 catch (UnknownHostException e) 
		 {
			 Log.d("Deconnection", "Serveur Inconnue");
			 this.etatConnection =false;
			 Log.d("Description", e.toString());
		    e.printStackTrace();
		 } 
		catch (IOException e) {
			Log.d("Deconnection", "Erreur Socket");
			this.etatConnection =false;
			Log.d("Descritpion", e.toString());
		    e.printStackTrace();
		 } 
	}
	
	public void envoyerMessage(byte[] msg)
	{
		try{
			if(requestSocket.isConnected()){
	 			Log.d("Client","Element envoy�");
				OutputStream out = requestSocket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);
				
				dos.writeInt(512);
				dos.write(msg, 0, 512);
			}
		}
		catch (UnknownHostException e) 
		 {
			 Log.d("Deconnection", "Serveur Inconnue");
			 this.etatConnection =false;
			 Log.d("Description", e.toString());
		    e.printStackTrace();
		 } 
		catch (IOException e) {
			Log.d("Deconnection", "Erreur Socket");
			this.etatConnection =false;
			Log.d("Descritpion", e.toString());
		    e.printStackTrace(); 
		}
	}
	public void envoyerMessage()
	{
		try{
			if(requestSocket.isConnected()){
	 			Log.d("Client","Element envoy�");
				OutputStream out = requestSocket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);
				dos.writeInt(512);
				dos.write(this.tabEntier, 0, 512);
			}
			else
			{
				Log.d("Client","Deconnecter");
				//new Exception() ;
			}
		}
		 catch (UnknownHostException e) 
		 {
			 Log.d("Client", "Erreur Serveur Introuvable");
			 Log.d("Descritpion", e.toString());
			 this.etatConnection =false;
		    e.printStackTrace();
		 } 
		catch (IOException e) {
			Log.d("Client", "Erreur d'envoi");
			this.etatConnection =false;
			Log.d("Descritpion", e.toString());
		 } 
	}
	public void setIp(String ip)
	{
		this.serverIpAddress = ip;
	}
	public void setPort(int port)
	{
		if(port < 0)
		{
			this.portCom = 5000;
		}
		else
		{
			this.portCom = port;
		}
	}
	public void setPort(String port)
	{
		int portInt = Integer.parseInt(port);
		if(portInt < 0)
		{
			this.portCom = 5000;
		}
		else
		{
			this.portCom = portInt;
		}
	}
}
