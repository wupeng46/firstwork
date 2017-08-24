package com.lbs.apps.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.lbs.commons.TransManager;
import com.lbs.commons.op.HibernateSession;
import com.lbs.commons.op.OPException;

public class DataBaseUtil {
	
	//执行单条SQL
	public String execSql(String ls_sql) throws ApplicationException{
		Session session = null; 
		Connection conn = null; 
		//Statement stmt = null;
		PreparedStatement ps = null;
		TransManager trans = new TransManager();
		try {
			trans.begin();
			session = HibernateSession.currentSession(); // 得到HIB连接
			conn = session.connection();
		} catch (OPException e) {
			//throw new ApplicationException("建立数据库连接失败",e);
			return "建立数据库连接失败...";
		}catch (HibernateException e) {				
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				//throw new ApplicationException("关闭数据库连接失败",e1);
				return "关闭数据库连接失败...";
			}
			//throw new ApplicationException("建立数据库连接失败",e);
			return "建立数据库连接失败...";
		}		
		try {			
			ps=conn.prepareStatement(ls_sql);			
			ps.executeUpdate();
			ps.close();
			trans.commit();						
			HibernateSession.closeSession();
		}catch (Exception e1) {
			//System.out.println( e1.getMessage() );
			try {
				trans.rollback();
			} catch (Exception e2) {
				//throw new ApplicationException("事务回滚时失败...",e2);
				return "事务回滚时失败...";
			}
			try {
				HibernateSession.closeSession();
			} catch (OPException e) {
				//throw new ApplicationException("事务回滚关闭session时失败...",e);
				return "事务回滚关闭session时失败...";
			}
			//throw new ApplicationException("保存数据时失败...",e1);
			return "保存数据时失败...";
		}
		return "0";
	}
	
	//执行多条SQL
	public String execBatchSql(List list_sql) throws ApplicationException{
		Session session = null; 
		Connection conn = null; 
		//Statement stmt = null;
		PreparedStatement ps = null;
		TransManager trans = new TransManager();
		try {
			trans.begin();
			session = HibernateSession.currentSession(); // 得到HIB连接
			conn = session.connection();
		} catch (OPException e) {
			//throw new ApplicationException("建立数据库连接失败",e);
			return "建立数据库连接失败...";
		}catch (HibernateException e) {				
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				//throw new ApplicationException("关闭数据库连接失败",e1);
				return "关闭数据库连接失败...";
			}
			//throw new ApplicationException("建立数据库连接失败",e);
			return "建立数据库连接失败...";
		}		
		try {			
			for (int i=0;i<list_sql.size();i++){
				ps=conn.prepareStatement((String)list_sql.get(i));
				ps.executeUpdate();
				ps.close();
			}			
			trans.commit();
			HibernateSession.closeSession();
		}catch (Exception e1) {
			//System.out.println( e1.getMessage() );
			try {
				trans.rollback();
			} catch (Exception e2) {
				//throw new ApplicationException("事务回滚时失败...",e2);
				return "事务回滚时失败...";
			}
			try {
				HibernateSession.closeSession();
			} catch (OPException e) {
				//throw new ApplicationException("事务回滚关闭session时失败...",e);
				return "事务回滚关闭session时失败...";
			}
			//throw new ApplicationException("保存数据时失败...",e1);
			return "保存数据时失败...";
		}
		return "0";
	}

}
