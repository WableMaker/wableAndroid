package com.thx.bizcat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.tab.login.PasswordFindActivity;
import com.thx.bizcat.tab.login.RegisterActivity;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.Utils;
import com.thx.bizcat.util.WeakHandler;
//import com.wable.http.apiproxy.JSONParser.sp_GetRequestsByTime_Item;

public class WableActivity extends Activity implements OnClickListener, RefHandlerMessage {
    /** Called when the activity is first created. */
	
	private Context context;
	private Facebook facebook;
	private Button loginOk;
	
	private EditText etUser, etPass, etUp;
	private boolean isWork;
	
	private ProgressDialog pd;
	//private SharedPreferences pref;
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
	public void handleMessage(Message msg) {
	
		switch(APICODE.fromInt(msg.what)) {

		case Login:

			if(pd != null) pd.dismiss();
			//				
			
			try {
				JSONObject obj = new JSONObject(msg.obj.toString());
				
				if(obj.getBoolean("success")) {
					Intent intent = new Intent(context, MainActivity.class);
					startActivity(intent);			
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					finish();
				} else {
					Toast.makeText(context, "계정정보를 확인하세요", Toast.LENGTH_LONG).show();
					
				}
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			

			//				if(success)
			//				{
			//					//Logger.Instance().Write(json.toString());
			
			//
			//				} else { 
			//
			//					handler.sendEmptyMessage(500);
			//					Logger.Instance().Write("login fail");
			//				}

			break;


		default:
			break;

		}
	}
	
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
        
        etUser.setText("cc");
		etPass.setText("111111");
		
		//Intent intent = new Intent(context, ChatActivity.class);
		//startActivity(intent);
        
//        etPass = (EditText)findViewById(R.id.editLoginPass);
//        etPass.setOnEditorActionListener(new OnEditorActionListener() {
//
//    

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

			if (etUser.getText().toString().length() < 1) {
				//Toast.makeText(context, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
				//break;
				
			} else if (etPass.getText().toString().length() < 1) {
				Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
				break;
			}
			
			pd = ProgressDialog.show(context, "로그인", "사용자 정보 조회중입니다...", true, true);

			APIProxyLayer.Instance().Login(etUser.getText().toString(), etPass.getText().toString(), mHandler);


					
//					new IAPIProxyCallback(){
//
//				@Override
//				public void OnCallback(boolean success, JSONObject json) {		
//					pd.dismiss();
//					
//					if(success)
//					{
//						//Logger.Instance().Write(json.toString());
//						Intent intent = new Intent(context, MainActivity.class);
//						startActivity(intent);			
//						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//						finish();
//
//					} else { 
//
//						handler.sendEmptyMessage(500);
//						Logger.Instance().Write("login fail");
//					}
//				}
			
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