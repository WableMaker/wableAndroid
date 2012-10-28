package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetRequestsByTime_Result;

public class sp_GetRequestsByTime_Items {

	public List<sp_GetRequestsByTime_Result> requestsItem = new ArrayList<sp_GetRequestsByTime_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetRequestsByTime_Items(){}
}