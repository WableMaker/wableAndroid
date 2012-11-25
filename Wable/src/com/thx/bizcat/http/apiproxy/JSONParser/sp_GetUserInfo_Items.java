package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetUserInfo_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetUserInfo_Items {
	
	public sp_GetUserInfo_Result result;
	
	public boolean bsuccess = false;
	public ResultCode resultCode = ResultCode.NONE;
	
	public sp_GetUserInfo_Items(JSONObject obj) {
		
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
				result = new sp_GetUserInfo_Result(obj.getJSONObject("data"));

			else
			{
				try	{ resultCode = ResultCode.valueOf(obj.getString("data")); } 
				catch (JSONException e) { Logger.Instance().Write(e); }
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
		
	}

}
