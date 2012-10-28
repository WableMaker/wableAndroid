package com.thx.bizcat.util;

import java.io.File;
import java.text.DecimalFormat;

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


}
