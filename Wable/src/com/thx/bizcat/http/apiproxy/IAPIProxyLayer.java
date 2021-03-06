package com.thx.bizcat.http.apiproxy;

import java.util.Date;

import android.os.Handler;
import android.text.format.Time;


public interface IAPIProxyLayer {
	
	// [start] Account
	
	
	//반환 클래스 class sp_LogIn_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_LogIn_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	// public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean Login(String loginid,String password, Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;		
	boolean Logout(Handler callback);
	
	//반환 클래스 class sp_LogIn_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_LogIn_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	// public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean Register(String loginid,String email,String username,String password, Handler callback);
	
	//반환 클래스 class sp_LogIn_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_LogIn_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	// public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean FBlogin(String fb_uid,String oauth_token, Handler callback);//�α��� �õ�
	
	//반환 클래스 class sp_LogIn_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_LogIn_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	// public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_LogIn_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean FBregister(String fb_uid,String oauth_token, Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean FBconnect(String fb_uid,String oauth_token, Handler callback);//�α��� �õ�
	//반환 클래스 sp_AccountResetPassword_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_AccountResetPassword_Result result=정보;
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public sp_AccountResetPassword_Result result=null;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_AccountResetPassword_Result result=null;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean AccountResetPassword(String loginid, String email,String mobile, Handler callback);
	boolean AccountResendActivation(Handler callback);//이메일 인증 다시 보내기
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean AccountEmailAuthorize(String email,Handler callback);
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean AccountChangePassword(String loginid, String oldpassword, String newpassword,Handler callback);
	
	// [end]
	
	// [start] User
	
	//반환 클래스 class sp_MyInfo_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_GetUserInfo_Result result = 내용;
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public sp_GetUserInfo_Result result = null;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_GetUserInfo_Result result = null;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;
	boolean MyInfo(Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean UserUpdate(String name, String introduce,String photo, Boolean publicfb, Boolean publictwitter,
			Boolean publicemail, Boolean publicmobile, Boolean publicphone,Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean UserEnablePushNotify(Time starttime, Time endtime,Handler callback);

	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;		
	boolean UserResetBadgeCount(Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;		
	boolean UserSendSMSAuthCode(String mobile, String code,Handler callback);

	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;		
	boolean UserAuthorizedMobile(String mobile, Handler callback);
	
	//반환 클래스 class sp_GetProviderDetail_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_GetProviderDetail_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public sp_GetProviderDetail_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_GetProviderDetail_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean UserProviderDetailById(String userid, Handler callback);
	
	//반환 클래스 class sp_GetRequesterDetail_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_GetRequesterDetail_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	// public sp_GetRequesterDetail_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_GetRequesterDetail_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean UserRequesterDetailById(String userid, Handler callback);
	
	
	//반환 클래스 class sp_UserGetUpdatedContents_Items
	//호출 오류시
	//null
	//
	//성공시
	// List<sp_GetMyUpdatedRequests_Result> newrequests;//인자 RequestRecentSyncTime 이후의 요청사항들
	// List<sp_GetMyUpdatedProvides_Result> newprovides;//인자 ProvideRecentSyncTime 이후의 요청사항들
	// List<sp_GetMyUpdatedBiddings_Result> newbiddings;//인자 BiddingRecentSyncTime 이후의 요청사항들
    // List<sp_GetNewMessage_Result> newbiddingmessages;//인자 BiddingMessageRecentSyncTime 이후의 요청사항들
	// List<sp_GetMyUpdatedMatch_Result> newmatches;//인자 MatchRecentSyncTime 이후의 요청사항들
	// String  last_modified_time_request;//서버에서 가져온 가장 마지막 Request 항목 의 시간, 이 값을 로컬에 저장하였다가 다음 호출시 인자 RequestRecentSyncTime에 넣어줌
	// String  last_modified_time_provide;//서버에서 가져온 가장 마지막 Provide 항목 의 시간, 이 값을 로컬에 저장하였다가 다음 호출시 인자 ProvideRecentSyncTime 넣어줌
	// String  last_modified_time_bidding;//서버에서 가져온 가장 마지막 bidding 항목 의 시간, 이 값을 로컬에 저장하였다가 다음 호출시 인자 BiddingRecentSyncTime 넣어줌
	// String  last_modified_time_biddingmessage;//서버에서 가져온 가장 마지막 biddingmessage 항목 의 시간, 이 값을 로컬에 저장하였다가 다음 호출시 인자 BiddingMessageRecentSyncTime 넣어줌
	// String  last_modified_time_match;//서버에서 가져온 가장 마지막 match 항목 의 시간, 이 값을 로컬에 저장하였다가 다음 호출시 인자 MatchRecentSyncTime 넣어줌
	// boolean bsuccess = true;
	// ResultCode resultCode = ResultCode.NONE;
	//
	//실패시
	// List<sp_GetMyUpdatedRequests_Result> newrequests= null
	// List<sp_GetMyUpdatedProvides_Result> newprovides= null
	// List<sp_GetMyUpdatedBiddings_Result> newbiddings= null
    // List<sp_GetNewMessage_Result> newbiddingmessages= null
	// List<sp_GetMyUpdatedMatch_Result> newmatches= null
	// String  last_modified_time_request= null
	// String  last_modified_time_provide= null
	// String  last_modified_time_bidding= null
	// String  last_modified_time_biddingmessage= null
	// String  last_modified_time_match= null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	// List<sp_GetMyUpdatedRequests_Result> newrequests= null
	// List<sp_GetMyUpdatedProvides_Result> newprovides= null
	// List<sp_GetMyUpdatedBiddings_Result> newbiddings= null
    // List<sp_GetNewMessage_Result> newbiddingmessages= null
	// List<sp_GetMyUpdatedMatch_Result> newmatches= null
	// String  last_modified_time_request= null
	// String  last_modified_time_provide= null
	// String  last_modified_time_bidding= null
	// String  last_modified_time_biddingmessage= null
	// String  last_modified_time_match= null
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean UserGetUpdatedContents(String RequestRecentSyncTime
            , String ProvideRecentSyncTime
            , String MatchRecentSyncTime
            , String BiddingRecentSyncTime
            , String BiddingMessageRecentSyncTime
            , String EndBiddingRecentSyncTime
            , String EndBiddingMessageRecentSyncTime, Handler callback);

	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean UserDeletePhoto(Handler callback);
	// [end]
		
	// [start] Request
	
	//반환 클래스 class sp_GetOtherRequests_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetOtherRequests_Result> requests = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetOtherRequests_Result> requests = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetOtherRequests_Result> requests = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean RequestOtherList(String userid,String lastid,Handler callback);

	//반환 클래스 class sp_GetMyActiveRequests_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetMyActiveRequests_Result> requests = 정보 ;
	//public List<sp_GetMySuggestRequests_Result> suggests = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetMyActiveRequests_Result> requests = null ;
	//public List<sp_GetMySuggestRequests_Result> suggests = null ;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetMyActiveRequests_Result> requests = null ;
	//public List<sp_GetMySuggestRequests_Result> suggests = null ;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean RequestMyActiveList(String lastid,Handler callback);
	
	//반환 클래스 class sp_GetMyActiveRequests_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetRequestListbyArea_Result> requests = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetRequestListbyArea_Result> requests = null ;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetRequestListbyArea_Result> requests = null ;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean RequestListbyArea(double north,double south,double ease,double west,String keyword,Handler callback);
	
	//반환 클래스 class sp_GetMyActiveRequests_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetRequestsByDistance_Result> requests = 정보 ;
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetRequestsByDistance_Result> requests = null ;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetRequestsByDistance_Result> requests = null ;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;
	boolean RequestListbyDistance(double lat, double lon,double mindistance,String keyword,Handler callback);

	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean RequestAdd(String title, String description, int price, Integer category, Date duedate
			,double lat,double lon, Boolean totwitter, Boolean tofacebook, Handler callback);
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean RequestDelete(String request_id ,Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean RequestDone(String request_id ,Handler callback);
	
	
	
	//반환 클래스 class sp_RequestMyDetailById_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_GetMyRequestByID_Result request= 정보;
	//public List<sp_GetMatchByRequest_Result> requests = 정보;
	//public List<sp_GetBiddingByRequest_Result> biddings = 정보;
	//public int bidding_count= 정보;
	//public int matching_count= 정보;
	//public boolean bsuccess = true;
	//public ResultCode resultCode=ResultCode .NONE;	
	//
	//실패시
	//public sp_GetMyRequestByID_Result request= null;
	//public List<sp_GetMatchByRequest_Result> requests = null;
	//public List<sp_GetBiddingByRequest_Result> biddings = null;
	//public int bidding_count= null;
	//public int matching_count= null;
	//public boolean bsuccess = null;
	//public ResultCode resultCode=실패사유;	
	//
	//서버호출 실패시
	//public sp_GetMyRequestByID_Result request= null;
	//public List<sp_GetMatchByRequest_Result> requests = null;
	//public List<sp_GetBiddingByRequest_Result> biddings= null;
	//public int bidding_count= null;
	//public int matching_count= null;
	//public boolean bsuccess = false;
	//public ResultCode resultCode=ResultCode .NONE;	
	boolean RequestMyDetailById(String request_id ,Handler callback);
	
	//반환 클래스 class sp_GetOtherRequestByID_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetOtherRequestByID_Result> requests = 정보 ;
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetOtherRequestByID_Result> requests = null ;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetOtherRequestByID_Result> requests = null ;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;
	boolean RequestOtherDetailById(String request_id ,Handler callback);

	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;	
	boolean RequestUpdate(String request_id,String title, String description,int price,int category,Date duedate
			,double lat,double lon,boolean totwitter, boolean tofacebook, Handler callback);
	
	//반환 클래스 class sp_GetRequestsByTime_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetRequestsByTime_Result> requests = 정보 ;
	//public boolean bsuccess =true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetRequestsByTime_Result> requests = null ;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetRequestsByTime_Result> requests = null ;
	//public boolean bsuccess =false;
	//public ResultCode resultCode = ResultCode .NONE;
	boolean RequestListbyTime(String lastid, String keyword,Handler callback);
	// [end]
	
	// [start] Provide
	
	//반환 클래스 class sp_GetOtherProvides_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetOtherProvides_Result> result : 정보
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetOtherProvides_Result> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetOtherProvides_Result> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;		
	boolean ProvideOtherList(String userid,String lastid,Handler callback);
	//boolean ProvideMyActiveList(String lastid,Handler callback);
	//boolean ProvideMyDoneList(String lastid,Handler callback);
	
	//반환 클래스 class sp_GetProvidesByTime_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetProvidesByTime_Result> result : 정보
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetProvidesByTime_Result> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetProvidesByTime_Result> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;		
	boolean ProvideListbyTime(String lastid,String keyword,Handler callback);
	
	//반환 클래스 class sp_GetProvidesByArea_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetProvidesByArea_Result> result : 정보
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetProvidesByArea_Result> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetProvidesByArea_Result> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;		
	boolean ProvideListbyArea(double north,double south,double ease,double west,String keyword,Handler callback);
	
	//반환 클래스 class sp_GetProvidesByDistance_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetProvidesByDistance_Items> result : 정보
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetProvidesByDistance_Items> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetProvidesByDistance_Items> result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;		
	boolean ProvideListbyDistance(double lat, double lon,double mindistance,String keyword,Handler callback);

	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean ProvideAdd(String title,String description, int minprice,int category
			,double lat,double lon,int radius,boolean totwitter, boolean tofacebook, Handler callback);
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean ProvideDelete(String provide_id ,Handler callback);
	//boolean ProvideDone(String provide_id ,Handler callback);
	
	//반환 클래스 class sp_GetMyProvideByID_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_GetMyProvideByID_Result result : 정보
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public sp_GetMyProvideByID_Result result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_GetMyProvideByID_Result result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;		
	boolean ProvideMyDetailById(String provide_id ,Handler callback);
	
	//반환 클래스 class sp_GetOtherProvideByID_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_GetOtherProvideByID_Result result : 정보
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public sp_GetOtherProvideByID_Result result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_GetOtherProvideByID_Result result = null
	//public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;		
	boolean ProvideOtherDetailById(String provide_id ,Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean ProvideUpdate(String provide_id,String title, String description, int minprice,int category
			,double lat,double lon,int radius,boolean totwitter, boolean tofacebook, Handler callback);
	// [end]
	
	// [start] Bidding

	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean BiddingDecideProvider(String biddingid,Handler callback);
	
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;	
	boolean BiddingDecideRequester(String biddingid,Handler callback);
	
	
	//반환 클래스 class sp_BiddingOfferAsProvider_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_BiddingOfferAsProvider_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	// public sp_BiddingOfferAsProvider_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_BiddingOfferAsProvider_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean BiddingOfferAsProvider(String provide_id,String request_id, Handler callback);
	
	//반환 클래스 class sp_BiddingOfferAsRequester_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_BiddingOfferAsRequester_Result result = 정보 ;
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	// public sp_BiddingOfferAsRequester_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_BiddingOfferAsRequester_Result result = null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean BiddingOfferAsRequester(String request_id,String provide_id, Handler callback);

//	boolean BiddingListAsProvider(String last_bidding_id,Handler callback);
//	boolean BiddingListAsRequester(String last_bidding_id,Handler callback);
	boolean BiddingRating(String bidding_id,String other_id, int rating,String description,Handler callback);
	
	//반환 클래스 class sp_Simple_Items
	//호출 오류시
	//null
	//
	//성공시
    //public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean BiddingDelete(String biddingid, Handler callback);
	
	boolean BiddingSendSMSOfferAsProvider(String request_id, Handler callback);
	boolean BiddingSendSMSOfferAsRequester(String provide_id, Handler callback);
	
	// [end]
	
	// [start] Message
	
	//반환 클래스 class sp_GetMessage_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetMessage_Result> newmessages = 인자로 넣은 lastmsgutctick이후의 새로운 메시지 ;
	//public sp_GetLatestReadMessage_Result readmessage = 상대방이 읽은 가장 마지막 메시지
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean MessageSendText(String biddingid, String message,String lastmsgutctick, String local_written_time,Handler callback);

	//반환 클래스 class sp_GetMessage_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetMessage_Result> newmessages = 인자로 넣은 lastmsgutctick이후의 새로운 메시지 ;
	//public sp_GetLatestReadMessage_Result readmessage = 상대방이 읽은 가장 마지막 메시지
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean MessageSendImage(String biddingid, String filepath,String lastmsgutctick,Handler callback);

	//반환 클래스 class sp_GetMessage_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetMessage_Result> newmessages = 인자로 넣은 lastmsgutctick이후의 새로운 메시지 ;
	//public sp_GetLatestReadMessage_Result readmessage = 상대방이 읽은 가장 마지막 메시지
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean MessageSendAudio(String biddingid, String filepath,String lastmsgutctick,Handler callback);

	//반환 클래스 class sp_GetMessage_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetMessage_Result> newmessages = 인자로 넣은 lastmsgutctick이후의 새로운 메시지 ;
	//public sp_GetLatestReadMessage_Result readmessage = 상대방이 읽은 가장 마지막 메시지
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean MessageSendVideo(String biddingid, String filepath,String lastmsgutctick,Handler callback);
	
	//반환 클래스 class sp_GetMessage_Items
	//호출 오류시
	//null
	//
	//성공시
	//public List<sp_GetMessage_Result> newmessages = 인자로 넣은 lastmsgutctick이후의 새로운 메시지 ;
	//public sp_GetLatestReadMessage_Result readmessage = 상대방이 읽은 가장 마지막 메시지
	//public boolean bsuccess =true;
    //public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
	//public boolean bsuccess =false;
    //public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public List<sp_GetMessage_Result> newmessages =null ;
	//public sp_GetLatestReadMessage_Result readmessage =null;
    //public boolean bsuccess =false;
    //public ResultCode resultCode = ResultCode .NONE;
	boolean MessageGet(String biddingid,String lastmsgutctick,Handler callback);

	
//	boolean MessageGetNewMessage(String lastmsgutctime,Handler callback);
	

	
	// [end]
	
	// [start] Category
	boolean CategoryList(Handler callback);	
	boolean CategoryUpdatedTime(Handler callback);
	
	// [end]
	
	// [start] setting
	
	boolean SettingRegisterDevice(String deviceid, int devicetype,Handler callback);
	
	// [end]
	
	// [start] System
	//반환 클래스 class sp_CompatiableAppVersion_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_CompatiableAppVersion_Result result = 버전;
	//public boolean bsuccess = true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public sp_CompatiableAppVersion_Result result = null;
	//public boolean bsuccess = false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_CompatiableAppVersion_Result result = null;
	//public boolean bsuccess = false;
	//public ResultCode resultCode = ResultCode .NONE;
	boolean CompatibleAppVersion(Handler callback);	
	
	// [start] System
	//반환 클래스 class sp_LatestAppVersion_Items
	//호출 오류시
	//null
	//
	//성공시
	//public sp_LatestAppVersion_Result result = 버전;
	//public boolean bsuccess = true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//실패시
	//public sp_LatestAppVersion_Result result = null;
	//public boolean bsuccess = false;
	//public ResultCode resultCode = 실패사유;
	//
	//서버호출 실패시
	//public sp_LatestAppVersion_Result result = null;
	//public boolean bsuccess = false;
	//public ResultCode resultCode = ResultCode .NONE;
	boolean LatestAppVersion(Handler callback);
	// [end]

	// [start] System
	//諛섑솚 �대옒��class sp_BaseImgUrl_Items
	//�몄텧 �ㅻ쪟��	//null
	//
	//�깃났��	//public sp_BaseImgUrl_Result result = 踰꾩쟾;
	//public boolean bsuccess = true;
	//public ResultCode resultCode = ResultCode .NONE;
	//
	//�ㅽ뙣��	//public sp_BaseImgUrl_Result result = null;
	//public boolean bsuccess = false;
	//public ResultCode resultCode = �ㅽ뙣�ъ쑀;
	//
	//�쒕쾭�몄텧 �ㅽ뙣��	//public sp_BaseImgUrl_Result result = null;
	//public boolean bsuccess = false;
	//public ResultCode resultCode = ResultCode .NONE;
	boolean BaseImgUrl(Handler callback);
}
