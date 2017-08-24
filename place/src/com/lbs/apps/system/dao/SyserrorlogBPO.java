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
		 * ��ѯϵͳ�쳣��־
		 * @param msgdata
		 *@param createdby	�û�����
          @param modmethod	������
          @param logdesc	��־����
          @param starttime	��ʼ����ʱ��
          @param endtime	��������ʱ��
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
