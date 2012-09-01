package com.wable.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetUnnotifieds_Result
{
    public String message;
    public String device_id;
    public int device_type;
    public String id;
    public long user_id;
    public boolean is_sent;
    public String sent_time;
    public int priority;
    public int type;
    public Long bidding_id;
    public Long provide_id;
    public Long request_id;
	public sp_GetUnnotifieds_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("message"))
		message= obj.getString("message");

	if(!obj.isNull("device_id"))
		device_id= obj.getString("device_id");

	if(!obj.isNull("device_type"))
		device_type= obj.getInt("device_type");

	if(!obj.isNull("id"))
		id= obj.getString("id");

	if(!obj.isNull("user_id"))
		user_id= obj.getLong("user_id");

	if(!obj.isNull("is_sent"))
		is_sent= obj.getBoolean("is_sent");

	if(!obj.isNull("sent_time"))
		sent_time= obj.getString("sent_time");

	if(!obj.isNull("priority"))
		priority= obj.getInt("priority");

	if(!obj.isNull("type"))
		type= obj.getInt("type");

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getLong("bidding_id");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getLong("provide_id");

	if(!obj.isNull("request_id"))
		request_id= obj.getLong("request_id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

