package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyActiveRequests_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetMyActiveRequests_Items  {

	public sp_GetMyActiveRequests_Result result;
	
	public boolean bsuccess = false;
	public ResultCode resultCode = ResultCode.NONE;
	
	public sp_GetMyActiveRequests_Items(JSONObject obj) {
		
		try {

			bsuccess = obj.getBoolean("success");

			if(bsuccess)
				result = new sp_GetMyActiveRequests_Result(obj.getJSONObject("data"));

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
