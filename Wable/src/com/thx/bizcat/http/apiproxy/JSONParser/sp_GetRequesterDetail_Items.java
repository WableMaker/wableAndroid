package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetRequesterDetail_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetRequesterDetail_Items extends sp_Simple_Items {
	public sp_GetRequesterDetail_Result result ;
	public sp_GetRequesterDetail_Items(JSONObject obj) {
		super(obj);
		try {
			
			if(bsuccess)
			{
				result = new sp_GetRequesterDetail_Result(obj.getJSONObject("data"));
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
