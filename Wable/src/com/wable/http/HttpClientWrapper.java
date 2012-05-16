package com.wable.http;

import java.util.Map;

public class HttpClientWrapper implements IHttpConnectionLayer {

	// [start] IHttpConnectionLayer ±¸Çö
	@Override
	public String RequestContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsConnectedSession() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean POST(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean GET(String url, IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}
	// [end]

}
