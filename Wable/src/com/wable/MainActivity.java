package com.wable;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.wable.tab.mypage.MypageActivity;
import com.wable.tab.post.PostActivity;
import com.wable.tab.search.SearchActivity;
import com.wable.tab.setting.SettingActivity;

public class MainActivity extends ActivityGroup implements OnClickListener {

	private int viewId;
	private View[] views;
	private LinearLayout container;
	
	private LayoutParams params;
	private LayoutParams paramh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		container = (LinearLayout)findViewById(R.id.Containers);
		findViewById(R.id.btnMainMypage).setOnClickListener(this);
		findViewById(R.id.btnMainPost).setOnClickListener(this);
		findViewById(R.id.btnMainSearch).setOnClickListener(this);
		findViewById(R.id.btnMainSetting).setOnClickListener(this);
		
		views = new View[4];
		views[0] = getLocalActivityManager().startActivity("MYPAGE", new Intent(this, MypageActivity.class)).getDecorView();
		views[1] = getLocalActivityManager().startActivity("POST", new Intent(this, PostActivity.class)).getDecorView();
		views[2] = getLocalActivityManager().startActivity("SEARCH", new Intent(this, SearchActivity.class)).getDecorView();
		views[3] = getLocalActivityManager().startActivity("SETTING", new Intent(this, SettingActivity.class)).getDecorView();
		
		viewId = views[0].getId();
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

		
	}

	@Override
	public void onClick(View v) {
		
		if(viewId == v.getId()) return;
		
		container.removeAllViews();		
		switch (v.getId()) {
		
		case R.id.btnMainMypage:
			container.addView(views[0]);
			break;
			
		case R.id.btnMainPost:
			container.addView(views[1]);
			break;
					
		case R.id.btnMainSearch:
			container.addView(views[2]);
			break;
			
		case R.id.btnMainSetting:
			container.addView(views[3]);
			break;

		}		
		viewId = v.getId();
	}
	
	public void hideBottomTab() { container.setLayoutParams(paramh); }
	public void showBottomTab() { container.setLayoutParams(params); }

}