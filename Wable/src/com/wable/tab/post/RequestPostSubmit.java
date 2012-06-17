package com.wable.tab.post;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
	
	private Button btPrice, btTime;
	
	
	private Context context;
	
	private boolean isLock;	
	
	private SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_submit);
		context = this;
		findViewById(R.id.POST_RQ_SBbtnCancel).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnCategory).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnDetail).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnLoc).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnPrice).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnSubmit).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnTime).setOnClickListener(this);
		
		
		mapview = (MapView)findViewById(R.id.mapview);
		tvAddr = (TextView)findViewById(R.id.textPostSubmitAddr);
		
		mapCtrl = mapview.getController();
		mapCtrl.setZoom(16);
		
		manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
		
		geo = new Geocoder(this, Locale.KOREAN);
		
		pin = new ImageView(this);
		isLock = false;
		
		btPrice = (Button)findViewById(R.id.POST_RQ_SBbtnPrice);
		btTime = (Button)findViewById(R.id.POST_RQ_SBbtnTime);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(getIntent().getBooleanExtra("FIRST", false)) {
		
			btPrice.setText( pref.getString("POST_PRICE", "비용"));
			btTime.setText(pref.getString("POST_TIME", "시간"));
		//pref.getString("POST_STR", "상세설명");
		}
	}
	
	@Override
	public void onClick(View v) {
		
		Intent i;
		View view;
		final EditText et;
		final AlertDialog d = new AlertDialog.Builder(context).setNegativeButton("취소", null).create();
		
		switch (v.getId()) {
		
		case R.id.POST_RQ_SBbtnCategory:
			i = new Intent(this, RequestCategory.class);			
			
			Editor editor = pref.edit();
			editor.putString("POST_PRICE", btPrice.getText().toString());
			editor.putString("POST_TIME", btTime.getText().toString());
			editor.putString("POST_STR", "");
			editor.commit();

			startActivity(i);
			break;
			
		case R.id.POST_RQ_SBbtnDetail:
			
			break;
			
		case R.id.POST_RQ_SBbtnPrice:
			
			view = getLayoutInflater().inflate(R.layout.post_request_dialog, null); 
			et = (EditText)view.findViewById(R.id.POST_RQ_DIALedit);
			et.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
					
					if(arg1 == EditorInfo.IME_ACTION_DONE) {
						
						btPrice.setText(new DecimalFormat("###,###").format( Long.parseLong(et.getText().toString())) +" 원");
						
					}
					return false;
				}
			});
			
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
			
			
//			d = new AlertDialog.Builder(context).setPositiveButton("적용", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					
//					btPrice.setText(new DecimalFormat("###,###").format( Long.parseLong(et.getText().toString())) + " 원");
//					
//				}
//
//			}).setNegativeButton("취소", null).setView(view).setTitle("가격을 입력하세요.").create();

			d.setView(view);
			d.setButton("적용", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					btPrice.setText(new DecimalFormat("###,###").format( Long.parseLong(et.getText().toString())) + " 원");
				}
			});
			
			d.setTitle("가격을 입력하세요.");
			d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			d.show();
			
			break;
			
		case R.id.POST_RQ_SBbtnTime:
			
			view = getLayoutInflater().inflate(R.layout.post_request_dialog, null); 
			et = (EditText)view.findViewById(R.id.POST_RQ_DIALedit);
			et.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
					
					if(arg1 == EditorInfo.IME_ACTION_DONE) {
						
						btTime.setText(et.getText() + " 시간");
						d.dismiss();
						
					}
					return false;
				}
			});
			
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
//			d = new AlertDialog.Builder(context).setPositiveButton("적용", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					
//					btTime.setText(et.getText() +" 시간");
//					
//				}
//
//			}).setNegativeButton("취소", null).setView(view).setTitle("시간을 입력하세요.").create();
//
//			
//			d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//			d.show();
			
			break;
			
		case R.id.POST_RQ_SBbtnSubmit:
			
			break;
			
		case R.id.POST_RQ_SBbtnLoc:
			
			break;
			
		case R.id.POST_RQ_SBbtnCancel:
			//i = new Intent(this, RequestPostList.class);
			//startActivity(i);
			break;
			

		}
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		if(isLock) return;
		
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
				buff.append(a.getSubLocality() + " 근처");
				
			}
			
			tvAddr.setText(buff.toString());
			
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
