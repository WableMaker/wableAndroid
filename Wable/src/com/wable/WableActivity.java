package com.wable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.android.Facebook;
import com.wable.tab.login.PasswordFindActivity;
import com.wable.tab.login.RegisterActivity;
import com.wable.util.KeyboardDetecter;

public class WableActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private Context context;
	private Facebook facebook;
	private SharedPreferences pref;
	private Button loginOk;
	private RelativeLayout layout;
	
	private EditText etUser, etPass, etUp;
	
	private boolean isUp, isWork;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);  
        context = this;        
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        
//        Editor edit = pref.edit();
//		edit.putBoolean("categoryUpdate", true);
//		edit.commit();		
        
//        String path = context.getFilesDir().getAbsolutePath() + "/cate";
//        Utils.DeleteFolder(path);        
//        File file = new File(path);
//        file.mkdir();
        
        layout = (RelativeLayout)findViewById(R.id.RelativeLayout1);
        
        findViewById(R.id.LOGINbtnFb).setOnClickListener(this);
        findViewById(R.id.LOGINbtnRegister).setOnClickListener(this);
        findViewById(R.id.LOGINbtnFind).setOnClickListener(this);
        
        loginOk = (Button)findViewById(R.id.LOGINbtnLogin);
        etUser = (EditText)findViewById(R.id.LOGINeditId);
        etPass = (EditText)findViewById(R.id.LOGINeditPass);
        etUp = (EditText)findViewById(R.id.LOGINeditUp);        
        loginOk.setOnClickListener(this);
        
        isUp = isWork = false;
        
        etUser.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
			
				if(arg1 && !isWork) {					
		  
					Rect rect = new Rect();         
					getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
					
					int layoutHeight = rect.height();
					int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
					int diff = screenHeight - layoutHeight;

					if(diff < 100) {
						
						isWork = true;
						etUser.postDelayed(new Runnable() {
			    			@Override
			    			public void run() {
			    				etUp.requestFocus();
			    				etUp.postDelayed(new Runnable() {
			    					@Override
			    					public void run() { 
			    						etUser.requestFocus();
			    						isWork = false;
			    						}
			    					}, 200);
			    			}
			    		}, 100);
					}
				}
			}
		});
        
        etPass.setOnFocusChangeListener(new OnFocusChangeListener() {

        	@Override
        	public void onFocusChange(View arg0, boolean arg1) {

        		if(arg1 && !isWork) {					

        			Rect rect = new Rect();         
        			getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        			int layoutHeight = rect.height();
        			int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        			int diff = screenHeight - layoutHeight;

        			if(diff < 100) {

        				isWork = true;
        				etPass.postDelayed(new Runnable() {
        					@Override
        					public void run() {
        						etUp.requestFocus();
        						etUp.postDelayed(new Runnable() {
        							@Override
        							public void run() { 
        								etPass.requestFocus();
        								isWork = false;
        							}
        						}, 200);
        					}
        				}, 100);
        			}
        		}
        	}
        });
        
        etUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(etUser.isFocused()) {
					isWork = true;
					etUp.requestFocus();
					etUp.postDelayed(new Runnable() {
    					@Override
    					public void run() { 
    						etUser.requestFocus();
    						isWork = false;
    						}
    					}, 200);
				}
				else 
					etUser.requestFocus();
			}
		});
        
        etPass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(etPass.isFocused()) {
					isWork = true;
					etUp.requestFocus();
					etUp.postDelayed(new Runnable() {
    					@Override
    					public void run() { 
    						etPass.requestFocus();
    						isWork = false;
    						}
    					}, 200);
				}
				else 
					etUser.requestFocus();
			}
		});
        
//        etPass = (EditText)findViewById(R.id.editLoginPass);
//        etPass.setOnEditorActionListener(new OnEditorActionListener() {
//
//              
//        APIProxyLayer.Instance().Login("cc", "111111", new IAPIProxyCallback(){
//
//			@Override
//			public void OnCallback(boolean success, JSONObject json) {
//				if(success)
//				{
//					Logger.Instance().Write(json.toString());
//					
//					
//					APIProxyLayer.Instance().MyInfo(new IAPIProxyCallback(){
//
//						@Override
//						public void OnCallback(boolean success, JSONObject json) {
//							if(success)
//							{
//								Logger.Instance().Write(json.toString());
//			
//								
//							}
//							else Logger.Instance().Write("Fail to GetMyInfo");
//						}
//						
//					});
//					
//					APIProxyLayer.Instance().RequestMyActiveList(null,new IAPIProxyCallback(){
//						@Override
//						public void OnCallback(boolean success, JSONObject json) {
//							if(success)
//							{
//								Logger.Instance().Write(json.toString());
//								
//							}
//							else Logger.Instance().Write("Fail to MessageSendImage");
//						}
//					});
//					
//					
//					APIProxyLayer.Instance().MessageSendImage("-9223372036854775805", "/sdcard/koala.jpg",null, new IAPIProxyCallback(){
//						@Override
//						public void OnCallback(boolean success, JSONObject json) {
//							if(success)
//							{
//								Logger.Instance().Write(json.toString());
//								
//							}
//							else Logger.Instance().Write("Fail to MessageSendImage");
//						}
//					});
//					
//					APIProxyLayer.Instance().UserUpdate("123","123123", "/sdcard/koala.jpg", new IAPIProxyCallback(){
//						@Override
//						public void OnCallback(boolean success, JSONObject json) {
//							if(success)
//							{
//								Logger.Instance().Write(json.toString());
//								
//							}
//							else Logger.Instance().Write("Fail to UserUpdate");
//						}
//					});
//					
//				
//				}else 	Logger.Instance().Write("Fail to login");
//			}
//        	
//        });
    
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
		
		case R.id.LOGINbtnFb:
			
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
			
		case R.id.LOGINbtnRegister:
			intent = new Intent(context, RegisterActivity.class);
			startActivity(intent);
			break;
			
			
		case R.id.LOGINbtnFind:
			intent = new Intent(context, PasswordFindActivity.class);
			startActivity(intent);
			break;
			
		case R.id.LOGINbtnLogin:
			
			//Toast.makeText(context, "Login OK", Toast.LENGTH_SHORT).show();
			intent = new Intent(context, MainActivity.class);
			startActivity(intent);			
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finish();
			break;
			
//		case R.id.LOGINeditId:
//		case R.id.LOGINeditPass:
//			
//			etUser.postDelayed(new Runnable() {
//    			@Override
//    			public void run() {
//    				etUp.requestFocus();
//    				etUp.postDelayed(new Runnable() {
//    					@Override
//    					public void run() { etUser.requestFocus();}}, 200);
//    			}
//    		}, 100);
//			
//			break;

		}
	}
	

}