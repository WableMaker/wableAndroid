package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.util.Logger;

public class sp_BiddingOfferAsRequester_Result {
	public String biddingid;
    public String description;
    
	public sp_BiddingOfferAsRequester_Result(JSONObject obj)
	{
		try {

			if(!obj.isNull("biddingid"))
				biddingid= obj.getString("biddingid");
		
			if(!obj.isNull("description"))
				description= obj.getString("description");
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}
}
