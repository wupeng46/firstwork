package com.lbs.commons.servlet;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.lbs.apps.common.PropertiesOperator;
import com.lbs.apps.system.po.Area;
import com.lbs.apps.system.po.Syscode;
import com.lbs.apps.system.po.Sysgroup;
import com.lbs.apps.system.po.Syspara;
import com.lbs.apps.system.po.Sysuser;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.configuration.GlobalConfiguration;
import com.lbs.commons.op.OPManager;

//系统启动时的运行类

public class AutoRunServlet extends HttpServlet {
	private Logger log=Logger.getLogger("[东北粮网移动平台服务器:]");
	private ServletContext context;
	
	public void init() throws ServletException{
		log.info("东北粮网平台服务器  CorePlatform Starting...");
		
	    //context = ServletActionContext.getServletContext();
		context = this.getServletContext();
	    //查找配置文件路径
	    try {
		    InputStream conFile = context.getResourceAsStream(GlobalNames.CONFIG_FILE);	
		    if(null == conFile){
		      	conFile = context.getResourceAsStream("/WEB-INF/conf/BZMIS_Config.xml");
		    }		   
			GlobalConfiguration gConf = new GlobalConfiguration(conFile);
			log.info("Initializing JMS...");
	        initJMS();
	        log.info("Initializing CodeList...");   //加载代码参数  未完成
	        initGlobalNames();  //加载全局配置
	        initCodePara();
		} catch (Exception e) {
			log.info("东北粮网移动平台服务器 Start with the following Errors:");
		    log.error("Init 东北粮网移动平台服务器 error: "+e.toString());
		}		
	}
	
	/**
	   * 初试化JMS
	   */
	  private void initJMS(){
	    //JMSLogger.init();
	  }
	  
	  private void initGlobalNames(){
		  GlobalNames.COMM_CHECK_KEY_SET= (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "COMM_CHECK_KEY_SET");
		  GlobalNames.AESPASSWORD= (String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "AESPASSWORD");
		  GlobalNames.SHA512PASSWORD=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SHA512PASSWORD");
		  GlobalNames.PLATFORM_APPID=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "PLATFORM_APPID");
		  GlobalNames.PLATFORM_AES_KEY=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "PLATFORM_AES_KEY");
		  GlobalNames.PLATFORM_BALANCE_URL=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "PLATFORM_BALANCE_URL");  
		  GlobalNames.PLATFORM_RUNMODE=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "PLATFORM_RUNMODE");		  
		  
		  GlobalNames.YUNSIGN_DOMAIN=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "YUNSIGN_DOMAIN");
		  GlobalNames.YUNSIGN_REGUSER_URL=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "YUNSIGN_REGUSER_URL");
		  GlobalNames.YUNSIGN_APPID=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "YUNSIGN_APPID");
		  GlobalNames.YUNSIGN_KEY=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "YUNSIGN_KEY");
		  
		  //阿里大鱼短信平台参数
		  GlobalNames.SMS_TAOBAO_URL=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_TAOBAO_URL");
		  GlobalNames.SMS_TAOBAO_APPKEY=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_TAOBAO_APPKEY");
		  GlobalNames.SMS_TAOBAO_SECRET=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_TAOBAO_SECRET");
		  GlobalNames.SMS_TAOBAO_EXTEND=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_TAOBAO_EXTEND");
		  GlobalNames.SMS_TAOBAO_FREESIGNNAME=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_TAOBAO_FREESIGNNAME");
		  GlobalNames.SMS_TAOBAO_REGVERIFY_TEMPLATECODE=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_TAOBAO_REGVERIFY_TEMPLATECODE");
		  GlobalNames.SMS_TAOBAO_FINDPASSWORDVERIFY_TEMPLATECODE=(String) PropertiesOperator.getValueByKey("com/lbs/apps/config/config.properties", "SMS_TAOBAO_FINDPASSWORDVERIFY_TEMPLATECODE");
	  }
	  
	  /**
	   * 加载代码参数
	   */
	  
	  private void initCodePara(){
		  String codeName = null;
		  Collection result = null;
		  Collection org = null;
		  Collection user=null;
		  Collection area=null;


		  try {
				OPManager op = new OPManager();
				//result = op.query(" from Syscode order by id.domainid,id.domaincode",-1);
				result = op.query(" from Syscode where isvalid='101'",-1);
				org = op.query(" from Sysgroup ", -1);
				user=op.query(" from Sysuser ",-1);
	            area=op.query(" from Area ",-1);
	            Syspara syspara=(Syspara)op.retrieveObj(Syspara.class, "BUY_ROLEID");
	            if (syspara!=null){
	            	GlobalNames.BUY_ROLEID=syspara.getParavalue();
	            }
	            syspara=(Syspara)op.retrieveObj(Syspara.class, "SALE_ROLEID");
	            if (syspara!=null){
	            	GlobalNames.SALE_ROLEID=syspara.getParavalue();
	            }
	            syspara=(Syspara)op.retrieveObj(Syspara.class, "PLATFORM_WORKTYPE");  //平台业务模式
	            if (syspara!=null){
	            	GlobalNames.PLATFORM_WORKTYPE=syspara.getParavalue();
	            }
	            syspara=(Syspara)op.retrieveObj(Syspara.class, "PLATFORM_MEMBERID");   //平台默认委托方编码
	            if (syspara!=null){
	            	GlobalNames.PLATFORM_MEMBERID=syspara.getParavalue();
	            }
	            syspara=(Syspara)op.retrieveObj(Syspara.class, "PLATFORM_COMPANYID");  //平台默认承运方编码
	            if (syspara!=null){
	            	GlobalNames.PLATFORM_COMPANYID=syspara.getParavalue();
	            }

				
			} catch (Exception ex) {
				log.error("=============## CodeList 加载错误！");
				ex.printStackTrace();
			}
		  
			if (null != result) {
				for (Iterator it = result.iterator(); it.hasNext();) {
					Syscode code = (Syscode) it.next();
					codeName = code.getId().getDomainid();
					if (null == this.getServletContext().getAttribute(codeName)) {
						TreeMap list = new TreeMap();
						list.put(code.getId().getDomaincode(), code.getDomainname());
						this.getServletContext().setAttribute(codeName, list);
					} else {
						TreeMap list = (TreeMap) this.getServletContext().getAttribute(codeName);
						list.put(code.getId().getDomaincode(), code.getDomainname());
					}
				}
			}
			
			if (null != org) {//处理经办机构
				TreeMap list = new TreeMap();
				for (Iterator it = org.iterator(); it.hasNext();) {
					Sysgroup sg = (Sysgroup) it.next();
					list.put(sg.getGroupid().toString(), sg.getGroupname());
				}
				this.getServletContext().setAttribute("CREATEDORG", list);				
			}

	        if (null != user) {//处理经办人
	            TreeMap list = new TreeMap();
	            for (Iterator it = user.iterator(); it.hasNext();) {
	                Sysuser u = (Sysuser) it.next();
	                list.put(u.getUserid().toString(), u.getUsername());
	            }
	            this.getServletContext().setAttribute("CREATEDBY", list);
	        }
	        
	        
	        if (null != area) {//处理行政区划
	            TreeMap list = new TreeMap();
	            for (Iterator it = area.iterator(); it.hasNext();) {
	                Area u = (Area) it.next();
	                list.put(u.getAreaid().toString(), u.getAreafullname());
	            }
	            //this.getServletContext().setAttribute("AREAID", list);
	            this.getServletContext().setAttribute("STARTAREAID", list);
	            this.getServletContext().setAttribute("ENDAREAID", list);
	        }
	        
	        /*
	        TreeMap log = new TreeMap();
			log.put("0", "否");
			log.put("1", "是");

			TreeMap type = new TreeMap();		
			type.put("0", "叶子");
			type.put("1", "节点");
			type.put("2", "按钮");

			this.getServletContext().setAttribute("LOG", log);
			this.getServletContext().setAttribute("TYPE", type);
			
			//	user是否是领导
			this.getServletContext().setAttribute("ISLEADER", log);
			*/
	  }
	  


}
