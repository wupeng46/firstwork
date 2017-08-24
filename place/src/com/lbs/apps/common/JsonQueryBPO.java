/*
 * Created on 2009-8-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.lbs.apps.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.lbs.apps.system.po.Sysgroup;
import com.lbs.commons.GlobalNames;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.HibernateSession;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JsonQueryBPO {
	LogHelper log = new LogHelper(this.getClass());   //��־�������
	
		
	
	public String GetRecordCountHql(String ls_sql){		
		int li_pos=ls_sql.toLowerCase().indexOf("from");
		if (li_pos>0){
			return "select count(*) "+ls_sql.substring(li_pos);
		}else{
		  return ""; 
		}
	}
	
	
	/*
	 * ����sql������ɲ�ѯ�ܼ�¼����SQL���
	 */
	public String GetRecordCountSql(String ls_sql){
		//��ǰ�Ĵ���ģʽ
		/*
		int li_pos=ls_sql.toLowerCase().indexOf("from");
		if (li_pos>0){
			return "select count(*) "+ls_sql.substring(li_pos);
		}else{
		  return ""; 
		}
		*/
		if (ls_sql.indexOf("order by")>0){
			ls_sql=ls_sql.substring(0,ls_sql.indexOf("order by"));
		}
		return "select count(*) from ("+ls_sql+") temptable";
	}
	
	
	/*
	 * ����SQL��ҳ�����ɷ�ҳSQL(sqlserver)
	 */
	public String GetPageSql(String ls_sql,int li_page,int li_pagesize,int li_recordcount){
		
		String ls_endsql=ls_sql.substring(6).trim().toLowerCase();
		int li_pos=ls_endsql.indexOf("from");
		String ls_fieldsql=ls_endsql.substring(0,li_pos).toLowerCase();
		String[] fieldArray;
		String sorttype="asc";
		String ls_sortSql="";                 //����SQL
		li_pos=ls_endsql.indexOf("order by");
		if (li_pos>0){
			ls_sortSql=ls_endsql.substring(li_pos+8).trim();
			if (ls_sortSql.indexOf("desc")>0) sorttype="desc";   //����
			if (ls_sortSql.indexOf(" ")>0) ls_sortSql=ls_sortSql.substring(0,ls_sortSql.indexOf(" "));
			fieldArray=ls_sortSql.split(",");                            //�������ֶ�(���ֶ�����)
			ls_endsql=ls_endsql.substring(0,li_pos).trim();              //ȥ�������order������ 
		}else{		
			fieldArray=ls_fieldsql.split(",");                           //�������ֶ�Ĭ��Ϊ��һ���ֶ�(���ֶ�����)
		}
		String ls_order2=fieldArray[0].trim();  //ȥ��ǰ��ı�����.    
		li_pos=ls_order2.indexOf(".");
		if (li_pos>0){
			ls_order2=ls_order2.substring(li_pos+1);
		}		
		String ls_result="";
		if (li_pagesize*li_page>li_recordcount){
			ls_result="select * from (   select TOP "+(li_recordcount-(li_page-1)*li_pagesize) +" * FROM ( SELECT TOP "+li_pagesize*li_page;
		}else{
			ls_result="select * from (   select TOP "+li_pagesize +" * FROM ( SELECT TOP "+li_pagesize*li_page;
		}
		String Rownum_sql="ROW_NUMBER() over(order by "+fieldArray[0].trim()+") as rows,";
		if (sorttype.toLowerCase().equals("asc")){
			ls_result=ls_result+" "+Rownum_sql+ls_endsql+"  ORDER BY "+fieldArray[0].trim()+" ASC ) as aSysTable   ORDER BY rows DESC ) as bSysTable   ORDER BY rows ASC";
		}else{
			ls_result=ls_result+" "+Rownum_sql+ls_endsql+"  ORDER BY "+fieldArray[0].trim()+" DESC ) as aSysTable   ORDER BY rows ASC ) as bSysTable   ORDER BY rows DESC";	
		}
		
		return ls_result;
	}	
	/*
	 * ����SQL��ҳ�����ɷ�ҳSQL(sqlserver) ���ָ������
	 */
	public String GetPageSqlOrderby(String ls_sql,int li_page,int li_pagesize,int li_recordcount,Map<String,String> map){
		
		String ls_endsql=ls_sql.substring(6).trim().toLowerCase();
		int li_pos=ls_endsql.indexOf("from");
		String ls_fieldsql=ls_endsql.substring(0,li_pos).toLowerCase();
		String[] fieldArray;
		String sorttype="asc";
		String ls_sortSql="";                 //����SQL
		li_pos=ls_endsql.indexOf("order by");
		if (li_pos>0){
			ls_sortSql=ls_endsql.substring(li_pos+8).trim();
			if (ls_sortSql.indexOf("desc")>0) sorttype="desc";   //����
			if (ls_sortSql.indexOf(" ")>0) ls_sortSql=ls_sortSql.substring(0,ls_sortSql.indexOf(" "));
			fieldArray=ls_sortSql.split(",");                            //�������ֶ�(���ֶ�����)
			ls_endsql=ls_endsql.substring(0,li_pos).trim();              //ȥ�������order������ 
		}else{		
			fieldArray=ls_fieldsql.split(",");                           //�������ֶ�Ĭ��Ϊ��һ���ֶ�(���ֶ�����)
		}
		String ls_order2=fieldArray[0].trim();  //ȥ��ǰ��ı�����.    
		li_pos=ls_order2.indexOf(".");
		if (li_pos>0){
			ls_order2=ls_order2.substring(li_pos+1);
		}		
		String ls_result="";
		if (li_pagesize*li_page>li_recordcount){
			ls_result="select * from (   select TOP "+(li_recordcount-(li_page-1)*li_pagesize) +" * FROM ( SELECT TOP "+li_pagesize*li_page;
		}else{
			ls_result="select * from (   select TOP "+li_pagesize +" * FROM ( SELECT TOP "+li_pagesize*li_page;
		}
		//map ѭ������
		String old_temp="";
		String new_temp="";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			old_temp = old_temp+","+entry.getKey()+" "+entry.getValue();
			if(entry.getValue().toLowerCase().equals("asc")){
				new_temp = new_temp+","+entry.getKey()+" "+"desc";
			}else if(entry.getValue().toLowerCase().equals("desc")){
				new_temp = new_temp+","+entry.getKey()+" "+"asc";
			}
		}
		if(!(StringUtils.isBlank(old_temp) || StringUtils.isBlank(new_temp))){
			old_temp = old_temp.substring(1);
			new_temp = new_temp.substring(1);
			//ȥ����ǰ��Ķ���
			ls_result=ls_result+" "+ls_endsql+"  ORDER BY "+old_temp+" ) as aSysTable   ORDER BY "+new_temp+" ) as bSysTable   ORDER BY "+old_temp;
			
		}else{
			ls_result=ls_result+" "+ls_endsql+"  ORDER BY "+fieldArray[0].trim()+" ASC ) as aSysTable   ORDER BY "+ls_order2+" DESC ) as bSysTable   ORDER BY "+ls_order2+" ASC";
		}
		//log.info(ls_result);
		return ls_result;
	}	
	
	/*
	 * 
	 * @author Administrator
	 * ��ѯ����
	 * ���:hql,��ǰ��ҳ��,ÿҳ��¼��
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryData(String Hql,String QueryField,int CurrPage,int PageRecord)throws ApplicationException{
		OPManager op=new OPManager();                     //���ݿ��������
		TransManager trans = new TransManager();          //���������
		String ls_countsql=GetRecordCountHql(Hql);   //���ɻ�ȡ�ܼ�¼�����ַ���
		int li_count=0;           //�ܼ�¼��
		int li_currpage=CurrPage;  //��ǰҳ��
		int total_page=0;         //��ҳ��
		String[] array_field=QueryField.split("\\|");     //��ȡ��ѯ�ֶ�,ע��һ��Ҫ��HQL����е�˳���Ӧ
		
		try {
			li_count=op.getCount(ls_countsql).intValue();
		} catch (OPException e) {
			throw new ApplicationException("��ȡ�ܼ�¼��ʱ����,ԭ��:"+e.getMessage(),e);
		}
		
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //������ҳ��		    
	  	}else{
	  		total_page=li_count/PageRecord;   //������ҳ��
	  	}
		if (CurrPage>total_page){      //����鿴ҳ��������ҳ����Ĭ��Ϊ���һҳ
	  		li_currpage=total_page;
	  	}
		
		Collection co=null;
		try {
			co= op.query(Hql, li_currpage, PageRecord);
		} catch (OPException ex) {
			throw new ApplicationException("��ѯ����", ex);
		}
		//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
		//StringBuffer json=new StringBuffer("{total:"+li_count+",start:"+Start+",limit:"+Limit+",root:[");		
		StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"�ɹ�\",\"total\":"+li_count+",\"root\":[");
		String tempValue="";
		if (co!=null){		
			Iterator it=co.iterator();
			while(it.hasNext()){
			//for (int i=0;i<list.size();i++){
				Object[] obj=(Object[])it.next();   //��ȡ��¼����
				json.append("{");
				for(int j=0;j<array_field.length;j++){  //ȡ���ݲ����JSON����  ע�⣺��δ�������������
					//TreeMap hm = (TreeMap) servlet.getServletContext().getAttribute(array_field[j].toUpperCase());
					TreeMap hm = (TreeMap) ServletActionContext.getServletContext().getAttribute(array_field[j].toUpperCase());					
					if (obj[j]==null || obj[j].toString().equals("") || obj[j].toString().equals("null")){
						tempValue="";
					}else{
						tempValue=obj[j].toString();
					}
					if (hm==null || tempValue.equals("")){
						json.append(array_field[j]).append(":'").append(tempValue).append("',");
					}else{
						json.append(array_field[j]).append(":'").append(hm.get(tempValue)).append("',");
					}
				}
				json.delete(json.length()-1,json.length());  //ȥ������,��
				json.append("},");
			}
			json.delete(json.length()-1,json.length());  //ȥ������,��			
		}
		json.append("]}");
		return json.toString();
	}	
	
	
	/*
	 * 
	 * @author Administrator
	 * ��ѯ����(����ҳ)
	 * ���:sql
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryDataBySqlForAll(String Sql,String convertcode)throws ApplicationException{
		String ls_countsql=GetRecordCountSql(Sql);   //���ɻ�ȡ�ܼ�¼�����ַ���
		int li_count=0;           //�ܼ�¼��
				
		//SQL����ʽ
		Session session = null; // ������ȡ��ͼ
		Connection conn = null; // ����
		Statement stmt = null;
		try {
			session = HibernateSession.currentSession(); // �õ�HIB����
		} catch (OPException e) {
			e.printStackTrace();
		}
		try {
			conn = session.connection();
		} catch (HibernateException e1) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e7) {
				throw new ApplicationException("�ر����ݿ�����ʧ��",e7);
			}
			throw new ApplicationException("�������ݿ�����ʧ��",e1);
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e2) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				throw new ApplicationException("�ر����ݿ�����ʧ��",e1);
			}
			throw new ApplicationException("������������ʧ��",e2);
		}
		ResultSet resultset=null;
		try {			
			resultset=stmt.executeQuery(ls_countsql);	
			if (resultset!=null){
				resultset.next();
				li_count=resultset.getInt(1);
				resultset.close();
				resultset=null;
			}
		} catch (SQLException e3) {	
			//���Գ������ݿ�������
			try {
				HibernateSession.closeSession();				
			} catch (OPException e5) {
				throw new ApplicationException("�ر�SESSIONʱ����...",e5);
			}
			throw new ApplicationException("��ѯʱ����",e3);
		}			
		/*  oracle �﷨
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //������ҳ��
	  	}else{
	  		total_page=li_count/PageRecord;     //������ҳ��
	  	} */
		//SQLSERVER�﷨
					
		String tempValue="";
		//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
		StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"�ɹ�\",\"total\":"+li_count+",\"root\":[");
		if (li_count>0){
			try {			
				resultset=stmt.executeQuery(Sql);				
			} catch (SQLException e3) {	
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����",e3);
			}
			try {
				if (resultset!=null){
					int cols=resultset.getMetaData().getColumnCount();  //��ȡ����
					while (resultset.next()){
						json.append("{");
						for(int j=0;j<cols;j++){  //ȡ���ݲ����JSON����  ע�⣺��δ�������������
							String fieldName=resultset.getMetaData().getColumnName(j+1).toLowerCase();
							if (fieldName.equals("rownum_")){
								continue;
							}
							TreeMap hm =null;
							if (convertcode==null || convertcode.toLowerCase().equals("true")){
								hm=(TreeMap) ServletActionContext.getServletContext().getAttribute(fieldName.toUpperCase());
							}
						    tempValue=resultset.getString(fieldName);
							if (tempValue==null || tempValue.equals("") || tempValue.equals("null")){
								tempValue="";
							}
							if (hm==null || tempValue.equals("")){
								tempValue=tempValue.replaceAll("\"", "'");   //��˫�����滻Ϊ������
								json.append("\""+fieldName+"\"").append(":\"").append(tempValue).append("\",");
							}else{
								String ls_codeValue=(String)hm.get(tempValue);
								if (ls_codeValue==null){
									json.append("\""+fieldName+"\"").append(":\"").append(tempValue).append("\",");
								}else{
									json.append("\""+fieldName+"\"").append(":\"").append(ls_codeValue).append("\",");
								}
							}
						}
						json.delete(json.length()-1,json.length());  //ȥ������,��
						json.append("},");
					}
					json.delete(json.length()-1,json.length());  //ȥ������,��		
					resultset.close();
					resultset=null;		
				}
				json.append("]}");
				stmt.close();
				stmt = null;
	            try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����...",e4);
			}
		}else{			
			try {
				stmt.close();
				stmt = null;
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����...",e4);
			}
			json.append("]}");			
		}
		return json.toString();
	}	
	
	
	/*
	 * 
	 * @author Administrator
	 * ��ѯ����
	 * ���:sql,��ǰ��ҳ��,ÿҳ��¼��
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryDataBySql(String Sql,String convertcode,int CurrPage,int PageRecord)throws ApplicationException{
		String ls_countsql=GetRecordCountSql(Sql);   //���ɻ�ȡ�ܼ�¼�����ַ���
		int li_count=0;           //�ܼ�¼��
		int li_currpage=CurrPage;  //��ǰҳ��
		int total_page=0;         //��ҳ��
				
		//SQL����ʽ
		Session session = null; // ������ȡ��ͼ
		Connection conn = null; // ����
		Statement stmt = null;
		try {
			session = HibernateSession.currentSession(); // �õ�HIB����
		} catch (OPException e) {
			e.printStackTrace();
		}
		try {
			conn = session.connection();
		} catch (HibernateException e1) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e7) {
				throw new ApplicationException("�ر����ݿ�����ʧ��",e7);
			}
			throw new ApplicationException("�������ݿ�����ʧ��",e1);
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e2) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				throw new ApplicationException("�ر����ݿ�����ʧ��",e1);
			}
			throw new ApplicationException("������������ʧ��",e2);
		}
		ResultSet resultset=null;
		try {			
			resultset=stmt.executeQuery(ls_countsql);	
			if (resultset!=null){
				resultset.next();
				li_count=resultset.getInt(1);
				resultset.close();
				resultset=null;
			}
		} catch (SQLException e3) {	
			//���Գ������ݿ�������
			try {
				HibernateSession.closeSession();				
			} catch (OPException e5) {
				throw new ApplicationException("�ر�SESSIONʱ����...",e5);
			}
			throw new ApplicationException("��ѯ����sql:"+ls_countsql+",����ԭ��",e3);
		}			
		/*  oracle �﷨
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //������ҳ��
	  	}else{
	  		total_page=li_count/PageRecord;     //������ҳ��
	  	} */
		//SQLSERVER�﷨
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord;   //������ҳ��
	  	}else{
	  		total_page=li_count/PageRecord+1;     //������ҳ��
	  	}
		
		if (CurrPage>total_page){      //����鿴ҳ��������ҳ����Ĭ��Ϊ��һҳ
	  		li_currpage=total_page;
	  	}				
		String tempValue="";
		//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
		StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"�ɹ�\",\"total\":"+li_count+",\"root\":[");
		if (li_count>0){
			try {			
				resultset=stmt.executeQuery(GetPageSql(Sql,li_currpage,PageRecord,li_count));				
			} catch (SQLException e3) {	
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����",e3);
			}
			try {
				if (resultset!=null){
					int cols=resultset.getMetaData().getColumnCount();  //��ȡ����
					while (resultset.next()){
						json.append("{");
						//for(int j=0;j<array_field.length;j++){  //ȡ���ݲ����JSON����  ע�⣺��δ�������������
						for(int j=0;j<cols;j++){  //ȡ���ݲ����JSON����  ע�⣺��δ�������������
							String fieldName=resultset.getMetaData().getColumnName(j+1).toLowerCase();
							if (fieldName.equals("rownum_")){
								continue;
							}
							TreeMap hm =null;
							if (convertcode==null || convertcode.toLowerCase().equals("true")){
								hm=(TreeMap) ServletActionContext.getServletContext().getAttribute(fieldName.toUpperCase());
							}
						    tempValue=resultset.getString(fieldName);
							if (tempValue==null || tempValue.equals("") || tempValue.equals("null")){
								tempValue="";
							}
							if (hm==null || tempValue.equals("")){
								tempValue=tempValue.replaceAll("\"", "'");   //��˫�����滻Ϊ������
								json.append("\""+fieldName+"\"").append(":\"").append(tempValue).append("\",");
							}else{
								String ls_codeValue=(String)hm.get(tempValue);
								if (ls_codeValue==null){
									json.append("\""+fieldName+"\"").append(":\"").append(tempValue).append("\",");
								}else{
									json.append("\""+fieldName+"\"").append(":\"").append(ls_codeValue).append("\",");
								}
							}
						}
						json.delete(json.length()-1,json.length());  //ȥ������,��
						json.append("},");
					}
					json.delete(json.length()-1,json.length());  //ȥ������,��		
					resultset.close();
					resultset=null;		
				}
				json.append("]}");
				stmt.close();
				stmt = null;
	            try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����...",e4);
			}
		}else{			
			try {
				stmt.close();
				stmt = null;
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����...",e4);
			}
			json.append("]}");			
		}
		return json.toString();
	}	
	
	/*
	 * 
	 * @author Administrator
	 * ��ѯ����(һ�����ڲ�ѯ������¼չʾ��ϸ)
	 * ���:sql,��ǰ��ҳ��,ÿҳ��¼��
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryDataBySql(String Sql,String QueryField,int CurrPage,int PageRecord,String callback)throws ApplicationException{
		String ls_countsql=GetRecordCountSql(Sql);   //���ɻ�ȡ�ܼ�¼�����ַ���
		int li_count=0;           //�ܼ�¼��
		int li_currpage=CurrPage;  //��ǰҳ��
		int total_page=0;         //��ҳ��
		String[] array_field=QueryField.split("\\|");     //��ȡ��ѯ�ֶ�,ע��һ��Ҫ��HQL����е�˳���Ӧ
				
		//SQL����ʽ
		Session session = null; // ������ȡ��ͼ
		Connection conn = null; // ����
		Statement stmt = null;
		try {
			session = HibernateSession.currentSession(); // �õ�HIB����
		} catch (OPException e) {
			e.printStackTrace();
		}
		try {
			conn = session.connection();
		} catch (HibernateException e1) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e7) {
				throw new ApplicationException("�ر����ݿ�����ʧ��",e7);
			}
			throw new ApplicationException("�������ݿ�����ʧ��",e1);
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e2) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				throw new ApplicationException("�ر����ݿ�����ʧ��",e1);
			}
			throw new ApplicationException("������������ʧ��",e2);
		}
		ResultSet resultset=null;
		try {			
			resultset=stmt.executeQuery(ls_countsql);	
			if (resultset!=null){
				resultset.next();
				li_count=resultset.getInt(1);
				resultset.close();
				resultset=null;
			}
		} catch (SQLException e3) {	
			//���Գ������ݿ�������
			try {
				HibernateSession.closeSession();				
			} catch (OPException e5) {
				throw new ApplicationException("�ر�SESSIONʱ����...",e5);
			}
			throw new ApplicationException("��ѯʱ����",e3);
		}			
		
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //������ҳ��
	  	}else{
	  		total_page=li_count/PageRecord;     //������ҳ��
	  	}                
		
		if (CurrPage>total_page){      //����鿴ҳ��������ҳ����Ĭ��Ϊ��һҳ
	  		li_currpage=total_page;
	  	}				
		String tempValue="";
		//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
		StringBuffer json=new StringBuffer(callback+"(");
		if (li_count>0){
			try {			
				resultset=stmt.executeQuery(GetPageSql(Sql,li_currpage,PageRecord,li_count));				
			} catch (SQLException e3) {	
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����",e3);
			}
			try {
				if (resultset!=null){
					while (resultset.next()){
						json.append("{");
						for(int j=0;j<array_field.length;j++){  //ȡ���ݲ����JSON����  ע�⣺��δ�������������	
							//TreeMap hm = (TreeMap) servlet.getServletContext().getAttribute(array_field[j].toUpperCase());
							TreeMap hm = (TreeMap) ServletActionContext.getServletContext().getAttribute(array_field[j].toUpperCase());
						    tempValue=resultset.getString(array_field[j]);
							if (tempValue==null || tempValue.equals("") || tempValue.equals("null")){
								tempValue="";
							}
							if (hm==null || tempValue.equals("")){
								json.append(array_field[j]).append(":'").append(tempValue).append("',");
							}else{
								String ls_codeValue=(String)hm.get(tempValue);
								if (ls_codeValue==null){
									json.append(array_field[j]).append(":'").append(tempValue).append("',");
								}else{
									json.append(array_field[j]).append(":'").append(ls_codeValue).append("',");
								}
							}
						}
						json.delete(json.length()-1,json.length());  //ȥ������,��
						json.append("},");
					}
					json.delete(json.length()-1,json.length());  //ȥ������,��		
					resultset.close();
					resultset=null;		
				}
				json.append(")");
				stmt.close();
				stmt = null;
	            try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����...",e4);
			}
		}else{			
			try {
				stmt.close();
				stmt = null;
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯʱ����...",e4);
			}
			json.append("]}");			
		}
		return json.toString();
	}	
	
	//����Ƿ����ӽ��
	public boolean checkChild(String parentId,List SysgroupList)throws ApplicationException{
		boolean lb_have=false;
		if (SysgroupList!=null){
			for (int i=0;i<SysgroupList.size();i++){
				Sysgroup Sysgroup=(Sysgroup)SysgroupList.get(i);   //ȡ������Ϣ
				if (Sysgroup.getParentid()!=null && Sysgroup.getParentid().toString().equals(parentId)){   //������µ��ӽ���б�
					lb_have=true;
					break;
				}
			}
		}
		return lb_have;
	}
	
	/*
	
    //���ݸ���������ѯ�ӻ������γ�JSON����
	public String QuerySysgroup(String parentId,HttpServletRequest request)throws ApplicationException{
		StringBuffer jason=new StringBuffer("[");
		boolean lb_haschild=false;
		List SysgroupList=(List)request.getSession().getAttribute(GlobalNames.SYSGROUP_LIST);
		if (SysgroupList!=null){
			for (int i=0;i<SysgroupList.size();i++){
				Sysgroup Sysgroup=(Sysgroup)SysgroupList.get(i);   //ȡ������Ϣ
				if (Sysgroup.getParentid()!=null && Sysgroup.getParentid().toString().equals(parentId)){   //������µ��ӽ���б�
					lb_haschild=true;
				    jason.append("{id:'"+Sysgroup.getGroupid()+"',text:'"+Sysgroup.getName()+"',");					
				    if(checkChild(Sysgroup.getGroupid().toString(),SysgroupList)){ 
				   	    jason.append("cls:'folder',leaf:"+false+"},"); 
				    }else{ 
				    	jason.append("cls:'file',leaf:"+true+"},"); 
				    }					
				}
			}
			if (lb_haschild){
				jason.delete(jason.length()-1,jason.length());  //ȥ������,��
			}
		}		
		jason.append("]");
		return jason.toString();
	}
	
	//���ݻ���id��ѯ��������
	public String GetOrgById(String jgid,HttpServletRequest request)throws ApplicationException{
		String ls_org="";
		List SysgroupList=(List)request.getSession().getAttribute(GlobalNames.SYSGROUP_LIST);
		if (SysgroupList!=null){
			for (int i=0;i<SysgroupList.size();i++){
				Sysgroup Sysgroup=(Sysgroup)SysgroupList.get(i);   //ȡ������Ϣ
				if (Sysgroup.getGroupid().toString().equals(jgid)){   //������µ��ӽ���б�
					ls_org=Sysgroup.getOrg();
					break;
				}
			}
		}		
		return ls_org;
	}	
	*/
	
	//����HQL�������HQL�ֶ��б�
	 public String GetFieldList(String hqlSource){
	 	String hql=hqlSource.toLowerCase();
	 	String ls_result="";
		hql=hql.substring(6);
		int pos=hql.indexOf("from");
		hql=hql.substring(0,pos).trim();
		String[] fieldArray=hql.split(",");
		StringBuffer sb=new StringBuffer("");
		for (int i=0;i<fieldArray.length;i++){
			String temp=temp=fieldArray[i];
			//ȥ�����������
			if (temp.indexOf(" ")>0){
				temp=temp.substring(temp.indexOf(" ")+1);
			}
			while (temp.indexOf(".")>0){
				temp=temp.substring(temp.indexOf(".")+1);
			}
			//sb.append(temp.substring(temp.indexOf(".")+1).trim()).append("|");
			sb.append(temp.trim()).append("|");
		}
		sb.deleteCharAt(sb.length()-1);
		ls_result=sb.toString();
		return ls_result;
	 }
	
	 /*
		 * 
		 * @author Administrator
		 * ��ѯ����
		 * ���:sql,��ǰ��ҳ��,ÿҳ��¼��
		 * TODO To change the template for this generated type comment go to
		 * Window - Preferences - Java - Code Style - Code Templates
		 */
		public String QueryDataBySqlOrderBy(String Sql,String convertcode,int CurrPage,int PageRecord,Map<String,String> map)throws ApplicationException{
			String ls_countsql=GetRecordCountSql(Sql);   //���ɻ�ȡ�ܼ�¼�����ַ���
			int li_count=0;           //�ܼ�¼��
			int li_currpage=CurrPage;  //��ǰҳ��
			int total_page=0;         //��ҳ��
					
			//SQL����ʽ
			Session session = null; // ������ȡ��ͼ
			Connection conn = null; // ����
			Statement stmt = null;
			try {
				session = HibernateSession.currentSession(); // �õ�HIB����
			} catch (OPException e) {
				e.printStackTrace();
			}
			try {
				conn = session.connection();
			} catch (HibernateException e1) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e7) {
					throw new ApplicationException("�ر����ݿ�����ʧ��",e7);
				}
				throw new ApplicationException("�������ݿ�����ʧ��",e1);
			}
			try {
				stmt = conn.createStatement();
			} catch (SQLException e2) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e1) {
					throw new ApplicationException("�ر����ݿ�����ʧ��",e1);
				}
				throw new ApplicationException("������������ʧ��",e2);
			}
			ResultSet resultset=null;
			try {			
				resultset=stmt.executeQuery(ls_countsql);	
				if (resultset!=null){
					resultset.next();
					li_count=resultset.getInt(1);
					resultset.close();
					resultset=null;
				}
			} catch (SQLException e3) {	
				//���Գ������ݿ�������
				try {
					HibernateSession.closeSession();				
				} catch (OPException e5) {
					throw new ApplicationException("�ر�SESSIONʱ����...",e5);
				}
				throw new ApplicationException("��ѯ����sql:"+ls_countsql+",����ԭ��",e3);
			}			
			/*  oracle �﷨
			if (li_count % PageRecord==0){ 
			    total_page=li_count/PageRecord-1;   //������ҳ��
		  	}else{
		  		total_page=li_count/PageRecord;     //������ҳ��
		  	} */
			//SQLSERVER�﷨
			if (li_count % PageRecord==0){ 
			    total_page=li_count/PageRecord;   //������ҳ��
		  	}else{
		  		total_page=li_count/PageRecord+1;     //������ҳ��
		  	}
			
			if (CurrPage>total_page){      //����鿴ҳ��������ҳ����Ĭ��Ϊ��һҳ
		  		li_currpage=total_page;
		  	}				
			String tempValue="";
			//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
			StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"�ɹ�\",\"total\":"+li_count+",\"root\":[");
			if (li_count>0){
				try {			
					resultset=stmt.executeQuery(GetPageSqlOrderby(Sql,li_currpage,PageRecord,li_count,map));				
				} catch (SQLException e3) {	
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("�ر�SESSIONʱ����...",e5);
					}
					throw new ApplicationException("��ѯʱ����",e3);
				}
				try {
					if (resultset!=null){
						int cols=resultset.getMetaData().getColumnCount();  //��ȡ����
						while (resultset.next()){
							json.append("{");
							//for(int j=0;j<array_field.length;j++){  //ȡ���ݲ����JSON����  ע�⣺��δ�������������
							for(int j=0;j<cols;j++){  //ȡ���ݲ����JSON����  ע�⣺��δ�������������
								String fieldName=resultset.getMetaData().getColumnName(j+1).toLowerCase();
								if (fieldName.equals("rownum_")){
									continue;
								}
								TreeMap hm =null;
								if (convertcode==null || convertcode.toLowerCase().equals("true")){
									hm=(TreeMap) ServletActionContext.getServletContext().getAttribute(fieldName.toUpperCase());
								}
							    tempValue=resultset.getString(fieldName);
								if (tempValue==null || tempValue.equals("") || tempValue.equals("null")){
									tempValue="";
								}
								if (hm==null || tempValue.equals("")){
									tempValue=tempValue.replaceAll("\"", "'");   //��˫�����滻Ϊ������
									json.append("\""+fieldName+"\"").append(":\"").append(tempValue).append("\",");
								}else{
									String ls_codeValue=(String)hm.get(tempValue);
									if (ls_codeValue==null){
										json.append("\""+fieldName+"\"").append(":\"").append(tempValue).append("\",");
									}else{
										json.append("\""+fieldName+"\"").append(":\"").append(ls_codeValue).append("\",");
									}
								}
							}
							json.delete(json.length()-1,json.length());  //ȥ������,��
							json.append("},");
						}
						json.delete(json.length()-1,json.length());  //ȥ������,��		
						resultset.close();
						resultset=null;		
					}
					json.append("]}");
					stmt.close();
					stmt = null;
		            try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("�ر�SESSIONʱ����...",e5);
					}
					
				} catch (SQLException e4) {
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("�ر�SESSIONʱ����...",e5);
					}
					throw new ApplicationException("��ѯʱ����...",e4);
				}
			}else{			
				try {
					stmt.close();
					stmt = null;
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("�ر�SESSIONʱ����...",e5);
					}
				} catch (SQLException e4) {
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("�ر�SESSIONʱ����...",e5);
					}
					throw new ApplicationException("��ѯʱ����...",e4);
				}
				json.append("]}");			
			}
			return json.toString();
		}	
}
