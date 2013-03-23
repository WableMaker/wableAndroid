package com.thx.bizcat.tab.gobiz;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class SearchProviderActivty extends Activity implements OnClickListener, RefHandlerMessage {

	private WeakHandler mHandler = new WeakHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.go_biz_search);
		
		
		findViewById(R.id.SEARCHbtnSeg1).setOnClickListener(this);
		findViewById(R.id.SEARCHbtnSeg2).setOnClickListener(this);
		findViewById(R.id.SEARCHbtnSeg3).setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.SEARCHbtnSeg1:
			//APIProxyLayer.Instance().ProvideListbyTime(lastid, keyword, mHandler);
			break;
			
		case R.id.SEARCHbtnSeg2:
			//APIProxyLayer.Instance().ProvideListbyDistance(lastid, keyword, mHandler);
			break;
					
		case R.id.SEARCHbtnSeg3:
			//APIProxyLayer.Instance().ProvideListbyArea(lastid, keyword, mHandler);
			break;
		
		default:
			break;
		}
		
	}

	@Override
	public void handleMessage(Message msg) {
			
		switch(APICODE.fromInt(msg.what)) {

		default:
			break;
		}		
	}
	
}
