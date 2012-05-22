package com.wable.http;

import java.util.Map;

public interface IHttpConnectionLayer {

	boolean IsConnectedSession();
	boolean POSTAsync(String url, Map<String,Object> params, IHttpCallback callback);
	boolean GETAsync(String url,Map<String, Object> params, IHttpCallback callback);
	
	boolean POSTFileAsync(String url, Map<String,Object> params, Map<String,Object> files, IHttpCallback callback);
	
	boolean POSTSync(String url, Map<String,Object> params, IHttpCallback callback);
	boolean GETSync(String url,Map<String, Object> params, IHttpCallback callback);
	
	void SessionEstablished();
	void SessionClosed();
	void SessionUpdate();

	
}
