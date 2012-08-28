package com.wable.tab.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.wable.R;

public class SettingActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		
		findViewById(R.id.STbtnTerms).setOnClickListener(this);
		findViewById(R.id.STbtnPrivacy).setOnClickListener(this);
		findViewById(R.id.STbtnLocation).setOnClickListener(this);
		findViewById(R.id.STbtnPush).setOnClickListener(this);
		findViewById(R.id.STbtnIntro).setOnClickListener(this);
		findViewById(R.id.STbtnNotice).setOnClickListener(this);
		findViewById(R.id.STbtnHowtouse).setOnClickListener(this);
		findViewById( R.id.STbtnFaq).setOnClickListener(this);
		findViewById(R.id.STbtnVersion).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		// 소개동영상
		case R.id.STbtnIntro:
			
			
		break;
		
		// Wable 이용방법
		case R.id.STbtnHowtouse:
			
			
		break;
		
		// 공지사항
		case R.id.STbtnNotice:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/help/notice");
			i.putExtra("title", "공지사항");
			startActivity(i);
		break;
			
		// FAQ
		case R.id.STbtnFaq:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/help/faq");
			i.putExtra("title", "FAQ");
			startActivity(i);
		break;
			
		// Version information 
		case R.id.STbtnVersion:
			i = new Intent (this, VersionActivity.class);
			startActivity(i);
			
			
		break;
		
		
		case R.id.STbtnTerms:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/terms/membership");
			i.putExtra("title", "Wable 이용약관");
			startActivity(i);
		break;
			
		case R.id.STbtnPrivacy:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/terms/privacy");
			i.putExtra("title", "개인정보취급방침 및 청소년보호정책");
			startActivity(i);
		break;
			
		case R.id.STbtnLocation:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/terms/location ");
			i.putExtra("title", "위치기반서비스 이용약관");
			startActivity(i);
		break;
	
		case R.id.STbtnPush:
			i = new Intent(this, PushActivity.class);
			
			startActivity(i);
		
		break;

		}
	}
	

}
