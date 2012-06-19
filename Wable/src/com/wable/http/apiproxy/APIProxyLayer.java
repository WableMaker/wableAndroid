package com.wable.http.apiproxy;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	// [start] 싱글톤
	

	static ReentrantLock _lock = new ReentrantLock();
	static IAPIProxyLayer _instance;
	public static IAPIProxyLayer Instance()
	{
		try
		{
			if(_lock.tryLock(2000,TimeUnit.MILLISECONDS))
			{
				try
				{	
					if(_instance == null)
						_instance = new APIProxyLayer();
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
				}
				finally
				{
					_lock.unlock();
				}
			}
			
			
		}
		catch(Exception e)
		{
			Logger.Instance().Write(e);
		}
		
		return _instance;
	}
	
	
	
	APIProxyLayer(){
		if(Integer.parseInt(Build.VERSION.SDK)<=Build.VERSION_CODES.FROYO)
		{
			_httpLayer = new HttpClientWrapper();
		}
		else	
		{
			_httpLayer =new HttpURLConnectionWrapper();
		}
	}
	
	// [end]
		
	// [start] 멤버 변수
	
	IHttpConnectionLayer _httpLayer;
	
	String _domain= "http://wable.co.kr/";
	
	String _loginid;
	String _password;
	String _oauth_token;
	String _fb_uid;
	// [end]
		
	// [Start] 멤버 함수
	
	void SessionDisconnected(String function)
	{
		Logger.Instance().Write("SessionDisconnected "+function);
		_httpLayer.SessionClosed();
	}
	
	void SessionConnected(String function)
	{
		Logger.Instance().Write("SessionConnected "+function);
		_httpLayer.SessionEstablished();
	}
	
	void SessionUpdate(String function)
	{
		Logger.Instance().Write("SessionUpdate "+function);
		_httpLayer.SessionUpdate();
	}

	
	String ConvertDateToString(Date date)
	{
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Logger.Instance().Write(date.toString());
		String result = curFormater.format(date);
		Logger.Instance().Write(result);
		return result;
	}
	
	boolean Relogin()
	{
		try
		{
			Map<String,Object> params = new HashMap<String,Object>();
			String result;
			if(_loginid==null || _password ==null)//로그인 계정정보 없는경우
			{
				if(_oauth_token==null || _fb_uid ==null)//페북계겅정보 없는 경우
				{
					return false;
				}				
				
				params.put("fb_uid", _fb_uid);
				params.put("oauth_token", _oauth_token);
				_httpLayer.POSTAsync(_domain+"account/FBLoginMobile", params,new IHttpCallback()
				{
					@Override
					public void OnCallback(boolean success,String result) {
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
								Logger.Instance().Write("Relogin "+e.toString());
								
							}
						}
					}
				});

			}
			else
			{
				params.put("loginid", _loginid);
				params.put("password", _password);
				_httpLayer.POSTAsync(_domain+"account/loginmobile", params,new IHttpCallback()
				{
					@Override
					public void OnCallback(boolean success,String result) {
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
								Logger.Instance().Write("Relogin "+e.toString());
								
							}
						}
					}
				});
			}
			
//			JSONObject json = new JSONObject(result);
//			boolean blogin= json.getBoolean("success");
//			return blogin;
		}
		catch(Exception e)
		{
			Logger.Instance().Write("Relogin"+ e.toString());
		}
		return true;
	}

	void SetAccountInfo(String loginid,	String password,String fb_uid ,	String oauth_token)
	{
		
		if(loginid==null)
		{
			_oauth_token = oauth_token;
			_fb_uid = fb_uid;
		}
		else
		{
			_loginid = loginid;
			_password = password;
		}
		
	}

	
	// [end]
	
	// [start] IAPIProxyLayer 구현
	
	@Override
	public boolean Login(String loginid, String password,
			final IAPIProxyCallback callback) {

		SetAccountInfo(loginid,password,null,null);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("password", password);
		
		_httpLayer.POSTAsync(_domain+"account/loginmobile", params, new IHttpCallback(){

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
	public boolean Logout(final IAPIProxyCallback callback) {
		SetAccountInfo(null,null,null,null);
		if(!_httpLayer.IsConnectedSession())
			return true;
		_httpLayer.POSTAsync(_domain+"account/LogOffMobile", null, new IHttpCallback(){

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
								SessionDisconnected("Logout");
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
	public boolean MyInfo(final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		_httpLayer.GETAsync(_domain+"user/myinfo",null, new IHttpCallback(){

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
						Logger.Instance().Write("MyInfo "+e.toString());
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

		SetAccountInfo(loginid,password,null,null);

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("email", email);
		params.put("username", username);
		params.put("password", password);
		
		_httpLayer.POSTAsync(_domain+"account/RegisterMobile", params, new IHttpCallback(){

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
		
		SetAccountInfo(null,null,fb_uid,oauth_token);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fb_uid", fb_uid);
		params.put("oauth_token", oauth_token);
		
		_httpLayer.POSTAsync(_domain+"account/FBLoginMobile", params, new IHttpCallback(){

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
	public boolean FBregister(String fb_uid,String oauth_token, final IAPIProxyCallback callback) {
		
		
		SetAccountInfo(null,null,fb_uid,oauth_token);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("oauth_token", oauth_token);
		params.put("fb_uid", fb_uid);
		
		_httpLayer.POSTAsync(_domain+"account/FBRegisterMobile", params, new IHttpCallback(){

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
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
			
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("fb_uid", fb_uid);
		params.put("oauth_token", oauth_token);
		
		_httpLayer.POSTAsync(_domain+"account/FBConnectMobile", params, new IHttpCallback(){

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
								SessionUpdate("FBconnect");
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
	public boolean RequestOtherList(String userid, String lastid,
			final IAPIProxyCallback callback) {

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", userid);
		if(lastid !=null)
			params.put("lastid", lastid);
		
		_httpLayer.GETAsync(_domain+"Request/OtherList",params, new IHttpCallback(){

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
	public boolean RequestMyActiveList(String lastid, final IAPIProxyCallback callback) {

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)
			params.put("lastid", lastid);
		
		_httpLayer.GETAsync(_domain+"Request/MyActiveList", params, new IHttpCallback(){

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
								SessionUpdate("MyActiveRequestList");
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

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)params.put("lastid", lastid);
		if(keyword !=null)params.put("keyword", keyword);
		
		_httpLayer.GETAsync(_domain+"request/ListbyTime", params, new IHttpCallback(){

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

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("north", north);
		params.put("south", south);
		params.put("east", east);
		params.put("west", west);
		if(keyword !=null)params.put("keyword", keyword);
		
		_httpLayer.GETAsync(_domain+"request/ListbyArea", params, new IHttpCallback(){

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
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("distance", distance);
		if(keyword !=null)params.put("keyword", keyword);
		
		_httpLayer.GETAsync(_domain+"Request/ListbyDistance", params, new IHttpCallback(){

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
	public boolean ProvideOtherList(String userid, String lastid,
			final IAPIProxyCallback callback) {

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", userid);
		if(lastid !=null)
			params.put("lastid", lastid);
		
		_httpLayer.GETAsync(_domain+"Provide/OtherList",params, new IHttpCallback(){

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
	public boolean ProvideMyActiveList(String lastid, final IAPIProxyCallback callback) {

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)
			params.put("lastid", lastid);
		
		_httpLayer.GETAsync(_domain+"Provide/MyActiveList", params, new IHttpCallback(){

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
								SessionUpdate("MyActiveProvideList");
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

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)params.put("lastid", lastid);
		if(keyword !=null)params.put("keyword", keyword);
		
		_httpLayer.GETAsync(_domain+"Provide/ListbyTime", params, new IHttpCallback(){

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

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("north", north);
		params.put("south", south);
		params.put("east", east);
		params.put("west", west);
		if(keyword !=null)params.put("keyword", keyword);
		
		_httpLayer.GETAsync(_domain+"Provide/ListbyArea", params, new IHttpCallback(){

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

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("distance", distance);
		if(keyword !=null)params.put("keyword", keyword);
		
		_httpLayer.GETAsync(_domain+"Provide/ListbyDistance", params, new IHttpCallback(){

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



	@Override
	public boolean RequestAdd(String title, String description, int postprice,
			Integer category, Date duedate, double lat, double lon,
			Boolean totwitter, Boolean tofacebook, 
			final IAPIProxyCallback callback) {

		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("title", title);
		params.put("description", description);
		params.put("price", postprice);
		params.put("category", category);
		params.put("duedate", ConvertDateToString(duedate));
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("totwitter", totwitter);
		params.put("tofacebook", tofacebook);
		
		
		_httpLayer.POSTAsync(_domain+"Request/Add", params, new IHttpCallback(){

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
								SessionUpdate("AddRequest");
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
	public boolean ProvideAdd(String title, int minprice, int category,
			double lat, double lon, int radius,  
			final IAPIProxyCallback callback) {


		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("title", title);
		params.put("minprice", minprice);
		params.put("category", category);
		params.put("radius", radius);
		params.put("lat", lat);
		params.put("lon", lon);
		
		
		_httpLayer.POSTAsync(_domain+"Provide/Add", params, new IHttpCallback(){

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
								SessionUpdate("AddProvide");
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
	public boolean CategoryList(final IAPIProxyCallback callback) {
		
		_httpLayer.GETAsync(_domain+"Category/List",null, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {

				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionUpdate("Category");
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
	public boolean CategoryUpdatedTime(final IAPIProxyCallback callback) {
		
		_httpLayer.GETAsync(_domain+"Category/Updatedtime",null, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionUpdate("Category");
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
	public boolean BiddingOfferAsProvider(String request_id, String message,
			final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		params.put("message", message);
		
		_httpLayer.POSTAsync(_domain+"Bidding/OfferAsProvider", params, new IHttpCallback(){

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
								SessionUpdate("OfferAsProvider");
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
	public boolean BiddingOfferAsRequester(String provide_id, String message,
			final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		params.put("message", message);
		
		_httpLayer.POSTAsync(_domain+"Bidding/OfferAsRequester", params, new IHttpCallback(){

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
								SessionUpdate("OfferAsRequester");
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
	public boolean MessageSendText(String biddingid, String message,Double tick,
			final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("message", message);
		params.put("lastmsgutctick", tick);
		
		_httpLayer.POSTAsync(_domain+"Message/SetMessage", params, new IHttpCallback(){

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
								SessionUpdate("Message/SetMessage");
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
	public boolean RequestDelete(String request_id, final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		_httpLayer.POSTAsync(_domain+"Request/Delete", params, new IHttpCallback(){

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
								SessionUpdate("RequestDelete");
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
	public boolean ProvideDelete(String provide_id, final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		
		_httpLayer.POSTAsync(_domain+"Provide/Delete", params, new IHttpCallback(){

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
								SessionUpdate("ProvideDelete");
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
	public boolean MessageGet(String biddingid, Double lastmsgutctick,
			final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", lastmsgutctick);
		
		_httpLayer.GETAsync(_domain+"Message/GetMessage",params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionUpdate("MessageGet");
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
	public boolean RequestMyDetailById(String request_id,
			final IAPIProxyCallback callback) {
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		_httpLayer.GETAsync(_domain+"Request/MyDetailById",params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionUpdate("RequestMyDetailById");
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
	public boolean ProvideMyDetailById(String request_id,
			final IAPIProxyCallback callback) {
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		_httpLayer.GETAsync(_domain+"Provide/MyDetailById",params, new IHttpCallback(){

			@Override
			public void OnCallback(boolean success,String result) {
				
				JSONObject obj = null;
				if(success == true)
				{
					try
					{
						
						if(result !=null)
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionUpdate("RequestMyDetailById");
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
	public boolean MessageSendImage(String biddingid, String filepath,Double tick,
			final IAPIProxyCallback callback) {
		
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", tick);
		Map<String,Object> files = new HashMap<String,Object>();
		files.put("filepath", filepath);
		
		_httpLayer.POSTFileAsync(_domain+"Message/SetImage", params,files, new IHttpCallback(){

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
								SessionUpdate("Message/SetImage");
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
	public boolean RequestOtherDetailById(String request_id,
			final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		_httpLayer.GETAsync(_domain+"Request/OtherDetailById", params, new IHttpCallback(){

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
								SessionUpdate("RequestOtherDetailById");
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
	public boolean ProvideOtherDetailById(String provide_id,
			final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		
		_httpLayer.GETAsync(_domain+"Provide/OtherDetailById", params, new IHttpCallback(){

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
								SessionUpdate("ProvideOtherDetailById");
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
	public boolean RequestUpdate(String request_id, String title,
			String description, int postprice, int category, Date duedate,
			double lat, double lon, boolean totwitter, boolean tofacebook,
			final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", request_id);
		params.put("title", title);
		params.put("description", description);
		params.put("postprice", postprice);
		params.put("category", category);
		params.put("duedate", ConvertDateToString(duedate));
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("totwitter", totwitter);
		params.put("tofacebook", tofacebook);
		
		
		_httpLayer.POSTAsync(_domain+"Request/Update", params, new IHttpCallback(){

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
								SessionUpdate("RequestUpdate");
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
	public boolean ProvideUpdate(String provide_id, String title, int minprice,
			int category, double lat, double lon, int radius,
			final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", provide_id);
		params.put("title", title);
		params.put("minprice", minprice);
		params.put("category", category);
		params.put("radius", radius);
		params.put("lat", lat);
		params.put("lon", lon);
		
		_httpLayer.POSTAsync(_domain+"Provide/Update", params, new IHttpCallback(){

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
								SessionUpdate("ProvideUpdate");
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
	public boolean SystemAppVersion(final IAPIProxyCallback callback) {
		

		_httpLayer.GETAsync(_domain+"System/AndroidAppVersion", null, new IHttpCallback(){

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
								SessionUpdate("SystemAppVersion");
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
	public boolean MessageSendAudio(String biddingid, String filepath,
			Double tick, final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", tick);
		Map<String,Object> files = new HashMap<String,Object>();
		files.put("filepath", filepath);
		
		_httpLayer.POSTFileAsync(_domain+"Message/SetAudio", params,files, new IHttpCallback(){

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
								SessionUpdate("Message/SetAudio");
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
	public boolean MessageSendVideo(String biddingid, String filepath,
			Double tick, final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", tick);
		Map<String,Object> files = new HashMap<String,Object>();
		files.put("filepath", filepath);
		
		_httpLayer.POSTFileAsync(_domain+"Message/SetVideo", params,files, new IHttpCallback(){

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
								SessionUpdate("Message/SetVideo");
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
	public boolean UserUpdate(String name, String introduce, String photo,
			final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("introduce", introduce);
		Map<String,Object> files = new HashMap<String,Object>();
		files.put("photo", photo);
		
		_httpLayer.POSTFileAsync(_domain+"User/Update", params,files, new IHttpCallback(){

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
								SessionUpdate("UserUpdate");
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
	public boolean BiddingListAsProvider(String last_bidding_id,
			final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("last_bidding_id", last_bidding_id);
		
		_httpLayer.GETAsync(_domain+"Bidding/ListAsProvider", params, new IHttpCallback(){

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
								SessionUpdate("BiddingListAsProvider");
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
	public boolean BiddingListAsRequester(String last_bidding_id,
			final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("last_bidding_id", last_bidding_id);
		
		_httpLayer.GETAsync(_domain+"Bidding/ListAsRequester", params, new IHttpCallback(){

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
								SessionUpdate("BiddingListAsRequester");
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
	public boolean RequestMyDoneList(String lastid, final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lastid", lastid);
		
		_httpLayer.GETAsync(_domain+"Request/MyDoneList", params, new IHttpCallback(){

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
								SessionUpdate("RequestMyDoneList");
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
	public boolean ProvideMyDoneList(String lastid, final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lastid", lastid);
		
		_httpLayer.GETAsync(_domain+"Provide/MyDoneList", params, new IHttpCallback(){

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
								SessionUpdate("Provide/MyDoneList");
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
	public boolean RequestDone(String request_id, final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		_httpLayer.POSTAsync(_domain+"Request/Done", params, new IHttpCallback(){

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
								SessionUpdate("RequestDone");
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
	public boolean ProvideDone(String provide_id, final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		
		_httpLayer.POSTAsync(_domain+"Provide/Done", params, new IHttpCallback(){

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
								SessionUpdate("ProvideDone");
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
	public boolean MessageGetNewMessage(Double tick, final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lastmsgutctick", tick);
		
		_httpLayer.GETAsync(_domain+"Message/GetNewMessage", params, new IHttpCallback(){

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
								SessionUpdate("MessageGetNewMessage");
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
	public boolean SettingRegisterDevice(String deviceid, int devicetype,
			final IAPIProxyCallback callback) {
		
		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceid", deviceid);
		params.put("devicetype", devicetype);
		
		_httpLayer.POSTAsync(_domain+"Setting/RegisterDevice", params, new IHttpCallback(){

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
								SessionUpdate("SettingRegisterDevice");
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
	public boolean BiddingRating(String bidding_id, String other_id,
			int rating, String description, final IAPIProxyCallback callback) {
		

		if(!_httpLayer.IsConnectedSession())
		{
			if(!Relogin())
				return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("bidding_id", bidding_id);
		params.put("other_id", other_id);
		params.put("rating", rating);
		params.put("description", description);
		
		_httpLayer.POSTAsync(_domain+"Bidding/Rating", params, new IHttpCallback(){

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
								SessionUpdate("BiddingRating");
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
