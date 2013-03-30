package com.thx.bizcat;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.thx.bizcat.chat.ChatActivity;
import com.thx.bizcat.tab.gobiz.GoBizActivity;
import com.thx.bizcat.tab.mypage.MypageActivity;
import com.thx.bizcat.tab.setting.SettingActivity;

public class MainActivity extends ActivityGroup implements OnClickListener {

	private int viewId;
	Drawable viewImage;
	private View originView;
	private View[] views;
	private LinearLayout container;
	
	private LayoutParams params;
	private LayoutParams paramh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Variables.BASE_STORAGE_PATH = getFilesDir().getAbsolutePath();
		
		container = (LinearLayout)findViewById(R.id.Containers);
		findViewById(R.id.btnMainMypage).setOnClickListener(this);
		findViewById(R.id.btnMainPost).setOnClickListener(this);
		findViewById(R.id.btnMainSetting).setOnClickListener(this);
		
		views = new View[3];
		views[0] = getLocalActivityManager().startActivity("MYPAGE", new Intent(this, MypageActivity.class)).getDecorView();
		views[1] = getLocalActivityManager().startActivity("BIZCAT", new Intent(this, GoBizActivity.class)).getDecorView();
		views[2] = getLocalActivityManager().startActivity("SETTING", new Intent(this, SettingActivity.class)).getDecorView();
		
		viewId = R.id.btnMainMypage;		
		originView = findViewById(R.id.btnMainMypage);			
		viewImage = null;
		
		container.addView(views[0]);	
		
		params =  new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
		paramh =  new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 

        params.addRule(RelativeLayout.ABOVE, R.id.Bottom);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        
        paramh.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramh.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        paramh.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        container.setLayoutParams(params);
		
		Intent intent = new Intent(this, WableActivity.class);
		startActivityForResult(intent, 0);			
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == 0) {
			if(resultCode == Activity.RESULT_OK)
				MypageActivity.getInstance().startView();
			else
				finish();
		} 
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	@Override
	public void onClick(View v) {
		
		if(viewId == v.getId()) return;	
	
		originView.setBackgroundDrawable(viewImage);
		viewImage = v.getBackground();
		
			
		container.removeAllViews();		
		switch (v.getId()) {
		
		case R.id.btnMainMypage:
			container.addView(views[0]);			
			v.setBackgroundResource(R.drawable.menu_tabbar_mybizcat_selected_btn);
			break;
			
		case R.id.btnMainPost:
			container.addView(views[1]);
			v.setBackgroundResource(R.drawable.menu_tabbar_gobizcat_selected_btn);
			break;

		case R.id.btnMainSetting:
			//container.addView(views[2]);
			//v.setBackgroundResource(R.drawable.menu_tabbar_setting_selected_btn);
			
			Intent in = new Intent(this,ChatActivity.class);
			startActivity(in);
			
			break;

		}		
		viewId = v.getId();
		originView = v;
	}
	
	public void hideBottomTab() { container.setLayoutParams(paramh); }
	public void showBottomTab() { container.setLayoutParams(params); }

}
