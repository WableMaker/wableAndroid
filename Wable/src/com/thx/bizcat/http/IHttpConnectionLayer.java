package com.thx.bizcat.http;

import java.util.Map;

public interface IHttpConnectionLayer {

	boolean IsConnectedSession();
	boolean POSTAsync(String url, Map<String,Object> params, IHttpCallback callback);
	boolean GETAsync(String url,Map<String, Object> params, IHttpCallback callback);
	
	boolean POSTFileAsync(String url, Map<String,Object> params, Map<String,Object> files, IHttpCallback callback);
	
	//������ null�̸� �����Ѱ��� null�� �ƴϰ� ���ڿ��̳� � ������ �ִٸ� ������
	String POSTSync(String url, Map<String,Object> params);
	String GETSync(String url,Map<String, Object> params);
	String POSTFileSync(String url, Map<String,Object> params, Map<String,Object> files);
	
	
	void SessionEstablished();
	void SessionClosed();
	void SessionUpdate();

	
}
