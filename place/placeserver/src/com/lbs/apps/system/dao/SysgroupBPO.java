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
		 * ��ѯϵͳ��֯����
		 * @param msgdata
		 * @param groupid  ��֯�������
		 * @param groupname ��֯��������
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
		 * ��ѯ�û����������ӻ����б�
		 * @param msgdata
		 * @param groupid ��֯�������
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
		 * ������֯����
		 * @param msgdata
		 * @param groupid ��֯����������
		 * @return
		 * @throws ApplicationException
		 */
		public String AddSysgroup(String msgdata)throws ApplicationException{ //����ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			Sysgroup sysgroup=new Sysgroup();   //ʵ����������
			ClassHelper.copyProperties(msgdata, sysgroup);	
			if (sysgroup.getGroupid()==null) return "�������벻��Ϊ��";
			if (sysgroup.getGroupname()==null || sysgroup.getGroupname().equals("")) return "�������Ʋ���Ϊ��";
			
			try {
				if (op.getCount("select count(*) from Sysgroup where groupid="+sysgroup.getGroupid()).intValue()>0){
					return "���������Ѿ�����";
				}
			} catch (OPException e) {
				return "��ȡ�Ѵ��ڻ�������ʱ����";
			}
			
			//���ݱ���
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
		 * �޸���֯����
		 *@param msgdata
		 *@param groupid	��֯��������
          @param groupname	��֯��������
          @param parentid	�ϼ���������(����ʱ�ϼ�����Ϊ0)
          @param isvalid	�Ƿ���Ч
          @param contact	��ϵ��
          @param telephone	��ϵ�绰
          @param address	��ַ
          @param memo	        ��ע
         * @return
		 * @throws ApplicationException
		 */
		public String ModSysgroup(String msgdata)throws ApplicationException{ //�޸�ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			Sysgroup sysgroup=new Sysgroup();   //ʵ����������
			ClassHelper.copyProperties(msgdata, sysgroup);	
			if (sysgroup.getGroupid()==null) return "�������벻��Ϊ��";
			if (sysgroup.getGroupname()==null || sysgroup.getGroupname().equals("")) return "�������Ʋ���Ϊ��";
			//���ݱ���
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
		 * ɾ����֯����
		 * @param msgdata
		 * @param ��֯�������
		 * @return
		 * @throws ApplicationException
		 */
		public String DelSysgroup(String msgdata)throws ApplicationException{ //ɾ��ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			SysgroupDTO dto=new SysgroupDTO();   //ʵ����������
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getGroupid()==null) return "�������벻��Ϊ��";
			//���ݱ���
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
