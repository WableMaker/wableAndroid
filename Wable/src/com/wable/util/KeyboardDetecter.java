package com.wable.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;

public class KeyboardDetecter extends View {

	private boolean key;     
	private OnShownKeyboardListener onShow;     
	private OnHiddenKeyboardListener onHidden; 
	
	public KeyboardDetecter(Context context) {
		super(context);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		Activity activity = (Activity)getContext();         
		Rect rect = new Rect();         
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);         
		int statusBarHeight = rect.top;         
		int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();  
		
		int diffHeight = (screenHeight - statusBarHeight) - h;         
		if (diffHeight > 100 && !key) { // 모든 키보드는 100px보다 크다고 가정            
			key = true;             
			onShownSoftKeyboard();         
		} else if (diffHeight < 100 && key) {            
			key = false;             
			onHiddenSoftKeyboard();        
		} 

		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	public interface OnShownKeyboardListener { public void onShow(); }    
	public void onShownSoftKeyboard() { if (onShow != null)	onShow.onShow(); }
	public void setOnShownKeyboard(OnShownKeyboardListener listener) { onShow = listener; }     
	
	public interface OnHiddenKeyboardListener { public void onHidden(); } 
	public void onHiddenSoftKeyboard() { if (onHidden != null) onHidden.onHidden(); } 	
	public void setOnHiddenKeyboard(OnHiddenKeyboardListener listener) { onHidden = listener; } 

}
