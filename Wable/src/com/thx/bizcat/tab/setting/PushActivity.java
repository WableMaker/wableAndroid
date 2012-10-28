package com.thx.bizcat.tab.setting;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.IAPIProxyCallback;
import com.thx.bizcat.util.Logger;

public class PushActivity extends Activity implements OnClickListener{
	private static int DIALOG_TIMEPICKER = 0;
	Time starttime, endtime;
	boolean ispush = false;
	String SETTING_PUSH_ENABLED = "PUSH_ENABLED", SETTING_STARTTIME = "STARTTIME" , SETTING_ENDTIME="ENDTIME";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_push);
		
		// 변수 초기화
		starttime = new Time();
		endtime = new Time();
		
		
		// 환경설정 정보를 가져와 출력한다.
		SharedPreferences pref = getPreferences(Activity.MODE_PRIVATE);
		ispush = pref.getBoolean(SETTING_PUSH_ENABLED, true);
		
		String st_time = pref.getString(SETTING_STARTTIME, "00:00");
		starttime.set(0, Integer.parseInt(st_time.split(":")[0]), 
						 Integer.parseInt(st_time.split(":")[1]), 0, 0, 0);
		String ed_time = pref.getString(SETTING_ENDTIME, "00:00");
		endtime.set(0, Integer.parseInt(ed_time.split(":")[0]), 
					   Integer.parseInt(ed_time.split(":")[1]), 0, 0, 0);
		
		
		TextView tv_title = (TextView) findViewById(R.id.STtvTitle);
		tv_title.setText(getIntent().getStringExtra("title"));
		
		findViewById(R.id.STbtnBack).setOnClickListener(this);
		
		ToggleButton tb = (ToggleButton) findViewById(R.id.STbtnPushToggle);
		tb.setOnClickListener(this);
		tb.setChecked(ispush);
		
		EditText start_time = (EditText) findViewById(R.id.STetStarttime);
		start_time.setOnClickListener(this);
		start_time.setText(pref.getString(SETTING_STARTTIME, "00:00"));
		
		EditText end_time = (EditText) findViewById(R.id.STetEndtime);
		end_time .setOnClickListener(this);
		end_time.setText(pref.getString(SETTING_ENDTIME, "00:00"));
		
	}

	Handler savePushPreferences = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			case 500:
				SharedPreferences pref = getPreferences(Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(SETTING_PUSH_ENABLED, ispush);
				editor.putString(SETTING_STARTTIME, starttime.format("%H:%M"));
				editor.putString(SETTING_ENDTIME, endtime.format("%H:%M")) ;
				
				editor.commit();
				
				break;
			}
			
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
	
		APIProxyLayer.Instance().UserEnablePushNotify(starttime, endtime, new IAPIProxyCallback() {
			
			@Override
			public void OnCallback(boolean success, JSONObject json) {	
				savePushPreferences.sendEmptyMessage(500);
				
				
				
				// TODO Auto-generated method stub
				//savePushPreferences();
			}
		});
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			
			case R.id.STbtnBack:
				onBackPressed();				
			break;
			

			case R.id.STetStarttime:
				showDialog(DIALOG_TIMEPICKER);
			break;		
			
			case R.id.STetEndtime:
				showDialog(1);
			break;
			
			case R.id.STbtnPushToggle:
				ispush=!ispush;
			break;
		}
		
	}

	TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
			tp.setCurrentHour(hourOfDay);
			tp.setCurrentMinute(minute);
			
			starttime.set(0, minute, hourOfDay, 0, 0, 0);
			EditText et = (EditText) findViewById(R.id.STetStarttime);
			Logger.Instance().Write(minute+"");
			et.setText(starttime.format("%H:%M"));
			
		}
	};	
	TimePickerDialog.OnTimeSetListener timePickerListener2 = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			endtime.set(0, minute, hourOfDay, 0, 0, 0);
			EditText et = (EditText) findViewById(R.id.STetEndtime);
			et.setText(endtime.format("%H:%M"));

		}
	};
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerListener, 0, 0,true);
		case 1:
			return new TimePickerDialog(this, 
										timePickerListener2, 0, 0, true);
		}
		return null;
	}
}
