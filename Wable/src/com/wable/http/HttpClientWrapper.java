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

	
	// [start] 멤버변수
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	BasicCookieStore cookieStore = new  BasicCookieStore();
	HttpContext httpContext = new BasicHttpContext();	 
	 /// 로그인 해서 세션 가지고 있는지 여부
	 static boolean m_session = false ;
	 
	 static long m_sessionLimitTime = 600000 ;  /// 세션 시간제한 (밀리세컨드)
	 static long m_sessionTime = 0 ;    /// 세션을 얻은 시간
	
	// [end]
	
	public HttpClientWrapper()
	{
		httpContext.setAttribute(ClientContext.COOKIE_STORE,cookieStore);
	}
	
	
	
	// [start] IHttpConnectionLayer 구현
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
		try
		{
			
		//타이암웃 걸기
		//	HttpParams params = http.getParams();
		//	HttpConnectionparams.setConnectionTimeout(params,5000);
		// HttpConnectionParams.setSoTimeout(params,5000);
			
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for(Map.Entry<String,Object> entry:params.entrySet())
				paramList.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(paramList));
			HttpResponse responsePost = httpClient.execute(httpPost,httpContext);
			HttpEntity resEntity = responsePost.getEntity();
			
			List<Cookie> cookies = cookieStore.getCookies();
			for(int i=0;i<cookies.size();i++)
				Logger.Instance().Write(url+"  cookie: " + cookies.get(i));
			
			if(resEntity !=null)
			{
				Logger.Instance().Write(url+"  response: " + EntityUtils.toString(resEntity));
				
				BufferedReader br = new BufferedReader(new InputStreamReader(resEntity.getContent()));
				
				String line ="";
				while(true)
				{
					String r = br.readLine();
					if(null == r) break;
					line +=r;
					if(!br.ready()) break;
				}
				br.close();
				callback.OnCallback(true,line);
				return  true;
				
			}

		}
		catch(Exception e){Logger.Instance().Write(e);}
		
		
		callback.OnCallback(false,null);
		return false;
	}

	@Override
	public boolean GET(String url, IHttpCallback callback) {
		// TODO Auto-generated method stub
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
	}

	@Override
	public void SessionUpdate() {
		// TODO Auto-generated method stub
		m_sessionTime = System.currentTimeMillis( ) ;
	}
	
	// [end]

}
