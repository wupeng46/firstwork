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
 * 持久化管理器
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
	 * 通过指定的class类型和id从数据库中将对象删除
	 * 
	 * @param c
	 *            待删除class的类型
	 * @param id
	 *            待删除class的id
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
	 * 根据class类型和id从数据库中重新获取对象
	 * 
	 * @param c
	 *            class的类型
	 * @param id
	 *            对象的id
	 * @return Object 如果对象不存在则抛NotFindException异常
	 * @throws OPException
	 *             对象获取失败
	 */
	public Object retrieveObj(Class c, Serializable id) throws OPException,
			NotFindException {
		Object obj = null;
		try {
			Session session = HibernateSession.currentSession();
			//obj = session.load(c, id);
			obj= session.get(c,id);          //modify by whd 2013.5.14 采用HIBERNATE3的新方法
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
	 * 获取对象的时候加锁，有如下五种：
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
	 * 对查询加锁
	 * 
	 * @param HQL
	 *            String hql
	 * @param alias
	 *            String 在from子句中别名
	 * @param lockMode
	 *            LockMode 锁的模式：
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
	 * 保存单个对象至数据库，使其持久化
	 * 
	 * @param obj
	 *            待保存对象
	 * @throws OPException
	 *             保存失败
	 */
	public void saveObj(Object obj) throws OPException {
		saveObjs(new Object[]{obj});
	}

	/**
	 * 在同一事务中保存一组对象
	 * 
	 * @param obj
	 *            待保存对象数组
	 * @throws OPException
	 *             保存失败
	 */
	public void saveObjs(Object[] objs) throws OPException {
		if (null == objs) {
			throw new OPException("保存失败，持久化对象为空！");
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
					throw new OPException("保存失败，持久化对象为空！");
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
	 * 在同一事务中保存并更新持久对象
	 * 
	 * @param save
	 *            要保存的对象数组
	 * @param update
	 *            要更新的对象数组
	 * @throws OPException
	 *             OP异常
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
						throw new OPException("保存失败，持久化对象为空！");
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
						throw new OPException("更新失败，持久化对象为空！");
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
	 * 更新单个对象
	 * 
	 * @param obj
	 *            待更新对象
	 * @throws OPException
	 *             更新失败
	 */
	public void updateObj(Object obj) throws OPException {
		updateObjs(new Object[]{obj});
	}

	/**
	 * 在同一事务中更新一组对象
	 * 
	 * @param obj
	 *            待更新对象数组
	 * @throws OPException
	 *             更新失败
	 */
	public void updateObjs(Object[] objs) throws OPException {
		if (null == objs) {
			throw new OPException("持久化对象为空！");
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
					throw new OPException("更新失败，持久化对象为空！");
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
	 * 关闭当前session
	 */
	private void closeSession() {
		try {
			HibernateSession.closeSession();
		} catch (OPException de) {
			System.err.println(de.getMessage());
		}
	}

	/**
	 * 根据给定HQL删除记录
	 * 
	 * @param key
	 *            HQL在资源文件中的key
	 * @param values
	 *            占位符的值
	 * @param types
	 *            占位符的类型
	 * @throws OPException
	 *             抛出给定异常
	 */
	public void removeObjs(String HQL) throws OPException {
		removeObjs(HQL, NO_ARGS, NO_TYPES);

	}

	/**
	 * 根据给定条件删除记录
	 * 
	 * @param HQL
	 *            hql
	 * @param values
	 *            占位符的值
	 * @param types
	 *            占位符的类型
	 * @throws OPException
	 *             抛出给定异常
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
			//modify by whd 2013.5.14 改成HIBERNATE3的删除方法
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
	 * 根据给定条件查询一条记录
	 * 
	 * @param HQL
	 *            hql
	 * @param values
	 *            占位符的值
	 * @param types
	 *            占位符的类型
	 * @return 满足条件的对象
	 * @throws OPException
	 *             抛出给定异常
	 */
	public List retrieveObjs(String HQL) throws OPException {
		return retrieveObjs(HQL, NO_ARGS, NO_TYPES);

	}

	/**
	 * 根据给定条件查询
	 * 
	 * @param key
	 *            hql
	 * @param values
	 *            占位符的值
	 * @param types
	 *            占位符的类型
	 * @return List类型的结果
	 * @throws OPException
	 *             抛出给定异常
	 */

	public List retrieveObjs(String HQL, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		logger.info("======##==hql: " + HQL);
		List results = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			//results = (List) session.find(HQL, paraValues, paraTypes);
			results = (List) session.createQuery(HQL).list();  //modify by whd 2013.5.14 采用hibernate3写法
			
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
	 * 根据给定条件查询，如果配置了缓存，使用此方法性能比较好
	 * 
	 * @param HQL
	 *            hql
	 * @return Iterator类型的结果
	 * @throws OPException
	 *             抛出给定异常
	 */
	public Iterator iterate(String HQL) throws OPException {
		return iterate(HQL, NO_ARGS, NO_TYPES);
	}

	/**
	 * 根据给定条件查询，如果配置了缓存，使用此方法性能比较好
	 * 
	 * @param HQL
	 *            hql
	 * @param values
	 *            占位符的值
	 * @param types
	 *            占位符的类型
	 * @return Iterator类型的结果
	 * @throws OPException
	 *             抛出给定异常
	 */
	public Iterator iterate(String HQL, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		Iterator it = null;
		Session session = null;
		try {
			session = HibernateSession.currentSession();
			//it = session.iterate(HQL, paraValues, paraTypes);
			//modify by whd 2013.5.14 更换成hibernate3的相关方法
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
	 * 分页查询
	 * 
	 * @param HQL
	 *            hql
	 * @param paraNames
	 *            参数名数组
	 * @param paraValues
	 *            参数值数组
	 * @param pageNumber
	 *            页码，从0开始，即0是第一页，-1是不分页
	 * @return 返回一页数据
	 * @throws OPException
	 *             OP异常
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
					logger.info("查询参数名: " + paraNames[i]);
					logger.info("查询参数值: " + paraValues[i]);
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
	 * 分页查询
	 * 
	 * @param HQL
	 *            hql
	 * @param pageNumber
	 *            页码，从0开始，即0是第一页,-1是不分页
	 * @return 返回一页数据
	 * @throws OPException
	 *             OP异常
	 */
	public List query(String HQL, int pageNumber) throws OPException {
		return query(HQL, NO_PARANAMES, NO_PARAVALUES, pageNumber,
				GlobalNames.PAGE_SIZE);
	}

	/**
	 * 分页查询
	 * 
	 * @param key
	 *            HBM文件中HQL的key值
	 * @param paraNames
	 *            参数名数组
	 * @param paraValues
	 *            参数值数组
	 * @param pageNumber
	 *            页码，从0开始，即0是第一页,-1是不分页
	 * @return 返回一页数据
	 * @throws OPException
	 *             OP异常
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
					logger.info("查询参数名: " + paraNames[i]);
					logger.info("查询参数值: " + paraValues[i]);
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
	 * 分页查询
	 * 
	 * @param key
	 *            HBM文件中HQL的key值
	 * @param pageNumber
	 *            页码，从0开始，即0是第一页,-1是不分页
	 * @return 返回一页数据
	 * @throws OPException
	 *             OP异常
	 */
	public List getQuery(String key, int pageNumber) throws OPException {
		return getQuery(key, NO_PARANAMES, NO_PARAVALUES, pageNumber);
	}

	/**
	 * 返回满足条件的总记录数
	 * 
	 * @param HQL
	 *            hql语句
	 * @return 记录总数
	 * @throws OPException
	 */
	public Integer getCount(String HQL) throws OPException {
		List list = retrieveObjs(HQL);
		//return (Integer) list.iterator().next();
		return new Integer(((Long)list.iterator().next()).intValue());
	}

	/**
	 * 返回满足条件的总记录数
	 * 
	 * @param HQL
	 *            hql语句
	 * @return 记录总数
	 * @throws OPException
	 */
	public Integer getCount(String HQL, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		List list = retrieveObjs(HQL, paraValues, paraTypes);
		//return (Integer) list.iterator().next();
		return new Integer(((Long)list.iterator().next()).intValue());
	}
	
	/**
	 * 执行sql的查询
	 * 
	 * @return 查询的结果
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
			throw new OPException("数据库访问错误：执行通用查询时出错！");
		} catch (Exception e) {
			throw new OPException("执行通用查询时出现异常！");
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
	 * 返回满足条件的总记录数
	 * 
	 * @param HQL
	 *            hql语句
	 * @return 记录总数
	 * @throws OPException
	 */
	public String getSequence(String sequenceName) throws OPException {
		if (null == sequenceName)
			throw new OPException("sequence的名字不能为空！");
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
	 * 返回seq
	 * 
	 * @param sequenceName
	 *            seq名称
	 * @param sequenceLen
	 *            seq长度，0为直接返回seq值，不补零
	 * @param sequencePre
	 *            seq前缀，'0'为不加前缀
	 * @return 记录总数
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
	 * 按照指定的步长返回sequence,sequence只能用这种方法来获取，不能使用其他两个获取sequence的方法
	 * 否则返回的结果将有可能不是预期的结果,如果步长是0或1则设定步长为1
	 * @param sequenceName sequence名称
	 * @param step  sequence步长
	 * @return
	 * @throws OPException
	 */
	public synchronized String getSequence(String sequenceName, int step) throws OPException {
		if (null == sequenceName)
			throw new OPException("sequence的名字不能为空！");
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
	//	 * 分页查询
	//	 *
	//	 * @param HQL
	//	 * hql
	//	 * @param paraNames
	//	 * 参数名数组
	//	 * @param paraValues
	//	 * 参数值数组
	//	 * @param pageNumber
	//	 * 页码，从0开始，即0是第一页，-1是不分页
	//	 * @return 返回一页数据
	//	 * @throws OPException
	//	 * OP异常
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
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
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
	 * 分页查询
	 * 
	 * @param HQL
	 *            hql
	 * @param pageNumber
	 *            页码，从0开始，即0是第一页,-1是不分页
	 * @return 返回一页数据
	 * @throws OPException
	 *             OP异常
	 */
	public List query(String HQL, int pageNumber, int pageSize)
			throws OPException {
		return query(HQL, NO_PARANAMES, NO_PARAVALUES, pageNumber, pageSize);
	}

	/**
	 * 分页查询
	 * 
	 * @param key
	 *            HBM文件中HQL的key值
	 * @param paraNames
	 *            参数名数组
	 * @param paraValues
	 *            参数值数组
	 * @param pageNumber
	 *            页码，从0开始，即0是第一页,-1是不分页
	 * @return 返回一页数据
	 * @throws OPException
	 *             OP异常
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
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
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
	 * 分页查询
	 * 
	 * @param key
	 *            HBM文件中HQL的key值
	 * @param pageNumber
	 *            页码，从0开始，即0是第一页,-1是不分页
	 * @return 返回一页数据
	 * @throws OPException
	 *             OP异常
	 */
	public List getQuery(String key, int pageNumber, int pageSize)
			throws OPException {
		return getQuery(key, NO_PARANAMES, NO_PARAVALUES, null, pageNumber,
				pageSize);
	}
	
	/**
	 * 执行sql的查询
	 * 
	 * @return 查询的结果
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
			//转化为动态Bean
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

			throw new OPException("数据库访问错误：执行通用查询时出错！");
		} catch (Exception e) {
			throw new OPException("执行通用查询时出现异常！");
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
	 * 清除Hibernate Session缓存中的对象
	 */
	public void clearSessionCache()throws OPException{
		Session session = HibernateSession.currentSession();
		session.clear();
	}
}