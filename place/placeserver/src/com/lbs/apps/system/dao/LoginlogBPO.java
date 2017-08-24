package com.lbs.apps.system.dao;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.OPManager;

public class LoginlogBPO {
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
		 * 查询登陆日志
		 * @param msgdata
		 * @param userid	用户编码
           @param starttime	开始登录时间
           @param endtime	结束登录时间
         * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchLoginlog(String msgdata)throws ApplicationException {
			LoginlogDTO dto=new LoginlogDTO();
			ClassHelper.copyProperties(msgdata, dto);		 
			StringBuffer sb=new StringBuffer("select id,userid,convert(varchar,logintime,120) logintime,convert(varchar,logouttime,120) logouttime,ip from Loginlog where 1=1");
			
			if (dto.getUserid()!=null){
				sb.append(" and userid=").append(dto.getUserid());
			}
			if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
				sb.append(" and logintime >= '").append(dto.getStarttime()).append(" 00:00:00'");				
			}
			if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
				sb.append(" and logintime <= '").append(dto.getEndtime()).append(" 23:59:59'");
			}
			if (!(dto.getIp()==null || dto.getIp().equals(""))){
				sb.append(" and ip = '").append(dto.getIp()).append("'");
			}
			sb.append(" order by id desc");
			String ls_sql=sb.toString();		
			return ls_sql;
		}	
		

}
