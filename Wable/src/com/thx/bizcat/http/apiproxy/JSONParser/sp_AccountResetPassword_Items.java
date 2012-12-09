package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_AccountResetPassword_Result;
import com.thx.bizcat.util.Logger;

public class sp_AccountResetPassword_Items {
	public sp_AccountResetPassword_Result result;
	public boolean bsuccess = false;
	public ResultCode resultCode;
	
	public sp_AccountResetPassword_Items(JSONObject obj) {
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
			{
				result = new sp_AccountResetPassword_Result(obj.getJSONObject("data"));
			}
			else//실패시는 errorcode입력
			{
				resultCode = ResultCode.valueOf(obj.getString("data"));
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}