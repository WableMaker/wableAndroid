package com.wable.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.wable.R;

public class PostActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main);
		
		findViewById(R.id.btnPostRequest).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btnPostRequest:
			intent = new Intent(this, RequestCategory.class);
			startActivity(intent);
			break;

		case R.id.btnPostOffer:
			
			break;
		}
		
	}
}
