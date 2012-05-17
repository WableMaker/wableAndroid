package com.wable.apiproxy;

public interface IAPIProxyLayer {
	
	// [start] ���� ����
	
	boolean Login(String loginid,String password, IAPIProxyCallback callback);//�α��� �õ�
	boolean Logout(IAPIProxyCallback callback);//�α��� �õ�
	boolean Register(String loginid,String email,String username,String password, IAPIProxyCallback callback);//�α��� �õ�
	
	boolean FBlogin(String fb_user_id,String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	boolean FBregister(String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	boolean FBconnect(String fb_user_id,String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	
	// [end]
	
	boolean MyInfo(IAPIProxyCallback callback);//�α��� �õ�
}
