package com.tokaracamara.android.verticalslidevar;

import android.os.AsyncTask;
import android.util.Log;

public class fonduThread extends AsyncTask<Void, Integer, Void>
{
	
	private VerticalSlidebarExampleActivity v;
	private VerticalSeekBar[] tabSeek;
	private int[] progressBefore = new int[12];
	private boolean ascendant = true;
	private boolean stop=false;
	
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
	
	public void majSeekbar()
	{
		for(int i = 0; i < 12 ; i++ )
		{
			progressBefore[i] = tabSeek[i].getProgress();
		}
	}
	public void setAscendant(boolean a)
	{
		this.ascendant = a;
	}
	
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		// Mise à jour des seek bars
			for(int j=0;j<12;j++)	
			{
				progressBefore[j] = progressBefore[j]+values[0];
				tabSeek[j].setProgress(progressBefore[j]);
			}
			v.tableauProjecteur(); 
		}
			
		
		public boolean resultOk()
		{
			majSeekbar();
			int result = 0;
			for(int i = 0; i<12;i++)
			{
				if(progressBefore[i] < 100 && ascendant == true)
				{
					Log.i("test haut :", progressBefore[i]+"");
					return false;
				}
				if(progressBefore[i] > 0 && ascendant == false)
				{
					Log.i("test bas :", progressBefore[i]+"");
					return false;
				}	
			}
			return false;
		}
	
		
		public void setStop(boolean pStop)
		{
			this.stop = pStop;			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			do
			{
				Log.i("test boucle :", "lol");
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
			}while(resultOk() == false && stop == false);
			return null;
		}

	}
