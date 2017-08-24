package com.lbs.apps.common;

import com.lbs.commons.GlobalNames;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsUtil {

	/*
	 * ���ð���������ƽ̨������֤����
	 * 
	 * telephone	String	��	�绰����
     * verifyCode	String	��	��֤��
     * verifyCodeType	String	��	��֤������(1001ע����֤1002������֤)
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
		req.setSmsParamString("{\"code\":\""+verifyCode+"\",\"product\":\"��������\"}");
		req.setRecNum(telephone);
		if (verifyCodeType.equals("1001")){  //ע����֤
			req.setSmsTemplateCode(GlobalNames.SMS_TAOBAO_REGVERIFY_TEMPLATECODE);
		}else if (verifyCodeType.equals("1002")){  //�һ�������֤
			req.setSmsTemplateCode(GlobalNames.SMS_TAOBAO_FINDPASSWORDVERIFY_TEMPLATECODE);
		}
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		//System.out.println(rsp.getBody());
		//return "0";
	}

}
