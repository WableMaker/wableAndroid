package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMatchByProvide_Result
{
    public String matched_time;
    public String request_id;
    public String title;
    public String requester_id;
    public String requester_name;
    public String requester_photo;
	public sp_GetMatchByProvide_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("matched_time"))
		matched_time= obj.getString("matched_time");

	if(!obj.isNull("request_id"))
		request_id= obj.getString("request_id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

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

