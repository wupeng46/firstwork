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
	
	//�жϲ�ѯ�����Ƿ�OK���ɹ�����0�����򷵻��쳣ԭ��
		public String CheckSql_Where(String msgdata){
			String ls_return="0";
			if (msgdata==null || msgdata.equals("")){  //û�д��ݲ���
				ls_return="��������쳣";			
			}else{					 
				try{
					JSONObject obj = JSONObject.fromObject(msgdata);   //����JSON���ݰ�				
					//if (!obj.has("roleid")){					
					//	ls_return="���ݰ���δ����roleid������Ϣ";									
					//}
				}catch(Exception e){
					ls_return="msgdata�����ַ����Ƿ�";						
				}			
			}	
			return ls_return;		
		}
		/**
		 * ��ѯ��½��־
		 * @param msgdata
		 * @param userid	�û�����
           @param starttime	��ʼ��¼ʱ��
           @param endtime	������¼ʱ��
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
