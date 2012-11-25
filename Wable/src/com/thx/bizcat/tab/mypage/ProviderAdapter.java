package com.thx.bizcat.tab.mypage;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProviderAdapter extends BaseAdapter {
	
	Context context;
	LayoutInflater inflater;
	ArrayList<ProviderAdapterItem> list;
	int layout;
	
	public ProviderAdapter(Context context, int layout, ArrayList<ProviderAdapterItem> list) {
		
		this.context = context;
		this.list = list;
		this.layout = layout;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() { return list.size(); }

	@Override
	public Object getItem(int pos) { return list.get(pos); }

	@Override
	public long getItemId(int arg0) { return 0; }

	@Override
	public View getView(final int pos, View view, ViewGroup group) {
		
		if(view == null) view = inflater.inflate(layout, group, false);
		
		ProviderAdapterItem item = list.get(pos);	
		
//		try {
		//
//		if(!success) return;
//
//		int cnt = 0;
//		JSONObject obj = json.getJSONObject("data");
//		JSONArray suggests = obj.getJSONArray("suggests");
//		JSONArray provides = obj.getJSONArray("provides");
//
//		cnt = suggests.length() + provides.length();
//
//
//		for(int i=0, m=suggests.length(); i<m; i++) {			
//
//			View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
//
//			if(i==0)
//				requestItem.findViewById(R.id.MYPAGE_RQtextUp).setVisibility(View.VISIBLE);
//
//			ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
//			TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
//			TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);
//			TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
//			TextView tvDesc = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextDesc);
//
//			JSONObject o = suggests.getJSONObject(i);
//
//			tvTitle.setText(o.getString("request_title"));
//			tvPrice.setText(Utils.ConvertStringToMoney(o.getString("request_price")) + "원");
//			tvDesc.setText(o.getString("request_description"));
//
//			requestItem.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					//Intent intent = new Intent(context, RequestListActivity.class);
//					//startActivity(intent);
//					//Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
//				}
//			});
//
//			if(i+1 == m)
//				requestItem.findViewById(R.id.MYPAGE_RQtextBottom).setVisibility(View.VISIBLE);
//			container2.addView(requestItem);
//		}
//
//
//		for(int i=0, m=provides.length(); i<m; i++) {			
//
//			View requestItem = inflater.inflate(R.layout.mypage_request_item, null);
//
//			if(i==0)
//				requestItem.findViewById(R.id.MYPAGE_RQtextUp).setVisibility(View.VISIBLE);
//
//			ImageView image = (ImageView)requestItem.findViewById(R.id.MYPAGE_RQimgTag);
//			TextView tvTitle = (TextView)requestItem.findViewById(R.id.MYAPGE_RQtextTitle);
//			TextView tvPrice = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextPrice);		
//			TextView tvMid = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextMid);
//			TextView tvDesc = (TextView)requestItem.findViewById(R.id.MYPAGE_RQtextDesc);
//
//			final JSONObject o = provides.getJSONObject(i);
//
//			tvTitle.setText(o.getString("title"));
//			tvPrice.setText(Utils.ConvertStringToMoney(o.getString("min_price")) + "원");
//			tvDesc.setText(o.getString("description"));
//
//
//			switch (o.getInt("status")) {
//			case 0:
//				tvMid.setText("대기중");
//				image.setImageResource(R.drawable.tab1_cell_box_b);
//				break;
//			case 1:
//				tvMid.setText("기한만료");
//				image.setImageResource(R.drawable.tab1_cell_box_c);
//				break;
//			case 2:
//				tvMid.setText("2");
//				image.setImageResource(R.drawable.tab1_cell_box_b);
//				break;
//
//			case 3:
//				tvMid.setText("3");
//				image.setImageResource(R.drawable.tab1_cell_box_c);
//				break;
//			case 4:
//				tvMid.setText("4");
//				image.setImageResource(R.drawable.tab1_cell_box_c);
//				break;
//			}
//
//			requestItem.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					Intent intent = new Intent(context, RequestListActivity.class);
//					intent.putExtra("JSON", o.toString());
//					startActivity(intent);
//					//Toast.makeText(context, v.getTag().toString(), Toast.LENGTH_SHORT).show();
//				}
//			});
//
//			if(i+1 == m)
//				requestItem.findViewById(R.id.MYPAGE_RQtextBottom).setVisibility(View.VISIBLE);
//			container2.addView(requestItem);
//		}
//
//	} catch (JSONException e) {
//		e.printStackTrace();
//	}	
		
	
		return view;
	}
	

}
