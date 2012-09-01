package com.wable.tab.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wable.R;

public class PushActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_push);
		
		TextView tv_title = (TextView) findViewById(R.id.STtxtTitle);
		tv_title.setText(getIntent().getStringExtra("title"));
		
		findViewById(R.id.STbtnBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

}
