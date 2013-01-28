package com.tokaracamara.android.verticalslidevar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;


public class Config extends Activity{
	
	private EditText ip;
	private EditText port;
	private String data = null; 
	public String[] dataTab = null;
	
	public void onCreate(Bundle savedInstanceState) //A la   creation de la vue
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main4);
        data = this.ReadSettings();
        
        ip = (EditText) findViewById(R.id.editIp);
        port = (EditText) findViewById(R.id.editPort);
        if(data == null || this.fichierExiste()==false)
        {
	        ip.setText("192.168.100.197");
	        port.setText("2013");
        }
        else
        {
        	dataTab = data.split(":");
        	ip.setText(dataTab[0]);
	        port.setText(dataTab[1]);
        }
        
    }
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
        	}
        return super.onKeyDown(keyCode, event);
    	}
	
	public void onBackPressed() {
		WriteSettings(this, ip.getText()+":"+port.getText());
		Intent intentPrincipalAction = new Intent(Config.this, pageAccueil.class);
        startActivity(intentPrincipalAction);
        
        finish();
 	}  
	
	public boolean fichierExiste()
	{
		try{ 
			OutputStreamWriter ecrire = new OutputStreamWriter(openFileOutput("regiLightSetting.dat",0));
		  return true;
		}catch(Exception e)
		{return false;}
	}
	
	public void WriteSettings(Context context, String data){  
		OutputStreamWriter ecrire;
		try{ 
		  ecrire = new OutputStreamWriter(openFileOutput("regiLightSetting.dat",0));
		  ecrire.write(data);
		  ecrire.close();
          Toast.makeText(context, "Informations sauvegardées",Toast.LENGTH_SHORT).show(); 
           }catch (Exception e) {       
                    Toast.makeText(context, "Informations non sauvegardées",Toast.LENGTH_SHORT).show(); 
            }
       }
	
	
	public String ReadSettings(){ 
		String line = null;
		try {
		    InputStream instream = openFileInput("regiLightSetting.dat");
		    if (instream != null) {
		      InputStreamReader inputreader = new InputStreamReader(instream);
		      BufferedReader buffreader = new BufferedReader(inputreader);
		      while (( line = buffreader.readLine()) != null) {}
		    	}     
		    instream.close();
		  } catch (java.io.FileNotFoundException e) {Log.d("Fichier", "fichier introuvable");
			Log.d("Fichier", e.toString());} 
		catch(IOException e){Log.d("Fichier COnfig", "Lecture Impossible");
		Log.d("Fichier", e.toString());}
		return line;
       }
	
}
