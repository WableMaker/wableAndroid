package com.wable.tab.mypage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.wable.R;
import com.wable.http.apiproxy.APIProxyLayer;
import com.wable.http.apiproxy.IAPIProxyCallback;
import com.wable.util.Utils;
import com.wable.util.pulltorefresh.PullToRefreshView;
import com.wable.util.pulltorefresh.PullToRefreshView.Listener;
import com.wable.util.pulltorefresh.PullToRefreshView.MODE;

public class MypageActivity extends Activity implements OnClickListener {

	private Context context;
	
	private int indexView;
	private ViewSwitcher vs;
	private Animation aniLeft;
	private Animation aniRight;
	
	private int position = 0;
	private Button menu;
	
	private PullToRefreshView pullView = null;
	
	LayoutInflater inflater;
	LinearLayout container, container2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypage_main);
		context = this;
		
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View v1 = inflater.inflate(R.layout.mypage_request, null);
		View v2 = inflater.inflate(R.layout.mypage_provider, null);
		
		vs = (ViewSwitcher)findViewById(R.id.ViewSwitcher1);
		vs.addView(v1);
		vs.addView(v2);
		
		
		container = (LinearLayout)v1.findViewById(R.id.MYPAGE_RQlinear);
		container2 = (LinearLayout)v2.findViewById(R.id.MYPAGE_PVlinear);
			
		APIProxyLayer.Instance().RequestMyActiveList("", new IAPIProxyCallback() {
			
			@Override
			public void OnCallback(boolean success, JSONObject json) {
			
				try {
					
					int cnt = 0;
					JSONObject obj = json.getJSONObject("data");
					JSONArray suggests = obj.getJSONArray("suggests");
					JSONArray requests = obj.getJSONArray("requests");
					
					cnt = suggests.length() + requests.length();
					
					if(cnt == 0) return;
					
					/* ��û ������û  */
					TextView tv = new TextView(context);
					tv.setBackgroundResource(R.drawable.table_header_cell);
					tv.setGravity(Gravity.CENTER_VERTICAL);
					tv.setTextColor(Color.WHITE);
					tv.setText("  ������û");
					container.addView(tv);	
				
					/* ������û ������ */
					for(int i=0, m=suggests.length(); i<m; i++) {			
						
						View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
						ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
						TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
						TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
						TextView tvRq = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRq);
						TextView tvRe = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRecommand);
						TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
						
						JSONObject o = suggests.getJSONObject(i);
						
						tvTitle.setText(o.getString("provide_title"));
						tvPrice.setText(Utils.ConvertStringToMoney(o.getString("provide_min_price")) + "��");
						
						requestItem.setTag(o.getString("provider_id"));
						requestItem.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								//Intent intent = new Intent(context, RequestListActivity.class);
								//startActivity(intent);
								Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
							}
						});
						container.addView(requestItem);
					}
					
					/* ������û */
					tv = new TextView(context);
					tv.setBackgroundResource(R.drawable.table_header_cell);
					tv.setGravity(Gravity.CENTER_VERTICAL);
					tv.setTextColor(Color.WHITE);
					tv.setText("  ������û");
					
					container.addView(tv);	
					
					
					/* ������û ������ */
					for(int i=0, m=requests.length(); i<m; i++) {			
						
						View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
						ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
						TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
						TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
						TextView tvRq = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRq);
						TextView tvRe = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRecommand);
						TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
						
						final JSONObject o = requests.getJSONObject(i);
						
						tvTitle.setText(o.getString("title"));
						tvPrice.setText(Utils.ConvertStringToMoney(o.getString("price")) + "��");
						
						tvRq.setText(o.getInt("bidding_count") + "�� ��û");
						tvRe.setText(o.getInt("matching_count") + "�� ��û");
						
						switch (o.getInt("status")) {
						case 0:
							image.setImageResource(R.drawable.cell_box_b);
							break;
						case 1:
							tvRq.setVisibility(View.INVISIBLE);
							tvRe.setVisibility(View.INVISIBLE);
							tvMid.setVisibility(View.VISIBLE);
							tvMid.setText("�����");
							image.setImageResource(R.drawable.cell_box_y);
							break;
						case 2:
							tvRq.setVisibility(View.INVISIBLE);
							tvRe.setVisibility(View.INVISIBLE);
							tvMid.setVisibility(View.VISIBLE);
							tvMid.setText("�ŷ��Ϸ�");
							image.setImageResource(R.drawable.cell_box_r);
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
						container.addView(requestItem);
					}
					
					
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		APIProxyLayer.Instance().ProvideMyActiveList("", new IAPIProxyCallback() {
			
			@Override
			public void OnCallback(boolean success, JSONObject json) {
				
					try {
					
					int cnt = 0;
					JSONObject obj = json.getJSONObject("data");
					JSONArray suggests = obj.getJSONArray("suggests");
					JSONArray provides = obj.getJSONArray("provides");
					
					cnt = suggests.length() + provides.length();
					
					if(cnt == 0) return;
					
					/* ��û ������û  */
					TextView tv = new TextView(context);
					tv.setBackgroundResource(R.drawable.table_header_cell);
					tv.setGravity(Gravity.CENTER_VERTICAL);
					tv.setTextColor(Color.WHITE);
					tv.setText("  ������û");
					container2.addView(tv);	
				
					/* ������û ������ */
					for(int i=0, m=suggests.length(); i<m; i++) {			
						
						View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
						ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
						TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
						TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
						TextView tvRq = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRq);
						TextView tvRe = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRecommand);
						TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
						
						JSONObject o = suggests.getJSONObject(i);
						
						tvTitle.setText(o.getString("title"));
						tvPrice.setText(Utils.ConvertStringToMoney(o.getString("request_price")) + "��");
						
						requestItem.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								//Intent intent = new Intent(context, RequestListActivity.class);
								//startActivity(intent);
								//Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
							}
						});
						container2.addView(requestItem);
					}
					
					/* ������û */
					tv = new TextView(context);
					tv.setBackgroundResource(R.drawable.table_header_cell);
					tv.setGravity(Gravity.CENTER_VERTICAL);
					tv.setTextColor(Color.WHITE);
					tv.setText("  ������û");
					
					container2.addView(tv);	
					
					
					/* ������û ������ */
					for(int i=0, m=provides.length(); i<m; i++) {			
						
						View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
						ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
						TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
						TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
						TextView tvRq = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRq);
						TextView tvRe = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRecommand);
						TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
						
						final JSONObject o = provides.getJSONObject(i);
						
						tvTitle.setText(o.getString("title"));
						tvPrice.setText(Utils.ConvertStringToMoney(o.getString("min_price")) + "��");
						
						tvRq.setText(o.getInt("bidding_count") + "�� ��û");
						tvRe.setText(o.getInt("matching_count") + "�� ��û");
						
						switch (o.getInt("status")) {
						case 0:
							image.setImageResource(R.drawable.cell_box_b);
							break;
						case 1:
							tvRq.setVisibility(View.INVISIBLE);
							tvRe.setVisibility(View.INVISIBLE);
							tvMid.setVisibility(View.VISIBLE);
							tvMid.setText("�����");
							image.setImageResource(R.drawable.cell_box_y);
							break;
						case 2:
							tvRq.setVisibility(View.INVISIBLE);
							tvRe.setVisibility(View.INVISIBLE);
							tvMid.setVisibility(View.VISIBLE);
							tvMid.setText("�ŷ��Ϸ�");
							image.setImageResource(R.drawable.cell_box_r);
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
						container2.addView(requestItem);
					}
					
					
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
//				/* ���� ������û */
//				tv = new TextView(context);
//				tv.setBackgroundResource(R.drawable.table_header_cell);
//				tv.setGravity(Gravity.CENTER_VERTICAL);
//				tv.setTextColor(Color.WHITE);
//				tv.setText("  ������û");
//				container2.addView(tv);	
//				
//			
//				/* ������û ������ */
//				for(int i=0; i<0; i++) {			
//					
//					requestItem = inflater.inflate(R.layout.mypage_request_item, null);
//					ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
//					TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
//					TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
//					TextView tvRq = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRq);
//					TextView tvRe = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRecommand);
//					TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
//					requestItem.setTag("����:"+i);
//					requestItem.setOnClickListener(new OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
//						}
//					});
//					container2.addView(requestItem);
//				}
//				
//				
//				
//				/* ������û */
//				tv = new TextView(context);
//				tv.setBackgroundResource(R.drawable.table_header_cell);
//				tv.setGravity(Gravity.CENTER_VERTICAL);
//				tv.setTextColor(Color.WHITE);
//				tv.setText("  ������û");
//				
//				container2.addView(tv);	
//				
//				
//				/* ������û ������ */
//				for(int i=0; i<0; i++) {
//					
//					requestItem = inflater.inflate(R.layout.mypage_request_item, null);
//					ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
//					image.setImageResource(R.drawable.cell_box_y);
//					TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
//					TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
//					TextView tvRq = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRq);
//					TextView tvRe = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextRecommand);
//					TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
//					
//					tvPrice.setText(Utils.ConvertStringToMoney(String.format("%d", i + 12000)) + "��");
//					tvRq.setVisibility(View.INVISIBLE);
//					tvRe.setVisibility(View.INVISIBLE);
//					tvMid.setVisibility(View.VISIBLE);
//					if(i % 2 == 0) {
//						tvMid.setText("�ŷ��Ϸ�");
//						image.setImageResource(R.drawable.cell_box_r);
//					}
//					
//					requestItem.setTag("����:"+i);
//					requestItem.setOnClickListener(new OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
//						}
//					});
//					
//					container2.addView(requestItem);
//				}
				
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
							menu.setBackgroundResource(R.drawable.buyingtab);
							vs.showNext();
							
							
							
							
							
							
							
							break;
						case 1:
							menu.setBackgroundResource(R.drawable.sellingtab);
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
