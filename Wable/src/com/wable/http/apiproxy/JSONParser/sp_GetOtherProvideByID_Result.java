package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetOtherProvideByID_Result
{
    public String id;
    public String description;
    public int min_price;
    public double lat;
    public double lon;
    public String name;
    public String title;
    public String photo;
    public String user_id;
    public int radius;
	public sp_GetOtherProvideByID_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getString("id");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("min_price"))
		min_price= obj.getInt("min_price");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("name"))
		name= obj.getString("name");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("photo"))
		photo= obj.getString("photo");

	if(!obj.isNull("user_id"))
		user_id= obj.getString("user_id");

	if(!obj.isNull("radius"))
		radius= obj.getInt("radius");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

