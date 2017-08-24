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
	
	//�жϲ�ѯ�����Ƿ�OK���ɹ�����0�����򷵻��쳣ԭ��
	public String CheckSql_Where(String msgdata){
		String ls_return="0";
		if (msgdata==null || msgdata.equals("")){  //û�д��ݲ���
			ls_return="��������쳣";			
		}else{					 
			try{
				JSONObject obj = JSONObject.fromObject(msgdata);   //����JSON���ݰ�
				//��¼������֤
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
	 * ��ѯϵͳ��ɫ
	 * @param msgdata
	 * @param roleid	��ɫ����
       @param rolename	��ɫ����
       @param ispreset	�Ƿ�Ԥ���ɫ
       @param sysname	��ɫ������ϵͳ
     * @return ��ѯsql
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
	 * ����ϵͳ��ɫ
	 * @param msgdata  ����������
	 * @param roleid   ��ɫ����
     * @param rolename ��ɫ����
     * @param roledesc ��ɫ����
     * @param sysname  ��ɫ������ϵͳ
     * @return
	 * @throws ApplicationException
	 */
	public String AddSysrole(String msgdata)throws ApplicationException{ //����ϵͳ��ɫ
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		Sysrole sysrole=new Sysrole();   //ʵ������ɫ��
		ClassHelper.copyProperties(msgdata, sysrole);	
		if (sysrole.getRoleid()==null||sysrole.getRoleid().intValue()==0) return "��ɫ��Ų���Ϊ��";
		if (sysrole.getRolename()==null || sysrole.getRolename().equals("")) return "��ɫ���Ʋ���Ϊ��";		
		try {
			if (op.getCount("select count(*) from Sysrole where roleid="+sysrole.getRoleid()).intValue()>0){
				return "��ɫ�����Ѿ�����";
			}
		} catch (OPException e) {
			return "��ȡ�Ѵ��ڽ�ɫ����ʱ����";
		}
			
		
		sysrole.setIspreset("102");  //�����ý�ɫ
		if (sysrole.getSysname()==null || sysrole.getSysname().equals(""))	sysrole.setSysname("101");
		//���ݱ���
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
	 * �޸�ϵͳ��ɫ
	 * @param msgdata  ����������
	 * @param roleid   ��ɫ����
	 * @param rolename ��ɫ����
     * @param roledesc ��ɫ����
     * @param ispreset �Ƿ�Ԥ���ɫ
     * @param sysname  ��ɫ������ϵͳ
     * @return
	 * @throws ApplicationException
	 */
	public String ModSysrole(String msgdata)throws ApplicationException{ //�޸�ϵͳ��ɫ
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysroleDTO dto=new SysroleDTO();   //ʵ������ɫ��
		ClassHelper.copyProperties(msgdata, dto);
		
		Sysrole sysrole=null;   //ʵ������ɫ��
		try {
			sysrole=(Sysrole)op.retrieveObj(Sysrole.class, dto.getRoleid());
			if (StringUtils.isBlank(sysrole.getIspreset())) sysrole.setIspreset("102");
			if ("101".equals(sysrole.getIspreset())) return "ϵͳԤ���ɫ�������޸�";
			if (StringUtils.isBlank(sysrole.getSysname()))	 sysrole.setSysname("101");  //Ĭ�Ϻ�̨ϵͳ��ɫ
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "��ɫ��Ϣ������";
		}
		ClassHelper.copyProperties(dto, sysrole);	
		if (sysrole.getRoleid()==null||sysrole.getRoleid().intValue()==0) return "��ɫ��Ų���Ϊ��";
		if (StringUtils.isBlank(sysrole.getRolename())) return "��ɫ���Ʋ���Ϊ��";
		if (StringUtils.isBlank(sysrole.getIspreset())) return "�Ƿ�ϵͳԤ���ɫ����Ϊ��";
		if (StringUtils.isBlank(sysrole.getSysname()))	return "��ɫ������ϵͳ���Ʋ���Ϊ��";
		//���ݱ���
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
	 * ɾ��ϵͳ��ɫ
	 * @param msgdata  ����������
	 * @param roleid ��ɫ����
	 * @return
	 * @throws ApplicationException
	 */
	public String DelSysrole(String msgdata)throws ApplicationException{ //ɾ��ϵͳ��ɫ
		String ls_return="0";
		//json��֤
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)) return "msgdata���ݰ���Ч";
		SysroleDTO dto=new SysroleDTO();   //ʵ������ɫ��
		ClassHelper.copyProperties(msgdata, dto);	
		if (dto.getRoleid()==null) return "��ɫ��Ų���Ϊ��";
		try {
			Sysrole sysrole=(Sysrole)op.retrieveObj(Sysrole.class, dto.getRoleid());
			if (StringUtils.isBlank(sysrole.getIspreset())) sysrole.setIspreset("102");
			if (sysrole.getIspreset().equals("101")) return "ϵͳԤ���ɫ������ɾ��";
		} catch (OPException e) {
			return e.getMessage();
		} catch (NotFindException e) {
			return "��ɫ��Ϣ������";
		}
		//���ݱ���
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
