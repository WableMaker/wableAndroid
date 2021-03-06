package com.thx.bizcat.tab.gobiz;

import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;

import com.thx.bizcat.R;
import com.thx.bizcat.adapter.CategoryElement;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;


public class GoBizActivity extends Activity implements OnClickListener, RefHandlerMessage {

	Context context;
	SharedPreferences  pref;
	public static Map<Integer, TreeMap<Integer, CategoryElement>> categoriesRequest;
	public static Map<Integer, TreeMap<Integer, CategoryElement>> categoriesProvide;
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
	public void handleMessage(Message msg) {
		
		switch(APICODE.fromInt(msg.what)) {

		case CategoryList:
				
//				if(success)
//				{
//					Logger.Instance().Write(json.toString());
//
//					categoriesRequest = new HashMap<Integer, TreeMap<Integer, CategoryElement>>();
//					categoriesProvide = new HashMap<Integer, TreeMap<Integer, CategoryElement>>();
//
//					try {
//
//						JSONObject obj = new JSONObject(json.getString("data"));
//						JSONArray request = obj.getJSONArray("request");
//						JSONArray provide = obj.getJSONArray("provide");
//
//						//��û ī�װ?
//						for(int i=0, m=request.length(); i<m; i++) {
//
//							CategoryElement element = new CategoryElement();
//
//							JSONObject o = request.getJSONObject(i);
//
//							element.setId(o.getInt("id"))
//							.setTitle(o.getString("title"))
//							.setPhoto(o.getString("photo"))
//							.setPrice(o.getInt("price"))
//							.setDescription(o.getString("description"))
//							.setType(o.getInt("type"))
//							.setDue_time(o.getString("due_time"))
//							.setOrder(o.getInt("order"))
//							.setParent_id(o.getString("parent_id"));
//
//							int p = 0;
//							if(element.getParent_id() != "null") {
//								p = Integer.parseInt(element.getParent_id());
//							}
//
//							if(!categoriesRequest.containsKey(p)) {
//								categoriesRequest.put(p, new TreeMap<Integer, CategoryElement>());
//							} 
//							categoriesRequest.get(p).put(element.getOrder(), element);       					 
//						}
//
//						//���� ī�װ?
//						for(int i=0, m=provide.length(); i<m; i++) {
//
//							CategoryElement element = new CategoryElement();
//
//							JSONObject o = provide.getJSONObject(i);
//
//							element.setId(o.getInt("id"))
//							.setTitle(o.getString("title"))
//							.setPhoto(o.getString("photo"))
//							.setPrice(o.getInt("price"))
//							.setDescription(o.getString("description"))
//							.setType(o.getInt("type"))
//							.setDue_time(o.getString("due_time"))
//							.setOrder(o.getInt("order"))
//							.setParent_id(o.getString("parent_id"));
//
//							int p = 0;
//							if(element.getParent_id() != "null") {
//								p = Integer.parseInt(element.getParent_id());
//							}
//
//							if(!categoriesProvide.containsKey(p)) {
//								categoriesProvide.put(p, new TreeMap<Integer, CategoryElement>());
//							} 
//							categoriesProvide.get(p).put(element.getOrder(), element);       					 
//						}
//
//						//����ȭ ����
//						String path = context.getFilesDir().getAbsolutePath();
//						Serializes.writeObject(path + "/r.ser", categoriesRequest);
//						Serializes.writeObject(path + "/p.ser", categoriesProvide);
//
//						Editor edit = pref.edit();
//						edit.putBoolean("categoryUpdate", false);
//						edit.commit();
//
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}	
//
//				} else 	Logger.Instance().Write("Fail to get Category");
//			}
//		});
//
//	} else {
//
//		String path = context.getFilesDir().getAbsolutePath();
//		categoriesRequest = (HashMap<Integer, TreeMap<Integer, CategoryElement>>) 
//				Serializes.readObject(path + "/r.ser");
//
//		categoriesProvide = (HashMap<Integer, TreeMap<Integer, CategoryElement>>) 
//				Serializes.readObject(path + "/p.ser");
//	}

			break;


		default:
			break;

		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.go_biz_main);
		context = this;
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		pref.edit().remove("TEMP_ISUSE").commit();
		
		findViewById(R.id.GOBIZtvPostReq).setOnClickListener(this);
		findViewById(R.id.GOBIZtvPostPro).setOnClickListener(this);
		findViewById(R.id.GOBIZtvSearchReq).setOnClickListener(this);
		findViewById(R.id.GOBIZtvSearchPro).setOnClickListener(this);

//		if(pref.getBoolean("categoryUpdate", true)) {
//			APIProxyLayer.Instance().CategoryList(mHandler);
//		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.GOBIZtvPostReq:
			intent = new Intent(this, PostRequestActivity.class);
			startActivity(intent);
			break;

		case R.id.GOBIZtvPostPro:
			//intent = new Intent(this, PostProvide.class);
			//startActivity(intent);
			break;

		case R.id.GOBIZtvSearchReq:

			break;

		case R.id.GOBIZtvSearchPro:

			break;
		}
		
	}
	
}
