package com.lbs.apps.system.dao;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.system.po.Syspara;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class SysparaBPO {
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
		 * 查询系统参数
		 * @param msgdata
		 * @param	paracode   系统参数编码
           @param	paravalue   系统参数值
         * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchSyspara(String msgdata)throws ApplicationException {
			SysparaDTO dto=new SysparaDTO();
			ClassHelper.copyProperties(msgdata, dto);		 
			StringBuffer sb=new StringBuffer("select paracode,paravalue,isvalid,paradesc from Syspara where 1=1");
			if (!(dto.getParacode()==null || dto.getParacode().equals(""))){
				sb.append(" and paracode='").append(dto.getParacode()+"'");
			}
			if (!(dto.getParavalue()==null || dto.getParavalue().equals(""))){
				sb.append(" and paravalue='").append(dto.getParavalue()+"'");
			}
			if (!(dto.getParadesc()==null || dto.getParadesc().equals(""))){
				sb.append(" and paradesc like '%").append(dto.getParadesc()).append("%'");
			}
			if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
				sb.append(" and isvalid='").append(dto.getIsvalid()).append("'");
			}
			String ls_sql=sb.toString();		
			return ls_sql;
		}
		/**
		 * 增加系统参数
		 * @param msgdata
		 *@param paracode 系统参数编码
          @param paravalue 系统参数值
          @param isvalid	是否有效
          @param paradesc	参数描述
         * @return 成功或者失败原因
		 * @throws ApplicationException
		 */
		public String AddSyspara(String msgdata)throws ApplicationException{ //增加系统参数
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			Syspara syspara=new Syspara();   //实例化参数表
			ClassHelper.copyProperties(msgdata, syspara);	
			if (syspara.getParacode()==null) return "参数代码不能为空";
			if (syspara.getParavalue()==null || syspara.getParavalue().equals("")) return "参数名称不能为空";
			//数据保存
			try {
				trans.begin();						
				op.saveObj(syspara);					
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
		 * @param paracode   系统参数编码
           @param paravalue	  系统参数值
           @param isvalid	  是否有效
           @param paradesc	  参数描述
         * @return
		 * @throws ApplicationException
		 */
		public String ModSyspara(String msgdata)throws ApplicationException{ //修改系统参数
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			Syspara syspara=new Syspara();   //实例化参数表
			ClassHelper.copyProperties(msgdata, syspara);	
			if (syspara.getParacode()==null) return "参数代码不能为空";
			if (syspara.getParavalue()==null || syspara.getParavalue().equals("")) return "参数名称不能为空";
			//数据保存
			try {
				trans.begin();						
				op.updateObj(syspara);					
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
		 * @param paracode 系统参数编码
		 * @return
		 * @throws ApplicationException
		 */
		public String DelSyspara(String msgdata)throws ApplicationException{ //删除系统参数
			String ls_return="0";
			//json验证
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata数据包无效";
			SysparaDTO dto=new SysparaDTO();   //实例化参数表
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getParacode()==null) return "参数代码不能为空";
			//数据保存
			try {
				trans.begin();						
				op.removeObjs( "delete from Syspara a where a.paracode='"+dto.getParacode()+"'");					
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
