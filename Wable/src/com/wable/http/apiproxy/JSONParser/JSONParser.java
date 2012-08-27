package com.wable.http.apiproxy.JSONParser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	public static sp_GetRequestsByTime_Result RequestListByTimeParser(JSONObject json)
	{
		sp_GetRequestsByTime_Result results = new sp_GetRequestsByTime_Result();
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
					
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetRequestsByTime_Item( array.getJSONObject(i)));
				}
				return results;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static sp_GetRequestsByDistance_Result RequestListByDistanceParser(JSONObject json)
	{
		sp_GetRequestsByDistance_Result results = new sp_GetRequestsByDistance_Result();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetRequestsByDistance_Item( array.getJSONObject(i)));
				}
				return results;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
	
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
}
