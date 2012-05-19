package com.wable.tab.setting;

import com.wable.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		
		findViewById(R.id.STbtnNotice).setOnClickListener(this);
		findViewById(R.id.STbtnPrivacy).setOnClickListener(this);
		findViewById(R.id.STbtnLocation).setOnClickListener(this);
		findViewById(R.id.STbtnPush).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		Intent i;
		switch (v.getId()) {
		
		case R.id.STbtnNotice:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/terms/membership");
			startActivity(i);
			break;
			
		case R.id.STbtnPrivacy:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/terms/privacy");
			startActivity(i);
			
			break;
			
		case R.id.STbtnLocation:
			i = new Intent(this, WebviewActivity.class);
			i.putExtra("URL", "http://www.wable.co.kr/terms/location ");
			startActivity(i);
		
		break;
		
		case R.id.STbtnPush:
			i = new Intent(this, PushActivity.class);
			startActivity(i);
		
		break;

		}
		
	}

}
