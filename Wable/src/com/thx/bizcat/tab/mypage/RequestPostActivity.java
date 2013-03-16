package com.thx.bizcat.tab.mypage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ListView;

import com.thx.bizcat.R;
import com.thx.bizcat.Variables;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.tab.mypage.item.MybizAdapter;
import com.thx.bizcat.tab.mypage.item.MybizElement;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.SqlManager;
import com.thx.bizcat.util.WeakHandler;

public class RequestPostActivity extends Activity implements OnClickListener, RefHandlerMessage {

	private Context context;
	private SharedPreferences pref;
	
	private ListView listview;
	private MybizAdapter adapter;
	
	private ProgressDialog pd;
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
	public void handleMessage(Message msg) {   

		switch(msg.what) { 
		
		case 0:
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
				
				
			}
			SqlManager.Release(c);
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybiz_req_post);
		context = this;
		
		findViewById(R.id.GOBIZ_REQtvTitle).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvDesc).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvPrice).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvTime).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvMapBtn).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvGoBtn).setOnClickListener(this);
		
//		listview = (ListView)findViewById(R.id.listView1);
//		adapter = new MybizAdapter(context, R.layout.mybiz_item, Variables.REQPOST, 0);
//		listview.setAdapter(adapter);
//		
//		mHandler.sendEmptyMessage(0);

	}

	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		
		case R.id.GOBIZ_REQtvTitle:
			break;
			
		case R.id.GOBIZ_REQtvDesc:
			break;
			
		case R.id.GOBIZ_REQtvPrice:
			break;
			
		case R.id.GOBIZ_REQtvTime:
			break;
			
		case R.id.GOBIZ_REQtvMapBtn:
			break;
			
		case R.id.GOBIZ_REQtvGoBtn:
			break;
		

		}
	}
}
