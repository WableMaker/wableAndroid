package com.wable.adapter;

import java.io.File;
import java.io.IOException;
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

import com.wable.MainActivity;
import com.wable.R;
import com.wable.tab.post.RequestPostSubmit;
import com.wable.util.DownloadImageURLThread;
import com.wable.util.ImageDownloader;

public class CategorySubAdapter extends BaseAdapter {
	
	Context context;
	LayoutInflater inflater;
	List<CategoryElement> list;
	int layout;
	boolean isLock;
	
	public CategorySubAdapter(Context context, int layout, List<CategoryElement> list) {
		
		this.context = context;
		this.list = list;
		this.layout = layout;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		isLock = false;
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
		TextView tv = (TextView)view.findViewById(R.id.POSTCategorySubItemText);
		tv.setText(item.getTitle() +"/"+item.getId());
		
		tv = (TextView)view.findViewById(R.id.POSTCategorySubPrice);
		tv.setText(item.getPrice() + "");
	
		ImageView iv = (ImageView)view.findViewById(R.id.POSTCategorySubItemImage);
		Bitmap bitmap = item.getBitmap();
		if(bitmap != null) {
			
			iv.setImageBitmap(bitmap);	
			//if(pos == 0)
			//Toast.makeText(context, cnt++ +" : 캐쉬", Toast.LENGTH_SHORT).show();
			
		} else {
		
			
			String url = "http://www.coolenjoy.net/bbs/data/26/%EC%8B%A0%EC%84%B8%EA%B2%BD5.jpg";
			String name = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
			
			String path = context.getFilesDir().getAbsolutePath() +"/cate";
			String files = path  + name;
			File file = new File(files);
			if(file.exists()) {
			
				Bitmap bm = BitmapFactory.decodeFile(files);
				iv.setImageBitmap(bm);
				list.get(pos).setBitmap(bm);
				
				//if(pos == 0)
				//Toast.makeText(context, cnt++ +" : 파일", Toast.LENGTH_SHORT).show();
				
			} else {
				ImageDownloader.getInstance().download("http://www.coolenjoy.net/bbs/data/26/%EC%8B%A0%EC%84%B8%EA%B2%BD5.jpg", iv, path);
				//if(pos == 0)
				//Toast.makeText(context, cnt++ +" : 다운", Toast.LENGTH_SHORT).show();
			}
		}
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, RequestPostSubmit.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
				
			}
		});
	
		return view;
	}


}
