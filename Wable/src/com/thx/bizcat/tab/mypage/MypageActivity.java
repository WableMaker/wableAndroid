package com.thx.bizcat.tab.mypage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.thx.bizcat.R;
import com.thx.bizcat.Variables;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_GetMyActiveProvides_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_GetMyActiveRequests_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_MyInfo_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_UserGetUpdatedContents_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedBiddings_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedMatch_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedProvides_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetMyUpdatedRequests_Result;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetNewMessage_Result;
import com.thx.bizcat.tab.mypage.item.MybizAdapter;
import com.thx.bizcat.tab.mypage.item.MybizElement;
import com.thx.bizcat.tab.post.GoBizActivity;
import com.thx.bizcat.tab.setting.SettingActivity;
import com.thx.bizcat.util.ImageDownloader;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.SqlManager;
import com.thx.bizcat.util.Utils;
import com.thx.bizcat.util.WeakHandler;

public class MypageActivity extends ActivityGroup  implements OnClickListener, RefHandlerMessage {

	private static Activity act;
	public static MypageActivity getInstance() {return (MypageActivity)act;}
	
	private Context context;
	private SharedPreferences pref;
	
	private View originView;
	private ListView listview;
	private MybizAdapter adapter;
	private List<MybizElement>[] arrays;
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
	public void handleMessage(Message msg) {   

		switch(APICODE.fromInt(msg.what)) {     

		case UserGetUpdatedContents:
		{
			
			sp_UserGetUpdatedContents_Items r =(sp_UserGetUpdatedContents_Items) msg.obj;
			if(r.bsuccess) {

				LAST_REQUEST = r.last_modified_time_request;
				LAST_PROVIDE = r.last_modified_time_provide;
				LAST_MATCH = r.last_modified_time_match;
				LAST_BIDDING = r.last_modified_time_bidding;
				LAST_BIDDINGMSG = r.last_modified_time_biddingmessage;
				
				Editor edit =  pref.edit();
				if(LAST_REQUEST != "")
				edit.putString("LAST_REQUEST", LAST_REQUEST);
				if(LAST_PROVIDE != "")
				edit.putString("LAST_PROVIDE", LAST_PROVIDE);
				if(LAST_MATCH != "")
				edit.putString("LAST_MATCH", LAST_MATCH);
				if(LAST_BIDDING != "")
				edit.putString("LAST_BIDDING", LAST_BIDDING);
				if(LAST_BIDDINGMSG != "")
				edit.putString("LAST_BIDDINGMSG", LAST_BIDDINGMSG);
				edit.commit();
				
				//SqlManager.Reset(context);
				
				if(r.newrequests != null)
				for(sp_GetMyUpdatedRequests_Result item : r.newrequests) {
					
					ContentValues values = new ContentValues();
					
					values.put("_id", item.id);	
					values.put("user_id", item.user_id);	
					values.put("title", item.title);
					values.put("description", item.description);	
					values.put("price", item.price);	
					values.put("category_id", item.category_id);
					values.put("due_date", item.due_date);	
					values.put("lat", item.lat);	
					values.put("lon", item.lon);
					values.put("totwitter", item.totwitter);	
					values.put("tofacebook", item.tofacebook);	
					values.put("status", item.status);
					values.put("receive_recommand", item.receive_recommend);	
					values.put("created_time", item.created_time);	
					values.put("deleted", item.deleted);
					values.put("modified_time", item.modified_time);
					
					SqlManager.ReplaceItem(context, "request", values);
					
				}
				
				if(r.newprovides != null)
				for(sp_GetMyUpdatedProvides_Result item : r.newprovides) {
					
					ContentValues values = new ContentValues();
					
					values.put("_id", item.id);
					values.put("user_id", item.user_id);
					values.put("title", item.title);
					values.put("min_price", item.min_price);
					values.put("lat", item.lat);
					values.put("lon", item.lon);
					values.put("radious", item.radius);
					values.put("status", item.status);
					values.put("created_time", item.created_time);
					values.put("descrition", item.description);
					values.put("photo1", item.photo1);
					values.put("photo2", item.photo2);
					values.put("photo3", item.photo3);
					values.put("photo4", item.photo4);
					values.put("photo5", item.photo5);
					values.put("deleted", item.deleted);
					values.put("modified_time", item.modified_time);
					values.put("totwitter", item.totwitter);
					values.put("tofacebook", item.tofacebook);
					
					SqlManager.ReplaceItem(context, "provide", values);
					
				}
				
				
				if(r.newmatches != null)
				for(sp_GetMyUpdatedMatch_Result item : r.newmatches) {
					
					ContentValues values = new ContentValues();
					
					values.put("request_id", item.request_id);
					values.put("provide_id", item.provide_id);
					values.put("matched_time", item.matched_time);
					values.put("status", item.status);
					values.put("recommend", item.recommend);
					values.put("other_user_id", item.other_user_id);
					values.put("other_title", item.other_title);
					values.put("other_description", item.other_description);
					values.put("other_price", item.other_price);
					values.put("other_user_photo", item.other_user_photo);
					values.put("other_user_name", item.other_user_name);
					values.put("deleted", item.deleted);
					values.put("modified_time", item.modified_time);
					
					SqlManager.InsertItem(context, "match", values);
					
				}
				
				if(r.newbiddings != null)
				for(sp_GetMyUpdatedBiddings_Result item : r.newbiddings) {
					
					ContentValues values = new ContentValues();
					
					values.put("bidding_id", item.bidding_id);
					values.put("requester_id", item.requester_id);
					values.put("provider_id", item.provider_id);
					values.put("request_id", item.request_id);
					values.put("provide_id", item.provide_id);
					values.put("request_price", item.request_price);
					values.put("provide_price", item.provide_price);
					values.put("created_time", item.created_time);
					values.put("settled_price", item.settled_price);
					values.put("status", item.status);
					values.put("requesteraccept", item.requesteraccept);
					values.put("provideraccept", item.provideraccept);
					values.put("requesterdelete", item.requesterdelete);
					values.put("providerdelete", item.providerdelete);
					values.put("approved_time", item.approved_time);
					values.put("completed_time", item.completed_time);
					values.put("requesteraccept_time", item.requesteraccept_time);
					values.put("provideraccept_time", item.provideraccept_time);
					values.put("modified_time", item.modified_time);
					values.put("other_user_name", item.other_user_name);
					values.put("other_title", item.other_title);
					values.put("other_description", item.other_description);
					values.put("other_price", item.other_price);
					values.put("other_user_photo", item.other_user_photo);
					values.put("provide_status", item.provide_status);
					values.put("provide_deleted", item.provide_deleted);
					values.put("request_status", item.request_status);
					values.put("request_deleted", item.request_deleted);
					
					SqlManager.InsertItem(context, "bidding", values);
					
				}
				
				if(r.newbiddingmessages != null)
				for(sp_GetNewMessage_Result item : r.newbiddingmessages) {
					
					ContentValues values = new ContentValues();
					
					values.put("bidding_id", item.bidding_id);
					values.put("writer_id", item.writer_id);
					values.put("msg", item.message);
					values.put("tick", item.written_tick);
					values.put("read_time", item.read_time);
					values.put("state", 0);
					values.put("audio", item.audio_path);
					values.put("video", item.video_path);
					values.put("picture", item.picture_path);
					
					SqlManager.InsertItem(context, "chat", values);
					
				}
				
				
				//findViewById(R.id.MYBIZbtnReqPost).performClick();
			}
		}
			
			break;
			
		case MyInfo:
			try
			{
				sp_MyInfo_Items r = (sp_MyInfo_Items)msg.obj;
				if(r.bsuccess){					
					
					String serverPath = Variables.BASE_SERVER__IMAGE_URL + r.result.photo;
					String localPath = Variables.BASE_STORAGE_PATH + "/user/" + r.result.photo;
					
					Bitmap bitmap = ImageDownloader.getInstance().imageViewProcessing(localPath, serverPath);
					Variables.USER_PICTURE = new BitmapDrawable(bitmap);
					adapter.notifyDataSetChanged();
				}
				else
					Toast.makeText(context, r.resultCode.toString() , Toast.LENGTH_LONG).show();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}		
			

			APIProxyLayer.Instance().UserGetUpdatedContents(
					LAST_REQUEST, LAST_PROVIDE, LAST_MATCH, LAST_BIDDING, LAST_BIDDINGMSG, "", "", mHandler);
			break;
			
		case USERSET1:
			
			Cursor c = SqlManager.getCursor(context, "SELECT * FROM request");
			
			while(c.moveToNext()) {
				
				//Status 0:대기중, 1:기한만료, 2:등록마
				MybizElement e = new MybizElement();
				
				e.setId(c.getLong(0)).setUser(c.getLong(1)).setCategory(c.getLong(5));
				e.setTitle(c.getString(2)).setDescription(c.getString(3));
				e.setPrice(c.getInt(4)).setLat(c.getInt(7)).setLon(c.getInt(8))
					.setStatus(c.getInt(11)).setRecommand(c.getInt(12));
				e.setDate(c.getString(6)).setCreated_time(c.getString(13));
				
				e.setTwitter(c.getInt(9) > 0 ? true : false).setFacebook(c.getInt(10) > 0 ? true : false);
				e.setDeleted(c.getInt(14) > 0 ? true : false);
				
				arrays[0].add(e);
				
			}
			SqlManager.Release(c);
			adapter.notifyDataSetChanged();
			
			findViewById(R.id.progressBar1).setVisibility(View.GONE);
			
			c = SqlManager.getCursor(context, "SELECT * FROM provide");
			
			while(c.moveToNext()) {
				
				//Status 0:대기중, 1:기한만료, 2:등록마
				MybizElement e = new MybizElement();
				
				e.setId(c.getLong(0)).setUser(c.getLong(1)).setCategory(c.getLong(5));
				e.setTitle(c.getString(2)).setDescription(c.getString(3));
				e.setPrice(c.getInt(4)).setLat(c.getInt(7)).setLon(c.getInt(8))
					.setStatus(c.getInt(11)).setRecommand(c.getInt(12));
				e.setDate(c.getString(6)).setCreated_time(c.getString(13));
				
				e.setTwitter(c.getInt(9) > 0 ? true : false).setFacebook(c.getInt(10) > 0 ? true : false);
				e.setDeleted(c.getInt(14) > 0 ? true : false);
				
				arrays[1].add(e);
				
			}
			SqlManager.Release(c);
			
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
		context = act = this;
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		findViewById(R.id.MYBIZbtnReqPost).setOnClickListener(this);
		findViewById(R.id.MYBIZbtnReqAsk).setOnClickListener(this);
		findViewById(R.id.MYBIZbtnProvPost).setOnClickListener(this);
		findViewById(R.id.MYBIZbtnProvAsk).setOnClickListener(this);
	
		listview = (ListView)findViewById(R.id.MYBIZcontainer);
		
		originView = findViewById(R.id.MYBIZbtnReqPost);
		originView.setSelected(true);
		
		LAST_REQUEST = pref.getString("LAST_REQUEST", "");
		LAST_PROVIDE = pref.getString("LAST_PROVIDE", "");
		LAST_MATCH = pref.getString("LAST_MATCH", "");
		LAST_BIDDING = pref.getString("LAST_BIDDING", "");
		LAST_BIDDINGMSG = pref.getString("LAST_BIDDINGMSG", "");

		arrays = new ArrayList[4];
		for(int i=0;i<arrays.length;i++)
			arrays[i] = new ArrayList<MybizElement>();
		
		adapter = new MybizAdapter(context, R.layout.mybiz_item, arrays);
		listview.setAdapter(adapter);
		
		mHandler.sendEmptyMessage(9000);
	}
	
	public void startView() {
		
		mHandler.postDelayed(new Runnable() {
			public void run() {
				APIProxyLayer.Instance().MyInfo(mHandler);
				
			}
		}, 300);
		
		
	}

	@Override
	public void onClick(View v) {
		
		if(originView.getId() == v.getId()) return;
		
		switch (v.getId()) {
		
		case R.id.MYBIZbtnReqPost:		adapter.setMode(0);	break;
			
		case R.id.MYBIZbtnReqAsk:		adapter.setMode(1);	break;
			
		case R.id.MYBIZbtnProvPost:		adapter.setMode(2);	break;
			
		case R.id.MYBIZbtnProvAsk:		adapter.setMode(3);	break;

		}
		originView.setSelected(false);
		v.setSelected(true);
		originView = v;
		
	}
	
	
	
}
