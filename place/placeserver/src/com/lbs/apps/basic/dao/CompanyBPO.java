package com.lbs.apps.basic.dao;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.system.dao.SeqBPO;
import com.lbs.apps.system.po.Company;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class CompanyBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	// �жϲ�ѯ�����Ƿ�OK���ɹ�����0�����򷵻��쳣ԭ��
		public String CheckSql_Where(String msgdata) {
			String ls_return = "0";
			if (msgdata == null || msgdata.equals("")) { // û�д��ݲ���
				ls_return = "��������쳣";
			} else {
				try {
					JSONObject obj = JSONObject.fromObject(msgdata); // ����JSON���ݰ�
					//if (!obj.has("companyid")) {
					//	ls_return = "���ݰ���δ����companyid������Ϣ";
					//}
					//String ls_companyid = (String) obj.get("companyid");
					//if (ls_companyid == null || ls_companyid.equals("")
					//		|| ls_companyid.equals("0"))
					//	return "�����̱��벻��Ϊ��";
				} catch (Exception e) {
					ls_return = "msgdata�����ַ����Ƿ�";
				}
			}
			return ls_return;
		}
	public String SearchCompany(String msgdata) throws ApplicationException {
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchCompany(dto);		
	}
	
	private String SearchCompany(CompanyDTO dto) throws ApplicationException {
		StringBuffer sb=new StringBuffer("select companyid,companyname,companylevel")
		.append(",companytype,regdate,enddate,orgcode,legalperson,legalcardtype,legalcardno")
		.append(",taxno,bankid,bankname,accountname,bankaccount,mailaddress,mailreceiver,mailtelephone")
		.append(",areaid,address,dutyperson,telephone,mobilephone,fax,email,zipcode,checkstatus")
		.append(",checkerid,convert(varchar,checkdate,120) checkdate,checkdesc,balance,frozenamount,balanceaccount,balancephone,allowpublish")
		.append(",memo,createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate,isvalid  FROM company where 1=1");
		
		if (!(dto.getCompanyid()==null || dto.getCompanyid().intValue()==0)){
			sb.append(" and companyid=").append(dto.getCompanyid());
		//}else{
		//	sb.append(" and companyid=-999999");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getCompanyname()==null || dto.getCompanyname().equals(""))){
			sb.append(" and companyname like '%").append(dto.getCompanyname()).append("%'");
		}
		if (!(dto.getDutyperson()==null || dto.getDutyperson().equals(""))){
			sb.append(" and dutyperson = '").append(dto.getDutyperson()).append("'");
		}
		if (!(dto.getTelephone()==null || dto.getTelephone().equals(""))){
			sb.append(" and (telephone = '").append(dto.getTelephone()).append("' or mobilephone='").append(dto.getTelephone()).append("')");
		}
		String ls_sql=sb.toString();		
		return ls_sql;	
	}
	
	private String SearchMember(CompanyDTO dto) throws ApplicationException {
		StringBuffer sb=new StringBuffer("select memberid,membertype,memberlevel,bankid,bankname,accountname,bankaccount,mailaddress,mailreceiver,mailtelephone,companyname,companytype")
		.append(",regdate,enddate,orgcode,legalperson,legalcardtype,legalcardno,taxno,areaid,address,dutyperson,telephone,mobilephone,fax,email,zipcode,checkstatus")
		.append(",checkerid,checkdate,checkdesc,memo,createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate,balance,frozenamount,balanceaccount,balancephone")
		.append(" from member where 1=1");
		
		if (!(dto.getMemberid()==null || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid=").append(dto.getMemberid());
		//}else{
		//	sb.append(" and companyid=-999999");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getCompanyname()==null || dto.getCompanyname().equals(""))){
			sb.append(" and companyname like '%").append(dto.getCompanyname()).append("%'");
		}
		if (!(dto.getDutyperson()==null || dto.getDutyperson().equals(""))){
			sb.append(" and dutyperson = '").append(dto.getDutyperson()).append("'");
		}
		if (!(dto.getTelephone()==null || dto.getTelephone().equals(""))){
			sb.append(" and (telephone = '").append(dto.getTelephone()).append("' or mobilephone='").append(dto.getTelephone()).append("')");
		}
		String ls_sql=sb.toString();		
		return ls_sql;	
	}
	
	public String SearchMember(String msgdata) throws ApplicationException {
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchMember(dto);		
	}

	public CommonDTO AddCompany(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyname()==null || dto.getCompanyname().equals("")){
			returnDTO.setMsg("���������Ʋ���Ϊ��");
			return returnDTO;
		}
		if ((dto.getTelephone()==null || dto.getTelephone().equals("") ) && (dto.getMobilephone()==null || dto.getMobilephone().equals(""))){
			returnDTO.setMsg("�����̵绰����Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		String hql="select count(*) from Company where companyname='"+dto.getCompanyname()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("�ó����������Ѵ���");
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		Company company=new Company();
		ClassHelper.copyProperties(dto, company);
		String ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //��ȡ���к�
		company.setCompanyid(new Integer(ls_seq));
		company.setIsvalid("101");
		company.setCreateddate(now);
		company.setModifydate(now);
		company.setModifyby(dto.getCreatedby());
		//���ݱ���
		try {
			trans.begin();						
			op.saveObj(company);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}	
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�����������Ϣ�ɹ�!");
		return returnDTO;
	}

	public CommonDTO ModCompany(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("�����̱�Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getCompanyname()==null || dto.getCompanyname().equals("")){
			returnDTO.setMsg("���������Ʋ���Ϊ��");
			return returnDTO;
		}
		if ((dto.getTelephone()==null || dto.getTelephone().equals("") ) && (dto.getMobilephone()==null || dto.getMobilephone().equals(""))){
			returnDTO.setMsg("�����̵绰����Ϊ��");
			return returnDTO;
		}
		if (dto.getIsvalid()==null || dto.getIsvalid().equals("")){
			returnDTO.setMsg("�Ƿ���Ч��ǲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		String hql="select count(*) from Company where companyname='"+dto.getCompanyname()+"' and companyid!="+dto.getCompanyid();
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("�ó����������Ѵ���");
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		Company company=null;
		try {
			company = (Company)op.retrieveObj(Company.class, dto.getCompanyid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�����̼�¼������,�����̱��:"+dto.getCompanyid());
			return returnDTO;
		}
		Integer li_createdby=company.getCreatedby();
		Timestamp ld_createddate=company.getCreateddate();
		Integer li_org=company.getCreatedorg();
		ClassHelper.copyProperties(dto, company);
		company.setCreatedby(li_createdby);
		company.setCreateddate(ld_createddate);
		company.setCreatedorg(li_org);
		company.setModifyby(dto.getCreatedby());
		company.setModifydate(now);
		//���ݱ���
		try {
			trans.begin();						
			op.updateObj(company);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}	
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�޸ĳ�������Ϣ�ɹ�!");
		return returnDTO;		
	}

	public CommonDTO DelCompany(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("�����̱�Ų���Ϊ��");
			return returnDTO;
		}
		//���ݱ���
		try {
			trans.begin();						
			op.removeObj(Company.class, dto.getCompanyid());
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}	
		returnDTO.setSuccess("true");
		returnDTO.setMsg("ɾ����������Ϣ�ɹ�!");
		return returnDTO;	
	}

	public String ViewCompany(String msgdata) throws ApplicationException {
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//Ϊ�յ�����²�����鿴����
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			dto.setCompanyid(new Integer(-999999));
		}
		return SearchCompany(dto);
	}
}
