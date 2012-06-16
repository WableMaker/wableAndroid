package com.wable.util;

import java.io.File;

public class Utils {
	
	/**
	 * 특정 경로에 파일 및 하위 경로 삭제
	 * @param path 지울 특정 경로
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
