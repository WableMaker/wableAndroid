package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetNotices_Result
{
    public int id;
    public String title="";
    public String description="";
	public sp_GetNotices_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getInt("id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("description"))
		description= obj.getString("description");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

