package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_BaseImgUrl_Result;
import com.thx.bizcat.util.Logger;

public final class sp_BaseImgUrl_Items {
	public sp_BaseImgUrl_Result result;
	public boolean bsuccess = false;
	public ResultCode resultCode;
	
	public sp_BaseImgUrl_Items(JSONObject obj) {
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
			{
				result = new sp_BaseImgUrl_Result(obj);
			}
			else
			{
				resultCode = ResultCode.valueOf(obj.getString("data"));
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}
