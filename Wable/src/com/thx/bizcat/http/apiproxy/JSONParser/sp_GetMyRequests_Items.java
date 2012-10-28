package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyRequests_Result;

public class sp_GetMyRequests_Items {
	public List<sp_GetMyRequests_Result> requestsItem = new ArrayList<sp_GetMyRequests_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetMyRequests_Items() {
	}

}
