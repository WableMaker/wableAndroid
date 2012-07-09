package com.wable.http;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;
import org.apache.http.util.EntityUtils;

import com.wable.util.Logger;

public class HttpClientWrapper extends HttpWrapper  {

	
	// [start] �������
	
	
	BasicCookieStore cookieStore = new  BasicCookieStore();
	SyncBasicHttpContext httpContext = new SyncBasicHttpContext(new BasicHttpContext());	 
	
	// [end]
	
	public HttpClientWrapper()
	{
		httpContext.setAttribute(ClientContext.COOKIE_STORE,cookieStore);
	}
	

	@Override
	public boolean POSTAsync(final String url,final Map<String, Object> params,
			final IHttpCallback callback) {
		

			new Thread()
	 		{
				@Override
	 			public void run()
	 			{
					try
					{ 
						DefaultHttpClient httpClient = new DefaultHttpClient();
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
					}

				}
				catch(Exception e){
					Logger.Instance().Write(e);
					callback.OnCallback(false,null);
					} 
	 			}
	 		}.start();
			
	
			
	 		return  true;
	}

	@Override
	public boolean GETAsync(String url, Map<String, Object> params, final IHttpCallback callback) {
		// TODO Auto-generated method stub
		
		try {
			final String urls =url + "?"+buildParameters(params);
			
			new Thread()
	 		{
				@Override
	 			public void run()
	 			{
					try
					{
						HttpGet httpGet = new HttpGet(urls);
						DefaultHttpClient httpClient = new DefaultHttpClient();
						httpGet.setHeader("Location", "ko");
						HttpResponse responsePost = httpClient.execute(httpGet,httpContext);
						HttpEntity resEntity = responsePost.getEntity();
						
						List<Cookie> cookies = cookieStore.getCookies();
						for(int i=0;i<cookies.size();i++)
							Logger.Instance().Write(urls+"  cookie: " + cookies.get(i));
						
						if(resEntity !=null)
						{
							String line =EntityUtils.toString(resEntity);
							Logger.Instance().Write(urls+"  response: " + line);
							callback.OnCallback(true,line);
							
							
						}

					}
					catch(Exception e){
						Logger.Instance().Write("GETAsync "+urls+" "+ e.toString());
						callback.OnCallback(false,null);
						}
					
				
				
				
	 			}
	 		}.start();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Logger.Instance().Write("GETAsync "+url+ " " + e1.toString());
			return false;
		}
			
	 		return  true;
			
	}

	

	@Override
	public boolean POSTFileAsync(final String url, final Map<String, Object> params,
			final Map<String, Object> files, final IHttpCallback callback) {
		

		 		new Thread()
		 		{
		 			@Override
		 			public void run()
		 			{
		 				
		 				try
						{
		 					DefaultHttpClient httpClient = new DefaultHttpClient();
		 				//Ÿ�̾Ͽ� �ɱ�
		 				//	HttpParams params = http.getParams();
		 				//	HttpConnectionparams.setConnectionTimeout(params,5000);
		 				// HttpConnectionParams.setSoTimeout(params,5000);
		 					
		 					HttpPost httpPost = new HttpPost(url);
		 					
		 					MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT); 
		 					
		 					for(Map.Entry<String,Object> entry:params.entrySet())
		 		            {
		 						ContentBody cb =  new StringBody(entry.getValue().toString(),"", null);
		 		            	 entity.addPart(entry.getKey(),cb); 
		 		            }
		 					
		 					for(Map.Entry<String,Object> entry:files.entrySet())
		 		            {
		 						 File file = new File(entry.getValue().toString());
		 						 ContentBody cbFile = new FileBody(file, "image");
		 		            	 entity.addPart(entry.getKey(),cbFile); 
		 		            }
		 					
		 					httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		 					httpPost.setEntity(entity);
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
		 						
		 						
		 					}

		 				}
		 				catch(Exception e){
		 					Logger.Instance().Write(e);
		 					callback.OnCallback(false,null);
		 					}
		 				
		 				
		 				
		 			}
		 		
		 		}.start();
		 		
			return true;
		
	}


	@Override
	public String POSTSync(String url, Map<String, Object> params) {
		// TODO Auto-generated method stub
		
		try
		{
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			//Ÿ�Ӿƿ� �ɱ�
			HttpParams httpParams = httpPost.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,timeout_ms_syncrequest);
			HttpConnectionParams.setSoTimeout(httpParams,timeout_ms_syncrequest);
	
			
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
				return  line;
				
			}
			return  "";
		}
		catch(Exception e){
			Logger.Instance().Write(e);
			
			} 
		return null;
	}


	@Override
	public String GETSync(String url, Map<String, Object> params) {
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			url +="?"+buildParameters(params);
			
			HttpGet httpGet = new HttpGet(url);
			
			//Ÿ�Ӿƿ� �ɱ�
			HttpParams httpParams = httpGet.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,timeout_ms_syncrequest);
			HttpConnectionParams.setSoTimeout(httpParams,timeout_ms_syncrequest);
	
			
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
				return line;
				
			}
			return "";
		}
		catch(Exception e){
			Logger.Instance().Write(e);
			}
		
		
		return null;
	}
	
	
	@Override
	public String POSTFileSync(String url, Map<String, Object> params,
			Map<String, Object> files) {
		try
		{
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//Ÿ�̾Ͽ� �ɱ�
			//	HttpParams params = http.getParams();
			//	HttpConnectionparams.setConnectionTimeout(params,5000);
			// HttpConnectionParams.setSoTimeout(params,5000);
				
			HttpPost httpPost = new HttpPost(url);
			
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT); 
			
			for(Map.Entry<String,Object> entry:params.entrySet())
            {
				ContentBody cb =  new StringBody(entry.getValue().toString(),"", null);
            	 entity.addPart(entry.getKey(),cb); 
            }
			
			for(Map.Entry<String,Object> entry:files.entrySet())
            {
				 File file = new File(entry.getValue().toString());
				 ContentBody cbFile = new FileBody(file, "image");
            	 entity.addPart(entry.getKey(),cbFile); 
            }
			
			httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			httpPost.setEntity(entity);
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
				return line;
				
				
			}
			
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
		}
		
		return null;
	}
	
	// [end]

}
