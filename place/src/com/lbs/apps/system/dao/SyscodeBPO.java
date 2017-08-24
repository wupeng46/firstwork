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
		 * ��ѯϵͳ�����б�
		 * @param msgdata
		 *@param domainid	ϵͳ����
          @param domaincode	 ϵͳ����ֵ
          @param domainname	 ����ֵ����
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
		 * ����ϵͳ�������
		 * @param msgdata
		 * @param	domainid	ϵͳ����
           @param	domaincode	ϵͳ����ֵ
           @param	domainname	����ֵ����
           @param	isvalid		�Ƿ���Ч
           @param	parameter1	����ֵ1
           @param	Parameter2	����ֵ2
           @param	Parameter3	����ֵ3
           @param	Parameter4	����ֵ4
         * @return
		 * @throws ApplicationException
		 */
		public String AddSyscode(String msgdata)throws ApplicationException{ //����ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			SyscodeDTO dto=new SyscodeDTO();
			ClassHelper.copyProperties(msgdata, dto);	
			
			if (dto.getDomainid()==null) return "����ID����Ϊ��";
			if (dto.getDomaincode()==null || dto.getDomaincode().equals("")) return "����ֵ����Ϊ��";
			if (dto.getDomainname()==null || dto.getDomainname().equals("")) return "���뺬�岻��Ϊ��";
			
			String hql="select count(*) from Syscode a where a.id.domainid='"+dto.getDomainid()+"' and a.id.domaincode='"+dto.getDomaincode()+"'";
			try {
				if (op.getCount(hql).intValue()>0){
					if (dto.getDomainid().equals("NEWSTYPE")){
						return "ר��ID�Ѵ���";
					}else{
						return "����ֵ�Ѵ���";
					}
				}
			} catch (OPException e) {
				return e.getMessage();
			}
			
			Syscode syscode=new Syscode();   //ʵ����������			
			ClassHelper.copyProperties(msgdata, syscode);
			//��������
			SyscodeId id=new SyscodeId();			
			id.setDomainid(dto.getDomainid());
			id.setDomaincode(dto.getDomaincode());
			syscode.setId(id);			
			//���ݱ���
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
		 * �޸�ϵͳ����
		 * @param msgdata
		 * @param	domainid	ϵͳ����
           @param	domaincode	ϵͳ����ֵ
           @param	domainname	����ֵ����
           @param	isvalid		�Ƿ���Ч
           @param	parameter1	����ֵ1
           @param	Parameter2	����ֵ2
           @param	Parameter3	����ֵ3
           @param	Parameter4	����ֵ4
		 * @return
		 * @throws ApplicationException
		 */
		public String ModSyscode(String msgdata)throws ApplicationException{ //�޸�ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			SyscodeDTO dto=new SyscodeDTO();
			ClassHelper.copyProperties(msgdata, dto);	
			
			if (dto.getDomainid()==null) return "����ID����Ϊ��";
			if (dto.getDomaincode()==null || dto.getDomaincode().equals("")) return "����ֵ����Ϊ��";
			if (dto.getDomainname()==null || dto.getDomainname().equals("")) return "���뺬�岻��Ϊ��";
			Syscode syscode=new Syscode();   //ʵ����������			
			ClassHelper.copyProperties(msgdata, syscode);
			//��������
			SyscodeId id=new SyscodeId();			
			id.setDomainid(dto.getDomainid());
			id.setDomaincode(dto.getDomaincode());
			syscode.setId(id);
			
			//���ݱ���
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
		 * ɾ��ϵͳ����
		 * @param msgdata
		 * @param domainid	ϵͳ����
           @param domaincode ϵͳ����ֵ
         * @return
		 * @throws ApplicationException
		 */
		public String DelSyscode(String msgdata)throws ApplicationException{ //ɾ��ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			SyscodeDTO dto=new SyscodeDTO();   //ʵ����������
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getDomainid()==null) return "����ID����Ϊ��";
			if (dto.getDomaincode()==null || dto.getDomaincode().equals("")) return "����ֵ����Ϊ��";
			//���ݱ���
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
		 * ��ȡ���������
		 * @param msgdata
		 * @param domainid 	����ID
		 * @return
		 * @throws ApplicationException
		 */
		public static String GetSyscodeList(String msgdata)throws ApplicationException{ //ɾ��ϵͳ����
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "{\"success\":\"false\",\"msg\":\"���ݰ���֤δͨ��\",\"total\":0,\"root\":[]}";
			SyscodeDTO dto=new SyscodeDTO();   //ʵ����������
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getDomainid()==null) return "{\"success\":\"false\",\"msg\":\"����ֵ����Ϊ��\",\"total\":0,\"root\":[]}";			
			TreeMap hm =(TreeMap) ServletActionContext.getServletContext().getAttribute(dto.getDomainid());  //��ȡ�������
			if (hm.size()==0) return "{\"success\":\"true\",\"msg\":\"�ɹ�\",\"total\":0,\"root\":[]}";
			Iterator it = hm.entrySet().iterator();  			
			StringBuffer json=new StringBuffer("{\"success\":\"true\",\"msg\":\"�ɹ�\",\"total\":"+hm.size()+",\"root\":[{");
			
			while (it.hasNext()) {  
	           // entry����������key0=value0��  
	           Map.Entry entry =(Map.Entry) it.next();  
	           Object key = entry.getKey();  
	           Object value=entry.getValue();  
	           json.append("\"").append(key.toString()).append("\":\"").append(value.toString()).append("\",");	             
	        }  
			json.delete(json.length()-1,json.length());  //ȥ������,��
			json.append("}]}");
			return json.toString();
		}
}
