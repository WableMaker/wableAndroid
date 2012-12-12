package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetMyUpdatedBiddings_Result
{
    public long requester_id =0;
    public long provider_id=0;
    public long bidding_id=0;
    public long request_id=0;
    public long provide_id=0;
    public int request_price=0;
    public int provide_price=0;
    public String created_time="";
    public Integer settled_price=0;
    public int status=0;
    public Boolean requesteraccept=false;
    public Boolean provideraccept=false;
    public boolean requesterdelete=false;
    public boolean providerdelete=false;
    public String approved_time="";
    public String completed_time="";
    public String requesteraccept_time="";
    public String provideraccept_time="";
    public String modified_time="";
    public String other_user_name="";
    public String other_title="";
    public String other_description="";
    public Integer other_price=0;
    public String other_user_photo="";
    public Integer provide_status=0;
    public Boolean provide_deleted=false;
    public Integer request_status=0;
    public Boolean request_deleted=false;
	public sp_GetMyUpdatedBiddings_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("requester_id"))
		requester_id= obj.getLong("requester_id");

	if(!obj.isNull("provider_id"))
		provider_id= obj.getLong("provider_id");

	if(!obj.isNull("bidding_id"))
		bidding_id= obj.getLong("bidding_id");

	if(!obj.isNull("request_id"))
		request_id= obj.getLong("request_id");

	if(!obj.isNull("provide_id"))
		provide_id= obj.getLong("provide_id");

	if(!obj.isNull("request_price"))
		request_price= obj.getInt("request_price");

	if(!obj.isNull("provide_price"))
		provide_price= obj.getInt("provide_price");

	if(!obj.isNull("created_time"))
		created_time= obj.getString("created_time");

	if(!obj.isNull("settled_price"))
		settled_price= obj.getInt("settled_price");

	if(!obj.isNull("status"))
		status= obj.getInt("status");

	if(!obj.isNull("requesteraccept"))
		requesteraccept= obj.getBoolean("requesteraccept");

	if(!obj.isNull("provideraccept"))
		provideraccept= obj.getBoolean("provideraccept");

	if(!obj.isNull("requesterdelete"))
		requesterdelete= obj.getBoolean("requesterdelete");

	if(!obj.isNull("providerdelete"))
		providerdelete= obj.getBoolean("providerdelete");

	if(!obj.isNull("approved_time"))
		approved_time= obj.getString("approved_time");

	if(!obj.isNull("completed_time"))
		completed_time= obj.getString("completed_time");

	if(!obj.isNull("requesteraccept_time"))
		requesteraccept_time= obj.getString("requesteraccept_time");

	if(!obj.isNull("provideraccept_time"))
		provideraccept_time= obj.getString("provideraccept_time");

	if(!obj.isNull("modified_time"))
		modified_time= obj.getString("modified_time");

	if(!obj.isNull("other_user_name"))
		other_user_name= obj.getString("other_user_name");

	if(!obj.isNull("other_title"))
		other_title= obj.getString("other_title");

	if(!obj.isNull("other_description"))
		other_description= obj.getString("other_description");

	if(!obj.isNull("other_price"))
		other_price= obj.getInt("other_price");

	if(!obj.isNull("other_user_photo"))
		other_user_photo= obj.getString("other_user_photo");

	if(!obj.isNull("provide_status"))
		provide_status= obj.getInt("provide_status");

	if(!obj.isNull("provide_deleted"))
		provide_deleted= obj.getBoolean("provide_deleted");

	if(!obj.isNull("request_status"))
		request_status= obj.getInt("request_status");

	if(!obj.isNull("request_deleted"))
		request_deleted= obj.getBoolean("request_deleted");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

