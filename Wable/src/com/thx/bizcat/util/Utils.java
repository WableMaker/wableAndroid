package com.thx.bizcat.util;

import java.io.File;
import java.text.DecimalFormat;

import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnTouchListener;

import com.thx.bizcat.R;

public class Utils {
	
	/**
	 * Ư�� ��ο� ���� �� ���� ��� ����
	 * @param path ���� Ư�� ���
	 */
	public static void DeleteFolder(String path) 
	{
	    File file = new File(path);
	    File[] childFileList = file.listFiles();
	    
	    if(file.exists()) {
	    
		    for(File childFile : childFileList)
		    {
		        if(childFile.isDirectory()) DeleteFolder(childFile.getAbsolutePath());
		        else  childFile.delete(); 
		    }	    
		    file.delete();  
		    
	    }
	}
	

	public static String ConvertStringToMoney(String money) 
	{
		return new DecimalFormat("###,###").format(Long.parseLong(money));
	}
	
	
	//프레그먼트
//	menu.setOnTouchListener(new OnTouchListener() {
//		
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//
//			if(event.getAction() == MotionEvent.ACTION_UP) {
//
//				v.playSoundEffect(SoundEffectConstants.CLICK);
//				int one =  v.getWidth() / 2;
//
//				int pos = (int)event.getX() / one;
//				if(position == pos) return false;
//
//				switch(pos) {
//				case 0:
//					menu.setBackgroundResource(R.drawable.tab1_buying);
//					vs.showNext();
//					break;
//				case 1:
//					menu.setBackgroundResource(R.drawable.tab1_selling);
//					vs.showNext();
//					break;
//				}
//				position = pos;
//			}
//			return false;
//		}
//	});


}
