package com.wable.apiproxy;

public interface IAPIProxyLayer {
	
	// [start] Account
	
	boolean Login(String loginid,String password, IAPIProxyCallback callback);//로그인 시도
	boolean Logout(IAPIProxyCallback callback);//로그인 시도
	boolean Register(String loginid,String email,String username,String password, IAPIProxyCallback callback);//로그인 시도
	
	boolean FBlogin(String fb_uid,String oauth_token, IAPIProxyCallback callback);//로그인 시도
	boolean FBregister(String oauth_token, IAPIProxyCallback callback);//로그인 시도
	boolean FBconnect(String fb_uid,String oauth_token, IAPIProxyCallback callback);//로그인 시도
	
	// [end]
	
	// [start] User
	
	boolean MyInfo(IAPIProxyCallback callback);//내정보
	
	// [end]
	
	
	// [start] Request
	
	boolean OtherRequestList(String userid,String lastid,IAPIProxyCallback callback);//내정보
	boolean MyRequestList(String lastid,IAPIProxyCallback callback);//내정보
	boolean RequestListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//내정보
	boolean RequestListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//내정보
	boolean RequestListbyDistance(double lat, double lon,double distance,String keyword,IAPIProxyCallback callback);//내정보

	
	
	// [end]
	
	// [start] Provide
	
	
	// [end]
	
	// [start] Bidding
	
	
	// [end]
	
	
}
