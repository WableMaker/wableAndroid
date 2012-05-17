package com.wable.apiproxy;

import org.json.JSONObject;

public interface IAPIProxyCallback {
	public void OnCallback(boolean success,JSONObject json);	
}
