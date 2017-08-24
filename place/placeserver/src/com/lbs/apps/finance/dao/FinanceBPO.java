package com.lbs.apps.finance.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.common.NoticeUtil;
import com.lbs.apps.system.dao.SeqBPO;
import com.lbs.apps.system.po.Payable;
import com.lbs.apps.system.po.Payableapply;
import com.lbs.apps.system.po.Payableconfirm;
import com.lbs.apps.system.po.Receivable;
import com.lbs.apps.system.po.Receivableapply;
import com.lbs.apps.system.po.Receivableconfirm;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class FinanceBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();

	public String SearchReceivableToApply(FinanceDTO dto)throws ApplicationException {
		StringBuffer sb=new StringBuffer("select receivableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,placeboxid,netweight,price,amount")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname  from receivable where 1=1");
		
		if (!(dto.getReceivablestatus()==null || dto.getReceivablestatus().equals(""))){
			sb.append(" and receivablestatus='").append(dto.getReceivablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}
	
	/**
	 * 查询待提报的过磅单记录
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchReceivableToApply(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setReceivablestatus("100");  //默认只查询未提报的待提报记录
		return SearchReceivableToApply(dto);
		
	}

	/**
	 * 提报应收报结单 
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO ReceivableApply(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getReceivableid()==null || dto.getReceivableid().equals("")){
			returnDTO.setMsg("应收编号不能为空");
			return returnDTO;
		}	
		if (dto.getPrice()==null){
			returnDTO.setMsg("单价不能为空");
			return returnDTO;
		}	
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_receivableid=dto.getReceivableid().split(",");  //分割应收编号
		String[] array_price=dto.getPrice().split(",");  //分割单价
		if (array_receivableid.length!=array_price.length){
			returnDTO.setMsg("选中的应收编号与单价数量不匹配");
			return returnDTO;
		}
		String ls_applybatch=SeqBPO.GetSequence("SEQ_APPLYBATCH");//提交批次号
		String ls_receivableid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_receivable_mod=new ArrayList();
		List array_receivableapply_add=new ArrayList();
		for (int i=0;i<array_receivableid.length;i++){
			String ls_receivableid=array_receivableid[i];  //获取应收编号
			String ls_price=array_price[i];  //获取单价
			ls_receivableid_temp=ls_receivableid_temp+ls_receivableid+",";
			Receivable receivable=null;
			try {
				receivable=(Receivable)op.retrieveObj(Receivable.class, ls_receivableid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应收记录不存在,应收编号:"+ls_receivableid);
				return returnDTO;
			}
			if (!receivable.getReceivablestatus().equals("100")){
				returnDTO.setMsg("应收记录已经提报或确认,不允许重复提报,应收编号:"+ls_receivableid);
				return returnDTO;
			}
			
			receivable.setPrice(new Double(ls_price));
			receivable.setAmount(receivable.getNetweight()*new Double(ls_price));
			receivable.setReceivablestatus("101");  //设置为已提报
			receivable.setModifyby(dto.getCreatedby());
			receivable.setModifydate(now);
			
			Receivableapply receivableapply=new Receivableapply();
			ClassHelper.copyProperties(receivable, receivableapply);
			receivableapply.setReceivableapplyid(SeqBPO.GetSequence("RECEIVABLEAPPLYID"));
			receivableapply.setReceivableid(ls_receivableid);
			receivableapply.setCreatedby(dto.getCancelby());
			receivableapply.setCreateddate(now);
			receivableapply.setApplytime(now);
			receivableapply.setApplybatch(ls_applybatch);
			receivableapply.setIsvalid("101");
			
			array_receivable_mod.add(receivable);
			array_receivableapply_add.add(receivableapply);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_receivable_mod.toArray());
			op.saveObjs(array_receivableapply_add.toArray());								
			trans.commit();
			NoticeUtil.Noticemsg("103", dto.getCreatedby(), now);
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"提报应收报结单,应收编号:"+ls_receivableid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"提报应收报结单,应收编号:"+ls_receivableid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("提报应收报结单成功!");
		return returnDTO;
		
	}


	public String SearchReceivableToConform(FinanceDTO dto)throws ApplicationException {
		StringBuffer sb=new StringBuffer("select b.receivableapplyid,b.applybatch,a.receivableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,a.placeboxid,a.netweight,a.price,a.amount")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname ")
		.append(" from receivable a,receivableapply b where a.receivableid=b.receivableid and b.isvalid='101'");
		
		if (!(dto.getReceivablestatus()==null || dto.getReceivablestatus().equals(""))){
			sb.append(" and a.receivablestatus='").append(dto.getReceivablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and a.carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and a.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and a.memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and a.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and b.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and b.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}
	
	/**
	 * 查询已提报的过磅单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchReceivableToConform(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setReceivablestatus("101");  //默认只查询已提报记录
		return SearchReceivableToConform(dto);
	}


	/**
	 * 应收提报收款确认
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO ReceivableConfirm(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getReceivableapplyid()==null || dto.getReceivableapplyid().equals("")){
			returnDTO.setMsg("应收提报编号不能为空");
			return returnDTO;
		}
		
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_receivableapplyid=dto.getReceivableapplyid().split(",");  //分割应收提报编号
		String ls_receivableapplyid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_receivable_mod=new ArrayList();
		List array_receivableconfirm_add=new ArrayList();
		for (int i=0;i<array_receivableapplyid.length;i++){
			String ls_receivableapplyid=array_receivableapplyid[i];  //获取应收提报编号
			ls_receivableapplyid_temp=ls_receivableapplyid_temp+ls_receivableapplyid+",";
			
			Receivableapply receivableapply=null;
			try {
				receivableapply=(Receivableapply)op.retrieveObj(Receivableapply.class, ls_receivableapplyid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("应收提报记录不存在,应收提报编号:"+ls_receivableapplyid);
				return returnDTO;
			}
			
			Receivable receivable=null;
			try {
				receivable=(Receivable)op.retrieveObj(Receivable.class, receivableapply.getReceivableid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应收记录不存在,应收编号:"+receivableapply.getReceivableid());
				return returnDTO;
			}
			if (receivable.getReceivablestatus().equals("100")){
				returnDTO.setMsg("应收记录尚未提报,不能进行收款确认,应收编号:"+receivableapply.getReceivableid());
				return returnDTO;
			}
			if (receivable.getReceivablestatus().equals("102")){
				returnDTO.setMsg("应收记录已经确认,不能重复确认,应收编号:"+receivableapply.getReceivableid());
				return returnDTO;
			}
			receivable.setReceivablestatus("102");  //设置为已确认
			receivable.setModifyby(dto.getCreatedby());
			receivable.setModifydate(now);		
			
			
			Receivableconfirm receivableconfirm=new Receivableconfirm();
			ClassHelper.copyProperties(receivableapply, receivableconfirm);
			receivableconfirm.setReceivableconfirmid(SeqBPO.GetSequence("RECEIVABLECONFIRMID"));  //过长去掉前面的SEQ＿
			receivableconfirm.setConfirmtime(now);
			
			array_receivable_mod.add(receivable);
			array_receivableconfirm_add.add(receivableconfirm);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_receivable_mod.toArray());
			op.saveObjs(array_receivableconfirm_add.toArray());								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应收提报收款确认,应收提报编号:"+ls_receivableapplyid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应收提报收款确认,应收提报编号:"+ls_receivableapplyid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("应收提报收款确认成功!");
		return returnDTO;
	}


	/**
	 * 查询已提报的应收报结单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchReceivableApply(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setReceivablestatus("101");  //默认只查询已提报记录
		return SearchReceivableToConform(dto);
	}


	/**
	 * 取消提报
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO CancelReceivableApply(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getReceivableapplyid()==null || dto.getReceivableapplyid().equals("")){
			returnDTO.setMsg("应收提报编号不能为空");
			return returnDTO;
		}
		if (dto.getCancelreson()==null || dto.getCancelreson().equals("")){
			returnDTO.setMsg("取消原因不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_receivableapplyid=dto.getReceivableapplyid().split(",");  //分割应收提报编号
		String ls_receivableapplyid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_receivable_mod=new ArrayList();
		List array_receivableapply_mod=new ArrayList();
		for (int i=0;i<array_receivableapplyid.length;i++){
			String ls_receivableapplyid=array_receivableapplyid[i];  //获取应收提报编号
			ls_receivableapplyid_temp=ls_receivableapplyid_temp+ls_receivableapplyid+",";
			
			Receivableapply receivableapply=null;
			try {
				receivableapply=(Receivableapply)op.retrieveObj(Receivableapply.class, ls_receivableapplyid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("应收提报记录不存在,应收提报编号:"+ls_receivableapplyid);
				return returnDTO;
			}
			receivableapply.setCancelby(dto.getCreatedby());
			receivableapply.setCancelreson(dto.getCancelreson());
			receivableapply.setCanceltime(now);
			receivableapply.setIsvalid("102");  //无效
			
			Receivable receivable=null;
			try {
				receivable=(Receivable)op.retrieveObj(Receivable.class, receivableapply.getReceivableid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应收记录不存在,应收编号:"+receivableapply.getReceivableid());
				return returnDTO;
			}
			if (receivable.getReceivablestatus().equals("100")){
				returnDTO.setMsg("应收提报已经取消,不需要重复取消,应收编号:"+receivableapply.getReceivableid());
				return returnDTO;
			}	
			if (receivable.getReceivablestatus().equals("102")){
				returnDTO.setMsg("应收记录已经确认,不允许取消提报,应收编号:"+receivableapply.getReceivableid());
				return returnDTO;
			}			
			receivable.setReceivablestatus("100");  //设置为待提报
			receivable.setModifyby(dto.getCreatedby());
			receivable.setModifydate(now);		
			
			array_receivable_mod.add(receivable);
			array_receivableapply_mod.add(receivableapply);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_receivable_mod.toArray());
			op.updateObjs(array_receivableapply_mod.toArray());								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应收提报取消,应收提报编号:"+ls_receivableapplyid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应收提报收款取消,应收提报编号:"+ls_receivableapplyid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("应收提报取消成功!");
		return returnDTO;
	}
	
	public String SearchReceivableConfirm(FinanceDTO dto)throws ApplicationException {
		StringBuffer sb=new StringBuffer("select b.receivableconfirmid,b.receivableapplyid,b.applybatch,a.receivableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,a.placeboxid,a.netweight,a.price,a.amount")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname ")
		.append(" from receivable a,receivableconfirm b where a.receivableid=b.receivableid and b.isvalid='101'");
		
		if (!(dto.getReceivablestatus()==null || dto.getReceivablestatus().equals(""))){
			sb.append(" and a.receivablestatus='").append(dto.getReceivablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and a.carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and a.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and a.memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and a.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and b.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and b.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}


	/**
	 * 查询收款确认
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchReceivableConfirm(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setReceivablestatus("102");  //默认只查询已确认记录
		return SearchReceivableConfirm(dto);
	}

    /**
     * 取消收款确认
     * @param msgdata
     * @return
     * @throws ApplicationException
     */
	public CommonDTO CancelReceivableConfirm(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getReceivableconfirmid()==null || dto.getReceivableconfirmid().equals("")){
			returnDTO.setMsg("收款确认编号不能为空");
			return returnDTO;
		}
		if (dto.getCancelreson()==null || dto.getCancelreson().equals("")){
			returnDTO.setMsg("取消原因不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_receivableconfirmid=dto.getReceivableconfirmid().split(",");  //分割收款确认编号
		String ls_receivableconfirmid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_receivable_mod=new ArrayList();
		List array_receivableconfirm_mod=new ArrayList();
		for (int i=0;i<array_receivableconfirmid.length;i++){
			String ls_receivableconfirmid=array_receivableconfirmid[i];  //获取应收提报编号
			ls_receivableconfirmid_temp=ls_receivableconfirmid_temp+ls_receivableconfirmid+",";
			
			Receivableconfirm receivableconfirm=null;
			try {
				receivableconfirm=(Receivableconfirm)op.retrieveObj(Receivableconfirm.class, ls_receivableconfirmid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("应收提报记录不存在,应收提报编号:"+ls_receivableconfirmid);
				return returnDTO;
			}
			receivableconfirm.setCancelby(dto.getCreatedby());
			receivableconfirm.setCancelreson(dto.getCancelreson());
			receivableconfirm.setCanceltime(now);
			receivableconfirm.setIsvalid("102");  //无效
			
			Receivable receivable=null;
			try {
				receivable=(Receivable)op.retrieveObj(Receivable.class, receivableconfirm.getReceivableid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应收记录不存在,应收编号:"+receivableconfirm.getReceivableid());
				return returnDTO;
			}
			if (!receivable.getReceivablestatus().equals("102")){
				returnDTO.setMsg("应收记录状态异常,不允许取消收款确认,应收编号:"+receivableconfirm.getReceivableid());
				return returnDTO;
			}			
			receivable.setReceivablestatus("101");  //设置为已提报
			receivable.setModifyby(dto.getCreatedby());
			receivable.setModifydate(now);		
			
			array_receivable_mod.add(receivable);
			array_receivableconfirm_mod.add(receivableconfirm);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_receivable_mod.toArray());
			op.updateObjs(array_receivableconfirm_mod.toArray());								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"收款确认取消,编号:"+ls_receivableconfirmid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"收款确认取消,编号:"+ls_receivableconfirmid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("收款确认取消成功!");
		return returnDTO;
	}


	/**
	 * 收款报结单查询
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchReceivable(String msgdata) throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select a.receivableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,placeboxid,a.netweight,a.price,a.amount,a.receivablestatus")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname,convert(varchar,a.createddate,120) createddate, convert(varchar,b.confirmtime,120) confirmtime  from receivable a")
		.append(" left join receivableconfirm b on a.receivableid=b.receivableid and b.isvalid='101' where 1=1");
		
		if (!(dto.getReceivablestatus()==null || dto.getReceivablestatus().equals(""))){
			sb.append(" and a.receivablestatus='").append(dto.getReceivablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and a.carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and a.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and a.memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and a.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}
	
	public String SearchPayableToApply(FinanceDTO dto)throws ApplicationException {
		StringBuffer sb=new StringBuffer("select payableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,placeboxid,netweight,price,amount")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname  from Payable where 1=1");
		
		if (!(dto.getPayablestatus()==null || dto.getPayablestatus().equals(""))){
			sb.append(" and payablestatus='").append(dto.getPayablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}
	
	/**
	 * 查询待提报的过磅单记录
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchPayableToApply(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setPayablestatus("100");  //默认只查询未提报的待提报记录
		return SearchPayableToApply(dto);
		
	}

	/**
	 * 提报应付报结单 
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO PayableApply(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getPayableid()==null || dto.getPayableid().equals("")){
			returnDTO.setMsg("应付编号不能为空");
			return returnDTO;
		}	
		if (dto.getPrice()==null){
			returnDTO.setMsg("单价不能为空");
			return returnDTO;
		}	
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_Payableid=dto.getPayableid().split(",");  //分割应收编号
		String[] array_price=dto.getPrice().split(",");  //分割单价
		if (array_Payableid.length!=array_price.length){
			returnDTO.setMsg("选中的应收编号与单价数量不匹配");
			return returnDTO;
		}
		String ls_applybatch=SeqBPO.GetSequence("SEQ_APPLYBATCH");//提交批次号
		String ls_payableid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_Payable_mod=new ArrayList();
		List array_Payableapply_add=new ArrayList();
		for (int i=0;i<array_Payableid.length;i++){
			String ls_payableid=array_Payableid[i];  //获取应收编号
			String ls_price=array_price[i];  //获取单价
			ls_payableid_temp=ls_payableid_temp+ls_payableid+",";
			Payable Payable=null;
			try {
				Payable=(Payable)op.retrieveObj(Payable.class, ls_payableid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应收记录不存在,应收编号:"+ls_payableid);
				return returnDTO;
			}
			if (!Payable.getPayablestatus().equals("100")){
				returnDTO.setMsg("应付记录已经提报或确认,不允许重复提报,应收编号:"+ls_payableid);
				return returnDTO;
			}
			
			Payable.setPrice(new Double(ls_price));
			Payable.setAmount(Payable.getNetweight()*new Double(ls_price));
			Payable.setPayablestatus("101");  //设置为已提报
			Payable.setModifyby(dto.getCreatedby());
			Payable.setModifydate(now);
			
			Payableapply payableapply=new Payableapply();
			ClassHelper.copyProperties(Payable, payableapply);
			payableapply.setPayableapplyid(SeqBPO.GetSequence("PAYABLEAPPLYID"));
			payableapply.setPayableid(ls_payableid);
			payableapply.setCreatedby(dto.getCancelby());
			payableapply.setCreateddate(now);
			payableapply.setApplytime(now);
			payableapply.setApplybatch(ls_applybatch);
			payableapply.setIsvalid("101");
			
			array_Payable_mod.add(Payable);
			array_Payableapply_add.add(payableapply);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_Payable_mod.toArray());
			op.saveObjs(array_Payableapply_add.toArray());								
			trans.commit();
			NoticeUtil.Noticemsg("105", dto.getCreatedby(), now);
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"提报应付报结单,应付编号:"+ls_payableid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"提报应付报结单,应付编号:"+ls_payableid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("提报应付报结单成功!");
		return returnDTO;
		
	}


	public String SearchPayableToConform(FinanceDTO dto)throws ApplicationException {
		StringBuffer sb=new StringBuffer("select b.payableapplyid,b.applybatch,a.Payableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,a.placeboxid,a.netweight,a.price,a.amount")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname ")
		.append(" from Payable a,Payableapply b where a.Payableid=b.Payableid and b.isvalid='101'");
		
		if (!(dto.getPayablestatus()==null || dto.getPayablestatus().equals(""))){
			sb.append(" and a.Payablestatus='").append(dto.getPayablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and a.carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and a.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and a.memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and a.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and b.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and b.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}
	
	/**
	 * 查询已提报的过磅单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchPayableToConform(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setPayablestatus("101");  //默认只查询已提报记录
		return SearchPayableToConform(dto);
	}


	/**
	 * 应收提报付款确认
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO PayableConfirm(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getPayableapplyid()==null || dto.getPayableapplyid().equals("")){
			returnDTO.setMsg("应收提报编号不能为空");
			return returnDTO;
		}
		
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_Payableapplyid=dto.getPayableapplyid().split(",");  //分割应收提报编号
		String ls_Payableapplyid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_Payable_mod=new ArrayList();
		List array_Payableconfirm_add=new ArrayList();
		for (int i=0;i<array_Payableapplyid.length;i++){
			String ls_Payableapplyid=array_Payableapplyid[i];  //获取应收提报编号
			ls_Payableapplyid_temp=ls_Payableapplyid_temp+ls_Payableapplyid+",";
			
			Payableapply payableapply=null;
			try {
				payableapply=(Payableapply)op.retrieveObj(Payableapply.class, ls_Payableapplyid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("应付提报记录不存在,应收提报编号:"+ls_Payableapplyid);
				return returnDTO;
			}
			
			Payable Payable=null;
			try {
				Payable=(Payable)op.retrieveObj(Payable.class, payableapply.getPayableid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应付记录不存在,应收编号:"+payableapply.getPayableid());
				return returnDTO;
			}
			if (Payable.getPayablestatus().equals("100")){
				returnDTO.setMsg("应付记录尚未提报,应付编号:"+payableapply.getPayableid());
				return returnDTO;
			}
			if (Payable.getPayablestatus().equals("102")){
				returnDTO.setMsg("应付记录已经付款式确认,不需要重复确认,应付编号:"+payableapply.getPayableid());
				return returnDTO;
			}
			Payable.setPayablestatus("102");  //设置为已确认
			Payable.setModifyby(dto.getCreatedby());
			Payable.setModifydate(now);		
			
			
			Payableconfirm payableconfirm=new Payableconfirm();
			ClassHelper.copyProperties(payableapply, payableconfirm);
			payableconfirm.setPayableconfirmid(SeqBPO.GetSequence("PAYABLECONFIRMID"));  //过长去掉前面的SEQ＿
			payableconfirm.setConfirmtime(now);
			
			array_Payable_mod.add(Payable);
			array_Payableconfirm_add.add(payableconfirm);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_Payable_mod.toArray());
			op.saveObjs(array_Payableconfirm_add.toArray());								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应付提报付款确认,提报编号:"+ls_Payableapplyid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应付提报付款确认,提报编号:"+ls_Payableapplyid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("应付提报付款确认成功!");
		return returnDTO;
	}


	/**
	 * 查询已提报的应付报结单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchPayableApply(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setPayablestatus("101");  //默认只查询已提报记录
		return SearchPayableToConform(dto);
	}


	/**
	 * 取消提报
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO CancelPayableApply(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getPayableapplyid()==null || dto.getPayableapplyid().equals("")){
			returnDTO.setMsg("应付提报编号不能为空");
			return returnDTO;
		}
		if (dto.getCancelreson()==null || dto.getCancelreson().equals("")){
			returnDTO.setMsg("取消原因不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_Payableapplyid=dto.getPayableapplyid().split(",");  //分割应收提报编号
		String ls_Payableapplyid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_Payable_mod=new ArrayList();
		List array_Payableapply_mod=new ArrayList();
		for (int i=0;i<array_Payableapplyid.length;i++){
			String ls_Payableapplyid=array_Payableapplyid[i];  //获取应收提报编号
			ls_Payableapplyid_temp=ls_Payableapplyid_temp+ls_Payableapplyid+",";
			
			Payableapply payableapply=null;
			try {
				payableapply=(Payableapply)op.retrieveObj(Payableapply.class, ls_Payableapplyid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("应付提报记录不存在,提报编号:"+ls_Payableapplyid);
				return returnDTO;
			}
			payableapply.setCancelby(dto.getCreatedby());
			payableapply.setCancelreson(dto.getCancelreson());
			payableapply.setCanceltime(now);
			payableapply.setIsvalid("102");  //无效
			
			Payable payable=null;
			try {
				payable=(Payable)op.retrieveObj(Payable.class, payableapply.getPayableid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应付记录不存在,应付编号:"+payableapply.getPayableid());
				return returnDTO;
			}
			if (payable.getPayablestatus().equals("102")){
				returnDTO.setMsg("应付记录已经确认,不允许取消提报,应付编号:"+payableapply.getPayableid());
				return returnDTO;
			}			
			payable.setPayablestatus("100");  //设置为待提报
			payable.setModifyby(dto.getCreatedby());
			payable.setModifydate(now);		
			
			array_Payable_mod.add(payable);
			array_Payableapply_mod.add(payableapply);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_Payable_mod.toArray());
			op.updateObjs(array_Payableapply_mod.toArray());								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应付提报取消,提报编号:"+ls_Payableapplyid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"应付提报付款取消,提报编号:"+ls_Payableapplyid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("应付提报取消成功!");
		return returnDTO;
	}
	
	public String SearchPayableConfirm(FinanceDTO dto)throws ApplicationException {
		StringBuffer sb=new StringBuffer("select b.Payableconfirmid,b.Payableapplyid,b.applybatch,a.Payableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,a.placeboxid,a.netweight,a.price,a.amount")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname ")
		.append(" from Payable a,Payableconfirm b where a.Payableid=b.Payableid and b.isvalid='101'");
		
		if (!(dto.getPayablestatus()==null || dto.getPayablestatus().equals(""))){
			sb.append(" and a.Payablestatus='").append(dto.getPayablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and a.carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and a.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and a.memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and a.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and b.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and b.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}


	/**
	 * 取消付款确认
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchPayableConfirm(String msgdata)
			throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setPayablestatus("102");  //默认只查询已确认记录
		return SearchPayableConfirm(dto);
	}

    /**
     * 取消付款确认
     * @param msgdata
     * @return
     * @throws ApplicationException
     */
	public CommonDTO CancelPayableConfirm(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getPayableconfirmid()==null || dto.getPayableconfirmid().equals("")){
			returnDTO.setMsg("付款确认编号不能为空");
			return returnDTO;
		}
		if (dto.getCancelreson()==null || dto.getCancelreson().equals("")){
			returnDTO.setMsg("取消原因不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_Payableconfirmid=dto.getPayableconfirmid().split(",");  //分割付款确认编号
		String ls_Payableconfirmid_temp="";
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		List array_Payable_mod=new ArrayList();
		List array_Payableconfirm_mod=new ArrayList();
		for (int i=0;i<array_Payableconfirmid.length;i++){
			String ls_Payableconfirmid=array_Payableconfirmid[i];  //获取应收提报编号
			ls_Payableconfirmid_temp=ls_Payableconfirmid_temp+ls_Payableconfirmid+",";
			
			Payableconfirm Payableconfirm=null;
			try {
				Payableconfirm=(Payableconfirm)op.retrieveObj(Payableconfirm.class, ls_Payableconfirmid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("应付提报记录不存在,提报编号:"+ls_Payableconfirmid);
				return returnDTO;
			}
			Payableconfirm.setCancelby(dto.getCreatedby());
			Payableconfirm.setCancelreson(dto.getCancelreson());
			Payableconfirm.setCanceltime(now);
			Payableconfirm.setIsvalid("102");  //无效
			
			Payable Payable=null;
			try {
				Payable=(Payable)op.retrieveObj(Payable.class, Payableconfirm.getPayableid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("应付记录不存在,应付编号:"+Payableconfirm.getPayableid());
				return returnDTO;
			}
			if (!Payable.getPayablestatus().equals("102")){
				returnDTO.setMsg("应付记录状态异常,不允许取消付款确认,应付编号:"+Payableconfirm.getPayableid());
				return returnDTO;
			}			
			Payable.setPayablestatus("101");  //设置为已提报
			Payable.setModifyby(dto.getCreatedby());
			Payable.setModifydate(now);		
			
			array_Payable_mod.add(Payable);
			array_Payableconfirm_mod.add(Payableconfirm);
		}
		//数据保存
		try {
			trans.begin();
			op.updateObjs(array_Payable_mod.toArray());
			op.updateObjs(array_Payableconfirm_mod.toArray());								
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"付款确认取消,编号:"+ls_Payableconfirmid_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"付款确认取消,编号:"+ls_Payableconfirmid_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("付款确认取消成功!");
		return returnDTO;
	}


	

	public String SearchPayable(String msgdata) throws ApplicationException {
		FinanceDTO dto=new FinanceDTO();
		ClassHelper.copyProperties(msgdata, dto);
		StringBuffer sb=new StringBuffer("select a.payableid,orderid,weighid,goodstype,packagetype,carriagetype,conveyance,businesstype,carnumber,closenumber,boxid,placeboxid,a.netweight,a.price,a.amount,a.payablestatus")
		.append(",memberid,dbo.sf_get_membername(memberid) membername,companyid,dbo.sf_get_companyname(companyid) companyname,convert(varchar,a.createddate,120) createddate, convert(varchar,b.confirmtime,120) confirmtime  from payable a")
		.append(" left join payableconfirm b on a.payableid=b.payableid and b.isvalid='101' where 1=1");
		
		if (!(dto.getPayablestatus()==null || dto.getPayablestatus().equals(""))){
			sb.append(" and a.payablestatus='").append(dto.getPayablestatus()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getCarnumber()==null || dto.getCarnumber().equals(""))){
			sb.append(" and a.carnumber='").append(dto.getCarnumber()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}		
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and a.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and a.memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and a.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}


}
