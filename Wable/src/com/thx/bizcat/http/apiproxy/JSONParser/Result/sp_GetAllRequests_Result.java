package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetAllRequests_Result
{
    public long id;
    public long user_id;
	public sp_GetAllRequests_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getLong("id");

	if(!obj.isNull("user_id"))
		user_id= obj.getLong("user_id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

