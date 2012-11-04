package com.thx.bizcat.util;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

/**
 * Handler 자체는 가비지 컬랙터 수집대상이 아니다
 * 그러나 Activity 에 핸들러가 연결되어 있다면 Activity 도 가비지 컬랙터가
 * 수집하지 못한다. 최악의 상황은 DeadLock 상태가 된다.
 * Activity 가 사라지면 이에 연결된 핸들러도 연결을 끊어 수집대상에 포함시킨다 
 * @author 2012/11/04 Jay
 *
 */
public class WeakHandler extends Handler {
	
	private final WeakReference<RefHandlerMessage> mHandlerActivity;
	
	public WeakHandler(RefHandlerMessage activity) {
		mHandlerActivity = new WeakReference<RefHandlerMessage>(activity);
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		RefHandlerMessage ref = (RefHandlerMessage)mHandlerActivity.get();
		
		if(ref == null) return;
		ref.handleMessage(msg);
	}

}
