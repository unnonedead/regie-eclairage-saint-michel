package com.tokaracamara.android.verticalslidevar;


import com.tokaracamara.android.verticalslidevar.VerticalSeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    
    String[] items = new String[] {"One", "Two", "Three"};
    int[] projo = new int[512];
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.initialiseTableau(100,0);
        this.initialiseTableauInput();
        Log.i("client", socketClient.toString());
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
  		char tabChar[]= new char[512];
    	for(int i = 0; i < 512 ; i++)
    	{
    		if(i < 12)
    		{
    			if(projo[i] == 0 || projo == null)
    				projo[i]=1;
    			projo[i] = (int)((tabSeek[i].getProgress())*2.55);
    		}
    		else
    		{
    			projo[i] = 1;
    		}
    		tabChar[i] = (char)projo[i]; 
    	}	
    	try
    	{
    	socketClient.envoyerMessage(tabChar);
    	}
    	catch(Exception e)
    	{
    		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                	Intent intentAccueilAction = new Intent(VerticalSlidebarExampleActivity.this, pageAccueil.class);
                    startActivity(intentAccueilAction);
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                	System.exit(1);
                    break;
                }
            }
        };
 	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Le serveur est introuvable voulez vous vous reconnecter ?").setPositiveButton("oui", dialogClickListener)
            .setNegativeButton("Non", dialogClickListener).show();				
    		 
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
				do{	z++;
				}while(tabSeek[z] != seekBar && z < 12);
					tabInput[z].setText(""+seekBar.getProgress()+"");
			}
			public void onStartTrackingTouch(VerticalSeekBar seekBar) {}
			public void onStopTrackingTouch(VerticalSeekBar seekBar) {
				try
				{
					Log.d("Client", "coucou sa va etre executer");  
					tableauProjecteur();
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