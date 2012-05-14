package com.wable.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;

public class HttpConnectionHelper {
	
	public static void excuteHttpMethod(Object obj, IhttpCallback callback) throws JSONException {
		
		try{
			
			URL url = new URL("http://1.241.72.164:9877/TEST" + obj);

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if (null != conn) {

				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestMethod("POST");					

				StringBuilder sb = new StringBuilder();				
				sb.append("TEST=").append("馬馬馬馬");

				PrintWriter wr = new PrintWriter( new OutputStreamWriter(conn.getOutputStream()));					 
				wr.write(sb.toString());
				wr.flush();


				final int response = conn.getResponseCode();
				if (response == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

					String line = "";						
					while (true) {
						String r = br.readLine();
						if (null == r) break;
						line += r;

					}
					br.close();
					callback.OnCallback(new JSONArray(line));
					wr.close();

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
