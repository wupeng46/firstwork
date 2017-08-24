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
		 * ��ѯϵͳ����
		 * @param msgdata
		 * @param	paracode   ϵͳ��������
           @param	paravalue   ϵͳ����ֵ
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
		 * ����ϵͳ����
		 * @param msgdata
		 *@param paracode ϵͳ��������
          @param paravalue ϵͳ����ֵ
          @param isvalid	�Ƿ���Ч
          @param paradesc	��������
         * @return �ɹ�����ʧ��ԭ��
		 * @throws ApplicationException
		 */
		public String AddSyspara(String msgdata)throws ApplicationException{ //����ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			Syspara syspara=new Syspara();   //ʵ����������
			ClassHelper.copyProperties(msgdata, syspara);	
			if (syspara.getParacode()==null) return "�������벻��Ϊ��";
			if (syspara.getParavalue()==null || syspara.getParavalue().equals("")) return "�������Ʋ���Ϊ��";
			//���ݱ���
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
		 * �޸�ϵͳ����
		 * @param msgdata
		 * @param paracode   ϵͳ��������
           @param paravalue	  ϵͳ����ֵ
           @param isvalid	  �Ƿ���Ч
           @param paradesc	  ��������
         * @return
		 * @throws ApplicationException
		 */
		public String ModSyspara(String msgdata)throws ApplicationException{ //�޸�ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			Syspara syspara=new Syspara();   //ʵ����������
			ClassHelper.copyProperties(msgdata, syspara);	
			if (syspara.getParacode()==null) return "�������벻��Ϊ��";
			if (syspara.getParavalue()==null || syspara.getParavalue().equals("")) return "�������Ʋ���Ϊ��";
			//���ݱ���
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
		 * ɾ��ϵͳ����
		 * @param msgdata
		 * @param paracode ϵͳ��������
		 * @return
		 * @throws ApplicationException
		 */
		public String DelSyspara(String msgdata)throws ApplicationException{ //ɾ��ϵͳ����
			String ls_return="0";
			//json��֤
			JsonValidator jsonValidator=new JsonValidator();
			if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
			SysparaDTO dto=new SysparaDTO();   //ʵ����������
			ClassHelper.copyProperties(msgdata, dto);	
			if (dto.getParacode()==null) return "�������벻��Ϊ��";
			//���ݱ���
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
