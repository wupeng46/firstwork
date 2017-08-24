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
	
	// 判断查询条件是否OK，成功返回0，否则返回异常原因
		public String CheckSql_Where(String msgdata) {
			String ls_return = "0";
			if (msgdata == null || msgdata.equals("")) { // 没有传递参数
				ls_return = "请求参数异常";
			} else {
				try {
					JSONObject obj = JSONObject.fromObject(msgdata); // 解析JSON数据包
					//if (!obj.has("companyid")) {
					//	ls_return = "数据包中未包含companyid参数信息";
					//}
					//String ls_companyid = (String) obj.get("companyid");
					//if (ls_companyid == null || ls_companyid.equals("")
					//		|| ls_companyid.equals("0"))
					//	return "承运商编码不能为空";
				} catch (Exception e) {
					ls_return = "msgdata数据字符串非法";
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
			returnDTO.setMsg("承运商名称不能为空");
			return returnDTO;
		}
		if ((dto.getTelephone()==null || dto.getTelephone().equals("") ) && (dto.getMobilephone()==null || dto.getMobilephone().equals(""))){
			returnDTO.setMsg("承运商电话不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("操作人员不能为空");
			return returnDTO;
		}
		String hql="select count(*) from Company where companyname='"+dto.getCompanyname()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("该承运商名称已存在");
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		Company company=new Company();
		ClassHelper.copyProperties(dto, company);
		String ls_seq=SeqBPO.GetSequence("SEQ_COMPANYID");  //获取序列号
		company.setCompanyid(new Integer(ls_seq));
		company.setIsvalid("101");
		company.setCreateddate(now);
		company.setModifydate(now);
		company.setModifyby(dto.getCreatedby());
		//数据保存
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
		returnDTO.setMsg("保存承运商信息成功!");
		return returnDTO;
	}

	public CommonDTO ModCompany(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("承运商编号不能为空");
			return returnDTO;
		}
		if (dto.getCompanyname()==null || dto.getCompanyname().equals("")){
			returnDTO.setMsg("承运商名称不能为空");
			return returnDTO;
		}
		if ((dto.getTelephone()==null || dto.getTelephone().equals("") ) && (dto.getMobilephone()==null || dto.getMobilephone().equals(""))){
			returnDTO.setMsg("承运商电话不能为空");
			return returnDTO;
		}
		if (dto.getIsvalid()==null || dto.getIsvalid().equals("")){
			returnDTO.setMsg("是否有效标记不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("操作人员不能为空");
			return returnDTO;
		}
		String hql="select count(*) from Company where companyname='"+dto.getCompanyname()+"' and companyid!="+dto.getCompanyid();
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("该承运商名称已存在");
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
			returnDTO.setMsg("承运商记录不存在,承运商编号:"+dto.getCompanyid());
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
		//数据保存
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
		returnDTO.setMsg("修改承运商信息成功!");
		return returnDTO;		
	}

	public CommonDTO DelCompany(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("承运商编号不能为空");
			return returnDTO;
		}
		//数据保存
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
		returnDTO.setMsg("删除承运商信息成功!");
		return returnDTO;	
	}

	public String ViewCompany(String msgdata) throws ApplicationException {
		CompanyDTO dto=new CompanyDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//为空的情况下不允许查看详情
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			dto.setCompanyid(new Integer(-999999));
		}
		return SearchCompany(dto);
	}
}
