package com.thx.bizcat.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

//IHttpConnectionLayer�� �����ϴ� HttpClientWrapper�� HttpURLConnectionWrapper�θ� Ŭ����
public class HttpWrapper implements IHttpConnectionLayer {
	
	// [start] ��� ����
	
	/// �α��� �ؼ� ���� ������ �ִ��� ����
	
	boolean m_session = false ;
	 
	long m_sessionLimitTime = 600000 ;  /// ���� �ð����� (�и�������)
	long m_sessionTime = 0 ;    /// ������ ���� �ð�
	
	protected int timeout_ms_syncrequest = 5000;//ms
	
	// [end]
	
	// [start] ��� �Լ�
	
	protected String buildParameters(Map<String,Object> params) throws IOException
	{
		if(params == null) return "";
		StringBuilder sb= new StringBuilder();
		for(Iterator<String> i=params.keySet().iterator();i.hasNext();) {
			String key = (String)i.next();
			sb.append(key);
			sb.append('=');
			sb.append(URLEncoder.encode(String.valueOf(params.get(key)),"UTF-8"));
			if(i.hasNext()) sb.append('&');
		
		}
		
		return sb.toString();
	}

		
	// [end]
	 
	 
	// [start] IHttpConnectionLayer ����
	 
	@Override
	public void SessionEstablished() {
		// TODO Auto-generated method stub
		m_session = true;
		m_sessionTime = System.currentTimeMillis( ) ;
	}

	@Override
	public void SessionClosed() {
		// TODO Auto-generated method stub
		m_session = false;
		m_sessionTime=0;
	}

	@Override
	public void SessionUpdate() {
		// TODO Auto-generated method stub
		m_sessionTime = System.currentTimeMillis( ) ;
	}
	
	@Override
	public boolean POSTAsync(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean GETAsync(String url,Map<String, Object> params, IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean IsConnectedSession() {
		 if( !m_session )
		  {
		   return false ;
		  }
		  
		  if( System.currentTimeMillis( ) < m_sessionTime + m_sessionLimitTime )
		  {

		   return true ; 
		  }
		  else
		  {
		   /// ���ѽð��� �Ѱ��� ������ ������
		   m_session = false ;
		   return false ; 
		  }
	}


	@Override
	public boolean POSTFileAsync(String url, Map<String, Object> params,
			Map<String, Object> files, IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String POSTSync(String url, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String GETSync(String url, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String POSTFileSync(String url, Map<String, Object> params,
			Map<String, Object> files) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// [end]
	
	
	
	


	
	

}
