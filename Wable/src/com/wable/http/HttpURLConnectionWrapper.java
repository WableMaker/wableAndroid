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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wable.util.Logger;

public class HttpURLConnectionWrapper extends HttpWrapper {

	// [start] �������
	
	/// ����Ʈ ������ ��ä�� ������ ��Ʈ��
	 public String m_request ;
	 	 
	 /// ���� ������ �ʿ��� ��Ű

	 String lineEnd = "\r\n";
	 String twoHyphens = "--";
	 String boundary = "dkjsei40f9844-------djs8dviw--4-s-df-";
	 
	 //��Ű�����
	 Map<String,String> m = Collections.synchronizedMap(new HashMap<String,String>());
	 private static final char NAME_VALUE_SEPARATOR = '=';
	 
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
				httpcon.setRequestMethod(method);//POST�� GET�̳�
				httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//���ڵ���
				httpcon.setRequestProperty("Language", "ko");//���
				
				if(timeout)
				{
					httpcon.setConnectTimeout(timeout_ms_syncrequest);
					httpcon.setReadTimeout(timeout_ms_syncrequest);
				}
				httpcon.setDoInput(true);//��ǲ��Ʈ�� ��뿩��
				if(m_session) httpcon.setRequestProperty("cookie",  GetCookies());
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
				long start = System.currentTimeMillis();
				
				int response =httpcon.getResponseCode();
				
				Logger.Instance().Write(url.toString()+" recv elapsed time  "+(System.currentTimeMillis()-start) );
				
				// ������ S���� ����� WebView��� Http��ſ��� 15���ΰ� �Ѿ�� ���� �����
                /// ������ �� �� ��� ��쵵 �־��� �ٸ���� �� �ߵǴµ� ������ ������ S��!!! �׷��� ��� �ٶ���
				/// ������ ���鼭 ����Ʈ�� ���������� �����Ѵ�.
				
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
				/// ����Ʈ �޴ٰ� ������ ���� �������鼭 ���� �޼����� �д´�.
				   if (httpcon.getResponseCode() == 500) 
				   {
				    /// ���� �����ϰ� ������ ���� ��ǲ��Ʈ�� ���ؼ� ����޼��� ���
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

		//���ο� ������ ����.
		HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		String bd= "dkjsei40f9844-------djs8dviw--4-s-df-";
		//�б�� ���� ��� �����ϰ� ����
		conn.setDoInput(true);
		conn.setDoOutput(true);

		//ĳ�ø� ������� �ʰ� ����
		conn.setUseCaches(false); 

		//POSTŸ������ ����
		conn.setRequestMethod("POST"); 
		if(m_session) conn.setRequestProperty("cookie", GetCookies());
		//��� ����

		conn.setRequestProperty("Connection","Keep-Alive"); 
		conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + bd); 

		//Output��Ʈ���� ����
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream()); 
		dos.write(buildeMultipartNormalParameter(params).getBytes());
		

		for(Map.Entry<String,Object> entry:files.entrySet())
		{
			File file = new File(entry.getValue().toString());
			FileInputStream fileInputStream = new FileInputStream(file);
			dos.writeBytes("--" + bd + "\r\n"); 
			dos.writeBytes("Content-Disposition: form-data; name=\"asd\";filename=\""+ file.getName() +"\"" + "\r\n"); 
			dos.writeBytes("\r\n"); 

			//���ۻ���� �����Ͽ� buffer�Ҵ�
			int bytesAvailable = fileInputStream.available(); 
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize); 
			byte[] buffer = new byte[bufferSize];
			 
			//��Ʈ���� �ۼ�
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

			//���� ���۸� stream�� ���.  
			dos.flush(); 
		}
		

		Logger.Instance().Write(url.toString()+  " Request success "+  GetCookies());
		long start = System.currentTimeMillis();
		
		int response =conn.getResponseCode();
		
		Logger.Instance().Write(url.toString()+" recv elapsed time  "+(System.currentTimeMillis()-start) );
		
		// ������ S���� ����� WebView��� Http��ſ��� 15���ΰ� �Ѿ�� ���� �����
        /// ������ �� �� ��� ��쵵 �־��� �ٸ���� �� �ߵǴµ� ������ ������ S��!!! �׷��� ��� �ٶ���
		/// ������ ���鼭 ����Ʈ�� ���������� �����Ѵ�.
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
	
	private String GetCookies()
	{
		String result="";
		synchronized(m)
		{
			
			for (Map.Entry entry : m.entrySet()) {
			    Object key = entry.getKey();
			    Object value = entry.getValue();
			    result+=key.toString()+"="+value.toString()+";";
			    
			}
		}
		Logger.Instance().Write("all cookie "+result);
		return result;
	}
	
	private void StoreCookie(List<String> cookie)
	{
		try {
			synchronized(m)
			{
				try
				{	
					for(int i=0;i<cookie.size();i++)
					{
						
						String name = cookie.get(i).substring(0, cookie.get(i).indexOf(NAME_VALUE_SEPARATOR));
						String value = cookie.get(i).substring(cookie.get(i).indexOf(NAME_VALUE_SEPARATOR) + 1, cookie.get(i).length());
						
						if(m.containsKey(name))
						{
							Logger.Instance().Write("duplicate cookie "+name);
							m.remove(name);
							
						}
						m.put(name, value);
						
						Logger.Instance().Write("cookie "+value);		
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
				}
			}
		
		} catch (Exception e) {
			Logger.Instance().Write(e);
		}
		
	}
	 
	// [start] IHttpConnectionLayer ����


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
					/// �ϴ� �ּҿ� �����Ͷ� ������
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
						
						/// �ϴ� �ּҿ� �����Ͷ� ������
						String recv = Request(new URL(urls),"GET",null,false);
						Logger.Instance().Write(recv);
						
						callback.OnCallback(true, recv);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
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
			/// �ϴ� �ּҿ� �����Ͷ� ������
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
	
	@Override
	public String POSTFileSync(String url, Map<String, Object> params,
			Map<String, Object> files) {
		try
		{
			
			String recv = RequestWithFiles(new URL(url),params,files);
			Logger.Instance().Write(recv);
			return recv;
			
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
		}
		
		return null;
	}
	
}
