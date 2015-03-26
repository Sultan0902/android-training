package com.example.iremember;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.iremember.contentprovider.Iremembercontent;

public class Story_view extends Activity {

	// Textview declaration for storyview
	TextView storytitle;
	TextView storybody;
	TextView imagename;
	TextView storytime;
	TextView storylongitude;
	TextView storylatitude;

	// Button declaration for storyview
	Button playaudio;
	Button editstory;
	Button deletestory;

	// Video and Photoview
	VideoView storyvideo;
	ImageView storyimage;

	// contain the position of the item in the list
	Story_item story;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_view);
		Intent storyitem = getIntent();
		// initializing the textviews

		storytitle = (TextView) findViewById(R.id.view_titlevalue);
		storybody = (TextView) findViewById(R.id.view_bodyvalue);
		imagename = (TextView) findViewById(R.id.view_imagenamevalue);
		storytime = (TextView) findViewById(R.id.view_timevalue);
		storylongitude = (TextView) findViewById(R.id.view_longitudevalue);
		storylatitude = (TextView) findViewById(R.id.view_latitudevalue);
		// initializing the button
		playaudio = (Button) findViewById(R.id.view_playaudio);
		editstory = (Button) findViewById(R.id.view_editbutton);
		deletestory = (Button) findViewById(R.id.view_deletebutton);
		// intializing the videoview and imageview
		storyvideo = (VideoView) findViewById(R.id.view_video);
		storyimage = (ImageView) findViewById(R.id.view_imageview);
		story = new Story_item(storyitem);

		// setting text of the textviews
		storytitle.setText(story.title);
		storybody.setText(story.body);
		if (story.imageName != "") {
			imagename.setText(story.imageName);
		} else {
			imagename.setText("No image found");
		}
		Date date = new Date(story.storyTime);
		String storydate = Story_item.FORMAT.format(date);
		storytime.setText("" + storydate);
		storylongitude.setText("" + story.longitude);
		storylatitude.setText("" + story.latitude);
		//setting audiolink and its button
		String audiolink = String.valueOf(story.audio_link).toString();
		final Ringtone tone;
		if (audiolink != "") {
			tone = RingtoneManager.getRingtone(getApplicationContext(),
					Uri.parse(audiolink));
		} else {
			tone = null;
		}
		playaudio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (tone != null) {

					try {

						if (!tone.isPlaying()) {
							tone.play();
						} else {
							tone.stop();
						}

					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"no audio found", Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					playaudio.setText("Play Audio");
					Toast.makeText(getApplicationContext(),
							"no audio found", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		//setting videoview
		final String videolink = String.valueOf(story.video_link).toString();
		MediaController videocontroller = new MediaController(getApplicationContext());
		videocontroller.setAnchorView(storyvideo);
		storyvideo.setMediaController(videocontroller);
		if(!videolink.isEmpty())
		{
			try
			{
				storyvideo.setVideoURI(Uri.parse(videolink));
				storyvideo.start();
			}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(),
						"no video found", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(),
					"no video found", Toast.LENGTH_LONG).show();
		}
		
		storyvideo.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				boolean playing = storyvideo.isPlaying();
				
				if(playing)
				{
					storyvideo.stopPlayback();
				}
				else
				{
					storyvideo.start();
				}
				return true;
			}
		});
		
		//setting imageview to view the image
		String imagelink = String.valueOf(story.image_link).toString();
		if(!imagelink.isEmpty())
		{
			storyimage.setImageURI(Uri.parse(imagelink));
		}
		else
		{
			storyimage.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
		}
		
		deletestory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				Uri uri = Uri.parse(Iremembercontent.CONTENT_URI + "/" + story.key_id);
				getContentResolver().delete(uri, null, null);
				Toast.makeText(getApplicationContext(), "Story Successfully Deleted", Toast.LENGTH_SHORT).show();
				finish();
			
			}
		});
		
		editstory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent editstoryIntent = new Intent(Story_view.this, Edit_story.class);
				Story_item.packageIntent(editstoryIntent, story);
				startActivity(editstoryIntent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.story_view, menu);
		return false;
	}

}
