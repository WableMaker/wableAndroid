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
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_GetMyActiveProvides_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_GetMyActiveRequests_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_UserGetUpdatedContents_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedBiddings_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedMatch_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedRequests_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetNewMessage_Result;
import com.thx.bizcat.tab.mypage.sub.ProviderAskActivity;
import com.thx.bizcat.tab.mypage.sub.ProviderPostActivity;
import com.thx.bizcat.tab.mypage.sub.RequestAskActivity;
import com.thx.bizcat.tab.mypage.sub.RequestPostActivity;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.SqlManager;
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
			sp_GetMyActiveProvides_Items r =(sp_GetMyActiveProvides_Items) msg.obj;
			if(r.bsuccess) {

				ArrayList<ProviderAdapterItem> list = new ArrayList<ProviderAdapterItem>();
				ProviderAdapter providerAdapter = 
						new ProviderAdapter(context, R.layout.mypage_provider_item, list);
				//listRequest.setAdapter(providerAdapter);
			}
		}
		break;

		case UserGetUpdatedContents:
		{
			sp_UserGetUpdatedContents_Items r =(sp_UserGetUpdatedContents_Items) msg.obj;
			if(r.bsuccess) {

				LAST_REQUEST = r.last_modified_time_request;
				LAST_PROVIDE = r.last_modified_time_provide;
				LAST_MATCH = r.last_modified_time_match;
				LAST_BIDDING = r.last_modified_time_bidding;
				LAST_BIDDINGMSG = r.last_modified_time_biddingmessage;
				
				//Editor edit =  pref.edit();
				//edit.putString("LAST_REQUEST", LAST_REQUEST);
				//edit.putString("LAST_PROVIDE", LAST_PROVIDE);
				//edit.putString("LAST_MATCH", LAST_MATCH);
				//edit.putString("LAST_BIDDING", LAST_BIDDING);
				//edit.putString("LAST_BIDDINGMSG", LAST_BIDDINGMSG);
				//edit.commit();
				
				SqlManager.Reset(context);
				
				if(r.newrequests != null)
				for(sp_GetMyUpdatedRequests_Result item : r.newrequests) {
					
					String sql = String.format("REPLACE INTO request (_id, user_id,title,description,price,category_id,due_date,lat,lon" +
							",totwitter,tofacebook,status,receive_recommand,created_time,deleted,modified_time) values " +
							"(%d,%d, '%s','%s', %d,%d, '%s',%f,%f,%d,%d,%d,%d,'%s',%d,'%s')"
							, item.id, item.user_id, item.title, item.description, item.price, item.category_id, item.due_date
							,item.lat, item.lon, item.totwitter ?1:0, item.tofacebook?1:0, item.status, item.receive_recommend?1:0, item.created_time
							,item.deleted?1:0, item.modified_time);
					
					SqlManager.excuteSql(context, sql);
					
				}
				
				if(r.newprovides != null)
				for(sp_GetMyUpdatedProvides_Result item : r.newprovides) {
					
					String sql = String.format("REPLACE INTO provide values " +
							"(%d,%d, '%s',%d, %f,%f, %d,%d, '%s','%s','%s','%s','%s','%s','%s',%d,'%s',%d,%d)"
							, item.id, item.user_id, item.title, item.min_price, item.lat, item.lon, item.radius,
							item.status, item.created_time, item.description, item.photo1, item.photo2,item.photo3,
							item.photo4,item.photo5, item.deleted?1:0,item.modified_time,item.totwitter?1:0,item.tofacebook?1:0);
					
					SqlManager.excuteSql(context, sql);
					
				}
				
				
				if(r.newmatches != null)
				for(sp_GetMyUpdatedMatch_Result item : r.newmatches) {
					
					String sql = String.format("REPLACE INTO match values " +
							"(%d,%d, '%s',%d,%d,%d, '%s','%s',%d, '%s','%s',%d,'%s')"
							, item.request_id, item.provide_id, item.matched_time, item.status, item.recommend?1:0,item.other_user_id,
							  item.other_title, item.other_description,item.other_price,item.other_user_photo, item.other_user_name,
							  item.deleted?1:0,item.modified_time);
					
					SqlManager.excuteSql(context, sql);
					
				}
				
				if(r.newbiddings != null)
				for(sp_GetMyUpdatedBiddings_Result item : r.newbiddings) {
					
					String sql = String.format("REPLACE INTO bidding values " +
							"(%d,%d,%d,%d,%d, %d,%d,'%s',%d,%d, %d,%d,%d,%d, '%s','%s','%s','%s','%s','%s','%s','%s',%d,'%s',%d,%d,%d,%d)"
							, item.requester_id, item.provider_id, item.bidding_id, item.request_id, item.provide_id, item.request_price,
							item.provide_price, item.completed_time, item.settled_price, item.status, item.requesteraccept?1:0, item.provideraccept?1:0,
							item.request_deleted?1:0,item.providerdelete?1:0,item.approved_time, item.completed_time,item.requesteraccept_time,
							item.provideraccept_time,item.modified_time,item.other_user_name,item.other_title,item.other_description,
							item.other_price,item.other_user_photo,item.provide_status,item.provide_deleted?1:0,item.request_status, 
									item.request_deleted?1:0);
					
					SqlManager.excuteSql(context, sql);
					
				}
				
				if(r.newbiddingmessages != null)
				for(sp_GetNewMessage_Result item : r.newbiddingmessages) {
					
					String sql = String.format("REPLACE INTO chat values " +
							"(%d,%d, '%s','%s',%d,%d, '%s','%s','%s')"
							, item.bidding_id,item.writer_id,item.message,item.written_tick,item.read_time, "state",item.audio_path
							,item.video_path,item.picture_path);
					
					SqlManager.excuteSql(context, sql);
				}


				
				
				
				
				
			}
		}
			
			
			break;
		default:
			break;
		}
	}; 
	
	
	String LAST_REQUEST = ""; 
	String LAST_PROVIDE = "";
	String LAST_MATCH = "";
	String LAST_BIDDING = "";
	String LAST_BIDDINGMSG = "";

	
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
		
		//LAST_REQUEST = pref.getString("LAST_REQUEST", "");
		LAST_PROVIDE = pref.getString("LAST_PROVIDE", "");
		LAST_MATCH = pref.getString("LAST_MATCH", "");
		LAST_BIDDING = pref.getString("LAST_BIDDING", "");
		LAST_BIDDINGMSG = pref.getString("LAST_BIDDINGMSG", "");

		
		//APIProxyLayer.Instance().UserGetUpdatedContents(
		//		LAST_REQUEST, LAST_PROVIDE, LAST_MATCH, LAST_BIDDING, LAST_BIDDINGMSG, "", "", mHandler);
		

		
		
		
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
