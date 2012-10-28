package com.thx.bizcat.http.apiproxy.JSONParser;

import java.util.ArrayList;
import java.util.List;

import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetFBAppInfo_Result;

public class sp_GetFBAppInfo_Items {
	public List<sp_GetFBAppInfo_Result> requestsItem = new ArrayList<sp_GetFBAppInfo_Result>();
	public boolean bsuccess = false;
	public ResultCode resultCode;
	public sp_GetFBAppInfo_Items() {
	}

}
