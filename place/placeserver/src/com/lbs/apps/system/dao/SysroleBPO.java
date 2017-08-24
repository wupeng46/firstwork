package com.lbs.apps.system.dao;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.lbs.apps.common.AesTools;
import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.system.po.Sysrole;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class SysroleBPO {
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
				//必录条件验证
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
	 * 查询系统角色
	 * @param msgdata
	 * @param roleid	角色编码
       @param rolename	角色名称
       @param ispreset	是否预设角色
       @param sysname	角色隶属子系统
     * @return 查询sql
	 * @throws ApplicationException
	 */
	public String GetSql_SearchSysrole(String msgdata)throws ApplicationException {
		SysroleDTO dto=new SysroleDTO();
		ClassHelper.copyProperties(msgdata, dto);		 
		StringBuffer sb=new StringBuffer("select roleid,rolename,roledesc,ispreset,sysname from sysrole where 1=1");
		if (dto.getRoleid()!=null){
			sb.append(" and roleid=").append(dto.getRoleid());
		}
		if (!(dto.getIspreset()==null || dto.getIspreset().equals(""))){
			sb.append(" and ispreset = '").append(dto.getIspreset()).append("'");
		}
		if (!(dto.getSysname()==null || dto.getSysname().equals(""))){
			sb.append(" and sysname = '").append(dto.getSysname()).append("'");
		}
		if (!(dto.getRolename()==null || dto.getRolename().equals(""))){
			sb.append(" and rolename like '%").append(dto.getRolename()).append("%'");
		}	
		String ls_sql=sb.toString();		
		return ls_sql;
	}
	/**
	 * 增加系统角色
	 * @param msgdata  参数加密体
	 * @param roleid   角色编码
     * @param rolename 角色名称
     * @param roledesc 角色描述
     * @param sysname  角色隶属子系统
     * @return
	 * @throws ApplicationException
	 */
	public String AddSysrole(String msgdata)throws ApplicationException{ //增加系统角色
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		Sysrole sysrole=new Sysrole();   //实例化角色表
		ClassHelper.copyProperties(msgdata, sysrole);	
		if (sysrole.getRoleid()==null||sysrole.getRoleid().intValue()==0) return "角色编号不能为空";
		if (sysrole.getRolename()==null || sysrole.getRolename().equals("")) return "角色名称不能为空";		
		try {
			if (op.getCount("select count(*) from Sysrole where roleid="+sysrole.getRoleid()).intValue()>0){
				return "角色编码已经存在";
			}
		} catch (OPException e) {
			return "获取已存在角色编码时出错";
		}
			
		
		sysrole.setIspreset("102");  //非内置角色
		if (sysrole.getSysname()==null || sysrole.getSysname().equals(""))	sysrole.setSysname("101");
		//数据保存
		try {
			trans.begin();						
			op.saveObj(sysrole);					
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
	 * 修改系统角色
	 * @param msgdata  参数加密体
	 * @param roleid   角色编码
	 * @param rolename 角色名称
     * @param roledesc 角色描述
     * @param ispreset 是否预设角色
     * @param sysname  角色隶属子系统
     * @return
	 * @throws ApplicationException
	 */
	public String ModSysrole(String msgdata)throws ApplicationException{ //修改系统角色
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysroleDTO dto=new SysroleDTO();   //实例化角色表
		ClassHelper.copyProperties(msgdata, dto);
		
		Sysrole sysrole=null;   //实例化角色表
		try {
			sysrole=(Sysrole)op.retrieveObj(Sysrole.class, dto.getRoleid());
			if (StringUtils.isBlank(sysrole.getIspreset())) sysrole.setIspreset("102");
			if ("101".equals(sysrole.getIspreset())) return "系统预设角色不允许修改";
			if (StringUtils.isBlank(sysrole.getSysname()))	 sysrole.setSysname("101");  //默认后台系统角色
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "角色信息不存在";
		}
		ClassHelper.copyProperties(dto, sysrole);	
		if (sysrole.getRoleid()==null||sysrole.getRoleid().intValue()==0) return "角色编号不能为空";
		if (StringUtils.isBlank(sysrole.getRolename())) return "角色名称不能为空";
		if (StringUtils.isBlank(sysrole.getIspreset())) return "是否系统预设角色不能为空";
		if (StringUtils.isBlank(sysrole.getSysname()))	return "角色隶属子系统名称不能为空";
		//数据保存
		try {
			trans.begin();						
			op.updateObj(sysrole);					
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
	 * 删除系统角色
	 * @param msgdata  参数加密体
	 * @param roleid 角色编码
	 * @return
	 * @throws ApplicationException
	 */
	public String DelSysrole(String msgdata)throws ApplicationException{ //删除系统角色
		String ls_return="0";
		//json验证
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
		SysroleDTO dto=new SysroleDTO();   //实例化角色表
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getRoleid()==null) return "角色编号不能为空";
		try {
			Sysrole sysrole=(Sysrole)op.retrieveObj(Sysrole.class, dto.getRoleid());
			if (StringUtils.isBlank(sysrole.getIspreset())) sysrole.setIspreset("102");
			if (sysrole.getIspreset().equals("101")) return "系统预设角色不允许删除";
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "角色信息不存在";
		}
		//数据保存
		try {
			trans.begin();	
			op.removeObjs( "delete from Rolemenu a where a.id.roleid="+dto.getRoleid());
			op.removeObjs( "delete from Sysrole a where a.roleid="+dto.getRoleid());					
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
