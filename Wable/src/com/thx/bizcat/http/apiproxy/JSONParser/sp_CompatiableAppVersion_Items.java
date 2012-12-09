package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_CompatiableAppVersion_Result;
import com.thx.bizcat.util.Logger;

public class sp_CompatiableAppVersion_Items {

	public sp_CompatiableAppVersion_Result result;
	public boolean bsuccess = false;
	public ResultCode resultCode;
	
	public sp_CompatiableAppVersion_Items(JSONObject obj) {
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
			{
				result = new sp_CompatiableAppVersion_Result(obj.getJSONObject("data"));
			}
			else//
			{
				resultCode = ResultCode.valueOf(obj.getString("data"));
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}



