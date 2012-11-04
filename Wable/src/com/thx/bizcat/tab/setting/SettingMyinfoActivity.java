package com.thx.bizcat.tab.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class SettingMyinfoActivity extends Activity implements OnClickListener{
	
	private String username;
	private String profile;
	private boolean cert_fb, cert_phone, cert_email;
	private boolean public_fb, public_phone, public_email;
	
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(new RefHandlerMessage() {
		
		@Override
		public void handleMessage(Message msg) {

			switch(APICODE.fromInt(msg.what)) {
			
			case MyInfo:
//				if (success){
//					try {
//						username = json.getString("name");
//						
//						profile = "프로필을 입력해주세요.";
//						
//						public_fb = json.getBoolean("public_fb");
//						public_email = json.getBoolean("public_email");
//						public_phone = json.getBoolean("public_phone");
//						
//						
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//					
//				}
				
				
				break;
				
				
			default:
				break;
			
			}
		}
	});
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_myinfo);
		
		updateData();
		updateView();
		
		findViewById(R.id.STtvName).setOnClickListener(this);
		findViewById(R.id.STtvProfile).setOnClickListener(this);
		
		findViewById(R.id.STbtnFacebook).setOnClickListener(this);
		findViewById(R.id.STbtnEmail).setOnClickListener(this);
		findViewById(R.id.STbtnPhone).setOnClickListener(this);
		
		findViewById(R.id.STbtnBack).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		
		case R.id.STbtnBack:
			onBackPressed();
		break;
		
		case R.id.STtvName:
			
			
			
		break;
			
		case R.id.STtvProfile:
			
			
		break;
			
		default:
			if (v.getId() == R.id.STbtnFacebook || v.getId() ==  R.id.STbtnEmail || v.getId() ==  R.id.STbtnPhone){
				Intent i = new Intent(this, SettingMyinfoCertification.class);
				i.putExtra("title", "인증정보");
				startActivity(i);				
			}
			
		break;
		
		}
	}	
	
	private void updateData() {
		APIProxyLayer.Instance().MyInfo(mHandler);
	}
	
	private void updateView () {
		// 상단 title 업데이트
		((TextView)findViewById(R.id.STtvTitle)).setText(getIntent().getStringExtra("title"));
		
		// update Name and Profile.
		String name = 1==2?"name":"hello world!";
		
		((TextView) findViewById(R.id.STtvName)).setText(name);
		
		
		// update Certification information
		
	}



	
}
