package com.thx.bizcat.tab.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.thx.bizcat.R;

public class SearchActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);
		
		findViewById(R.id.SEARCHbtnProvider).setOnClickListener(this);
		findViewById(R.id.SEARCHbtnRequest).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		Intent i;
		switch (v.getId()) {
		
		
		case R.id.SEARCHbtnProvider:
			i = new Intent(this, SearchProviderActivity.class);
			startActivity(i);
			break;
			
		case R.id.SEARCHbtnRequest:
			
			break;
		}

		
	}

}
