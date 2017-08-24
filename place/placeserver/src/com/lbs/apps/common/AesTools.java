package com.lbs.apps.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.sf.o2o.common.util.AES256CBCEncode;
import com.sf.o2o.common.util.HMACSha512Encode;

public class AesTools {
	
	
	//���ݲ������ؽ����ַ���
	public static String decrypt(String password){
		String comm_check_key_set = (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "COMM_CHECK_KEY_SET");	
		if (comm_check_key_set.equals("1")){
			return password;
		}else{
			String aesPassword = (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "AESPASSWORD");			
			AES256CBCEncode aes = new AES256CBCEncode();
			return	aes.decrypt(password, aesPassword);		
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//String sql="select * from e where 1=2";
		//System.out.println(GetWhereSql(sql));
		/*
		String sms_url=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_URL");
		String sms_servicecode=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_SERVICECODE");
		String sms_name=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_NAME");
		String sms_psw=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_PSW");
		//String ls_timestamp=DateUtil.getCurrentDate_String("yyyymmddhh24miss");
		String ls_timestamp=DateUtil.getCurrentDate_String("yyyymmddhh24miss");
		ls_timestamp="20160817112651";
		String ls_sign=Md5Util.getMD5(sms_psw+ls_timestamp).toUpperCase();   //ǩ��
		
		
		System.out.println(sms_psw+ls_timestamp);
		System.out.println(ls_sign);
		*/
		//System.out.println(Md5Util.getMD5("kzsgd@ABC20160817112651").toUpperCase());
		
		
		String aeskey = "bi/0GYzmwiWiE6vqYmHsbQ4gDZg0ouAW";
		String sha512Password="HxMv40BMnW347w6nx/TyyBbL91RoNxY7";
		HMACSha512Encode hmac = new HMACSha512Encode();
		AES256CBCEncode aes = new AES256CBCEncode();
		String msgData="{\"companyid\":\"1\"}";
		System.out.println("AES256CBC ����ǰ�ı���  ---" + msgData);
		msgData=URLEncoder.encode(msgData,"UTF-8");
		System.out.println("URLEncode��ı���  ---" + msgData);
        msgData = aes.encrypt(msgData, aeskey);           
        System.out.println("AES256CBC ���ܺ�ı���  ---" + msgData);  //msbKPDntI+O4hG4kS/5qDg==
        String dataDigest = hmac.encryptHMAC(msgData, sha512Password);
        System.out.println("AES256CBCǩ��  ---" + dataDigest);  //9632f2abb973dc7b5b3a97c2270b465fb5d853b9de210637e763a1a3fd5cedb8d5b3938b8eeb38c1d89049a2861e55b0550ab10550b09f8236cb6e09b8a886cf
        //msgData="UoRt7nMchqpU/X/6vboE5GeZn84s51jJ5ITqyNlxMZM=";
        //msgData="UoRt7nMchqpU/X/6vboE5LdrKYnaS2QraaEA1n91Yi0=";
        //msgData="Iab4TC0BtD7Gs1EbPis8mQ==";
        msgData=aes.decrypt(msgData, aeskey);
        System.out.println("AES256CBC ���ܺ�ı���  ---" + msgData);
        msgData=URLDecoder.decode(msgData,"UTF-8");
        System.out.println("���ܺ��UrlDecoder��ı���  ---" + msgData);
        
      //https://127.0.0.1:8443/placeserver/SearchSysrole.action?msgdata=msbKPDntI+O4hG4kS/5qDg==&msgdatasign=9632f2abb973dc7b5b3a97c2270b465fb5d853b9de210637e763a1a3fd5cedb8d5b3938b8eeb38c1d89049a2861e55b0550ab10550b09f8236cb6e09b8a886cf
       
	}

}
