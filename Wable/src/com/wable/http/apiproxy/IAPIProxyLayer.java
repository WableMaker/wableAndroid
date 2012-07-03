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
	
	// [end]
	
	// [start] User
	
	boolean MyInfo(IAPIProxyCallback callback);//������	
	boolean UserUpdate(String name,String introduce,String photo,IAPIProxyCallback callback);//������
	
	boolean UserEnablePushNotify(Time starttime, Time endtime,IAPIProxyCallback callback);//Ǫ������ �ð�
	boolean UserResetBadgeCount(IAPIProxyCallback callback);//Ǫ������ �ð�
	
	// [end]
		
	// [start] Request
	
	boolean RequestOtherList(String userid,String lastid,IAPIProxyCallback callback);//������
	boolean RequestMyActiveList(String lastid,IAPIProxyCallback callback);//Ȱ��ȭ�� �Ǵ� ����� ��û���
	boolean RequestMyDoneList(String lastid,IAPIProxyCallback callback);//�Ϸ�� ��û���
	boolean RequestListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//������
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
	// [end]
	
	// [start] Provide
	
	boolean ProvideOtherList(String userid,String lastid,IAPIProxyCallback callback);//������
	boolean ProvideMyActiveList(String lastid,IAPIProxyCallback callback);//Ȱ��ȭ�� �Ǵ� ����� ������
	boolean ProvideMyDoneList(String lastid,IAPIProxyCallback callback);//�Ϸ�� ������
	boolean ProvideListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//������
	boolean ProvideListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//������
	boolean ProvideListbyDistance(double lat, double lon,double mindistance,String keyword,IAPIProxyCallback callback);//������

	boolean ProvideAdd(String title,String description, int minprice,int category
			,double lat,double lon,int radius, IAPIProxyCallback callback);//������
	boolean ProvideDelete(String provide_id ,IAPIProxyCallback callback);//���� ����
	boolean ProvideDone(String provide_id ,IAPIProxyCallback callback);//���� ����
	
	boolean ProvideMyDetailById(String provide_id ,IAPIProxyCallback callback);//������
	
	boolean ProvideOtherDetailById(String provide_id ,IAPIProxyCallback callback);
	boolean ProvideUpdate(String provide_id,String title, String description, int minprice,int category
			,double lat,double lon,int radius, IAPIProxyCallback callback);//������
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
	
	// [end]
	
	// [start] Message
	
	boolean MessageSendText(String biddingid, String message,Double tick,IAPIProxyCallback callback);//������
	boolean MessageSendImage(String biddingid, String filepath,Double tick,IAPIProxyCallback callback);//������
	boolean MessageSendAudio(String biddingid, String filepath,Double tick,IAPIProxyCallback callback);//������
	boolean MessageSendVideo(String biddingid, String filepath,Double tick,IAPIProxyCallback callback);//������
	
	boolean MessageGet(String biddingid,Double tick,IAPIProxyCallback callback);//������
	boolean MessageGetNewMessage(Double tick,IAPIProxyCallback callback);//���ڷ� ���� ���������� ��� �ŷ��޽��� �ޱ�
	

	
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
