package com.lbs.apps.emptybox.dao;

import java.sql.Timestamp;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.system.dao.SeqBPO;
import com.lbs.apps.system.po.Boxinfo;
import com.lbs.apps.system.po.Emptyboxinpark;
import com.lbs.apps.system.po.Emptyboxinplace;
import com.lbs.apps.system.po.Emptyboxoutpark;
import com.lbs.apps.system.po.Emptyboxoutplace;
import com.lbs.apps.system.po.Placeboxinfo;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class EmptyboxBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	/**
	 * ������԰
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO EmptyBoxInPark(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("��װ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("��װ�����Ͳ���Ϊ��");
			return returnDTO;
		}
		if (dto.getInparktime()==null || dto.getInparktime().equals("")){
			returnDTO.setMsg("��԰ʱ�䲻��Ϊ��");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("���������̲���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //���ڱ��
		Boxinfo boxinfo=null;
		try {
			boxinfo=(Boxinfo)op.retrieveObj(Boxinfo.class, dto.getBoxid());
			boxinfo.setBoxtype(dto.getBoxtype());
			boxinfo.setCompanyid(dto.getCompanyid());
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			lb_exists=false;
			boxinfo=new Boxinfo();
			ClassHelper.copyProperties(dto, boxinfo);
			boxinfo.setIsvalid("101");
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
			boxinfo.setCreateddate(now);
		}
		boxinfo.setPostype("101");  //��԰
		
		//������԰��¼��
		Emptyboxinpark emptyboxinpark=new Emptyboxinpark();
		ClassHelper.copyProperties(dto, emptyboxinpark);
		emptyboxinpark.setEmptyboxinparkid(SeqBPO.GetSequence("EMPTYBOXINPARKID"));
		emptyboxinpark.setInparktime(now);
		emptyboxinpark.setIsvalid("101");
		emptyboxinpark.setModifyby(dto.getCancelby());
		emptyboxinpark.setModifydate(now);
		emptyboxinpark.setCreateddate(now);		
		//���ݱ���
		try {
			trans.begin();
			if (lb_exists){
				op.updateObj(boxinfo);
			}else{
				op.saveObj(boxinfo);
			}
			op.saveObj(emptyboxinpark);								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������԰�����:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������԰�����:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("������԰�����ɹ�!");
		return returnDTO;
	}

	
	public CommonDTO EmptyBoxInPlace(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("��װ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("��װ�����Ͳ���Ϊ��");
			return returnDTO;
		}
		if (dto.getInplacetime()==null || dto.getInplacetime().equals("")){
			returnDTO.setMsg("�볡ʱ�䲻��Ϊ��");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("���������̲���Ϊ��");
			return returnDTO;
		}
		if (dto.getPlaceboxid()==null || dto.getPlaceboxid().equals("")){
			returnDTO.setMsg("�ѳ���λ��Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //���ڱ��
		Boxinfo boxinfo=null;
		try {
			boxinfo=(Boxinfo)op.retrieveObj(Boxinfo.class, dto.getBoxid());
			boxinfo.setBoxtype(dto.getBoxtype());
			boxinfo.setCompanyid(dto.getCompanyid());
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			lb_exists=false;
			boxinfo=new Boxinfo();
			ClassHelper.copyProperties(dto, boxinfo);
			boxinfo.setIsvalid("101");
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
			boxinfo.setCreateddate(now);
		}
		boxinfo.setPostype("102");  //�ڳ�
		
		//������԰��¼��
		Emptyboxinplace emptyboxinplace=new Emptyboxinplace();
		ClassHelper.copyProperties(dto, emptyboxinplace);
		emptyboxinplace.setEmptyboxinplaceid(SeqBPO.GetSequence("EMPTYBOXINPLACEID"));
		emptyboxinplace.setInplacetime(now);
		emptyboxinplace.setIsvalid("101");
		emptyboxinplace.setModifyby(dto.getCancelby());
		emptyboxinplace.setModifydate(now);
		emptyboxinplace.setCreateddate(now);	
		
		//�ѳ���λ��¼
		Placeboxinfo placeboxinfo=null;
		try {
			placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, dto.getPlaceboxid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�ѳ���λ��¼������,�ѳ���λ���:"+dto.getPlaceboxid());
			return returnDTO;
		}
		placeboxinfo.setStatusstartdate(now);
		placeboxinfo.setBoxid(dto.getBoxid());
		placeboxinfo.setPlaceboxstatus("104");  //ռ��
		placeboxinfo.setIsempty("101"); //�����볡
		placeboxinfo.setModifyby(dto.getCancelby());
		placeboxinfo.setModifydate(now);
		
		//���ݱ���
		try {
			trans.begin();
			if (lb_exists){
				op.updateObj(boxinfo);
			}else{
				op.saveObj(boxinfo);
			}
			op.saveObj(emptyboxinplace);	
			op.updateObj(placeboxinfo);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�����볡�����:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�����볡�����:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�����볡�����ɹ�!");
		return returnDTO;
	}

	
	public CommonDTO EmptyBoxOutPlace(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("��װ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("��װ�����Ͳ���Ϊ��");
			return returnDTO;
		}
		if (dto.getOutplacetime()==null || dto.getOutplacetime().equals("")){
			returnDTO.setMsg("����ʱ�䲻��Ϊ��");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("���������̲���Ϊ��");
			return returnDTO;
		}
		if (dto.getPlaceboxid()==null || dto.getPlaceboxid().equals("")){
			returnDTO.setMsg("�ѳ���λ��Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //���ڱ��
		Boxinfo boxinfo=null;
		try {
			boxinfo=(Boxinfo)op.retrieveObj(Boxinfo.class, dto.getBoxid());
			boxinfo.setBoxtype(dto.getBoxtype());
			boxinfo.setCompanyid(dto.getCompanyid());
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			lb_exists=false;
			boxinfo=new Boxinfo();
			ClassHelper.copyProperties(dto, boxinfo);
			boxinfo.setIsvalid("101");
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
			boxinfo.setCreateddate(now);
		}
		boxinfo.setPostype("101");  //��԰
		
		//���������¼��
		Emptyboxoutplace emptyboxoutplace=new Emptyboxoutplace();
		ClassHelper.copyProperties(dto, emptyboxoutplace);
		emptyboxoutplace.setEmptyboxoutplaceid(SeqBPO.GetSequence("EMPTYBOXOUTPLACEID"));
		emptyboxoutplace.setInplacetime(now);
		emptyboxoutplace.setIsvalid("101");
		emptyboxoutplace.setModifyby(dto.getCancelby());
		emptyboxoutplace.setModifydate(now);
		emptyboxoutplace.setCreateddate(now);	
		
		//�ѳ���λ��¼
		Placeboxinfo placeboxinfo=null;
		try {
			placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, dto.getPlaceboxid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�ѳ���λ��¼������,�ѳ���λ���:"+dto.getPlaceboxid());
			return returnDTO;
		}
		placeboxinfo.setPlaceboxstatus("101");  //����
		placeboxinfo.setStatusenddate(now);
		placeboxinfo.setModifyby(dto.getCancelby());
		placeboxinfo.setModifydate(now);
				
		//���ݱ���
		try {
			trans.begin();
			if (lb_exists){
				op.updateObj(boxinfo);
			}else{
				op.saveObj(boxinfo);
			}
			op.saveObj(emptyboxoutplace);	
			op.updateObj(placeboxinfo);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("������������ɹ�!");
		return returnDTO;
	}

	
	public CommonDTO EmptyBoxOutPark(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("��װ���Ų���Ϊ��");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("��װ�����Ͳ���Ϊ��");
			return returnDTO;
		}
		if (dto.getOutparktime()==null || dto.getOutparktime().equals("")){
			returnDTO.setMsg("��԰ʱ�䲻��Ϊ��");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("���������̲���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //���ڱ��
		Boxinfo boxinfo=null;
		try {
			boxinfo=(Boxinfo)op.retrieveObj(Boxinfo.class, dto.getBoxid());
			boxinfo.setBoxtype(dto.getBoxtype());
			boxinfo.setCompanyid(dto.getCompanyid());
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			lb_exists=false;
			boxinfo=new Boxinfo();
			ClassHelper.copyProperties(dto, boxinfo);
			boxinfo.setIsvalid("101");
			boxinfo.setModifyby(dto.getCancelby());
			boxinfo.setModifydate(now);
			boxinfo.setCreateddate(now);
		}
		boxinfo.setPostype("103");  //��԰
		
		//�����԰��¼��
		Emptyboxoutpark emptyboxoutpark=new Emptyboxoutpark();
		ClassHelper.copyProperties(dto, emptyboxoutpark);
		emptyboxoutpark.setEmptyboxoutparkid(SeqBPO.GetSequence("EMPTYBOXOUTPARKID"));
		emptyboxoutpark.setInparktime(now);
		emptyboxoutpark.setIsvalid("101");
		emptyboxoutpark.setModifyby(dto.getCancelby());
		emptyboxoutpark.setModifydate(now);
		emptyboxoutpark.setCreateddate(now);		
		//���ݱ���
		try {
			trans.begin();
			if (lb_exists){
				op.updateObj(boxinfo);
			}else{
				op.saveObj(boxinfo);
			}
			op.saveObj(emptyboxoutpark);								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�����԰�����:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�����԰�����:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�����԰�����ɹ�!");
		return returnDTO;
	}

	
	public String SearchEmptyBoxWarm(String msgdata)
			throws ApplicationException {
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select a.boxid,boxtype,postype,b.placeboxid,statusstartdate,DATEDIFF(day,statusstartdate,getdate()) indays,dbo.sf_get_warnlevel(DATEDIFF(day,statusstartdate,getdate())) warnlevel,a.companyid,dbo.sf_get_companyname(a.companyid) companyname")
		.append(" from boxinfo a,placeboxinfo b where a.boxid=b.boxid and b.isempty='101' and b.placeboxstatus='104' and b.statusstartdate+dbo.sf_get_minwarndays('')<getdate()");
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and b.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getWarnlevel()==null || dto.getWarnlevel().equals(""))){
			sb.append(" and dbo.sf_get_warnlevel(DATEDIFF(day,statusstartdate,getdate()))='").append(dto.getWarnlevel()).append("'");
		}
		if (!(dto.getCompanyid()==null || dto.getCompanyid().intValue()==0)){
			sb.append(" and b.companyid=").append(dto.getCompanyid());
		}
		return sb.toString();
	}
}
