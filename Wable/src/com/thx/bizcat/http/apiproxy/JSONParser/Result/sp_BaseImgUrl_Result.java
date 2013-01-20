package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.util.Logger;

public class sp_BaseImgUrl_Result {
	public String baseImgUrl="";
	
	public sp_BaseImgUrl_Result(JSONObject obj)
	{
		try {

			if(!obj.isNull("data"))
				baseImgUrl= obj.getString("data");
		
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}

}