package com.wable.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.wable.http.apiproxy.JSONParser.Result.sp_GetUnnotifieds_Result;

/// 리턴값 특이함.. 주의할 것!
public class sp_GetUnnotifieds_Items {
	public List<sp_GetUnnotifieds_Result> requestsItem = new ArrayList<sp_GetUnnotifieds_Result>();
	public boolean bsuccess = false;
	public sp_GetUnnotifieds_Items() {
	}

}
