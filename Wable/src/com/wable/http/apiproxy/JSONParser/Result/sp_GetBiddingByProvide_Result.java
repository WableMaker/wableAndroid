package com.wable.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetBiddingByProvide_Result
{
    public String bidding_id;
    public String message;
    public String picture_path;
    public String writer_name;
    public String writer_photo;
    public String writer_id;
    public String requester_id;
    public String requester_photo;
    public String requester_name;
	public sp_GetBiddingByProvide_Result(JSONObject obj)
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

	if(!obj.isNull("requester_id"))
		requester_id= obj.getString("requester_id");

	if(!obj.isNull("requester_photo"))
		requester_photo= obj.getString("requester_photo");

	if(!obj.isNull("requester_name"))
		requester_name= obj.getString("requester_name");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

