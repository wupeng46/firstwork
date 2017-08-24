package com.lbs.commons.log.jms;

import java.io.Serializable;

import javax.ejb.CreateException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.apps.system.po.Loginlog;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;
/**
 * 记录日志
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class LogBean
    implements MessageDrivenBean, MessageListener {
  MessageDrivenContext messageDrivenContext;
  private Log logger = LogFactory.getLog(LogBean.class);
  private OPManager op = null;
  public void ejbCreate() throws CreateException {
    op = new OPManager();
  }

  public void ejbRemove() {
  }

  public void onMessage(Message msg) {

          Serializable obj = null;
          try {
            ObjectMessage message = (ObjectMessage) msg;
            obj = (Serializable)message.getObject();
            if (obj instanceof Loginlog) {//登录日志
            	Loginlog lh = (Loginlog)obj;
              if(null == lh.getLogouttime()){
                op.saveObj(lh);
              }else{
                op.updateObj(lh);
              }
            }else{//系统和业务日志
                op.saveObj(obj);
            }
          } catch (OPException ex) {
            logger.error(ex.toString());
          } catch (JMSException ex) {
            logger.error(ex.toString());
          }

           logger.info("=======##=log info: " + ClassHelper.getProperties(obj));


  }

  public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) {
    this.messageDrivenContext = messageDrivenContext;
  }
}
