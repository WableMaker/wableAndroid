package com.thx.bizcat.tab.post;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONObject;

import android.R.style;
import android.content.Context;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.thx.bizcat.R;
import com.thx.bizcat.http.apiproxy.APIProxyLayer;
import com.thx.bizcat.http.apiproxy.IAPIProxyCallback;

public class RequestPostSubmit extends MapActivity implements LocationListener, OnClickListener {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_submit);
		context = this;
		
		findViewById(R.id.POST_RQ_SBbtnCancel).setOnClickListener(this);
		//findViewById(R.id.POST_RQ_SBbtnCategory).setOnClickListener(this);
		//findViewById(R.id.POST_RQ_SBbtnDetail).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnLoc).setOnClickListener(this);
		//findViewById(R.id.POST_RQ_SBbtnPrice).setOnClickListener(this);
		findViewById(R.id.POST_RQ_SBbtnSubmit).setOnClickListener(this);
		//findViewById(R.id.POST_RQ_SBbtnTime).setOnClickListener(this);
		
		btPrice = (Button)findViewById(R.id.POST_RQ_SBbtnPrice);
		btTime = (Button)findViewById(R.id.POST_RQ_SBbtnTime);
		btCategory = (Button)findViewById(R.id.POST_RQ_SBbtnCategory);
		tvDetail = (TextView)findViewById(R.id.POST_RQ_SBbtnDetail);
		
		btPrice.setOnClickListener(this);
		btTime.setOnClickListener(this);
		btCategory.setOnClickListener(this);
		tvDetail.setOnClickListener(this);
		
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
		
		Intent data;
		View view;
		final EditText et;
		//final AlertDialog d = new AlertDialog.Builder(context).setNegativeButton("���", null).create();
		
		switch (v.getId()) {
		
		case R.id.POST_RQ_SBbtnCategory:
			
			
			data = new Intent(this, RequestCategory.class);			
			
			Editor editor = pref.edit();
			editor.putString("POST_PRICE", btPrice.getText().toString());
			editor.putString("POST_TIME", btTime.getText().toString());
			editor.putString("POST_STR", "");
			editor.commit();

			startActivity(data);
			break;
			
		case R.id.POST_RQ_SBbtnDetail:
			
			data = new Intent(this, RequestSubmitPopup.class);
			startActivity(data);
			
			break;
			
		case R.id.POST_RQ_SBbtnPrice:

			//data = new Intent(this, RequestSubmitPopup.class);			
			//startActivity(data);
			
			RequestSubmitPopup d = new RequestSubmitPopup(context, style.Theme_Dialog);
			d.setTitle("");
			d.show();
			
			break;
			
		case R.id.POST_RQ_SBbtnTime:
			
			data = new Intent(this, RequestSubmitPopup.class);
			startActivity(data);
			
//			view = getLayoutInflater().inflate(R.layout.post_request_dialog, null); 
//			et = (EditText)view.findViewById(R.id.POST_RQ_DIALedit);
//			et.setOnEditorActionListener(new OnEditorActionListener() {
//				
//				@Override
//				public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
//					
//					if(arg1 == EditorInfo.IME_ACTION_DONE) {
//						btTime.setText(et.getText() + " �ð�");
//						btTime.setTag( et.getText() );
//						d.dismiss();
//					}
//					return false;
//				}
//			});
//			et.setInputType(InputType.TYPE_CLASS_NUMBER);
//			
//			d.setView(view);
//			d.setButton("���", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					btTime.setText(et.getText() +" �ð�");
//					btTime.setTag( et.getText() );
//					
//				}
//			});
//			
//			d.setTitle("�ð��� �Է��ϼ���.");
//			d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//			d.show();
			
			break;
			
		case R.id.POST_RQ_SBbtnSubmit:
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			int price = Integer.parseInt(btPrice.getTag().toString());
			c.add(Calendar.HOUR, Integer.parseInt(btTime.getTag().toString()));
			
			APIProxyLayer.Instance().RequestAdd(
					btCategory.getText().toString(),
					tvDetail.getText().toString(), 
					price, category, c.getTime(), lat, lon, false, false, 
					new IAPIProxyCallback() {
						
						@Override
						public void OnCallback(boolean success, JSONObject json) {
							if(success)
								Toast.makeText(context, "제공 등록완료", Toast.LENGTH_SHORT).show();
						}
					});
			break;
			
		case R.id.POST_RQ_SBbtnLoc:
			
			break;
			
		case R.id.POST_RQ_SBbtnCancel:
			finish();
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
				buff.append(a.getSubLocality() + " ��ó");
				
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
