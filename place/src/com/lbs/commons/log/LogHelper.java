package com.lbs.commons.log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.apps.common.CurrentUser;
import com.lbs.apps.system.po.Loginlog;
import com.lbs.apps.system.po.Syserrorlog;
import com.lbs.apps.system.po.Systemlog;
import com.lbs.commons.DateUtil;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.log.jms.JMSLogger;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

/**
 * 对系统日志进行操作的辅助类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author LIN Shumin
 * @version 1.0
 */
public class LogHelper
    implements java.io.Serializable {

  private Log log;
  private OPManager op = null;
  /**
   * 构造函数
   * @param className Class 发起日志请求的源类
   */
  public LogHelper(Class className) {
    log = LogFactory.getLog(className.getClass());
    op = new OPManager();
  }

  public static LogHelper getLogger(Class className) {
    LogHelper log = new LogHelper(className);
    return log;
  }

  /**
   * 记录调试信息
   * @param msg String 记录信息
   */
  public void debug(String msg) {
    log.debug(msg);
  }

  /**
   * 记录普通信息
   * @param msg String 记录信息
   */
  public void info(String msg) {
    log.info(msg);
  }

  /**
   * 记录警告信息
   * @param msg String 记录信息
   */
  public void warn(String msg) {
    log.warn(msg);
    doLog(msg);
  }

  /**
   * 记录错误信息
   * @param msg String 记录信息
   */
  public void error(String msg) {
    log.error(msg);
    doLog(msg);
  }

  /**
   * 记录严重错误信息
   * @param msg String 记录信息
   */
  public void fatal(String msg) {
    log.fatal(msg);
    doLog(msg);
  }

  public void doLog(String msg) {
    info(msg);
    //CurrentUser currentUser=(CurrentUser)ActionContext.getContext().getSession().get(GlobalNames.CURRENT_USER);    
    //doLog(currentUser, msg);
  }
  
 

  /**
   * 记录系统日志
   * @param msg String
   */
  public void doLog(CurrentUser user, String msg) {
    System.err.println("\n========##======error info: " + msg);
    /*
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());
    String strDate = format.format(date);
    Systemlog sysLog = new Systemlog();
    sysLog.setUserid(user.getUser().getUserid());
    sysLog.setIp(user.getIp());
    sysLog.setMessage(msg);
    sysLog.setMsgdate(strDate);
    if (GlobalNames.LOG_IS_ASYNCH) {
      JMSLogger.log(sysLog);
    } else {
      try {
        op.saveObj(sysLog);
      } catch (OPException ex) {
        log.error(ex.toString());
      }
    }
    */
  }
  
  public void doLog(String modmethod,String logdesc,Integer createdby){
	if (!(logdesc==null || logdesc.equals("") || createdby==null || createdby.intValue()==0)){
		  Systemlog systemlog=new Systemlog();
		  systemlog.setModmethod(modmethod);
		  systemlog.setLogdesc(logdesc);
		  systemlog.setCreatedby(createdby);
		  Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		  systemlog.setCreateddate(now);
		  try {
			op.saveObj(systemlog);
		} catch (OPException e) {
			log.error(e.toString());
		}	  
	}	  
  }
  
  //记录异常日志
  public void error(String modmethod,String logdesc,String errorreason,Integer createdby){
		if (!(logdesc==null || logdesc.equals("") || errorreason==null || errorreason.equals("") || createdby==null || createdby.intValue()==0)){
			  Syserrorlog syserrorlog=new Syserrorlog();
			  syserrorlog.setModmethod(modmethod);
			  syserrorlog.setLogdesc(logdesc);
			  syserrorlog.setErrorreason(errorreason);
			  syserrorlog.setCreatedby(createdby);
			  Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
			  syserrorlog.setCreateddate(now);
			  try {
				op.saveObj(syserrorlog);
			} catch (OPException e) {
				log.error(e.toString());
			}	  
		}	  
	  }

  /**
   * 用户登录成功后调用此方法记录日志
   * @param cu CurrentUser 当前用户信息
   */
  public void doLogonLog(CurrentUser cu) {
    info("========##======登录");
    /*
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());
    String strDate = format.format(date);

    Logonhistory lh = new Logonhistory();
    lh.setUserid(cu.getUser().getUserid());
    lh.setPasswd(cu.getUser().getPasswd());
    lh.setIp(cu.getIp());
    lh.setLogontime(strDate);
    cu.setLogonTime(strDate);
    if (GlobalNames.LOG_IS_ASYNCH) {
      JMSLogger.log(lh);
    } else {
      try {
        op.saveObj(lh);
        cu.setId(lh.getId());
      } catch (OPException ex) {
        log.error(ex.toString());
      }
    }
    */
  }
  
  public void doLogonLog(Integer userid) {
	  Loginlog loginlog=new Loginlog();
	  loginlog.setUserid(userid);
	  Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
	  loginlog.setLogintime(now);
	  try {
		op.saveObj(loginlog);
	} catch (OPException e) {
		log.error(e.toString());
		//e.printStackTrace();
	}	  
  }

  /**
   * 用户下线后调用此方法记录日志
   * @param cu CurrentUser 当前用户信息
   */
  public void doLogoffLog(Integer userid) {
    info("========##======下线");
    Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());    
    String hql=" from Loginlog where userid="+userid+" order by id desc";
    List list=null;
	try {
		list = (List)op.retrieveObjs(hql);
	} catch (OPException e) {
		log.error(e.toString());
	}
    if (list!=null){
    	Loginlog loginlog=(Loginlog)list.get(0);
    	loginlog.setLogouttime(now);    	
    	try {
    		op.updateObj(loginlog);
        } catch (OPException ex) {
        	log.error(ex.toString());
        }
    }   
    
  }
  
}
