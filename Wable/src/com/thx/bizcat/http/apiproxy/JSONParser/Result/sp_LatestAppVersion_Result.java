package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.util.Logger;

public class sp_LatestAppVersion_Result {
	public String latestAppVersion;
	
	public sp_LatestAppVersion_Result(JSONObject obj)
	{
		try {

			if(!obj.isNull("data"))
				latestAppVersion= obj.getString("data");
		
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}
