package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyProvides_Result
{
    public int status;
    public String keyword="";
    public int min_price;
    public Integer bidding_count;
    public Integer match_count;
    public String provide_id="";
    public double lat;
    public double lon;
	public sp_GetMyProvides_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("keyword"))
		keyword= obj.getString("keyword");

	if(!obj.isNull("min_price"))
		min_price= obj.getInt("min_price");

	if(!obj.isNull("bidding_count"))
		bidding_count= obj.getInt("bidding_count");

	if(!obj.isNull("match_count"))
		match_count= obj.getInt("match_count");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getString("provide_id");

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

