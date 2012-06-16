package com.wable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.facebook.android.Facebook;
import com.wable.tab.login.PasswordFindActivity;
import com.wable.tab.login.RegisterActivity;

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
        
//        String path = context.getFilesDir().getAbsolutePath() + "/cate";
//        Utils.DeleteFolder(path);        
//        File file = new File(path);
//        file.mkdir();
        
        findViewById(R.id.btnFacebook).setOnClickListener(this);
        findViewById(R.id.btnLoginFind).setOnClickListener(this);
        findViewById(R.id.btnLoginRegister).setOnClickListener(this);
        
        sv = (ScrollView)findViewById(R.id.scrollView1);    
        
        
        layout = (RelativeLayout)findViewById(R.id.RelativeLayout1);
        aniUp = AnimationUtils.loadAnimation(context, R.anim.layout_up);
        aniUp.setFillEnabled(true);
        aniUp.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				
						
				
			}
		});
        
        sv.setEnabled(false);
        
        aniDown = AnimationUtils.loadAnimation(context, R.anim.layout_down);
        isUp = false;
        
        loginOk = (Button)findViewById(R.id.btnLogin);
        loginOk.setOnClickListener(this);
        
                
        etUser = (EditText)findViewById(R.id.editLoginEmail);
        etUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//if(!isUp) {
					layout.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							sv.smoothScrollTo(0, 200);
						
						}
					}, 100); 
					sv.setScrollContainer(false);
					//layout.startAnimation(aniUp);
					//layout.scrollTo(0, 220);
			
					//layout.scrollTo(0, 220);
				//}
				//	layout.startAnimation(aniUp);
				
			   //LayoutParams params = (LayoutParams)layout.getLayoutParams();			
			
				
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