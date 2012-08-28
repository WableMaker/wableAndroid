package com.wable.http.apiproxy;

import java.util.Date;

import android.text.format.Time;


public interface IAPIProxyLayer {
	
	// [start] Account
	
	boolean Login(String loginid,String password, IAPIProxyCallback callback);//�α��� �õ�
	boolean Logout(IAPIProxyCallback callback);//�α��� �õ�
	boolean Register(String loginid,String email,String username,String password, IAPIProxyCallback callback);//�α��� �õ�
	
	boolean FBlogin(String fb_uid,String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	boolean FBregister(String fb_uid,String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	boolean FBconnect(String fb_uid,String oauth_token, IAPIProxyCallback callback);//�α��� �õ�
	boolean AccountResetPassword(String loginid, String email,String mobile, IAPIProxyCallback callback);//패스워드 초기화
	boolean AccountResendActivation(IAPIProxyCallback callback);//이메일 인증 다시 보내기
	boolean AccountEmailAuthorize(String email,IAPIProxyCallback callback);
	boolean AccountChangePassword(String loginid, String oldpassword, String newpassword,IAPIProxyCallback callback);
	
	// [end]
	
	// [start] User
	
	boolean MyInfo(IAPIProxyCallback callback);//������	
	boolean UserUpdate(String name, String introduce,String photo, Boolean publicfb, Boolean publictwitter,
			Boolean publicemail, Boolean publicmobile, Boolean publicphone,IAPIProxyCallback callback);//������
	
	boolean UserEnablePushNotify(Time starttime, Time endtime,IAPIProxyCallback callback);//Ǫ������ �ð�
	boolean UserResetBadgeCount(IAPIProxyCallback callback);//Ǫ������ �ð�
	boolean UserSendSMSAuthCode(String mobile, String code,IAPIProxyCallback callback);
	boolean UserAuthorizedMobile(String mobile, IAPIProxyCallback callback);
	boolean UserGetUpdatedContents(String RequestRecentSyncTime
            , String ProvideRecentSyncTime
            , String MatchRecentSyncTime
            , String BiddingRecentSyncTime
            , String BiddingMessageRecentSyncTime
            , String EndBiddingRecentSyncTime
            , String EndBiddingMessageRecentSyncTime, IAPIProxyCallback callback);
	
	// [end]
		
	// [start] Request
	
	boolean RequestOtherList(String userid,String lastid,IAPIProxyCallback callback);//������
	boolean RequestMyActiveList(String lastid,IAPIProxyCallback callback);//Ȱ��ȭ�� �Ǵ� �������û���	boolean RequestMyDoneList(String lastid,IAPIProxyCallback callback);//�Ϸ����û���	boolean RequestListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//������
	boolean RequestListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//������
	boolean RequestListbyDistance(double lat, double lon,double mindistance,String keyword,IAPIProxyCallback callback);//������

	boolean RequestAdd(String title, String description, int price, Integer category, Date duedate
			,double lat,double lon, Boolean totwitter, Boolean tofacebook, IAPIProxyCallback callback);//������
	boolean RequestDelete(String request_id ,IAPIProxyCallback callback);//��û ����
	boolean RequestDone(String request_id ,IAPIProxyCallback callback);//��û ����
	
	boolean RequestMyDetailById(String request_id ,IAPIProxyCallback callback);//������
	boolean RequestOtherDetailById(String request_id ,IAPIProxyCallback callback);
	boolean RequestUpdate(String request_id,String title, String description,int price,int category,Date duedate
			,double lat,double lon,boolean totwitter, boolean tofacebook, IAPIProxyCallback callback);//������
	
	boolean RequestListbyTime(String lastid, String keyword,IAPIProxyCallback callback);
	// [end]
	
	// [start] Provide
	
	boolean ProvideOtherList(String userid,String lastid,IAPIProxyCallback callback);//������
	boolean ProvideMyActiveList(String lastid,IAPIProxyCallback callback);//Ȱ��ȭ�� �Ǵ� �����������
	boolean ProvideMyDoneList(String lastid,IAPIProxyCallback callback);//�Ϸ��������
	boolean ProvideListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//������
	boolean ProvideListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//������
	boolean ProvideListbyDistance(double lat, double lon,double mindistance,String keyword,IAPIProxyCallback callback);//������

	boolean ProvideAdd(String title,String description, int minprice,int category
			,double lat,double lon,int radius,boolean totwitter, boolean tofacebook, IAPIProxyCallback callback);//������
	boolean ProvideDelete(String provide_id ,IAPIProxyCallback callback);//���� ����
	boolean ProvideDone(String provide_id ,IAPIProxyCallback callback);//���� ����
	
	boolean ProvideMyDetailById(String provide_id ,IAPIProxyCallback callback);//������
	
	boolean ProvideOtherDetailById(String provide_id ,IAPIProxyCallback callback);
	boolean ProvideUpdate(String provide_id,String title, String description, int minprice,int category
			,double lat,double lon,int radius,boolean totwitter, boolean tofacebook, IAPIProxyCallback callback);//������
	// [end]
	
	// [start] Bidding

	//�����ڸ� �����Ѵ�
	boolean BiddingDecideProvider(String biddingid,IAPIProxyCallback callback);
	
	//��û���� ������ �����Ѵ�
	boolean BiddingDecideRequester(String biddingid,IAPIProxyCallback callback);
	
	
	boolean BiddingOfferAsProvider(String provide_id,String request_id, IAPIProxyCallback callback);//������
	boolean BiddingOfferAsRequester(String request_id,String provide_id, IAPIProxyCallback callback);//������

	boolean BiddingListAsProvider(String last_bidding_id,IAPIProxyCallback callback);
	boolean BiddingListAsRequester(String last_bidding_id,IAPIProxyCallback callback);
	boolean BiddingRating(String bidding_id,String other_id, int rating,String description,IAPIProxyCallback callback);
	
	//�ŷ� ����
	boolean BiddingDelete(String biddingid, IAPIProxyCallback callback);
	
	boolean BiddingSendSMSOfferAsProvider(String request_id, IAPIProxyCallback callback);
	boolean BiddingSendSMSOfferAsRequester(String provide_id, IAPIProxyCallback callback);
	
	// [end]
	
	// [start] Message
	
	boolean MessageSendText(String biddingid, String message,String lastmsgutctime,IAPIProxyCallback callback);//������
	boolean MessageSendImage(String biddingid, String filepath,String lastmsgutctime,IAPIProxyCallback callback);//������
	boolean MessageSendAudio(String biddingid, String filepath,String lastmsgutctime,IAPIProxyCallback callback);//������
	boolean MessageSendVideo(String biddingid, String filepath,String lastmsgutctime,IAPIProxyCallback callback);//������
	
	boolean MessageGet(String biddingid,String lastmsgutctime,IAPIProxyCallback callback);//������
	boolean MessageGetNewMessage(String lastmsgutctime,IAPIProxyCallback callback);//���ڷ� ���� ���������� ����ŷ��޽��� �ޱ�
	

	
	// [end]
	
	// [start] Category
	boolean CategoryList(IAPIProxyCallback callback);	
	boolean CategoryUpdatedTime(IAPIProxyCallback callback);
	
	// [end]
	
	// [start] setting
	
	boolean SettingRegisterDevice(String deviceid, int devicetype,IAPIProxyCallback callback);
	
	// [end]
	
	// [start] System
	boolean SystemAppVersion(IAPIProxyCallback callback);	
	// [end]
}
