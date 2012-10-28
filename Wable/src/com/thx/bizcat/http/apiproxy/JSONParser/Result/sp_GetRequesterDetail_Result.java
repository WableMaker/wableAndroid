package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetRequesterDetail_Result
{
    public String name;
    public String introduce;
    public Integer request_count;
    public boolean public_mobile;
    public boolean public_phone;
    public boolean public_email;
    public boolean public_fb;
    public String fb_user_id;
    public String fb_oauth_token;
    public String photo;
    public String email;
    public String mobile;
    public String phone;
    public boolean is_email_authorized;
    public boolean is_mobile_authorized;
	public sp_GetRequesterDetail_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("name"))
		name= obj.getString("name");

	if(!obj.isNull("introduce"))
		introduce= obj.getString("introduce");

	if(!obj.isNull("request_count"))
		request_count= obj.getInt("request_count");

	if(!obj.isNull("public_mobile"))
		public_mobile= obj.getBoolean("public_mobile");

	if(!obj.isNull("public_phone"))
		public_phone= obj.getBoolean("public_phone");

	if(!obj.isNull("public_email"))
		public_email= obj.getBoolean("public_email");

	if(!obj.isNull("public_fb"))
		public_fb= obj.getBoolean("public_fb");

	if(!obj.isNull("fb_user_id"))
		fb_user_id= obj.getString("fb_user_id");

	if(!obj.isNull("fb_oauth_token"))
		fb_oauth_token= obj.getString("fb_oauth_token");

	if(!obj.isNull("photo"))
		photo= obj.getString("photo");

	if(!obj.isNull("email"))
		email= obj.getString("email");

	if(!obj.isNull("mobile"))
		mobile= obj.getString("mobile");

	if(!obj.isNull("phone"))
		phone= obj.getString("phone");

	if(!obj.isNull("is_email_authorized"))
		is_email_authorized= obj.getBoolean("is_email_authorized");

	if(!obj.isNull("is_mobile_authorized"))
		is_mobile_authorized= obj.getBoolean("is_mobile_authorized");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

