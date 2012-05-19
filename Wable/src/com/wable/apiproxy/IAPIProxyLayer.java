package com.wable.apiproxy;

public interface IAPIProxyLayer {
	
	// [start] Account
	
	boolean Login(String loginid,String password, IAPIProxyCallback callback);//�α��� �õ�
	boolean Logout(IAPIProxyCallback callback);//�α��� �õ�
	boolean Register(String loginid,String email,String username,String password, IAPIProxyCallback callback);//�α��� �õ�
	
	boolean FBlogin(String fb_uid,String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	boolean FBregister(String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	boolean FBconnect(String fb_uid,String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	
	// [end]
	
	// [start] User
	
	boolean MyInfo(IAPIProxyCallback callback);//������
	
	// [end]
	
	
	// [start] Request
	
	boolean OtherRequestList(String userid,String lastid,IAPIProxyCallback callback);//������
	boolean MyRequestList(String lastid,IAPIProxyCallback callback);//������
	boolean RequestListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//������
	boolean RequestListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//������
	boolean RequestListbyDistance(double lat, double lon,double distance,String keyword,IAPIProxyCallback callback);//������

	
	
	// [end]
	
	// [start] Provide
	
	
	// [end]
	
	// [start] Bidding
	
	
	// [end]
	
	
}
