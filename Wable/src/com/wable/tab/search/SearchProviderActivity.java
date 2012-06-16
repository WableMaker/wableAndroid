package com.wable.tab.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wable.R;

public class SearchProviderActivity extends Activity {
	
	private Context context;
	private LinearLayout searchBox;
	private Button segment;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_provider);
		context = this;
		
		segment =  (Button)findViewById(R.id.SEARCH_PRObtnSegment);
		segment.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == MotionEvent.ACTION_UP) {
					
					int one =  v.getWidth() / 3;
					position = (int)event.getX() / one;
					switch(position) {
					
					case 0:
						segment.setBackgroundResource(R.drawable.segment_recent_selected);
						break;
					case 1:
						segment.setBackgroundResource(R.drawable.segment_distance_selceted);
						break;
					case 2:
						segment.setBackgroundResource(R.drawable.segment_map_selected);
						break;
					
					}
				}
				return false;
			}
		});
		
		searchBox = (LinearLayout)findViewById(R.id.SEARCH_PROlinear);
		
		Button dropdown = (Button)findViewById(R.id.SEARCH_PRObtnDropDown);
		dropdown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				ViewGroup.LayoutParams params = searchBox.getLayoutParams();
				params.height = LayoutParams.WRAP_CONTENT;
				
			
				searchBox.setLayoutParams(params);
				
				
				Animation ani = AnimationUtils.loadAnimation(context, R.anim.searchbox_open);
				
				LayoutAnimationController ctrl = new 
						LayoutAnimationController(ani);
				
				
				//searchBox.setLayoutAnimation(ctrl);
				searchBox.startAnimation(ani);
				
				
		   
				
			}
		});
		
		
		
		
		
	}

}
