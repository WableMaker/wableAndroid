package com.thx.bizcat.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Serializes {
	
	/**
	 * ����ȭ ���Ͽ� ������Ʈ ����
	 * @param strPath ���ϰ��
	 * @param obj ��ü
	 */
	public static void writeObject(String strPath, Object obj) {
		
		try {
			
			FileOutputStream fos = new FileOutputStream(strPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			fos.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * ����ȭ ���Ͽ��� ������Ʈ �б�
	 * @param strPath ���ϰ��
	 * @return ������Ʈ
	 */
	public static Object readObject(String strPath) {

		Object obj = null;
		try{
			FileInputStream fis = new FileInputStream(strPath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;

	}
	
	public static byte[] serializeObject(Object o) {     
		ByteArrayOutputStream bos = new ByteArrayOutputStream();     
		try {       
			ObjectOutput out = new ObjectOutputStream(bos);       
		out.writeObject(o);       
		out.close();        // Get the bytes of the serialized object       
		byte[] buf = bos.toByteArray();        return buf;     
		} catch(IOException ioe) {       
			return null;    
		}  
	} 




	
	
	
	

}
