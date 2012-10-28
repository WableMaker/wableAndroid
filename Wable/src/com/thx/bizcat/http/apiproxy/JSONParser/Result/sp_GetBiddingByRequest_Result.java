package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetBiddingByRequest_Result
{
    public String bidding_id;
    public String message;
    public String picture_path;
    public String writer_name;
    public String writer_photo;
    public String writer_id;
    public String provider_id;
    public String provider_name;
    public String provider_photo;
	public sp_GetBiddingByRequest_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getString("bidding_id");

	if(!obj.isNull("message"))
		message= obj.getString("message");

	if(!obj.isNull("picture_path"))
		picture_path= obj.getString("picture_path");

	if(!obj.isNull("writer_name"))
		writer_name= obj.getString("writer_name");

	if(!obj.isNull("writer_photo"))
		writer_photo= obj.getString("writer_photo");

	if(!obj.isNull("writer_id"))
		writer_id= obj.getString("writer_id");

	if(!obj.isNull("provider_id"))
		provider_id= obj.getString("provider_id");

	if(!obj.isNull("provider_name"))
		provider_name= obj.getString("provider_name");

	if(!obj.isNull("provider_photo"))
		provider_photo= obj.getString("provider_photo");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

