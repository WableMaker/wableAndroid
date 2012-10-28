package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMySuggestRequests_Result
{
    public String bidding_id;
    public String provide_id;
    public boolean providerdelete;
    public int status;
    public String provide_title;
    public String provide_description;
    public int provide_min_price;
    public String provider_id;
    public String provider_name;
    public String provider_photo;
    public int provide_status;
    public String message;
    public String picture_path;
    public String audio_path;
    public String video_path;
	public sp_GetMySuggestRequests_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getString("provide_id");

	if(!obj.isNull("providerdelete"))
		providerdelete= obj.getBoolean("providerdelete");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("provide_title"))
		provide_title= obj.getString("provide_title");

	if(!obj.isNull("provide_description"))
		provide_description= obj.getString("provide_description");

	if(!obj.isNull("provide_min_price"))
		provide_min_price= obj.getInt("provide_min_price");

	if(!obj.isNull("provider_id"))
		provider_id= obj.getString("provider_id");

	if(!obj.isNull("provider_name"))
		provider_name= obj.getString("provider_name");

	if(!obj.isNull("provider_photo"))
		provider_photo= obj.getString("provider_photo");

	if(!obj.isNull("provide_status"))
		provide_status= obj.getInt("provide_status");

	if(!obj.isNull("message"))
		message= obj.getString("message");

	if(!obj.isNull("picture_path"))
		picture_path= obj.getString("picture_path");

	if(!obj.isNull("audio_path"))
		audio_path= obj.getString("audio_path");

	if(!obj.isNull("video_path"))
		video_path= obj.getString("video_path");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

