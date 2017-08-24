package com.lbs.apps.basic.dao;

import java.sql.Timestamp;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.system.po.Boxinfo;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class BoxBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	//��ȡ��ѯ��װ���SQL
	public String SearchBox(String msgdata)throws ApplicationException{
		BoxDTO dto=new BoxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchBox(dto);
		
	}
	
	//��ȡ��ѯ��װ���SQL
	private String SearchBox(BoxDTO dto)throws ApplicationException{
		StringBuffer sb=new StringBuffer("select boxid,boxtype,postype,isvalid,memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname,orderid,weighid,memo,createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate,placeboxid from boxinfo where 1=1");
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getBoxtype()==null || dto.getBoxtype().equals(""))){
			sb.append(" and boxtype='").append(dto.getBoxtype()).append("'");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getPostype()==null || dto.getPostype().equals(""))){
			sb.append(" and postype='").append(dto.getPostype()).append("'");
		}
		if (!(dto.getMemberid()==null || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null || dto.getCompanyid().intValue()==0)){
			sb.append(" and companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getMemo()==null || dto.getMemo().equals(""))){
			sb.append(" and memo like '%").append(dto.getMemo()).append("%'");
		}
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		return sb.toString();
		
	}
	//���Ӽ�װ��
	public CommonDTO AddBox(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		BoxDTO dto=new BoxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("��װ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("��װ�����Ͳ���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		String hql="select count(*) from Boxinfo where boxid='"+dto.getBoxid()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("��װ�����Ѵ���");
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		Boxinfo boxinfo=new Boxinfo();
		ClassHelper.copyProperties(dto, boxinfo);
		boxinfo.setIsvalid("101");
		boxinfo.setCreateddate(now);
		boxinfo.setModifydate(now);
		boxinfo.setModifyby(dto.getCreatedby());
		
		//���ݱ���
		try {
			trans.begin();						
			op.saveObj(boxinfo);
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
		returnDTO.setMsg("���漯װ����Ϣ�ɹ�!");
		return returnDTO;
	}
	//�޸ļ�װ��
	public CommonDTO ModBox(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		BoxDTO dto=new BoxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("��װ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("��װ�����Ͳ���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		Boxinfo boxinfo=null;
		try {
			boxinfo = (Boxinfo)op.retrieveObj(Boxinfo.class, dto.getBoxid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("��װ���¼������,��װ����:"+dto.getBoxid());
			return returnDTO;
		}
		Integer li_createdby=boxinfo.getCreatedby();
		Timestamp ld_createddate=boxinfo.getCreateddate();
		Integer li_org=boxinfo.getCreatedorg();		
		ClassHelper.copyProperties(dto, boxinfo);
		boxinfo.setCreatedby(li_createdby);
		boxinfo.setCreateddate(ld_createddate);
		boxinfo.setCreatedorg(li_org);
		boxinfo.setModifydate(now);
		boxinfo.setModifyby(dto.getCreatedby());
		
		//���ݱ���
		try {
			trans.begin();						
			op.updateObj(boxinfo);
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
		returnDTO.setMsg("�޸ļ�װ����Ϣ�ɹ�!");
		return returnDTO;
	}
	//ɾ����װ��
	public CommonDTO DelBox(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		BoxDTO dto=new BoxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("��װ���Ų���Ϊ��");
			return returnDTO;
		}
		//���ݱ���
		try {
			trans.begin();						
			op.removeObj(Boxinfo.class, dto.getBoxid());
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
		returnDTO.setMsg("ɾ����װ����Ϣ�ɹ�!");
		return returnDTO;	
	}
	//��ȡ�鿴��װ���SQL
	public String ViewBox(String msgdata)throws ApplicationException{
		BoxDTO dto=new BoxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			dto.setBoxid("-999999");
		}
		return SearchBox(dto);
		
	}
	
			
	//��ȡ��ѯ��װ������SQL
	public String SearchBoxMana(String msgdata)throws ApplicationException{
		BoxDTO dto=new BoxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchBox(dto);
	}
}
