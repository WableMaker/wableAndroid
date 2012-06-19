package com.wable.util.pulltorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wable.R;
import com.wable.util.pulltorefresh.PullToRefreshView.Listener;
import com.wable.util.pulltorefresh.PullToRefreshView.MODE;



public class PulltorefreshbynixActivity extends Activity implements OnTouchListener{
	private PullToRefreshView pullView = null;
	private BottomPullToRefreshView pullView2 = null;
	private ListView listView = null;
	private ArrayAdapter<String> adapter =null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //pullView = (PullToRefreshView)findViewById(R.id.pull_to_refresh);
        pullView.setListener(new Listener() {
			
			@Override
			public void onChangeMode(MODE mode) {
				Log.w("cranix","pullView:"+mode);
				switch(mode) {
					case NORMAL:
						listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);	
						break;
					case PULL: case READY_TO_REFRESH:
						if (pullView.isTop()) {
							listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);	
						}
						break;
					case REFRESH:
//						pullView.completeRefresh();
						break;
				}
			}
		});
        
       // pullView2 = (BottomPullToRefreshView)findViewById(R.id.pull_to_refresh2);
        //pullView2.setListener(new BottomPullToRefreshView.Listener() {
//			@Override
//			public void onChangeMode(net.cranix.android.ui.pulltorefresh.BottomPullToRefreshView.MODE mode) {
//				Log.w("cranix","pullView2:"+mode);
//				switch(mode) {
//					case NORMAL:
//						listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
//						break;
//					case PULL:case READY_TO_REFRESH:
//						if (pullView2.isBottom()) {
//							listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//						}
//						break;
//					case REFRESH:
//						break;
//				}
//			}
//	
//		});
        
        
        
//        listView = (ListView)findViewById(R.id.listView);
		
       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[] {
    	   "test1","test1","test1","test1","test1","test1","test1","test1","test1","test1","test1","test1","test1","test1","test1"
        });
        listView.setAdapter(adapter);
        listView.setOnTouchListener(this);
        listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				View firstView = view.getChildAt(0);
				if (firstView == null) {
					return;
				}
				if (pullView == null) {
					return;
				}
				if (firstVisibleItem == 0 && firstView.getTop() == 0) {
					Log.w("cranix","firstVisibleItem:"+firstVisibleItem+",firstView.getTop():"+firstView.getTop());
					pullView.setTop(true);
				}
				else {
					pullView.setTop(false);
				}
				
				View lastView = view.getChildAt(view.getChildCount()-1);
				if (lastView == null) {
					return;
				}
				if (totalItemCount == firstVisibleItem + visibleItemCount && lastView.getBottom() <= view.getHeight()) {
					pullView2.setBottom(true);
				}
				else {
					pullView2.setBottom(false);
				}
			}
		});

        
//        Button btn = (Button)findViewById(R.id.button);
//        btn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				pullView.completeRefresh();
//			}
//		});
//        
//        Button btn2 = (Button)findViewById(R.id.button2);
//        btn2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				pullView2.completeRefresh();
//			}
//		});
    }

	/**
	 * @param v
	 * @param event
	 * @return
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		pullView.touchDelegate(v, event);
		pullView2.touchDelegate(v, event);
		return false;
	}
}