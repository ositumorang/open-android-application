package com.global.android.ContactBlackList;



import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
/**这Class主要对SQL的操作,以及ContentProvider对操作*/
 
 public class DataProvider extends ContentProvider {
	private static final String DATABASE_NAME = "ContactBlackList.db";
	private static final int DATABASE_VERSION = 2;
	private static final String TABLE_NAME = "BlackListTable";
	private Cursor c;
	private String sql_1,field_1,field_2;
	private String field_3=null,field_4=null;
	private static class DatabaseHelper extends SQLiteOpenHelper {
	DatabaseHelper(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override public void onCreate(SQLiteDatabase db) {
	db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
			 +"_id INTEGER PRIMARY KEY autoincrement," 
			 + "PID integer," 
			 + "user_name" + " TEXT," 
			 + "phone_number" + " integer," 
			 + "number TEXT," 
			 + "mms TEXT"  + ");");
	
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	}
	private DatabaseHelper mOpenHelper;
	@Override public boolean onCreate() {
	mOpenHelper = new DatabaseHelper(getContext());
	return true;
	}
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	String[] selectionArgs, String sortOrder) {
	SQLiteDatabase db = mOpenHelper.getReadableDatabase();
	if(selectionArgs==null)
	   {c = db.query(TABLE_NAME, projection, null, null, null, null, null);}
	else{
		
	    c = db.query(TABLE_NAME, projection, selectionArgs[0] + "='" + selectionArgs[1] + "'", null, null, null, null);
	
	}//Cursor c = db.query(TABLE_NAME, projection, null, null, null, null, null);
	return c;
	}
	@Override public String getType(Uri uri)
	{ return null; }
	@Override public Uri insert(Uri uri, ContentValues initialValues)
	{ 
	 field_1 = initialValues.get("user_name").toString();
	 field_2 = initialValues.get("phone_number").toString();
	 field_3 = initialValues.get("number").toString();
	 field_4 = initialValues.get("mms").toString();
	SQLiteDatabase db = mOpenHelper.getWritableDatabase();
	    //insert date 
	sql_1 = "insert into "+ TABLE_NAME +
					" (user_name, phone_number,number,mms) values('" + field_1 + "', '"
					+ field_2+"', '" + field_3+"', '" + field_4 + "');";
	
	try { db.execSQL(sql_1); }
	catch (SQLException e) { Log.e("ERROR", e.toString()); }
	return uri; 
	}
	@Override public int delete(Uri uri, String where, String[] whereArgs)
	{ 
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		if(whereArgs!=null)
		{
		db.delete(TABLE_NAME, whereArgs[0] + "='" + whereArgs[1] + "'", null);
		}
		else
		{
			db.delete(TABLE_NAME, null, null);
		}
		return 0; 
		}
	@Override public int update(Uri uri, ContentValues values,
	String where, String[] whereArgs)
	{
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		ContentValues cv =new ContentValues();
		String field_5 = values.get("user_name").toString();
		String field_6 = values.get("phone_number").toString();
		String field_7 = values.get("number").toString();
		String field_8 = values.get("mms").toString();
		
		cv.put("user_name", field_5);
		cv.put("phone_number", field_6);
		cv.put("number", field_7);
		cv.put("mms", field_8);
		db.update(TABLE_NAME, cv, whereArgs[0] + "='" + whereArgs[1] + "'", null);
		
		return 0;
		}
	}