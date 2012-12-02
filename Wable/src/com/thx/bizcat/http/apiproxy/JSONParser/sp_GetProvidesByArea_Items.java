package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetProvidesByArea_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetProvidesByArea_Items extends sp_Simple_Items  {
	public List<sp_GetProvidesByArea_Result> result = new ArrayList<sp_GetProvidesByArea_Result>();
	public sp_GetProvidesByArea_Items(JSONObject obj) {
		super(obj);
		try {
			
			if(bsuccess)
			{
				// 새로운 요청 목록 처리
				
				result = new ArrayList<sp_GetProvidesByArea_Result>();
				JSONArray array = obj.getJSONArray("data");
				for(int i=0;i<array.length();i++)
				{
					result.add(new sp_GetProvidesByArea_Result(array.getJSONObject(i)));
				}
			}
			else//�패�는 errorcode�력
			{
				resultCode =ResultCode.valueOf(obj.getString("data"));		
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}

}
