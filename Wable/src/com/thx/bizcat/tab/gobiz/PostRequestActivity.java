package com.thx.bizcat.tab.gobiz;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.Overlay;
import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APICODE;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_LogIn_Items;
import com.thx.bizcat.http.apiproxy.JSONParser.sp_Simple_Items;

public class PostRequestActivity extends MapActivity implements LocationListener, OnClickListener {

	private Geocoder geo;
	private LocationManager manager;
	
	private MapView mapview;
	private MapController mapCtrl;
	private ImageView pin;
	private double lat, lon;
	
	private Context context;
	
	private boolean isLock;	
	private SharedPreferences pref;
	
	private List<Overlay> overlays;
	private TextView tvTitle, tvDesc, tvPrice, tvTime;
	private String strDesc, strPrice, strTime;
	private long ticks = 0;
	
	Integer category;
	
	/* Handler */
	private Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch(APICODE.fromInt(msg.what)) {
			
			case RequestAdd:
					sp_Simple_Items item = (sp_Simple_Items)msg.obj;
					if(item.bsuccess)
						Toast.makeText(context, "제공 등록완료", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(context, "네트워크 오류 잠시 후 다시시도해 주세요", Toast.LENGTH_SHORT).show();
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
		findViewById(R.id.GOBIZ_REQtvDesc).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvPrice).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvTime).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvMapBtn).setOnClickListener(this);
		findViewById(R.id.GOBIZ_REQtvGoBtn).setOnClickListener(this);
		
		tvTitle = (TextView) findViewById(R.id.GOBIZ_REQtvTitle);
		tvDesc = (TextView) findViewById(R.id.GOBIZ_REQtvDesc);
		tvPrice = (TextView) findViewById(R.id.GOBIZ_REQtvPrice);
		tvTime = (TextView) findViewById(R.id.GOBIZ_REQtvTime);
		
		mapview = (MapView)findViewById(R.id.mapview);
		mapview.setBuiltInZoomControls(false);
		mapview.setSatellite(false);
		
		mapCtrl = mapview.getController();
		mapCtrl.setZoom(16);
		
		manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
		
		overlays = mapview.getOverlays();
		overlays.clear();
		overlays.add(new MapOverlay());
		
		geo = new Geocoder(this, Locale.KOREAN);		
		pin = new ImageView(this);
		pin.setImageResource(R.drawable.map_pin);
		isLock = false;
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);

		
	}
	
	class MapOverlay extends com.google.android.maps.Overlay
    {
	
		@Override
		public boolean onTap(GeoPoint gp, MapView mapView) {
			
			mapCtrl.animateTo(gp);		
			MapView.LayoutParams lp = new MapView.LayoutParams
					(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gp, LayoutParams.CENTER);
			
			mapview.removeView(pin);
			mapview.addView(pin, lp);		
			
			lat = gp.getLatitudeE6() / 1000000;
			lon = gp.getLongitudeE6() / 1000000;
			
			return super.onTap(gp, mapView);
		}
		
    }
	
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.GOBIZ_REQtvTitle:
			
			break;
			
		case R.id.GOBIZ_REQtvDesc:
			
			Intent intent = new Intent(this, SelectTabActivity.class);
			startActivityForResult(intent, 0);
					
			break;
					
		case R.id.GOBIZ_REQtvPrice:
			
			break;
			
		case R.id.GOBIZ_REQtvTime:
			
			break;
			
		case R.id.GOBIZ_REQtvMapBtn:
			isLock = false;
			break;
			
		case R.id.GOBIZ_REQtvGoBtn:
			
			java.util.Date dt = new Date(System.currentTimeMillis() + ticks);
			APIProxyLayer.Instance().RequestAdd("TEST POST", strDesc, Integer.parseInt(strPrice), category, dt, lat, lon, null, null, mHandler);
			
			
			break;

		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == 0 && resultCode == RESULT_OK) {
			
			strDesc = pref.getString("TEMP_DESC", "");
			strPrice = pref.getString("TEMP_PRICE", "");
			strTime = pref.getString("TEMP_TIME", "");
			ticks = pref.getLong("TEMP_TICK", 0);
			
			tvDesc.setText(strDesc);
			tvPrice.setText(strPrice);
			tvTime.setText(strTime);
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		if(isLock) return;
		
		StringBuffer buff = new StringBuffer();
		lat = location.getLatitude();
		lon = location.getLongitude();
		
		GeoPoint gp = new GeoPoint((int)(lat * 1000000), (int)(lon*1000000));
		
		mapCtrl.animateTo(gp);
		
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
		if(manager != null) {
			manager.removeUpdates(this);
		}
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		
		if(manager != null) {
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
		}
		super.onResume();
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
