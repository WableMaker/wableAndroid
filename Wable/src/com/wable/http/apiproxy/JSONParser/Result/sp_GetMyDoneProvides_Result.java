package com.wable.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyDoneProvides_Result
{
    public String provide_id;
    public int min_price;
    public double lat;
    public double lon;
    public String title;
	public sp_GetMyDoneProvides_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("provide_id"))
		provide_id= obj.getString("provide_id");

	if(!obj.isNull("min_price"))
		min_price= obj.getInt("min_price");

	if(!obj.isNull("lat"))
		lat= obj.getDouble("lat");

	if(!obj.isNull("lon"))
		lon= obj.getDouble("lon");

	if(!obj.isNull("title"))
		title= obj.getString("title");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

