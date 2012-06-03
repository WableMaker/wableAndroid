package com.wable.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wable.R;
import com.wable.util.DownloadImageURLThread;

public class CategorySubAdapter extends BaseAdapter {
	
	Context context;
	LayoutInflater inflater;
	List<CategoryElement> list;
	int layout;
	boolean isLock;
	
	public CategorySubAdapter(Context context, int layout, List<CategoryElement> list) {
		
		this.context = context;
		this.list = list;
		this.layout = layout;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		isLock = false;
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
	
		CategoryElement item = list.get(pos);
		TextView tv = (TextView)view.findViewById(R.id.POSTCategorySubItemText);
		tv.setText(item.getTitle() +"/"+item.getId());
		
		tv = (TextView)view.findViewById(R.id.POSTCategorySubPrice);
		tv.setText(item.getPrice() + "");
	
		ImageView iv = (ImageView)view.findViewById(R.id.POSTCategoryItemImage);
		Bitmap bm = item.getBitmap();
		if(bm != null) {
			iv.setImageBitmap(bm);
		}
		
		try {
			
			if(!isLock) {
				
				DownloadImageURLThread.DownloadOrLoadFile(list, this);				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				
			}
		});
	
		return view;
	}


}
