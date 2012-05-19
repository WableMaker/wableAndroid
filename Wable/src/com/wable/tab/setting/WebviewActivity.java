package com.wable.tab.setting;

import com.wable.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebviewActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_webview);
		String url = getIntent().getStringExtra("URL");
		
		WebView web = (WebView)findViewById(R.id.webView1);
		web.loadUrl(url);
	}

}
