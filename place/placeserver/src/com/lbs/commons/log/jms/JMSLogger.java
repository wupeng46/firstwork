package com.lbs.commons.log.jms;

import java.util.Hashtable;
import javax.jms.QueueSession;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.ObjectMessage;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.jms.JMSException;

import com.lbs.commons.GlobalNames;
import org.apache.commons.logging.*;
import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class JMSLogger {
  private static QueueSession qSession;
  private static QueueSender qSender;
  private static ObjectMessage msg;
  private static Log logger = LogFactory.getLog(JMSLogger.class);

  public static void init() {
    logger.info(" ======##======≥ı ‘ªØJMS......");
    Hashtable hash = new Hashtable();
    hash.put(Context.INITIAL_CONTEXT_FACTORY,
             GlobalNames.INITIAL_FACTORY);
    hash.put(Context.PROVIDER_URL, GlobalNames.JMS_URL);
    try {
      InitialContext ic = new InitialContext(hash);
      QueueConnectionFactory ConnFactory = (QueueConnectionFactory) ic.lookup(
          GlobalNames.JMS_CONNECTION_FACTROY);
      Queue queue = (Queue) ic.lookup(GlobalNames.JNDI_QUEUE_NAME);
      QueueConnection qConn = ConnFactory.createQueueConnection();
      qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      qSender = qSession.createSender(queue);
      msg = qSession.createObjectMessage();
    } catch (JMSException ex) {
      logger.error(" ======##======≥ı ‘ªØJMS ß∞‹£∫ " + ex.toString());
    } catch (NamingException ex) {
      logger.error(" ======##======≥ı ‘ªØJMS ß∞‹(mdb)£∫ " + ex.toString());
    }
    logger.info(" ======##======≥ı ‘ªØJMS≥…π¶");
  }

  public static void log(Serializable obj) {
    if((null == msg) || (null == qSender)) init();
    try {
      msg.setObject(obj);
      qSender.send(msg);
    } catch (Exception ex) {
      logger.error(ex.toString());
    }

  }
}

