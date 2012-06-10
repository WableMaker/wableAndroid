package com.wable.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wable.R;
import com.wable.tab.post.RequestCategorySub;
import com.wable.util.ImageDownloader;
import com.wable.util.ImageDownloader.Mode;

public class CategoryAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	List<CategoryElement> list;
	int layout;
	int cnt = 0;
	boolean isLock;
	String[] urls = { "http://lh3.ggpht.com/_loGyjar4MMI/S-InS0tJJSI/AAAAAAAADHU/E8GQJ_qII58/s144-c/Windmills.jpg",          
			"http://lh4.ggpht.com/_loGyjar4MMI/S-InbXaME3I/AAAAAAAADHo/4gNYkbxemFM/s144-c/Frantic.jpg",          
			"http://lh5.ggpht.com/_loGyjar4MMI/S-InKAviXzI/AAAAAAAADHA/NkyP5Gge8eQ/s144-c/Rice%20Fields.jpg",          
			"http://lh3.ggpht.com/_loGyjar4MMI/S-InZA8YsZI/AAAAAAAADH8/csssVxalPcc/s144-c/Seahorse.jpg",          
			"http://lh3.ggpht.com/_syQa1hJRWGY/TBwkCHcq6aI/AAAAAAABBEg/R5KU1WWq59E/s144-c/Antelope.JPG",         
			"http://lh5.ggpht.com/_MoEPoevCLZc/S9fHzNgdKDI/AAAAAAAADwE/UAno6j5StAs/s144-c/c84_7083.jpg",          
			"http://lh4.ggpht.com/_DJGvVWd7IEc/TBpRsGjdAyI/AAAAAAAAFNw/rdvyRDgUD8A/s144-c/Free.jpg",          
			"http://lh6.ggpht.com/_iO97DXC99NY/TBwq3_kmp9I/AAAAAAABcz0/apq1ffo_MZo/s144-c/IMG_0682_cp.jpg",         
			"http://lh4.ggpht.com/_7V85eCJY_fg/TBpXudG4_PI/AAAAAAAAPEE/8cHJ7G84TkM/s144-c/20100530_120257_0273-Edit-2.jpg" 
	};
	
	public CategoryAdapter(Context context, int layout, List<CategoryElement> list) {
		
		this.context = context;
		this.list = list;
		this.layout = layout;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		isLock = false;
		ImageDownloader.getInstance().setMode(Mode.CORRECT);
	}
	
	@Override
	public int getCount() { return list.size(); }

	@Override
	public Object getItem(int pos) { return list.get(pos); }

	@Override
	public long getItemId(int arg0) { return 0; }

	@Override
	public View getView(final int pos, View view, ViewGroup group) {
		
		if(view == null) view = inflater.inflate(layout, group, false);
	
		CategoryElement item = list.get(pos);
		TextView tv = (TextView)view.findViewById(R.id.POSTCategoryItemText);
		tv.setTag(pos);
		tv.setText(item.getTitle() +"/"+item.getId());
		
		ImageView iv = (ImageView)view.findViewById(R.id.POSTCategoryItemImage);
		
		Bitmap bitmap = item.getBitmap();
		if(bitmap != null) {
			
			iv.setImageBitmap(bitmap);	
			if(pos == 0)
			Toast.makeText(context, cnt++ +" : 캐쉬", Toast.LENGTH_SHORT).show();
			
		} else {
		
			
			String url = urls[pos];
			String name = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
			
			String path = context.getFilesDir().getAbsolutePath() +"/cate";
			String files = path  + name;
			File file = new File(files);
			if(file.exists()) {
			
				Bitmap bm = BitmapFactory.decodeFile(files);
				iv.setImageBitmap(bm);
				list.get(pos).setBitmap(bm);
				
				if(pos == 0)
				Toast.makeText(context, cnt++ +" : 파일", Toast.LENGTH_SHORT).show();
				
			} else {
				ImageDownloader.getInstance().download(urls[pos], iv, path);
				if(pos == 0)
				Toast.makeText(context, cnt++ +" : 다운", Toast.LENGTH_SHORT).show();
			}
		}
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(context, RequestCategorySub.class);
				i.putExtra("ID", list.get(pos).getId());	
				context.startActivity(i);
				
			}
		});
	
		return view;
	}


}
