package com.wable.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

//IHttpConnectionLayer를 구현하는 HttpClientWrapper와 HttpURLConnectionWrapper부모 클래스
public class HttpWrapper implements IHttpConnectionLayer {
	
	// [start] 멤버 변수
	
	/// 로그인 해서 세션 가지고 있는지 여부
	
	boolean m_session = false ;
	 
	long m_sessionLimitTime = 600000 ;  /// 세션 시간제한 (밀리세컨드)
	long m_sessionTime = 0 ;    /// 세션을 얻은 시간
	
	// [end]
	
	// [start] 멤버 함수
	
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
	 
	 
	// [start] IHttpConnectionLayer 구현
	 
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
		   /// 제한시간을 넘겼음 세션을 제거함
		   m_session = false ;
		   return false ; 
		  }
	}
	
	// [end]
	
	
	
	


	
	

}
