package com.lbs.apps.common;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.lbs.apps.common.redis.RedisClientTemplate;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.SpringContextUtil;
import com.lbs.commons.StringHelper;
import com.sf.o2o.common.util.HMACSha512Encode;

public class LoginFilter implements Filter {
	private static String comm_check_key_set=null; //1 允许key和不用key共存，效验失败允许继续请求 2允许key和不用key共存，但效验失败的记录不能继续处理，3仅允许key进行通讯，效验失败的记录不能继续处理。
	private static String prekey=null;
	private static String aesPassword = null;
	private static String sha512Password = null;
	private static final RedisClientTemplate redisClient =  (RedisClientTemplate) SpringContextUtil.getBean("redisClientTemplate");
	private static String[] withoutVerify=null;

	static {
		comm_check_key_set = (String) PropertiesOperator.getValueByKey(
				"com/lbs/apps/config/config.properties", "COMM_CHECK_KEY_SET");
		
		if (comm_check_key_set==null || comm_check_key_set.equals("")){
			comm_check_key_set="3";
		}
		prekey = (String) PropertiesOperator.getValueByKey(
				"com/lbs/apps/config/config.properties", "PREKEY");
		if (prekey==null || prekey.equals("")){
			prekey="kzsgd2016whd";
		}
		
		aesPassword = (String) PropertiesOperator.getValueByKey(
				"com/lbs/apps/config/config.properties", "AESPASSWORD");
		
		sha512Password = (String) PropertiesOperator.getValueByKey(
				"com/lbs/apps/config/config.properties", "SHA512PASSWORD");
		
		String ls_temp=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "WITHOUT_VERIFY");
		if (!(ls_temp==null || ls_temp.equals(""))){
			withoutVerify=ls_temp.split(",");
		}		
	}
	

	public void destroy() {
		
	}
	
	public String inputStreamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {	
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		CurrentUser currentUser = (CurrentUser) session.getAttribute(GlobalNames.CURRENT_USER);
		HttpServletResponse response = (HttpServletResponse) res;
		//String url = request.getRequestURI();
		String url=request.getServletPath();
		HMACSha512Encode hmac = new HMACSha512Encode();	
        
        JSONObject jsPara = null;
        jsPara=JSONObject.fromObject(request.getParameterMap());       
        
        
		if (url.indexOf(".action")>0){   //只对action方法进行校验
			//System.out.println(url.replace("/", ""));
			if (comm_check_key_set.equals("2")||comm_check_key_set.equals("3")){   
				boolean lb_verify=false;
				if (withoutVerify!=null){  //有白名单
					for (int i=0;i<withoutVerify.length;i++){ 
						if (withoutVerify[i].toLowerCase().equals(url.replace("/", "").toLowerCase())){
							lb_verify=true;
							break;
						}
					}
				}		
				
				lb_verify=true;  //暂时全部走ACTION端验证
				
				if (lb_verify){
					jsPara.put("access","allow");
		        	jsPara.put("commcheck","withoutVerify");					
				}else if (jsPara.has("msgdata") && jsPara.has("msgdatasign")){
					//System.out.println("msgdata:"+jsPara.getString("msgdata"));
					String msgdata=HtmlUtil.convertPara(jsPara.getString("msgdata"));  //得到加密后的msgdata
					String msgdatasign=HtmlUtil.convertPara(jsPara.getString("msgdatasign"));  //得到加密后的msgdata验证签名
					String dataDigest = hmac.encryptHMAC(msgdata, sha512Password);
					if (!msgdatasign.equals(dataDigest)){
						HtmlUtil.writerJson(response, "{\"success\":\"false\",\"msg\":\"数据包签名异常\"}");
		        		return;						
					}				
				 }else if (jsPara.has("key") && jsPara.has("deviceid") && jsPara.has("timestamp")){		        	
		        	String key=HtmlUtil.convertPara(jsPara.getString("key"));                    //加密通讯key
		        	String deviceid=HtmlUtil.convertPara(jsPara.getString("deviceid"));		   //设备id	     
		        	String timestamp=HtmlUtil.convertPara(jsPara.getString("timestamp"));		   //时间
		        	String ls_result=commCheck(key,deviceid,timestamp);
		        	//String ls_result="1";
		        	if (!(ls_result.equals("0"))){
		        		jsPara.put("access","deny");
		        		jsPara.put("commcheck", ls_result);
		        		//logger.error(url.replace("/", "") +"\t"+ jsPara.toString() + "\t");
		        		HtmlUtil.writerJson(response, "{\"success\":\"false\",\"msg\":\""+ls_result+"\"}");
		        		return;
		        	}		        
		        }else{  //请求参数中未带验证参数   
		        	HtmlUtil.writerJson(response, "{\"success\":\"false\",\"msg\":\"通讯参数异常\"}");
	        		return;	        		
		        }		        
	        }			
		} else if ((url.toLowerCase().lastIndexOf(".jpg")>0)||(url.toLowerCase().lastIndexOf(".jpeg")>0)||(url.toLowerCase().lastIndexOf(".bmp")>0)||(url.toLowerCase().lastIndexOf(".png")>0))
        {
        	jsPara.put("access","allow");
        	jsPara.put("commcheck","urltypeallow");
        	//logger.info(url.replace("/", "") +"\t"+ jsPara.toString() + "\t");		        
        	if (jsPara.has("w")&&jsPara.has("h")){
        		int w=Integer.parseInt(HtmlUtil.convertPara(jsPara.getString("w")));
        		int h=Integer.parseInt(HtmlUtil.convertPara(jsPara.getString("h")));
        		String newurl=url.replace(".", "_"+String.valueOf(w)+"_"+String.valueOf(h)+".");
        		String srcImgPath=request.getRealPath(url);
        		String outImgPath=request.getRealPath(newurl);
        		
        		if (!FileUtil.CheckFile(outImgPath)){
        			ImageUtil.compressImage2(srcImgPath, outImgPath, w, h);
        		}
        		
        		if (FileUtil.CheckFile(outImgPath)){
        			request.getRequestDispatcher(newurl).forward(request,res);	        		
        		}	        		
        	}
        }		
        
		//如果用户请求了index.html，这时就必须做登录判断，判断用户是否登录。
		if((GlobalNames.WEB_APP+"/mainapp/app.html").equals(url)) {
			if(currentUser.getUser().getUserid() == null || "".equals(currentUser.getUser().getUserid())) {
				//response.sendRedirect("../login.html");
				response.sendRedirect(response.encodeRedirectURL(GlobalNames.WEB_APP + GlobalNames.RELOGON_PAGE));
			} else {
				chain.doFilter(request, response);
			}
		}else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	
	public static String commCheck(String key,String deviceid,String timestamp) {
		// TODO Auto-generated method stub
		//System.out.println("timestamp="+timestamp +"&key="+key +"&deviceid="+deviceid);
		
		if (key.length()!=32){
			return "通讯参数异常";
		}
		
		
		if (deviceid==null){
			return "通讯参数异常";
		}
		
		if (deviceid.equals("000000")){
			return "通讯参数异常";
		}
		
		if (!StringHelper.isNumber(timestamp)){
			return "通讯参数异常";
		}
			
		//if (!StringHelper.isNumber(key.substring(0, 1))){
		//	return "通讯参数异常";
		//}
		
		try {
			if (redisClient.exists(deviceid+"_"+timestamp)){
				return "通讯时间验证异常";
			}
			
			
			String md5=Md5Util.toMD5(prekey+deviceid+timestamp);
			
			//System.out.println("md5:"+md5);
			
			if (!md5.equals(key)){
				return "通讯验证未通过";
			}
			
			redisClient.setex(deviceid+"_"+timestamp,60*5,"1");
		}   catch (Exception e){
			return "通讯异常";
		}	
		
		return "0";
	}	
	
	
	public static String commCheck_old(String key,String deviceid,String timestamp) {
		// TODO Auto-generated method stub
		
		if (key.length()!=33){
			return "通讯参数异常";
		}
		
		if (deviceid==null){
			return "通讯参数异常";
		}
		
		if (deviceid.equals("000000")){
			return "通讯参数异常";
		}
		
		if (!StringHelper.isNumber(timestamp)){
			return "通讯参数异常";
		}
			
		if (!StringHelper.isNumber(key.substring(0, 1))){
			return "通讯参数异常";
		}
		
		try {
			//logger.info("timestamp="+timestamp +"&key="+key +"&y0102="+y0102);
			
			long ll_currentTimeMillis=Long.valueOf(timestamp);
			
			long ll_syscurrentTimeMillis=System.currentTimeMillis();
			
			if (redisClient.exists(deviceid+"_"+timestamp)){
				return "通讯时间验证异常";
			}
			
			int random=Integer.valueOf(key.substring(0, 1));
			
			String StringEncrypt=AlgorithmUtil.getStringEncrypt(prekey, random);
						
			String md51=Md5Util.toMD5(String.valueOf(ll_currentTimeMillis) + deviceid + prekey);
			
			String md52=Md5Util.toMD5(StringEncrypt);
			String md5=Md5Util.toMD5(md51+md52);
			
			//logger.info("md51="+md51 +",md52="+md52 +",md5="+md5);
			
			if (!md5.equals(key.substring(1))){
				return "通讯验证未通过";
			}
			
			redisClient.setex(deviceid+"_"+timestamp,60*5,"1");
		}   catch (Exception e){
			return "通讯异常";
		}	
		
		return "0";
	}	
	
}
