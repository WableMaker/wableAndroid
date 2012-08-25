package com.wable.tab.post;

import com.wable.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Bundle;

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
