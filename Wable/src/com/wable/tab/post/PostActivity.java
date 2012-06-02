package com.wable.tab.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.wable.MainActivity;
import com.wable.R;
import com.wable.adapter.CategoryElement;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.util.Logger;

public class PostActivity extends Activity implements OnClickListener {

	
	Map<Integer, HashMap<Integer, CategoryElement>> categoriesRequest;
	Map<Integer, HashMap<Integer, CategoryElement>> categoriesProvide;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main);
		
		findViewById(R.id.btnPostRequest).setOnClickListener(this);
		
		
		
		
    categoriesRequest = new HashMap<Integer, HashMap<Integer, CategoryElement>>();
        
//        APIProxyLayer.Instance().CategoryList(new IAPIProxyCallback() {
//
//        	@Override
//        	public void OnCallback(boolean success, JSONObject json) {
//
//        		if(success)
//        		{
//        			Logger.Instance().Write(json.toString());
//
//        			try {
//
//        				JSONObject obj = new JSONObject(json.getString("data"));
//        				JSONArray request = obj.getJSONArray("request");
//        				JSONArray provide = obj.getJSONArray("provide");
//        				
//        				List<CategoryElement> list = new ArrayList<CategoryElement>();
//        				
//        				 for(int i=0, m=request.length(); i<m; i++) {
//        					 
//        					 CategoryElement element = new CategoryElement();
//        					 
//        					 JSONObject o = request.getJSONObject(i);
//        					 
//        					 element.setId(o.getInt("id"))
//        					 		.setTitle(o.getString("title"))
//        					 		.setPhoto(o.getString("photo"))
//        					 		.setPrice(o.getInt("price"))
//        					 		.setDescription(o.getString("description"))
//        					 		.setType(o.getInt("type"))
//        					 		.setDue_time(o.getString("due_time"))
//        					 		.setOrder(o.getInt("order"))
//        					 		.setParent_id(o.getString("parent_id"));
//        					 
//        					 int p = 0;
//        					 if(element.getParent_id() != "null") {
//        						 p = Integer.parseInt(element.getParent_id());
//        					 }
//        					 
//    						 if(!categoriesRequest.containsKey(p)) {
//    							 categoriesRequest.put(p, new HashMap<Integer, CategoryElement>());
//    						 } 
//    						 categoriesRequest.get(p).put(element.getOrder(), element);       					 
//        				 }
//        				 
//        				 Iterator<Integer> treeMapIter = categoriesRequest.keySet().iterator();
//        				 
//        				 String str ="";
//        				 while( treeMapIter.hasNext()) {
//        					 int key = treeMapIter.next();
//        					 str += key + " / ";
//        				 }
//        				 
//        				 TreeMap<Integer, HashMap<Integer, CategoryElement>> treeMap = new TreeMap( categoriesRequest );
//        				 treeMapIter = treeMap.keySet().iterator();
//        				 
//        				 str ="";
//        				 while( treeMapIter.hasNext()) {
//        					 int key = treeMapIter.next();
//        					 //String value = (String)treeMap.get( key );
//
//        					 str += key + " / ";
//        				 }


//        				for(int i=0, m=arr.length(); i<m; i++ ) {
//
//        					JSONObject data = arr.getJSONObject(i);
//
//        					String str = data.getString("id");
//        					str = data.getString("title");
//        					str = data.getString("description");
//        					str = data.getString("price");
//        					str = data.getString("photo");
//        					str = data.getString("type");
//        					str = data.getString("order");
//
//        				}




//        			} catch (JSONException e) {
//        				e.printStackTrace();
//        			}	
//
//        		}else 	Logger.Instance().Write("Fail to get Category");
//        	}
//        });
		
		
		
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btnPostRequest:
			
			MainActivity activity = (MainActivity)getParent();
			//activity.hideBottomTab();			
			
			intent = new Intent(this, RequestPostSubmit.class);
			startActivity(intent);
			break;

		case R.id.btnPostOffer:
			
			break;
		}
		
	}
}
