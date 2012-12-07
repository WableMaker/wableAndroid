package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetBiddingByRequest_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMatchByRequest_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyRequestByID_Result;
import com.thx.bizcat.util.Logger;

public class sp_RequestMyDetailById_Items {

	public sp_GetMyRequestByID_Result request;
	public List<sp_GetMatchByRequest_Result> requests = new ArrayList<sp_GetMatchByRequest_Result>();
	public List<sp_GetBiddingByRequest_Result> biddings = new ArrayList<sp_GetBiddingByRequest_Result>();
	public int bidding_count;
	public int matching_count;
	public boolean bsuccess = false;
	public ResultCode resultCode;	
	
	
	public sp_RequestMyDetailById_Items(JSONObject obj)
	{
		try {

			bsuccess = obj.getBoolean("success");

			if(bsuccess && !obj.isNull("data"))
			{
				JSONObject data = obj.getJSONObject("data");

				if(!data.isNull("request"))
				{
					sp_GetMyRequestByID_Result arr = new sp_GetMyRequestByID_Result(data.getJSONObject("request"));					
				}		
				else
				{
					try	{ resultCode = ResultCode.valueOf(obj.getString("data")); } 
					catch (JSONException e) { Logger.Instance().Write(e); }
				}

				if(!data.isNull("requests"))
				{
					JSONArray arr = data.getJSONArray("requests");
					for(int i=0,m=arr.length(); i<m; i++) 
						requests.add(new sp_GetMatchByRequest_Result(arr.getJSONObject(i)));
				}		
				else
				{
					try	{ resultCode = ResultCode.valueOf(obj.getString("data")); } 
					catch (JSONException e) { Logger.Instance().Write(e); }
				}
				
				if(!data.isNull("biddings"))
				{
					JSONArray arr = data.getJSONArray("biddings");
					for(int i=0,m=arr.length(); i<m; i++) 
						biddings.add(new sp_GetBiddingByRequest_Result(arr.getJSONObject(i)));
				}		
				else
				{
					try	{ resultCode = ResultCode.valueOf(obj.getString("data")); } 
					catch (JSONException e) { Logger.Instance().Write(e); }
				}
				
				if(!data.isNull("bidding_count"))
				{
					int bidding_count = data.getInt("bidding_count");
				}		
				else
				{
					try	{ resultCode = ResultCode.valueOf(obj.getString("data")); } 
					catch (JSONException e) { Logger.Instance().Write(e); }
				}
				
				if(!data.isNull("matching_count"))
				{
					int bidding_count = data.getInt("matching_count");
				}		
				else
				{
					try	{ resultCode = ResultCode.valueOf(obj.getString("data")); } 
					catch (JSONException e) { Logger.Instance().Write(e); }
				}
			}
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
	
}
