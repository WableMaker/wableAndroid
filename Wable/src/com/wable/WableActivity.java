package com.wable;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.facebook.android.Facebook;
import com.wable.tab.login.PasswordFindActivity;
import com.wable.tab.login.RegisterActivity;
import com.wable.util.Utils;

public class WableActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private Context context;
	private Facebook facebook;
	private SharedPreferences pref;
	private Button loginOk;
	private EditText etUser, etPass;
	
	
	private RelativeLayout layout;
	Animation aniUp, aniDown;
	boolean isUp;
	
	private ScrollView sv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);  
        context = this;        
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        
//        Editor edit = pref.edit();
//		edit.putBoolean("categoryUpdate", true);
//		edit.commit();		
        
        String path = context.getFilesDir().getAbsolutePath() + "/cate";
        Utils.DeleteFolder(path);        
        File file = new File(path);
        file.mkdir();
        
        findViewById(R.id.btnFacebook).setOnClickListener(this);
        findViewById(R.id.btnLoginFind).setOnClickListener(this);
        findViewById(R.id.btnLoginRegister).setOnClickListener(this);
        
        sv = (ScrollView)findViewById(R.id.scrollView1);    
        layout = (RelativeLayout)findViewById(R.id.RelativeLayout1);
        aniUp = AnimationUtils.loadAnimation(context, R.anim.layout_up);
        aniDown = AnimationUtils.loadAnimation(context, R.anim.layout_down);
        isUp = false;
        
        loginOk = (Button)findViewById(R.id.btnLogin);
        loginOk.setOnClickListener(this);
        
        etUser = (EditText)findViewById(R.id.editLoginEmail);
        etUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!isUp)
					layout.startAnimation(aniUp);				
			}
		});
        
        
        etPass = (EditText)findViewById(R.id.editLoginPass);
        etPass.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_DONE) loginOk.performClick();				
				return false;
			}
		});
        
      
              
//        APIProxyLayer.Instance().Login("cc", "111111", new IAPIProxyCallback(){
////
////			@Override
////			public void OnCallback(boolean success, JSONObject json) {
////				if(success)
////				{
////					Logger.Instance().Write(json.toString());
//
//			@Override
//			public void OnCallback(boolean success, JSONObject json) {
//				if(success)
//				{
//					try {
//						Logger.Instance().Write(json.toString());						
//						long category =  pref.getLong("category", 0);
//						long server = json.getJSONObject("data").getLong("categorytick");
//						if(category < server) {
//									
//							Editor edit = pref.edit();
//							edit.putLong("category", server );
//							edit.putBoolean("categoryUpdate", true);
//							edit.commit();					
//
//						}
//						
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
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
////					APIProxyLayer.Instance().MessageSendImage("-9223372036854775805", "/sdcard/koala.jpg", new IAPIProxyCallback(){
////						@Override
////						public void OnCallback(boolean success, JSONObject json) {
////							if(success)
////							{
////								Logger.Instance().Write(json.toString());
////								
////							}
////							else Logger.Instance().Write("Fail to MessageSendImage");
////						}
////					});
//					
////					long dtMili = System.currentTimeMillis();
////					Date dt = new Date(dtMili);
////					
////					APIProxyLayer.Instance().MyInfo(new IAPIProxyCallback(){
////
////						@Override
////						public void OnCallback(boolean success, JSONObject json) {
////							if(success)
////							{
////								Logger.Instance().Write(json.toString());
////								try {
////									JSONObject data = new JSONObject(json.getString("data"));
////									String str = data.getString("name");
////									str = data.getString("email");
////									str = data.getString("phone");
////									str = data.getString("mobile");
////									
////								} catch (JSONException e) {
////									e.printStackTrace();
////								}
////								
////							}
////							else Logger.Instance().Write("Fail to GetMyInfo");
////						}
////						
////					});
////					
//////					long dtMili = System.currentTimeMillis();
//////					Date dt = new Date(dtMili);
//////					
//////					APIProxyLayer.Instance().AddRequest("android", "android", 123, 0, dt, 36, 127, false, false, false, new IAPIProxyCallback(){
//////
//////						@Override
//////						public void OnCallback(boolean success, JSONObject json) {
//////							// TODO Auto-generated method stub
//////							if(success)
//////							{
//////								Logger.Instance().Write(json.toString());
//////							}
//////							else Logger.Instance().Write("Fail to AddRequest");
//////						}
//////						
//////					});
////				}else 	Logger.Instance().Write("Fail to login");
////			}
////        	
////        });
//    
//				}
//			}
//	    });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

	@Override
	public void onClick(View v) {
		
		if(isUp)
			layout.startAnimation(aniDown);	
		
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