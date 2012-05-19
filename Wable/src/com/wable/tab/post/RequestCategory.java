package com.wable.tab.post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.wable.R;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.util.Logger;

public class RequestCategory extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_category);
		
		//findViewById(R.id.btnPostRequestList).setOnClickListener(this);
		findViewById(R.id.CATEbtnCancel).setOnClickListener(this);
		
		
		APIProxyLayer.Instance().CategoryList(new IAPIProxyCallback() {
			
			@Override
			public void OnCallback(boolean success, JSONObject json) {
				
				if(success)
				{
					Logger.Instance().Write(json.toString());
					
					try {
						
						JSONArray arr = new JSONArray(json.getString("data"));
						
						for(int i=0, m=arr.length(); i<m; i++ ) {
							
							JSONObject data = arr.getJSONObject(i);
							
							String str = data.getString("id");
							str = data.getString("title");
							str = data.getString("description");
							str = data.getString("price");
							str = data.getString("photo");
							str = data.getString("type");
							str = data.getString("order");
							
						}
						
						
						
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
				
				}else 	Logger.Instance().Write("Fail to get Category");
			}
		});
		
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
