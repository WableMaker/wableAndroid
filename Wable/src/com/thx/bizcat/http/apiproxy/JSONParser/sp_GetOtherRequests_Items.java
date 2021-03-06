package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetOtherRequests_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetOtherRequests_Items {
	public List<sp_GetOtherRequests_Result> requests = new ArrayList<sp_GetOtherRequests_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	
	public sp_GetOtherRequests_Items(JSONObject obj) {
		
		try {

			bsuccess = obj.getBoolean("success");

			if(bsuccess && !obj.isNull("data")) {
				
				JSONArray arr = obj.getJSONArray("");
				for(int i=0,m=arr.length(); i<m; i++) 
					requests.add(new sp_GetOtherRequests_Result(arr.getJSONObject(i)));
			}

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
