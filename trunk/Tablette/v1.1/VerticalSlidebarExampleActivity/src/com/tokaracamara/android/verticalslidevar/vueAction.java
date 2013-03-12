package com.tokaracamara.android.verticalslidevar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
 
public class vueAction extends Activity{
	ImageButton retourPrincipal;
	ImageButton fonduNoire;
	    public void onCreate(Bundle savedInstanceState) //A la   creation de la vue
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main2); //Afficher la vue portant le nom "jeu"
	        
	        retourPrincipal= (ImageButton) findViewById(R.id.retourPrincipal);
	        fonduNoire = (ImageButton) findViewById(R.id.fonduNoir);
	        
	        retourPrincipal.setOnClickListener(new View.OnClickListener()      
	        {
	            public void onClick(View v)
	            {
	                Intent intentPrincipalAction = new Intent(vueAction.this, VerticalSlidebarExampleActivity.class);
	                startActivity(intentPrincipalAction);   
	            }
	        });
	        fonduNoire.setOnClickListener(new View.OnClickListener()      
	        {
	            public void onClick(View v)
	            {
	                Intent intentPrincipalAction = new Intent(vueAction.this, VerticalSlidebarExampleActivity.class);
	                startActivity(intentPrincipalAction);   
	            }
	        });
	        
	        
	    }
}
