package com.wable.http;

import java.util.Map;

public interface IHttpConnectionLayer {
	String RequestContents();
	boolean IsConnectedSession();
	boolean POST(String url, Map<String,Object> params, IHttpCallback callback);
	boolean GET(String url, IHttpCallback callback);
	
	void SessionEstablished();
	void SessionClosed();
	void SessionUpdate();

	
}
