package com.wable.tab.search;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wable.R;
import com.wable.adapter.SearchElement;
import com.wable.http.apiproxy.APIProxyLayer;

public class SearchProviderActivity extends Activity {
	
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
		
		searchEdit = (EditText)findViewById(R.id.SEARCH_PROeditSearch);
		searchBox = (LinearLayout)findViewById(R.id.SEARCH_PROlinear);
		
		Button dropdown = (Button)findViewById(R.id.SEARCH_PRObtnDropDown);
		dropdown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				ViewGroup.LayoutParams params = searchEdit.getLayoutParams();
				params.height = LayoutParams.WRAP_CONTENT;
				searchEdit.setLayoutParams(params);
				
			}
		});
		
	}
	
	private void getRecent() {
		
		List<SearchElement> list = new ArrayList<SearchElement>();
		
	}
	
	private void getDistance() {
		
	}

	private void getMap() {
		
	}
	
	

}
