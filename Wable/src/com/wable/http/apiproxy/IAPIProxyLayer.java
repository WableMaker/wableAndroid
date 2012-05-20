package com.wable.http.apiproxy;

import java.util.Date;

import android.text.format.DateFormat;

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

	boolean AddRequest(String title, String description,int postprice,int category,Date duedate
			,double lat,double lon,boolean totwitter, boolean tofacebook, boolean userprofilepos,IAPIProxyCallback callback);//������
	
	
	// [end]
	
	// [start] Provide
	
	boolean OtherProvideList(String userid,String lastid,IAPIProxyCallback callback);//������
	boolean MyProvideList(String lastid,IAPIProxyCallback callback);//������
	boolean ProvideListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//������
	boolean ProvideListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//������
	boolean ProvideListbyDistance(double lat, double lon,double distance,String keyword,IAPIProxyCallback callback);//������

	boolean AddProvide(String title, int minprice,int category
			,double lat,double lon,int radius, boolean userprofilepos,IAPIProxyCallback callback);//������
	
	// [end]
	
	// [start] Bidding
	
	boolean OfferAsProvider(String request_id, int price,IAPIProxyCallback callback);//������
	boolean OfferAsRequester(String provide_id, int price,IAPIProxyCallback callback);//������

	
	// [end]
	
	// [start] Message
	
	boolean SetMessage(String biddingid, String message,IAPIProxyCallback callback);//������

	
	// [end]
	
	// [start] Category
	boolean CategoryList(IAPIProxyCallback callback);	
	boolean CategoryUpdatedTime(IAPIProxyCallback callback);
	
	// [end]
	
}
