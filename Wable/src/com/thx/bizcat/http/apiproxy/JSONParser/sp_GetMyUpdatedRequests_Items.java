package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedRequests_Result;

public class sp_GetMyUpdatedRequests_Items {
	public List<sp_GetMyUpdatedRequests_Result> requestsItem = new ArrayList<sp_GetMyUpdatedRequests_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetMyUpdatedRequests_Items() {
	}

}
