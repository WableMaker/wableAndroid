package com.thx.bizcat.util;

import com.thx.bizcat.WableActivity;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

public class InterceptHWkeyLayout extends RelativeLayout {

    private WableActivity mHWActivity;;

    public InterceptHWkeyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptHWkeyLayout(Context context) {
        super(context);
    }

    public void setInterceptHWkeyLayout(WableActivity searchActivity) {
    	mHWActivity = searchActivity;
    }

    /**
     * Overrides the handling of the back key to move back to the 
     * previous sources or dismiss the search dialog, instead of 
     * dismissing the input method.
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
//        if (mHWActivity != null && 
//                    event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            KeyEvent.DispatcherState state = getKeyDispatcherState();
//            if (state != null) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN
//                        && event.getRepeatCount() == 0) {
//                    state.startTracking(event, this);
//                    return true;
//                } else if (event.getAction() == KeyEvent.ACTION_UP
//                        && !event.isCanceled() && state.isTracking(event)) {
//                	mHWActivity.onBackPressed();
//                    return true;
//                }
//            }
//        }
    	
    	InputMethodManager imm = (InputMethodManager) mHWActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm.isActive() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            	imm.hideSoftInputFromInputMethod(getApplicationWindowToken(), 0);
            	mHWActivity.upLayoutF();
            }

        return super.dispatchKeyEventPreIme(event);
    }

	
}


