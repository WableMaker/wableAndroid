package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyBiddingAsRequester_Result
{
    public String bidding_id;
    public String request_id;
    public String provider_id;
    public String provide_id;
    public String provider_photo;
    public int status;
	public sp_GetMyBiddingAsRequester_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("request_id"))
		request_id= obj.getString("request_id");

	if(!obj.isNull("provider_id"))
		provider_id= obj.getString("provider_id");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getString("provide_id");

	if(!obj.isNull("provider_photo"))
		provider_photo= obj.getString("provider_photo");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

