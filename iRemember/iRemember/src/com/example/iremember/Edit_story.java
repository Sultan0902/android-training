package com.example.iremember;

import java.util.Date;

import com.example.iremember.contentprovider.Iremembercontent;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_story extends Activity {

	//declaring buttons
	Button savebutton;
	Button cancelButton;
	Button resetButton;
	//declaring editexts
	EditText title;
	EditText body;
	EditText imagename;
	EditText longitude;
	EditText latitude;
	//declaring textview
	
	TextView audiolink;
	TextView videolink;
	TextView imagelink;
	TextView storytime;
	//storyitem
	private Story_item story;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_story);
		//initalizing buttons
		savebutton = (Button) findViewById(R.id.editsavebutton);
		cancelButton = (Button) findViewById(R.id.editcancelbutton);
		resetButton = (Button) findViewById(R.id.editresetbutton);
		//intializing edittexts
		title = (EditText) findViewById(R.id.edit_storytitlevalue);
		body = (EditText) findViewById(R.id.edit_addbodyvalue);
		imagename = (EditText) findViewById(R.id.edit_addphotovalue);
		longitude = (EditText) findViewById(R.id.edit_longitudevalue);
		latitude = (EditText) findViewById(R.id.edit_latitudevalue);
		//initializing textviews
		audiolink = (TextView) findViewById(R.id.edit_storyaudiolocation);
		videolink = (TextView) findViewById(R.id.edit_storyvideolocation);
		imagelink = (TextView) findViewById(R.id.edit_storyphotolocation);
		storytime = (TextView) findViewById(R.id.edit_storytimevalue);
		//initialinzing fields
		initializefields();
		//button click events 
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initializefields();
			}
		});
		
		savebutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String storytitle = title.getText().toString();
				String storybody = body.getText().toString();
				String storyimagename = imagename.getText().toString();
				Long storylongitude = Long.parseLong(longitude.getText().toString());
				Long storylatitude = Long.parseLong(latitude.getText().toString());
				
				if(title.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Enter story Name", Toast.LENGTH_SHORT).show();
				}
				else
				{
				ContentValues values = new ContentValues();
				values.put(Iremembercontent.STORY_TITLE, storytitle);
				values.put(Iremembercontent.STORY_BODY, storybody);
				values.put(Iremembercontent.IMAGE_TITLE, storyimagename);
				values.put(Iremembercontent.LOC_LONGITUDE, storylongitude);
				values.put(Iremembercontent.LOC_LATITUDE, storylatitude);
				
				Uri uri = Uri.parse(Iremembercontent.CONTENT_URI + "/" + story.key_id);
				getContentResolver().update(uri, values, null, null);
				Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_LONG).show();
				Intent mainactivityIntent = new Intent(getApplicationContext(), Mainlistactivity.class);
				mainactivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mainactivityIntent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_story, menu);
		return true;
	}
	//intializing the textviews and edittext with story_item data
	public void initializefields()
	{
		Intent editIntent = getIntent();
		story = new Story_item(editIntent);
		//initializing text of all edittexts
		title.setText(story.title);
		body.setText(story.body);
		imagename.setText(story.imageName);
		longitude.setText("" + story.longitude);
		latitude.setText("" + story.latitude);
		//initialinzing text of all textviews
		audiolink.setText(story.audio_link);
		videolink.setText(story.video_link);
		imagelink.setText(story.image_link);
		Date date = new Date(story.storyTime);
		String dateString = Story_item.FORMAT.format(date);
		storytime.setText(dateString);
	}

}
