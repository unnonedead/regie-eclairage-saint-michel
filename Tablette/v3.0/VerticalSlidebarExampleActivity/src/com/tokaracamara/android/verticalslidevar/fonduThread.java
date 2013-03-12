package com.tokaracamara.android.verticalslidevar;

import android.os.AsyncTask;
import android.util.Log;

public class fonduThread extends AsyncTask<Void, Integer, Void>
{
	
	private VerticalSlidebarExampleActivity v;
	private VerticalSeekBar[] tabSeek;
	private int[] progressBefore = new int[12];
	private boolean ascendant = true;
	
	public fonduThread(VerticalSlidebarExampleActivity vertSlidebar, boolean pAscendant)
	{
		v = vertSlidebar;
		this.ascendant = pAscendant;
		tabSeek = vertSlidebar.tabSeek;
		for(int j=0;j<12;j++)	
		{
			progressBefore[j] = tabSeek[j].getProgress();
		}
	}
	
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		// Mise à jour des seek bars
			for(int j=0;j<12;j++)	
			{
				Log.d("TestProgress", tabSeek.toString());
				progressBefore[j] = progressBefore[j]+values[0];
				tabSeek[j].setProgress(progressBefore[j]);
			}
			v.tableauProjecteur(); 
		}
			
		
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			for(int i =0;i<100;i++)
			{
				
				try {
					Thread.sleep(50);
					if (ascendant)
						publishProgress(1);
					else
						publishProgress(-1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

	}
