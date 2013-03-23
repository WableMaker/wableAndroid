package com.thx.bizcat.tab.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class ProviderAskActivity extends Activity implements OnClickListener, RefHandlerMessage {

	private Context context;
	private SharedPreferences pref;
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
	public void handleMessage(Message msg) {   

		switch(APICODE.fromInt(msg.what)) {     

		default:
			break;
		}
	}; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybiz_prov_ask);
		context = this;
		
		/* 요청목록 */
		//String lastid = pref.getString("LAST_ID", "");
		//APIProxyLayer.Instance().RequestMyActiveList(lastid, mHandler);
				
		/* 제공목록 */
		//APIProxyLayer.Instance().ProvideMyActiveList(lastid, mHandler); 
					

	}

	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		

		}
	}
}
