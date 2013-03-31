package com.thx.bizcat.tab.gobiz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.thx.bizcat.R;

public class SelectTabActivity extends Activity implements OnClickListener {

	private Context context;
	private SharedPreferences pref;
	
	private TextView tvLimit;
	private EditText etDesc, etPrice;
	private View vDesc, vPrice, vTime, vLimitLayout;
	private long ticks = 0;
	
	private InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.go_biz_select);
		context = this;
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		vLimitLayout = findViewById(R.id.GOBIZSELlayoutTime);
		
		vDesc = findViewById(R.id.GOBIZSELtvDesc);
		vPrice = findViewById(R.id.GOBIZSELtvPrice);
		vTime = findViewById(R.id.GOBIZSELtvTime);
		vDesc.setOnClickListener(this);
		vPrice.setOnClickListener(this);
		vTime.setOnClickListener(this);
		vDesc.setSelected(true);
		
		etDesc = (EditText)findViewById(R.id.GOBIZSELeditDesc);
		etPrice = (EditText)findViewById(R.id.GOBIZSELeditPrice);
		tvLimit = (TextView)findViewById(R.id.GOBIZSELtvLimit);
			
		if(pref.getBoolean("TEMP_ISUSE", false)) {
			
			String desc = pref.getString("TEMP_DESC", "");
			String price = pref.getString("TEMP_PRICE", "");
			String time = pref.getString("TEMP_TIME", "");
			
			etDesc.setText(desc);
			etPrice.setText(price);
			tvLimit.setText(time);
			
		}
		
		etPrice.setVisibility(View.INVISIBLE);
		tvLimit.setVisibility(View.INVISIBLE);
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		switch (getIntent().getIntExtra("NUMBER", 0)) {
		case 0:	vDesc.performClick();	break;
		case 1:	vPrice.performClick();	break;
		case 2:	vTime.performClick();	break;
		}
		
	}
	

	public void onTimeClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.GOBIZSELbtn1:	tvLimit.setText("1 hour"); ticks = 1000*60*60;  break;
		case R.id.GOBIZSELbtn2:	tvLimit.setText("2 hour"); ticks = 1000*60*60*2;	break;
		case R.id.GOBIZSELbtn3:	tvLimit.setText("4 hour"); ticks = 1000*60*60*4;	break;
		case R.id.GOBIZSELbtn4:	tvLimit.setText("6 hour"); ticks = 1000*60*60*6;	break;
		case R.id.GOBIZSELbtn5:	tvLimit.setText("12 hour"); ticks = 1000*60*60*12;	break;
		case R.id.GOBIZSELbtn6:	tvLimit.setText("1-Day"); ticks = 1000*60*60*24;	break;
		case R.id.GOBIZSELbtn7:	tvLimit.setText("2-Day"); ticks = 1000*60*60*24*2;	break;
		case R.id.GOBIZSELbtn8:	tvLimit.setText("3-Day"); ticks = 1000*60*60*24*3;	break;
		case R.id.GOBIZSELbtn9:	tvLimit.setText("4-Day"); ticks = 1000*60*60*24*4;	 break;
		case R.id.GOBIZSELbtn10: tvLimit.setText("5-Day"); ticks = 1000*60*60*24*5;	break;
		case R.id.GOBIZSELbtn11: tvLimit.setText("6-Day"); ticks = 1000*60*60*24*6;	break;
		case R.id.GOBIZSELbtn12: tvLimit.setText("7-Day"); ticks = 1000*60*60*24*7;  break;
	
		}
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.isSelected()) return;
		
		vLimitLayout.setVisibility(View.GONE);
		vDesc.setSelected(false);
		vPrice.setSelected(false);
		vTime.setSelected(false);
		
		etDesc.setVisibility(View.INVISIBLE);
		etPrice.setVisibility(View.INVISIBLE);
		tvLimit.setVisibility(View.INVISIBLE);
		
		switch (v.getId()) {
		

		case R.id.GOBIZSELtvDesc:

			etDesc.setVisibility(View.VISIBLE);
			imm.showSoftInput(etDesc, InputMethodManager.SHOW_IMPLICIT);
			etDesc.requestFocus();
			vDesc.setSelected(true);
			break;

		case R.id.GOBIZSELtvPrice:
			
			etPrice.setVisibility(View.VISIBLE);
			imm.showSoftInput(etPrice, InputMethodManager.SHOW_IMPLICIT);
			etPrice.requestFocus();
			vPrice.setSelected(true);
			break;
			
		case R.id.GOBIZSELtvTime:
			imm.hideSoftInputFromWindow(etDesc.getWindowToken(), 0);
			vLimitLayout.setVisibility(View.VISIBLE);
			tvLimit.setVisibility(View.VISIBLE);
			vTime.setSelected(true);
			break;
		
		}
		
	}
	
	@Override
	public void onBackPressed() {
		
		if(pref != null) {
			
			Editor edit = pref.edit();
			edit.putBoolean("TEMP_ISUSE", true);
			edit.putString("TEMP_DESC", etDesc.getText().toString());
			edit.putString("TEMP_TIME", tvLimit.getText().toString());
			edit.putString("TEMP_PRICE", etPrice.getText().toString());
			edit.putLong("TEMP_TICK", ticks);
			edit.commit();	
			setResult(RESULT_OK);
		}
		
		super.onBackPressed();
	}
	
	
}
