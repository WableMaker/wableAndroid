package com.wable;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.android.Facebook;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.tab.login.PasswordFindActivity;
import com.wable.tab.login.RegisterActivity;
import com.wable.util.Logger;

public class WableActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private Context context;
	private Facebook facebook;
	private Button loginOk;
	
	private EditText etUser, etPass, etUp;
	private boolean isWork;
	
	private ProgressDialog pd;
	//private SharedPreferences pref;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);  
        context = this;        
        
//        pref = PreferenceManager.getDefaultSharedPreferences(this);
//
//        Editor edit = pref.edit();
//        edit.putBoolean("categoryUpdate", true);
//        edit.commit();		
//
//        String path = context.getFilesDir().getAbsolutePath() + "/cate";
//        Utils.DeleteFolder(path);        
//        File file = new File(path);
//        file.mkdir();

        findViewById(R.id.LOGINbtnFb).setOnClickListener(this);
        findViewById(R.id.LOGINbtnRegister).setOnClickListener(this);
        findViewById(R.id.LOGINbtnFind).setOnClickListener(this);
        
        loginOk = (Button)findViewById(R.id.LOGINbtnLogin);
        etUser = (EditText)findViewById(R.id.LOGINeditId);
        etPass = (EditText)findViewById(R.id.LOGINeditPass);
        etUp = (EditText)findViewById(R.id.LOGINeditUp);        
        
        loginOk.setOnClickListener(this);
        etUser.setOnClickListener(this);
        etPass.setOnClickListener(this);
        
        isWork = false;
        
        etUser.setOnFocusChangeListener(onFocusChangeListner);
        etPass.setOnFocusChangeListener(onFocusChangeListner);
        
       

              
        APIProxyLayer.Instance().Login("cc", "111111", new IAPIProxyCallback(){

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
				}else 	Logger.Instance().Write("Fail to login");
			}
        	
        });
    
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

	@Override
	public void onClick(final View v) {
		
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
		
			pd = ProgressDialog.show(context, "로그인", "정보 확인중입니다.", true, false);
			
			APIProxyLayer.Instance().Login("cc", "111111", new IAPIProxyCallback(){

				@Override
				public void OnCallback(boolean success, JSONObject json) {
					
					pd.dismiss();
					
					if(success)
					{
						//Logger.Instance().Write(json.toString());
						Intent intent = new Intent(context, MainActivity.class);
						startActivity(intent);			
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						finish();


//						APIProxyLayer.Instance().MyInfo(new IAPIProxyCallback(){
//
//							@Override
//							public void OnCallback(boolean success, JSONObject json) {
//								if(success)
//								{
//									//Logger.Instance().Write(json.toString());
//
//
//								}
//								else 
//									Toast.makeText(context, "로그인 정보를 확인하세요", Toast.LENGTH_SHORT).show();
//								//Logger.Instance().Write("Fail to GetMyInfo");
//							}
//
//						});

					}
				}
			});
			
			//Toast.makeText(context, "Login OK", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.LOGINeditId:
		case R.id.LOGINeditPass:
			
			if(v.isFocused()) {
				isWork = true;
				etUp.requestFocus();
				etUp.postDelayed(new Runnable() {
					@Override
					public void run() { 
						v.requestFocus();
						isWork = false;
					}
				}, 200);
			}
			else 
				v.requestFocus();
			
			break;

		}
	}
	
	private OnFocusChangeListener onFocusChangeListner = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(final View v, boolean hasFocus) {
			
			if(hasFocus && !isWork) {					

    			Rect rect = new Rect();         
    			getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

    			int layoutHeight = rect.height();
    			int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
    			int diff = screenHeight - layoutHeight;

    			if(diff < 100) {

    				isWork = true;
    				v.postDelayed(new Runnable() {
    					@Override
    					public void run() {
    						etUp.requestFocus();
    						etUp.postDelayed(new Runnable() {
    							@Override
    							public void run() { 
    								v.requestFocus();
    								isWork = false;
    							}
    						}, 200);
    					}
    				}, 20);
    			}
    		}
			
		}
	};
	
	
	

}