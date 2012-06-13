package com.wable;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.tab.login.PasswordFindActivity;
import com.wable.tab.login.RegisterActivity;
import com.wable.util.Logger;
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
					
					APIProxyLayer.Instance().UserUpdate("123","123123", "/sdcard/koala.jpg", new IAPIProxyCallback(){
						@Override
						public void OnCallback(boolean success, JSONObject json) {
							if(success)
							{
								Logger.Instance().Write(json.toString());
								
							}
							else Logger.Instance().Write("Fail to UserUpdate");
						}
					});
					
				
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