package com.wable.util;

import java.io.File;

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


}
