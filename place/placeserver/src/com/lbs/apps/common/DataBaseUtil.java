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
	
	//ִ�е���SQL
	public String execSql(String ls_sql) throws ApplicationException{
		Session session = null; 
		Connection conn = null; 
		//Statement stmt = null;
		PreparedStatement ps = null;
		TransManager trans = new TransManager();
		try {
			trans.begin();
			session = HibernateSession.currentSession(); // �õ�HIB����
			conn = session.connection();
		} catch (OPException e) {
			//throw new ApplicationException("�������ݿ�����ʧ��",e);
			return "�������ݿ�����ʧ��...";
		}catch (HibernateException e) {				
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				//throw new ApplicationException("�ر����ݿ�����ʧ��",e1);
				return "�ر����ݿ�����ʧ��...";
			}
			//throw new ApplicationException("�������ݿ�����ʧ��",e);
			return "�������ݿ�����ʧ��...";
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
				//throw new ApplicationException("����ع�ʱʧ��...",e2);
				return "����ع�ʱʧ��...";
			}
			try {
				HibernateSession.closeSession();
			} catch (OPException e) {
				//throw new ApplicationException("����ع��ر�sessionʱʧ��...",e);
				return "����ع��ر�sessionʱʧ��...";
			}
			//throw new ApplicationException("��������ʱʧ��...",e1);
			return "��������ʱʧ��...";
		}
		return "0";
	}
	
	//ִ�ж���SQL
	public String execBatchSql(List list_sql) throws ApplicationException{
		Session session = null; 
		Connection conn = null; 
		//Statement stmt = null;
		PreparedStatement ps = null;
		TransManager trans = new TransManager();
		try {
			trans.begin();
			session = HibernateSession.currentSession(); // �õ�HIB����
			conn = session.connection();
		} catch (OPException e) {
			//throw new ApplicationException("�������ݿ�����ʧ��",e);
			return "�������ݿ�����ʧ��...";
		}catch (HibernateException e) {				
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				//throw new ApplicationException("�ر����ݿ�����ʧ��",e1);
				return "�ر����ݿ�����ʧ��...";
			}
			//throw new ApplicationException("�������ݿ�����ʧ��",e);
			return "�������ݿ�����ʧ��...";
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
				//throw new ApplicationException("����ع�ʱʧ��...",e2);
				return "����ع�ʱʧ��...";
			}
			try {
				HibernateSession.closeSession();
			} catch (OPException e) {
				//throw new ApplicationException("����ع��ر�sessionʱʧ��...",e);
				return "����ع��ر�sessionʱʧ��...";
			}
			//throw new ApplicationException("��������ʱʧ��...",e1);
			return "��������ʱʧ��...";
		}
		return "0";
	}

}
