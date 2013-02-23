package com.thx.bizcat.http.apiproxy;

import android.util.SparseArray;

public enum APICODE {


	Login(0),
	Logout(1),
	Register(2),
	
	FBlogin(3),
	FBregister(4),
	FBconnect(5),
	AccountResetPassword(6),
	AccountResendActivation(7),
	AccountEmailAuthorize(8),
	AccountChangePassword(9),
	
	// [end]
	
	// [start] User
	
	MyInfo(10),
	UserUpdate(11),
	
	UserEnablePushNotify(12),
	UserResetBadgeCount(13),
	UserSendSMSAuthCode(14),
	UserAuthorizedMobile(15),
	UserGetUpdatedContents(16),
	UserDeletePhoto(17),
	UserProviderDetailById(100),
	UserRequesterDetailById(101),
	// [end]
		
	// [start] Request
	
	RequestOtherList(18),
	RequestMyActiveList(19),
	RequestListbyArea(20),
	RequestListbyDistance(21),

	RequestAdd(22),
	RequestDelete(23),
	RequestDone(24),
	
	RequestMyDetailById(25),
	RequestOtherDetailById(26),
	RequestUpdate(27),
	
	RequestListbyTime(28),
	// [end]
	
	// [start] Provide
	
	ProvideOtherList(29),
	ProvideMyActiveList(30),
	ProvideMyDoneList(31),
	ProvideListbyTime(32),
	ProvideListbyArea(33),
	ProvideListbyDistance(34),

	ProvideAdd(35),
	ProvideDelete(36),
	ProvideDone(37),
	
	ProvideMyDetailById(38),
	
	ProvideOtherDetailById(39),
	ProvideUpdate(40),
	// [end]
	
	// [start] Bidding


	BiddingDecideProvider(41),

	BiddingDecideRequester(42),
	
	
	BiddingOfferAsProvider(43),
	BiddingOfferAsRequester(44),

	BiddingListAsProvider(45),
	BiddingListAsRequester(46),
	BiddingRating(47),
	
	//�ŷ� ����
	BiddingDelete(48),
	
	BiddingSendSMSOfferAsProvider(49),
	BiddingSendSMSOfferAsRequester(50),
	
	// [end]
	
	// [start] Message
	
	MessageSendText(51),
	MessageSendImage(52),
	MessageSendAudio(53),
	MessageSendVideo(54),
	
	MessageGet(55),
	MessageGetNewMessage(56),
	

	
	// [end]
	
	// [start] Category
	CategoryList(57),	
	CategoryUpdatedTime(58),
	
	// [end]
	
	// [start] setting
	
	SettingRegisterDevice(59),
	
	// [end]
	
	// [start] System
	CompatiableAppVersion(60),
	LatestAppVersion(61),
	BaseImgUrl(62),
	
	USERSET1(9000),
	USERSET2(9001),
	UNKNOWN(10000);

    private static final SparseArray<APICODE> typesByValue = new SparseArray<APICODE>();

    static {
        for (APICODE type : APICODE.values()) {
            typesByValue.put(type.value, type);
        }
    }

    private final int value;

    private APICODE(int value) {
        this.value = value;
    }
    
    public int toInt()
    {
    	return value;
    }

    public static APICODE fromInt(int value) {
       
        
        try{
        	 return typesByValue.get(value);
            }catch(Exception e){
               return APICODE.UNKNOWN;
            }
    }
	
	
}
