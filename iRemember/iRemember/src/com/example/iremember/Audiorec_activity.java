package com.example.iremember;

import java.io.IOException;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Audiorec_activity extends Activity {
	
	private Button mrecord;
	private Button mplay;
	private MediaRecorder mrecorder;
	private MediaPlayer mplayer;
	private Boolean recorded = false;
	private Boolean mstartrecording;
	private Boolean mstartplaying;
	
	private String mFilename;
	public static String EXTRA_OUTPUT = "Output_Filename";
	 private static final String LOG_TAG = Audiorec_activity.class.getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audiorec);
		mrecord = (Button) findViewById(R.id.recordbutton);
		mplay = (Button) findViewById(R.id.playbutton);
		mstartrecording = true;
		mstartplaying = true;
		Intent caller = getIntent();
		mFilename = caller.getStringExtra(EXTRA_OUTPUT);
		Log.i("this is filename..zzzzzzzz",mFilename);
		
		mrecord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mstartrecording)
				{	
					//start recording
					mrecorder = new MediaRecorder();
					mrecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
					mrecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					mrecorder.setOutputFile(mFilename);
					mrecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					try
					{
						mrecorder.prepare();
					}
					catch(IOException i)
					{
						Log.e(LOG_TAG, i.getMessage());
					}
					mrecorder.start();
					//changing button text
					mrecord.setText("Stop Recording");
					
				}
				else
				{
					//stopping the recorder
					mrecorder.stop();
					mrecorder.release();
					mrecorder = null;
					recorded = true;
					//sending intent back to caller
					Intent data = new Intent();
					data.putExtra("data", mFilename);
					setResult(RESULT_OK, data);
					//changing text of button
					mrecord.setText("Start Recording");
				}
				mstartrecording = !mstartrecording;
				
			}
		});
		
		mplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mstartplaying)
				{
					//starting the mediaplayer
					mplayer = new MediaPlayer();
					try
					{
						mplayer.setDataSource(mFilename);
						mplayer.prepare();
						mplayer.start();
					}
					catch(IOException e)
					{
						Log.e(LOG_TAG, "Media Player failed to start");
					}
					
					//stopping the mediaplayer
					mplayer.stop();
					mplayer.release();
					mplayer = null;
					//changing the text on the button
					mplay.setText("Stop Playing");
				}
				
				else
				{
					//changing the text on the button
					mplay.setText("Start Playing");
				
				}
				mstartplaying = !mstartplaying;
			}
		});
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
	}
	
	@Override
	protected void onPause()
	{
		if(mrecorder != null)
		{
			mrecorder.release();
			mrecorder = null;
		}
		if(mplayer != null)
		{
			mplayer.release();
			mplayer = null;
		}
		super.onPause();
	}
	
	@Override
	public void onBackPressed()
	{
		if(!recorded)
		{
			setResult(RESULT_CANCELED, null);
			
		}
		super.onBackPressed();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.audiorec, menu);
		return true;
	}

}
