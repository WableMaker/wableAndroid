package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetFBAppInfo_Result
{
    public String app_id;
    public String app_secret_code;
	public sp_GetFBAppInfo_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("app_id"))
		app_id= obj.getString("app_id");

	if(!obj.isNull("app_secret_code"))
		app_secret_code= obj.getString("app_secret_code");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

