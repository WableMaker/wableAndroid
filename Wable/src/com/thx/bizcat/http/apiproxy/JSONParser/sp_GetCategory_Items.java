package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetCategory_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetCategory_Items extends sp_Simple_Items {
	public List<sp_GetCategory_Result> result = new ArrayList<sp_GetCategory_Result>();
	
	public sp_GetCategory_Items(JSONObject obj) {
		super(obj);
		try {
			
			if(bsuccess)
			{
				
				//새 매시지
				if(!obj.isNull("data"))
				{
					JSONArray array = obj.getJSONArray("data");
					if(array !=null)
					{
						for(int i=0;i<array.length();i++)
						{
							result.add(new sp_GetCategory_Result(array.getJSONObject(i)));
						}
					}
					
				}
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
