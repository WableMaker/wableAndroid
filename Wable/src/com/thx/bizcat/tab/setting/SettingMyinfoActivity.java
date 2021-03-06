package com.thx.bizcat.tab.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_MyInfo_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_Simple_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.Result.sp_GetUserInfo_Result;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class SettingMyinfoActivity extends Activity implements OnClickListener, RefHandlerMessage{
	
	
	private sp_GetUserInfo_Result userInfo;
	private Context context;
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(this);
			
		@Override
		public void handleMessage(Message msg) {

			switch(APICODE.fromInt(msg.what)) {
			
			case UserUpdate :
				
				break;
			
			case MyInfo:
				try
				{
					sp_MyInfo_Items r = (sp_MyInfo_Items)msg.obj;
					userInfo = r.result;
					if(r.bsuccess){					
						//String ServerImgUrl = Utils.BaseImgUrl+r.result.photo;
						//String localImgUrl = getFilesDir().getAbsolutePath() +'/'+ r.result.photo;
						
						Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).mkdirs();						
						//String localImgUrl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() +'/'+ r.result.photo;
						//ImageDownloader.getInstance().imageViewProcessing(localImgUrl, ServerImgUrl, (ImageView)findViewById(R.id.STimageProfile));
						//ImageDownloader.getInstance().uploadImage(localImgUrl);
															
						setUseableEditText(((EditText)findViewById(R.id.STtvName)),false);
						setUseableEditText(((EditText)findViewById(R.id.STtvProfile)),false);
						
						((EditText)findViewById(R.id.STtvName)).setText(userInfo.name);
						((EditText)findViewById(R.id.STtvProfile)).setText(userInfo.introduce);
						
					}
					else
						Toast.makeText(context, r.resultCode.toString() , Toast.LENGTH_LONG).show();
				}
				catch(Exception e)
				{
					e.printStackTrace();	
				}							
				break;
				
			default:
				break;
			
			}
		}

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_myinfo);
		
		updateData();

		
		findViewById(R.id.STtvName).setOnClickListener(this);
		findViewById(R.id.STtvProfile).setOnClickListener(this);
		
		findViewById(R.id.STbtnFacebook).setOnClickListener(this);
		findViewById(R.id.STbtnEmail).setOnClickListener(this);
		findViewById(R.id.STbtnPhone).setOnClickListener(this);
		findViewById(R.id.edit).setOnClickListener(this);
		findViewById(R.id.update).setOnClickListener(this);
		
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
		
		case R.id.edit:
			setUseableEditText(((EditText)findViewById(R.id.STtvName)),true);
			setUseableEditText(((EditText)findViewById(R.id.STtvProfile)),true);
			break;
			
		case R.id.update:			
			userInfo.name = ((EditText)findViewById(R.id.STtvName)).getText().toString();
			userInfo.introduce = ((EditText)findViewById(R.id.STtvProfile)).getText().toString();			
			//사용자 정보 업데이트
			APIProxyLayer.Instance().UserUpdate(userInfo.name, userInfo.introduce, userInfo.photo, userInfo.public_fb, userInfo.public_twitter, userInfo.public_email, userInfo.public_mobile, userInfo.public_phone, mHandler);

			setUseableEditText(((EditText)findViewById(R.id.STtvName)),false);
			setUseableEditText(((EditText)findViewById(R.id.STtvProfile)),false);
			
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
	
	private void setUseableEditText(EditText editText, boolean useable) {
		editText.setClickable(useable);
		editText.setEnabled(useable);
		editText.setFocusable(useable);
		editText.setFocusableInTouchMode(useable);
	}
		
	
	private void updateData() {
		APIProxyLayer.Instance().MyInfo(mHandler);
	}
	
	private void updateView () {
		

				
		//((TextView)findViewById(R.id.STtvTitle)).setText(getIntent().getStringExtra("title"));
		
		// update Name and Profile.
		//String name = 1==2?"name":"hello world!";
		
		//((TextView) findViewById(R.id.STtvName)).setText(name);
		
		
		// update Certification information
		
	}



	
}
