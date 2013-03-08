package com.thx.bizcat.util;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import com.thx.bizcat.Variables;
import com.thx.bizcat.http.IHttpConnectionLayer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

/**
 * This helper class download images from the Internet and binds those with the provided ImageView.
 *
 * <p>It requires the INTERNET permission, which should be added to your application's manifest
 * file.</p>
 *
 * A local cache of downloaded images is maintained internally to improve performance.
 */


public class ImageDownloader {
    private static final String LOG_TAG = "ImageDownloader";
    
    private static ImageDownloader downloader;
    public static ImageDownloader getInstance() { 
    	if(downloader == null) 
    		downloader = new ImageDownloader();
    	
    	return downloader;
    }
    
    public enum Mode { NO_ASYNC_TASK, NO_DOWNLOADED_DRAWABLE, CORRECT }
    private Mode mode = Mode.NO_ASYNC_TASK;
    
    /**
     * Download the specified image from the Internet and binds it to the provided ImageView. The
     * binding is immediate if the image is found in the cache and will be done asynchronously
     * otherwise. A null bitmap will be associated to the ImageView if an error occurs.
     *
     * @param url The URL of the image to download.
     * @param imageView The ImageView to bind the downloaded image to.
     */
    String files;
    public void download(String url, ImageView imageView, String files) {
        resetPurgeTimer();
        Bitmap bitmap = getBitmapFromCache(url);
        this.files = files;
        
        if (bitmap == null) {
            forceDownload(url, imageView);
        } else {
            cancelPotentialDownload(url, imageView);
            imageView.setImageBitmap(bitmap);
        }
    }
      
    
    //이미지 다운로드
    public void imageViewProcessing(String localImgUrl, String ServerImgUrl, ImageView imageView)
    {   
    	//1.Cache Memory에 있는지 확인 
    	Bitmap bitmap = getBitmapFromCache(localImgUrl);
    	
    	if (bitmap == null) {
    	
    		//이미지가 파일로 다운로드 되어 있는지 확인
    		File file = new File(localImgUrl);    	
    		if(file.exists()) 
    		{    		
    			Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
    			if(bm!=null)
    				imageView.setImageBitmap(bm);
    			else
    				file.delete();
    		}
    		else
    		{
    			try
    			{
    				//이미지 서버에서 다운로드해서 로컬 저장
    				URL url = new URL(ServerImgUrl);

    				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    				
    				//주의 : Input Stream은 두번 읽을 수 없음 (파일 저장 후 다시 읽어오는 로직으로 구현)
    				InputStream is = conn.getInputStream();    									
    				
    				FileOutputStream fos = new FileOutputStream(localImgUrl);
									
					int len = conn.getContentLength();										
					byte[] raster = new byte[len];

					int Read=0;
					while(true) {
						Read = is.read(raster);
						if (Read <= 0) {
							break;
						}
						fos.write(raster, 0, Read);
					}											
														
					is.close();
					fos.close();
					conn.disconnect();
					
					//저장된 이미지 표시함
					Bitmap bm = BitmapFactory.decodeFile(localImgUrl);
	    			if(bm!=null)
	    				imageView.setImageBitmap(bm);
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}
    		}
    	}
    	else
    	{    		
    		imageView.setImageBitmap(bitmap);
    	}
    }
    
    public Bitmap imageViewProcessing(String localImgUrl, String ServerImgUrl )
    {   
    	// 1. Cache Check
    	Bitmap bm = getBitmapFromCache(localImgUrl);
    	if(bm != null) return bm; 
    	
    	// 2. File Check
    	File file = new File(localImgUrl);    	
    	if(file.exists()) 
    	{    		
    		bm = BitmapFactory.decodeFile(file.getAbsolutePath());
    		if(bm != null) return bm;
    		else file.delete();
    	}
    	else {
    		boolean b = file.mkdirs();
    		b = file.delete();
    		//b = dir.createNewFile();
    	}

    	// 3. Download Image
    	try
    	{
    		URL url = new URL(ServerImgUrl);
    		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    		InputStream is = conn.getInputStream();    									
    		FileOutputStream fos = new FileOutputStream(localImgUrl);

    		int len = conn.getContentLength();										
    		byte[] raster = new byte[len];

    		int read = 0;
    		while(true) {
    			read = is.read(raster);
    			if (read <= 0)	break;
    			fos.write(raster, 0, read);
    		}											

    		is.close();
    		fos.close();
    		conn.disconnect();

    		bm = BitmapFactory.decodeFile(localImgUrl);
    		if(bm != null) return bm;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
    
    IHttpConnectionLayer _httpLayer;
    String _domain= "http://wable.co.kr/";    
    public void uploadImage(String localImgUrl)
    {
    	
    	Map<String,Object> params = new HashMap<String,Object>();
    	String fileName = "upload_test";
    	params.put("fileName", fileName);
    	    	
		final Map<String,Object> files = new HashMap<String,Object>();
		files.put("filepath", localImgUrl);
    	
    	_httpLayer.POSTFileSync(_domain+"Setting/ImageUpload", params, files);
    
    }
    
    /*
     * Same as download but the image is always downloaded and the cache is not used.
     * Kept private at the moment as its interest is not clear.
       private void forceDownload(String url, ImageView view) {
          forceDownload(url, view, null);
       }
     */

    /**
     * Same as download but the image is always downloaded and the cache is not used.
     * Kept private at the moment as its interest is not clear.
     */
    private void forceDownload(String url, ImageView imageView) {
        // State sanity: url is guaranteed to never be null in DownloadedDrawable and cache keys.
        if (url == null) {
            imageView.setImageDrawable(null);
            return;
        }

        if (cancelPotentialDownload(url, imageView)) {
            switch (mode) {
                case NO_ASYNC_TASK:
                    Bitmap bitmap = downloadBitmap(url);
                    addBitmapToCache(url, bitmap);
                    imageView.setImageBitmap(bitmap);
                    break;

                case NO_DOWNLOADED_DRAWABLE:
                    imageView.setMinimumHeight(156);
                    BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
                    task.execute(url);
                    break;

                case CORRECT:
                    task = new BitmapDownloaderTask(imageView);
                    DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
                    imageView.setImageDrawable(downloadedDrawable);
                    imageView.setMinimumHeight(156);
                    task.execute(url);
                    break;
            }
        }
    }

    /**
     * Returns true if the current download has been canceled or if there was no download in
     * progress on this image view.
     * Returns false if the download in progress deals with the same url. The download is not
     * stopped in that case.
     */
    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    /**
     * @param imageView Any imageView
     * @return Retrieve the currently active download task (if any) associated with this imageView.
     * null if there is no such task.
     */
    private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    Bitmap downloadBitmap(String url) {
        final int IO_BUFFER_SIZE = 4 * 1024;

        // AndroidHttpClient is not allowed to be used from the main thread
        final HttpClient client = (mode == Mode.NO_ASYNC_TASK) ? new DefaultHttpClient() :
            AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    // return BitmapFactory.decodeStream(inputStream);
                    // Bug on slow connections, fixed in future release.
                    return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            if ((client instanceof AndroidHttpClient)) {
                ((AndroidHttpClient) client).close();
            }
        }
        return null;
    }

    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    /**
     * The actual AsyncTask that will asynchronously download the image.
     */
    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        /**
         * Actual download method.
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            return downloadBitmap(url);
        }

        /**
         * Once the image is downloaded, associates it to the imageView
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            addBitmapToCache(url, bitmap);

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with it
                // Or if we don't use any bitmap to task association (NO_DOWNLOADED_DRAWABLE mode)
                if ((this == bitmapDownloaderTask) || (mode != Mode.CORRECT)) {
                    imageView.setImageBitmap(bitmap);
                    
                    String name = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
                    try {
						FileOutputStream out = new FileOutputStream(files + name);
						bitmap.compress(CompressFormat.JPEG, 80, out);
						out.close();
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

                    
                    
                }
            }
        }
    }


    /**
     * A fake Drawable that will be attached to the imageView while the download is in progress.
     *
     * <p>Contains a reference to the actual download task, so that a download task can be stopped
     * if a new binding is required, and makes sure that only the last started download process can
     * bind its result, independently of the download finish order.</p>
     */
    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
            super(Color.BLACK);
            bitmapDownloaderTaskReference =
                new WeakReference<BitmapDownloaderTask>(bitmapDownloaderTask);
        }

        public BitmapDownloaderTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        clearCache();
    }

    
    /*
     * Cache-related fields and methods.
     * 
     * We use a hard and a soft cache. A soft reference cache is too aggressively cleared by the
     * Garbage Collector.
     */
    
    private static final int HARD_CACHE_CAPACITY = 10;
    private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds

    // Hard cache, with a fixed maximum capacity and a life duration
    private final HashMap<String, Bitmap> sHardBitmapCache =
        new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(LinkedHashMap.Entry<String, Bitmap> eldest) {
            if (size() > HARD_CACHE_CAPACITY) {
                // Entries push-out of hard reference cache are transferred to soft reference cache
                sSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                return true;
            } else
                return false;
        }
    };

    // Soft cache for bitmaps kicked out of hard cache
    private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache =
        new ConcurrentHashMap<String, SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2);

    private final Handler purgeHandler = new Handler();

    private final Runnable purger = new Runnable() {
        public void run() {
            clearCache();
        }
    };

    /**
     * Adds this bitmap to the cache.
     * @param bitmap The newly downloaded bitmap.
     */
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (sHardBitmapCache) {
                sHardBitmapCache.put(url, bitmap);
            }
        }
    }

    /**
     * @param url The URL of the image that will be retrieved from the cache.
     * @return The cached bitmap or null if it was not found.
     */
    private Bitmap getBitmapFromCache(String url) {
        // First try the hard reference cache
        synchronized (sHardBitmapCache) {
            final Bitmap bitmap = sHardBitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in hard cache
                // Move element to first position, so that it is removed last
                sHardBitmapCache.remove(url);
                sHardBitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        // Then try the soft reference cache
        SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
        if (bitmapReference != null) {
            final Bitmap bitmap = bitmapReference.get();
            if (bitmap != null) {
                // Bitmap found in soft cache
                return bitmap;
            } else {
                // Soft reference has been Garbage Collected
                sSoftBitmapCache.remove(url);
            }
        }

        return null;
    }
 
    /**
     * Clears the image cache used internally to improve performance. Note that for memory
     * efficiency reasons, the cache will automatically be cleared after a certain inactivity delay.
     */
    public void clearCache() {
        sHardBitmapCache.clear();
        sSoftBitmapCache.clear();
    }

    /**
     * Allow a new delay before the automatic cache clear is done.
     */
    private void resetPurgeTimer() {
        purgeHandler.removeCallbacks(purger);
        purgeHandler.postDelayed(purger, DELAY_BEFORE_PURGE);
    }
}

