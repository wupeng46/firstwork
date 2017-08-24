package com.lbs.commons.op;

import com.lbs.apps.common.ApplicationException;
import com.lbs.commons.TransManager;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: LBS</p>
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class OPUtil {
  public OPUtil() {
  }
  public static void rollbackTrans(int flag,TransManager trans) throws ApplicationException{
    try {
      if (1 == flag && null != trans)
          trans.rollback();
    } catch (OPException ex) {
      throw new ApplicationException("ÊÂÎñ»Ø¹öÊ§°Ü£¡",ex);
    }

  }
  
  public static OPException handleException(Exception e){
  	if(e instanceof JDBCException){
  		return new OPException(((JDBCException)e).getSQLException());
  	}else if(e instanceof HibernateException){
  		return new OPException((HibernateException)e);
  	}else{
  		return new OPException(e);
  	}
  }
}
