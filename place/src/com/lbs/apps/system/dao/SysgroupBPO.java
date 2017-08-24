package com.lbs.apps.system.dao;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.system.po.Sysgroup;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class SysgroupBPO {
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
		 * 查询系统组织机构
		 * @param msgdata
		 * @param groupid  组织机构编号
		 * @param groupname 组织机构名称
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchSysgroup(String msgdata)throws ApplicationException {
			SysgroupDTO dto=new SysgroupDTO();
			ClassHelper.copyProperties(msgdata, dto);		 
			StringBuffer sb=new StringBuffer("select groupid,groupname,parentid,isvalid,contact,telephone,address,memo from Sysgroup where 1=1");
			if (dto.getGroupid()!=null){
				sb.append(" and groupid=").append(dto.getGroupid());
			}
			if (!(dto.getGroupname()==null || dto.getGroupname().equals(""))){
				sb.append(" and groupname like '%").append(dto.getGroupname()).append("%'");
			}		
			String ls_sql=sb.toString();		
			return ls_sql;
		}
		
		/**
		 * 查询该机构下所有子机构列表
		 * @param msgdata
		 * @param groupid 组织机构编号
		 * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchChildSysgroup(String msgdata)throws ApplicationException {
			SysgroupDTO dto=new SysgroupDTO();
			ClassHelper.copyProperties(msgdata, dto);		 
			StringBuffer sb=new StringBuffer("select groupid,groupname,parentid,isvalid,contact,telephone,address,memo from Sysgroup where 1=1");
			if (dto.getGroupid()!=null){
				sb.append(" and parentid=").append(dto.getGroupid());
			}				
			String ls_sql=sb.toString();		
			return ls_sql;
		}
		/**
		 * 增加组织机构
		 * @param msgdata
		 * @param groupid 组织机构代码编号
		 * @return
		 * @throws ApplicationException
		 */
		public String AddSysgroup(String msgdata)throws ApplicationException{ //增加系统机构
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			Sysgroup sysgroup=new Sysgroup();   //实例化参数表
			ClassHelper.copyProperties(msgdata, sysgroup);	
			if (sysgroup.getGroupid()==null) return "机构代码不能为空";
			if (sysgroup.getGroupname()==null || sysgroup.getGroupname().equals("")) return "机构名称不能为空";
			
			try {
				if (op.getCount("select count(*) from Sysgroup where groupid="+sysgroup.getGroupid()).intValue()>0){
					return "机构编码已经存在";
				}
			} catch (OPException e) {
				return "获取已存在机构编码时出错";
			}
			
			//数据保存
			try {
				trans.begin();						
				op.saveObj(sysgroup);					
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
		 * 修改组织机构
		 *@param msgdata
		 *@param groupid	组织机构编码
          @param groupname	组织机构名称
          @param parentid	上级机构编码(父级时上级编码为0)
          @param isvalid	是否有效
          @param contact	联系人
          @param telephone	联系电话
          @param address	地址
          @param memo	        备注
         * @return
		 * @throws ApplicationException
		 */
		public String ModSysgroup(String msgdata)throws ApplicationException{ //修改系统机构
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			Sysgroup sysgroup=new Sysgroup();   //实例化参数表
			ClassHelper.copyProperties(msgdata, sysgroup);	
			if (sysgroup.getGroupid()==null) return "机构代码不能为空";
			if (sysgroup.getGroupname()==null || sysgroup.getGroupname().equals("")) return "机构名称不能为空";
			//数据保存
			try {
				trans.begin();						
				op.updateObj(sysgroup);					
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
		 * 删除组织机构
		 * @param msgdata
		 * @param 组织机构编号
		 * @return
		 * @throws ApplicationException
		 */
		public String DelSysgroup(String msgdata)throws ApplicationException{ //删除系统机构
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			SysgroupDTO dto=new SysgroupDTO();   //实例化参数表
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getGroupid()==null) return "机构代码不能为空";
			//数据保存
			try {
				trans.begin();						
				op.removeObjs( "delete from Sysgroup a where a.groupid="+dto.getGroupid());					
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

}
