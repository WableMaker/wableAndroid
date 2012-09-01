package com.wable.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetRequestsByTime_Result
{
	
    public String id;
    public String title;
    public String photo;
    public String created_time;
    public String due_date;
    public String bidding_id;
    public int price;
    public String name;
    public String description;
    public String user_id;
	public sp_GetRequestsByTime_Result(JSONObject obj)
	{
		try {

			if(!obj.isNull("id"))
				id= obj.getString("id");
		
			if(!obj.isNull("title"))
				title= obj.getString("title");
		
			if(!obj.isNull("photo"))
				photo= obj.getString("photo");
		
			if(!obj.isNull("created_time"))
				created_time= obj.getString("created_time");
		
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
				e.printStackTrace();
			}
	}
	
}
