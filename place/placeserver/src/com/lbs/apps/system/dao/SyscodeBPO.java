package com.lbs.apps.system.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.system.po.Syscode;
import com.lbs.apps.system.po.SyscodeId;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class SyscodeBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	//判断查询条件是否OK，成功返回0，否则返回异常原因
		public String CheckSql_Where(String msgdata){
			String ls_return="0";
			if (msgdata==null || msgdata.equals("")){  //没有传递参数
				ls_return="请求参数异常";			
			}else{					 
				try{
					JSONObject obj = JSONObject.fromObject(msgdata);   //解析JSON数据包				
					//if (!obj.has("roleid")){					
					//	ls_return="数据包中未包含roleid参数信息";									
					//}
				}catch(Exception e){
					ls_return="msgdata数据字符串非法";						
				}			
			}	
			return ls_return;		
		}
		
		/**
		 * 查询系统参数列表
		 * @param msgdata
		 *@param domainid	系统代码
          @param domaincode	 系统代码值
          @param domainname	 代码值含义
         * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchSyscode(String msgdata)throws ApplicationException {
			SyscodeDTO dto=new SyscodeDTO();
			ClassHelper.copyProperties(msgdata, dto);				
			
			StringBuffer sb=new StringBuffer("select domainid,domaincode,domainname,isvalid,parameter1,parameter2,parameter3,parameter4 from Syscode where 1=1");
			if (!(dto.getDomainid()==null || dto.getDomainid().equals(""))){
				sb.append(" and domainid='").append(dto.getDomainid()+"'");
			}
			if (!(dto.getDomaincode()==null || dto.getDomaincode().equals(""))){
				sb.append(" and domaincode = '").append(dto.getDomaincode()).append("'");
			}
			if (!(dto.getDomainname()==null || dto.getDomainname().equals(""))){
				sb.append(" and domainname like '%").append(dto.getDomainname()).append("%'");
			}
			if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
				sb.append(" and isvalid='").append(dto.getIsvalid()).append("'");
			}
			sb.append(" order by domainid");
			String ls_sql=sb.toString();		
			return ls_sql;
		}
		/**
		 * 增加系统代码参数
		 * @param msgdata
		 * @param	domainid	系统代码
           @param	domaincode	系统代码值
           @param	domainname	代码值含义
           @param	isvalid		是否有效
           @param	parameter1	参数值1
           @param	Parameter2	参数值2
           @param	Parameter3	参数值3
           @param	Parameter4	参数值4
         * @return
		 * @throws ApplicationException
		 */
		public String AddSyscode(String msgdata)throws ApplicationException{ //增加系统参数
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			SyscodeDTO dto=new SyscodeDTO();
			ClassHelper.copyProperties(msgdata, dto);	
			
			if (dto.getDomainid()==null) return "代码ID不能为空";
			if (dto.getDomaincode()==null || dto.getDomaincode().equals("")) return "代码值不能为空";
			if (dto.getDomainname()==null || dto.getDomainname().equals("")) return "代码含义不能为空";
			
			String hql="select count(*) from Syscode a where a.id.domainid='"+dto.getDomainid()+"' and a.id.domaincode='"+dto.getDomaincode()+"'";
			try {
				if (op.getCount(hql).intValue()>0){
					if (dto.getDomainid().equals("NEWSTYPE")){
						return "专题ID已存在";
					}else{
						return "代码值已存在";
					}
				}
			} catch (OPException e) {
				return e.getMessage();
			}
			
			Syscode syscode=new Syscode();   //实例化参数表			
			ClassHelper.copyProperties(msgdata, syscode);
			//设置主键
			SyscodeId id=new SyscodeId();			
			id.setDomainid(dto.getDomainid());
			id.setDomaincode(dto.getDomaincode());
			syscode.setId(id);			
			//数据保存
			try {
				trans.begin();						
				op.saveObj(syscode);					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;		
		}
		/**
		 * 修改系统参数
		 * @param msgdata
		 * @param	domainid	系统代码
           @param	domaincode	系统代码值
           @param	domainname	代码值含义
           @param	isvalid		是否有效
           @param	parameter1	参数值1
           @param	Parameter2	参数值2
           @param	Parameter3	参数值3
           @param	Parameter4	参数值4
		 * @return
		 * @throws ApplicationException
		 */
		public String ModSyscode(String msgdata)throws ApplicationException{ //修改系统参数
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			SyscodeDTO dto=new SyscodeDTO();
			ClassHelper.copyProperties(msgdata, dto);	
			
			if (dto.getDomainid()==null) return "代码ID不能为空";
			if (dto.getDomaincode()==null || dto.getDomaincode().equals("")) return "代码值不能为空";
			if (dto.getDomainname()==null || dto.getDomainname().equals("")) return "代码含义不能为空";
			Syscode syscode=new Syscode();   //实例化参数表			
			ClassHelper.copyProperties(msgdata, syscode);
			//设置主键
			SyscodeId id=new SyscodeId();			
			id.setDomainid(dto.getDomainid());
			id.setDomaincode(dto.getDomaincode());
			syscode.setId(id);
			
			//数据保存
			try {
				trans.begin();						
				op.updateObj(syscode);					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;	
		}
		/**
		 * 删除系统参数
		 * @param msgdata
		 * @param domainid	系统代码
           @param domaincode 系统代码值
         * @return
		 * @throws ApplicationException
		 */
		public String DelSyscode(String msgdata)throws ApplicationException{ //删除系统参数
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			SyscodeDTO dto=new SyscodeDTO();   //实例化参数表
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getDomainid()==null) return "代码ID不能为空";
			if (dto.getDomaincode()==null || dto.getDomaincode().equals("")) return "代码值不能为空";
			//数据保存
			try {
				trans.begin();						
				op.removeObjs( "delete from Syscode a where a.id.domainid='"+dto.getDomainid()+"' and a.id.domaincode='"+dto.getDomaincode()+"'");					
				trans.commit();
			} catch (OPException e1) {
			    try {
					trans.rollback();
				} catch (OPException e2) {
					return e2.getMessage();
				}	
				return e1.getMessage();
			}		
			return ls_return;	
		}
		
		/**
		 * 获取代码表内容
		 * @param msgdata
		 * @param domainid 	代码ID
		 * @return
		 * @throws ApplicationException
		 */
		public static String GetSyscodeList(String msgdata)throws ApplicationException{ //删除系统参数
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "{\"success\":\"false\",\"msg\":\"数据包验证未通过\",\"total\":0,\"root\":[]}";
			SyscodeDTO dto=new SyscodeDTO();   //实例化参数表
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getDomainid()==null) return "{\"success\":\"false\",\"msg\":\"代码值不能为空\",\"total\":0,\"root\":[]}";			
			TreeMap hm =(TreeMap) ServletActionContext.getServletContext().getAttribute(dto.getDomainid());  //获取缓存代码
			if (hm.size()==0) return "{\"success\":\"true\",\"msg\":\"成功\",\"total\":0,\"root\":[]}";
			Iterator it = hm.entrySet().iterator();  			
			StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"成功\",\"total\":"+hm.size()+",\"root\":[{");
			
			while (it.hasNext()) {  
	           // entry的输出结果如key0=value0等  
	           Map.Entry entry =(Map.Entry) it.next();  
	           Object key = entry.getKey();  
	           Object value=entry.getValue();  
	           json.append("\"").append(key.toString()).append("\":\"").append(value.toString()).append("\",");	             
	        }  
			json.delete(json.length()-1,json.length());  //去掉最后的,号
			json.append("}]}");
			return json.toString();
		}
}
