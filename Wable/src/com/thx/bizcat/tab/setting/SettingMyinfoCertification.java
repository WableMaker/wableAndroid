package com.thx.bizcat.tab.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.*;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class SettingMyinfoCertification extends Activity implements OnClickListener, RefHandlerMessage{
	
	private Context context;
	EditText phoneNumber; 
	
	
	private WeakHandler mHandler = new WeakHandler(this);
	
	public void handleMessage(Message msg) {

		switch(APICODE.fromInt(msg.what)) {	
	
		case UserAuthorizedMobile:
			Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
			break;
		
		default:
			break;
		
		}
	}
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_myinfo_certification);
		context = this;
		updateView();
		
		findViewById(R.id.STbtnConfirm).setOnClickListener(this);
		findViewById(R.id.STcbPublicEmail).setOnClickListener(this);
		findViewById(R.id.STcbPublicFacebook).setOnClickListener(this);
		findViewById(R.id.STcbPublicPhone).setOnClickListener(this);
		findViewById(R.id.STbtnBack).setOnClickListener(this);
		findViewById(R.id.SETTINGbtnCertificationEmail).setOnClickListener(this);
		findViewById(R.id.SETTINGeditPhoneNumber).setOnClickListener(this);
		
		phoneNumber =  (EditText) findViewById(R.id.SETTINGeditPhoneNumber);
		
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
	
			case R.id.STcbPublicFacebook:
				CheckBox fb = (CheckBox) findViewById(R.id.STcbPublicFacebook);
				Toast.makeText(getApplicationContext(), fb.isChecked()?"True":"False", Toast.LENGTH_SHORT).show();
				
			break;
				
			case R.id.STcbPublicPhone:
			
				TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);		          
				String number = tm.getLine1Number();  // 전번					
				phoneNumber.setText(number);
															
			break;
				
			case R.id.STcbPublicEmail:
				
			break;
			
			
			// confirm. modify information.
			case R.id.STbtnConfirm:
				
			break;
			
			case R.id.STbtnBack:
				onBackPressed();
			break;
			
			case R.id.SETTINGeditPhoneNumber:
				//인증 번호 서버에 전송하기
				if(phoneNumber==null)
				{
					new AlertDialog.Builder(context).setPositiveButton("OK", null)
				    .setTitle("").setMessage("전화번호 입력하세요.").show();
				}
				else
				{
					APIProxyLayer.Instance().UserAuthorizedMobile(phoneNumber.toString(), mHandler);					
				}
								
			break;
			
		}
	}
	
	
	private void updateView() {
		((Button) findViewById(R.id.STbtnConfirm)).setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.STtvTitle)).setText(getIntent().getStringExtra("title"));
	}
}