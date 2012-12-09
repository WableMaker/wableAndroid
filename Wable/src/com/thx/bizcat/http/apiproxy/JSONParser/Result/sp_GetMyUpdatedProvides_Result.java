package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyUpdatedProvides_Result
{
    public long id;
    public long user_id;
    public Integer category_id;
    public String title="";
    public int min_price;
    public double lat;
    public double lon;
    public int radius;
    public int status;
    public String created_time="";
    public String description="";
    public String photo1="";
    public String photo2="";
    public String photo3="";
    public String photo4="";
    public String photo5="";
    public boolean deleted;
    public String modified_time="";
    public boolean totwitter;
    public boolean tofacebook;
	public sp_GetMyUpdatedProvides_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getLong("id");

	if(!obj.isNull("user_id"))
		user_id= obj.getLong("user_id");

	if(!obj.isNull("category_id"))
		category_id= obj.getInt("category_id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("min_price"))
		min_price= obj.getInt("min_price");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("radius"))
		radius= obj.getInt("radius");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("created_time"))
		created_time= obj.getString("created_time");

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

	if(!obj.isNull("deleted"))
		deleted= obj.getBoolean("deleted");

	if(!obj.isNull("modified_time"))
		modified_time= obj.getString("modified_time");

	if(!obj.isNull("totwitter"))
		totwitter= obj.getBoolean("totwitter");

	if(!obj.isNull("tofacebook"))
		tofacebook= obj.getBoolean("tofacebook");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

