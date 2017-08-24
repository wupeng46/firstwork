package com.lbs.apps.system.dao;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.OPManager;

public class SyserrorlogBPO {
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
		 * 查询系统异常日志
		 * @param msgdata
		 *@param createdby	用户编码
          @param modmethod	方法名
          @param logdesc	日志描述
          @param starttime	开始建立时间
          @param endtime	结束建立时间
         * @return
		 * @throws ApplicationException
		 */
		public String GetSql_SearchSyserrorlog(String msgdata)throws ApplicationException {
			SyserrorlogDTO dto=new SyserrorlogDTO();
			ClassHelper.copyProperties(msgdata, dto);		 
			StringBuffer sb=new StringBuffer("select syserrorlogid,modmethod,logdesc,errorreason,createdby,convert(varchar, createddate, 120) createddate from Syserrorlog where 1=1");
			
			if (!(dto.getModmethod()==null || dto.getModmethod().equals(""))){
				sb.append(" and modmethod like '%").append(dto.getModmethod()+"%'");
			}
			if (!(dto.getLogdesc()==null || dto.getLogdesc().equals(""))){
				sb.append(" and logdesc like '%").append(dto.getLogdesc()).append("%'");
			}
			if (!(dto.getCreatedby()==null)){
				sb.append(" and createdby = ").append(dto.getCreatedby());
			}
			if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
				sb.append(" and createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
			}
			if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
				sb.append(" and createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
			}
			sb.append(" order by syserrorlogid desc");
			String ls_sql=sb.toString();		
			return ls_sql;
		}	
		

}
