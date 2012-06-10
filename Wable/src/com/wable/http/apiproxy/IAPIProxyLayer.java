package com.wable.http.apiproxy;

import java.util.Date;


public interface IAPIProxyLayer {
	
	// [start] Account
	
	boolean Login(String loginid,String password, IAPIProxyCallback callback);//로그인 시도
	boolean Logout(IAPIProxyCallback callback);//로그인 시도
	boolean Register(String loginid,String email,String username,String password, IAPIProxyCallback callback);//로그인 시도
	
	boolean FBlogin(String fb_uid,String oauth_token, IAPIProxyCallback callback);//로그인 시도
	boolean FBregister(String fb_uid,String oauth_token, IAPIProxyCallback callback);//로그인 시도
	boolean FBconnect(String fb_uid,String oauth_token, IAPIProxyCallback callback);//로그인 시도
	
	// [end]
	
	// [start] User
	
	boolean MyInfo(IAPIProxyCallback callback);//내정보
	boolean UserUpdate(String name,String photo,IAPIProxyCallback callback);
	
	// [end]
		
	// [start] Request
	
	boolean RequestOtherList(String userid,String lastid,IAPIProxyCallback callback);//내정보
	boolean RequestMyList(String lastid,IAPIProxyCallback callback);//내정보
	boolean RequestListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//내정보
	boolean RequestListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//내정보
	boolean RequestListbyDistance(double lat, double lon,double distance,String keyword,IAPIProxyCallback callback);//내정보

	boolean RequestAdd(String title, String description,int postprice,int category,Date duedate
			,double lat,double lon,boolean totwitter, boolean tofacebook, IAPIProxyCallback callback);//내정보
	boolean RequestDelete(String request_id ,IAPIProxyCallback callback);//내정보
	
	boolean RequestMyDetailById(String request_id ,IAPIProxyCallback callback);//내정보
	boolean RequestOtherDetailById(String request_id ,IAPIProxyCallback callback);
	boolean RequestUpdate(String request_id,String title, String description,int postprice,int category,Date duedate
			,double lat,double lon,boolean totwitter, boolean tofacebook, IAPIProxyCallback callback);//내정보
	// [end]
	
	// [start] Provide
	
	boolean ProvideOtherList(String userid,String lastid,IAPIProxyCallback callback);//내정보
	boolean ProvideMyList(String lastid,IAPIProxyCallback callback);//내정보
	boolean ProvideListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//내정보
	boolean ProvideListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//내정보
	boolean ProvideListbyDistance(double lat, double lon,double distance,String keyword,IAPIProxyCallback callback);//내정보

	boolean ProvideAdd(String title, int minprice,int category
			,double lat,double lon,int radius, IAPIProxyCallback callback);//내정보
	boolean ProvideDelete(String provide_id ,IAPIProxyCallback callback);//내정보
	
	boolean ProvideMyDetailById(String provide_id ,IAPIProxyCallback callback);//내정보
	
	boolean ProvideOtherDetailById(String provide_id ,IAPIProxyCallback callback);
	boolean ProvideUpdate(String provide_id,String title, int minprice,int category
			,double lat,double lon,int radius, IAPIProxyCallback callback);//내정보
	// [end]
	
	// [start] Bidding
	
	boolean BiddingOfferAsProvider(String request_id, String message,IAPIProxyCallback callback);//내정보
	boolean BiddingOfferAsRequester(String provide_id, String message,IAPIProxyCallback callback);//내정보

	
	// [end]
	
	// [start] Message
	
	boolean MessageSendText(String biddingid, String message,double tick,IAPIProxyCallback callback);//내정보
	boolean MessageSendImage(String biddingid, String filepath,double tick,IAPIProxyCallback callback);//내정보
	boolean MessageSendAudio(String biddingid, String filepath,double tick,IAPIProxyCallback callback);//내정보
	boolean MessageSendVideo(String biddingid, String filepath,double tick,IAPIProxyCallback callback);//내정보
	
	boolean MessageGet(String biddingid,double tick,IAPIProxyCallback callback);//내정보

	
	// [end]
	
	// [start] Category
	boolean CategoryList(IAPIProxyCallback callback);	
	boolean CategoryUpdatedTime(IAPIProxyCallback callback);
	
	// [end]
	
	// [start] System
	boolean SystemAppVersion(IAPIProxyCallback callback);	
	// [end]
}
