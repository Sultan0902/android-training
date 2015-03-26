package com.example.iremember;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.iremember.contentprovider.Iremembercontent;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;


public class Story_item {
	
	public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	int key_id;
	int story_id;
	String title;
	String body;
	String audio_link;
	String video_link;
	String imageName;
	String image_link;
	long storyTime;
	long longitude;
	long latitude;
	
	public final static String KEY_ID = "keyid";
	public final static String STORY_ID = "storyid";
	public final static String STORY_TITLE = "storytitle";
	public final static String STORY_BODY = "storybody";
	public final static String AUDIO_LINK = "audiolink";
	public final static String VIDEO_LINK = "videolink";
	public final static String IMAGE_LINK = "imagelink";
	public final static String IMAGE_TITLE = "imagename";
	public final static String STORY_TIME = "storytime";
	public final static String LOC_LONGITUDE = "longitude";
	public final static String LOC_LATITUDE = "latitude";
	
	
	public Story_item()
	{
		
	}
	
	public Story_item(int key_id, int s_id, String title, String body, String a_link, String v_link, String iname, String i_link, long s_time, long longitude, long latitude)
	{
		this.key_id = key_id;
		story_id = s_id;
		this.title = title;
		this.body = body;
		this.audio_link = a_link;
		this.video_link = v_link;
		this.imageName = iname;
		this.image_link  = i_link;
		this.storyTime = s_time;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Story_item(Intent data)
	{
		key_id = (Integer) data.getExtras().get(KEY_ID);
		story_id = (Integer) data.getExtras().get(STORY_ID);
		title = data.getStringExtra(STORY_TITLE);
		body = data.getStringExtra(STORY_BODY);
		audio_link = data.getStringExtra(AUDIO_LINK);
		video_link = data.getStringExtra(VIDEO_LINK);
		imageName = data.getStringExtra(IMAGE_TITLE);
		image_link = data.getStringExtra(IMAGE_LINK);
		storyTime = (Long) data.getExtras().get(STORY_TIME);
		longitude = (Long) data.getExtras().get(LOC_LONGITUDE);
		latitude = (Long) data.getExtras().get(LOC_LATITUDE);
	}
	
	public int getStoryid()
	{
		return this.story_id;
	}

	

	
	public static void packageIntent(Intent data, Story_item story)
	{
		data.putExtra(KEY_ID, story.key_id);
		data.putExtra(STORY_ID, story.story_id);
		data.putExtra(STORY_TITLE, story.title);
		data.putExtra(STORY_BODY, story.body);
		data.putExtra(AUDIO_LINK, story.audio_link);
		data.putExtra(VIDEO_LINK, story.video_link);
		data.putExtra(IMAGE_LINK, story.image_link);
		data.putExtra(IMAGE_TITLE, story.imageName);
		data.putExtra(STORY_TIME, story.storyTime);
		data.putExtra(LOC_LONGITUDE, story.longitude);
		data.putExtra(LOC_LATITUDE, story.latitude);
	}
	public static void packagevalues(ContentValues values, Story_item story)
	{
		values.put(Iremembercontent.STORY_ID, story.story_id);
		values.put(Iremembercontent.STORY_TITLE, story.title);
		values.put(Iremembercontent.STORY_BODY, story.body);
		values.put(Iremembercontent.AUDIO_LINK, story.audio_link);
		values.put(Iremembercontent.VIDEO_LINK, story.video_link);
		values.put(Iremembercontent.IMAGE_LINK, story.image_link);
		values.put(Iremembercontent.IMAGE_TITLE, story.imageName);
		values.put(Iremembercontent.STORY_TIME, story.storyTime);
		values.put(Iremembercontent.LOC_LONGITUDE, story.longitude);
		values.put(Iremembercontent.LOC_LATITUDE, story.latitude);
	}
	
	public static Story_item cursorToStory(Cursor cursor)
	{
		Story_item story = new Story_item();
		story.key_id = cursor.getInt(cursor.getColumnIndex(Iremembercontent.KEY_ID));
		story.story_id = cursor.getInt(cursor.getColumnIndex(Iremembercontent.STORY_ID));
		story.title = cursor.getString(cursor.getColumnIndex(Iremembercontent.STORY_TITLE));
		story.body = cursor.getString(cursor.getColumnIndex(Iremembercontent.STORY_BODY));
		story.audio_link = cursor.getString(cursor.getColumnIndex(Iremembercontent.AUDIO_LINK));
		story.video_link = cursor.getString(cursor.getColumnIndex(Iremembercontent.VIDEO_LINK));
		story.image_link = cursor.getString(cursor.getColumnIndex(Iremembercontent.IMAGE_LINK));
		story.imageName = cursor.getString(cursor.getColumnIndex(Iremembercontent.IMAGE_TITLE));
		story.storyTime = cursor.getLong(cursor.getColumnIndex(Iremembercontent.STORY_TIME));
		story.longitude = cursor.getLong(cursor.getColumnIndex(Iremembercontent.LOC_LONGITUDE));
		story.latitude = cursor.getLong(cursor.getColumnIndex(Iremembercontent.LOC_LATITUDE));
		return story;
	}
}
