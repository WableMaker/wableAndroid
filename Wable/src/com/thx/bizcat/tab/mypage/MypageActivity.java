package com.thx.bizcat.tab.mypage;

import java.util.ArrayList;

import android.app.ActivityGroup;
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
import com.thx.bizcat.http.apiproxy.JSONParser.sp_GetMyActiveRequests_Items;
import com.thx.bizcat.tab.mypage.sub.ProviderAskActivity;
import com.thx.bizcat.tab.mypage.sub.ProviderPostActivity;
import com.thx.bizcat.tab.mypage.sub.RequestAskActivity;
import com.thx.bizcat.tab.mypage.sub.RequestPostActivity;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class MypageActivity extends ActivityGroup  implements OnClickListener, RefHandlerMessage {

	private Context context;
	
	private View originView;
	private View[] views;
	private LinearLayout container;
	
	private SharedPreferences pref;
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
	public void handleMessage(Message msg) {   

		switch(APICODE.fromInt(msg.what)) {     

		case RequestMyActiveList:
		{
			sp_GetMyActiveRequests_Items r =(sp_GetMyActiveRequests_Items) msg.obj;
			if(r.bsuccess) {

				ArrayList<RequestAdapterItem> list = new ArrayList<RequestAdapterItem>();
				RequestAdapter requestAdapter = 
						new RequestAdapter(context, R.layout.mypage_request_item, list);
				//listRequest.setAdapter(requestAdapter);
			}
		}
		break;

		case ProvideMyActiveList:
		{
			sp_GetMyActiveRequests_Items r =(sp_GetMyActiveRequests_Items) msg.obj;
			if(r.bsuccess) {

				ArrayList<ProviderAdapterItem> list = new ArrayList<ProviderAdapterItem>();
				ProviderAdapter providerAdapter = 
						new ProviderAdapter(context, R.layout.mypage_provider_item, list);
				//listRequest.setAdapter(providerAdapter);
			}
		}
		break;

		default:
			break;
		}
	}; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybiz_main);
		context = this;
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		container = (LinearLayout)findViewById(R.id.MYBIZContainer);
		findViewById(R.id.MYBIZbtnReqPost).setOnClickListener(this);
		findViewById(R.id.MYBIZbtnReqAsk).setOnClickListener(this);
		findViewById(R.id.MYBIZbtnProvPost).setOnClickListener(this);
		findViewById(R.id.MYBIZbtnProvAsk).setOnClickListener(this);
		
		views = new View[4];
		views[0] = getLocalActivityManager().startActivity("REQ_POST", new Intent(this, RequestPostActivity.class)).getDecorView();
		views[1] = getLocalActivityManager().startActivity("REQ_ASK", new Intent(this, RequestAskActivity.class)).getDecorView();
		views[2] = getLocalActivityManager().startActivity("PROV_POST", new Intent(this, ProviderPostActivity.class)).getDecorView();
		views[3] = getLocalActivityManager().startActivity("PROV_ASK", new Intent(this, ProviderAskActivity.class)).getDecorView();
		originView = container;
		
	}

	@Override
	public void onClick(View v) {
		
		if(originView.getId() == v.getId()) return;	
		
		container.removeAllViews();	
		originView.setBackgroundResource(0);
		
		switch (v.getId()) {
		
		case R.id.MYBIZbtnReqPost:
			container.addView(views[0]);
			v.setBackgroundResource(R.drawable.biz_request_post_btn_selected);
			break;
			
		case R.id.MYBIZbtnReqAsk:
			container.addView(views[1]);	
			v.setBackgroundResource(R.drawable.biz_request_ask_btn_seleted);
			break;
			
		case R.id.MYBIZbtnProvPost:
			container.addView(views[2]);
			v.setBackgroundResource(R.drawable.biz_provide_post_btn_seleted);
			break;
			
		case R.id.MYBIZbtnProvAsk:
			container.addView(views[3]);
			v.setBackgroundResource(R.drawable.biz_provide_ask_btn_seleted);
			break;

		}
		
		originView = v;
	}
}
