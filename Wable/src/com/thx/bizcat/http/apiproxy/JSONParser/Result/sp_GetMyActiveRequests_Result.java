package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyActiveRequests_Result
{
    public String request_id="";
    public String title="";
    public String description="";
    public String due_date="";
    public double lat;
    public double lon;
    public int status;
    public Integer bidding_count;
    public int price;
    public Integer matching_count;
	public sp_GetMyActiveRequests_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("request_id"))
		request_id= obj.getString("request_id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("due_date"))
		due_date= obj.getString("due_date");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("bidding_count"))
		bidding_count= obj.getInt("bidding_count");

	if(!obj.isNull("price"))
		price= obj.getInt("price");

	if(!obj.isNull("matching_count"))
		matching_count= obj.getInt("matching_count");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}


