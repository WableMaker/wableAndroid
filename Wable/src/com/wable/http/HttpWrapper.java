package com.wable.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

//IHttpConnectionLayer�� �����ϴ� HttpClientWrapper�� HttpURLConnectionWrapper�θ� Ŭ����
public class HttpWrapper implements IHttpConnectionLayer {
	
	// [start] ��� ����
	
	/// �α��� �ؼ� ���� ������ �ִ��� ����
	
	boolean m_session = false ;
	 
	long m_sessionLimitTime = 600000 ;  /// ���� �ð����� (�и�������)
	long m_sessionTime = 0 ;    /// ������ ���� �ð�
	
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
	public boolean POST(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean GET(String url,Map<String, Object> params, IHttpCallback callback) {
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
	
	// [end]
	
	
	
	


	
	

}
