package com.thx.bizcat.http.apiproxy.JSONParser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetAllRequests_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyActiveProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyActiveRequests_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetOtherProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetOtherRequests_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetProvidesByArea_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetProvidesByDistance_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetProvidesByTime_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetRequestsByArea_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetRequestsByDistance_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetRequestsByTime_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetUserInfo_Result;

public class JSONParser {

	public static sp_GetOtherRequests_Items RequestOtherListParser(JSONObject json)
	{
		sp_GetOtherRequests_Items results = new sp_GetOtherRequests_Items();
		if(json !=null)
		{
			try 
			{
				results.bsuccess = json.getBoolean("success");
				
				if(results.bsuccess)
				{
					JSONArray array= json.getJSONArray("data");
						
					for(int i=0;i<array.length();i++)
					{
						results.requestsItem.add(new sp_GetOtherRequests_Result( array.getJSONObject(i)));
					}
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
		}
		return null;
	}
	
	public static sp_GetMyActiveRequests_Items RequestMyActiveListParser(JSONObject json)
	{
//		sp_GetMyActiveRequests_Items results = new sp_GetMyActiveRequests_Items();
//		
//		try 
//		{
//			results.bsuccess = json.getBoolean("success");
//			
//			if(results.bsuccess)
//			{
//				JSONArray array= json.getJSONArray("data");
//				
//				for(int i=0;i<array.length();i++)
//				{
//					results.requestsItem.add(new sp_GetMyActiveRequests_Result( array.getJSONObject(i)));
//				}
//				results.resultCode = ResultCode.SUCCESS;
//				return results;
//			}
//			else
//			{
//				results.resultCode = ResultCode.valueOf(json.getString("data"));
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
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
	
	public static sp_GetOtherProvides_Items ProvideOtherListParser(JSONObject json)
	{
		sp_GetOtherProvides_Items results = new sp_GetOtherProvides_Items();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetOtherProvides_Result( array.getJSONObject(i)));
				}
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
	
	public static sp_GetMyActiveProvides_Items ProvideMyActiveListParser(JSONObject json)
	{
		sp_GetMyActiveProvides_Items results = new sp_GetMyActiveProvides_Items();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetMyActiveProvides_Result( array.getJSONObject(i)));
				}
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
	
	
	public static sp_GetProvidesByTime_Items ProvideListbyTimeParser(JSONObject json)
	{
		sp_GetProvidesByTime_Items results = new sp_GetProvidesByTime_Items();
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
					
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetProvidesByTime_Result( array.getJSONObject(i)));
				}
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
	
	public static sp_GetProvidesByArea_Items ProvideListbyAreaParser(JSONObject json)
	{
		sp_GetProvidesByArea_Items results = new sp_GetProvidesByArea_Items();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
					results.requestsItem.add(new sp_GetProvidesByArea_Result( array.getJSONObject(i)));
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
	
	
	public static sp_GetProvidesByDistance_Items ProvideListbyDistanceParser(JSONObject json)
	{
		sp_GetProvidesByDistance_Items results = new sp_GetProvidesByDistance_Items();
		
		try 
		{
			results.bsuccess = json.getBoolean("success");
			
			if(results.bsuccess)
			{
				JSONArray array= json.getJSONArray("data");
				
				for(int i=0;i<array.length();i++)
				{
					results.requestsItem.add(new sp_GetProvidesByDistance_Result( array.getJSONObject(i)));
				}
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
					results.result.add(new sp_GetAllRequests_Result( array.getJSONObject(i)));
				}
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

	

	
	public static sp_GetUserInfo_Items MyInfo(JSONObject json)
	{
//		sp_GetUserInfo_Items item = new sp_GetUserInfo_Items();
//		try 
//		{
//			item.bsuccess = json.getBoolean("success");
//			
//			if(item.bsuccess)
//			{
//				JSONObject obj= json.getJSONObject("data");
//					
//				item.result = new sp_GetUserInfo_Result(obj);
//				item.resultCode = ResultCode.SUCCESS;
//				return item;
//			}
//			else
//			{
//				item.resultCode = ResultCode.valueOf(json.getString("data"));
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
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
