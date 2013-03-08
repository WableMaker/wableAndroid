package com.thx.bizcat.tab.post;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;

public class PostRequestActivity extends MapActivity implements LocationListener, OnClickListener {

	private Geocoder geo;
	private LocationManager manager;
	
	private MapView mapview;
	private MapController mapCtrl;
	private ImageView pin;
	private double lat, lon;
	
	private TextView tvAddr, tvDetail;
	private Button btPrice, btTime, btCategory;
	
	private Context context;
	
	private boolean isLock;	
	private SharedPreferences pref;
	
	Integer category;
	
	/* Handler */
	private Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch(APICODE.fromInt(msg.what)) {
			
			case RequestAdd:
//				if(success)
//					Toast.makeText(context, "제공 등록완료", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.go_biz_post_req);
		context = this;
		
		findViewById(R.id.GOBIZ_REQtvTitle).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvPrice).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvTime).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvMapBtn).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvGoBtn).setOnClickListener(this);
		
		mapview = (MapView)findViewById(R.id.mapview);
		//tvAddr = (TextView)findViewById(R.id.textPostSubmitAddr);
		
		mapCtrl = mapview.getController();
		mapCtrl.setZoom(16);
		
		manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
		
		geo = new Geocoder(this, Locale.KOREAN);		
		pin = new ImageView(this);
		isLock = false;
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(getIntent().getBooleanExtra("FIRST", false)) {
		
			String price = pref.getString("POST_PRICE", "");
			String time = pref.getString("POST_TIME", "");
			
			btPrice.setText( price + " 원");
			btTime.setText( time + " 시간");
			
			btPrice.setTag( price );
			btTime.setTag( time );
			
			tvDetail.setText(pref.getString("POST_STR", "제공등록"));
			category = pref.getInt("POST_CATE", 0);
			
			Editor editor = pref.edit();
			editor.remove("FIRST");
			editor.commit();
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.GOBIZ_REQtvTitle:
			
			break;

		}
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		if(isLock) return;
		
		StringBuffer buff = new StringBuffer();
		lat = location.getLatitude();
		lon = location.getLongitude();
		
		GeoPoint gp = new GeoPoint((int)(lat * 1000000), (int)(lon*1000000));
		
		mapCtrl.animateTo(gp);
		
		pin.setImageResource(R.drawable.map_pin);
		MapView.LayoutParams lp = new MapView.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gp, LayoutParams.CENTER);
		
		mapview.removeView(pin);
		mapview.addView(pin, lp);

		try {
			List<Address> addr = geo.getFromLocation(lat, lon, 1);
			
			for(Address a : addr) {
				
				buff.append(a.getLocality() + " ");
				buff.append(a.getSubLocality() + " ");
				
			}
			
			//tvAddr.setText(buff.toString());
			Toast.makeText(context, buff.toString(), Toast.LENGTH_SHORT).show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		isLock = true;
		
	}
	
	@Override
	protected void onPause() {
		manager.removeUpdates(this);
		super.onPause();
	}
	
	@Override
	protected boolean isRouteDisplayed() { return false; }

	@Override
	public void onProviderDisabled(String arg0) { }

	@Override
	public void onProviderEnabled(String arg0) { }

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) { }


}
