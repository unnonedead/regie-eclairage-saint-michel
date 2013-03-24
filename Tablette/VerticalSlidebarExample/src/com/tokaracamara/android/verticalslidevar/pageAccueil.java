package com.tokaracamara.android.verticalslidevar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class pageAccueil extends Activity{
	
	public static socket socketClient;
	private MediaPlayer player = null;
	private boolean entre = true;
	
	public void onCreate(Bundle savedInstanceState) //A la   creation de la vue
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3); //Afficher la vue portant le nom "jeu"
        player = MediaPlayer.create(this, R.raw.son); 
        
        Log.d("Client", "Initialisation de la détection d'erreur");
        if(pageAccueil.isConnected(this) == false)
        {
        	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                    	startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                    	System.exit(1);
                        break;
                    }
                }
            };
     	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Veullez activer le wifi !").setPositiveButton("Paramètre", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
        }
        
        
        new Thread(new Runnable() {
            public void run() {
            	socketClient = new socket();
            	boolean result;
            	try {
           		do
					{
            			Thread.sleep(500);
						try
					    {
							 result = socketClient.seConnecter();
							 player.start();
					    	 Log.d("Client", "La connexion ne génère aucune erreurs"); 
					    	 if(entre == true)
					    	 {
					    		 Log.d("Client", "Entrée pour intent"); 
					    		 Intent intentAccueilAction = new Intent(pageAccueil.this, VerticalSlidebarExampleActivity.class);
					             startActivity(intentAccueilAction);
					             finish();
					             entre = false;
					    	 }
				             
					    }
					    catch(Exception e)
					    {
					    	 Log.d("Client", "Erreur connexion");  
					    	 
					    	 Log.d("Description Erreur",e.toString());
					    	 result = false;
					    }
					}while(result == false);
                    
                   } catch (Exception e)
				{
					Log.d("Client", "Errreur boucle thread");  
			    	 Log.d("Description Erreur",e.toString());
				}
            }
        }).start();        
      
    }
	
	private static boolean isConnected(Context context) {
	    ConnectivityManager connectivityManager = (ConnectivityManager)
	        context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = null;
	    if (connectivityManager != null) {
	        networkInfo =
	            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    }
	    return networkInfo == null ? false : networkInfo.isConnected();
	}
}
