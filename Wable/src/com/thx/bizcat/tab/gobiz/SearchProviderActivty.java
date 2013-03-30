package com.thx.bizcat.tab.gobiz;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_GetProvidesByTime_Items;
import com.thx.bizcat.util.RefHandlerMessage;
import com.thx.bizcat.util.WeakHandler;

public class SearchProviderActivty extends Activity implements OnClickListener, RefHandlerMessage {

	private WeakHandler mHandler = new WeakHandler(this);
	Context context;
	String lastid;	
	EditText keyword;
	SearchType status;
	public enum SearchType
	{
		Time,
		Distance,
		Area;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.go_biz_search);				
		findViewById(R.id.SEARCHbtnSeg1).setOnClickListener(this);
		findViewById(R.id.SEARCHbtnSeg2).setOnClickListener(this);
		findViewById(R.id.SEARCHbtnSeg3).setOnClickListener(this);
		findViewById(R.id.SEARCH_PRObtnSearch).setOnClickListener(this);
		keyword  = (EditText)findViewById(R.id.SEARCH_PROqueryTxt);	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.SEARCHbtnSeg1:		
			APIProxyLayer.Instance().ProvideListbyTime(null, null, mHandler);
			status = SearchType.Time;
			break;
			
		case R.id.SEARCHbtnSeg2:
			//APIProxyLayer.Instance().ProvideListbyDistance(lat, lon, mindistance, null, callback)
			status = SearchType.Distance;
			break;
					
		case R.id.SEARCHbtnSeg3:
			//APIProxyLayer.Instance().ProvideListbyArea(north, south, ease, west, null, callback)
			status = SearchType.Area;
			break;
			
		case R.id.SEARCH_PRObtnSearch:
			
			switch(status)
			{
				case Time:
					APIProxyLayer.Instance().ProvideListbyTime(null, keyword.getText().toString(), mHandler);
					break;
				case Distance:
					//APIProxyLayer.Instance().ProvideListbyDistance(lat, lon, mindistance, null, callback)
					break;
				case Area:
					//APIProxyLayer.Instance().ProvideListbyArea(north, south, ease, west, null, callback)
					break;
				default:
					break;
			}		
						
		default:
			break;
		}
		
	}

	@Override
	public void handleMessage(Message msg) {
			
		switch(APICODE.fromInt(msg.what)) {
		case ProvideListbyTime:
			try
			{
				sp_GetProvidesByTime_Items r = (sp_GetProvidesByTime_Items)msg.obj;
				
				if(r.bsuccess){
					
					for(int i=0;i<=r.result.size();i++)
					{
						Toast.makeText(context, r.result.get(i).title, Toast.LENGTH_LONG).show();
						lastid = r.result.get(i).id;
					}
				}
				else
					Toast.makeText(context, r.resultCode.toString() , Toast.LENGTH_LONG).show();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}	
			
			
			break;
			
		default:
			break;
		}		
	}
	
}
