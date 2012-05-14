package com.wable.mypage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ViewSwitcher;

import com.wable.R;

public class MypageActivity extends Activity implements OnClickListener {

	private int indexView;
	private ViewSwitcher vs;
	private Animation aniLeft;
	private Animation aniRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypage_main);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v1 = inflater.inflate(R.layout.mypage_request, null);
		View v2 = inflater.inflate(R.layout.mypage_offer, null);
		
		vs = (ViewSwitcher)findViewById(R.id.ViewSwitcher1);
		vs.addView(v1);
		vs.addView(v2);
		
		aniLeft = AnimationUtils.loadAnimation(this, R.anim.left_in);
		aniRight = AnimationUtils.loadAnimation(this, R.anim.right_in);
		
		findViewById(R.id.btnMyRequest).setOnClickListener(this);
		findViewById(R.id.btnMyOffer).setOnClickListener(this);
		
		indexView = 0;
		
		
	}

	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		
		case R.id.btnMyRequest:
			if(indexView != 0) {
			    vs.showNext();
				vs.startAnimation(aniLeft);
			}
			indexView = 0;
			break;
			
		case R.id.btnMyOffer:
			if(indexView != 1) {
				vs.showPrevious();
				vs.startAnimation(aniRight);
			}
			indexView = 1;
			
			break;

		}
		
	}
}
