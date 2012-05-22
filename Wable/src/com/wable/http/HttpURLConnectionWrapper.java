package com.wable.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.wable.util.Logger;

public class HttpURLConnectionWrapper extends HttpWrapper {

	// [start] IHttpConnectionLayer 구현
	
	/// 리퀘스트 내용을 통채로 저장할 스트링
	 public String m_request ;
	 	 
	 /// 세션 유지에 필요한 쿠키
	 String m_cookies = "" ;


	 // [end]
	 

	protected String Request(URL url,String method, Map<String,Object> params) throws IOException
		{
			InputStream in = null;
			OutputStream out = null;
			HttpURLConnection httpcon = null;
			try
			{
				
				httpcon = (HttpURLConnection) url.openConnection();
				httpcon.setUseCaches(false);
				httpcon.setRequestMethod(method);//POST냐 GET이냐
				httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//인코딩방식
				httpcon.setRequestProperty("Language", "ko");//언어
		
				httpcon.setDoInput(true);//인풋스트림 사용여부
				if(m_session) httpcon.setRequestProperty("cookie", m_cookies);
				if(method.equals("POST"))
				{
					httpcon.setDoOutput(true);//post는 데이터를 주소와 별개로 보냄 즉 바디에 넣어서..
					String paramstr =buildParameters(params);
					Logger.Instance().Write(url.toString()+" parameter " +paramstr);
					out = httpcon.getOutputStream();
					out.write(paramstr.getBytes("UTF-8"));
					out.flush();
					out.close();
				}
				Logger.Instance().Write(url.toString()+  " Request success "+  m_cookies);
				long start = System.currentTimeMillis();
				
				int response =httpcon.getResponseCode();
				
				Logger.Instance().Write(url.toString()+" recv elapsed time  "+(System.currentTimeMillis()-start) );
				
				// 갤럭시 S에서 어떤앱은 WebView라던가 Http통신에서 15초인가 넘어가면 세션 끊기는
                /// 원인을 알 수 없는 경우도 있었음 다른기기 다 잘되는데 오로지 갤럭시 S만!!! 그랬음 참고 바람요
				/// 루프를 돌면서 리퀘스트로 받은내용을 저장한다.
				
				if(response == HttpURLConnection.HTTP_OK)
				{
					BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
					String line="";
					while(true)
					{
						String r = br.readLine();
						if(null == r) break;
						line +=r;
					}
					br.close();
					Map<String,List<String>> imap = httpcon.getHeaderFields();
					if(imap.containsKey("Set-Cookie"))
					{
						List<String> cookie = imap.get("Set-Cookie");
						for(int i=0;i<cookie.size();i++) m_cookies += cookie.get(i);
						Logger.Instance().Write(m_cookies);			
					}
					
					return line;
				}
				
				
			}
			catch (IOException e) 
			{
				/// 리퀘스트 받다가 에러가 나면 에러나면서 받은 메세지를 읽는다.
				   if (httpcon.getResponseCode() == 500) 
				   {
				    /// 버퍼 리셋하고 에러값 받을 인풋스트림 생성해서 레어메세지 얻기
					BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
						
				    String line="";
				    while (true) 
				    {
				    	String r = br.readLine();
						if(null == r) break;
						line +=r;
				    }
				    br.close();

				    Logger.Instance().Write(line);
				   }
				   throw e;
			}
			finally
			{
				if(in !=null) in.close();
				if(httpcon !=null) httpcon.disconnect();
			}
			return null;
			
			
		}
		
		
	 
	// [start] IHttpConnectionLayer 구현


	@Override
	public boolean POSTAsync(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		
		try
		{
			/// 일단 주소에 데이터랑 보내고
			String recv = Request(new URL(url),"POST",params);
			Logger.Instance().Write(recv);
			callback.OnCallback(true, recv);
			
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
			callback.OnCallback(false, null);
		}
		
		return false;
	}

	@Override
	public boolean GETAsync(String url, Map<String, Object> params,
			IHttpCallback callback) {
		try
		{
			url +="?"+buildParameters(params);
			/// 일단 주소에 데이터랑 보내고
			String recv = Request(new URL(url),"GET",null);
			Logger.Instance().Write(recv);
			callback.OnCallback(true, recv);
			
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
			callback.OnCallback(false, null);
		}
		
		return false;
	}

	

	@Override
	public boolean POSTFileAsync(String url, Map<String, Object> params,
			Map<String, Object> files, IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean POSTSync(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean GETSync(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}
	// [end]
	

}
