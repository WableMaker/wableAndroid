package com.wable.http.apiproxy;

import org.json.JSONObject;

////////////////////////////////
//-- APIProxyCallback 인터페이스--//
//-----최종 수정일 2012.04.08------//
//------수정자 : 백두산------------//
//------버전 : 0.01-------------//
////////////////////////////////
public interface IAPIProxyCallback {
	public void OnCallback(boolean success,JSONObject json);	
}
