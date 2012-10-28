package com.thx.bizcat.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thx.bizcat.R;

public class ChatAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<ChatElement> list;
	private int layout;
	
	public ChatAdapter(Context context, int layout, List<ChatElement> list ) {

		this.context = context;
		this.list = list;
		this.layout = layout;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}


	@Override
	public View getView(final int pos, View view, ViewGroup parent) {

		if(view == null) view = inflater.inflate(layout, parent, false);
		
		ChatElement element = list.get(pos);
		
		// 왼쪽
		if(element.getType() == 0) {
			
			view.findViewById(R.id.CHATtextRight).setVisibility(View.INVISIBLE);
			view.findViewById(R.id.CHATtextRightStr).setVisibility(View.INVISIBLE);
			
			TextView tv = (TextView)view.findViewById(R.id.CHATtextLeft);
			tv.setVisibility(View.VISIBLE);
			tv.setText("누구심");
			
			tv = (TextView)view.findViewById(R.id.CHATtextLeftStr);
			tv.setVisibility(View.VISIBLE);
			tv.setText(element.getText());
			
		// 오른쪽
		} else {
			
			view.findViewById(R.id.CHATtextLeft).setVisibility(View.INVISIBLE);
			view.findViewById(R.id.CHATtextLeftStr).setVisibility(View.INVISIBLE);
			
			TextView tv = (TextView)view.findViewById(R.id.CHATtextRight);
			tv.setVisibility(View.VISIBLE);
			tv.setText("누구심");
			
			tv = (TextView)view.findViewById(R.id.CHATtextRightStr);
			tv.setVisibility(View.VISIBLE);
			tv.setText(element.getText());
		}
			

		return view;
	}
	
	@Override
	public int getCount() { return list.size(); }

	@Override
	public Object getItem(int position) { return list.get(position); }

	@Override
	public long getItemId(int position) { return 0; }

}
