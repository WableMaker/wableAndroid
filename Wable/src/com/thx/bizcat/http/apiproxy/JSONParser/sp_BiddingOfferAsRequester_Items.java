package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_BiddingOfferAsRequester_Result;
import com.thx.bizcat.util.Logger;

public class sp_BiddingOfferAsRequester_Items extends sp_Simple_Items  {
	public sp_BiddingOfferAsRequester_Result result;
	
	public sp_BiddingOfferAsRequester_Items(JSONObject obj) {
		super(obj);
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
			{
				result = new sp_BiddingOfferAsRequester_Result(obj.getJSONObject("data"));
				
			}
			else//실패시는 errorcode입력
			{
				
				resultCode = ResultCode.valueOf(obj.getString("data"));
				
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}


