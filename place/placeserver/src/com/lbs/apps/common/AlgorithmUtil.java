package com.lbs.apps.common;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class AlgorithmUtil {	
	public static String getStringEncrypt(String key, int random) {

		String result ="";
		switch (random) {
		case 1:
			result =getStringEncryptByIndex1(key);
			break;
		case 2:
			result =getStringEncryptByIndex2(key);
			break;
		case 3:
			result =getStringEncryptByIndex3(key);
			break;
		case 4:
			result =getStringEncryptByIndex4(key);
			break;
		case 5:
			result =getStringEncryptByIndex5(key);
			break;
		}

		return result;
	}
	
	public static String getRandomString(String key){
		
        StringBuffer temp_key =new StringBuffer();
		char[] keys =key.toCharArray();
		for(int i=0;i<keys.length;i++){
			
			temp_key.append(keys[i]);
			temp_key.append((int)(Math.random()*10));
		}
		return temp_key.toString();
	}

	private static String getStringEncryptByIndex1(String key) {
		   
		String result ="";
		StringBuffer temp_key =new StringBuffer();
		
		char[] keys =key.toCharArray();
		for(int i=0;i<keys.length;i+=2){
			
			if((i +1) < keys.length){
				char char_next =keys[i+1];
				char char_pre =keys[i];
				int int_next =char_next;
				int int_pre =char_pre;
				int int_result =int_next>int_pre?int_next -int_pre:int_pre -int_next;
				if(int_result <33)
					int_result +=33;
				
				char char_result =(char)int_result;
				temp_key.append(char_result);
				temp_key.append(char_next);
			} else{
				temp_key.append(keys[i]);
			}
		}
		
		result =getStringOddEven(temp_key.toString());

		result =Md5Util.toMD5(result);
		
		result =result.substring(0, 10);
		
		return result;
	}
	
	private static String getStringEncryptByIndex2(String key) {

		String result ="";
		result =key.substring(1,key.length()-1);
		result =getStringOddEven(result);
		result =Md5Util.toMD5(result);		
		result =result.substring(11,21);
		return result;
	}

	//
	private static String getStringEncryptByIndex3(String key) {

		String result ="";
		result =key.substring(0,1)+"##"+key.substring(1);
		result =getStringOddEven(result);
		result =Md5Util.toMD5(result);
		
		char[] keys =result.toCharArray();
		StringBuffer temp_key =new StringBuffer();
		for(int i=0;i<keys.length;i++){
			
			if((i+1) %2 !=0 && (i+1)<20){
				temp_key.append(keys[i]);
			}
			if(i >20)
				break;
		}
		result =temp_key.toString();
		return result;
	}

	//
	private static String getStringEncryptByIndex4(String key) {

		String result ="";
		char[] keys =key.toCharArray();
		StringBuffer temp_key =new StringBuffer();
		for(int i=0;i<keys.length;i++){
			
			char char_result =(char) (keys[i]+3);
			temp_key.append(char_result);
		}
		result =getStringOddEven(temp_key.toString());
		result =Md5Util.toMD5(result);
		
		
		keys =result.toCharArray();
		temp_key =new StringBuffer();
		for(int i=0;i<keys.length;i++){
			
			if((i+1) %2 ==0 && (i+1)<22 && (i+1)>=2){
				temp_key.append(keys[i]);
			}
			
			if(i >20)
				break;
		}
		result =temp_key.toString();
		return result;
	}

	//
	private static String getStringEncryptByIndex5(String key) {
		String result ="";
		StringBuffer temp_key =new StringBuffer();
		
		char[] keys =key.toCharArray();
		for(int i=0;i<keys.length;i+=2){
			
			if((i +1) < keys.length){
				char char_next =keys[i+1];
				char char_pre =keys[i];
				int int_next =char_next;
				int int_pre =char_pre;
				int int_result =int_next>int_pre?int_next /int_pre:int_pre /int_next;
				if(int_result <33)
					int_result +=33;
				
				char char_result =(char)int_result;
				temp_key.append(char_pre);
				temp_key.append(char_result);
			} else{
				temp_key.append(keys[i]);
			}
		}
		result =getStringOddEven(temp_key.toString());
		result =Md5Util.toMD5(result);
		 
		
		keys =result.toCharArray();
		temp_key =new StringBuffer();
		for(int i=0;i<keys.length;i++){
			
			if((i+1) %2 ==0 && (i+1)>12){
				temp_key.append(keys[i]);
			}
		}
		result =temp_key.toString();
		return result;
	}
	
    private static String getStringOddEven(String key){
		
		char[] keys =key.toCharArray();
		StringBuffer result_key =new StringBuffer();
		for(int i=0;i<keys.length;i+=2){
			
			if((i +1) < keys.length){
				result_key.append(keys[i+1]);
				result_key.append(keys[i]);
			} else{
				result_key.append(keys[i]);
			}
		}
		
		return result_key.toString();
	}   
    
}
