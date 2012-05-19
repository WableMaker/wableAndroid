package com.wable.apiproxy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import android.os.Build;

import com.wable.http.HttpClientWrapper;
import com.wable.http.HttpURLConnectionWrapper;
import com.wable.http.IHttpCallback;
import com.wable.http.IHttpConnectionLayer;
import com.wable.util.Logger;

public class APIProxyLayer implements IAPIProxyLayer {

	// [start] IAPIProxyLayer ΩÃ±€≈Ê
	
	static ReentrantLock lock = new ReentrantLock();
	static IAPIProxyLayer instance;
	public static IAPIProxyLayer Instance()
	{
		try
		{
			if(lock.tryLock(2000,TimeUnit.MILLISECONDS))
			{
				try
				{	
					if(instance == null)
						instance = new APIProxyLayer();
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
				}
				finally
				{
					lock.unlock();
				}
			}
			
			
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
		}
		
		return instance;
	}
	
	
	
	APIProxyLayer(){
		if(Integer.parseInt(Build.VERSION.SDK)<=Build.VERSION_CODES.FROYO)
		{
			httpLayer = new HttpClientWrapper();
		}
		else	
		{
			httpLayer = new HttpClientWrapper();//new HttpURLConnectionWrapper();
		}
	}
	
	// [end]
		
	// [start] ∏‚πˆ ∫Øºˆ
	
	IHttpConnectionLayer httpLayer;
	
	String domain= "http://www.wable.co.kr/";
	
	// [end]
		
	// [start] IAPIProxyLayer ±∏«ˆ
	
	@Override
	public boolean Login(String loginid, String password,
			final IAPIProxyCallback callback) {

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("password", password);
		
		httpLayer.POST(domain+"account/loginmobile", params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(false == obj.getBoolean("success"))
								SessionDisconnected();
							else SessionConnected();
						}
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}
				callback.OnCallback(success,obj);
			}
		
		});
		
		return true;

	}

	@Override
	public boolean Logout(IAPIProxyCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean MyInfo(final IAPIProxyCallback callback) {
		if(!httpLayer.IsConnectedSession())
			return false;

		httpLayer.GET(domain+"user/myinfo", new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionUpdate();
						}
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}
				callback.OnCallback(success,obj);
			}
		
		});
		
		return true;
	}



	@Override
	public boolean Register(String loginid, String email, String username,
			String password, final IAPIProxyCallback callback) {


		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("email", email);
		params.put("username", username);
		params.put("password", password);
		
		httpLayer.POST(domain+"account/RegisterMobile", params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(false == obj.getBoolean("success"))
								SessionDisconnected();
							else SessionConnected();
						}
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}
				callback.OnCallback(success,obj);
			}
		
		});
		
		return true;

	}



	@Override
	public boolean FBlogin(String fb_uid, String oauth_token,
			final IAPIProxyCallback callback) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fb_uid", fb_uid);
		params.put("oauth_token", oauth_token);
		
		httpLayer.POST(domain+"account/FBLoginMobile", params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(false == obj.getBoolean("success"))
								SessionDisconnected();
							else SessionConnected();
						}
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}
				callback.OnCallback(success,obj);
			}
		
		});
		
		return true;
	}



	@Override
	public boolean FBregister(String oauth_token, final IAPIProxyCallback callback) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("oauth_token", oauth_token);
		
		httpLayer.POST(domain+"account/FBRegisterMobile", params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(false == obj.getBoolean("success"))
								SessionDisconnected();
							else SessionConnected();
						}
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}
				callback.OnCallback(success,obj);
			}
		
		});
		
		return true;
	}



	@Override
	public boolean FBconnect(String fb_uid, String oauth_token,
			final IAPIProxyCallback callback) {
		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fb_uid", fb_uid);
		params.put("oauth_token", oauth_token);
		
		httpLayer.POST(domain+"account/FBConnectMobile", params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(false == obj.getBoolean("success"))
								SessionDisconnected();
							else SessionConnected();
						}
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}
				callback.OnCallback(success,obj);
			}
		
		});
		
		return true;
	}
	
	// [end]
	
	void SessionDisconnected()
	{
		Logger.Instance().Write("SessionDisconnected");
		httpLayer.SessionClosed();
	}
	
	void SessionConnected()
	{
		Logger.Instance().Write("SessionConnected");
		httpLayer.SessionEstablished();
	}
	
	void SessionUpdate()
	{
		Logger.Instance().Write("SessionUpdate");
		httpLayer.SessionUpdate();
	}
}
