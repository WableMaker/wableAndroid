package com.wable.tab.search;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wable.R;

public class SearchProviderActivity extends ActivityGroup implements OnTouchListener{

	private Context context;
	private LinearLayout searchBox;
	private EditText searchEdit;
	private Button segment;
	private int position;
	private View[] views;
	private int viewId;
	private LinearLayout container;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_provider);
		context = this;
		
		segment =  (Button)findViewById(R.id.SEARCH_PRObtnSegment);
		segment.setOnTouchListener(this);
		
		views = new View[3];
		views[0] = getLocalActivityManager().startActivity("DISTANCE", new Intent(this, SearchProvideDistance.class)).getDecorView();
		views[1] = getLocalActivityManager().startActivity("TIME", new Intent(this, SearchProvideTime.class)).getDecorView();
		views[2] = getLocalActivityManager().startActivity("MAP", new Intent(this, SearchProvideMap.class)).getDecorView();
		viewId = 0;
		
		container = (LinearLayout) findViewById (R.id.SEARCH_PROlist);
		container.addView(views[viewId]);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_UP) {
			int one =  v.getWidth() / 3;
			position = (int) event.getX() / one;
			
			if (position == viewId) return false;
			
			viewId = position;	
			container.removeAllViews();	
			container.addView(views[position]);
			
			switch(position) {
			
			case 0:
				segment.setBackgroundResource(R.drawable.search_segment_distance_selected);
				break;
			case 1:
				segment.setBackgroundResource(R.drawable.search_segment_recent_selceted);
				break;
			case 2:
				segment.setBackgroundResource(R.drawable.search_segment_map_selected);
				break;
			
			}
		}
		return false;	
	}
	
}
