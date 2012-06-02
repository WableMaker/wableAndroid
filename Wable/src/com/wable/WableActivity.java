package com.wable;

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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.facebook.android.Facebook;
import com.wable.adapter.CategoryElement;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.tab.login.PasswordFindActivity;
import com.wable.tab.login.RegisterActivity;
import com.wable.util.Logger;

public class WableActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private Context context;
	private Facebook facebook;
	private SharedPreferences pref;
	private Button loginOk;
	
	Map<Integer, HashMap<Integer, CategoryElement>> categoriesRequest;
	Map<Integer, HashMap<Integer, CategoryElement>> categoriesProvide;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);  
        context = this;
        
        findViewById(R.id.btnFacebook).setOnClickListener(this);
        findViewById(R.id.btnLoginFind).setOnClickListener(this);
        findViewById(R.id.btnLoginRegister).setOnClickListener(this);
        
        loginOk = (Button)findViewById(R.id.btnLogin);
        loginOk.setOnClickListener(this);
        
        EditText et = (EditText)findViewById(R.id.editLoginPass);
        et.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_DONE) loginOk.performClick();				
				return false;
			}
		});
        
      
        categoriesRequest = new HashMap<Integer, HashMap<Integer, CategoryElement>>();
        
        APIProxyLayer.Instance().CategoryList(new IAPIProxyCallback() {

        	@Override
        	public void OnCallback(boolean success, JSONObject json) {

        		if(success)
        		{
        			Logger.Instance().Write(json.toString());

        			try {

        				JSONObject obj = new JSONObject(json.getString("data"));
        				JSONArray request = obj.getJSONArray("request");
        				JSONArray provide = obj.getJSONArray("provide");
        				
        				List<CategoryElement> list = new ArrayList<CategoryElement>();
        				
        				 for(int i=0, m=request.length(); i<m; i++) {
        					 
        					 CategoryElement element = new CategoryElement();
        					 
        					 JSONObject o = request.getJSONObject(i);
        					 
        					 element.setId(o.getInt("id"))
        					 		.setTitle(o.getString("title"))
        					 		.setPhoto(o.getString("photo"))
        					 		.setPrice(o.getInt("price"))
        					 		.setDescription(o.getString("description"))
        					 		.setType(o.getInt("type"))
        					 		.setDue_time(o.getString("due_time"))
        					 		.setOrder(o.getInt("order"))
        					 		.setParent_id(o.getString("parent_id"));
        					 
        					 int p = 0;
        					 if(element.getParent_id() != "null") {
        						 p = Integer.parseInt(element.getParent_id());
        					 }
        					 
    						 if(!categoriesRequest.containsKey(p)) {
    							 categoriesRequest.put(p, new HashMap<Integer, CategoryElement>());
    						 } 
    						 categoriesRequest.get(p).put(element.getOrder(), element);       					 
        				 }
        				 
        				 Iterator<Integer> treeMapIter = categoriesRequest.keySet().iterator();
        				 
        				 String str ="";
        				 while( treeMapIter.hasNext()) {
        					 int key = treeMapIter.next();
        					 str += key + " / ";
        				 }
        				 
        				 TreeMap<Integer, HashMap<Integer, CategoryElement>> treeMap = new TreeMap( categoriesRequest );
        				 treeMapIter = treeMap.keySet().iterator();
        				 
        				 str ="";
        				 while( treeMapIter.hasNext()) {
        					 int key = treeMapIter.next();
        					 //String value = (String)treeMap.get( key );

        					 str += key + " / ";
        				 }


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




        			} catch (JSONException e) {
        				e.printStackTrace();
        			}	

        		}else 	Logger.Instance().Write("Fail to get Category");
        	}
        });
        

             
        APIProxyLayer.Instance().Login("cc", "111111", new IAPIProxyCallback(){
//
//			@Override
//			public void OnCallback(boolean success, JSONObject json) {
//				if(success)
//				{
//					Logger.Instance().Write(json.toString());

			@Override
			public void OnCallback(boolean success, JSONObject json) {
				if(success)
				{
					Logger.Instance().Write(json.toString());
					
					APIProxyLayer.Instance().MyInfo(new IAPIProxyCallback(){

						@Override
						public void OnCallback(boolean success, JSONObject json) {
							if(success)
							{
								Logger.Instance().Write(json.toString());
			
								
							}
							else Logger.Instance().Write("Fail to GetMyInfo");
						}
						
					});
					
					APIProxyLayer.Instance().MessageSendImage("-9223372036854775805", "/sdcard/koala.jpg", new IAPIProxyCallback(){
						@Override
						public void OnCallback(boolean success, JSONObject json) {
							if(success)
							{
								Logger.Instance().Write(json.toString());
								
							}
							else Logger.Instance().Write("Fail to MessageSendImage");
						}
					});
					
//					long dtMili = System.currentTimeMillis();
//					Date dt = new Date(dtMili);
//					
//					APIProxyLayer.Instance().MyInfo(new IAPIProxyCallback(){
//
//						@Override
//						public void OnCallback(boolean success, JSONObject json) {
//							if(success)
//							{
//								Logger.Instance().Write(json.toString());
//								try {
//									JSONObject data = new JSONObject(json.getString("data"));
//									String str = data.getString("name");
//									str = data.getString("email");
//									str = data.getString("phone");
//									str = data.getString("mobile");
//									
//								} catch (JSONException e) {
//									e.printStackTrace();
//								}
//								
//							}
//							else Logger.Instance().Write("Fail to GetMyInfo");
//						}
//						
//					});
//					
////					long dtMili = System.currentTimeMillis();
////					Date dt = new Date(dtMili);
////					
////					APIProxyLayer.Instance().AddRequest("android", "android", 123, 0, dt, 36, 127, false, false, false, new IAPIProxyCallback(){
////
////						@Override
////						public void OnCallback(boolean success, JSONObject json) {
////							// TODO Auto-generated method stub
////							if(success)
////							{
////								Logger.Instance().Write(json.toString());
////							}
////							else Logger.Instance().Write("Fail to AddRequest");
////						}
////						
////					});
//				}else 	Logger.Instance().Write("Fail to login");
//			}
//        	
//        });
    
				}
			}
	    });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

	@Override
	public void onClick(View v) {
		
		Intent intent;
		switch (v.getId()) {
		
		case R.id.btnFacebook:
			
//			facebook = new Facebook("180729825379631");
//	        
//	        pref = getPreferences(MODE_PRIVATE);
//	        String access_token = pref.getString("access_token", null);
//	        long expires = pref.getLong("access_expires", 0);
//	        if(access_token != null) {
//	            facebook.setAccessToken(access_token);
//	        }
//	        if(expires != 0) {
//	            facebook.setAccessExpires(expires);
//	        }
//
//	        
//	        
//			
//			facebook.authorize(WableActivity.this, new DialogListener() {
//		        @Override
//		        public void onComplete(Bundle values) {
//		        	
//		        	Editor editor = pref.edit();
//	                editor.putString("access_token", facebook.getAccessToken());
//	                editor.putLong("access_expires", facebook.getAccessExpires());
//	                editor.commit();
//		        }
//
//		        @Override
//		        public void onFacebookError(FacebookError error) {
//		        	
//		        }
//
//		        @Override
//		        public void onError(DialogError e) {
//		        	
//		        }
//
//		        @Override
//		        public void onCancel() {
//		        	
//		        }
//		    }); 
//			
//			
			break;
			
		case R.id.btnLoginFind:
			intent = new Intent(context, PasswordFindActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btnLogin:
			//Toast.makeText(context, "Login OK", Toast.LENGTH_SHORT).show();
			intent = new Intent(context, MainActivity.class);
			startActivity(intent);			
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finish();
			
			break;
			
		case R.id.btnLoginRegister:
			intent = new Intent(context, RegisterActivity.class);
			startActivity(intent);
			break;

		}
	}
}