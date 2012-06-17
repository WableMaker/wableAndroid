package com.wable.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wable.R;
import com.wable.tab.post.RequestCategorySub;
import com.wable.util.ImageDownloader;
import com.wable.util.ImageDownloader.Mode;

public class SearchAdapter extends BaseAdapter {

	int layout;
	Context context;
	LayoutInflater inflater;
	List<SearchElement> list;
	
	public SearchAdapter(Context context, int layout, List<SearchElement> list) {
		
		this.layout = layout;
		this.context = context;
		this.list = list;
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
	
		SearchElement item = list.get(pos);
		//TextView tv = (TextView)view.findViewById(R.id.POSTCategoryItemText);
		//tv.setTag(pos);
		//tv.setText(item.getTitle() +"/"+item.getId());
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
	
		return view;
	}


}
