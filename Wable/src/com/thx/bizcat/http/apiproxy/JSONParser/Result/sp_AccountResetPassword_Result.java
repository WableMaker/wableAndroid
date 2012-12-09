package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.util.Logger;

public class sp_AccountResetPassword_Result {
	public String newpassword;
	
	public sp_AccountResetPassword_Result(JSONObject obj)
	{
		try {

			if(!obj.isNull("newpassword"))
				newpassword= obj.getString("newpassword");
		
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}

}
