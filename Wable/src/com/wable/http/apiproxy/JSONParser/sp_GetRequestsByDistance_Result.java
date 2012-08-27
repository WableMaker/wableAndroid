package com.wable.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONException;

class sp_GetRequestsByDistance_Item
{
    public String id;
    public String title;
    public String photo;
    public Double distance;
    public String due_date;
    public String bidding_id;
    public Integer price;
    public String name;
    public String description;
    public String user_id;
	public sp_GetRequestsByDistance_Item(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getString("id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("photo"))
		photo= obj.getString("photo");

	if(!obj.isNull("distance"))
		distance= obj.getDouble("distance");

	if(!obj.isNull("due_date"))
		due_date= obj.getString("due_date");

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("price"))
		price= obj.getInt("price");

	if(!obj.isNull("name"))
		name= obj.getString("name");

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

public class sp_GetRequestsByDistance_Result
{
	public List<sp_GetRequestsByDistance_Item> requestsItem = new ArrayList<sp_GetRequestsByDistance_Item>();
	public boolean bsuccess = false;
	
	public sp_GetRequestsByDistance_Result(){}
}
