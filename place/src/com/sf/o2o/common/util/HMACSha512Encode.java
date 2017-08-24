package com.sf.o2o.common.util;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;



public class HMACSha512Encode {

    private final static String KEY_MAC = "HmacSHA512"; 
    private final Charset charset = Charset.forName("UTF-8");
    //private byte keySeeds[];
 
    /** 
     * BASE64 加密 
     * @param key 需要加密的字节数组 
     * @return 字符串 
     * @throws Exception 
     */  
    public byte[] encryptBase64(String key) throws Exception {  
        return Base64.encodeBase64(key.getBytes(charset));  
    }  
    
    /** 
     * BASE64 解密 
     * @param key 需要解密的字符串 
     * @return 字节数组 
     * @throws Exception 
     */  
    public byte[] decryptBase64(String key) throws Exception {  
        return Base64.decodeBase64(key);  
    }  
       
    /** 
     * HMAC加密 
     * @param data 需要加密的字符串 
     * @param key 密钥 
     * @return 字符串 
     */  
    public String encryptHMAC(String data, String key) {  
        SecretKey secretKey;  
        byte[] bytes = null;  
        byte[] hashed = null;
        try {  
            secretKey = new SecretKeySpec(encryptBase64(key), KEY_MAC);  
            Mac mac = Mac.getInstance("HmacSHA512");  
            mac.init(secretKey);  
            bytes = mac.doFinal(data.getBytes(charset)); 
            hashed = (new Hex()).encode(bytes);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        return new String(hashed, charset); 
    }  
    
 /*   public void setKeySeeds(String keeySeedsString)
    {
        keySeeds =(new Base64Codec()).encrypt(keeySeedsString);
    }

    public String generateHMAC(String datas)
        throws Exception
    {
        SecretKeySpec secretKey = new SecretKeySpec(keySeeds, "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKey);
        byte macData[] = mac.doFinal(datas.getBytes(charset));
        byte hashed[] = (new Hex()).encode(macData);
        return new String(hashed, charset);
    }
    */
    public static void main(String[] args) throws Exception {  
        HMACSha512Encode hmac = new HMACSha512Encode();

    }  
}
