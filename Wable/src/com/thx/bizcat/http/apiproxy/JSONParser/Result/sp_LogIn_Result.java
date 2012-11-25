package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.util.Logger;

public class sp_LogIn_Result {
	public String categorytick;
    public String description;
	public sp_LogIn_Result(JSONObject obj)
	{
		try {

			if(!obj.isNull("categorytick"))
				categorytick= obj.getString("categorytick");
		
			if(!obj.isNull("description"))
				description= obj.getString("description");
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}
