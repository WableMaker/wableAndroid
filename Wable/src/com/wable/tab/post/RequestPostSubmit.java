package com.wable.tab.post;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.wable.R;

public class RequestPostSubmit extends MapActivity implements LocationListener, OnClickListener {

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
		
		findViewById(R.id.POSTbtnSubmitTitle).setOnClickListener(this);
		findViewById(R.id.POSTbtnSubmitDetail).setOnClickListener(this);
		findViewById(R.id.POSTbtnService).setOnClickListener(this);
		findViewById(R.id.POSTbtnTime).setOnClickListener(this);
		findViewById(R.id.POSTbtnCancel).setOnClickListener(this);
		findViewById(R.id.POSTbtnRequestList).setOnClickListener(this);
		
		
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
	public void onClick(View v) {
		
		Intent i;
		
		switch (v.getId()) {
		
		
		case R.id.POSTbtnSubmitTitle:
			i = new Intent(this, RequestCategory.class);
			startActivity(i);
			break;
			
		case R.id.POSTbtnSubmitDetail:
			
			break;
			
		case R.id.POSTbtnService:
			
			break;
			
		case R.id.POSTbtnTime:
			
			break;
			
		case R.id.POSTbtnCancel:
			
			break;
			
		case R.id.POSTbtnRequestList:
			i = new Intent(this, RequestPostList.class);
			startActivity(i);
			break;
			

		}
		
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
