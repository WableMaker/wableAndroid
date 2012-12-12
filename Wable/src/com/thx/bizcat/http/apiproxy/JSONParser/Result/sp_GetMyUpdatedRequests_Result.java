package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyUpdatedRequests_Result
{
    public long id = 0;
    public long user_id = 0;
    public String title="";
    public String description="";
    public int price = 0;
    public Integer category_id;
    public String due_date="";
    public double lat= 0;
    public double lon = 0;
    public boolean totwitter = false;
    public boolean tofacebook = false;
    public int status = 0;
    public boolean receive_recommend = false;
    public String created_time="";
    public boolean deleted = false;
    public String modified_time="";
	public sp_GetMyUpdatedRequests_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getLong("id");

	if(!obj.isNull("user_id"))
		user_id= obj.getLong("user_id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("price"))
		price= obj.getInt("price");

	if(!obj.isNull("category_id"))
		category_id= obj.getInt("category_id");

	if(!obj.isNull("due_date"))
		due_date= obj.getString("due_date");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("totwitter"))
		totwitter= obj.getBoolean("totwitter");

	if(!obj.isNull("tofacebook"))
		tofacebook= obj.getBoolean("tofacebook");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("receive_recommend"))
		receive_recommend= obj.getBoolean("receive_recommend");

	if(!obj.isNull("created_time"))
		created_time= obj.getString("created_time");

	if(!obj.isNull("deleted"))
		deleted= obj.getBoolean("deleted");

	if(!obj.isNull("modified_time"))
		modified_time= obj.getString("modified_time");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

