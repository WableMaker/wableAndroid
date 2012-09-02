package com.wable.http.apiproxy.JSONParser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wable.http.apiproxy.JSONParser.Result.UserGetUpdatedContents_Result;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetAllRequests_Result;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetMyActiveRequests_Result;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetRequestsByArea_Result;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetRequestsByDistance_Result;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetRequestsByTime_Result;

public class JSONParser {


	public static sp_GetAllRequests_Items RequestListAllParser(JSONObject json)
	{
		sp_GetAllRequests_Items results = new sp_GetAllRequests_Items();
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
					
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetAllRequests_Result( array.getJSONObject(i)));
				}
				return results;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static sp_GetRequestsByTime_Items RequestListByTimeParser(JSONObject json)
	{
		sp_GetRequestsByTime_Items results = new sp_GetRequestsByTime_Items();
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
					
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetRequestsByTime_Result( array.getJSONObject(i)));
				}
				return results;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	public static sp_GetRequestsByDistance_Items RequestListByDistanceParser(JSONObject json)
	{
		sp_GetRequestsByDistance_Items results = new sp_GetRequestsByDistance_Items();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetRequestsByDistance_Result( array.getJSONObject(i)));
				}
				return results;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static sp_GetRequestsByArea_Items RequestListByAreaParser(JSONObject json)
	{
		sp_GetRequestsByArea_Items results = new sp_GetRequestsByArea_Items();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
					results.requestsItem.add(new sp_GetRequestsByArea_Result( array.getJSONObject(i)));
				results.resultCode = ResultCode.SUCCESS;
				return results;
			}
			else
			{
				results.resultCode = ResultCode.valueOf(json.getString("data"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
	
	public static sp_GetMyActiveRequests_Items RequestMyActiveListParser(JSONObject json)
	{
		sp_GetMyActiveRequests_Items results = new sp_GetMyActiveRequests_Items();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetMyActiveRequests_Result( array.getJSONObject(i)));
				}
				results.resultCode = ResultCode.SUCCESS;
				return results;
			}
			else
			{
				//results.resultCode = json.getJSONObject("data")
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/*//
	public static UserGetUpdatedContents_Result  UserGetUpdatedContents(JSONObject json)
	{
		try {
			
			boolean bsuccess= json.getBoolean("success");
			if(bsuccess)
			{
				
				
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	*/
	
}
