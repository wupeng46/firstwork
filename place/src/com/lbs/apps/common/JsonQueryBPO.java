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
	LogHelper log = new LogHelper(this.getClass());   //日志处理对象
	
		
	
	public String GetRecordCountHql(String ls_sql){		
		int li_pos=ls_sql.toLowerCase().indexOf("from");
		if (li_pos>0){
			return "select count(*) "+ls_sql.substring(li_pos);
		}else{
		  return ""; 
		}
	}
	
	
	/*
	 * 根据sql语句生成查询总记录数的SQL语句
	 */
	public String GetRecordCountSql(String ls_sql){
		//以前的处理模式
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
	 * 根据SQL及页码生成分页SQL(sqlserver)
	 */
	public String GetPageSql(String ls_sql,int li_page,int li_pagesize,int li_recordcount){
		
		String ls_endsql=ls_sql.substring(6).trim().toLowerCase();
		int li_pos=ls_endsql.indexOf("from");
		String ls_fieldsql=ls_endsql.substring(0,li_pos).toLowerCase();
		String[] fieldArray;
		String sorttype="asc";
		String ls_sortSql="";                 //排序SQL
		li_pos=ls_endsql.indexOf("order by");
		if (li_pos>0){
			ls_sortSql=ls_endsql.substring(li_pos+8).trim();
			if (ls_sortSql.indexOf("desc")>0) sorttype="desc";   //降序
			if (ls_sortSql.indexOf(" ")>0) ls_sortSql=ls_sortSql.substring(0,ls_sortSql.indexOf(" "));
			fieldArray=ls_sortSql.split(",");                            //求排序字段(单字段排序)
			ls_endsql=ls_endsql.substring(0,li_pos).trim();              //去掉后面的order的排序 
		}else{		
			fieldArray=ls_fieldsql.split(",");                           //无排序字段默认为第一个字段(单字段排序)
		}
		String ls_order2=fieldArray[0].trim();  //去掉前面的别名和.    
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
	 * 根据SQL及页码生成分页SQL(sqlserver) 添加指定排序
	 */
	public String GetPageSqlOrderby(String ls_sql,int li_page,int li_pagesize,int li_recordcount,Map<String,String> map){
		
		String ls_endsql=ls_sql.substring(6).trim().toLowerCase();
		int li_pos=ls_endsql.indexOf("from");
		String ls_fieldsql=ls_endsql.substring(0,li_pos).toLowerCase();
		String[] fieldArray;
		String sorttype="asc";
		String ls_sortSql="";                 //排序SQL
		li_pos=ls_endsql.indexOf("order by");
		if (li_pos>0){
			ls_sortSql=ls_endsql.substring(li_pos+8).trim();
			if (ls_sortSql.indexOf("desc")>0) sorttype="desc";   //降序
			if (ls_sortSql.indexOf(" ")>0) ls_sortSql=ls_sortSql.substring(0,ls_sortSql.indexOf(" "));
			fieldArray=ls_sortSql.split(",");                            //求排序字段(单字段排序)
			ls_endsql=ls_endsql.substring(0,li_pos).trim();              //去掉后面的order的排序 
		}else{		
			fieldArray=ls_fieldsql.split(",");                           //无排序字段默认为第一个字段(单字段排序)
		}
		String ls_order2=fieldArray[0].trim();  //去掉前面的别名和.    
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
		//map 循环排序
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
			//去除最前面的逗号
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
	 * 查询数据
	 * 入参:hql,当前的页数,每页记录数
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryData(String Hql,String QueryField,int CurrPage,int PageRecord)throws ApplicationException{
		OPManager op=new OPManager();                     //数据库操作对象
		TransManager trans = new TransManager();          //事务处理对象
		String ls_countsql=GetRecordCountHql(Hql);   //生成获取总记录数的字符串
		int li_count=0;           //总记录数
		int li_currpage=CurrPage;  //当前页数
		int total_page=0;         //总页数
		String[] array_field=QueryField.split("\\|");     //获取查询字段,注意一定要与HQL语句中的顺序对应
		
		try {
			li_count=op.getCount(ls_countsql).intValue();
		} catch (OPException e) {
			throw new ApplicationException("获取总记录数时出错,原因:"+e.getMessage(),e);
		}
		
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //计算总页数		    
	  	}else{
	  		total_page=li_count/PageRecord;   //计算总页数
	  	}
		if (CurrPage>total_page){      //如果查看页数大于总页数则默认为最后一页
	  		li_currpage=total_page;
	  	}
		
		Collection co=null;
		try {
			co= op.query(Hql, li_currpage, PageRecord);
		} catch (OPException ex) {
			throw new ApplicationException("查询出错！", ex);
		}
		//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
		//StringBuffer json=new StringBuffer("{total:"+li_count+",start:"+Start+",limit:"+Limit+",root:[");		
		StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"成功\",\"total\":"+li_count+",\"root\":[");
		String tempValue="";
		if (co!=null){		
			Iterator it=co.iterator();
			while(it.hasNext()){
			//for (int i=0;i<list.size();i++){
				Object[] obj=(Object[])it.next();   //获取记录内容
				json.append("{");
				for(int j=0;j<array_field.length;j++){  //取内容并组合JSON数据  注意：尚未处理代码型数据
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
				json.delete(json.length()-1,json.length());  //去掉最后的,号
				json.append("},");
			}
			json.delete(json.length()-1,json.length());  //去掉最后的,号			
		}
		json.append("]}");
		return json.toString();
	}	
	
	
	/*
	 * 
	 * @author Administrator
	 * 查询数据(不分页)
	 * 入参:sql
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryDataBySqlForAll(String Sql,String convertcode)throws ApplicationException{
		String ls_countsql=GetRecordCountSql(Sql);   //生成获取总记录数的字符串
		int li_count=0;           //总记录数
				
		//SQL处理方式
		Session session = null; // 用来读取视图
		Connection conn = null; // 连接
		Statement stmt = null;
		try {
			session = HibernateSession.currentSession(); // 得到HIB连接
		} catch (OPException e) {
			e.printStackTrace();
		}
		try {
			conn = session.connection();
		} catch (HibernateException e1) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e7) {
				throw new ApplicationException("关闭数据库连接失败",e7);
			}
			throw new ApplicationException("建立数据库连接失败",e1);
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e2) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				throw new ApplicationException("关闭数据库连接失败",e1);
			}
			throw new ApplicationException("建立数据连接失败",e2);
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
			//测试出错数据库链接数
			try {
				HibernateSession.closeSession();				
			} catch (OPException e5) {
				throw new ApplicationException("关闭SESSION时出错...",e5);
			}
			throw new ApplicationException("查询时出错",e3);
		}			
		/*  oracle 语法
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //计算总页数
	  	}else{
	  		total_page=li_count/PageRecord;     //计算总页数
	  	} */
		//SQLSERVER语法
					
		String tempValue="";
		//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
		StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"成功\",\"total\":"+li_count+",\"root\":[");
		if (li_count>0){
			try {			
				resultset=stmt.executeQuery(Sql);				
			} catch (SQLException e3) {	
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错",e3);
			}
			try {
				if (resultset!=null){
					int cols=resultset.getMetaData().getColumnCount();  //获取列数
					while (resultset.next()){
						json.append("{");
						for(int j=0;j<cols;j++){  //取内容并组合JSON数据  注意：尚未处理代码型数据
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
								tempValue=tempValue.replaceAll("\"", "'");   //将双引号替换为单引号
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
						json.delete(json.length()-1,json.length());  //去掉最后的,号
						json.append("},");
					}
					json.delete(json.length()-1,json.length());  //去掉最后的,号		
					resultset.close();
					resultset=null;		
				}
				json.append("]}");
				stmt.close();
				stmt = null;
	            try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错...",e4);
			}
		}else{			
			try {
				stmt.close();
				stmt = null;
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错...",e4);
			}
			json.append("]}");			
		}
		return json.toString();
	}	
	
	
	/*
	 * 
	 * @author Administrator
	 * 查询数据
	 * 入参:sql,当前的页数,每页记录数
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryDataBySql(String Sql,String convertcode,int CurrPage,int PageRecord)throws ApplicationException{
		String ls_countsql=GetRecordCountSql(Sql);   //生成获取总记录数的字符串
		int li_count=0;           //总记录数
		int li_currpage=CurrPage;  //当前页数
		int total_page=0;         //总页数
				
		//SQL处理方式
		Session session = null; // 用来读取视图
		Connection conn = null; // 连接
		Statement stmt = null;
		try {
			session = HibernateSession.currentSession(); // 得到HIB连接
		} catch (OPException e) {
			e.printStackTrace();
		}
		try {
			conn = session.connection();
		} catch (HibernateException e1) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e7) {
				throw new ApplicationException("关闭数据库连接失败",e7);
			}
			throw new ApplicationException("建立数据库连接失败",e1);
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e2) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				throw new ApplicationException("关闭数据库连接失败",e1);
			}
			throw new ApplicationException("建立数据连接失败",e2);
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
			//测试出错数据库链接数
			try {
				HibernateSession.closeSession();				
			} catch (OPException e5) {
				throw new ApplicationException("关闭SESSION时出错...",e5);
			}
			throw new ApplicationException("查询出错sql:"+ls_countsql+",出错原因",e3);
		}			
		/*  oracle 语法
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //计算总页数
	  	}else{
	  		total_page=li_count/PageRecord;     //计算总页数
	  	} */
		//SQLSERVER语法
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord;   //计算总页数
	  	}else{
	  		total_page=li_count/PageRecord+1;     //计算总页数
	  	}
		
		if (CurrPage>total_page){      //如果查看页数大于总页数则默认为第一页
	  		li_currpage=total_page;
	  	}				
		String tempValue="";
		//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
		StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"成功\",\"total\":"+li_count+",\"root\":[");
		if (li_count>0){
			try {			
				resultset=stmt.executeQuery(GetPageSql(Sql,li_currpage,PageRecord,li_count));				
			} catch (SQLException e3) {	
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错",e3);
			}
			try {
				if (resultset!=null){
					int cols=resultset.getMetaData().getColumnCount();  //获取列数
					while (resultset.next()){
						json.append("{");
						//for(int j=0;j<array_field.length;j++){  //取内容并组合JSON数据  注意：尚未处理代码型数据
						for(int j=0;j<cols;j++){  //取内容并组合JSON数据  注意：尚未处理代码型数据
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
								tempValue=tempValue.replaceAll("\"", "'");   //将双引号替换为单引号
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
						json.delete(json.length()-1,json.length());  //去掉最后的,号
						json.append("},");
					}
					json.delete(json.length()-1,json.length());  //去掉最后的,号		
					resultset.close();
					resultset=null;		
				}
				json.append("]}");
				stmt.close();
				stmt = null;
	            try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错...",e4);
			}
		}else{			
			try {
				stmt.close();
				stmt = null;
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错...",e4);
			}
			json.append("]}");			
		}
		return json.toString();
	}	
	
	/*
	 * 
	 * @author Administrator
	 * 查询数据(一般用于查询单条记录展示详细)
	 * 入参:sql,当前的页数,每页记录数
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public String QueryDataBySql(String Sql,String QueryField,int CurrPage,int PageRecord,String callback)throws ApplicationException{
		String ls_countsql=GetRecordCountSql(Sql);   //生成获取总记录数的字符串
		int li_count=0;           //总记录数
		int li_currpage=CurrPage;  //当前页数
		int total_page=0;         //总页数
		String[] array_field=QueryField.split("\\|");     //获取查询字段,注意一定要与HQL语句中的顺序对应
				
		//SQL处理方式
		Session session = null; // 用来读取视图
		Connection conn = null; // 连接
		Statement stmt = null;
		try {
			session = HibernateSession.currentSession(); // 得到HIB连接
		} catch (OPException e) {
			e.printStackTrace();
		}
		try {
			conn = session.connection();
		} catch (HibernateException e1) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e7) {
				throw new ApplicationException("关闭数据库连接失败",e7);
			}
			throw new ApplicationException("建立数据库连接失败",e1);
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e2) {
			try {
				HibernateSession.closeSession();
			} catch (OPException e1) {
				throw new ApplicationException("关闭数据库连接失败",e1);
			}
			throw new ApplicationException("建立数据连接失败",e2);
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
			//测试出错数据库链接数
			try {
				HibernateSession.closeSession();				
			} catch (OPException e5) {
				throw new ApplicationException("关闭SESSION时出错...",e5);
			}
			throw new ApplicationException("查询时出错",e3);
		}			
		
		if (li_count % PageRecord==0){ 
		    total_page=li_count/PageRecord-1;   //计算总页数
	  	}else{
	  		total_page=li_count/PageRecord;     //计算总页数
	  	}                
		
		if (CurrPage>total_page){      //如果查看页数大于总页数则默认为第一页
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
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错",e3);
			}
			try {
				if (resultset!=null){
					while (resultset.next()){
						json.append("{");
						for(int j=0;j<array_field.length;j++){  //取内容并组合JSON数据  注意：尚未处理代码型数据	
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
						json.delete(json.length()-1,json.length());  //去掉最后的,号
						json.append("},");
					}
					json.delete(json.length()-1,json.length());  //去掉最后的,号		
					resultset.close();
					resultset=null;		
				}
				json.append(")");
				stmt.close();
				stmt = null;
	            try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错...",e4);
			}
		}else{			
			try {
				stmt.close();
				stmt = null;
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
			} catch (SQLException e4) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询时出错...",e4);
			}
			json.append("]}");			
		}
		return json.toString();
	}	
	
	//检查是否有子结点
	public boolean checkChild(String parentId,List SysgroupList)throws ApplicationException{
		boolean lb_have=false;
		if (SysgroupList!=null){
			for (int i=0;i<SysgroupList.size();i++){
				Sysgroup Sysgroup=(Sysgroup)SysgroupList.get(i);   //取机构信息
				if (Sysgroup.getParentid()!=null && Sysgroup.getParentid().toString().equals(parentId)){   //父结点下的子结点列表
					lb_have=true;
					break;
				}
			}
		}
		return lb_have;
	}
	
	/*
	
    //根据父级机构查询子机构并形成JSON数据
	public String QuerySysgroup(String parentId,HttpServletRequest request)throws ApplicationException{
		StringBuffer jason=new StringBuffer("[");
		boolean lb_haschild=false;
		List SysgroupList=(List)request.getSession().getAttribute(GlobalNames.SYSGROUP_LIST);
		if (SysgroupList!=null){
			for (int i=0;i<SysgroupList.size();i++){
				Sysgroup Sysgroup=(Sysgroup)SysgroupList.get(i);   //取机构信息
				if (Sysgroup.getParentid()!=null && Sysgroup.getParentid().toString().equals(parentId)){   //父结点下的子结点列表
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
				jason.delete(jason.length()-1,jason.length());  //去掉最后的,号
			}
		}		
		jason.append("]");
		return jason.toString();
	}
	
	//根据机构id查询机构编码
	public String GetOrgById(String jgid,HttpServletRequest request)throws ApplicationException{
		String ls_org="";
		List SysgroupList=(List)request.getSession().getAttribute(GlobalNames.SYSGROUP_LIST);
		if (SysgroupList!=null){
			for (int i=0;i<SysgroupList.size();i++){
				Sysgroup Sysgroup=(Sysgroup)SysgroupList.get(i);   //取机构信息
				if (Sysgroup.getGroupid().toString().equals(jgid)){   //父结点下的子结点列表
					ls_org=Sysgroup.getOrg();
					break;
				}
			}
		}		
		return ls_org;
	}	
	*/
	
	//根据HQL语句生成HQL字段列表
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
			//去掉别名的情况
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
		 * 查询数据
		 * 入参:sql,当前的页数,每页记录数
		 * TODO To change the template for this generated type comment go to
		 * Window - Preferences - Java - Code Style - Code Templates
		 */
		public String QueryDataBySqlOrderBy(String Sql,String convertcode,int CurrPage,int PageRecord,Map<String,String> map)throws ApplicationException{
			String ls_countsql=GetRecordCountSql(Sql);   //生成获取总记录数的字符串
			int li_count=0;           //总记录数
			int li_currpage=CurrPage;  //当前页数
			int total_page=0;         //总页数
					
			//SQL处理方式
			Session session = null; // 用来读取视图
			Connection conn = null; // 连接
			Statement stmt = null;
			try {
				session = HibernateSession.currentSession(); // 得到HIB连接
			} catch (OPException e) {
				e.printStackTrace();
			}
			try {
				conn = session.connection();
			} catch (HibernateException e1) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e7) {
					throw new ApplicationException("关闭数据库连接失败",e7);
				}
				throw new ApplicationException("建立数据库连接失败",e1);
			}
			try {
				stmt = conn.createStatement();
			} catch (SQLException e2) {
				try {
					HibernateSession.closeSession();
				} catch (OPException e1) {
					throw new ApplicationException("关闭数据库连接失败",e1);
				}
				throw new ApplicationException("建立数据连接失败",e2);
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
				//测试出错数据库链接数
				try {
					HibernateSession.closeSession();				
				} catch (OPException e5) {
					throw new ApplicationException("关闭SESSION时出错...",e5);
				}
				throw new ApplicationException("查询出错sql:"+ls_countsql+",出错原因",e3);
			}			
			/*  oracle 语法
			if (li_count % PageRecord==0){ 
			    total_page=li_count/PageRecord-1;   //计算总页数
		  	}else{
		  		total_page=li_count/PageRecord;     //计算总页数
		  	} */
			//SQLSERVER语法
			if (li_count % PageRecord==0){ 
			    total_page=li_count/PageRecord;   //计算总页数
		  	}else{
		  		total_page=li_count/PageRecord+1;     //计算总页数
		  	}
			
			if (CurrPage>total_page){      //如果查看页数大于总页数则默认为第一页
		  		li_currpage=total_page;
		  	}				
			String tempValue="";
			//StringBuffer json=new StringBuffer("{totalProperty:"+li_count+",root:[");
			StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"成功\",\"total\":"+li_count+",\"root\":[");
			if (li_count>0){
				try {			
					resultset=stmt.executeQuery(GetPageSqlOrderby(Sql,li_currpage,PageRecord,li_count,map));				
				} catch (SQLException e3) {	
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("关闭SESSION时出错...",e5);
					}
					throw new ApplicationException("查询时出错",e3);
				}
				try {
					if (resultset!=null){
						int cols=resultset.getMetaData().getColumnCount();  //获取列数
						while (resultset.next()){
							json.append("{");
							//for(int j=0;j<array_field.length;j++){  //取内容并组合JSON数据  注意：尚未处理代码型数据
							for(int j=0;j<cols;j++){  //取内容并组合JSON数据  注意：尚未处理代码型数据
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
									tempValue=tempValue.replaceAll("\"", "'");   //将双引号替换为单引号
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
							json.delete(json.length()-1,json.length());  //去掉最后的,号
							json.append("},");
						}
						json.delete(json.length()-1,json.length());  //去掉最后的,号		
						resultset.close();
						resultset=null;		
					}
					json.append("]}");
					stmt.close();
					stmt = null;
		            try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("关闭SESSION时出错...",e5);
					}
					
				} catch (SQLException e4) {
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("关闭SESSION时出错...",e5);
					}
					throw new ApplicationException("查询时出错...",e4);
				}
			}else{			
				try {
					stmt.close();
					stmt = null;
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("关闭SESSION时出错...",e5);
					}
				} catch (SQLException e4) {
					try {
						HibernateSession.closeSession();
					} catch (OPException e5) {
						throw new ApplicationException("关闭SESSION时出错...",e5);
					}
					throw new ApplicationException("查询时出错...",e4);
				}
				json.append("]}");			
			}
			return json.toString();
		}	
}
