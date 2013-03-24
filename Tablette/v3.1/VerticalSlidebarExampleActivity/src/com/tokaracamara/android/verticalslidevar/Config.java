package com.tokaracamara.android.verticalslidevar;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

public class Config extends Activity{
	
	private EditText ip;
	private String data = null; 
	public static fichier fichierIp;
	
	public void onCreate(Bundle savedInstanceState) //A la   creation de la vue
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main4);
        fichierIp = new fichier();
        data = fichierIp.getIp();
        //lectureReseau();
        ip = (EditText) findViewById(R.id.editIp);
        if(data == null)
        {
	        ip.setText("0.0.0.0");
        }
        else
        {
        	ip.setText(data);
        }
        ip.setText(data);
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
		fichierIp.setIp(ip.getText()+"");
		Intent intentPrincipalAction = new Intent(Config.this, pageAccueil.class);
        startActivity(intentPrincipalAction);
        
        finish();
 	}  
	
	public void lectureReseau()
	{
		int timeOut = 3000; // I recommend 3 seconds at least int timeout = 3000 / / Je recommande 3 secondes au moins 
		boolean status=false;
		try {
			status = InetAddress.getByName ("192.168.100.157"). isReachable (timeOut);
			} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				} 

		}
}
