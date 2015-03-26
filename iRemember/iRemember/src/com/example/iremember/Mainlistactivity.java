package com.example.iremember;

import com.example.iremember.contentprovider.Iremembercontent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Mainlistactivity extends Activity {

	public static listviewadapter madapter;
	Button addbutton;
	EditText searchbox;
	ListView storylistview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		madapter = new listviewadapter(getApplicationContext());
		addbutton = (Button) findViewById(R.id.addstorybutton);
		searchbox = (EditText) findViewById(R.id.searchbox);
		storylistview = (ListView) findViewById(R.id.storylistView);
		storylistview.setAdapter(madapter);
		// click listener to add button
		addbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent addstory = new Intent(Mainlistactivity.this,
						add_story.class);
				startActivity(addstory);

			}
		});

		// clicklistener for listviewclick
		storylistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,
					long id) {
				Intent storyviewIntent = new Intent(Mainlistactivity.this,
						Story_view.class);
				Story_item story1 = (Story_item) madapter.getItem(position);
				Story_item.packageIntent(storyviewIntent, story1);
				startActivity(storyviewIntent);

			}
		});
		//initializiing searchbox and its listener
		searchbox = (EditText) findViewById(R.id.searchbox);
		searchbox.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				String searchText = searchbox.getText().toString();
				madapter.searchFilter(searchText);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
		
	

	@Override
	protected void onResume() {
		super.onResume();
		Cursor cursor = getContentResolver().query(
				Iremembercontent.CONTENT_URI, null, null, null, "KEY_ID");
		if (!cursor.moveToFirst()) {
			Toast.makeText(getApplicationContext(), "No content found yet",
					Toast.LENGTH_LONG).show();
		} else {
			do {
				Story_item story = Story_item.cursorToStory(cursor);
				madapter.additem(story);

			} while (cursor.moveToNext());
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		madapter.clearAll();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

}
