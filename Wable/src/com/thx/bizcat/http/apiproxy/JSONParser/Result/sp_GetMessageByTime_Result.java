package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMessageByTime_Result
{
    public String message;
    public String picture_path;
    public String audio_path;
    public String video_path;
    public String written_time;
    public String writer_id;
    public String written_time_tick;
	public sp_GetMessageByTime_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("message"))
		message= obj.getString("message");

	if(!obj.isNull("picture_path"))
		picture_path= obj.getString("picture_path");

	if(!obj.isNull("audio_path"))
		audio_path= obj.getString("audio_path");

	if(!obj.isNull("video_path"))
		video_path= obj.getString("video_path");

	if(!obj.isNull("written_time"))
		written_time= obj.getString("written_time");

	if(!obj.isNull("writer_id"))
		writer_id= obj.getString("writer_id");

	if(!obj.isNull("written_time_tick"))
		written_time_tick= obj.getString("written_time_tick");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

