package com.eon.uc.util;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;

import com.lbs.apps.common.Md5Util;
import com.lbs.commons.GlobalNames;

/**
 * AES�ӽ���
 * @author jiatj
 *
 */
public class AESUtil {
	
	 /**
	  *  ����
	  * @param sSrc Ҫ�����ַ���
	  * @param sKey ��Կ
	  * @return
	  * @throws Exception
	  */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("KeyΪ��null");
            return null;
        }
        // �ж�Key�Ƿ�Ϊ16λ
        if (sKey.length() != 16) {
            System.out.print("Key���Ȳ���16λ");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"�㷨/ģʽ/���뷽ʽ"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
 
        return new Base64().encodeToString(encrypted);//�˴�ʹ��BASE64��ת�빦�ܣ�ͬʱ����2�μ��ܵ����á�
    }
 
    // ����
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // �ж�Key�Ƿ���ȷ
            if (sKey == null) {
                System.out.print("KeyΪ��null");
                return null;
            }
            // �ж�Key�Ƿ�Ϊ16λ
            if (sKey.length() != 16) {
                System.out.print("Key���Ȳ���16λ");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//����base64����
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /*
     * ��ȡ��������Ƿ���ȷ��ǩ��
     * ��Σ���¼�ʺ�ID������
     * ʾ��:http://foodeco.chinacloudapp.cn/v1/b2b/checkPassword?sign_type=MD5&input_charset=UTF-8&sign=6DBE72F34DE94F2759FE0182DC0881BF&appid=wuliu&bodata={"firm_id":"10000030","password":"8Ogr9X/Cmvy3J6CV2V97WA=="}
     */
    
    public static String CheckPassword(String firm_id,String password){    	
    	String cKey =GlobalNames.PLATFORM_AES_KEY;   //AES����KEY
    	String enPassword="";
    	try {
    		enPassword = AESUtil.encrypt(password, cKey);  //�������AES����
    		//String DeString = AESUtil.decrypt(enPassword, cKey);  //AES����
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("firm_id",firm_id);
        paraMap.put("password",enPassword);
        //paraMap.put("password","8Ogr9X/Cmvy3J6CV2V97WA==");
        String queryString=SignUtil.createLinkString(paraMap);   //�Բ�������ascii����
        String bodata="{\"firm_id\":\""+firm_id+"\",\"password\":\""+enPassword+"\"}";
        //bodata="{\"bank_billno\":\"1475652093815\",\"code\":\"0\",\"total_fee\":\"10000\",\"service_fee\":\"0\",\"product_fee\":\"0\",\"order_num\":\"\",\"attach\":\"\",\"pay_time\":\"2016-10-05 15:21:33\"}";
        
        System.out.println("bodata="+bodata);
        String dataSign=MD5.sign(bodata+"&key="+cKey,"UTF-8").toString();
        System.out.println("datasign="+dataSign);    	
    	return dataSign;
    }
    
    /*
     * ��ȡ��ѯ���ӿ�ǩ��
     * ��Σ���¼�ʺ�ID������
     * ʾ��:http://foodeco.chinacloudapp.cn/v1/b2b/balance?sign_type=MD5&input_charset=UTF-8&sign=9D040B48B4C91AA0E295995628B65EB8&appid=wuliu&bodata={"firm_id":"10000030"}
     */
    
    public static String Balance(String firm_id){    	
    	String cKey =GlobalNames.PLATFORM_AES_KEY;   //AES����KEY
    	Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("firm_id",firm_id);
        //paraMap.put("password","8Ogr9X/Cmvy3J6CV2V97WA==");
        String queryString=SignUtil.createLinkString(paraMap);   //�Բ�������ascii����
        String bodata="{\"firm_id\":\""+firm_id+"\"}";
        System.out.println("bodata="+bodata);
        String dataSign=MD5.sign(bodata+"&key="+cKey,"UTF-8").toString();
        System.out.println("datasign="+dataSign);    	
    	return dataSign;
    }
 
    public static void main(String[] args) throws Exception {
    	
    	String s="{\"signer\":\"田诗�?,\"status\":\"sss\"}";
    	
    	s= new String(s.getBytes("GBK"),"UTF-8");
    	//s=s.replace("?,\"status\"", "?\",\"status\"");
    	System.out.println(s);
    	
    	s="{\"signer\":\"��ʫ��\",\"status\":\"sss\"}";
    	s=new String(s.toString().getBytes("UTF-8"));  
    	System.out.println(s);
    	/*
        String user_id="10000095";
        String password="123456";
        CheckPassword(user_id,password);
        Balance(user_id);
        */
    }


}
