package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMySuggestProvides_Result
{
    public String bidding_id;
    public String request_id;
    public boolean requesterdelete;
    public int status;
    public int request_status;
    public String message;
    public String picture_path;
    public String audio_path;
    public String video_path;
    public String request_title;
    public String request_description;
    public int request_price;
    public String requester_id;
    public String requester_name;
    public String requester_photo;
	public sp_GetMySuggestProvides_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("request_id"))
		request_id= obj.getString("request_id");

	if(!obj.isNull("requesterdelete"))
		requesterdelete= obj.getBoolean("requesterdelete");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("request_status"))
		request_status= obj.getInt("request_status");

	if(!obj.isNull("message"))
		message= obj.getString("message");

	if(!obj.isNull("picture_path"))
		picture_path= obj.getString("picture_path");

	if(!obj.isNull("audio_path"))
		audio_path= obj.getString("audio_path");

	if(!obj.isNull("video_path"))
		video_path= obj.getString("video_path");

	if(!obj.isNull("request_title"))
		request_title= obj.getString("request_title");

	if(!obj.isNull("request_description"))
		request_description= obj.getString("request_description");

	if(!obj.isNull("request_price"))
		request_price= obj.getInt("request_price");

	if(!obj.isNull("requester_id"))
		requester_id= obj.getString("requester_id");

	if(!obj.isNull("requester_name"))
		requester_name= obj.getString("requester_name");

	if(!obj.isNull("requester_photo"))
		requester_photo= obj.getString("requester_photo");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

