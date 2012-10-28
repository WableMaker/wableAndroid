package com.thx.bizcat.util;

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
	

}


// SQL Helper Inner Class
class SqlHelper extends SQLiteOpenHelper {

	public SqlHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		/* 테이블 생성자 */ 

		// Profile 테이블 
		db.execSQL("CREATE TABLE profile "
				+"( idx INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, nation TEXT, code INTEGER, num TEXT);");
		
		// Request Table
		db.execSQL("CREATE TABLE request "		   
				+"( idx INTEGER PRIMARY KEY AUTOINCREMENT, call DATETIME, type INTEGER, code INTEGER, num TEXT, name TEXT);");
		
		// Provider Table
		db.execSQL("CREATE TABLE provider "
				+"( idx INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, duration INTEGER);");
		
		// Category Table
		db.execSQL("CREATE TABLE category "
				+"( idx INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, duration INTEGER);");
		
		// Chat Table
		db.execSQL("CREATE TABLE chat "
				+"( idx INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, duration INTEGER);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		/* 버전 변경시 테이블 변경, 거의 안쓰임 */
		
		//db.execSQL("DROP TABLE IF EXISTS favorite");
		//db.execSQL("DROP TABLE IF EXISTS history");
		//db.execSQL("DROP TABLE IF EXISTS status");
		onCreate(db);
		
	}
	
	
	


}