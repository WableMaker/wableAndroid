package com.wable.apiproxy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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
			httpLayer = new HttpURLConnectionWrapper();
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
			IAPIProxyCallback callback) {
		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("password", password);
		
		httpLayer.POST(domain+"loginmobile", params, new IHttpCallback(){

			@Override
			public void OnCallback(String result) {
				// TODO Auto-generated method stub
				
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
	public boolean GetMyInfo(IAPIProxyCallback callback) {
		if(httpLayer.IsConnectedSession())
			return false;
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
}
