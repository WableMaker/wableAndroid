package com.wable.http.apiproxy;

import java.util.Date;

import android.text.format.Time;

////////////////////////////////
//------ APIProxy 인터페이스------//
//-----최종 수정일 2012.07.08------//
//------수정자 : 백두산------------//
//------버전 : 0.87-------------//
////////////////////////////////
public interface IAPIProxyLayer {
	
	// [start] Account
	
	boolean Login(String loginid,String password, IAPIProxyCallback callback);//로그인
	boolean Logout(IAPIProxyCallback callback);//로그아웃
	boolean Register(String loginid,String email,String username,String password, IAPIProxyCallback callback);//회원가입
	
	boolean FBlogin(String fb_uid,String oauth_token, IAPIProxyCallback callback);//페이스북 계정으로 로그인
	boolean FBregister(String fb_uid,String oauth_token, IAPIProxyCallback callback);//페이스북 계정으로 가입
	boolean FBconnect(String fb_uid,String oauth_token, IAPIProxyCallback callback);//페이스북 계정을 현재 계정과 연결, 로그인 필요
	
	// [end]
	
	// [start] User
	
	boolean MyInfo(IAPIProxyCallback callback);//내정보 획득
	boolean UserUpdate(String name,String introduce,String photo,IAPIProxyCallback callback);//내정보 업데이트
	
	boolean UserEnablePushNotify(Time starttime, Time endtime,IAPIProxyCallback callback);//푸쉬통보 가능 시간 설정
	boolean UserResetBadgeCount(IAPIProxyCallback callback);//푸쉬서비스의 배지카운트 초기화
	boolean UserSendSMSAuthCode(String mobile, String code,IAPIProxyCallback callback);//SMS 인증코드 전송
	boolean UserAuthorizedMobile(String mobile, IAPIProxyCallback callback);//인증된 모바일번호 입력
	
	// [end]
		
	// [start] Request
	
	boolean RequestOtherList(String userid,String lastid,IAPIProxyCallback callback);//다른 사용자의 요청목록 획득
	boolean RequestMyActiveList(String lastid,IAPIProxyCallback callback);//나의 요청목록 획득
	boolean RequestMyDoneList(String lastid,IAPIProxyCallback callback);//나의 완료된 요청 목록
	boolean RequestListbyTime(String lastid,String keyword,IAPIProxyCallback callback);//시간별 요청목록 검색
	boolean RequestListbyArea(double north,double south,double ease,double west,String keyword,IAPIProxyCallback callback);//특정 지역내 요청목록 검색
	boolean RequestListbyDistance(double lat, double lon,double mindistance,String keyword,IAPIProxyCallback callback);//현재 자신의 위치에서 거리별 요청목록 검색

	// [end]
	

}
