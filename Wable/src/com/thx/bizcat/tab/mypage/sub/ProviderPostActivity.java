package com.thx.bizcat.tab.mypage.sub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.tab.post.PostActivity;
import com.thx.bizcat.tab.search.SearchActivity;
import com.thx.bizcat.tab.setting.SettingActivity;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class ProviderPostActivity extends Activity implements OnClickListener, RefHandlerMessage {

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
		setContentView(R.layout.mybiz_prov_post);
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
