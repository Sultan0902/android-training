package com.example.iremember.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class Iremembercontentprovider extends ContentProvider{

	public SQLiteDatabase database;
	public static final String DATABASE_NAME = "Stories";
	public static final int DATABASE_VERSION = 1;
	
	public static class Databasehelper extends SQLiteOpenHelper
	{

		public Databasehelper(Context context) {
			super(context,DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(Iremembercontent.CREATE_STORY_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + Iremembercontent.STORY_TABLE_NAME);
			onCreate(db);
			
		}
		
	}
	
	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		
		String id  = uri.getLastPathSegment();
		whereClause = Iremembercontent.KEY_ID + "=" + id;
		int deleteCount = database.delete(Iremembercontent.STORY_TABLE_NAME, whereClause, whereArgs);
		getContext().getContentResolver().notifyChange(Iremembercontent.CONTENT_URI, null);
		return deleteCount;
	}

	@Override
	public String getType(Uri arg0) {
		return "netpace.com.storyitem/com.example.iremember.story";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		long Row_id = database.insert(Iremembercontent.STORY_TABLE_NAME, "", values);
		if(Row_id > 0)
		{
			Uri uri1 = ContentUris.withAppendedId(Iremembercontent.CONTENT_URI, Row_id);
			getContext().getContentResolver().notifyChange(Iremembercontent.CONTENT_URI, null);
			return uri1;
		}
		throw new SQLException("Failed to add record into " + uri);
		
	}

	@Override
	public boolean onCreate() {
		
		Databasehelper dbhelper = new Databasehelper(getContext());
		database = dbhelper.getWritableDatabase();
		return (database != null);
	}

	@Override
	public Cursor query(Uri arg0, String[] projectionIn, String selection, String[] selectionArgs,
			String sortOrder) {
		SQLiteQueryBuilder querybuilder = new SQLiteQueryBuilder();
		querybuilder.setTables(Iremembercontent.STORY_TABLE_NAME);
		Cursor cursor = querybuilder.query(database, projectionIn, selection, selectionArgs, null, null, sortOrder);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionarg) {
		
		String id = uri.getLastPathSegment();
		selection = Iremembercontent.KEY_ID + "=" + id;
		int updateCount = database.update(Iremembercontent.STORY_TABLE_NAME, values, selection, selectionarg);
		getContext().getContentResolver().notifyChange(Iremembercontent.CONTENT_URI, null);
		return updateCount;
	}

}
