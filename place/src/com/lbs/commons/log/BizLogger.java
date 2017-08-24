package com.lbs.commons.log;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.apps.common.CurrentUser;
import com.lbs.apps.system.po.Systemlog;
import com.lbs.commons.DateUtil;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.log.jms.JMSLogger;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

/**
 * 业务日志处理器
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author LIN Shumin
 * @version 1.0
 */
public class BizLogger
    implements Serializable {
  /**
	 * 
	 */
	
  private Log log = LogFactory.getLog(BizLogger.class);
  private OPManager op = null;
  
  public BizLogger() {
    op = new OPManager();
  }

  /**
   * 记录业务日志
   * @param createdby 建立者
   * @param modmethod 访问方法
   * @param logdesc  日志描述
   */
  public void log(String createdby, String modmethod, String logdesc) {
	  Systemlog aLog = new Systemlog();

    if (createdby == null || createdby.equals("")) {
      log.warn("业务日志记录操作人员为空!");
      return;
    }
    if (logdesc == null || logdesc.equals("")) {
    	log.warn("业务日志记录信息为空");
    	return;
    }

    Integer systemlogid=new Integer(1);  //需获取日志序列号
    aLog.setSystemlogid(systemlogid);
    aLog.setCreatedby(new Integer(createdby));    
    aLog.setCreateddate(DateUtil.dateToTimestamp(DateUtil.getCurrentDate()));  
    aLog.setLogdesc(logdesc);
    aLog.setModmethod(modmethod);
    
    
    if (GlobalNames.LOG_IS_ASYNCH) {
      JMSLogger.log(aLog);
    } else {
      try {
        op.saveObj(aLog);
      } catch (OPException ex) {
        log.error("记录业务日志时错!");
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] ar) {

    

  }
}
