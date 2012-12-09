package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyRequestByID_Result
{
    public String title="";
    public String description="";
    public String due_date="";
    public int status;
    public double lat;
    public double lon;
    public int category_id;
    public int price;
	public sp_GetMyRequestByID_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("due_date"))
		due_date= obj.getString("due_date");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("category_id"))
		category_id= obj.getInt("category_id");

	if(!obj.isNull("price"))
		price= obj.getInt("price");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

