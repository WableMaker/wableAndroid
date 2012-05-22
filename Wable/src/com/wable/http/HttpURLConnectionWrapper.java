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

	// [start] IHttpConnectionLayer ����
	
	/// ������Ʈ ������ ��ä�� ������ ��Ʈ��
	 public String m_request ;
	 	 
	 /// ���� ������ �ʿ��� ��Ű
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
				httpcon.setRequestMethod(method);//POST�� GET�̳�
				httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//���ڵ����
				httpcon.setRequestProperty("Language", "ko");//���
		
				httpcon.setDoInput(true);//��ǲ��Ʈ�� ��뿩��
				if(m_session) httpcon.setRequestProperty("cookie", m_cookies);
				if(method.equals("POST"))
				{
					httpcon.setDoOutput(true);//post�� �����͸� �ּҿ� ������ ���� �� �ٵ� �־..
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
				
				// ������ S���� ����� WebView����� Http��ſ��� 15���ΰ� �Ѿ�� ���� �����
                /// ������ �� �� ���� ��쵵 �־��� �ٸ���� �� �ߵǴµ� ������ ������ S��!!! �׷��� ���� �ٶ���
				/// ������ ���鼭 ������Ʈ�� ���������� �����Ѵ�.
				
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
				/// ������Ʈ �޴ٰ� ������ ���� �������鼭 ���� �޼����� �д´�.
				   if (httpcon.getResponseCode() == 500) 
				   {
				    /// ���� �����ϰ� ������ ���� ��ǲ��Ʈ�� �����ؼ� ����޼��� ���
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
		
		
	 
	// [start] IHttpConnectionLayer ����


	@Override
	public boolean POSTAsync(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		
		try
		{
			/// �ϴ� �ּҿ� �����Ͷ� ������
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
			/// �ϴ� �ּҿ� �����Ͷ� ������
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
