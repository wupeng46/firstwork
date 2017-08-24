package com.lbs.apps.common;
/*
 * 此类可以保证JAVA，PHP,C#，JS加解密内容一致
 * 参考:http://my.oschina.net/Jacker/blog/86383
 */

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sf.o2o.common.util.HMACSha512Encode;

import sun.misc.BASE64Decoder;

public class AesUtil
{
	public static void main(String args[]) throws Exception {
		String key="bi/0GYzmwiWiE6vq";
		
    	String content="{\"domainid\":\"NEWSTYPE\",\"isvalid\":\"101\"}";
    	content="{\"newstype\":\"\",\"newstitle\":\"\u7269\u6d41\u5e73\u53f0\",\"createddate\":\"\",\"isvalid\":\"101\"}";
    	//content="{\"newstype\":\"\",\"newstitle\":\"物流平台\",\"isvalid\":\"101\"}";
    	System.out.println("加密前消息包内容："+content);    	
    	//正向加密过程
    	String password=encrypt(content,key);   //AES加密
    	System.out.println("加密后内容："+password);
    	
    	
    	String md5_key="HxMv40BMnW347w6nx/TyyBbL91RoNxY7";
    	//password="2xxa/zghS0H0mB6AVzdhfnw59wDIvb76LARF3vlxxONAMk+pX8sIp+ScQ+dBYpN9XmqxE7Su8jLXx7/ezUAMwTkgGA7Uhr6+WqYxk7qomnqhbCkQ6T8DhMkoOYhGq4Zt";
    	System.out.println("Md5签名串  ---" + md5_key+password);
    	String msgdatasign=Md5Util.toMD5(md5_key+password);
    	System.out.println("Md5签名  ---" + msgdatasign);
    	
    	String urlencode_content=URLEncoder.encode(password,"UTF-8");
    	System.out.println("UrlEncode后消息包内容："+urlencode_content);   //这是msgdata中要传递的参数
    	
    	
    	
		//String password="2xxa/zghS0H0mB6AVzdhfnybgSlF5NX7457rOBQVjCxlntJQZEw1gPxtehjqelAWoypTZBUt0B5/MI4HpMgYWg==";
    	
    	//反向解析过程
    	
    	String msgData=URLDecoder.decode(urlencode_content,"UTF-8");  //先UrlDecode进行解码
    	System.out.println("解密后的UrlDecoder后的报文  ---" + msgData);    	
    	
    	msgData="2xxa/zghS0H0mB6AVzdhfnw59wDIvb76LARF3vlxxONAMk+pX8sIp+ScQ+dBYpN9XmqxE7Su8jLXx7/ezUAMwTkgGA7Uhr6+WqYxk7qomnqhbCkQ6T8DhMkoOYhGq4Zt";
    	
    	String oldcontent=desEncrypt(msgData,key);        
        System.out.println("解密后原始msgdata内容："+oldcontent);       
        
        
    	
        
        
    }
    public static void main_old(String args[]) throws Exception {
    	//String cKey="c5d608eb97d94123";
    	//String password=encrypt("123456",cKey);   //AES加密
    	//System.out.println("加密后内容："+password);
    	
    	//String content="{\"companyid\":\"1\"}";
    	String content="{\"domainid\":\"NEWSTYPE\",\"isvalid\":\"101\"}";
    	//content="{\"newstype\":\"\",\"newstitle\":\"\u7269\u6d41\u5e73\uf3f0\",\"isvalid\":\"101\"}";
    	//content="{\"newstype\":\"\",\"newstitle\":\"物流平台\",\"isvalid\":\"101\"}";
    	System.out.println("加密前消息包内容："+content);
    	String key="bi/0GYzmwiWiE6vq";
    	//正向加密过程
    	String password=encrypt(content,key);   //AES加密
    	System.out.println("加密后内容："+password);
    	
    	String md5_key="HxMv40BMnW347w6nx/TyyBbL91RoNxY7";
    	String msgdatasign=Md5Util.toMD5(md5_key+password);
    	//System.out.println("Md5签名  ---" + msgdatasign);
    	
    	String urlencode_content=URLEncoder.encode(password,"UTF-8");
    	System.out.println("UrlEncode后消息包内容："+urlencode_content);   //这是msgdata中要传递的参数
    	
    	String urlencode_datasign=URLEncoder.encode(msgdatasign,"UTF-8");
    	//System.out.println("UrlEncode_msgdatasign后消息包内容："+urlencode_datasign);   //这是msgdatasign中要传递的参数
    	
    	//反向解析过程
    	String msgData=URLDecoder.decode(urlencode_content,"UTF-8");  //先UrlDecode进行解码
    	System.out.println("解密后的UrlDecoder后的报文  ---" + msgData);    	
    	String oldcontent=desEncrypt(msgData,key);        
        System.out.println("解密后原始msgdata内容："+oldcontent);       
        
        urlencode_datasign=URLDecoder.decode(urlencode_datasign,"UTF-8");  //先UrlDecode进行解码
        //System.out.println("msgdatasign解密后的UrlDecoder后的报文  ---" + urlencode_datasign);
        
    }

    public static String encrypt(String content, String key) throws Exception {
        try {
            String data = content;
            String iv = key;

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new sun.misc.BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desEncrypt(String content, String key) throws Exception {
        try
        {
            String data = content;
            String iv = key;
            
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}