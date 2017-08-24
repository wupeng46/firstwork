package com.lbs.apps.common;

import com.lbs.commons.GlobalNames;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsUtil {

	/*
	 * 调用阿里大鱼短信平台发送验证短信
	 * 
	 * telephone	String	是	电话号码
     * verifyCode	String	是	验证码
     * verifyCodeType	String	是	验证码类型(1001注册验证1002找密验证)
	 */
	public static void SendSms(String telephone,String verifyCode,String verifyCodeType) throws ApiException {
		String url=GlobalNames.SMS_TAOBAO_URL;
		String appkey=GlobalNames.SMS_TAOBAO_APPKEY;
		String secret=GlobalNames.SMS_TAOBAO_SECRET;
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(GlobalNames.SMS_TAOBAO_EXTEND);
		req.setSmsType("normal");
		req.setSmsFreeSignName(GlobalNames.SMS_TAOBAO_FREESIGNNAME);		
		req.setSmsParamString("{\"code\":\""+verifyCode+"\",\"product\":\"东北粮网\"}");
		req.setRecNum(telephone);
		if (verifyCodeType.equals("1001")){  //注册验证
			req.setSmsTemplateCode(GlobalNames.SMS_TAOBAO_REGVERIFY_TEMPLATECODE);
		}else if (verifyCodeType.equals("1002")){  //找回密码验证
			req.setSmsTemplateCode(GlobalNames.SMS_TAOBAO_FINDPASSWORDVERIFY_TEMPLATECODE);
		}
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		//System.out.println(rsp.getBody());
		//return "0";
	}

}
