package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.util.Logger;

public class sp_CompatiableAppVersion_Result {
	public String compatibleAppVersion;
	
	public sp_CompatiableAppVersion_Result(JSONObject obj)
	{
		try {

			if(!obj.isNull("data"))
				compatibleAppVersion= obj.getString("data");
		
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}

}