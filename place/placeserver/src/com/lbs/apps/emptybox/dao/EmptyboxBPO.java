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
	 * 空箱入园
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
			returnDTO.setMsg("集装箱编号不能为空");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("集装箱类型不能为空");
			return returnDTO;
		}
		if (dto.getInparktime()==null || dto.getInparktime().equals("")){
			returnDTO.setMsg("入园时间不能为空");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("所属承运商不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //存在标记
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
		boxinfo.setPostype("101");  //在园
		
		//空箱入园记录表
		Emptyboxinpark emptyboxinpark=new Emptyboxinpark();
		ClassHelper.copyProperties(dto, emptyboxinpark);
		emptyboxinpark.setEmptyboxinparkid(SeqBPO.GetSequence("EMPTYBOXINPARKID"));
		emptyboxinpark.setInparktime(now);
		emptyboxinpark.setIsvalid("101");
		emptyboxinpark.setModifyby(dto.getCancelby());
		emptyboxinpark.setModifydate(now);
		emptyboxinpark.setCreateddate(now);		
		//数据保存
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱入园，箱号:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱入园，箱号:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("空箱入园操作成功!");
		return returnDTO;
	}

	
	public CommonDTO EmptyBoxInPlace(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("集装箱编号不能为空");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("集装箱类型不能为空");
			return returnDTO;
		}
		if (dto.getInplacetime()==null || dto.getInplacetime().equals("")){
			returnDTO.setMsg("入场时间不能为空");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("所属承运商不能为空");
			return returnDTO;
		}
		if (dto.getPlaceboxid()==null || dto.getPlaceboxid().equals("")){
			returnDTO.setMsg("堆场箱位编号不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //存在标记
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
		boxinfo.setPostype("102");  //在场
		
		//空箱入园记录表
		Emptyboxinplace emptyboxinplace=new Emptyboxinplace();
		ClassHelper.copyProperties(dto, emptyboxinplace);
		emptyboxinplace.setEmptyboxinplaceid(SeqBPO.GetSequence("EMPTYBOXINPLACEID"));
		emptyboxinplace.setInplacetime(now);
		emptyboxinplace.setIsvalid("101");
		emptyboxinplace.setModifyby(dto.getCancelby());
		emptyboxinplace.setModifydate(now);
		emptyboxinplace.setCreateddate(now);	
		
		//堆场箱位记录
		Placeboxinfo placeboxinfo=null;
		try {
			placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, dto.getPlaceboxid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("堆场箱位记录不存在,堆场箱位编号:"+dto.getPlaceboxid());
			return returnDTO;
		}
		placeboxinfo.setStatusstartdate(now);
		placeboxinfo.setBoxid(dto.getBoxid());
		placeboxinfo.setPlaceboxstatus("104");  //占用
		placeboxinfo.setIsempty("101"); //空箱入场
		placeboxinfo.setModifyby(dto.getCancelby());
		placeboxinfo.setModifydate(now);
		
		//数据保存
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱入场，箱号:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱入场，箱号:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("空箱入场操作成功!");
		return returnDTO;
	}

	
	public CommonDTO EmptyBoxOutPlace(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("集装箱编号不能为空");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("集装箱类型不能为空");
			return returnDTO;
		}
		if (dto.getOutplacetime()==null || dto.getOutplacetime().equals("")){
			returnDTO.setMsg("出场时间不能为空");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("所属承运商不能为空");
			return returnDTO;
		}
		if (dto.getPlaceboxid()==null || dto.getPlaceboxid().equals("")){
			returnDTO.setMsg("堆场箱位编号不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //存在标记
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
		boxinfo.setPostype("101");  //在园
		
		//空箱出场记录表
		Emptyboxoutplace emptyboxoutplace=new Emptyboxoutplace();
		ClassHelper.copyProperties(dto, emptyboxoutplace);
		emptyboxoutplace.setEmptyboxoutplaceid(SeqBPO.GetSequence("EMPTYBOXOUTPLACEID"));
		emptyboxoutplace.setInplacetime(now);
		emptyboxoutplace.setIsvalid("101");
		emptyboxoutplace.setModifyby(dto.getCancelby());
		emptyboxoutplace.setModifydate(now);
		emptyboxoutplace.setCreateddate(now);	
		
		//堆场箱位记录
		Placeboxinfo placeboxinfo=null;
		try {
			placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, dto.getPlaceboxid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("堆场箱位记录不存在,堆场箱位编号:"+dto.getPlaceboxid());
			return returnDTO;
		}
		placeboxinfo.setPlaceboxstatus("101");  //空闲
		placeboxinfo.setStatusenddate(now);
		placeboxinfo.setModifyby(dto.getCancelby());
		placeboxinfo.setModifydate(now);
				
		//数据保存
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱出场，箱号:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱出场，箱号:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("空箱出场操作成功!");
		return returnDTO;
	}

	
	public CommonDTO EmptyBoxOutPark(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		EmptyboxDTO dto=new EmptyboxDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBoxid()==null || dto.getBoxid().equals("")){
			returnDTO.setMsg("集装箱编号不能为空");
			return returnDTO;
		}
		if (dto.getBoxtype()==null || dto.getBoxtype().equals("")){
			returnDTO.setMsg("集装箱类型不能为空");
			return returnDTO;
		}
		if (dto.getOutparktime()==null || dto.getOutparktime().equals("")){
			returnDTO.setMsg("出园时间不能为空");
			return returnDTO;
		}
		if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){
			returnDTO.setMsg("所属承运商不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		boolean lb_exists=true;  //存在标记
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
		boxinfo.setPostype("103");  //出园
		
		//空箱出园记录表
		Emptyboxoutpark emptyboxoutpark=new Emptyboxoutpark();
		ClassHelper.copyProperties(dto, emptyboxoutpark);
		emptyboxoutpark.setEmptyboxoutparkid(SeqBPO.GetSequence("EMPTYBOXOUTPARKID"));
		emptyboxoutpark.setInparktime(now);
		emptyboxoutpark.setIsvalid("101");
		emptyboxoutpark.setModifyby(dto.getCancelby());
		emptyboxoutpark.setModifydate(now);
		emptyboxoutpark.setCreateddate(now);		
		//数据保存
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱出园，箱号:"+dto.getBoxid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"空箱出园，箱号:"+dto.getBoxid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("空箱出园操作成功!");
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
