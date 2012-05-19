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
		
	// [Start] ∏‚πˆ «‘ºˆ
	
	void SessionDisconnected(String function)
	{
		Logger.Instance().Write("SessionDisconnected "+function);
		httpLayer.SessionClosed();
	}
	
	void SessionConnected(String function)
	{
		Logger.Instance().Write("SessionConnected "+function);
		httpLayer.SessionEstablished();
	}
	
	void SessionUpdate(String function)
	{
		Logger.Instance().Write("SessionUpdate "+function);
		httpLayer.SessionUpdate();
	}

	
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
								SessionDisconnected("Login");
							else SessionConnected("Login");
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

		httpLayer.GET(domain+"user/myinfo",null, new IHttpCallback(){

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
								SessionUpdate("MyInfo");
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
								SessionDisconnected("Register");
							else SessionConnected("Register");
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
								SessionDisconnected("FBlogin");
							else SessionConnected("FBlogin");
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
								SessionDisconnected("FBregister");
							else SessionConnected("FBregister");
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
								SessionDisconnected("FBconnect");
							else SessionConnected("FBconnect");
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
	public boolean OtherRequestList(String userid, String lastid,
			final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", userid);
		if(lastid !=null)
			params.put("lastid", lastid);
		
		httpLayer.GET(domain+"Request/OtherRequestList",params, new IHttpCallback(){

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
								SessionUpdate("OtherRequestList");
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
	public boolean MyRequestList(String lastid, final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)
			params.put("lastid", lastid);
		
		httpLayer.GET(domain+"Request/MyList", params, new IHttpCallback(){

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
								SessionUpdate("MyRequestList");
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
	public boolean RequestListbyTime(String lastid, String keyword,
			final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)params.put("lastid", lastid);
		if(keyword !=null)params.put("keyword", keyword);
		
		httpLayer.GET(domain+"request/ListbyTime", params, new IHttpCallback(){

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
								SessionUpdate("RequestListbyTime");
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
	public boolean RequestListbyArea(double north, double south, double east,
			double west, String keyword, final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("north", north);
		params.put("south", south);
		params.put("east", east);
		params.put("west", west);
		if(keyword !=null)params.put("keyword", keyword);
		
		httpLayer.GET(domain+"request/ListbyArea", params, new IHttpCallback(){

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
								SessionUpdate("RequestListbyArea");
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
	public boolean RequestListbyDistance(double lat, double lon,
			double distance, String keyword, final IAPIProxyCallback callback) {
		
		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("distance", distance);
		if(keyword !=null)params.put("keyword", keyword);
		
		httpLayer.GET(domain+"Request/ListbyDistance", params, new IHttpCallback(){

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
								SessionUpdate("RequestListbyDistance");
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
	public boolean OtherProvideList(String userid, String lastid,
			final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", userid);
		if(lastid !=null)
			params.put("lastid", lastid);
		
		httpLayer.GET(domain+"Provide/OtherProvideList",params, new IHttpCallback(){

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
								SessionUpdate("OtherProvideList");
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
	public boolean MyProvideList(String lastid, final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)
			params.put("lastid", lastid);
		
		httpLayer.GET(domain+"Provide/MyList", params, new IHttpCallback(){

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
								SessionUpdate("MyProvideList");
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
	public boolean ProvideListbyTime(String lastid, String keyword,
			final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)params.put("lastid", lastid);
		if(keyword !=null)params.put("keyword", keyword);
		
		httpLayer.GET(domain+"Provide/ListbyTime", params, new IHttpCallback(){

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
								SessionUpdate("ProvideListbyTime");
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
	public boolean ProvideListbyArea(double north, double south, double east,
			double west, String keyword, final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("north", north);
		params.put("south", south);
		params.put("east", east);
		params.put("west", west);
		if(keyword !=null)params.put("keyword", keyword);
		
		httpLayer.GET(domain+"Provide/ListbyArea", params, new IHttpCallback(){

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
								SessionUpdate("ProvideListbyArea");
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
	public boolean ProvideListbyDistance(double lat, double lon,
			double distance, String keyword, final IAPIProxyCallback callback) {

		if(!httpLayer.IsConnectedSession())
			return false;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("distance", distance);
		if(keyword !=null)params.put("keyword", keyword);
		
		httpLayer.GET(domain+"Provide/ListbyDistance", params, new IHttpCallback(){

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
								SessionUpdate("ProvideListbyDistance");
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
	




}
