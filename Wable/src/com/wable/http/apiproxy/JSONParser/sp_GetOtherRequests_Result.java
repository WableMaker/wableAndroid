package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetOtherRequests_Result
{
    public String id;
    public String title;
    public int price;
    public double lat;
    public double lon;
    public String name;
    public String due_date;
    public String description;
    public String bidding_id;
	public sp_GetOtherRequests_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getString("id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("price"))
		price= obj.getInt("price");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("name"))
		name= obj.getString("name");

	if(!obj.isNull("due_date"))
		due_date= obj.getString("due_date");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

