package com.wable.http.apiproxy.JSONParser;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_RegisterUser_Result
{
    public Long userid;
    public String activation_code;
    public Integer cnt;
	public sp_RegisterUser_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("userid"))
		userid= obj.getLong("userid");

	if(!obj.isNull("activation_code"))
		activation_code= obj.getString("activation_code");

	if(!obj.isNull("cnt"))
		cnt= obj.getInt("cnt");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

