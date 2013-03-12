package com.tokaracamara.android.verticalslidevar;

import com.tokaracamara.android.verticalslidevar.VerticalSeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class VerticalSlidebarExampleActivity extends Activity {
    /** Called when the activity is first created. */
    
	public socket socketClient = pageAccueil.socketClient;
	private VerticalSeekBar[] tabSeek = new VerticalSeekBar[12];
	private EditText[] tabInput = new EditText[12];
    protected ImageButton changerVue;
    private int boucleFor;
    int[] projo = new int[12];
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.initialiseTableau(100,0);
        this.initialiseTableauInput();

        new Thread(new Runnable() {
			public void run() {
            	boolean result;
            	try {
           		do{
            		Thread.sleep(2000);
						if(socketClient.isConnected()==true){
							result= false;
						}
						else{
							decoServeur();
							Log.d("Client", "Deconnecté");
							result = true;
						}
					}while(result == false);
                   } catch (Exception e){
					Log.d("Client", "Errreur boucle thread");  
			    	 Log.d("Description Erreur",e.toString());
                   }
            }
        }).start();

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
        	   Log.d("CDA", "onBackPressed Called");
        	   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                       case DialogInterface.BUTTON_POSITIVE:
                    	   socketClient.seDeconnecter();
                    	   System.exit(1);
                           break;
                       case DialogInterface.BUTTON_NEGATIVE:
                           break;
                       }
                   }
               };
        	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setMessage("Voulez vous quiter l'application ?").setPositiveButton("Oui", dialogClickListener)
                   .setNegativeButton("Non", dialogClickListener).show();
        	}  
       
  	public void tableauProjecteur()
    {  
  		try {
	  		for(int i = 0; i < 12 ; i++)
	    	{
	    		socketClient.tabEntier[i] = (byte)(int)((tabSeek[i].getProgress())*2.55);
	    	}	 
    	socketClient.setEnvoyer(true);
    	//socketClient.envoyerMessage();
    	}
    	catch(Exception e)
    	{
    		Log.d("Descritpion", e.toString());
    		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                    	Intent intentPrincipalAction = new Intent(VerticalSlidebarExampleActivity.this, pageAccueil.class);
                        startActivity(intentPrincipalAction);
                        finish();
                        System.exit(1);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                    	
                    	socketClient.seDeconnecter();
                    	System.exit(1);
                    	
                        break;
                    }
                }
            };
     	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Le serveur est déconnecté !").setPositiveButton("Se reconnecter", dialogClickListener)
                .setNegativeButton("Quitter", dialogClickListener).show();			
            }
            }
  	
    public void initialiseTableau(int max, int enCour)
    {
    	tabSeek[0] = (VerticalSeekBar)findViewById(R.id.SeekBar01);
    	tabSeek[1] = (VerticalSeekBar)findViewById(R.id.SeekBar02);
    	tabSeek[2] = (VerticalSeekBar)findViewById(R.id.SeekBar03);
    	tabSeek[3] = (VerticalSeekBar)findViewById(R.id.SeekBar04);
    	tabSeek[4] = (VerticalSeekBar)findViewById(R.id.SeekBar05);
    	tabSeek[5] = (VerticalSeekBar)findViewById(R.id.SeekBar06);
    	tabSeek[6] = (VerticalSeekBar)findViewById(R.id.SeekBar07);
    	tabSeek[7] = (VerticalSeekBar)findViewById(R.id.SeekBar08);
    	tabSeek[8] = (VerticalSeekBar)findViewById(R.id.SeekBar09);
    	tabSeek[9] = (VerticalSeekBar)findViewById(R.id.SeekBar10);
    	tabSeek[10] = (VerticalSeekBar)findViewById(R.id.SeekBar11);
    	tabSeek[11] = (VerticalSeekBar)findViewById(R.id.SeekBar12);
    	for(int i = 0; i < 12; i++)
    	{
        tabSeek[i].setMax(max);
        tabSeek[i].setProgress(enCour);
        tabSeek[i].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        	
			public void onProgressChanged(VerticalSeekBar seekBar,
					int progress, boolean fromUser) {
				int z =-1;
				if(progress != seekBar.getProgress())
				{
					try
					{
						Log.d("Client", "on tracking");  
						//tableauProjecteur();
					}catch(Exception e)
					{Log.d("Client", "Errreur Envoie message tableau");  
			    	 Log.d("Description Erreur :",e.toString());}
				}
				do{	z++;
				Log.d("Client", ""+seekBar.getProgress()); 
				}while(tabSeek[z] != seekBar && z < 12);
					tabInput[z].setText(""+seekBar.getProgress()+"");
					tableauProjecteur();
			}
			public void onStartTrackingTouch(VerticalSeekBar seekBar) {}
			public void onStopTrackingTouch(VerticalSeekBar seekBar) {
				try
				{
					//Log.d("Client", "Relacher le doigt");  
					//tableauProjecteur();
				}catch(Exception e)
				{Log.d("Client", "Errreur Envoie message tableau");  
		    	 Log.d("Description Erreur :",e.toString());}
				int z = -1;
				do{	z++;
				}while(tabSeek[z] != seekBar && z < 12); 
			}
			
		});
    	}
    }
    
    public void decoServeur()
    {
    	AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
    	TextView myMsg = new TextView(this);
    	myMsg.setText("Le serveur est deconnecté");
    	myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
    	popupBuilder.setView(myMsg);
    	Intent intentPrincipalAction = new Intent(VerticalSlidebarExampleActivity.this, pageAccueil.class);
        startActivity(intentPrincipalAction);
        finish();
    }
    public void initialiseTableauInput()
    {
    	
    	tabInput[0] = (EditText) findViewById(R.id.editText1);
    	tabInput[1] = (EditText) findViewById(R.id.editText2);
    	tabInput[2] = (EditText) findViewById(R.id.editText3);
    	tabInput[3] = (EditText) findViewById(R.id.editText4);
    	tabInput[4] = (EditText) findViewById(R.id.editText5);
    	tabInput[5] = (EditText) findViewById(R.id.editText6);
    	tabInput[6] = (EditText) findViewById(R.id.editText7);
    	tabInput[7] = (EditText) findViewById(R.id.editText8);
    	tabInput[8] = (EditText) findViewById(R.id.editText9);
    	tabInput[9] = (EditText) findViewById(R.id.editText10);
    	tabInput[10] = (EditText) findViewById(R.id.editText11);
    	tabInput[11] = (EditText) findViewById(R.id.editText12);
    	for(this.boucleFor = 0; this.boucleFor < 12;this.boucleFor++)
    	{
    		
    		tabInput[boucleFor].setText(""+tabSeek[boucleFor].getProgress()+"");
    		tabInput[boucleFor].setFocusable(false);
    		tabInput[boucleFor].setOnEditorActionListener(new TextView.OnEditorActionListener() {
    				@Override
    				public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
    					tabSeek[boucleFor].setProgress(Integer.parseInt(tabInput[boucleFor].getText().toString()));
    					return false;
    				}
    				
    				
    	        });
    	}	
    }
    public void fonduAuNoir()
	{
    	int i = 0;
		for(i=0;i<100;i++)
				{
			int result = tabSeek[i].getProgress();
			result--;
			tabSeek[i].setProgress(result);
			
		}
		
	}
}