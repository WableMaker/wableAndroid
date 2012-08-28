package com.wable.tab.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.wable.R;

public class WebviewActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_webview);
		String url = getIntent().getStringExtra("URL");
		String title = getIntent().getStringExtra("title");
		TextView title_view = (TextView) findViewById(R.id.STtxtTitle);
		title_view.setText(title);
		
		findViewById(R.id.STbtnBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();			
			}
		});
		
		WebView web = (WebView)findViewById(R.id.webView1);
		web.loadUrl(url);
	}
}
