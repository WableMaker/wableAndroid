package com.thx.bizcat.tab.login;

import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class RegisterActivity extends Activity implements OnClickListener{
	private Pattern pattern_email = Pattern.compile(
	        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
			);
	
	/* Handler */
	private WeakHandler mHandler = new WeakHandler(new RefHandlerMessage() {
		
		@Override
		public void handleMessage(Message msg) {

			switch(APICODE.fromInt(msg.what)) {
			
			case Register:
				
				
//				if(success) {
//					// Email 인증하라고 말하고, 이메일 인증이 되면 로그인 가능.
//					
//					// Register Success;
//					// goto main activity 
//					
//					//Logger.Instance().Write(json.toString());
//					//Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//					//startActivity(intent);			
//					//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//					//finish();
//					
//				} else {
//					// Login fail
//					Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.login_register);
		
		findViewById(R.id.btnRegisterOk).setOnClickListener(this);
		findViewById(R.id.btnRegisterRequest).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btnRegisterRequest:
				// send sms information. 
				break;
				
			case R.id.btnRegisterOk:
				// login. 
				// check the password is available and email address is availalbe
				
				String password = ((EditText) findViewById(R.id.editRegisterPass)).getText().toString();
				String email = ((EditText) findViewById(R.id.editRegisterEmail)).getText().toString();
				if (!pattern_email.matcher(email).matches()) {
					Toast.makeText(getApplicationContext(), "Email의 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
					break;
				}	
				if (password.length() <= 6) {
					Toast.makeText(getApplicationContext(), "비밀번호가 너무 짧습니다.", Toast.LENGTH_SHORT).show();
					break;
				}
			
				String loginid = ( (EditText) findViewById(R.id.editRegisterTel)).getText().toString();
				String username = ((EditText) findViewById(R.id.editRegisterName)).getText().toString();

				APIProxyLayer.Instance().Register(loginid, email, username, password, mHandler);
				break;
		}
		
	}
	
	
	
	
	

}
