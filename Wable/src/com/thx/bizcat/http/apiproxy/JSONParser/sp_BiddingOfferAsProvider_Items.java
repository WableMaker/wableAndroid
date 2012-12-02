package com.thx.bizcat.http.apiproxy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_BiddingOfferAsProvider_Result;
import com.thx.bizcat.util.Logger;

public class sp_BiddingOfferAsProvider_Items extends sp_Simple_Items {
	public sp_BiddingOfferAsProvider_Result result;
	public sp_BiddingOfferAsProvider_Items(JSONObject obj) {
		super(obj);
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
			{
				result = new sp_BiddingOfferAsProvider_Result(obj.getJSONObject("data"));
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