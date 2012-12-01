package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetLatestReadMessage_Result
{
    public String bidding_id;
    public String written_time;
    public String written_tick;
    public String local_written_time;
	public sp_GetLatestReadMessage_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("written_time"))
		written_time= obj.getString("written_time");
	if(!obj.isNull("written_tick"))
		written_tick= obj.getString("written_tick");
	if(!obj.isNull("local_written_time"))
		local_written_time= obj.getString("local_written_time");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

