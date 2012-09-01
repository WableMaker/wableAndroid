package com.wable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.http.apiproxy.JSONParser.JSONParser;
//import com.wable.http.apiproxy.JSONParser.sp_GetRequestsByTime_Item;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetMyActiveRequests_Result;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetRequestsByDistance_Result;
import com.wable.http.apiproxy.JSONParser.Result.sp_GetRequestsByTime_Result;
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
        
//        etPass = (EditText)findViewById(R.id.editLoginPass);
//        etPass.setOnEditorActionListener(new OnEditorActionListener() {
//
//    
       /*   // JSON Parser TEST code
       APIProxyLayer.Instance().Login("cc", "111111", new IAPIProxyCallback(){

			@Override
			public void OnCallback(boolean success, JSONObject json) {
				if(success)
				{
					Logger.Instance().Write(json.toString());
					
					//APIProxyLayer.Instance().RequestListbyTime(null, null,new IAPIProxyCallback()
					//APIProxyLayer.Instance().RequestListbyArea(0,0,0,0,null,new IAPIProxyCallback()
					APIProxyLayer.Instance().RequestMyActiveList(null,new IAPIProxyCallback()
					{
						@Override
						public void OnCallback(boolean success, JSONObject json) {
							if(success)
							{
								sp_GetMyActiveRequests_Result result=  JSONParser.RequestMyActiveListParser(json);
								if(result != null){
									int i = 0;
								}
							}
							else Logger.Instance().Write("Fail to GetMyInfo");
							
						}
						
					});
					 				
			
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
					
					APIProxyLayer.Instance().RequestMyActiveList(null,new IAPIProxyCallback(){
						@Override
						public void OnCallback(boolean success, JSONObject json) {
							if(success)
							{
								Logger.Instance().Write(json.toString());
								
							}
							else Logger.Instance().Write("Fail to MessageSendImage");
						}
					});
					
					
					APIProxyLayer.Instance().MessageSendImage("-9223372036854775805", "/sdcard/koala.jpg",null, new IAPIProxyCallback(){
						@Override
						public void OnCallback(boolean success, JSONObject json) {
							if(success)
							{
								Logger.Instance().Write(json.toString());
								
							}
							else Logger.Instance().Write("Fail to MessageSendImage");
						}
					});
					
					
					
				
				}else 	Logger.Instance().Write("Fail to login");
			}
			
        	
        });
    	  */   // JSON Parser TEST code
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

			if (etUser.getText().toString().length() == 0) {
				//Toast.makeText(context, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
				//break;
				etUser.setText("test1111");
				etPass.setText("1111111");
			} else if (etPass.getText().toString().length() == 0) {
				Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
				break;
			}
			
			pd = ProgressDialog.show(context, "로그인", "사용자 정보 조회중입니다...", true, false);
			
					
			APIProxyLayer.Instance().Login(etUser.getText().toString(), etPass.getText().toString(), new IAPIProxyCallback(){

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

					} else { 

						handler.sendEmptyMessage(500);
						Logger.Instance().Write("login fail");
					}
				}
			});
			//Toast.makeText(context, "아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
			
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
	
	private Handler handler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			
			// Password Not matching
			case 500:
				Toast.makeText(context, "아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
				break;
			}
			
			super.handleMessage(msg);
		}
	};
	
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