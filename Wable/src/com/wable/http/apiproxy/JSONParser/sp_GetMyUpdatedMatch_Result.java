package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyUpdatedMatch_Result
{
    public long request_id;
    public long provide_id;
    public String matched_time;
    public int status;
    public boolean recommend;
    public long other_user_id;
    public String other_title;
    public String other_description;
    public Integer other_price;
    public String other_user_photo;
    public String other_user_name;
    public Boolean deleted;
    public String modified_time;
	public sp_GetMyUpdatedMatch_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("request_id"))
		request_id= obj.getLong("request_id");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getLong("provide_id");

	if(!obj.isNull("matched_time"))
		matched_time= obj.getString("matched_time");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("recommend"))
		recommend= obj.getBoolean("recommend");

	if(!obj.isNull("other_user_id"))
		other_user_id= obj.getLong("other_user_id");

	if(!obj.isNull("other_title"))
		other_title= obj.getString("other_title");

	if(!obj.isNull("other_description"))
		other_description= obj.getString("other_description");

	if(!obj.isNull("other_price"))
		other_price= obj.getInt("other_price");

	if(!obj.isNull("other_user_photo"))
		other_user_photo= obj.getString("other_user_photo");

	if(!obj.isNull("other_user_name"))
		other_user_name= obj.getString("other_user_name");

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

