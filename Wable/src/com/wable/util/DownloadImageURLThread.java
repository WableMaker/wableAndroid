package com.wable.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageURLThread {
	
	public static void Download(final String url, final String path) throws IOException {
		
		
		new Thread(new Runnable() {
			public void run() {	

				try {

					URL urls = new URL(url);
					String name = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
					String files = path + name;

					HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
					int len = conn.getContentLength();
					byte[] bt = new byte[len];
					InputStream is = conn.getInputStream();
					FileOutputStream fos = new FileOutputStream(files);

					while(true){
						int read = is.read(bt);
						if(read <= 0)break;
						fos.write(bt, 0, read);
					}

					is.close();
					fos.close();
					conn.disconnect();

				} catch (IOException e) {
					e.printStackTrace();
				}
				
			
			}
		}).start();
		
		
		//String sUrl = "http://www.coolenjoy.net/bbs/data/26/%EC%8B%A0%EC%84%B8%EA%B2%BD5.jpg";
		
		
//		File file = new File(files);
//		if(file.exists()) {
//		
//			Bitmap bm = BitmapFactory.decodeFile(files);
//			ImageView imgview = (ImageView) findViewById(R.id.TestImages);
//			imgview.setImageBitmap(bm);
//		}
		
		
	}

}
