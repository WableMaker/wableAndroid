package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMatchByRequest_Result
{
    public String matched_time;
    public String provide_id;
    public String title;
    public String provider_id;
    public String provider_name;
    public String provider_photo;
	public sp_GetMatchByRequest_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("matched_time"))
		matched_time= obj.getString("matched_time");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getString("provide_id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("provider_id"))
		provider_id= obj.getString("provider_id");

	if(!obj.isNull("provider_name"))
		provider_name= obj.getString("provider_name");

	if(!obj.isNull("provider_photo"))
		provider_photo= obj.getString("provider_photo");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

