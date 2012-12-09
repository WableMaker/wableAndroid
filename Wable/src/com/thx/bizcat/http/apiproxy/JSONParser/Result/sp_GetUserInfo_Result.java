package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetUserInfo_Result
{
    public String name="";
    public String email="";
    public boolean blocked;
    public String phone="";
    public String mobile="";
    public double pos_lat;
    public double pos_lon;
    public String blocked_info="";
    public String twitter_uid="";
    public String twitter_token="";
    public String registered_date="";
    public String last_visited_date="";
    public boolean deleted;
    public String issmb="";
    public String photo="";
    public boolean public_mobile;
    public boolean public_phone;
    public boolean public_email;
    public boolean public_fb;
    public boolean public_twitter;
    public double review_rating;
    public int review_count;
    public String fb_user_id="";
    public String fb_oauth_token="";
    public String id;
	public sp_GetUserInfo_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("name"))
		name= obj.getString("name");

	if(!obj.isNull("email"))
		email= obj.getString("email");

	if(!obj.isNull("blocked"))
		blocked= obj.getBoolean("blocked");

	if(!obj.isNull("phone"))
		phone= obj.getString("phone");

	if(!obj.isNull("mobile"))
		mobile= obj.getString("mobile");

	if(!obj.isNull("pos_lat"))
		pos_lat= obj.getDouble("pos_lat");

	if(!obj.isNull("pos_lon"))
		pos_lon= obj.getDouble("pos_lon");

	if(!obj.isNull("blocked_info"))
		blocked_info= obj.getString("blocked_info");

	if(!obj.isNull("twitter_uid"))
		twitter_uid= obj.getString("twitter_uid");

	if(!obj.isNull("twitter_token"))
		twitter_token= obj.getString("twitter_token");

	if(!obj.isNull("registered_date"))
		registered_date= obj.getString("registered_date");

	if(!obj.isNull("last_visited_date"))
		last_visited_date= obj.getString("last_visited_date");

	if(!obj.isNull("deleted"))
		deleted= obj.getBoolean("deleted");

	if(!obj.isNull("issmb"))
		issmb= obj.getString("issmb");

	if(!obj.isNull("photo"))
		photo= obj.getString("photo");

	if(!obj.isNull("public_mobile"))
		public_mobile= obj.getBoolean("public_mobile");

	if(!obj.isNull("public_phone"))
		public_phone= obj.getBoolean("public_phone");

	if(!obj.isNull("public_email"))
		public_email= obj.getBoolean("public_email");

	if(!obj.isNull("public_fb"))
		public_fb= obj.getBoolean("public_fb");

	if(!obj.isNull("public_twitter"))
		public_twitter= obj.getBoolean("public_twitter");

	if(!obj.isNull("review_rating"))
		review_rating= obj.getDouble("review_rating");

	if(!obj.isNull("review_count"))
		review_count= obj.getInt("review_count");

	if(!obj.isNull("fb_user_id"))
		fb_user_id= obj.getString("fb_user_id");

	if(!obj.isNull("fb_oauth_token"))
		fb_oauth_token= obj.getString("fb_oauth_token");

	if(!obj.isNull("id"))
		id= obj.getString("id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

