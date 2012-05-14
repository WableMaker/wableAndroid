package com.wable.post;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.wable.R;

public class RequestPostSubmit extends MapActivity implements LocationListener {

	private Geocoder geo;
	private LocationManager manager;
	
	private MapView mapview;
	private MapController mapCtrl;
	private ImageView pin;
	
	private TextView tvAddr;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_submit);
		
		mapview = (MapView)findViewById(R.id.mapview);
		tvAddr = (TextView)findViewById(R.id.textPostSubmitAddr);
		
		mapCtrl = mapview.getController();
		mapCtrl.setZoom(16);
		
		manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
		
		geo = new Geocoder(this, Locale.KOREAN);
		
		pin = new ImageView(this);
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		StringBuffer buff = new StringBuffer();
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		GeoPoint gp = new GeoPoint((int)(latitude * 1000000), (int)(longitude*1000000));
		
		mapCtrl.animateTo(gp);
		
		pin.setImageResource(R.drawable.map_pin);
		MapView.LayoutParams lp = new MapView.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gp, LayoutParams.CENTER);
		
		mapview.removeView(pin);
		mapview.addView(pin, lp);

		try {
			List<Address> addr = geo.getFromLocation(latitude, longitude, 1);
			
			for(Address a : addr) {
				
				buff.append(a.getLocality() + " ");
				buff.append(a.getSubLocality() + " ±Ÿ√≥");
				
			}
			
			tvAddr.setText(buff.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
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
