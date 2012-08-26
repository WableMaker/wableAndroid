package com.wable.tab.search;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wable.R;

public class SearchProviderAdapter extends ArrayAdapter<String>{
	Context context;
	int layoutResourceId;
	int txtResourceId;
	List<String> data = null;
	
	public SearchProviderAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.txtResourceId = textViewResourceId;
		this.data = objects;
	}

	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ItemHolder();
            //holder.name;; // = (ImageView)row.findViewById(R.id.name);
            //holder.distance;// = (TextView)row.findViewById(R.id.distance);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }
        
       // Weather weather = data[position];
       //holder.txtTitle.setText(weather.title);
       // holder.imgIcon.setImageResource(weather.icon);
        
        return row;
    }
    
    // Holder for each data of object;
    static class ItemHolder {
    	TextView name;
    	TextView distance;
    	TextView regDate;    	
    }
}
