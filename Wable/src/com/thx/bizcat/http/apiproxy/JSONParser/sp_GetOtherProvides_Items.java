package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetOtherProvides_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetOtherProvides_Items extends sp_Simple_Items {
	public List<sp_GetOtherProvides_Result> result = new ArrayList<sp_GetOtherProvides_Result>();
	public sp_GetOtherProvides_Items(JSONObject obj) {
		super(obj);
		try {
			if(bsuccess) {
				if(!obj.isNull("data"))
				{
				JSONArray arr = obj.getJSONArray("data");
				for(int i=0,m=arr.length(); i<m; i++) 
					result.add(new sp_GetOtherProvides_Result(arr.getJSONObject(i)));
				}
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
