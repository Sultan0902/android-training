package com.example.iremember;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.iremember.contentprovider.Iremembercontent;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class add_story extends Activity {

	private final static String LOG_TAG = add_story.class.getCanonicalName();
	// Constants use to refer type of media
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int MEDIA_TYPE_AUDIO = 2;
	private static final int MEDIA_TYPE_VIDEO = 3;
	static final int CAMERA_PIC_REQUEST = 1;
	static final int CAMERA_VIDEO_REQUEST = 2;
	static final int MIC_SOUND_REQUEST = 3;

	// Buttons in the activity
	Button addaudio;
	Button addvideo;
	Button addphoto;
	Button getlocation;
	Button reset;
	Button cancel;
	Button create;

	// Editview and textviews in activity
	EditText storytitle;
	EditText storybody;
	EditText photoname;
	TextView audiolink;
	TextView videolink;
	TextView photolink;
	static TextView storytime;
	TextView longitude;
	TextView latitude;

	// other variables
	String audiopath;
	Uri videoUri;
	Uri imageUri;
	Location mlocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_story);

		// Assigning views to its xml equivalents
		storytitle = (EditText) findViewById(R.id.storytitlevalue);
		storybody = (EditText) findViewById(R.id.addbodyvalue);
		photoname = (EditText) findViewById(R.id.addphotovalue);

		audiolink = (TextView) findViewById(R.id.storyaudiolocation);
		videolink = (TextView) findViewById(R.id.storyvideolocation);
		photolink = (TextView) findViewById(R.id.storyphotolocation);
		storytime = (TextView) findViewById(R.id.storytimevalue);
		longitude = (TextView) findViewById(R.id.longitudevalue);
		latitude = (TextView) findViewById(R.id.latitudevalue);

		addaudio = (Button) findViewById(R.id.audiobutton);
		addvideo = (Button) findViewById(R.id.videobutton);
		addphoto = (Button) findViewById(R.id.imagebutton);
		getlocation = (Button) findViewById(R.id.locationbutton);
		reset = (Button) findViewById(R.id.resetbutton);
		cancel = (Button) findViewById(R.id.cancelbutton);
		create = (Button) findViewById(R.id.createbutton);

		// assigning logics on clickevents of the buttons
		// adding audiofile
		addaudio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent soundIntent = new Intent(getApplicationContext(),
						Audiorec_activity.class);
				File fileSound = getOutputmediaFile(MEDIA_TYPE_AUDIO);
				soundIntent.putExtra(Audiorec_activity.EXTRA_OUTPUT,
						fileSound.getAbsolutePath());
				startActivityForResult(soundIntent, MIC_SOUND_REQUEST);
			}
		});
		//adding video button
		addvideo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				Uri videoFileUri = Uri.fromFile(getOutputmediaFile(MEDIA_TYPE_VIDEO));
				videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoFileUri);
				videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
				startActivityForResult(videoIntent, CAMERA_VIDEO_REQUEST);
				
				
			}
		});
		
		//adding imagebutton implementation
		addphoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri imageFileUri = Uri.fromFile(getOutputmediaFile(MEDIA_TYPE_IMAGE));
				imageUri = imageFileUri;
				imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
				startActivityForResult(imageIntent, CAMERA_PIC_REQUEST);
				
			}
		});
		//Story Date Picker implementation
		storytime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Datepickerfragment datefragment = new Datepickerfragment();
				datefragment.show(getFragmentManager(), "date picker");
				
				
			}
		});
		//getlocation button implementation
		getlocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final LocationManager mylocationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
				LocationListener locationListener = new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						//no need
						
					}
					
					@Override
					public void onProviderEnabled(String provider) {
						// no need
						
					}
					
					@Override
					public void onProviderDisabled(String provider) {
						// no need
					}
					
					@Override
					public void onLocationChanged(Location location) {
						
						//make toast to tell that status has been changed then stop updating
						Toast.makeText(getApplicationContext(), "Location has been changed", Toast.LENGTH_LONG).show();
						Log.i(LOG_TAG, "set location = " + location );
						longitude.setText("" + location.getLongitude());
						latitude.setText("" + location.getLatitude());
						mlocation = location;
						mylocationmanager.removeUpdates(this);
					}
				};
				
				if(mylocationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				{
					Log.i(LOG_TAG, "Is provider Enabled = true/gps");
					mylocationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
					Location location = mylocationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if(location != null)
					{
						Log.i(LOG_TAG, "set location = " + location );
						longitude.setText("" + location.getLongitude());
						latitude.setText("" + location.getLatitude());
						mlocation = location;
						
					}
					else
					{
						Toast.makeText(getApplicationContext(), "GPS has yet to calculate location", Toast.LENGTH_LONG).show();
					}
				}
				
				else
				{
					Toast.makeText(getApplicationContext(), "GPS Provider is disabled", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		//reset button implementation
		reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				storytitle.setText("");
				storybody.setText("");
				audiolink.setText("");
				videolink.setText("");
				photolink.setText("");
				storytime.setText("Click text to set time");
				longitude.setText("");
				latitude.setText("");
				mlocation = null;
				Toast.makeText(getApplicationContext(), "Data Cleared", Toast.LENGTH_LONG).show();
			}
		});
		
		//cancel button 
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String title = storytitle.getText().toString();
				String body = storybody.getText().toString();
				String audioLink = audiolink.getText().toString();
				String videoLink = videolink.getText().toString();
				String photoLink = photolink.getText().toString();
				String photoName = photoname.getText().toString();
				String storyTime = storytime.getText().toString();
				Long Longitude;
				Long Latitude;
				if(mlocation != null)
				{
					
				Longitude = (long) mlocation.getLongitude();
				Latitude = (long) mlocation.getLatitude();
				}
				else
				{
					Longitude = (long) 0;
					Latitude  = (long) 0;
				}
				Date date;
				try {
					 date = Story_item.FORMAT.parse(storyTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					date = new Date();	
				}
				Story_item newStory = new Story_item(1, 1 , title, body, audioLink, videoLink, photoName, photoLink, date.getTime(),Longitude, Latitude);
				//putting values in the content resolver
				if(title.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Enter story name", Toast.LENGTH_SHORT).show();
				}
				else
				{
					
				
				ContentValues values = new ContentValues();
				Story_item.packagevalues(values, newStory);
				getContentResolver().insert(Iremembercontent.CONTENT_URI, values);
				Toast.makeText(getBaseContext(), "Story Successfully Added", Toast.LENGTH_SHORT).show();
				//returning intent
				finish();
				}
			}
		});
	}

	private static File getOutputmediaFile(int mediatype) {
		Log.e(LOG_TAG, "outputmediafileformat is" + mediatype);
		File mediastorageDir = new File(
				Environment.getExternalStorageDirectory(), "iRemember");
		// check directory.. if it doesnt exist, create it
		if (!mediastorageDir.exists()) {
			if (!mediastorageDir.mkdir()) {
				Log.d("OutputMediaFile", "Unable to make directory");
				return null;
			}
		}
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
				.format(new Date());
		File mediaFile;
		if (mediatype == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediastorageDir.getAbsolutePath()
					+ File.separator + "IMG_" + timestamp + ".jpg");
		} else if (mediatype == MEDIA_TYPE_AUDIO) {
			mediaFile = new File(mediastorageDir.getAbsolutePath()
					+ File.separator + "AUD_" + timestamp + ".3gp");
		} else if (mediatype == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediastorageDir.getAbsolutePath()
					+ File.separator + "VID_" + timestamp + ".mp4");
		} else {
			Log.e(LOG_TAG, "Media type not supported " + mediatype);
			return null;
		}
		return mediaFile;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check the request code and result code related to particular media
		// type and then assign values
		//for audio file
		if (requestCode == MIC_SOUND_REQUEST) {
			if (resultCode == RESULT_OK) {
				audiopath = (String) data.getExtras().get("data");
				audiolink.setText("File://" + audiopath.toString());
			} else if (resultCode == RESULT_CANCELED) {

			} else {

			}
		}
		//for video file
		else if (requestCode == CAMERA_VIDEO_REQUEST) {
			if (resultCode == RESULT_OK)

			{
				
				videoUri = data.getData();
				videolink.setText(videoUri.toString());
			} else if (resultCode == RESULT_CANCELED) {

			} else {

			}
		}
		//for image file
		else if (requestCode == CAMERA_PIC_REQUEST) {
			if (resultCode == RESULT_OK)

			{
				photolink.setText(imageUri.toString());
				
			} else if (resultCode == RESULT_CANCELED) {

			} else {

			}
		}
	}
	
	public static void setdateatview(int year, int monthofyear, int dayofmonth)
	{
		monthofyear++;
		String Day = "" + dayofmonth;
		String Month = "" + monthofyear;
		if(monthofyear < 10)
			Month = "0" + monthofyear;
		if(dayofmonth < 10)
			Day = "0" + dayofmonth;
		storytime.setText(year + "-" + Month + "-" + Day);
	}

}
