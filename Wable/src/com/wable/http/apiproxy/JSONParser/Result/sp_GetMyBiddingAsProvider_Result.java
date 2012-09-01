package com.wable.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyBiddingAsProvider_Result
{
    public String bidding_id;
    public String provide_id;
    public String requester_id;
    public String request_id;
    public String requester_photo;
    public int status;
	public sp_GetMyBiddingAsProvider_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getString("provide_id");

	if(!obj.isNull("requester_id"))
		requester_id= obj.getString("requester_id");

	if(!obj.isNull("request_id"))
		request_id= obj.getString("request_id");

	if(!obj.isNull("requester_photo"))
		requester_photo= obj.getString("requester_photo");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

