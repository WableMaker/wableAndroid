package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMySuggestRequests_Result;

public class sp_GetMySuggestRequests_Items {
	public List<sp_GetMySuggestRequests_Result> requestsItem = new ArrayList<sp_GetMySuggestRequests_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetMySuggestRequests_Items() {
	}

}
