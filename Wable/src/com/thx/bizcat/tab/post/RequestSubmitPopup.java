package com.thx.bizcat.tab.post;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.thx.bizcat.R;

public class RequestSubmitPopup extends Dialog {
	
	public RequestSubmitPopup(Context context, int theme) {
		super(context, theme);
	}
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_request_submit_input);
		
	}

}
