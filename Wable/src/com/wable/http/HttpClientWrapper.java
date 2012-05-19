package com.wable.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.wable.util.Logger;

public class HttpClientWrapper implements IHttpConnectionLayer {

	
	// [start] �������
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	BasicCookieStore cookieStore = new  BasicCookieStore();
	HttpContext httpContext = new BasicHttpContext();	 
	 /// �α��� �ؼ� ���� ������ �ִ��� ����
	 static boolean m_session = false ;
	 
	 static long m_sessionLimitTime = 600000 ;  /// ���� �ð����� (�и�������)
	 static long m_sessionTime = 0 ;    /// ������ ���� �ð�
	
	// [end]
	
	public HttpClientWrapper()
	{
		httpContext.setAttribute(ClientContext.COOKIE_STORE,cookieStore);
	}
	
	
	
	// [start] IHttpConnectionLayer ����
	@Override
	public String RequestContents() {
		// TODO Auto-generated method stub
		return null;
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
	public boolean POST(String url, Map<String, Object> params,
			IHttpCallback callback) {
		try
		{
			
		//Ÿ�̾Ͽ� �ɱ�
		//	HttpParams params = http.getParams();
		//	HttpConnectionparams.setConnectionTimeout(params,5000);
		// HttpConnectionParams.setSoTimeout(params,5000);
			
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for(Map.Entry<String,Object> entry:params.entrySet())
				paramList.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(paramList));
			httpPost.setHeader("Location", "ko");
			HttpResponse responsePost = httpClient.execute(httpPost,httpContext);
			HttpEntity resEntity = responsePost.getEntity();
			
			List<Cookie> cookies = cookieStore.getCookies();
			for(int i=0;i<cookies.size();i++)
				Logger.Instance().Write(url+"  cookie: " + cookies.get(i));
			
			if(resEntity !=null)
			{
				String line =EntityUtils.toString(resEntity);
				Logger.Instance().Write(url+"  response: " + line);
				callback.OnCallback(true,line);
				return  true;
				
			}

		}
		catch(Exception e){
			Logger.Instance().Write(e);
			}
		
		
		callback.OnCallback(false,null);
		return false;
	}

	@Override
	public boolean GET(String url, IHttpCallback callback) {
		// TODO Auto-generated method stub
		try
		{
			
		//Ÿ�̾Ͽ� �ɱ�
		//	HttpParams params = http.getParams();
		//	HttpConnectionparams.setConnectionTimeout(params,5000);
		// HttpConnectionParams.setSoTimeout(params,5000);
			
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.setHeader("Location", "ko");
			HttpResponse responsePost = httpClient.execute(httpGet,httpContext);
			HttpEntity resEntity = responsePost.getEntity();
			
			List<Cookie> cookies = cookieStore.getCookies();
			for(int i=0;i<cookies.size();i++)
				Logger.Instance().Write(url+"  cookie: " + cookies.get(i));
			
			if(resEntity !=null)
			{
				String line =EntityUtils.toString(resEntity);
				Logger.Instance().Write(url+"  response: " + line);
				callback.OnCallback(true,line);
				return  true;
				
			}

		}
		catch(Exception e){
			Logger.Instance().Write(e);
			}
		
		
		callback.OnCallback(false,null);
		return false;
	}
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
	
	// [end]

}
