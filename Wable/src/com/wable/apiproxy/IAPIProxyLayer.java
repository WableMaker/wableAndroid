package com.wable.apiproxy;

public interface IAPIProxyLayer {
	
	// [start] 계정 설정
	
	boolean Login(String loginid,String password, IAPIProxyCallback callback);//로그인 시도
	boolean Logout(IAPIProxyCallback callback);//로그인 시도
	boolean Register(String loginid,String email,String username,String password, IAPIProxyCallback callback);//로그인 시도
	
	boolean FBlogin(String fb_user_id,String oauth_token, IAPIProxyCallback callback);//로그인 시도
	boolean FBregister(String oauth_token, IAPIProxyCallback callback);//로그인 시도
	boolean FBconnect(String fb_user_id,String oauth_token, IAPIProxyCallback callback);//로그인 시도
	
	// [end]
	
	boolean MyInfo(IAPIProxyCallback callback);//로그인 시도
}
