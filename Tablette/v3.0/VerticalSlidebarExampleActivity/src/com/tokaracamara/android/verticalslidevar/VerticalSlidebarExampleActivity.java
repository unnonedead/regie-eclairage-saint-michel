package com.tokaracamara.android.verticalslidevar;

import java.lang.reflect.Method;

import com.tokaracamara.android.verticalslidevar.VerticalSeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class VerticalSlidebarExampleActivity extends Activity implements OnItemSelectedListener {
    /** Called when the activity is first created. */
    
	public socket socketClient = pageAccueil.socketClient;
	public VerticalSeekBar[] tabSeek = new VerticalSeekBar[12];
	private EditText[] tabInput = new EditText[12];
	private Spinner[] tabSpinner = new Spinner[12];
    protected ImageButton changerVue;
    private int boucleFor;
    private int test=0;
    private int [] tableauCouleur = new int[]{Color.WHITE,Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.CYAN,Color.MAGENTA};
    private int[] projoProgress = new int[12];
    private final Button[] tabButtonGroupe = new Button[12];
    private int[] tabIndiceGroupeBouton = new int[12];
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.initialiseTableau(100,0);											// Initialisation des 12 seekBars (Valeur max, valeur min)
        this.initialiseTableauInput();											// Initialisation des 12 inputBox non selectionable ils indiquent la valeur en % des projecteur
        this.initialiseProjoProgress(0);										// Initialise un tableau pour sauvegarder les valeur des projecteur à t-1
        this.initBoutonGroupe();												// Initialisation des bouton pour l'appartenance de groupe de chaques projecteurs
        this.initBoutonAction();												// Initialisation des boutons de supplémentaire (ex fondu, remise à zero, etc..)
        
        new Thread(new Runnable() {
				public void run() {
			            	boolean result;
			            	try {												//////////////////////////////////////////////////////////
			           		do{													// Thread de test de connexion 							//
			            		Thread.sleep(2000);								// Il verifie si l'utilisateur est toujours connecté	//
									if(socketClient.isConnected()==true){		// Sinon la connexio a été perdu on execute 			//
										result= false;							// la fonction decoServeur								//
									}											//////////////////////////////////////////////////////////
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
    
        public boolean onKeyDown(int keyCode, KeyEvent event)  {			//////////////////////////////////////////////////////////
            if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5			// Methode Android qui écoute les touche de l'appareil	//
                    && keyCode == KeyEvent.KEYCODE_BACK						// appuyées et execute la fonction onBackPressed si elle//
                    && event.getRepeatCount() == 0) {						// elle correspond à la touche retour de l'appareil		//
                Log.d("CDA", "onKeyDown Called");							//////////////////////////////////////////////////////////
                onBackPressed();
            	}
            return super.onKeyDown(keyCode, event);
        	}
    
        public void onBackPressed() {
        	   Log.d("CDA", "onBackPressed Called");
        	   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {	//////////////////////////////////////////////////////////////
                       switch (which){										// Une boite de dialogue est créée et propose deux 			//
                       case DialogInterface.BUTTON_POSITIVE:				// réponse possible si la reponse est positive l'application//
                    	   socketClient.seDeconnecter();					// se deconnecte et se ferme								//
                    	   System.exit(1);									//////////////////////////////////////////////////////////////
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
     
        public void initBoutonAction()
        {
        	ImageButton fonduBlanc = (ImageButton)  findViewById(R.id.butFonduBlancs);
        	fonduBlanc.setOnClickListener(new View.OnClickListener() {						//////////////////////////////////////////////////////////////
																							// initialise et créer le bouton pour le fondu an blanc		//
				@Override																	// ajout d'un listener et si le bouton est cliquer la 		//
				public void onClick(View v) {												// methode fondu s'execute									//
					// TODO Auto-generated method stub										//////////////////////////////////////////////////////////////
					fonduThread blanc=new fonduThread(VerticalSlidebarExampleActivity.this,true);
					blanc.execute();
					 
				}
			});
        	
        	ImageButton fonduNoir = (ImageButton)  findViewById(R.id.butFonduNoir);			//////////////////////////////////////////////////////////////
        	fonduNoir.setOnClickListener(new View.OnClickListener() {						// 					idem que fonduBlanc						//
        																					//////////////////////////////////////////////////////////////
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub									//////////////////////////////////////////////////////////////
					fonduThread noir=new fonduThread(VerticalSlidebarExampleActivity.this,false);
					noir.execute();
					
					
				}
			});
        	
        	ImageButton test = (ImageButton)  findViewById(R.id.butRestart);				//////////////////////////////////////////////////////////////////
        	test.setOnClickListener(new View.OnClickListener() {							// initialise et créer un bouton pour réinitialisé les groupes	//
				@Override																	// tous les groupes sont mis à 0 càd par default ils sont seuls	//
				public void onClick(View v) {												//////////////////////////////////////////////////////////////////
					for(int j = 0; j<12;j++)
					{
						tabIndiceGroupeBouton[j]=0;
						tabSeek[j].setGroupe(tabIndiceGroupeBouton[j]);
		        		tabButtonGroupe[j].setBackgroundColor(tableauCouleur[0]);
					}
					
				}
			});
        	
        }
        
        public void initBoutonGroupe()
        {
        	this.tabButtonGroupe[0] = (Button)  findViewById(R.id.button1);
        	this.tabButtonGroupe[1] = (Button)  findViewById(R.id.button2);
        	this.tabButtonGroupe[2] = (Button)  findViewById(R.id.button3);						//////////////////////////////////////////////////////////////
        	this.tabButtonGroupe[3] = (Button)  findViewById(R.id.button4);						// Declaration de tous les boutons groupes dans un tableau 	//
        	this.tabButtonGroupe[4] = (Button)  findViewById(R.id.button5);						// Ensuite ils sont initialisées à 0 et la couleur blanc	//
        	this.tabButtonGroupe[5] = (Button)  findViewById(R.id.button6);						// le listener pour chacun d'eux grace à une boucle			//
        	this.tabButtonGroupe[6] = (Button)  findViewById(R.id.button7);						//////////////////////////////////////////////////////////////
        	this.tabButtonGroupe[7] = (Button)  findViewById(R.id.button8);
        	this.tabButtonGroupe[8] = (Button)  findViewById(R.id.button9);
        	this.tabButtonGroupe[9] = (Button)  findViewById(R.id.button10);
        	this.tabButtonGroupe[10] = (Button)  findViewById(R.id.button11);
        	this.tabButtonGroupe[11] = (Button)  findViewById(R.id.button12);
        	
        	for(int j = 0;j<12;j++)
	   	     {
   	    	 	this.tabIndiceGroupeBouton[j]=0;
        		this.tabButtonGroupe[j].setBackgroundColor(this.tableauCouleur[0]);
        		this.tabButtonGroupe[j].setOnClickListener(new View.OnClickListener()
                	{
        			@Override
        			public void onClick(View v) {											//////////////////////////////////////////////////////////////
        				Log.e("TAG", "View clicked v: " + v.getId());						// le listener cherche le numéro du bouton (l'emplacement)	//
        				int z=-1;															// pour pouvoir assigner le groupe à l'objet seekbar		//
        				do{z++;}while(tabButtonGroupe[z].getId() != v.getId());				// lors de l'action de ce listener il change le groupe		//
        				Log.e("TAG", "Button select: " + tabButtonGroupe[z].getId());		// si le groupe est egale à 7 la valeur repasse a zéro car 	//
        				tabIndiceGroupeBouton[z]+=1;										// il n'y a que 6 groupe les valeurs des groupe est contenue//
        				if(tabIndiceGroupeBouton[z] == 7)									// dans un tableau qui suit le nombre de seekbar ce qui nous//
        				{																	// donne une valeur par seekbar ex seek 1 => 3/				//
        					tabIndiceGroupeBouton[z]=0;										// La valeur est ensuite injecter dans un attribut de la	//
        				}																	// classe seekbar.											//
        				tabSeek[z].setGroupe(tabIndiceGroupeBouton[z]);						// Et enfin ou change la couleur du bouton pour plus 		//
        				Log.d("Indice "+z,"COntenue "+tabIndiceGroupeBouton[z]);			// d'ergonomie. Ces couleurs sont aussi dans un tableau 	//
        				for(int i=0;i<7;i++)												// représentant les 6 groupes								//
        				{																	//////////////////////////////////////////////////////////////
        					if( tabIndiceGroupeBouton[z] == i )
        					{
        						if(tabIndiceGroupeBouton[z] == 0)
        						{v.setBackgroundColor(tableauCouleur[0]);}
        						else
        						{v.setBackgroundColor(tableauCouleur[i]);}
        					}
        				}
        			}
                	});  
	   	     }	
        	
        	
        }
        
    public void ajustementGroupe(VerticalSeekBar seek,int z)
    {
    	int difference = seek.getProgress()-this.projoProgress[z];    	    	
    	if(difference != 0)
	    	{
    		test++;																				//////////////////////////////////////////////////////////////////
    		for(int i =0;i<12;i++)																// Methode pour effectuer le changement des track dans groupe	//
		{																						// On récupère l'ancienne valeur de la seekbar grâce à notre	//
	    			if(seek.getGroupe() == this.tabSeek[i].getGroupe())							// tableau projoProgress et on fais la différence avec la valeur//
	    			{																			// actuelle pour avoir cette différence que l'on va affecter aux//
	    				if(this.tabSeek[i] != seek)												// seekbars du même groupe.										//
	    				{																		//////////////////////////////////////////////////////////////////
	    					this.tabSeek[i].setProgress2(this.projoProgress[i] + difference);
	    					
	    					this.tabInput[i].setText(this.tabSeek[i].getProgress()+"");
	    					Log.i("seek "+i, ""+this.tabSeek[i].getProgress());
	    					this.projoProgress[i]=this.tabSeek[i].getProgress();
	    				}
    			}
    		}
    		}
    		this.projoProgress[z]=seek.getProgress();
    }
        
  	public void tableauProjecteur()
    {  
  		try {
	  		for(int i = 0; i < 12 ; i++)														///////////////////////////////////////////////////////////////////////
	    	{																					// Methode pour modifier la valeur du tableau d'envoie de projecteur //
	    		socketClient.tabEntier[i] = (byte)(int)((tabSeek[i].getProgress())*2.55);		///////////////////////////////////////////////////////////////////////
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
  	
  	public void initialiseProjoProgress(int init)
  	{
  		for(int i=0;i<12;i++)						//////////////////////////////////////////////////////////////
  		{											// initialisation du tableau des valeurs de seekbar a t-1	//
  			this.projoProgress[i] = init;			//////////////////////////////////////////////////////////////
  		}
  	}
  	
    public void initialiseTableau(int max, int enCour)
    {
    	tabSeek[0] = (VerticalSeekBar)findViewById(R.id.SeekBar01);
    	tabSeek[1] = (VerticalSeekBar)findViewById(R.id.SeekBar02);
    	tabSeek[2] = (VerticalSeekBar)findViewById(R.id.SeekBar03);					//////////////////////////////////////////////////////////////
    	tabSeek[3] = (VerticalSeekBar)findViewById(R.id.SeekBar04);					// Ajout dans un tableau des seekbars, initialisation des	//
    	tabSeek[4] = (VerticalSeekBar)findViewById(R.id.SeekBar05);					// seekbar avec leurs valeurs max et min 					//
    	tabSeek[5] = (VerticalSeekBar)findViewById(R.id.SeekBar06);					//////////////////////////////////////////////////////////////
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
				
				do{	z++;																//////////////////////////////////////////////////////////////
				}while(tabSeek[z] != seekBar && z < 12);								// ce listener s'execute lorsque la seekBar change d'état	//
				if(projoProgress[z] != seekBar.getProgress())							// (en mouvement ou pas)									//
				{																		// si il y a une différence et le groupe est différent de 0	//
					try																	// il execute la methode ajustementGroupe et envoie l'ordre	//
					{																	// d'envoyer à la methode tableauProjecteur					//
							tabInput[z].setText(""+seekBar.getProgress()+"");			//////////////////////////////////////////////////////////////
							if(seekBar.getGroupe() !=0) ajustementGroupe(seekBar,z);
							tableauProjecteur();
					}catch(Exception e)
					{Log.d("On mouse track", "Errreur dans groupe ou envoie");  
			    	 Log.d("Description Erreur :",e.toString());}
				}
				
			}
			public void onStartTrackingTouch(VerticalSeekBar seekBar) {}
			public void onStopTrackingTouch(VerticalSeekBar seekBar) {
				try
				{
					//Log.d("Client", "Relacher le doigt");  							//////////////////////////////////////////////////////////////
					//tableauProjecteur();												// Listener agissant lors du relacher du doigt (onmouseUp)	//
				}catch(Exception e)														//////////////////////////////////////////////////////////////
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
    	myMsg.setText("Le serveur est deconnecté");																//////////////////////////////////////////////////////////////
    	myMsg.setGravity(Gravity.CENTER_HORIZONTAL);															// Alert box qui s'affiche pour signaler que le serveur et 	//
    	popupBuilder.setView(myMsg);																			// la tablette ne sont plus connecter						//
    	Intent intentPrincipalAction = new Intent(VerticalSlidebarExampleActivity.this, pageAccueil.class);		// La tablette revient ensuite sur la page d'accueil pour	//
        startActivity(intentPrincipalAction);																	// une reconnexion avec le serveur							//
        finish();																								//////////////////////////////////////////////////////////////
    }
    public void initialiseTableauInput()
    {
    	
    	tabInput[0] = (EditText) findViewById(R.id.editText1);
    	tabInput[1] = (EditText) findViewById(R.id.editText2);
    	tabInput[2] = (EditText) findViewById(R.id.editText3);										//////////////////////////////////////////////////////////////////////////
    	tabInput[3] = (EditText) findViewById(R.id.editText4);										// Initialisation de editText qui sont par defaut non selectionnable	//	
    	tabInput[4] = (EditText) findViewById(R.id.editText5);										// Listener qui ne sera pas executer si il est toujour non selectionnabl//
    	tabInput[5] = (EditText) findViewById(R.id.editText6);										// change la valeur de la progress bar en fonction du nombre inséré		//
    	tabInput[6] = (EditText) findViewById(R.id.editText7);										//////////////////////////////////////////////////////////////////////////
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		int z =-1;
		if(arg2 == 1)
	     {
	    	 Log.i("test", "sa rentre");												
	    	 arg1.setBackgroundColor(Color.BLUE);
	     }
		do{
			z++;
		}while(tabSpinner[z] != arg0 && z < 12);
		Log.i("Numero", z+" et valeur "+arg2);
		tabSeek[z].setGroupe(arg2);
		projoProgress[z]=tabSeek[z].getProgress();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		Log.i("Errot", "");
	}
}
