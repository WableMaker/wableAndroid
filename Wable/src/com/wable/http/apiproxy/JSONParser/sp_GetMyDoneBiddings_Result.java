package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyDoneBiddings_Result
{
    public String provider_id;
    public String requester_id;
    public String bidding_id;
    public String completed_time;
    public String provider_name;
    public String requester_name;
	public sp_GetMyDoneBiddings_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("provider_id"))
		provider_id= obj.getString("provider_id");

	if(!obj.isNull("requester_id"))
		requester_id= obj.getString("requester_id");

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("completed_time"))
		completed_time= obj.getString("completed_time");

	if(!obj.isNull("provider_name"))
		provider_name= obj.getString("provider_name");

	if(!obj.isNull("requester_name"))
		requester_name= obj.getString("requester_name");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

