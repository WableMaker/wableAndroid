package com.wable.tab.setting;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wable.R;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;

public class SettingMyinfoActivity extends Activity implements OnClickListener{
	
	private String username;
	private String profile;
	private boolean cert_fb, cert_phone, cert_email;
	private boolean public_fb, public_phone, public_email;
	
	

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
		// TODO Auto-generated method stub

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
		APIProxyLayer.Instance().MyInfo(new IAPIProxyCallback() {
			
			@Override
			public void OnCallback(boolean success, JSONObject json) {
				// TODO Auto-generated method stub
				if (success){
					try {
						username = json.getString("name");
						
						profile = "프로필을 입력해주세요.";
						
						public_fb = json.getBoolean("public_fb");
						public_email = json.getBoolean("public_email");
						public_phone = json.getBoolean("public_phone");
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
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
