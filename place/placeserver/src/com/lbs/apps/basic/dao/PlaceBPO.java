package com.lbs.apps.basic.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.system.po.Placeboxinfo;
import com.lbs.apps.system.po.Placeinfo;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class PlaceBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	//��ȡ��ѯ�ѳ���SQL
	public String SearchPlace(String msgdata)throws ApplicationException{
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchPlace(dto);	
	}
	
	private String SearchPlace(PlaceDTO dto)throws ApplicationException{
		StringBuffer sb=new StringBuffer("select placeid,prefix,xmax,ymax,levelnum,isvalid,memo,createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate from placeinfo where 1=1");
		if (!(dto.getPlaceid()==null || dto.getPlaceid().equals(""))){
			sb.append(" and placeid='").append(dto.getPlaceid()).append("'");
		}
		if (!(dto.getIsvalid()==null || dto.getIsvalid().equals(""))){
			sb.append(" and isvalid = '").append(dto.getIsvalid()).append("'");
		}
		if (!(dto.getLevelnum()==null || dto.getLevelnum().intValue()==0)){
			sb.append(" and levelnum = ").append(dto.getLevelnum());
		}
		if (!(dto.getMemo()==null || dto.getMemo().equals(""))){
			sb.append(" and memo like '%").append(dto.getMemo()).append("%'");
		}
		return sb.toString();
	}
	//���Ӷѳ�
	public CommonDTO AddPlace(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getPlaceid()==null || dto.getPlaceid().equals("")){
			returnDTO.setMsg("�ѳ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getPrefix()==null || dto.getPrefix().equals("")){
			returnDTO.setMsg("ǰ׺�벻��Ϊ��");
			return returnDTO;
		}
		if (dto.getXmax()==null || dto.getXmax().intValue()==0){
			returnDTO.setMsg("X���ֵ����Ϊ��");
			return returnDTO;
		}
		if (dto.getYmax()==null || dto.getYmax().intValue()==0){
			returnDTO.setMsg("Y���ֵ����Ϊ��");
			return returnDTO;
		}
		if (dto.getLevelnum()==null || dto.getLevelnum().intValue()==0){
			returnDTO.setMsg("��������Ϊ��");
			return returnDTO;
		}
		if (dto.getMemo()==null || dto.getMemo().equals("")){
			returnDTO.setMsg("�ѳ�˵������Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		String hql="select count(*) from Placeinfo where placeid='"+dto.getPlaceid()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("�ѳ�����Ѵ���");
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		Placeinfo placeinfo=new Placeinfo();
		ClassHelper.copyProperties(dto, placeinfo);
		placeinfo.setIsvalid("101");
		placeinfo.setCreateddate(now);
		placeinfo.setModifydate(now);
		placeinfo.setModifyby(dto.getCreatedby());
		
		//���ݱ���
		try {
			trans.begin();						
			op.saveObj(placeinfo);
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
		returnDTO.setMsg("����ѳ���Ϣ�ɹ�!");
		return returnDTO;
	}
	//�޸Ķѳ�
	public CommonDTO ModPlace(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getPlaceid()==null || dto.getPlaceid().equals("")){
			returnDTO.setMsg("�ѳ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getPrefix()==null || dto.getPrefix().equals("")){
			returnDTO.setMsg("ǰ׺�벻��Ϊ��");
			return returnDTO;
		}
		if (dto.getXmax()==null || dto.getXmax().intValue()==0){
			returnDTO.setMsg("X���ֵ����Ϊ��");
			return returnDTO;
		}
		if (dto.getYmax()==null || dto.getYmax().intValue()==0){
			returnDTO.setMsg("Y���ֵ����Ϊ��");
			return returnDTO;
		}
		if (dto.getLevelnum()==null || dto.getLevelnum().intValue()==0){
			returnDTO.setMsg("��������Ϊ��");
			return returnDTO;
		}
		if (dto.getMemo()==null || dto.getMemo().equals("")){
			returnDTO.setMsg("�ѳ�˵������Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		Placeinfo placeinfo=null;
		try {
			placeinfo = (Placeinfo)op.retrieveObj(Placeinfo.class, dto.getPlaceid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�ѳ���¼������,��װ����:"+dto.getPlaceid());
			return returnDTO;
		}
		Integer li_createdby=placeinfo.getCreatedby();
		Timestamp ld_createddate=placeinfo.getCreateddate();
		Integer li_org=placeinfo.getCreatedorg();		
		ClassHelper.copyProperties(dto, placeinfo);
		placeinfo.setCreatedby(li_createdby);
		placeinfo.setCreateddate(ld_createddate);
		placeinfo.setCreatedorg(li_org);
		placeinfo.setModifydate(now);
		placeinfo.setModifyby(dto.getCreatedby());
		
		//���ݱ���
		try {
			trans.begin();						
			op.updateObj(placeinfo);
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
		returnDTO.setMsg("�޸Ķѳ���Ϣ�ɹ�!");
		return returnDTO;
	}
	//ɾ���ѳ�
	public CommonDTO DelPlace(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getPlaceid()==null || dto.getPlaceid().equals("")){
			returnDTO.setMsg("�ѳ���Ų���Ϊ��");
			return returnDTO;
		}
		//���ݱ���
		try {
			trans.begin();		
			op.removeObjs(" delete from Placeboxinfo where placeid='"+dto.getPlaceid()+"'");
			op.removeObj(Placeinfo.class, dto.getPlaceid());
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
		returnDTO.setMsg("ɾ���ѳ���Ϣ�ɹ�!");
		return returnDTO;	
	}
	//��ȡ�鿴�ѳ���SQL
	public String ViewPlace(String msgdata)throws ApplicationException{
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getPlaceid()==null || dto.getPlaceid().equals("")){
			dto.setPlaceid("-999999");
		}
		return SearchPlace(msgdata);
	}
	//���ɶѳ���λ���
	public CommonDTO BuildPlaceBoxNo(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getPlaceid()==null || dto.getPlaceid().equals("")){
			returnDTO.setMsg("�ѳ���Ų���Ϊ��");
			return returnDTO;
		}		
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		Placeinfo placeinfo=null;
		try {
			placeinfo = (Placeinfo)op.retrieveObj(Placeinfo.class, dto.getPlaceid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�ѳ���¼������,��װ����:"+dto.getPlaceid());
			return returnDTO;
		}
		if (placeinfo.getXmax().intValue()>99){
			returnDTO.setMsg("X���ֵ����99");
			return returnDTO;
		}
		if (placeinfo.getYmax().intValue()>99){
			returnDTO.setMsg("Y���ֵ����99");
			return returnDTO;
		}
		if (placeinfo.getLevelnum().intValue()>99){
			returnDTO.setMsg("�������ֵ����99");
			return returnDTO;
		}
		
		//ѭ�����ɶѳ���λ����
		List array_add_placeboxinfo=new ArrayList();
		String ls_placeboxno="";
		String ls_xno="";
		String ls_yno="";
		String ls_levelno="";	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		for (short i=1;i<=placeinfo.getXmax();i++){
			if (i<10){
				ls_xno="0"+i;
			}else{
				ls_xno=new Integer(i).toString();
			}
			for (short j=1;j<=placeinfo.getYmax();j++){
				if (j<10){
					ls_yno="0"+j;
				}else{
					ls_yno=new Integer(j).toString();
				}
				for (short k=1;k<=placeinfo.getLevelnum();k++){
					if (k<10){
						ls_levelno="0"+k;
					}else{
						ls_levelno=new Integer(k).toString();;
					}
					ls_placeboxno=placeinfo.getPrefix()+ls_xno+ls_yno+ls_levelno;
					Placeboxinfo placeboxinfo=new Placeboxinfo();
					placeboxinfo.setPlaceboxid(ls_placeboxno);
					placeboxinfo.setPlaceid(dto.getPlaceid());
					placeboxinfo.setXnum(i);
					placeboxinfo.setYnum(j);
					placeboxinfo.setLevelnum(k);
					placeboxinfo.setPlaceboxstatus("101");  //����
					placeboxinfo.setIsvalid("101");
					placeboxinfo.setStatusstartdate(now);
					placeboxinfo.setCreatedby(dto.getCreatedby());
					placeboxinfo.setCreateddate(now);
					placeboxinfo.setModifyby(dto.getCreatedby());
					placeboxinfo.setModifydate(now);
					
					array_add_placeboxinfo.add(placeboxinfo);
				}				
			}			
		}
		
		//���ݱ���
		try {
			trans.begin();						
			op.removeObjs(" delete from Placeboxinfo where placeid='"+dto.getPlaceid()+"'");
			op.saveObjs(array_add_placeboxinfo.toArray());
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
		returnDTO.setMsg("���ɶѳ���λ�ɹ�!");
		return returnDTO;	
	}
	//��ȡ��ѯ�ѳ������SQL
	public String SearchPlaceMana(String msgdata)throws ApplicationException{
		PlaceDTO dto = new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchPlaceMana(dto);
	}
	
	private String SearchPlaceMana(PlaceDTO dto)throws ApplicationException{
		StringBuffer sb=new StringBuffer("select placeboxid,placeid,xnum,ynum,levelnum,placeboxstatus,isvalid,orderid,weighid,boxid,memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname,convert(varchar,statusstartdate,120) statusstartdate,convert(varchar,statusenddate,120) statusenddate,memo,createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate from placeboxinfo where 1=1");
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getLevelnum()==null || dto.getLevelnum().intValue()==0)){
			sb.append(" and levelnum = ").append(dto.getLevelnum());
		}
		if (!(dto.getPlaceboxstatus()==null || dto.getPlaceboxstatus().equals(""))){
			sb.append(" and placeboxstatus='").append(dto.getPlaceboxstatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getMemberid()==null || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid = ").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null || dto.getCompanyid().intValue()==0)){
			sb.append(" and companyid = ").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}		
		return sb.toString();
	}
	//�ѳ���λ����
	public CommonDTO LockPlaceBox(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getPlaceboxid()==null || dto.getPlaceboxid().equals("")){
			returnDTO.setMsg("�ѳ���λ��Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		Placeboxinfo placeboxinfo=null;
		try {
			placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, dto.getPlaceboxid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�ѳ���λ���"+dto.getPlaceboxid()+"������");
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		placeboxinfo.setPlaceboxstatus("103");  //101���С�102���ƻ�������103 �ֹ�������104:ռ��
		placeboxinfo.setModifyby(dto.getCreatedby());
		placeboxinfo.setModifydate(now);
		//���ݱ���
		try {
			trans.begin();						
			op.updateObj(placeboxinfo);
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
		returnDTO.setMsg("�ѳ���λ�����ɹ�!");
		return returnDTO;
	}
	//�ѳ���λ����
	public CommonDTO UnLockPlaceBox(String msgdata)throws ApplicationException{
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		PlaceDTO dto=new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getPlaceboxid()==null || dto.getPlaceboxid().equals("")){
			returnDTO.setMsg("�ѳ���λ��Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("������Ա����Ϊ��");
			return returnDTO;
		}
		Placeboxinfo placeboxinfo=null;
		try {
			placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, dto.getPlaceboxid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�ѳ���λ���"+dto.getPlaceboxid()+"������");
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		placeboxinfo.setPlaceboxstatus("101");  //101���С�102���ƻ�������103 �ֹ�������104:ռ��
		placeboxinfo.setModifyby(dto.getCreatedby());
		placeboxinfo.setModifydate(now);
		//���ݱ���
		try {
			trans.begin();						
			op.updateObj(placeboxinfo);
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
		returnDTO.setMsg("�ѳ���λ�����ɹ�!");
		return returnDTO;
	}
	//��ȡ��ѯ�ѳ���λƽ��ͼ��SQL
	public String SearchPlacePlan(String msgdata)throws ApplicationException{
		PlaceDTO dto = new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select placeboxid,xnum,ynum,levelnum,placeboxstatus,isvalid from placeboxinfo where 1=1");
		if (!(dto.getPlaceid()==null || dto.getPlaceid().equals(""))){
			sb.append(" and placeid='").append(dto.getPlaceid()).append("'");
		}
		if (!(dto.getLevelnum()==null || dto.getLevelnum().intValue()==0)){
			sb.append(" and levelnum=").append(dto.getLevelnum());
		}
		sb.append(" order by placeboxid");
		return sb.toString();
	}
	
	//��ȡ��ѯ�ѳ��������ʹ��״̬��SQL
	public String SearchPlaceLevelStatus(String msgdata)throws ApplicationException{
		PlaceDTO dto = new PlaceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select levelnum,count(*) emptynum from placeboxinfo where placeboxstatus='101'");
		if (!(dto.getPlaceid()==null || dto.getPlaceid().equals(""))){
			sb.append(" and placeid='").append(dto.getPlaceid()).append("'");
		}		
		sb.append(" group by levelnum order by levelnum");
		return sb.toString();
	}

}
