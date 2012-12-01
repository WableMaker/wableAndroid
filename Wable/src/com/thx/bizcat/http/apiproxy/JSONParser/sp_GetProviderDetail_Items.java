package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetProviderDetail_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetUserInfo_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetProviderDetail_Items extends sp_Simple_Items {
	public sp_GetProviderDetail_Result result ;
	public sp_GetProviderDetail_Items(JSONObject obj) {
		super(obj);
		try {
			
			if(bsuccess)
			{
				result = new sp_GetProviderDetail_Result(obj.getJSONObject("data"));
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
