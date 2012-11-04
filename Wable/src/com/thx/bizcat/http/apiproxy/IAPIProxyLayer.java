package com.thx.bizcat.http.apiproxy;

import java.util.Date;

import android.os.Handler;
import android.text.format.Time;


public interface IAPIProxyLayer {
	
	// [start] Account
	
	//boolean Login(String loginid,String password, Handler callback);//�α��� �õ�
	boolean Login(String loginid,String password, Handler callback);//�α��� �õ�
	boolean Logout(Handler callback);//�α��� �õ�
	boolean Register(String loginid,String email,String username,String password, Handler callback);//�α��� �õ�
	
	boolean FBlogin(String fb_uid,String oauth_token, Handler callback);//�α��� �õ�
	boolean FBregister(String fb_uid,String oauth_token, Handler callback);//�α��� �õ�
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
	
	boolean MessageSendText(String biddingid, String message,String lastmsgutctick,Handler callback);//������
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