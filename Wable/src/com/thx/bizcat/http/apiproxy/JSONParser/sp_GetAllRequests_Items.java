package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetAllRequests_Result;

public class sp_GetAllRequests_Items {

	public List<sp_GetAllRequests_Result> requestsItem = new ArrayList<sp_GetAllRequests_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetAllRequests_Items() {
	}

}
