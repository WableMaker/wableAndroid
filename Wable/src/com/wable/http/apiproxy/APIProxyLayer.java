package com.wable.http.apiproxy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import android.os.Build;
import android.text.format.Time;

import com.wable.http.HttpClientWrapper;
import com.wable.http.HttpURLConnectionWrapper;
import com.wable.http.IHttpCallback;
import com.wable.http.IHttpConnectionLayer;
import com.wable.util.Logger;

////////////////////////////////
//---APIProxyInterface 구현 클래스-//
//-----최종 수정일 2012.07.12------//
//------수정자 : 백두산------------//
//------버전 : 0.99-------------//
////////////////////////////////

//각 함수들의 내용은 IAPIProxyLayer.java에 명시되어 있음
public class APIProxyLayer implements IAPIProxyLayer {

	// [start] 싱글콘 구현부
	

	static ReentrantLock _lock = new ReentrantLock();//싱글콘 개체 락
	static IAPIProxyLayer _instance;//싱글톤 객체
	public static IAPIProxyLayer Instance()//싱글톤 객체 반환
	{
		try
		{
			if(_lock.tryLock(2000,TimeUnit.MILLISECONDS))//동기화를 위한 락. 단 타임아웃을 주어 데드락 방지
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
	
	
	//생성자
	APIProxyLayer(){
		if(Integer.parseInt(Build.VERSION.SDK)<=Build.VERSION_CODES.FROYO)
		{
			_httpLayer = new HttpClientWrapper();//프로요 이하
		}
		else	
		{
			_httpLayer =new HttpURLConnectionWrapper();//진저브레드 이상
		}
	}
	
	// [end]
		
	// [start] 멤버변수
	
	IHttpConnectionLayer _httpLayer;//실제 HTTP연결부
	
	String _domain= "http://wable.co.kr/";//기본 도메인
	String _domainSSL= "https://wable.co.kr/";//SSL도메인
	
	String _loginid;//마지막으로 로그인한 아이디
	String _password;//마지막으로 로그인한 패스워드
	String _oauth_token;//마지막 으로 로그인한 페이스북 토큰
	String _fb_uid;//마지막 으로 로그인한 페이스북 유저 아이디
	// [end]
		
	// [Start] 내부 함수
	
	//세견 해제
	//인자 : 호출된 함수
	void SessionDisconnected(String function)
	{
		Logger.Instance().Write("SessionDisconnected "+function);
		_httpLayer.SessionClosed();
	}
	
	//세견 연결
	//인자 : 호출된 함수
	void SessionConnected(String function)
	{
		Logger.Instance().Write("SessionConnected "+function);
		_httpLayer.SessionEstablished();
	}
	
	//세션 업데이트
	//인자 : 호출된 함수
	void SessionUpdate(String function)
	{
		Logger.Instance().Write("SessionUpdate "+function);
		_httpLayer.SessionUpdate();
	}

	//날짜객체를 문자열로 반환, 형식 : yyyy-MM-dd HH:mm:ss
	String ConvertDateToString(Date date)
	{
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Logger.Instance().Write(date.toString());
		String result = curFormater.format(date);
		Logger.Instance().Write(result);
		return result;
	}
	
	//로그인된 계정정보 저장, 재로그인을 위해 임시 저장함
	void SetAccountInfo(String loginid,	String password,String fb_uid ,	String oauth_token)
	{
		
		if(loginid==null)//페북계정접속
		{
			_oauth_token = oauth_token;
			_fb_uid = fb_uid;
		}
		else//일반 아이디로 접속
		{
			_loginid = loginid;
			_password = password;
		}
		
	}
	
	//재로그인, 스레드내에서 불리우는 함수이므로 동기식으로 처리함
	//반환 : true - 성공 ,false - 실패
	boolean Relogin()
	{
		try
		{
			Map<String,Object> params = new HashMap<String,Object>();
			String result;
			if(_loginid==null || _password ==null)//마지막으로 로그인한 아이디없음
			{
				if(_oauth_token==null || _fb_uid ==null)//마지막으로 페이스북으로 로그인한 적업음
				{
					return false;//기존 로그인 정보 없음
				}				
				
				//마지막으로 페이스북으로 로그인
				params.put("fb_uid", _fb_uid);
				params.put("oauth_token", _oauth_token);
				result = _httpLayer.POSTSync(_domainSSL+"account/FBLoginMobile", params);

			}
			else
			{
				//마지막으로 로그인한 아디이로 로그인
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
						//로그인 성공
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
		//로그인 실패
		return false;
		
	}

	// [end]
	
	// [start]  IAPIProxyLayer 인터페이스 구현
	// 각 함수들은 스레드 방식으로 비동기 처리하여 UI가 블럭되는 형상 방지
	
	@Override
	public boolean Login(String loginid, String password,
			final IAPIProxyCallback callback) {

		SetAccountInfo(loginid,password,null,null);
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("password", password);
		
		//비동기 스레드 시작
		new Thread()
		{
			@Override
 			public void run()
 			{
				try
				{
						String result = _httpLayer.POSTSync(_domainSSL+"account/loginmobile", params);
						// TODO Auto-generated method stub
						JSONObject obj = null;
						if(result != null)
						{
							try
							{
								obj = new JSONObject(result);
								if(false == obj.getBoolean("success"))
									SessionDisconnected("Login");
								else SessionConnected("Login");
								callback.OnCallback(true,obj);
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
				callback.OnCallback(false,null);

 			}
		}.start();
		
		
		
		return true;

	}

	@Override
	public boolean Logout(final IAPIProxyCallback callback) {
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
					// TODO Auto-generated method stub
					JSONObject obj = null;
					if(result != null)
					{
						try
						{
							obj = new JSONObject(result);
							if(true == obj.getBoolean("success"))
								SessionDisconnected("Logout");
							callback.OnCallback(true,obj);
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
				callback.OnCallback(false,null);
				

 			}
		}.start();
		
		
		
		return true;
	}

	@Override
	public boolean MyInfo(final IAPIProxyCallback callback) {
		
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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				if(result != null)
				{
					try
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionDisconnected("Logout");
						callback.OnCallback(true,obj);
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
			String password, final IAPIProxyCallback callback) {

		SetAccountInfo(loginid,password,null,null);

		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginid", loginid);
		params.put("email", email);
		params.put("username", username);
		params.put("password", password);
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.POSTSync(_domainSSL+"account/RegisterMobile", params);
				// TODO Auto-generated method stub
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
			final IAPIProxyCallback callback) {
		
		SetAccountInfo(null,null,fb_uid,oauth_token);
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("fb_uid", fb_uid);
		params.put("oauth_token", oauth_token);
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.POSTSync(_domainSSL+"account/FBLoginMobile", params);
				// TODO Auto-generated method stub
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
	public boolean FBregister(String fb_uid,String oauth_token, final IAPIProxyCallback callback) {
		
		
		SetAccountInfo(null,null,fb_uid,oauth_token);
		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("oauth_token", oauth_token);
		params.put("fb_uid", fb_uid);
		
		
		
		new Thread()
		{
			@Override
 			public void run()
 			{
				String result = _httpLayer.POSTSync(_domainSSL+"account/FBRegisterMobile", params);
				// TODO Auto-generated method stub
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
			final IAPIProxyCallback callback) {

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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("FBconnect");
						
						callback.OnCallback(true,obj);
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
			final IAPIProxyCallback callback) {

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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("OtherRequestList");
						
						callback.OnCallback(true,obj);
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
	public boolean RequestMyActiveList(String lastid, final IAPIProxyCallback callback) {

		
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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("MyActiveRequestList");
						
						callback.OnCallback(true,obj);
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
			final IAPIProxyCallback callback) {

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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestListbyTime");
						
						callback.OnCallback(true,obj);
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
			double west, String keyword, final IAPIProxyCallback callback) {

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

				String result = _httpLayer.GETSync(_domain+"Request/ListbyArea", params);
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestListbyArea");
						
						callback.OnCallback(true,obj);
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
			double mindistance, String keyword, final IAPIProxyCallback callback) {
		
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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestListbyDistance");
						
						callback.OnCallback(true,obj);
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
	public boolean UserUpdate(String name, String introduce, String photo,
			final IAPIProxyCallback callback) {
		

		
		final Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("introduce", introduce);
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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserUpdate");
						callback.OnCallback(true,obj);
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
	public boolean RequestMyDoneList(String lastid, final IAPIProxyCallback callback) {
		

		
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
				
				String result = _httpLayer.GETSync(_domain+"Request/MyDoneList", params);
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("RequestMyDoneList");
						callback.OnCallback(true,obj);
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
			final IAPIProxyCallback callback) {
	
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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserEnablePushNotify");
						callback.OnCallback(true,obj);
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
	public boolean UserResetBadgeCount(final IAPIProxyCallback callback) {
		

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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserResetBadgeCount");
						callback.OnCallback(true,obj);
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
			final IAPIProxyCallback callback) {
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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserSendSMSAuthCode");
						callback.OnCallback(true,obj);
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
			final IAPIProxyCallback callback) {
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
				// TODO Auto-generated method stub
				JSONObject obj = null;
				
				try
				{
					if(result !=null)
					{
						obj = new JSONObject(result);
						if(true == obj.getBoolean("success"))
							SessionUpdate("UserAuthorizedMobile");
						callback.OnCallback(true,obj);
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
