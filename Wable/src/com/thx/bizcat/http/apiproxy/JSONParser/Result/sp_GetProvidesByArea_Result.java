package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetProvidesByArea_Result
{
    public String id="";
    public String photo="";
    public double lat;
    public double lon;
    public String bidding_id="";
    public String title="";
    public String name="";
    public int min_price;
    public String description="";
    public String user_id="";
	public sp_GetProvidesByArea_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getString("id");

	if(!obj.isNull("photo"))
		photo= obj.getString("photo");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("name"))
		name= obj.getString("name");

	if(!obj.isNull("min_price"))
		min_price= obj.getInt("min_price");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("user_id"))
		user_id= obj.getString("user_id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

