package com.wable.util;

import android.util.Log;

public class Logger {

	static Logger instance;
	public static Logger Instance()
	{
		if(instance == null)
			instance = new Logger();

		return instance;

	}
	String tag = "wable";
	
	public void Write(Exception e)
	{
		Log.e(tag, e.toString());
	}
	
	public void Write(String message)
	{
		Log.i(tag, message);
	}
}
