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
 * ��ϵͳ��־���в����ĸ�����
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
   * ���캯��
   * @param className Class ������־�����Դ��
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
   * ��¼������Ϣ
   * @param msg String ��¼��Ϣ
   */
  public void debug(String msg) {
    log.debug(msg);
  }

  /**
   * ��¼��ͨ��Ϣ
   * @param msg String ��¼��Ϣ
   */
  public void info(String msg) {
    log.info(msg);
  }

  /**
   * ��¼������Ϣ
   * @param msg String ��¼��Ϣ
   */
  public void warn(String msg) {
    log.warn(msg);
    doLog(msg);
  }

  /**
   * ��¼������Ϣ
   * @param msg String ��¼��Ϣ
   */
  public void error(String msg) {
    log.error(msg);
    doLog(msg);
  }

  /**
   * ��¼���ش�����Ϣ
   * @param msg String ��¼��Ϣ
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
   * ��¼ϵͳ��־
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
  
  //��¼�쳣��־
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
   * �û���¼�ɹ�����ô˷�����¼��־
   * @param cu CurrentUser ��ǰ�û���Ϣ
   */
  public void doLogonLog(CurrentUser cu) {
    info("========##======��¼");
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
   * �û����ߺ���ô˷�����¼��־
   * @param cu CurrentUser ��ǰ�û���Ϣ
   */
  public void doLogoffLog(Integer userid) {
    info("========##======����");
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
