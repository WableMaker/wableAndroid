package com.wable.http;

import java.util.Map;

public interface IHttpConnectionLayer {

	boolean IsConnectedSession();
	boolean POSTAsync(String url, Map<String,Object> params, IHttpCallback callback);
	boolean GETAsync(String url,Map<String, Object> params, IHttpCallback callback);
	
	boolean POSTFileAsync(String url, Map<String,Object> params, Map<String,Object> files, IHttpCallback callback);
	
	//리턴이 null이면 실패한것임 null이 아니고 빈문자열이나 어떤 내용이 있다면 성공임
	String POSTSync(String url, Map<String,Object> params);
	String GETSync(String url,Map<String, Object> params);
	
	void SessionEstablished();
	void SessionClosed();
	void SessionUpdate();

	
}
