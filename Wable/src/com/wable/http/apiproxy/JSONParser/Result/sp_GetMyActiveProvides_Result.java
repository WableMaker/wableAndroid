package com.wable.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyActiveProvides_Result
{
    public String provide_id;
    public int min_price;
    public double lat;
    public double lon;
    public int status;
    public Integer bidding_count;
    public String title;
    public Integer matching_count;
    public String description;
	public sp_GetMyActiveProvides_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("provide_id"))
		provide_id= obj.getString("provide_id");

	if(!obj.isNull("min_price"))
		min_price= obj.getInt("min_price");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("bidding_count"))
		bidding_count= obj.getInt("bidding_count");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("matching_count"))
		matching_count= obj.getInt("matching_count");

	if(!obj.isNull("description"))
		description= obj.getString("description");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

