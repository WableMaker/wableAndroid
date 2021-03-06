package com.thx.bizcat.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlManager {
	
	private static SqlHelper help;
	
	private static int VERSION = 1;
	private static String DB_NAME = "bizcat";
	
	public static SQLiteDatabase getReader(Context context) {
		if(help == null) help = new SqlHelper(context, DB_NAME, VERSION);
		return help.getReadableDatabase();	
	}
	
	public static SQLiteDatabase getWriter(Context context) {
		if(help == null) help = new SqlHelper(context, DB_NAME, VERSION);		
		return help.getWritableDatabase();	
	}
	
	public static Cursor getCursor(Context context, String sql) {		
		return getReader(context).rawQuery(sql, null);
	}
	public static void excuteSql(Context context, String sql) {
		getWriter(context).execSQL(sql);
		Release();
	}
	
	public static void Release() {
		help.close();
	}
	
	public static void Release(Cursor cursor) {
		cursor.close();
		Release();
	}
	
	public static void Reset(Context context) {
		if(help == null) help = new SqlHelper(context, DB_NAME, VERSION);		
	    help.Reset(help.getWritableDatabase());	
	}
	
	
	public static boolean InsertItem(Context context, String table, ContentValues values) {
		
		boolean rst = false;
		if(help == null) help = new SqlHelper(context, DB_NAME, VERSION);
		SQLiteDatabase db = help.getWritableDatabase();
		db.insert(table, null, values);
		db.close();
		help.close();
		return rst;
	}
	
	public static boolean ReplaceItem(Context context, String table, ContentValues values) {
		
		boolean rst = false;
		if(help == null) help = new SqlHelper(context, DB_NAME, VERSION);
		SQLiteDatabase db = help.getWritableDatabase();
		db.replace(table, null, values);
		db.close();
		help.close();
		
		return rst;
	}
	

}


// SQL Helper Inner Class
class SqlHelper extends SQLiteOpenHelper {

	public SqlHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		/* 테이블 생성자 */ 		
		//Sqlite DataType v3.0 INTEGER, TEXT, NONE, REAL, NUMERIC
		
		// Request Table
		db.execSQL("CREATE TABLE request "		   
				+"(_id INTEGER, user_id INTEGER, title TEXT, description TEXT, price INTEGER, category_id INTEGER, due_date TEXT," 
				+"lat INTEGER, lon INTEGER, totwitter INTEGER, tofacebook INTEGER, status INTEGER, receive_recommand INTEGER," 
				+"created_time TEXT, deleted INTEGER, modified_time TEXT, PRIMARY KEY (_id, user_id) );");
		
		// Provider Table
		db.execSQL("CREATE TABLE provide "
				+"( _id INTEGER, user_id INTEGER, title TEXT, min_price INTEGER, lat INTEGER, lon INTEGER, radious INTEGER" +
				",status INTEGER, created_time TEXT, descrition TEXT, photo1 TEXT, photo2 TEXT, photo3 TEXT, photo4 TEXT, photo5 TEXT," +
				"deleted INTEGER, modified_time TEXT, totwitter INTEGER, tofacebook INTEGER, PRIMARY KEY (_id, user_id));");
		
		// Match Table
		db.execSQL("CREATE TABLE match "
				+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, request_id INTEGER, provide_id INTEGER, matched_time TEXT, status INTEGER," +
				"recommend INTEGER, other_user_id INTEGER, other_title TEXT, other_description TEXT, other_price INTEGER," +
				"other_user_photo TEXT, other_user_name TEXT, deleted INTEGER, modified_time TEXT);");
		
		// Bidding Table 
		db.execSQL("CREATE TABLE bidding "
				+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, bidding_id INTEGER, requester_id INTEGER, provider_id INTEGER, request_id INTEGER, provide_id INTEGER," +
				"request_price INTEGER, provide_price INTEGER, created_time TEXT, settled_price INTEGER, status INTEGER," +
				"requesteraccept INTEGER, provideraccept INTEGER, requesterdelete INTEGER, providerdelete INTEGER," +
				"approved_time TEXT, completed_time TEXT, requesteraccept_time TEXT, provideraccept_time TEXT, modified_time TEXT," +
				"other_user_name TEXT, other_title TEXT, other_description TEXT, other_price INTEGER, other_user_photo TEXT, " +
				"provide_status INTEGER, provide_deleted INTEGER, request_status INTEGER, request_deleted INTEGER);");
		
		// chat Table
		db.execSQL("CREATE TABLE chat "
				+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, bidding_id INTEGER, writer_id INTEGER, msg TEXT, tick TEXT, read_time TEXT, state INTEGER," +
				"audio TEXT, video TEXT, picture TEXT);");
		
		// Chat Table
		//db.execSQL("CREATE TABLE chat "
		//		+"( idx INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, duration INTEGER);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		/* 버전 변경시 테이블 변경, 거의 안쓰임 */
		
		//db.execSQL("DROP TABLE IF EXISTS favorite");
		//db.execSQL("DROP TABLE IF EXISTS history");
		//db.execSQL("DROP TABLE IF EXISTS status");
		onCreate(db);
		
	}
	
	public void Reset(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS request");
		db.execSQL("DROP TABLE IF EXISTS provide");
		db.execSQL("DROP TABLE IF EXISTS match");
		db.execSQL("DROP TABLE IF EXISTS bidding");
		db.execSQL("DROP TABLE IF EXISTS chat");
		onCreate(db);
	}
	
	
	


}