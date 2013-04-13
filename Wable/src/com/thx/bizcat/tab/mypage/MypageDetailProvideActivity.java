package com.thx.bizcat.tab.mypage;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import com.google.android.maps.MapActivity;
import com.thx.bizcat.R;

public class MypageDetailProvideActivity extends MapActivity implements OnClickListener {
	
	private boolean isUp = true;
	private View topLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybiz_detail_provide);


		findViewById(R.id.MYBIZPOST_REQtvDown).setOnClickListener(this);
		topLayout = findViewById(R.id.MYBIZPOST_REQtvToplayout);
		
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.MYBIZPOST_REQtvDown:
			
			LayoutParams params = topLayout.getLayoutParams();
			
			if(isUp)
				params.height += 70;
			else
				params.height -= 70;
			isUp = !isUp;
			topLayout.setLayoutParams(params);
			
			break;

		}
		
		
	}

}
