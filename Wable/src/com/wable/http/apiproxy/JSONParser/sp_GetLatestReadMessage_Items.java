package com.wable.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.wable.http.apiproxy.JSONParser.Result.sp_GetLatestReadMessage_Result;

public class sp_GetLatestReadMessage_Items {
	public List<sp_GetLatestReadMessage_Result> requestsItem = new ArrayList<sp_GetLatestReadMessage_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetLatestReadMessage_Items() {
	}

}
