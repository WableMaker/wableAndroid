package com.wable.tab.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.wable.R;

public class RequestCategory extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_category);
		
		//findViewById(R.id.btnPostRequestList).setOnClickListener(this);
		findViewById(R.id.CATEbtnCancel).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		
		Intent intent;
		
		switch (v.getId()) {
//		case R.id.btnPostRequestList:
//			intent = new Intent(this, RequestPostList.class);
//			startActivity(intent);
//			break;
			
		case R.id.CATEbtnCancel:
//			intent = new Intent(this, RequestPostSubmit.class);
//			startActivity(intent);
			finish();
			break;

		}
		
	}
}
