package com.thx.bizcat.tab.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.thx.bizcat.R;

public class WebviewActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_webview);
		String url = getIntent().getStringExtra("URL");
		String title = getIntent().getStringExtra("title");
		TextView title_view = (TextView) findViewById(R.id.STtvTitle);
		title_view.setText(title);
		
		findViewById(R.id.STbtnBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();			
			}
		});
		
		WebView web = (WebView)findViewById(R.id.webView1);
		WebSettings webSettings = web.getSettings();
		webSettings.setJavaScriptEnabled(true);
		web.loadUrl(url);
	}
}
