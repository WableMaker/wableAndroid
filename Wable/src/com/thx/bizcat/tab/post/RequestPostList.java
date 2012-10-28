package com.thx.bizcat.tab.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.thx.bizcat.R;

public class RequestPostList extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_list);
		
		findViewById(R.id.btnPostRequestListNext).setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnPostRequestListNext:
			Intent intent = new Intent(this, RequestPostSubmit.class);
			startActivity(intent);
			break;

		}
		
	}

}
