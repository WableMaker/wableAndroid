package com.wable.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.wable.http.apiproxy.JSONParser.Result.sp_GetMyActiveRequests_Result;

public class sp_GetMyActiveRequests_Items {

	public List<sp_GetMyActiveRequests_Result> requestsItem = new ArrayList<sp_GetMyActiveRequests_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetMyActiveRequests_Items() {
	}

}
