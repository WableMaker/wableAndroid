package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetLatestReadMessage_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMessage_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedBiddings_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedMatch_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedRequests_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetNewMessage_Result;
import com.thx.bizcat.util.Logger;

public class sp_GetMessage_Items {
	public List<sp_GetMessage_Result> newmessages = null;
	public sp_GetLatestReadMessage_Result readmessage = null;
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetMessage_Items(JSONObject obj) {
		try {
			bsuccess = obj.getBoolean("success");
			
			if(bsuccess)
			{
				JSONObject data = obj.getJSONObject("data");
				if(data != null)//
				{
					//새 매시지
					newmessages = new ArrayList<sp_GetMessage_Result>();
					JSONArray array = data.getJSONArray("newmessages");
					for(int i=0;i<array.length();i++)
					{
						newmessages.add(new sp_GetMessage_Result(array.getJSONObject(i)));
					}
					
					// 상대방이 읽은 마지막 메시지
					readmessage = new  sp_GetLatestReadMessage_Result(data.getJSONObject("readmessage"));
					
				}
				else Logger.Instance().Write("sp_GetMessage_Items 파싱중 data가 null임");
			}
			else//실패시는 errorcode입력
			{
				try
				{
					resultCode = ResultCode.valueOf(obj.getString("data"));
				} catch (JSONException e) {
					Logger.Instance().Write(e);
				}
			}
			
		} catch (JSONException e) {
			Logger.Instance().Write(e);
		}
	}

}
