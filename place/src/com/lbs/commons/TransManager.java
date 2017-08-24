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
 * �������񣬱���Ƕ������ĳ���
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
	 * ��ʼ����
	 * 
	 * @throws OPException
	 * @return int - 1��ʾ�¿�ʼ��һ������0��ʾ��ͬһ���߳����Ѿ���һ������������û�п�ʼ������
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
	 * �ύ����
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
	 * �ع�����
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
	 * �õ���ǰ�����״̬
	 * 
	 * @return boolean false����ǰû������true����ǰ��������
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