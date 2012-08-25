package com.wable.http.apiproxy.JSONParser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	public static sp_GetRequestsByTime_Result RequestListByTimeParser(JSONObject json)
	{
		sp_GetRequestsByTime_Result results = new sp_GetRequestsByTime_Result();
		//sp_GetRequestsByTime_Item[] results = null;
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				results.requestsItem = new sp_GetRequestsByTime_Item[array.length()];
				
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem[i] = new sp_GetRequestsByTime_Item( array.getJSONObject(i));
				}
				return results;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
//	public static sp_GetRequestsByTime_Item[]  RequestListByTimeParser(JSONObject json)
//	{
//		sp_GetRequestsByTime_Item[] results = null;
//		try 
//		{
//			boolean bsuccess= json.getBoolean("success");
//			
//			if(bsuccess)
//			{
//				JSONArray array= json.getJSONArray("data");
//				results = new sp_GetRequestsByTime_Item[array.length()];
//				
//				for(int i=0;i<array.length();i++)
//				{
//					results[i] = new sp_GetRequestsByTime_Item( array.getJSONObject(i));
//				}
//				return results;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		//return results;
//		return null;
//	}
	
//	public static List<sp_GetRequestsByTime_Result> RequestListByDistanceParser(JSONObject json)
//	{
//		List<sp_GetRequestsByTime_Result> results = new ArrayList<sp_GetRequestsByTime_Result>();
//		
//		//sp_GetRequestsByTime_Result[] results = null;
//		try 
//		{
//			boolean bsuccess= json.getBoolean("success");
//			
//			if(bsuccess)
//			{
//				JSONArray array= json.getJSONArray("data");
//				
//				for(int i=0;i<array.length();i++)
//				{
//					results.
//					results[i] = new sp_GetRequestsByTime_Result( array.getJSONObject(i));
//				}
//				//return results;
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return results;	
//	}
	
	
	
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
