package com.thx.bizcat.http.apiproxy.JSONParser.Result;

import org.json.JSONObject;
import org.json.JSONException;

public class sp_GetCategory_Result
{
    public int id;
    public String title="";
    public String description="";
    public int price;
    public String photo="";
    public int parent_id;
    public String due_time="";
    public int type;
    public int order;
	public sp_GetCategory_Result(JSONObject obj)
	{
		try {

	if(!obj.isNull("id"))
		id= obj.getInt("id");

	if(!obj.isNull("title"))
		title= obj.getString("title");

	if(!obj.isNull("description"))
		description= obj.getString("description");

	if(!obj.isNull("price"))
		price= obj.getInt("price");

	if(!obj.isNull("photo"))
		photo= obj.getString("photo");

	if(!obj.isNull("parent_id"))
		parent_id= obj.getInt("parent_id");

	if(!obj.isNull("due_time"))
		due_time= obj.getString("due_time");

	if(!obj.isNull("type"))
		type= obj.getInt("type");

	if(!obj.isNull("order"))
		order= obj.getInt("order");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

