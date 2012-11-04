package com.thx.bizcat.http.apiproxy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import android.os.Build;
import android.os.Handler;
import android.text.format.Time;

import com.thx.bizcat.http.HttpClientWrapper;
import com.thx.bizcat.http.HttpURLConnectionWrapper;
import com.thx.bizcat.http.IHttpConnectionLayer;
import com.thx.bizcat.util.Logger;

public class APIProxyLayer implements IAPIProxyLayer {

	// [start] �̱���
	

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
		
	// [start] �������
	
	IHttpConnectionLayer _httpLayer;
	
	String _domain= "http://wable.co.kr/";
	String _domainSSL= "https://wable.co.kr/";
	
	String _loginid;
	String _password;
	String _oauth_token;
	String _fb_uid;
	// [end]
		
	// [Start] ����Լ�
	
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
	
	boolean Relogin()
	{
		try
		{
			Map<String,Object> params = new HashMap<String,Object>();
			String result;
			if(_loginid==null || _password ==null)//�α��� �������� ��°��
			{
				if(_oauth_token==null || _fb_uid ==null)//��ϰ������ ������				
					return false;			
				
				params.put("fb_uid", _fb_uid);
				params.put("oauth_token", _oauth_token);
				result = _httpLayer.POSTSync(_domainSSL+"account/FBLoginMobile", params);

			}
			else
			{
				params.put("loginid", _loginid);
				params.put("password", _password);
				result = _httpLayer.POSTSync(_domainSSL+"account/loginmobile", params);
			}

			JSONObject obj = null;
			if(result != null)
			{
				try
				{

					obj = new JSONObject(result);
					if(false == obj.getBoolean("success")) SessionDisconnected("Login");
					else 
					{
						SessionConnected("Login");
						return true;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write("Relogin "+e.toString());
					
				}
			}

		}
		catch(Exception e)
		{
			Logger.Instance().Write("Relogin"+ e.toString());
		}
		
		return false;
		
	}

	// [end]
	
	// [start] IAPIProxyLayer ����
	
	@Override
	public boolean Login(String loginid, String password,
			//final IAPIProxyCallback callback) {

			final Handler callback) {
			
		SetAccountInfo(loginid,password,null,null);
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("login_id", loginid);
		params.put("password", password);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				try
				{
						String result = _httpLayer.POSTSync(_domainSSL+"account/loginmobile", params);
						 
						JSONObject obj = null;
						if(result != null)
						{
							try
							{
								obj = new JSONObject(result);
								boolean success = obj.getBoolean("success");
								if(false == success)
									SessionDisconnected("Login");
								else {
									SessionConnected("Login");
								}
								
								callback.sendMessage(callback.obtainMessage(APICODE.Login.toInt(), obj));
								return;
							}
							catch(Exception e)
							{
								Logger.Instance().Write(e);
								
							}
						}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.sendMessage(callback.obtainMessage(APICODE.Login.toInt(), obj));
				

 			}
		}.start();
		
		
		
		return true;

	}

	@Override
	public boolean Logout(final Handler callback) {
		SetAccountInfo(null,null,null,null);
		if(!_httpLayer.IsConnectedSession())
			return true;
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				try
				{
					String result = _httpLayer.POSTSync(_domain+"account/LogOffMobile", null);
					 
					JSONObject obj = null;
					if(result != null)
					{
						try
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionDisconnected("Logout");
							callback.sendMessage(callback.obtainMessage(APICODE.Logout.toInt(), obj));
							return;
						}
						catch(Exception e)
						{
							Logger.Instance().Write(e);
							
						}
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				callback.sendMessage(callback.obtainMessage(APICODE.Logout.toInt(), obj));
				

 			}
		}.start();
		
		
		
		return true;
	}

	@Override
	public boolean MyInfo(final Handler callback) {
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"user/myinfo", null);
				 
				JSONObject obj = null;
				if(result != null)
				{
					try
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionDisconnected("Logout");
						callback.OnCallback(true,obj);
						return;
					}
					catch(Exception e)
					{
						Logger.Instance().Write(e);
						
					}
				}
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		
		return true;
	}



	@Override
	public boolean Register(String loginid, String email, String username,
			String password, final Handler callback) {

		SetAccountInfo(loginid,password,null,null);

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("login_id", loginid);
		params.put("email", email);
		params.put("username", username);
		params.put("password", password);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.POSTSync(_domainSSL+"account/RegisterMobile", params);
				 
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(false == obj.getBoolean("success"))
							SessionDisconnected("Register");
						else SessionConnected("Register");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
				}
					
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;

	}



	@Override
	public boolean FBlogin(String fb_uid, String oauth_token,
			final Handler callback) {
		
		SetAccountInfo(null,null,fb_uid,oauth_token);
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("fb_uid", fb_uid);
		params.put("oauth_token", oauth_token);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.POSTSync(_domainSSL+"account/FBLoginMobile2", params);
				 
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(false == obj.getBoolean("success"))
							SessionDisconnected("FBlogin");
						else SessionConnected("FBlogin");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean FBregister(String fb_uid,String oauth_token, final Handler callback) {
		
		
		SetAccountInfo(null,null,fb_uid,oauth_token);
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("oauth_token", oauth_token);
		params.put("fb_uid", fb_uid);
		
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.POSTSync(_domainSSL+"account/FBRegisterMobile2", params);
				 
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(false == obj.getBoolean("success"))
							SessionDisconnected("FBregister");
						else SessionConnected("FBregister");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
				
		return true;
	}



	@Override
	public boolean FBconnect(String fb_uid, String oauth_token,
			final Handler callback) {

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("fb_uid", fb_uid);
		params.put("oauth_token", oauth_token);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domainSSL+"account/FBConnectMobile", params);
				 
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("FBconnect");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}
	
	@Override
	public boolean RequestOtherList(String userid, String lastid,
			final Handler callback) {

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", userid);
		if(lastid !=null)
			params.put("lastid", lastid);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Request/OtherList", params);
				 
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("OtherRequestList");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean RequestMyActiveList(String lastid, final Handler callback) {

		
		final Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)
			params.put("lastid", lastid);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Request/MyActiveList", params);
				 
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("MyActiveRequestList");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();

		return true;
	}



	@Override
	public boolean RequestListbyTime(String lastid, String keyword,
			final Handler callback) {

		final Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)params.put("lastid", lastid);
		if(keyword !=null)params.put("keyword", keyword);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}
				String result = _httpLayer.GETSync(_domain+"Request/ListbyTime", params);
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestListbyTime");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		

		return true;
	}



	@Override
	public boolean RequestListbyArea(double north, double south, double east,
			double west, String keyword, final Handler callback) {

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("north", north);
		params.put("south", south);
		params.put("east", east);
		//test params.put("west", west);
		if(keyword !=null)params.put("keyword", keyword);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Request/ListbyArea", params);
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestListbyArea");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean RequestListbyDistance(double lat, double lon,
			double mindistance, String keyword, final Handler callback) {
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("mindistance", mindistance);
		if(keyword !=null)params.put("keyword", keyword);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Request/ListbyDistance", params);
				 
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestListbyDistance");
						
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean ProvideOtherList(String userid, String lastid,
			final Handler callback) {

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", userid);
		if(lastid !=null)
			params.put("lastid", lastid);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Provide/OtherList", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("OtherProvideList");
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean ProvideMyActiveList(String lastid, final Handler callback) {

		
		final Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)
			params.put("lastid", lastid);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Provide/MyActiveList", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("MyActiveProvideList");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean ProvideListbyTime(String lastid, String keyword,
			final Handler callback) {

		final Map<String,Object> params = new HashMap<String,Object>();
		if(lastid !=null)params.put("lastid", lastid);
		if(keyword !=null)params.put("keyword", keyword);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Provide/ListbyTime", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideListbyTime");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean ProvideListbyArea(double north, double south, double east,
			double west, String keyword, final Handler callback) {

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("north", north);
		params.put("south", south);
		params.put("east", east);
		params.put("west", west);
		if(keyword !=null)params.put("keyword", keyword);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Provide/ListbyArea", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideListbyArea");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();

		return true;
	}


	@Override
	public boolean ProvideListbyDistance(double lat, double lon,
			double mindistance, String keyword, final Handler callback) {

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("mindistance", mindistance);
		if(keyword !=null)params.put("keyword", keyword);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Provide/ListbyDistance", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideListbyDistance");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean RequestAdd(String title, String description, int price,
			Integer category, Date duedate, double lat, double lon,
			Boolean totwitter, Boolean tofacebook, 
			final Handler callback) {

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("title", title);
		params.put("description", description);
		params.put("price", price);
		params.put("category", category);
		params.put("duedate", ConvertDateToString(duedate));
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("totwitter", totwitter);
		params.put("tofacebook", tofacebook);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Request/Add", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("AddRequest");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean ProvideAdd(String title,String description, int minprice, int category,
			double lat, double lon, int radius, boolean totwitter, boolean tofacebook, 
			final Handler callback) {


		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("title", title);
		params.put("description", description);
		params.put("minprice", minprice);
		params.put("category", category);
		params.put("radius", radius);
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("totwitter", totwitter);
		params.put("tofacebook", tofacebook);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"Provide/Add", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("AddProvide");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean CategoryList(final Handler callback) {
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.GETSync(_domain+"Category/List", null);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Category");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean CategoryUpdatedTime(final Handler callback) {
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.GETSync(_domain+"Category/Updatedtime", null);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Category");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean BiddingOfferAsProvider(String provide_id,String request_id, 
			final Handler callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/OfferAsProvider", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("OfferAsProvider");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean BiddingOfferAsRequester(String request_id,String provide_id, 
			final Handler callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/OfferAsRequester", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("OfferAsRequester");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
				
		return true;
	}
	
	@Override
	public boolean MessageSendText(String biddingid, String message,String lastmsgutctick,
			final Handler callback) {
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("message", message);
		params.put("lastmsgutctick", lastmsgutctick);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Message/SetMessage", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Message/SetMessage");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean RequestDelete(String request_id, final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Request/Delete", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestDelete");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
	
		return true;
	}



	@Override
	public boolean ProvideDelete(String provide_id, final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Provide/Delete", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideDelete");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
			
		return true;
	}



	@Override
	public boolean MessageGet(String biddingid, String lastmsgutctick,
			final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", lastmsgutctick);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Message/GetMessage", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("MessageGet");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
				
		return true;
	}



	@Override
	public boolean RequestMyDetailById(String request_id,
			final Handler callback) {
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", request_id);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Request/MyDetailById", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestMyDetailById");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
				
		return true;
	}



	@Override
	public boolean ProvideMyDetailById(String request_id,
			final Handler callback) {
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", request_id);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Provide/MyDetailById", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideMyDetailById");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();

		return true;
	}

	@Override
	public boolean MessageSendImage(String biddingid, String filepath,String lastmsgutctick,
			final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", lastmsgutctick);
		final Map<String,Object> files = new HashMap<String,Object>();
		files.put("filepath", filepath);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTFileSync(_domain+"Provide/MyDetailById", params,files);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Message/SetImage");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
				
		return true;
	}
	
	@Override
	public boolean RequestOtherDetailById(String request_id,
			final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", request_id);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Request/OtherDetailById", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestOtherDetailById");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
				
		return true;
	}

	@Override
	public boolean ProvideOtherDetailById(String provide_id,
			final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", provide_id);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Provide/OtherDetailById", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideOtherDetailById");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		
		return true;
	}

	@Override
	public boolean RequestUpdate(String request_id, String title,
			String description, int price, int category, Date duedate,
			double lat, double lon, boolean totwitter, boolean tofacebook,
			final Handler callback) {
		

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", request_id);
		params.put("title", title);
		params.put("description", description);
		params.put("price", price);
		params.put("category", category);
		params.put("duedate", ConvertDateToString(duedate));
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("totwitter", totwitter);
		params.put("tofacebook", tofacebook);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Request/Update", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestUpdate");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();

		
		return true;
		
	}

	@Override
	public boolean ProvideUpdate(String provide_id, String title, String description, int minprice,
			int category, double lat, double lon, int radius,boolean totwitter, boolean tofacebook, 
			final Handler callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", provide_id);
		params.put("title", title);
		params.put("description", description);
		params.put("minprice", minprice);
		params.put("category", category);
		params.put("radius", radius);
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("totwitter", totwitter);
		params.put("tofacebook", tofacebook);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Provide/Update", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideUpdate");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}

	@Override
	public boolean SystemAppVersion(final Handler callback) {
		

		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"System/AndroidAppVersion", null);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("SystemAppVersion");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();

		return true;
	}

	@Override
	public boolean MessageSendAudio(String biddingid, String filepath,
			String lastmsgutctick, final Handler callback) {
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", lastmsgutctick);
		final Map<String,Object> files = new HashMap<String,Object>();
		files.put("filepath", filepath);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTFileSync(_domain+"Message/SetAudio", params,files);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Message/SetAudio");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}

	@Override
	public boolean MessageSendVideo(String biddingid, String filepath,
			String lastmsgutctick, final Handler callback) {
		

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		params.put("lastmsgutctick", lastmsgutctick);
		final Map<String,Object> files = new HashMap<String,Object>();
		files.put("filepath", filepath);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTFileSync(_domain+"Message/SetVideo", params,files);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Message/SetVideo");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}

	@Override
	public boolean UserUpdate(String name, String introduce,String photo, Boolean publicfb, Boolean publictwitter,
			Boolean publicemail, Boolean publicmobile, Boolean publicphone,
			final Handler callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("introduce", introduce);
		params.put("publicfb", publicfb);
		params.put("publictwitter", publictwitter);
		params.put("publicemail", publicemail);
		params.put("publicmobile", publicmobile);
		params.put("publicphone", publicphone);
		final Map<String,Object> files = new HashMap<String,Object>();
		files.put("photo", photo);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTFileSync(_domain+"User/Update", params,files);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserUpdate");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();

		return true;
	}

	@Override
	public boolean BiddingListAsProvider(String last_bidding_id,
			final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("last_bidding_id", last_bidding_id);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Bidding/ListAsProvider", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingListAsProvider");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();

		return true;
	}



	@Override
	public boolean BiddingListAsRequester(String last_bidding_id,
			final Handler callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("last_bidding_id", last_bidding_id);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Bidding/ListAsRequester", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingListAsRequester");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}


	@Override
	public boolean ProvideMyDoneList(String lastid, final Handler callback) {
		

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("lastid", lastid);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Provide/MyDoneList", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Provide/MyDoneList");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean RequestDone(String request_id, final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Request/Done", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestDone");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean ProvideDone(String provide_id, final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Provide/Done", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("ProvideDone");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean MessageGetNewMessage(String lastmsgutctime, final Handler callback) {
		

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("lastmsgutctime", lastmsgutctime);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.GETSync(_domain+"Message/GetNewMessage", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("MessageGetNewMessage");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean SettingRegisterDevice(String deviceid, int devicetype,
			final Handler callback) {
		
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceid", deviceid);
		params.put("devicetype", devicetype);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Setting/RegisterDevice", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("SettingRegisterDevice");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean BiddingRating(String bidding_id, String other_id,
			int rating, String description, final Handler callback) {
		

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("bidding_id", bidding_id);
		params.put("other_id", other_id);
		params.put("rating", rating);
		params.put("description", description);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/Rating", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingRating");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean BiddingDecideProvider(String biddingid,
			final Handler callback) {
		

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/DecideProvider", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingDecideProvider");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean BiddingDecideRequester(String biddingid,
			final Handler callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/DecideRequester", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingDecideRequester");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		

		return true;
	}



	@Override
	public boolean BiddingDelete(String biddingid, final Handler callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("biddingid", biddingid);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/Delete", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingDelete");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean UserEnablePushNotify(Time starttime, Time endtime,
			final Handler callback) {
	
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("starttime", starttime.format("HH:mm:ss"));
		params.put("endtime", endtime.format("HH:mm:ss"));
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"User/EnablePushNotify", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserEnablePushNotify");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean UserResetBadgeCount(final Handler callback) {
		

		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"User/ResetBadgeCount", null);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserResetBadgeCount");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean UserSendSMSAuthCode(String mobile, String code,
			final Handler callback) {
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", mobile);
		params.put("code", code);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"User/SendSMSAuthCode", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserSendSMSAuthCode");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		
		return true;
	}



	@Override
	public boolean UserAuthorizedMobile(String mobile,
			final Handler callback) {
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", mobile);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"User/AuthorizedMobile", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserAuthorizedMobile");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}



	@Override
	public boolean UserGetUpdatedContents(String RequestRecentSyncTime
            , String ProvideRecentSyncTime
            , String MatchRecentSyncTime
            , String BiddingRecentSyncTime
            , String BiddingMessageRecentSyncTime
            , String EndBiddingRecentSyncTime
            , String EndBiddingMessageRecentSyncTime,final Handler callback) {
		 
			
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("RequestRecentSyncTime", RequestRecentSyncTime);
		params.put("ProvideRecentSyncTime", ProvideRecentSyncTime);
		params.put("MatchRecentSyncTime", MatchRecentSyncTime);
		params.put("BiddingRecentSyncTime", BiddingRecentSyncTime);
		params.put("BiddingMessageRecentSyncTime", BiddingMessageRecentSyncTime);
		params.put("EndBiddingRecentSyncTime", EndBiddingRecentSyncTime);
		params.put("EndBiddingMessageRecentSyncTime", EndBiddingMessageRecentSyncTime);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())	
					{
						callback.OnCallback(false,null);
						return;
					}
				}

				String result = _httpLayer.GETSync(_domain+"User/GetUpdatedContents", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("GetUpdatedContents");
						callback.OnCallback(true,obj);
						return;
					}
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
 			}
			
		}.start();
		
		return true;
	}



	@Override
	public boolean AccountResetPassword(String loginid, String email
			,String mobile,final Handler callback) {
		 
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("email", email);
		params.put("mobile", mobile);
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Account/ResetPassword", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("Account/ResetPassword");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}



	@Override
	public boolean AccountResendActivation(final Handler callback) {
		 
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Account/ResendActivation", null);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("AccountResendActivation");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}

	@Override
	public boolean AccountEmailAuthorize(String email,final Handler callback) {
		 
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("email", email);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Account/EmailAuthorize", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("AccountEmailAuthorize");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}



	@Override
	public boolean AccountChangePassword(String loginid, String oldpassword,
			String newpassword, final Handler callback) {
		 
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("oldpassword", oldpassword);
		params.put("newpassword", newpassword);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Account/ChangePassword", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("AccountChangePassword");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}



	@Override
	public boolean BiddingSendSMSOfferAsProvider(String request_id,
			final Handler callback) {
		 
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("request_id", request_id);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/SendSMSOfferAsProvider", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingSendSMSOfferAsProvider");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}



	@Override
	public boolean BiddingSendSMSOfferAsRequester(String provide_id,
			final Handler callback) {
		 
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("provide_id", provide_id);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"Bidding/SendSMSOfferAsRequester", params);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("BiddingSendSMSOfferAsRequester");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}



	@Override
	public boolean UserDeletePhoto(final Handler callback) {
		 
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				if(!_httpLayer.IsConnectedSession())
				{
					if(!Relogin())
						return;
				}
				
				String result = _httpLayer.POSTSync(_domain+"User/DeletePhoto", null);
				 
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserDeletePhoto");
						callback.OnCallback(true,obj);
						return;
					}
					
				}
				catch(Exception e)
				{
					Logger.Instance().Write(e);
					
				}
				
				callback.OnCallback(false,null);
				
 			}
			
		}.start();
		return true;
	}


	
	// [end]
	




}
