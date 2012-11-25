package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetUserInfo_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_LogIn_Result;
import com.thx.bizcat.util.Logger;

public class sp_MyInfo_Items {
	
	public sp_GetUserInfo_Result result;
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_MyInfo_Items(JSONObject obj) {
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
			{
				result = new sp_GetUserInfo_Result(obj.getJSONObject("data"));
				
			}
			else//�패�는 errorcode�력
			{
				try
				{
					resultCode =ResultCode.valueOf(obj.getString("data"));
				} catch (JSONException e) {
					Logger.Instance().Write(e);
				}
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}

}
