package com.thx.bizcat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_BaseImgUrl_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_GetMyActiveRequests_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_LogIn_Items;
import com.thx.bizcat.tab.login.PasswordFindActivity;
import com.thx.bizcat.tab.login.RegisterActivity;
import com.thx.bizcat.util.InterceptHWkeyLayout;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.Utils;
import com.thx.bizcat.util.WeakHandler;
//import com.wable.http.apiproxy.JSONParser.sp_GetRequestsByTime_Item;

public class WableActivity extends Activity implements OnClickListener, RefHandlerMessage {
    /** Called when the activity is first created. */
	
	private Context context;
	private Facebook facebook;
	private Button loginOk;
	
	private EditText etUser, etPass;
	private TextView tvSplash; 
	
	private ProgressDialog pd;
	private SharedPreferences pref;
	
	private boolean isUp = false;
	private LayoutParams params;
	private InterceptHWkeyLayout upLayout;
	private int upCnt = 0;
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
	public void handleMessage(Message msg) {
	
		switch(APICODE.fromInt(msg.what)) {

		case Login:
		{
			sp_LogIn_Items r = (sp_LogIn_Items)msg.obj;
			if(pd != null) pd.dismiss();
			
			if(r.bsuccess) {
				
				String lastid = pref.getString("LAST_ID", "");
				APIProxyLayer.Instance().RequestMyActiveList(lastid, mHandler);
				
			} else
				Toast.makeText(context, r.resultCode.toString() , Toast.LENGTH_LONG).show();
			break;
		}
		
		case RequestMyActiveList:
		{
			//if(pd != null) pd.dismiss();
			sp_GetMyActiveRequests_Items r = (sp_GetMyActiveRequests_Items)msg.obj;
			
			if(r.bsuccess) {
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);			
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}			
			break;
		}
		
		case BaseImgUrl:
		{
			sp_BaseImgUrl_Items r = (sp_BaseImgUrl_Items)msg.obj;
			
			if(r.bsuccess){
				Utils.BaseImgUrl = r.result.baseImgUrl;
			}
			else
				Toast.makeText(context, r.resultCode.toString() , Toast.LENGTH_LONG).show();
			break;
		}
		
		case USERSET1:
			//if(upCnt++ < 5) {
				
				params.topMargin -= 300;
				upLayout.setLayoutParams(params);
			//}
			
			break;
			
		case USERSET2:
			params.topMargin += 300;
			upLayout.setLayoutParams(params);
			
			break;
		default:
			tvSplash.setVisibility(View.GONE);
			break;

		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);  
        context = this;        
        
        pref = PreferenceManager.getDefaultSharedPreferences(this);
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
        tvSplash = (TextView)findViewById(R.id.LOGINtvSplash);
        upLayout = (InterceptHWkeyLayout)findViewById(R.id.LOGINLayout);
        params = (LayoutParams)upLayout.getLayoutParams();
        
        loginOk.setOnClickListener(this);
       
        etUser.setText("cc");
		etPass.setText("111111");
		
		etUser.setOnClickListener(this);
		etPass.setOnClickListener(this);
		
		upLayout.setInterceptHWkeyLayout(this);
		
		
		mHandler.sendEmptyMessageDelayed(10000, 2000);
		
		//Intent intent = new Intent(context, ChatActivity.class);
		//startActivity(intent);
        
//        etPass = (EditText)findViewById(R.id.editLoginPass);
//        etPass.setOnEditorActionListener(new OnEditorActionListener() {
//
//    
		//이미지 서버 주소 받아오기
		APIProxyLayer.Instance().BaseImgUrl(mHandler);
	
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
			break;
			
		case R.id.LOGINeditId:
		case R.id.LOGINeditPass:

			if(!isUp) {
				isUp = true;
				mHandler.sendEmptyMessageDelayed(9000, 50);
			}
				
			break;
			
		}
	}
	
	public void upLayoutF() {
		if(isUp) {
			isUp = false;
			mHandler.sendEmptyMessageDelayed(9001, 300);
		}
	}
	

}