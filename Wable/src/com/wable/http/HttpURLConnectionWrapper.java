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
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wable.util.Logger;

public class HttpURLConnectionWrapper extends HttpWrapper {

	// [start] IHttpConnectionLayer ����
	
	/// ������Ʈ ������ ��ä�� ������ ��Ʈ��
	 public String m_request ;
	 	 
	 /// ���� ������ �ʿ��� ��Ű
	 String m_cookies = "" ;

	 String lineEnd = "\r\n";
	 String twoHyphens = "--";
	 String boundary = "dkjsei40f9844-------djs8dviw--4-s-df-";

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
						for(int i=0;i<cookie.size();i++) m_cookies += cookie.get(i)+";";
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
		if(m_session) conn.setRequestProperty("cookie", m_cookies);
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
		

		Logger.Instance().Write(url.toString()+  " Request success "+  m_cookies);
		long start = System.currentTimeMillis();
		
		int response =conn.getResponseCode();
		
		Logger.Instance().Write(url.toString()+" recv elapsed time  "+(System.currentTimeMillis()-start) );
		
		// ������ S���� ����� WebView����� Http��ſ��� 15���ΰ� �Ѿ�� ���� �����
        /// ������ �� �� ���� ��쵵 �־��� �ٸ���� �� �ߵǴµ� ������ ������ S��!!! �׷��� ���� �ٶ���
		/// ������ ���鼭 ������Ʈ�� ���������� �����Ѵ�.
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
				for(int i=0;i<cookie.size();i++) m_cookies += cookie.get(i)+";";
				Logger.Instance().Write(m_cookies);			
			}
			
			
		}
		return line;
	}
	 
	// [start] IHttpConnectionLayer ����


	@Override
	public boolean POSTAsync(String url, Map<String, Object> params,
			IHttpCallback callback) {
		// TODO Auto-generated method stub
		
		try
		{
			/// �ϴ� �ּҿ� �����Ͷ� ������
			//String recv = Request(new URL(url),"POST",params,null);
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
