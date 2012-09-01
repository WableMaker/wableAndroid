package com.wable.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetLatestReadMessage_Result
{
    public long bidding_id;
    public String written_time;
	public sp_GetLatestReadMessage_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getLong("bidding_id");

	if(!obj.isNull("written_time"))
		written_time= obj.getString("written_time");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

