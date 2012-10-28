package com.thx.bizcat.tab.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.thx.bizcat.R;

public class SettingMyinfoCertification extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_myinfo_certification);
		
		
		updateView();
	
		
		findViewById(R.id.STbtnConfirm).setOnClickListener(this);
		findViewById(R.id.STcbPublicEmail).setOnClickListener(this);
		findViewById(R.id.STcbPublicFacebook).setOnClickListener(this);
		findViewById(R.id.STcbPublicPhone).setOnClickListener(this);
		findViewById(R.id.STbtnBack).setOnClickListener(this);
		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
	
			case R.id.STcbPublicFacebook:
				CheckBox fb = (CheckBox) findViewById(R.id.STcbPublicFacebook);
				Toast.makeText(getApplicationContext(), fb.isChecked()?"True":"False", Toast.LENGTH_SHORT).show();
				
			break;
				
			case R.id.STcbPublicPhone:
			
			break;
				
			case R.id.STcbPublicEmail:
				
			break;
			
			
			// confirm. modify information.
			case R.id.STbtnConfirm:
				
			break;
			
			case R.id.STbtnBack:
				onBackPressed();
			break;
			
		}
	}
	
	
	private void updateView() {
		((Button) findViewById(R.id.STbtnConfirm)).setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.STtvTitle)).setText(getIntent().getStringExtra("title"));
	}
}