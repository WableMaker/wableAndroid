package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetOtherRequestByID_Result
{
    public String id;
    public String title;
    public String description;
    public String due_date;
    public int price;
    public double lat;
    public double lon;
    public String name;
    public String photo;
    public String user_id;
	public sp_GetOtherRequestByID_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getString("id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("due_date"))
		due_date= obj.getString("due_date");

	if(!obj.isNull("price"))
		price= obj.getInt("price");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("name"))
		name= obj.getString("name");

	if(!obj.isNull("photo"))
		photo= obj.getString("photo");

	if(!obj.isNull("user_id"))
		user_id= obj.getString("user_id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

