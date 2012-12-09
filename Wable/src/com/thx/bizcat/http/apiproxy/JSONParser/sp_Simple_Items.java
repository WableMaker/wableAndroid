package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.util.Logger;

public class sp_Simple_Items {
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_Simple_Items(JSONObject obj)
	{
		try {
			bsuccess = obj.getBoolean("success");
			
			if(!bsuccess)//실패시는 errorcode입력
			{
				resultCode = ResultCode.valueOf(obj.getString("data"));
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}
