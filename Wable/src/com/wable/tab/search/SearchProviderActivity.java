package com.wable.tab.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wable.R;

public class SearchProviderActivity extends Activity implements OnTouchListener{
	
	private Context context;
	private LinearLayout searchBox;
	private EditText searchEdit;
	private Button segment;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_provider);
		context = this;
		
		segment =  (Button)findViewById(R.id.SEARCH_PRObtnSegment);
		segment.setOnTouchListener(this);
		
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_UP) {
			
			int one =  v.getWidth() / 3;
			position = (int) event.getX() / one;
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
