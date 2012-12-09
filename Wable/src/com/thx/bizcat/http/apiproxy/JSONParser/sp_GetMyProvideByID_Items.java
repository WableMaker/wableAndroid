package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyProvideByID_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetMyProvideByID_Items extends sp_Simple_Items {
	public sp_GetMyProvideByID_Result result ;
	public sp_GetMyProvideByID_Items(JSONObject obj) {
		super(obj);
		try {

			
			if(bsuccess) {
				
				if(!obj.isNull("data"))
					result = new sp_GetMyProvideByID_Result(obj.getJSONObject("data"));
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
