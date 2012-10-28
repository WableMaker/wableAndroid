package com.thx.bizcat.tab.mypage;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RequestAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	ArrayList<RequestAdapterItem> list;
	int layout;
	
	public RequestAdapter(Context context, int layout, ArrayList<RequestAdapterItem> list) {
		
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
		
		RequestAdapterItem item = list.get(pos);		
	
		return view;
	}
	
	
	

	
}

