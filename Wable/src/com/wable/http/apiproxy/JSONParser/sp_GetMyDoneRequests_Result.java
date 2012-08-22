package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyDoneRequests_Result
{
    public String request_id;
    public String title;
    public String description;
    public String due_date;
    public double lat;
    public double lon;
    public int price;
	public sp_GetMyDoneRequests_Result(JSONObject obj)
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

	if(!obj.isNull("price"))
		price= obj.getInt("price");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

