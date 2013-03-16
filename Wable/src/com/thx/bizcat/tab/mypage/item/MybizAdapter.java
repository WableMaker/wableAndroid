package com.thx.bizcat.tab.mypage.item;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thx.bizcat.R;
import com.thx.bizcat.Variables;

public class MybizAdapter extends BaseAdapter  {
	
	Context context;
	LayoutInflater inflater;
	List<MybizElement>[] arrays;
	int layout;
	int mode;
	
	public MybizAdapter(final Context context, int layout, List<MybizElement>[] arrays) {
		
		this.mode = 0;
		this.context = context;
		this.arrays = arrays;
		this.layout = layout;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(final int pos, View view, ViewGroup group) {
		
		if(view == null) view = inflater.inflate(layout, group, false);
		MybizElement e = arrays[mode].get(pos);
		
		switch(mode) {
		
		case 0:
		{
		
		TextView tv = (TextView)view.findViewById(R.id.MYBIZITEMtvTitle);
		tv.setText(e.getTitle());
		
		tv = (TextView)view.findViewById(R.id.MYBIZITEMtvPrice);
		tv.setText(e.getPrice() +"");
		
		tv = (TextView)view.findViewById(R.id.MYBIZITEMtvTime);
		tv = (TextView)view.findViewById(R.id.MYBIZITEMtvReq);
		tv = (TextView)view.findViewById(R.id.MYBIZITEMtvLike);
		
		tv = (TextView)view.findViewById(R.id.MYBIZITEMtvPic);
		if(Variables.USER_PICTURE !=null)
			tv.setBackgroundDrawable(Variables.USER_PICTURE);
		else
			tv.setBackgroundResource(R.drawable.mybizcat_contents_tabbar_userpicture);
		
		Calendar c = Calendar.getInstance(Locale.KOREA);
		
		try {
			long d = java.util.Date.parse(e.getDate().replace("T", " "));
		
		} catch(Exception er) {
			er.printStackTrace();
		}
		
		//long tick = d.getTime();
		long tic2 = c.getTimeInMillis();
		
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
		
		}
		break;
		
		case 1:
		{
			TextView tv = (TextView)view.findViewById(R.id.MYBIZITEMtvTitle);
			tv.setText(e.getTitle());
			
			tv = (TextView)view.findViewById(R.id.MYBIZITEMtvPrice);
			tv.setText(e.getPrice() +"");
			
			tv = (TextView)view.findViewById(R.id.MYBIZITEMtvTime);
			tv = (TextView)view.findViewById(R.id.MYBIZITEMtvReq);
			tv = (TextView)view.findViewById(R.id.MYBIZITEMtvLike);
			
			tv = (TextView)view.findViewById(R.id.MYBIZITEMtvPic);
			tv.setBackgroundResource(R.drawable.mybizcat_contents_tabbar_userpicture);
			
			Calendar c = Calendar.getInstance(Locale.KOREA);

			try {
			long d = java.util.Date.parse(e.getCreated_time().replace("T", " "));
			
			} catch(Exception er) {
				er.printStackTrace();
			}
			
			//long tick = d.getTime();
			long tic2 = c.getTimeInMillis();
			
			
//			switch (e.getStatus()) {
//			case 0:
//				Calendar c = Calendar.getInstance(Locale.KOREA);
//				String due = e.getDate();
//				Date d = Date.valueOf(due.replace("T", " "));
//				
//				long tick = d.getTime();
//				long tic2 = c.getTimeInMillis();
//				
//				tv.setText("");
//				break;
	//
//			case 1:
//				tv.setText("기한만료");
//				break;
//				
//			case 2:
//				tv.setText("등록마감");
//				break;
//			}
		}
		break;
		
		
		}
		
		return view;
		
	}
	
	public void setMode(int mode) { this.mode = mode; notifyDataSetChanged(); }
	
	public int getCount() { return arrays[mode].size(); }

	public Object getItem(int pos) { return arrays[mode].get(pos); }

	public long getItemId(int arg0) { return 0; } 

}
