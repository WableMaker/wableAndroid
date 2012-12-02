package com.thx.bizcat.http.apiproxy;

import java.util.Date;
import java.util.List;

import com.thx.bizcat.http.apiproxy.JSONParser.ResultCode;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetLatestReadMessage_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMessage_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedBiddings_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedMatch_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedRequests_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetNewMessage_Result;

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
	boolean Logout(Handler callback);//�α��� �õ�
	
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
	boolean Register(String loginid,String email,String username,String password, Handler callback);//�α��� �õ�
	
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
	boolean FBregister(String fb_uid,String oauth_token, Handler callback);//�α��� �õ�
	
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
	boolean AccountResetPassword(String loginid, String email,String mobile, Handler callback);//패스워드 초기화
	boolean AccountResendActivation(Handler callback);//이메일 인증 다시 보내기
	boolean AccountEmailAuthorize(String email,Handler callback);
	boolean AccountChangePassword(String loginid, String oldpassword, String newpassword,Handler callback);
	
	// [end]
	
	// [start] User
	
	boolean MyInfo(Handler callback);//������	
	boolean UserUpdate(String name, String introduce,String photo, Boolean publicfb, Boolean publictwitter,
			Boolean publicemail, Boolean publicmobile, Boolean publicphone,Handler callback);//������
	
	boolean UserEnablePushNotify(Time starttime, Time endtime,Handler callback);//Ǫ������ �ð�
	boolean UserResetBadgeCount(Handler callback);//Ǫ������ �ð�
	boolean UserSendSMSAuthCode(String mobile, String code,Handler callback);
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
	boolean UserDeletePhoto(Handler callback);
	// [end]
		
	// [start] Request
	
	boolean RequestOtherList(String userid,String lastid,Handler callback);//������
	boolean RequestMyActiveList(String lastid,Handler callback);//Ȱ��ȭ�� �Ǵ� �������û���	boolean RequestMyDoneList(String lastid,Handler callback);//�Ϸ����û���	boolean RequestListbyTime(String lastid,String keyword,Handler callback);//������
	boolean RequestListbyArea(double north,double south,double ease,double west,String keyword,Handler callback);//������
	boolean RequestListbyDistance(double lat, double lon,double mindistance,String keyword,Handler callback);//������

	boolean RequestAdd(String title, String description, int price, Integer category, Date duedate
			,double lat,double lon, Boolean totwitter, Boolean tofacebook, Handler callback);//������
	boolean RequestDelete(String request_id ,Handler callback);//��û ����
	boolean RequestDone(String request_id ,Handler callback);//��û ����
	
	boolean RequestMyDetailById(String request_id ,Handler callback);//������
	boolean RequestOtherDetailById(String request_id ,Handler callback);
	boolean RequestUpdate(String request_id,String title, String description,int price,int category,Date duedate
			,double lat,double lon,boolean totwitter, boolean tofacebook, Handler callback);//������
	
	boolean RequestListbyTime(String lastid, String keyword,Handler callback);
	// [end]
	
	// [start] Provide
	
	boolean ProvideOtherList(String userid,String lastid,Handler callback);//������
	boolean ProvideMyActiveList(String lastid,Handler callback);//Ȱ��ȭ�� �Ǵ� �����������
	boolean ProvideMyDoneList(String lastid,Handler callback);//�Ϸ��������
	boolean ProvideListbyTime(String lastid,String keyword,Handler callback);//������
	boolean ProvideListbyArea(double north,double south,double ease,double west,String keyword,Handler callback);//������
	boolean ProvideListbyDistance(double lat, double lon,double mindistance,String keyword,Handler callback);//������

	boolean ProvideAdd(String title,String description, int minprice,int category
			,double lat,double lon,int radius,boolean totwitter, boolean tofacebook, Handler callback);//������
	boolean ProvideDelete(String provide_id ,Handler callback);//���� ����
	boolean ProvideDone(String provide_id ,Handler callback);//���� ����
	
	boolean ProvideMyDetailById(String provide_id ,Handler callback);//������
	
	boolean ProvideOtherDetailById(String provide_id ,Handler callback);
	boolean ProvideUpdate(String provide_id,String title, String description, int minprice,int category
			,double lat,double lon,int radius,boolean totwitter, boolean tofacebook, Handler callback);//������
	// [end]
	
	// [start] Bidding

	//�����ڸ� �����Ѵ�
	boolean BiddingDecideProvider(String biddingid,Handler callback);
	
	//��û���� ������ �����Ѵ�
	boolean BiddingDecideRequester(String biddingid,Handler callback);
	
	
	boolean BiddingOfferAsProvider(String provide_id,String request_id, Handler callback);//������
	boolean BiddingOfferAsRequester(String request_id,String provide_id, Handler callback);//������

	boolean BiddingListAsProvider(String last_bidding_id,Handler callback);
	boolean BiddingListAsRequester(String last_bidding_id,Handler callback);
	boolean BiddingRating(String bidding_id,String other_id, int rating,String description,Handler callback);
	
	//�ŷ� ����
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
	boolean MessageSendText(String biddingid, String message,String lastmsgutctick, String local_written_time,Handler callback);//������
	boolean MessageSendImage(String biddingid, String filepath,String lastmsgutctick,Handler callback);//������
	boolean MessageSendAudio(String biddingid, String filepath,String lastmsgutctick,Handler callback);//������
	boolean MessageSendVideo(String biddingid, String filepath,String lastmsgutctick,Handler callback);//������
	
	boolean MessageGet(String biddingid,String lastmsgutctick,Handler callback);//������
	boolean MessageGetNewMessage(String lastmsgutctime,Handler callback);//���ڷ� ���� ���������� ����ŷ��޽��� �ޱ�
	

	
	// [end]
	
	// [start] Category
	boolean CategoryList(Handler callback);	
	boolean CategoryUpdatedTime(Handler callback);
	
	// [end]
	
	// [start] setting
	
	boolean SettingRegisterDevice(String deviceid, int devicetype,Handler callback);
	
	// [end]
	
	// [start] System
	boolean SystemAppVersion(Handler callback);	
	// [end]
}
