package com.thx.bizcat.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.BaseAdapter;

import com.thx.bizcat.adapter.CategoryElement;

public class DownloadImageURLThread {
	
	public static void DownloadOrLoadFile(final List<CategoryElement> list, final BaseAdapter adapter) throws IOException {
		
		
		
//		File file = new File(files);
//		if(file.exists()) {
//		
//			Bitmap bm = BitmapFactory.decodeFile(files);
//			view.setImageBitmap(bm);
//			item.setBitmap(bm);
//			
//		} else {
			
			new Thread(new Runnable() {
				public void run() {	

//					try {
						
							
						//String name = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
						//final String files = path + name;
					
					for(int i=0, m=list.size(); i<m; i++) {

						try {

							CategoryElement item = list.get(i);
							if(item.getBitmap() == null) {
						
								String url = Utils.BaseImgUrl+item.getPhoto();	
								URL urls = new URL(url);

								HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
								InputStream is = conn.getInputStream();
								Bitmap bm = BitmapFactory.decodeStream(is);
								item.setBitmap(bm);
								is.close();
								conn.disconnect();								
								
								adapter.notifyDataSetChanged();
							}

						} catch (IOException e) {
							e.printStackTrace();
						}


					}

						

//						HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
//						int len = conn.getContentLength();
//						byte[] bt = new byte[len];
//						InputStream is = conn.getInputStream();
						//FileOutputStream fos = new FileOutputStream(files);
//						Bitmap bm = BitmapFactory.decodeStream(is);

//						while(true){
//							int read = is.read(bt);
//							if(read <= 0)break;
//							fos.write(bt, 0, read);
//						}
						
						//Bitmap bm = BitmapFactory.decodeStream(is);
						//view.setImageBitmap(bm);
//						is.close();
//						fos.close();
//						conn.disconnect();

//					} catch (IOException e) {
//						e.printStackTrace();
//					}


				}
			}).start();

		}
//	}

}
