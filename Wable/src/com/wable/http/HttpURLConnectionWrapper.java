package com.wable.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.util.Logger;

public class HttpURLConnectionWrapper extends HttpWrapper {

	// [start] 멤버변수
	
	/// 리퀘스트 내용을 통채로 저장할 스트링
	 public String m_request ;
	 	 
	 /// 세션 유지에 필요한 쿠키
	 String m_cookies = "" ;

	 String lineEnd = "\r\n";
	 String twoHyphens = "--";
	 String boundary = "dkjsei40f9844-------djs8dviw--4-s-df-";
	 static ReentrantLock _cookielock = new ReentrantLock();
	 // [end]
	 String buildeMultipartNormalParameter(Map<String,Object> params)
	 {
		 if(params == null) return "";
			StringBuilder sb= new StringBuilder();
			
			for(Map.Entry<String,Object> entry:params.entrySet())
			{

				sb.append(twoHyphens + boundary + lineEnd);
				sb.append("Content-Disposition: form-data; name=\""+entry.getKey()+"\""+lineEnd);
				sb.append(lineEnd);
				sb.append(entry.getValue());
				sb.append(lineEnd);
			}

			return sb.toString();
	 }

	protected String Request(URL url,String method, Map<String,Object> params,boolean timeout) throws IOException
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
				
				if(timeout)
				{
					httpcon.setConnectTimeout(timeout_ms_syncrequest);
					httpcon.setReadTimeout(timeout_ms_syncrequest);
				}
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
						StoreCookie(cookie);	
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
		
		
	protected String RequestWithFiles(URL url,Map<String,Object> params,  Map<String,Object> files) throws IOException
	{

		//새로운 접속을 연다.
		HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		String bd= "dkjsei40f9844-------djs8dviw--4-s-df-";
		//읽기와 쓰기 모두 가능하게 설정
		conn.setDoInput(true);
		conn.setDoOutput(true);

		//캐시를 사용하지 않게 설정
		conn.setUseCaches(false); 

		//POST타입으로 설정
		conn.setRequestMethod("POST"); 
		if(m_session) conn.setRequestProperty("cookie", m_cookies);
		//헤더 설정

		conn.setRequestProperty("Connection","Keep-Alive"); 
		conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + bd); 

		//Output스트림을 열어
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream()); 
		dos.write(buildeMultipartNormalParameter(params).getBytes());
		

		for(Map.Entry<String,Object> entry:files.entrySet())
		{
			File file = new File(entry.getValue().toString());
			FileInputStream fileInputStream = new FileInputStream(file);
			dos.writeBytes("--" + bd + "\r\n"); 
			dos.writeBytes("Content-Disposition: form-data; name=\"asd\";filename=\""+ file.getName() +"\"" + "\r\n"); 
			dos.writeBytes("\r\n"); 

			//버퍼사이즈를 설정하여 buffer할당
			int bytesAvailable = fileInputStream.available(); 
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize); 
			byte[] buffer = new byte[bufferSize];
			 
			//스트림에 작성
			int bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
			while (bytesRead > 0) 
			{ 
				// Upload file part(s) 
				dos.write(buffer, 0, bufferSize); 
				bytesAvailable = fileInputStream.available(); 
				bufferSize = Math.min(bytesAvailable, maxBufferSize); 
				bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
			} 
			dos.writeBytes("\r\n"); 
			dos.writeBytes("--" + bd + "--" + "\r\n"); 
			fileInputStream.close();

			//써진 버퍼를 stream에 출력.  
			dos.flush(); 
		}
		

		Logger.Instance().Write(url.toString()+  " Request success "+  m_cookies);
		long start = System.currentTimeMillis();
		
		int response =conn.getResponseCode();
		
		Logger.Instance().Write(url.toString()+" recv elapsed time  "+(System.currentTimeMillis()-start) );
		
		// 갤럭시 S에서 어떤앱은 WebView라던가 Http통신에서 15초인가 넘어가면 세션 끊기는
        /// 원인을 알 수 없는 경우도 있었음 다른기기 다 잘되는데 오로지 갤럭시 S만!!! 그랬음 참고 바람요
		/// 루프를 돌면서 리퀘스트로 받은내용을 저장한다.
		String line="";
		if(response == HttpURLConnection.HTTP_OK)
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			while(true)
			{
				String r = br.readLine();
				if(null == r) break;
				line +=r;
			}
			br.close();
			Map<String,List<String>> imap = conn.getHeaderFields();
			if(imap.containsKey("Set-Cookie"))
			{
				List<String> cookie = imap.get("Set-Cookie");
				StoreCookie(cookie);
			}
			
			
		}
		return line;
	}
	
	private void StoreCookie(List<String> cookie)
	{
		try {
			if(_cookielock.tryLock(2000,TimeUnit.MILLISECONDS))
			{
				try
				{	
					for(int i=0;i<cookie.size();i++) m_cookies += cookie.get(i)+";";
					Logger.Instance().Write(m_cookies);		
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
				}
				finally
				{
					_cookielock.unlock();
				}
			}
		} catch (Exception e) {
			Logger.Instance().Write(e);
		}
		
	}
	 
	// [start] IHttpConnectionLayer 구현


	@Override
	public boolean POSTAsync(final String url, final Map<String, Object> params,
			final IHttpCallback callback) {
		// TODO Auto-generated method stub
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				try
				{
					/// 일단 주소에 데이터랑 보내고
					//String recv = Request(new URL(url),"POST",params,null);
					String recv = Request(new URL(url),"POST",params,false);
					Logger.Instance().Write(recv);
					callback.OnCallback(true, recv);

				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					callback.OnCallback(false, null);
				}
 			}
		}.start();
		return true;
	}

	@Override
	public boolean GETAsync(String url, Map<String, Object> params,
			final IHttpCallback callback) {
		
		try {
			final String urls = url +"?"+buildParameters(params);
			new Thread()
			{
				@Override
	 			public void run()
	 			{
					
	 			
					try
					{
						
						/// 일단 주소에 데이터랑 보내고
						String recv = Request(new URL(urls),"GET",null,false);
						Logger.Instance().Write(recv);
						callback.OnCallback(true, recv);
						
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false, null);
					}
	 			}
			}.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return true;
	}

	

	@Override
	public boolean POSTFileAsync(String url, Map<String, Object> params,
			Map<String, Object> files, IHttpCallback callback) {
		try
		{
			/// 일단 주소에 데이터랑 보내고
			String recv = RequestWithFiles(new URL(url),params,files);
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
	public String POSTSync(String url, Map<String, Object> params){
		// TODO Auto-generated method stub
		try
		{
			String recv = Request(new URL(url),"POST",params,true);
			Logger.Instance().Write(recv);
			return recv;
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
		}
		return null;
	}


	@Override
	public String GETSync(String url, Map<String, Object> params) {
		try
		{
			url +="?"+buildParameters(params);
			String recv = Request(new URL(url),"GET",params,true);
			Logger.Instance().Write(recv);
			return recv;
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
		}
		return null;
	}
	// [end]
	

	
}
