package com.wable.tab.post;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.wable.R;
import com.wable.adapter.CategoryElement;
import com.wable.adapter.CategorySubAdapter;

public class RequestCategorySub extends Activity {
	
	ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_category_sub);
		
		listview = (ListView)findViewById(R.id.POSTCategoryDList);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		
		
		int id = getIntent().getIntExtra("ID", 0);
		
		CategorySubAdapter adapter = new CategorySubAdapter(this, R.layout.post_request_category_sub_item
				, new ArrayList<CategoryElement>( PostActivity.categoriesRequest.get(id).values()) );
		
		listview.setAdapter(adapter);
		
		
	}
}
