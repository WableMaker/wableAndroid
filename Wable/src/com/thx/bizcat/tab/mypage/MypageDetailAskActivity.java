package com.thx.bizcat.tab.mypage;

import com.google.android.maps.MapActivity;
import com.thx.bizcat.R;

import android.app.Activity;
import android.os.Bundle;

public class MypageDetailAskActivity extends MapActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybiz_detail_ask);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
