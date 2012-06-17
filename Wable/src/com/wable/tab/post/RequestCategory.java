package com.wable.tab.post;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.wable.R;
import com.wable.adapter.CategoryAdapter;
import com.wable.adapter.CategoryElement;

public class RequestCategory extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_category);
		
		//findViewById(R.id.btnPostRequestList).setOnClickListener(this);
		findViewById(R.id.POSTCategoryBtnBack).setOnClickListener(this);
		findViewById(R.id.POSTCategoryBtnWrite).setOnClickListener(this);
		
		ListView listview = (ListView)findViewById(R.id.POSTCategoryList);

		CategoryAdapter adpater = 
				new CategoryAdapter(this, R.layout.post_request_category_item, 
						new ArrayList<CategoryElement>( PostActivity.categoriesRequest.get(0).values()) );
		listview.setAdapter(adpater);
		
		
	}

	@Override
	public void onClick(View v) {
		
		//Intent intent;
		
		switch (v.getId()) {
		case R.id.POSTCategoryBtnWrite:
			Toast.makeText(this, "직접 작성", Toast.LENGTH_SHORT).show();
			
			
			
			break;
			
		case R.id.POSTCategoryBtnBack:
			finish();
			break;

		}
		
	}
}
