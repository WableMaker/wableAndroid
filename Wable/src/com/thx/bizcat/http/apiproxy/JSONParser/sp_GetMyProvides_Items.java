package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyActiveProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMySuggestProvides_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetMyProvides_Items {
	
	public List<sp_GetMyProvides_Result> provides = new ArrayList<sp_GetMyProvides_Result>();
	public List<sp_GetMySuggestProvides_Result> suggests = new ArrayList<sp_GetMySuggestProvides_Result>();
	
	public sp_GetMyProvides_Result result;
	public boolean bsuccess = false;
	public ResultCode resultCode;
	
	public sp_GetMyProvides_Items(JSONObject obj) {
		
		try {

			bsuccess = obj.getBoolean("success");

			if(bsuccess) {
				
				JSONArray arr = obj.getJSONObject("data").getJSONArray("provides");
				for(int i=0,m=arr.length(); i<m; i++) 
					provides.add(new sp_GetMyProvides_Result(arr.getJSONObject(i)));
				
				arr = obj.getJSONObject("data").getJSONArray("suggests");
				for(int i=0,m=arr.length(); i<m; i++) 
					suggests.add(new sp_GetMySuggestProvides_Result(arr.getJSONObject(i)));
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
