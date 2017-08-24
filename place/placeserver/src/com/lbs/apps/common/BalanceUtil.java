package com.lbs.apps.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

import com.eon.uc.util.MD5;
import com.eon.uc.util.MyVerifyHostname;
//import com.eon.uc.util.MyX509TrustManager;
import com.eon.uc.util.SignUtil;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.log.LogHelper;

public class BalanceUtil {
	LogHelper  log = new LogHelper(this.getClass());	
	/*
     * ��ȡ��ѯ���ӿ�ǩ��
     * ��Σ���¼�ʺ�ID
     * ʾ��:http://foodeco.chinacloudapp.cn/v1/b2b/balance?sign_type=MD5&input_charset=UTF-8&sign=9D040B48B4C91AA0E295995628B65EB8&appid=wuliu&bodata={"firm_id":"10000030"}
     */
    
    public String GetUserBalance(String userid){    	
    	String ld_return="";    	
    	String cKey =GlobalNames.PLATFORM_AES_KEY;   //AES����KEY
    	Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("firm_id",userid);
        //paraMap.put("password","8Ogr9X/Cmvy3J6CV2V97WA==");
        String queryString=SignUtil.createLinkString(paraMap);   //�Բ�������ascii����
        String bodata="{\"firm_id\":\""+userid+"\"}";
        //System.out.println("bodata="+bodata);
        String dataSign=MD5.sign(bodata+"&key="+cKey,"UTF-8").toString();
        //System.out.println("datasign="+dataSign);    	
        String ls_url=GlobalNames.PLATFORM_BALANCE_URL;
        String ls_para="sign_type=MD5&input_charset=UTF-8&sign="+dataSign+ "&appid="+GlobalNames.PLATFORM_APPID+"&bodata="+bodata;
        log.info("��ѯ�ʽ���ls_para="+ls_para);
        //System.out.println("��ѯ�ʽ���ls_para="+ls_para);
        String ls_result=HttpUtil.sendPost(ls_url,ls_para);
        log.info("��ѯ�ʽ���ls_result="+ls_result);
        //System.out.println("��ѯ�ʽ���ls_result="+ls_result);
        
        JSONObject obj = JSONObject.fromObject(ls_result);   //����JSON���ݰ�        
        if (obj.has("code")){
        	if (obj.get("code").equals("0")){
        		ld_return=obj.get("kc").toString();  //���ý��
        	}
        }    	
    	return ld_return;
    }
    
    
   public static void main(String[] args) throws Exception {
        String userid="10000083";
        //System.out.println(GetUserBalance(userid));        
    }

}
