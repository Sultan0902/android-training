package com.example.iremember.contentprovider;

import android.net.Uri;

public class Iremembercontent {

	public static final String AUTHORITY = "com.example.iremember.provider";
	public static final Uri BASE_URI = Uri
			.parse("content://" + AUTHORITY + "/");
	public static final String STORY_TABLE_NAME = "story";
	// the URI which represents the whole table
	public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI,
			STORY_TABLE_NAME);

	// Columns of the table
	public final static String KEY_ID = "KEY_ID";
	public final static String STORY_ID = "STORY_ID";
	public final static String STORY_TITLE = "STORY_TITLE";
	public final static String STORY_BODY = "STORY_BODY";
	public final static String AUDIO_LINK = "AUDIO_LINK";
	public final static String VIDEO_LINK = "VIDEO_LINK";
	public final static String IMAGE_LINK = "IMAGE_LINK";
	public final static String IMAGE_TITLE = "IMAGE_TITLE";
	public final static String STORY_TIME = "STORY_TIME";
	public final static String LOC_LONGITUDE = "LONGITUDE";
	public final static String LOC_LATITUDE = "LATITUDE";

	// Creating table statement
	public static final String CREATE_STORY_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ STORY_TABLE_NAME
			+ " ( "
			+ KEY_ID
			+ " integer primary key autoincrement, "
			+ STORY_ID
			+ " INTEGER , "
			+ STORY_TITLE
			+ " TEXT NOT NULL,"
			+ STORY_BODY
			+ " TEXT, "
			+ AUDIO_LINK
			+ " TEXT, "
			+ VIDEO_LINK
			+ " TEXT, "
			+ IMAGE_LINK
			+ " TEXT, "
			+ IMAGE_TITLE
			+ " TEXT, "
			+ STORY_TIME
			+ " INTEGER NOT NULL, "
			+ LOC_LONGITUDE
			+ " REAL NOT NULL, "
			+ LOC_LATITUDE + " REAL NOT NULL);";

}
