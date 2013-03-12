package com.tokaracamara.android.verticalslidevar;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import android.os.Environment;
import android.util.Log;

public class fichier {

	private String ip = "0.0.0.0";
	private String FileName = "config.dat";
	private File dossier;
	private File fichier;
	
	
	public fichier()
	{
		this.dossier = Environment.getExternalStorageDirectory(); 
		this.fichier = new File(this.dossier,this.FileName); 

		try {
			
			this.fichier.createNewFile();
			this.lire();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("Création de fichier", "Erreur");
			Log.i("Création de fichier", e.toString());
		}	
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
		this.ecrire();
	}
	
	public String getIp()
	{
		if(this.lire() == null)
			return this.ip;
		else
		{
			this.ip = this.lire();
			return this.ip;
		}
	}
	
	private void ecrire(){  
		try{ 
			 PrintWriter pWriter = new PrintWriter(new FileWriter(this.fichier, false));
		        pWriter.print(this.ip);
		        pWriter.close() ;
		        Log.d("Fichier", "Ecriture Ok");
           }catch (Exception e) {       
        	   Log.d("Fichier", "Ecriture fail"); 
        	   Log.d("Fichier", e.toString());
            }
       }
	
	private String lire(){ 
		String line = null;
		try {
			Scanner scanner = new Scanner(new FileReader(this.fichier));
			
			 while (scanner.hasNextLine()) {
				 line = scanner.nextLine();
			 }
			 if(line == "")
				 line = "0.0.0.0";
			 Log.d("Fichier", "lecture Ok");
		}catch(Exception e)
		{
			 Log.d("Fichier", "lecture fail");
			 Log.d("Fichier", e.toString());
		}
		return line;
       }
}
