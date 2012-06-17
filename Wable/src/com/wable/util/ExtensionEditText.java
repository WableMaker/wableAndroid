package com.wable.util;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * EditText EditText 위에 키보드가 떠있는 경우 뒤로 가기 버튼을 감지하기 위한 EditText 확장 클래스
 * 
 * @author GHLab
 */
public class ExtensionEditText extends EditText {

	private Context context;
	private Handler handler = null;

	private boolean isHiddenKeyboard = true;

	public ExtensionEditText(Context context) {
		super(context);
		this.context = context;
	}

	public ExtensionEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExtensionEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {		
		
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

			if (handler != null) {

				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					handler.sendEmptyMessage(InputMethodManager.RESULT_HIDDEN);
				}
			}

			if (isHiddenKeyboard == false) {
				return true;
			}
		}
		return super.onKeyPreIme(keyCode, event);
	}
	
//	@Override
//	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
//
//		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//
//			if (handler != null) {
//
//				if (event.getAction() == KeyEvent.ACTION_DOWN) {
//					handler.sendEmptyMessage(InputMethodManager.RESULT_HIDDEN);
//				}
//			}
//
//			if (isHiddenKeyboard == false) {
//				return true;
//			}
//		}
//
//		return super.dispatchKeyEvent(event);
//	}

	public void setHiddenKeyboardOnBackPressed(boolean isHiddenKeyboard) {
		this.isHiddenKeyboard = isHiddenKeyboard;
	}

	/**
	 * back 버튼 후 실행될 핸들러 지정
	 * @param handler back 버튼 후 실행될 핸들러
	 */
	public void setOnBackPressedHandler(Handler handler) {
		this.handler = handler;
	}
}
