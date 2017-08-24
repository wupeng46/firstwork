package com.lbs.commons.op;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.commons.ClassHelper;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.StringHelper;
import com.lbs.commons.TransManager;

/**
 * �־û�������
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: LBS
 * </p>
 * 
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class OPManager {
	private Log logger = LogFactory.getLog(OPManager.class);
	private final Object[] NO_ARGS = new Object[0];
	private final Type[] NO_TYPES = new Type[0];

	private final String[] NO_PARANAMES = new String[0];
	private final Object[] NO_PARAVALUES = new Object[0];
	public OPManager() {
	}

	public void newSession() throws OPException {
		HibernateSession.closeSession();

	}
	/**
	 * ͨ��ָ����class���ͺ�id�����ݿ��н�����ɾ��
	 * 
	 * @param c
	 *            ��ɾ��class������
	 * @param id
	 *            ��ɾ��class��id
	 * @throws OPException
	 */
	public void removeObj(Class c, Serializable id) throws OPException {
		TransManager trans = null;
		int flag = 0;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			trans = new TransManager();
			flag = trans.begin();
			Object obj = session.load(c, id);
			session.delete(obj);
			if (1 == flag)
				trans.commit();
		} catch (HibernateException e) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * ����class���ͺ�id�����ݿ������»�ȡ����
	 * 
	 * @param c
	 *            class������
	 * @param id
	 *            �����id
	 * @return Object ������󲻴�������NotFindException�쳣
	 * @throws OPException
	 *             �����ȡʧ��
	 */
	public Object retrieveObj(Class c, Serializable id) throws OPException,
			NotFindException {
		Object obj = null;
		try {
			Session session = HibernateSession.currentSession();
			//obj = session.load(c, id);
			obj= session.get(c,id);          //modify by whd 2013.5.14 ����HIBERNATE3���·���
			if (obj==null){
				throw new NotFindException();
			}
		} catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				throw new NotFindException(he);
			} else {
				throw OPUtil.handleException(he);
			}
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}
		return obj;
	}

	/**
	 * ��ȡ�����ʱ����������������֣�
	 * LockMode.NONE/LockMode.READ/LockMode.UPGRADE/LockMode.UPGRADE_NOWAIT/LockMode.WRITE
	 * 
	 * @param c
	 *            Class
	 * @param id
	 *            Serializable
	 * @param lockMode
	 *            LockMode
	 * @throws OPException
	 * @throws NotFindException
	 * @return Object
	 */
	public Object retrieveObjForUpdate(Class c, Serializable id,
			LockMode lockMode) throws OPException, NotFindException {
		Object obj = null;
		try {
			Session session = HibernateSession.currentSession();
			obj = session.load(c, id, lockMode);
		} catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				throw new NotFindException(he);
			} else {
				throw OPUtil.handleException(he);
			}
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}
		return obj;
	}

	/**
	 * �Բ�ѯ����
	 * 
	 * @param HQL
	 *            String hql
	 * @param alias
	 *            String ��from�Ӿ��б���
	 * @param lockMode
	 *            LockMode ����ģʽ��
	 *            LockMode.NONE/LockMode.READ/LockMode.UPGRADE/LockMode.UPGRADE_NOWAIT/LockMode.WRITE
	 * @throws OPException
	 * @return List
	 */
	public List queryForUpdate(String HQL, String alias, LockMode lockMode)
			throws OPException {
		Session session = null;
		List list = null;
		try {
			session = HibernateSession.currentSession();
			Query query = session.createQuery(HQL);
			query.setLockMode(alias, lockMode);
			list = query.list();
		} catch (HibernateException ex) {
			throw OPUtil.handleException(ex);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}

		if (list.iterator().hasNext()) {
			return list;
		} else {
			return null;
		}

	}

	/**
	 * ���浥�����������ݿ⣬ʹ��־û�
	 * 
	 * @param obj
	 *            ���������
	 * @throws OPException
	 *             ����ʧ��
	 */
	public void saveObj(Object obj) throws OPException {
		saveObjs(new Object[]{obj});
	}

	/**
	 * ��ͬһ�����б���һ�����
	 * 
	 * @param obj
	 *            �������������
	 * @throws OPException
	 *             ����ʧ��
	 */
	public void saveObjs(Object[] objs) throws OPException {
		if (null == objs) {
			throw new OPException("����ʧ�ܣ��־û�����Ϊ�գ�");
		}
		TransManager trans = null;
		int flag = 0;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			trans = new TransManager();
			flag = trans.begin();
			for (int i = 0; i < objs.length; i++) {
				if (null == objs[i]) {
					throw new OPException("����ʧ�ܣ��־û�����Ϊ�գ�");
				}
				session.save(objs[i]);
				if(i % GlobalNames.JDBC_BATCH_SIZE == 0 || i == (objs.length - 1)){
					session.flush();
					session.clear();
				}
			}
			if (1 == flag)
				trans.commit();

		} catch (HibernateException he) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(e);
		}

	}

	/**
	 * ��ͬһ�����б��沢���³־ö���
	 * 
	 * @param save
	 *            Ҫ����Ķ�������
	 * @param update
	 *            Ҫ���µĶ�������
	 * @throws OPException
	 *             OP�쳣
	 */
	public void saveAndUpdateObjs(Object[] save, Object[] update)
			throws OPException {
		TransManager trans = null;
		int flag = 0;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			trans = new TransManager();
			flag = trans.begin();
			if (null != save) {
				for (int i = 0; i < save.length; i++) {
					if (null == save[i]) {
						throw new OPException("����ʧ�ܣ��־û�����Ϊ�գ�");
					}
					session.save(save[i]);
					if(i % GlobalNames.JDBC_BATCH_SIZE == 0 || i == (save.length - 1)){
						session.flush();
						session.clear();
					}
				}
			}
			if (null != update) {
				for (int i = 0; i < update.length; i++) {
					if (null == update[i]) {
						throw new OPException("����ʧ�ܣ��־û�����Ϊ�գ�");
					}
					session.update(update[i]);
					if(i % GlobalNames.JDBC_BATCH_SIZE == 0 || i == (update.length - 1)){
						session.flush();
						session.clear();
					}
				}
			}
			if (1 == flag)
				trans.commit();

		} catch (HibernateException he) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * ���µ�������
	 * 
	 * @param obj
	 *            �����¶���
	 * @throws OPException
	 *             ����ʧ��
	 */
	public void updateObj(Object obj) throws OPException {
		updateObjs(new Object[]{obj});
	}

	/**
	 * ��ͬһ�����и���һ�����
	 * 
	 * @param obj
	 *            �����¶�������
	 * @throws OPException
	 *             ����ʧ��
	 */
	public void updateObjs(Object[] objs) throws OPException {
		if (null == objs) {
			throw new OPException("�־û�����Ϊ�գ�");
		}

		TransManager trans = null;
		int flag = 0;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			trans = new TransManager();
			flag = trans.begin();
			for (int i = 0; i < objs.length; i++) {
				if (null == objs[i]) {
					throw new OPException("����ʧ�ܣ��־û�����Ϊ�գ�");
				}
				session.update(objs[i]);
				if(i % GlobalNames.JDBC_BATCH_SIZE == 0 || i == (objs.length - 1)){
					session.flush();
					session.clear();
				}
			}
			if (1 == flag)
				trans.commit();

		} catch (HibernateException he) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * �رյ�ǰsession
	 */
	private void closeSession() {
		try {
			HibernateSession.closeSession();
		} catch (OPException de) {
			System.err.println(de.getMessage());
		}
	}

	/**
	 * ���ݸ���HQLɾ����¼
	 * 
	 * @param key
	 *            HQL����Դ�ļ��е�key
	 * @param values
	 *            ռλ����ֵ
	 * @param types
	 *            ռλ��������
	 * @throws OPException
	 *             �׳������쳣
	 */
	public void removeObjs(String HQL) throws OPException {
		removeObjs(HQL, NO_ARGS, NO_TYPES);

	}

	/**
	 * ���ݸ�������ɾ����¼
	 * 
	 * @param HQL
	 *            hql
	 * @param values
	 *            ռλ����ֵ
	 * @param types
	 *            ռλ��������
	 * @throws OPException
	 *             �׳������쳣
	 */
	public void removeObjs(String HQL, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		logger.info("======##==hql: " + HQL);
		Session session = null;
		TransManager trans = null;
		int flag = 0;
		try {
			session = HibernateSession.currentSession();
			trans = new TransManager();
			flag = trans.begin();
			//int delNum = session.delete(HQL, paraValues, paraTypes);
			//modify by whd 2013.5.14 �ĳ�HIBERNATE3��ɾ������
			Query query =session.createQuery(HQL);
			query.executeUpdate();			
			if (1 == flag)
				trans.commit();
		} catch (Exception e) {
			session.clear();
			if (1 == flag && null != trans)
				trans.rollback();
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * ���ݸ���������ѯһ����¼
	 * 
	 * @param HQL
	 *            hql
	 * @param values
	 *            ռλ����ֵ
	 * @param types
	 *            ռλ��������
	 * @return ���������Ķ���
	 * @throws OPException
	 *             �׳������쳣
	 */
	public List retrieveObjs(String HQL) throws OPException {
		return retrieveObjs(HQL, NO_ARGS, NO_TYPES);

	}

	/**
	 * ���ݸ���������ѯ
	 * 
	 * @param key
	 *            hql
	 * @param values
	 *            ռλ����ֵ
	 * @param types
	 *            ռλ��������
	 * @return List���͵Ľ��
	 * @throws OPException
	 *             �׳������쳣
	 */

	public List retrieveObjs(String HQL, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		logger.info("======##==hql: " + HQL);
		List results = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			//results = (List) session.find(HQL, paraValues, paraTypes);
			results = (List) session.createQuery(HQL).list();  //modify by whd 2013.5.14 ����hibernate3д��
			
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}

		if (results.iterator().hasNext()) {
			return results;
		} else {
			return null;
		}

	}

	/**
	 * ���ݸ���������ѯ����������˻��棬ʹ�ô˷������ܱȽϺ�
	 * 
	 * @param HQL
	 *            hql
	 * @return Iterator���͵Ľ��
	 * @throws OPException
	 *             �׳������쳣
	 */
	public Iterator iterate(String HQL) throws OPException {
		return iterate(HQL, NO_ARGS, NO_TYPES);
	}

	/**
	 * ���ݸ���������ѯ����������˻��棬ʹ�ô˷������ܱȽϺ�
	 * 
	 * @param HQL
	 *            hql
	 * @param values
	 *            ռλ����ֵ
	 * @param types
	 *            ռλ��������
	 * @return Iterator���͵Ľ��
	 * @throws OPException
	 *             �׳������쳣
	 */
	public Iterator iterate(String HQL, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		Iterator it = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			//it = session.iterate(HQL, paraValues, paraTypes);
			//modify by whd 2013.5.14 ������hibernate3����ط���
			it = session.createQuery(HQL).iterate();
			
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}
		if (it.hasNext()) {
			return it;
		} else {
			return null;
		}

	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param HQL
	 *            hql
	 * @param paraNames
	 *            ����������
	 * @param paraValues
	 *            ����ֵ����
	 * @param pageNumber
	 *            ҳ�룬��0��ʼ����0�ǵ�һҳ��-1�ǲ���ҳ
	 * @return ����һҳ����
	 * @throws OPException
	 *             OP�쳣
	 */
	public List query(String HQL, String[] paraNames, Object[] paraValues,
			int pageNumber, int pageSize) throws OPException {
		logger.info("======##==hql: " + HQL);
		List lt = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			Query query = session.createQuery(HQL);
			if (null != paraNames) {

				for (int i = 0; i < paraNames.length; i++) {
					logger.info("��ѯ������: " + paraNames[i]);
					logger.info("��ѯ����ֵ: " + paraValues[i]);
					if (null != paraValues[i])
						query.setParameter(paraNames[i], paraValues[i]);
				}
			}
			if (-1 != pageNumber) {
				query.setFirstResult(pageSize * pageNumber);
				query.setMaxResults(pageSize);
			}

			lt = query.list();

		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}
		if (lt.iterator().hasNext()) {
			return lt;
		} else {
			return null;
		}
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param HQL
	 *            hql
	 * @param pageNumber
	 *            ҳ�룬��0��ʼ����0�ǵ�һҳ,-1�ǲ���ҳ
	 * @return ����һҳ����
	 * @throws OPException
	 *             OP�쳣
	 */
	public List query(String HQL, int pageNumber) throws OPException {
		return query(HQL, NO_PARANAMES, NO_PARAVALUES, pageNumber,
				GlobalNames.PAGE_SIZE);
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param key
	 *            HBM�ļ���HQL��keyֵ
	 * @param paraNames
	 *            ����������
	 * @param paraValues
	 *            ����ֵ����
	 * @param pageNumber
	 *            ҳ�룬��0��ʼ����0�ǵ�һҳ,-1�ǲ���ҳ
	 * @return ����һҳ����
	 * @throws OPException
	 *             OP�쳣
	 */
	public List getQuery(String key, String[] paraNames, Object[] paraValues,
			int pageNumber) throws OPException {
		List lt = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			Query query = session.getNamedQuery(key);
			if (null != paraNames) {

				for (int i = 0; i < paraNames.length; i++) {
					logger.info("��ѯ������: " + paraNames[i]);
					logger.info("��ѯ����ֵ: " + paraValues[i]);
					if (null != paraValues[i])
						query.setParameter(paraNames[i], paraValues[i]);
				}
			}
			if (-1 != pageNumber) {
				query.setFirstResult(GlobalNames.PAGE_SIZE * pageNumber);
				query.setMaxResults(GlobalNames.PAGE_SIZE);
			}
			lt = query.list();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}

		if (lt.iterator().hasNext()) {
			return lt;
		} else {
			return null;
		}

	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param key
	 *            HBM�ļ���HQL��keyֵ
	 * @param pageNumber
	 *            ҳ�룬��0��ʼ����0�ǵ�һҳ,-1�ǲ���ҳ
	 * @return ����һҳ����
	 * @throws OPException
	 *             OP�쳣
	 */
	public List getQuery(String key, int pageNumber) throws OPException {
		return getQuery(key, NO_PARANAMES, NO_PARAVALUES, pageNumber);
	}

	/**
	 * ���������������ܼ�¼��
	 * 
	 * @param HQL
	 *            hql���
	 * @return ��¼����
	 * @throws OPException
	 */
	public Integer getCount(String HQL) throws OPException {
		List list = retrieveObjs(HQL);
		//return (Integer) list.iterator().next();
		return new Integer(((Long)list.iterator().next()).intValue());
	}

	/**
	 * ���������������ܼ�¼��
	 * 
	 * @param HQL
	 *            hql���
	 * @return ��¼����
	 * @throws OPException
	 */
	public Integer getCount(String HQL, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		List list = retrieveObjs(HQL, paraValues, paraTypes);
		//return (Integer) list.iterator().next();
		return new Integer(((Long)list.iterator().next()).intValue());
	}
	
	/**
	 * ִ��sql�Ĳ�ѯ
	 * 
	 * @return ��ѯ�Ľ��
	 */
	
	public String executeMinMaxSQLQuery(String strSQL)
			throws OPException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		Collection colRowSet = null;
		String ls_str=null;
//		DynaBean rowtest = null;

		try {
			Connection conn = HibernateSession.currentSession().connection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			while(rs.next()){
				ls_str=rs.getString(1);
			}
		} catch (SQLException se) {
			throw new OPException("���ݿ���ʴ���ִ��ͨ�ò�ѯʱ����");
		} catch (Exception e) {
			throw new OPException("ִ��ͨ�ò�ѯʱ�����쳣��");
		} finally {
			try {
				if (rsmd != null)
					rsmd = null;
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (!TransManager.getTransState()) {
					HibernateSession.closeSession();
				}
				
			} catch (Exception e) {
			}
		}
		return ls_str;
	}

	/**
	 * ���������������ܼ�¼��
	 * 
	 * @param HQL
	 *            hql���
	 * @return ��¼����
	 * @throws OPException
	 */
	public String getSequence(String sequenceName) throws OPException {
		if (null == sequenceName)
			throw new OPException("sequence�����ֲ���Ϊ�գ�");
		String seq = null;
		String sql = "select " + sequenceName + ".nextval from dual";
		Session session = HibernateSession.currentSession();
		ResultSet rs = null;
		try {
			PreparedStatement ps = session.connection().prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				seq = rs.getString(1);
			if(null != rs)
				rs.close();
			if(null != ps)
				ps.close();
		} catch (HibernateException ex) {
			throw OPUtil.handleException(ex);
		} catch (SQLException ex) {
			throw OPUtil.handleException(ex);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}
		return seq;
	}

	/**
	 * ����seq
	 * 
	 * @param sequenceName
	 *            seq����
	 * @param sequenceLen
	 *            seq���ȣ�0Ϊֱ�ӷ���seqֵ��������
	 * @param sequencePre
	 *            seqǰ׺��'0'Ϊ����ǰ׺
	 * @return ��¼����
	 * @throws OPException
	 *  
	 */
	public String getSequence(String sequenceName, int sequenceLen,
			String sequencePre) throws OPException {
		String seq = getSequence(sequenceName);
		if (sequenceLen == 0 && "0".equals(sequencePre)) {
			return seq;
		}

		if (sequenceLen == 0 && !"0".equals(sequencePre)) {
			return (sequencePre + seq);
		}

		if (sequenceLen != 0 && "0".equals(sequencePre)) {
			return StringHelper.fillZero(seq, sequenceLen);
		}

		if (sequenceLen != 0 && !"0".equals(sequencePre)) {
			seq = StringHelper.fillZero(seq, sequenceLen);
			return (sequencePre + seq.substring(sequencePre.length()));
		}

		return seq;
	}

	/**
	 * ����ָ���Ĳ�������sequence,sequenceֻ�������ַ�������ȡ������ʹ������������ȡsequence�ķ���
	 * ���򷵻صĽ�����п��ܲ���Ԥ�ڵĽ��,���������0��1���趨����Ϊ1
	 * @param sequenceName sequence����
	 * @param step  sequence����
	 * @return
	 * @throws OPException
	 */
	public synchronized String getSequence(String sequenceName, int step) throws OPException {
		if (null == sequenceName)
			throw new OPException("sequence�����ֲ���Ϊ�գ�");
		String seq = null;
		if(0 == step || 1 == step)
			return getSequence(sequenceName);

		Session session = HibernateSession.currentSession();
		ResultSet rs = null;
		try {
			Connection conn = session.connection();
			PreparedStatement ps = conn.prepareStatement("alter sequence "
					+ sequenceName + " increment by " + step + " nocache");
			ps.executeUpdate();
			ps = conn.prepareStatement("select " + sequenceName
					+ ".nextval from dual");
			ps.executeQuery();
			ps = conn.prepareStatement("alter sequence " + sequenceName
					+ " increment by 1 nocache");
			ps.executeUpdate();
			ps = session.connection().prepareStatement(
					"select " + sequenceName + ".nextval from dual");
			rs = ps.executeQuery();

			if (rs.next())
				seq = rs.getString(1);
			if (rs!=null) {
				rs.close();
			}

		} catch (HibernateException ex) {
			throw OPUtil.handleException(ex);
		} catch (SQLException ex) {
			throw OPUtil.handleException(ex);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}
		return seq;
	}
	/**
	 * delObjs
	 * 
	 * @param ids
	 *            String[]
	 * @throws OPException
	 */
	public void delObjs(String[] ids, String className, String key)
			throws OPException {
		String id = null;
		try {
			StringBuffer buffer = new StringBuffer(1024);
			String lowerClassName = className.toLowerCase();
			buffer.append("from ");
			buffer.append(className);
			buffer.append(" as ");
			buffer.append(lowerClassName);
			buffer.append(" where ");
			buffer.append(lowerClassName);
			buffer.append(".");
			buffer.append(key);
			buffer.append(" in (");
			for (int i = 0; i < ids.length; i++) {
				id = ids[i];
				buffer.append("\'").append(id).append("\',");
				logger.info(id);
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append(")");
			logger.info(buffer.toString());
			removeObjs(buffer.toString());
		} catch (OPException ex) {
			throw OPUtil.handleException(ex);
		}
	}
	//
	//	/**
	//	 * ��ҳ��ѯ
	//	 *
	//	 * @param HQL
	//	 * hql
	//	 * @param paraNames
	//	 * ����������
	//	 * @param paraValues
	//	 * ����ֵ����
	//	 * @param pageNumber
	//	 * ҳ�룬��0��ʼ����0�ǵ�һҳ��-1�ǲ���ҳ
	//	 * @return ����һҳ����
	//	 * @throws OPException
	//	 * OP�쳣
	//	 */
	//	public List query(String HQL, String[] paraNames, Object[] paraValues,
	//			int pageNumber, int pageSize) throws OPException {
	//		return this.query(HQL, paraNames, paraValues, null, pageNumber,
	//				pageSize);
	//	}

	public List query(String HQL, String[] paraNames, Object[] paraValues,
			Type[] paraTypes, int pageNumber, int pageSize) throws OPException {
		logger.info("======##==hql: " + HQL);
		List lt = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			Query query = session.createQuery(HQL);
			if (null != paraNames) {

				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("��ѯ������: " + paraNames[i]);
					logger.debug("��ѯ����ֵ: " + paraValues[i]);
					if (null != paraValues[i])
						if ((null == paraTypes) || (paraTypes.length == 0)) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i],
									paraTypes[i]);
						}
				}
			}
			if (-1 != pageNumber) {
				query.setFirstResult(pageSize * pageNumber);
				query.setMaxResults(pageSize);
			}

			lt = query.list();

		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			if (!TransManager.getTransState()) {
				closeSession();
			}
		}
		if (lt.iterator().hasNext()) {
			return lt;
		} else {
			return null;
		}
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param HQL
	 *            hql
	 * @param pageNumber
	 *            ҳ�룬��0��ʼ����0�ǵ�һҳ,-1�ǲ���ҳ
	 * @return ����һҳ����
	 * @throws OPException
	 *             OP�쳣
	 */
	public List query(String HQL, int pageNumber, int pageSize)
			throws OPException {
		return query(HQL, NO_PARANAMES, NO_PARAVALUES, pageNumber, pageSize);
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param key
	 *            HBM�ļ���HQL��keyֵ
	 * @param paraNames
	 *            ����������
	 * @param paraValues
	 *            ����ֵ����
	 * @param pageNumber
	 *            ҳ�룬��0��ʼ����0�ǵ�һҳ,-1�ǲ���ҳ
	 * @return ����һҳ����
	 * @throws OPException
	 *             OP�쳣
	 */

	public List getQuery(String key, String[] paraNames, Object[] paraValues,
			Type[] paraTypes, int pageNumber, int pageSize) throws OPException {
		List lt = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			Query query = session.getNamedQuery(key);
			if (null != paraNames) {

				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("��ѯ������: " + paraNames[i]);
					logger.debug("��ѯ����ֵ: " + paraValues[i]);
					if (null != paraValues[i])
						if ((null == paraTypes) || (paraTypes.length == 0)) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i],
									paraTypes[i]);
						}
				}
			}
			if (-1 != pageNumber) {
				query.setFirstResult(pageSize * pageNumber);
				query.setMaxResults(pageSize);
			}
			lt = query.list();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			if (!TransManager.getTransState())
				closeSession();
		}

		if (lt.iterator().hasNext()) {
			return lt;
		} else {
			return null;
		}
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param key
	 *            HBM�ļ���HQL��keyֵ
	 * @param pageNumber
	 *            ҳ�룬��0��ʼ����0�ǵ�һҳ,-1�ǲ���ҳ
	 * @return ����һҳ����
	 * @throws OPException
	 *             OP�쳣
	 */
	public List getQuery(String key, int pageNumber, int pageSize)
			throws OPException {
		return getQuery(key, NO_PARANAMES, NO_PARAVALUES, null, pageNumber,
				pageSize);
	}
	
	/**
	 * ִ��sql�Ĳ�ѯ
	 * 
	 * @return ��ѯ�Ľ��
	 */

	public Collection executeSQLQuery(String strSQL)
			throws OPException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		Collection colRowSet = null;
//		DynaBean rowtest = null;

		try {
			Connection conn = HibernateSession.currentSession().connection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			//      rsmd = rs.getMetaData();
			//ת��Ϊ��̬Bean
			ResultSetDynaClass rsdc = null;
			rsdc = new ResultSetDynaClass(rs, true);
			BasicDynaClass bdc = new BasicDynaClass("test",
					BasicDynaBean.class, rsdc.getDynaProperties());
			Iterator rows = rsdc.iterator();
			if (rows.hasNext()) {
				colRowSet = new ArrayList();
			} else {
				colRowSet = null;
			}
			while (rows.hasNext()) {
				DynaBean oldRow = (DynaBean) rows.next();
				DynaBean newRow = bdc.newInstance();
				ClassHelper.copyProperties(oldRow, newRow);
				colRowSet.add(newRow);
			}
		} catch (SQLException se) {

			throw new OPException("���ݿ���ʴ���ִ��ͨ�ò�ѯʱ����");
		} catch (Exception e) {
			throw new OPException("ִ��ͨ�ò�ѯʱ�����쳣��");
		} finally {
			try {
				if (rsmd != null)
					rsmd = null;
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (!TransManager.getTransState()) {
					HibernateSession.closeSession();
				}
				
			} catch (Exception e) {
			}
		}
		return colRowSet;
	}
	

	/**
	 * ���Hibernate Session�����еĶ���
	 */
	public void clearSessionCache()throws OPException{
		Session session = HibernateSession.currentSession();
		session.clear();
	}
}