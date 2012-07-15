package com.wable.tab.mypage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.wable.R;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.util.Utils;

public class MypageActivity extends Activity implements OnClickListener {

	private Context context;
	
	private View v1, v2, relRQ, relPV;
	private ViewSwitcher vs;
	
	private int position = 0;
	private Button menu;
	

	private ListView listview1, listview2;
	private List<RequestAdapterItem> list1;
	
	LayoutInflater inflater;
	LinearLayout container, container2;
	
	private ProgressDialog pd;
	
	public final Handler mHandler = new Handler() {        

		public void handleMessage(Message msg) {           
			switch(msg.what) {          
			case 0: 
				pd = ProgressDialog.show(context, "MYActivity", "로딩중입니다..", true, false);			
				
				break;        
			case 1:             
				if(pd != null && pd.isShowing()) pd.dismiss();
				break;
			}       
		}     
	}; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypage_main);
		context = this;
		
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		v1 = inflater.inflate(R.layout.mypage_request, null);
		v2 = inflater.inflate(R.layout.mypage_provider, null);
		
		vs = (ViewSwitcher)findViewById(R.id.ViewSwitcher1);
		vs.addView(v1);	
		vs.addView(v2);		
		
		container = (LinearLayout)v1.findViewById(R.id.MYPAGE_RQlinear);
		container2 = (LinearLayout)v2.findViewById(R.id.MYPAGE_PVlinear);
		
		relRQ = findViewById(R.id.MYPAGE_RQrelNoitem);
		relPV = findViewById(R.id.MYPAGE_PVrelNoitem);
			
		mHandler.sendEmptyMessage(0);
	

		listview1 = (ListView)findViewById(R.id.MYPAGE_RQlist);
		list1 = new ArrayList<RequestAdapterItem>();
		
		
		
		APIProxyLayer.Instance().RequestMyActiveList("", new IAPIProxyCallback() {
			
		
			@Override
			public void OnCallback(boolean success, final JSONObject json) {
			
				if(!success) return;
				

				runOnUiThread(new Runnable() {
					public void run() {

						try {

							int cnt = 0;
							JSONObject obj = json.getJSONObject("data");
							JSONArray suggests = obj.getJSONArray("suggests");
							JSONArray requests = obj.getJSONArray("requests");

							cnt = suggests.length() + requests.length();

							if(cnt == 0) return;
							relRQ.setVisibility(View.INVISIBLE);

							/* 요청  */
							TextView tv = new TextView(context);
							tv.setBackgroundResource(R.drawable.tab1_table_header_cell);
							tv.setGravity(Gravity.CENTER_VERTICAL);
							tv.setTextColor(Color.WHITE);
							tv.setText("  받은신청");

							container.addView(tv);	

							for(int i=0, m=suggests.length(); i<m; i++) {			

								RequestAdapterItem item = new RequestAdapterItem();
								
								
								View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
								
								if(i==0)
								requestItem.findViewById(R.id.MYPAGE_RQtextUp).setVisibility(View.VISIBLE);
								
								ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
								TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
								TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
								TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
								TextView tvDesc = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextDesc);

								JSONObject o = suggests.getJSONObject(i);

								tvTitle.setText(o.getString("provide_title"));
								tvPrice.setText(Utils.ConvertStringToMoney(o.getString("provide_min_price")) + "��");
								tvDesc.setText(o.getString("description"));
								
								requestItem.setTag(o.getString("provider_id"));
								requestItem.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										//Intent intent = new Intent(context, RequestListActivity.class);
										//startActivity(intent);
										Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
									}
								});
								
								if(i+1 == m)
								requestItem.findViewById(R.id.MYPAGE_RQtextBottom).setVisibility(View.VISIBLE);								
								container.addView(requestItem);
							}
							
							tv = new TextView(context);
							tv.setBackgroundResource(R.drawable.tab1_table_header_cell);
							tv.setGravity(Gravity.CENTER_VERTICAL);
							tv.setTextColor(Color.WHITE);
							tv.setText("  보낸신청");

							container.addView(tv);	


							for(int i=0, m=requests.length(); i<m; i++) {			

								View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
								
								if(i==0)
									requestItem.findViewById(R.id.MYPAGE_RQtextUp).setVisibility(View.VISIBLE);
								
								ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
								TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
								TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
								TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
								TextView tvDesc = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextDesc);

								final JSONObject o = requests.getJSONObject(i);

								tvTitle.setText(o.getString("title"));
								tvPrice.setText(Utils.ConvertStringToMoney(o.getString("price")) + "원");
								tvDesc.setText(o.getString("description"));

								//0:제안, 1:결정, 2:결제, 3:완료, 4:취소
								switch (o.getInt("status")) {
//								case 0:
//									image.setImageResource(R.drawable.tab1_cell_box_a);
//									tvMid.setText(o.getInt("bidding_count") +"명 신청\n"+o.getInt("matching_count") + "명 추천");
//									break;
								case 0:
									tvMid.setText("대기중");
									image.setImageResource(R.drawable.tab1_cell_box_b);
									break;
								case 1:
									tvMid.setText("기한만료");
									image.setImageResource(R.drawable.tab1_cell_box_c);
									break;
								case 2:
									tvMid.setText("2");
									image.setImageResource(R.drawable.tab1_cell_box_b);
									break;
								
								case 3:
									tvMid.setText("3");
									image.setImageResource(R.drawable.tab1_cell_box_c);
									break;
								case 4:
									tvMid.setText("4");
									image.setImageResource(R.drawable.tab1_cell_box_c);
									break;
								}

								requestItem.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										Intent intent = new Intent(context, RequestListActivity.class);
										intent.putExtra("JSON", o.toString());
										startActivity(intent);
										//Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
									}
								});
								
								if(i+1 == m)
								requestItem.findViewById(R.id.MYPAGE_RQtextBottom).setVisibility(View.VISIBLE);
								container.addView(requestItem);
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

						//mHandler.sendMessage(mHandler.obtainMessage(0, v1));
						//vs.addView(v1);
						mHandler.sendEmptyMessage(1);
						
					}
				});
				
			}
		});
		
		/* 제공 */
		APIProxyLayer.Instance().ProvideMyActiveList("", new IAPIProxyCallback() {
			
			@Override
			public void OnCallback(boolean success, JSONObject json) {
				
					try {
					
						if(!success) return;
						
					int cnt = 0;
					JSONObject obj = json.getJSONObject("data");
					JSONArray suggests = obj.getJSONArray("suggests");
					JSONArray provides = obj.getJSONArray("provides");
					
					cnt = suggests.length() + provides.length();
					
					if(cnt == 0) return;
					relPV.setVisibility(View.INVISIBLE);
			
					TextView tv = new TextView(context);
					tv.setBackgroundResource(R.drawable.tab1_table_header_cell);
					tv.setGravity(Gravity.CENTER_VERTICAL);
					tv.setTextColor(Color.WHITE);
					tv.setText("  받은신청");
					container2.addView(tv);	
				
					for(int i=0, m=suggests.length(); i<m; i++) {			
						
						View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
						
						if(i==0)
							requestItem.findViewById(R.id.MYPAGE_RQtextUp).setVisibility(View.VISIBLE);
						
						ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
						TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
						TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
						TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
						TextView tvDesc = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextDesc);
						
						JSONObject o = suggests.getJSONObject(i);
						
						tvTitle.setText(o.getString("request_title"));
						tvPrice.setText(Utils.ConvertStringToMoney(o.getString("request_price")) + "원");
						tvDesc.setText(o.getString("request_description"));
						
						requestItem.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								//Intent intent = new Intent(context, RequestListActivity.class);
								//startActivity(intent);
								//Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
							}
						});
						
						if(i+1 == m)
						requestItem.findViewById(R.id.MYPAGE_RQtextBottom).setVisibility(View.VISIBLE);
						container2.addView(requestItem);
					}
					
					tv = new TextView(context);
					tv.setBackgroundResource(R.drawable.tab1_table_header_cell);
					tv.setGravity(Gravity.CENTER_VERTICAL);
					tv.setTextColor(Color.WHITE);
					tv.setText("  보낸신청");
					
					container2.addView(tv);	
					
					
					for(int i=0, m=provides.length(); i<m; i++) {			
						
						View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
						
						if(i==0)
							requestItem.findViewById(R.id.MYPAGE_RQtextUp).setVisibility(View.VISIBLE);
						
						ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
						TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
						TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);		
						TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
						TextView tvDesc = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextDesc);
						
						final JSONObject o = provides.getJSONObject(i);
						
						tvTitle.setText(o.getString("title"));
						tvPrice.setText(Utils.ConvertStringToMoney(o.getString("min_price")) + "원");
						tvDesc.setText(o.getString("description"));
						
						
						switch (o.getInt("status")) {
						case 0:
							tvMid.setText("대기중");
							image.setImageResource(R.drawable.tab1_cell_box_b);
							break;
						case 1:
							tvMid.setText("기한만료");
							image.setImageResource(R.drawable.tab1_cell_box_c);
							break;
						case 2:
							tvMid.setText("2");
							image.setImageResource(R.drawable.tab1_cell_box_b);
							break;
						
						case 3:
							tvMid.setText("3");
							image.setImageResource(R.drawable.tab1_cell_box_c);
							break;
						case 4:
							tvMid.setText("4");
							image.setImageResource(R.drawable.tab1_cell_box_c);
							break;
						}
						
						requestItem.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								Intent intent = new Intent(context, RequestListActivity.class);
								intent.putExtra("JSON", o.toString());
								startActivity(intent);
								//Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
							}
						});
						
						if(i+1 == m)
						requestItem.findViewById(R.id.MYPAGE_RQtextBottom).setVisibility(View.VISIBLE);
						container2.addView(requestItem);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}	
		
			
			}
		});		
		
		
		menu = (Button)findViewById(R.id.MYPAGEbtnMenu);
		menu.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
					if(event.getAction() == MotionEvent.ACTION_UP) {
						
						v.playSoundEffect(SoundEffectConstants.CLICK);
						int one =  v.getWidth() / 2;
						
						int pos = (int)event.getX() / one;
						if(position == pos) return false;
												
						switch(pos) {
						
						case 0:
							menu.setBackgroundResource(R.drawable.tab1_buying);
							vs.showNext();
							
							break;
						case 1:
							menu.setBackgroundResource(R.drawable.tab1_selling);
							vs.showNext();
							break;
						
						}
						position = pos;
						
				}
				return false;
			}
		});
		
		
	}

	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		

		}
	}
}
