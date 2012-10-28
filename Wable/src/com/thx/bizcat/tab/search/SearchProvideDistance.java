package com.thx.bizcat.tab.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thx.bizcat.R;

public class SearchProvideDistance extends Activity{
	List<String> data;
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_provider_distance);	
		
		ListView lv = (ListView) findViewById(R.id.SEARCH_PRO_DIS_listview);
		//SearchProviderAdapter adapter = new SearchProviderAdapter (getApplicationContext(),
		//		R.layout.search_provider_item, data);
		data = new ArrayList<String>();
		
		
		adapter = new ArrayAdapter<String> (getApplicationContext(),
				android.R.layout.simple_list_item_1, data);
		
		lv.setAdapter(adapter);
	}
	
	public boolean  addString(String seg) {
		boolean ret  = this.data.add(seg);
		adapter.notifyDataSetChanged();
		return ret;
	}
	
	public boolean setData (List<String> data){
		this.data = data;
		adapter.notifyDataSetChanged();
		return true;
	}
	
	
}
