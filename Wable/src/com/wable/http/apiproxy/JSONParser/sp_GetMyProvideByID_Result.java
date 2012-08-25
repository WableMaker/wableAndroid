package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyProvideByID_Result
{
    public String description;
    public String photo1;
    public String photo2;
    public String photo3;
    public String photo4;
    public String photo5;
    public int min_price;
    public double lat;
    public double lon;
    public int status;
    public Integer category_id;
    public String title;
    public int radius;
	public sp_GetMyProvideByID_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("photo1"))
		photo1= obj.getString("photo1");

	if(!obj.isNull("photo2"))
		photo2= obj.getString("photo2");

	if(!obj.isNull("photo3"))
		photo3= obj.getString("photo3");

	if(!obj.isNull("photo4"))
		photo4= obj.getString("photo4");

	if(!obj.isNull("photo5"))
		photo5= obj.getString("photo5");

	if(!obj.isNull("min_price"))
		min_price= obj.getInt("min_price");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("category_id"))
		category_id= obj.getInt("category_id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("radius"))
		radius= obj.getInt("radius");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

