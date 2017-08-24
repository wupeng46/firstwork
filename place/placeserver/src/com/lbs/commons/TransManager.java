package com.lbs.commons;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lbs.commons.op.HibernateSession;
import com.lbs.commons.op.OPException;

/**
 * <p>
 * Title: leaf framework (lemis core platform)
 * </p>
 * <p>
 * Description:
 * </p>
 * 管理事务，避免嵌套事务的出现
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: LBS
 * </p>
 * 
 * @author LBS POC TEAM
 * @author chenshuichao
 * @version 1.0
 */
public final class TransManager {
	private static final ThreadLocal threadTrans = new ThreadLocal();

	public TransManager() {

	}

	/**
	 * 开始事务
	 * 
	 * @throws OPException
	 * @return int - 1表示新开始了一个事务，0表示在同一个线程内已经有一个开启的事务，没有开始新事务
	 */
	public int begin() throws OPException {
		Object trans = threadTrans.get();
		if (trans == null) {
			Session session = (Session) HibernateSession.currentSession();
			try {
				Transaction tx = session.beginTransaction();
				threadTrans.set(tx);
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new OPException(e);
			}
			return 1;
		} else {
			return 0;
		}

	}

	/**
	 * 提交事务
	 * 
	 * @throws OPException
	 */
	public void commit() throws OPException {
		Object trans = threadTrans.get();
		if (trans != null) {
			try {
				Transaction t = (Transaction) trans;
				t.commit();
				threadTrans.set(null);
				HibernateSession.closeSession();
			} catch (HibernateException e) {
				//				e.printStackTrace();
				throw new OPException(e);
			}
		}
	}

	/**
	 * 回滚事务
	 * 
	 * @throws OPException
	 */
	public void rollback() throws OPException {
		Object trans = threadTrans.get();
		if (trans != null) {
			try {
				Transaction t = (Transaction) trans;
				t.rollback();
				threadTrans.set(null);
				HibernateSession.closeSession();
			} catch (HibernateException e) {
				//				e.printStackTrace();
				throw new OPException(e);
			}
		}
	}

	/**
	 * 得到当前事务的状态
	 * 
	 * @return boolean false：当前没有事务；true：当前存在事务
	 */
	public static boolean getTransState() {
		Object trans = threadTrans.get();
		if (trans == null) {
			return false;
		} else {
			return true;
		}
	}

}