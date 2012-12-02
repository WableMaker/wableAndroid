package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetOtherProvideByID_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetOtherProvideByID_Items extends sp_Simple_Items {
	public sp_GetOtherProvideByID_Result result ;
	public sp_GetOtherProvideByID_Items(JSONObject obj) {
		super(obj);
		try {
			
			if(bsuccess)
			{
				result = new sp_GetOtherProvideByID_Result(obj.getJSONObject("data"));
			}
			else//�패�는 errorcode�력
			{
				resultCode =ResultCode.valueOf(obj.getString("data"));		
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}

}
