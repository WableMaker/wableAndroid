package com.thx.bizcat.tab.mypage;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.thx.bizcat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MybizAdapter extends BaseAdapter  {
	
	Context context;
	LayoutInflater inflater;
	List<MybizElement> list;
	int layout;
	
	public MybizAdapter(final Context context, int layout, List<MybizElement> list) {
		
		this.context = context;
		this.list = list;
		this.layout = layout;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(final int pos, View view, ViewGroup group) {
		
		if(view == null) view = inflater.inflate(layout, group, false);
		MybizElement e = list.get(pos);
		
		TextView tv = (TextView)view.findViewById(R.id.MYBIZITEMtvTitle);
		tv.setText(e.getTitle());
		
		tv = (TextView)view.findViewById(R.id.MYBIZITEMtvPrice);
		tv.setText(e.getPrice() +"");
		
		tv = (TextView)view.findViewById(R.id.MYBIZITEMtvTime);
		TextView img = (TextView)view.findViewById(R.id.MYBIZITEMtvState);
		
		Calendar c = Calendar.getInstance(Locale.KOREA);
		String due = e.getDate();
		long d = Date.parse(due.replace("T", " "));
		
		//long tick = d.getTime();
		long tic2 = c.getTimeInMillis();
		
		tv.setText("");
		
//		switch (e.getStatus()) {
//		case 0:
//			Calendar c = Calendar.getInstance(Locale.KOREA);
//			String due = e.getDate();
//			Date d = Date.valueOf(due.replace("T", " "));
//			
//			long tick = d.getTime();
//			long tic2 = c.getTimeInMillis();
//			
//			tv.setText("");
//			break;
//
//		case 1:
//			tv.setText("기한만료");
//			break;
//			
//		case 2:
//			tv.setText("등록마감");
//			break;
//		}
		
		
		
		
		
		return view;
		
	}
	
	public int getCount() { return list.size(); }

	public Object getItem(int pos) { return list.get(pos); }

	public long getItemId(int arg0) { return 0; } 

}
