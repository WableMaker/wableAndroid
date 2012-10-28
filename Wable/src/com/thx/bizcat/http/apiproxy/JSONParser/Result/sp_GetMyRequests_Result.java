package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyRequests_Result
{
    public int status;
    public String title;
    public String description;
    public String due_date;
    public int post_price;
    public Integer bidding_count;
    public Integer match_count;
    public String request_id;
    public Double lat;
    public Double lon;
	public sp_GetMyRequests_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("due_date"))
		due_date= obj.getString("due_date");

	if(!obj.isNull("post_price"))
		post_price= obj.getInt("post_price");

	if(!obj.isNull("bidding_count"))
		bidding_count= obj.getInt("bidding_count");

	if(!obj.isNull("match_count"))
		match_count= obj.getInt("match_count");

	if(!obj.isNull("request_id"))
		request_id= obj.getString("request_id");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

