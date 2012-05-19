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
				if(success == true)
				{
					try
					{
						JSONObject obj = null;
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(false == obj.getBoolean("success"))
								SessionDisconnected();
							else SessionConnected();
							
							callback.OnCallback(success,new JSONObject(result));
							
						}
						else callback.OnCallback(success,null);
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}else callback.OnCallback(success,null);
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
				if(success == true)
				{
					try
					{
						JSONObject obj = null;
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(false == obj.getBoolean("success"))
								SessionDisconnected();
							else SessionConnected();
							
							callback.OnCallback(success,new JSONObject(result));
							
						}
						else callback.OnCallback(success,null);
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						callback.OnCallback(false,null);
					}
				}else callback.OnCallback(success,null);
			}
		
		});
		
		return true;
	}



	@Override
	public boolean Register(String loginid, String email, String username,
			String password, IAPIProxyCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean FBlogin(String fb_user_id, String oauth_token,
			IAPIProxyCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean FBregister(String oauth_token, IAPIProxyCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean FBconnect(String fb_user_id, String oauth_token,
			IAPIProxyCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// [end]
	
	void SessionDisconnected()
	{
		httpLayer.SessionClosed();
	}
	
	void SessionConnected()
	{
		httpLayer.SessionEstablished();
	}
	
	void SessionUpdate()
	{
		httpLayer.SessionUpdate();
	}
}
