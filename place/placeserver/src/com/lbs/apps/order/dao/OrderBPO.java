package com.lbs.apps.order.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.apps.common.CommonDTO;
import com.lbs.apps.common.JsonValidator;
import com.lbs.apps.common.NoticeUtil;
import com.lbs.apps.system.dao.SeqBPO;
import com.lbs.apps.system.po.Boxinplace;
import com.lbs.apps.system.po.Boxoutpark;
import com.lbs.apps.system.po.Boxoutplace;
import com.lbs.apps.system.po.Member;
import com.lbs.apps.system.po.Ordercompany;
import com.lbs.apps.system.po.Orderfinish;
import com.lbs.apps.system.po.Orderinfo;
import com.lbs.apps.system.po.Orderlog;
import com.lbs.apps.system.po.Orderschedule;
import com.lbs.apps.system.po.Payable;
import com.lbs.apps.system.po.Placeboxinfo;
import com.lbs.apps.system.po.Receivable;
import com.lbs.apps.system.po.Waybill;
import com.lbs.apps.system.po.Waybillweigh;
import com.lbs.apps.system.po.WaybillweighId;
import com.lbs.apps.system.po.Weigh;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.DateUtil;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.NotFindException;
import com.lbs.commons.op.OPException;
import com.lbs.commons.op.OPManager;

public class OrderBPO {
	LogHelper log = new LogHelper(this.getClass());	
	TransManager trans = new TransManager();
	OPManager op = new OPManager();
	
	private String SearchOrder(OrderDTO dto) throws ApplicationException {
		StringBuffer sb=new StringBuffer("select a.orderid,memberid,dbo.sf_get_membername(memberid) membername,ordertype,goodstype,packagetype,carriagetype,qty,boxqty,valuation,expectprice,startareaid,startaddress,endareaid,endaddress,needcarriagesafe")
		.append(",convert(varchar,havestartdate,120) havestartdate,convert(varchar,requestfinishdate,120) requestfinishdate,invoicerequest,businesstype,consigner,consignermobilephone,consignertelephone,consignerfax,consigneremail,receiver")
		.append(",receivermobilephone,receivertelephone,receiverfax,receiveremail,convert(varchar,losedate,120) losedate,orderstatus,orderamount,discount,realamount,payamount,relatedorderid,isvalid,memo")
		.append(",createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate,membersignstatus,companysignstatus,price,safeprice,publishdeposit,companybidbond,memberbidbond")
		.append(",memberpoundage,companypoundage,memberinsurance,companyinsurance,ifspottrans,safeorder,askpublishdate,agreeqty,agreeprice,agreediscount,agreeamount,extorderid")
		.append(",origin,brand,sourcename,productlevel,yearnum,retriveshop,retrievesdate,retrieveedate,conveyance,invoicetitle,invoiceno,water,impurity,capacity,smell,imperfecttotal")
		.append(",imperfectnum,imperfectnum2,outqty,ordersource,dbo.sf_get_domainname('ORDERSOURCE',ordersource) ordersourcename,sendcompany,retrievecompany,sendcontact,retrievecontact,productprice,b.companyid,dbo.sf_get_companyname(b.companyid) companyname")
		.append(" from orderinfo a left join ordercompany  b on a.orderid=b.orderid where 1=1");
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getOrderstatus()==null || dto.getOrderstatus().equals(""))){
			sb.append(" and orderstatus='").append(dto.getOrderstatus()).append("'");
		}
		if (!(dto.getGoodstype()==null || dto.getGoodstype().equals(""))){
			sb.append(" and goodstype='").append(dto.getGoodstype()).append("'");
		}
		if (!(dto.getPackagetype()==null || dto.getPackagetype().equals(""))){
			sb.append(" and packagetype='").append(dto.getPackagetype()).append("'");
		}
		if (!(dto.getCarriagetype()==null || dto.getCarriagetype().equals(""))){
			sb.append(" and carriagetype='").append(dto.getCarriagetype()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and b.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getIsplan()==null || dto.getIsplan().equals(""))){
			sb.append(" and a.isplan='").append(dto.getIsplan()).append("'");
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		//查询逾期
		if (dto.getExpire()==null) dto.setExpire("100");
		if (dto.getExpire().equals("101")){  
			sb.append(" and a.requestfinishdate<getdate()");
		}
		return sb.toString();
	}
	
	public String SearchOrder(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchOrder(dto);
		
	}

	/**
	 * 保存订单
	 * @param msgdata
	 * 注：msgdata中json除了包括正常的orderinfo中的字段外，还需要增加company字段，如果有多个company则数据之间用,分割
	 * @return
	 * @throws ApplicationException
	 */
	 
	public ReturnDTO AddOrder(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getGoodstype()==null || dto.getGoodstype().equals("")){
			returnDTO.setMsg("发货品类不能为空");
			return returnDTO;
		} 
			
		if (dto.getPackagetype()==null || dto.getPackagetype().equals("")){
			returnDTO.setMsg("包装形式不能为空");
			return returnDTO;
		}		
		if (dto.getCarriagetype()==null || dto.getCarriagetype().equals("")){
			returnDTO.setMsg("运输方式不能为空");
			return returnDTO;
		}		
		if (dto.getConveyance()==null || dto.getConveyance().equals("")){
			returnDTO.setMsg("运输工具不能为空");
			return returnDTO;
		}
		if (dto.getQty()==null || dto.getQty().doubleValue()<=0){
			returnDTO.setMsg("请输入正确的运量(吨)");
			return returnDTO;
		}		
		if (dto.getStartareaid()==null || dto.getStartareaid().equals("")){
			returnDTO.setMsg("发货地不能为空");
			return returnDTO;
		}		
		if(dto.getStartaddress()==null || dto.getStartaddress().equals("")){
			returnDTO.setMsg("发货详细地址不能为空");
			return returnDTO;
		}		
		if (dto.getEndareaid()==null || dto.getEndareaid().equals("")){
			returnDTO.setMsg("收货地不能为空");
			return returnDTO;
		}		
		if(dto.getEndaddress()==null || dto.getEndaddress().equals("")){
			returnDTO.setMsg("发货详细地址不能为空");
			return returnDTO;
		}		
		if(dto.getExpectprice()==null || dto.getExpectprice().equals("")){
			returnDTO.setMsg("预期运价不能为空");
			return returnDTO;
		}		
		if (dto.getNeedcarriagesafe()==null || dto.getNeedcarriagesafe().equals("")){
			returnDTO.setMsg("是否需要开具运输保险不能为空");
			return returnDTO;
		}		
		if(dto.getHavestartdate()==null || dto.getHavestartdate().equals("")){
			returnDTO.setMsg("具备起运日期不能为空");
			return returnDTO;
		}		
		if(dto.getHavestartdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("具备起运日期不能小于当前日期");
			return returnDTO;
		}		
		if(dto.getRequestfinishdate()==null || dto.getRequestfinishdate().equals("")){
			returnDTO.setMsg("要求完成日期不能为空");
			return returnDTO;
		}
		if(dto.getRequestfinishdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("要求完成日期不能小于当前日期");
			return returnDTO;
		}				
		if(dto.getHavestartdate().compareTo(dto.getRequestfinishdate())>0){
			returnDTO.setMsg("要求完成日期不能小于具备起运日期");
			return returnDTO;
		}		
		
		if (dto.getSendcompany()==null || dto.getSendcompany().equals("")){
			returnDTO.setMsg("委托单位不能为空");
			return returnDTO;
		}
		if (dto.getSendcontact()==null || dto.getSendcontact().equals("")){
			returnDTO.setMsg("委托联系人不能为空");
			return returnDTO;
		}	
		if (dto.getRetrievecompany()==null || dto.getRetrievecompany().equals("")){
			returnDTO.setMsg("收货单位不能为空");
			return returnDTO;
		}	
		if (dto.getRetrievecontact()==null || dto.getRetrievecontact().equals("")){
			returnDTO.setMsg("收货联系人不能为空");
			return returnDTO;
		}	
		
		if (dto.getConsigner()==null || dto.getConsigner().equals("")){
			returnDTO.setMsg("委托人不能为空");
			return returnDTO;
		}	
		if((dto.getConsignermobilephone()==null || dto.getConsignermobilephone().equals("")) && (dto.getConsignertelephone()==null || dto.getConsignertelephone().equals(""))){
			returnDTO.setMsg("委托人的联系手机和固定电话二选一");
			return returnDTO;
		}		
		if (dto.getReceiver()==null || dto.getReceiver().equals("")){
			returnDTO.setMsg("收货人不能为空");
			return returnDTO;
		}		
		
		if((dto.getReceivermobilephone()==null || dto.getReceivermobilephone().equals("")) && (dto.getReceivertelephone()==null || dto.getReceivertelephone().equals(""))){
			returnDTO.setMsg("收货人的联系手机和固定电话至少二选一");
			return returnDTO;
		}		
		if (dto.getInvoicerequest()==null || dto.getInvoicerequest().equals("")){
			returnDTO.setMsg("发票要求不能为空");
			return returnDTO;
		}		
		/*
		if (dto.getBusinesstype()==null || dto.getBusinesstype().equals("")){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		*/
		if (dto.getLosedate()==null || dto.getLosedate().equals("")){
			returnDTO.setMsg("挂单失效日期不能为空");
			return returnDTO;
		}		 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}		
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("")){
			returnDTO.setMsg("承运商不能为空");
			return returnDTO;
		}
		
		
		//分割company:获取承运商列表
		Orderinfo orderinfo=new Orderinfo();
		ClassHelper.copyProperties(dto, orderinfo);
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		orderinfo.setCreateddate(now);
		orderinfo.setModifydate(now);	
		orderinfo.setModifyby(dto.getCreatedby());
		//String ls_seq=SeqBPO.GetSequence("SEQ_ORDERID");  //获取序列号
		String ls_seq=SeqBPO.GetOrderid();  //获取订单编号
		if (ls_seq.equals("-1")){
			returnDTO.setMsg("获取订单编号时出错...");
			return returnDTO;
		} 
		orderinfo.setOrderid(ls_seq);
		orderinfo.setOrderstatus("103");  //有效	
		orderinfo.setPayamount(new Double(0));  //设置实际付款金额
		orderinfo.setSafeprice(new Double(0)); //设置运输保险单价
		orderinfo.setPublishdeposit(new Double(0)); //发布保证金
		orderinfo.setMemberbidbond(new Double(0));  //委托方履约保证金
		orderinfo.setCompanybidbond(new Double(0)); //承运商履约保证金
		orderinfo.setOrdersource("101");  //线下订单录入
		orderinfo.setIsplan("100");   //未排期
		orderinfo.setIsvalid("101");
		//判断委托方是否存在，不存在则增加
		boolean lb_add_member=false;
		Member member=null;
		String hql=" from Member where companyname='"+dto.getSendcompany()+"'";
		List list=null;
		try {
			list=op.retrieveObjs(hql);
			if (list==null){
				lb_add_member=true;
				member=new Member();
				String ls_memberid=SeqBPO.GetSequence("SEQ_MEMBERID");
				member.setMemberid(new Integer(ls_memberid));
				member.setCompanyname(dto.getSendcompany());
			}else{
				member=(Member)list.get(0);
			}
			orderinfo.setMemberid(member.getMemberid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}		
		List array_ordercompany_add=new ArrayList();
		String[] array_company=dto.getCompanyid().split(",");  //分割承运商
		for (int i=0;i<array_company.length;i++){
			Ordercompany ordercompany=new Ordercompany();
			ordercompany.setOrderid(orderinfo.getOrderid());
			ordercompany.setCompanyid(new Integer(array_company[i]));
			ordercompany.setOrdercompanyid(SeqBPO.GetSequence("SEQ_ORDERCOMPANYID"));
			array_ordercompany_add.add(ordercompany);
		}
				
		//增加订单操作日志
		Orderlog orderlog=new Orderlog();
		orderlog.setOrderid(orderinfo.getOrderid());
		orderlog.setOrderopertype("101"); //创建订单
		orderlog.setMemo("线下订单录入");
		orderlog.setCreatedby(dto.getCreatedby());
		orderlog.setCreateddate(now);
		//数据保存
		try {
			trans.begin();				
			if (lb_add_member) op.saveObj(member);
			op.saveObj(orderinfo);	
			op.saveObjs(array_ordercompany_add.toArray());
			op.saveObj(orderlog);					
			trans.commit();
			NoticeUtil.Noticemsg("101", dto.getCreatedby(), now);
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"线下订单录入,订单编号:"+orderinfo.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"线下订单录入,订单编号:"+orderinfo.getOrderid(),dto.getCreatedby());
		returnDTO.setOrderid(orderinfo.getOrderid());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("线下订单保存成功!");
		return returnDTO;
	}

	
	public CommonDTO ModOrder(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("订单编号不能为空");
			return returnDTO;
		} 
		if (dto.getGoodstype()==null || dto.getGoodstype().equals("")){
			returnDTO.setMsg("发货品类不能为空");
			return returnDTO;
		} 
			
		if (dto.getPackagetype()==null || dto.getPackagetype().equals("")){
			returnDTO.setMsg("包装形式不能为空");
			return returnDTO;
		}		
		if (dto.getCarriagetype()==null || dto.getCarriagetype().equals("")){
			returnDTO.setMsg("运输方式不能为空");
			return returnDTO;
		}		
		if (dto.getConveyance()==null || dto.getConveyance().equals("")){
			returnDTO.setMsg("运输工具不能为空");
			return returnDTO;
		}
		if (dto.getQty()==null || dto.getQty().doubleValue()<=0){
			returnDTO.setMsg("请输入正确的运量(吨)");
			return returnDTO;
		}		
		if (dto.getStartareaid()==null || dto.getStartareaid().equals("")){
			returnDTO.setMsg("发货地不能为空");
			return returnDTO;
		}		
		if(dto.getStartaddress()==null || dto.getStartaddress().equals("")){
			returnDTO.setMsg("发货详细地址不能为空");
			return returnDTO;
		}		
		if (dto.getEndareaid()==null || dto.getEndareaid().equals("")){
			returnDTO.setMsg("收货地不能为空");
			return returnDTO;
		}		
		if(dto.getEndaddress()==null || dto.getEndaddress().equals("")){
			returnDTO.setMsg("发货详细地址不能为空");
			return returnDTO;
		}		
		if(dto.getExpectprice()==null || dto.getExpectprice().equals("")){
			returnDTO.setMsg("预期运价不能为空");
			return returnDTO;
		}		
		if (dto.getNeedcarriagesafe()==null || dto.getNeedcarriagesafe().equals("")){
			returnDTO.setMsg("是否需要开具运输保险不能为空");
			return returnDTO;
		}		
		if(dto.getHavestartdate()==null || dto.getHavestartdate().equals("")){
			returnDTO.setMsg("具备起运日期不能为空");
			return returnDTO;
		}		
		if(dto.getHavestartdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("具备起运日期不能小于当前日期");
			return returnDTO;
		}		
		if(dto.getRequestfinishdate()==null || dto.getRequestfinishdate().equals("")){
			returnDTO.setMsg("要求完成日期不能为空");
			return returnDTO;
		}
		if(dto.getRequestfinishdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("要求完成日期不能小于当前日期");
			return returnDTO;
		}				
		if(dto.getHavestartdate().compareTo(dto.getRequestfinishdate())>0){
			returnDTO.setMsg("要求完成日期不能小于具备起运日期");
			return returnDTO;
		}
		
		if (dto.getSendcompany()==null || dto.getSendcompany().equals("")){
			returnDTO.setMsg("委托单位不能为空");
			return returnDTO;
		}
		if (dto.getSendcontact()==null || dto.getSendcontact().equals("")){
			returnDTO.setMsg("委托联系人不能为空");
			return returnDTO;
		}	
		if (dto.getRetrievecompany()==null || dto.getRetrievecompany().equals("")){
			returnDTO.setMsg("收货单位不能为空");
			return returnDTO;
		}	
		if (dto.getRetrievecontact()==null || dto.getRetrievecontact().equals("")){
			returnDTO.setMsg("收货联系人不能为空");
			return returnDTO;
		}	
		
		if (dto.getConsigner()==null || dto.getConsigner().equals("")){
			returnDTO.setMsg("委托人不能为空");
			return returnDTO;
		}	
		if((dto.getConsignermobilephone()==null || dto.getConsignermobilephone().equals("")) && (dto.getConsignertelephone()==null || dto.getConsignertelephone().equals(""))){
			returnDTO.setMsg("委托人的联系手机和固定电话二选一");
			return returnDTO;
		}		
		if (dto.getReceiver()==null || dto.getReceiver().equals("")){
			returnDTO.setMsg("收货人不能为空");
			return returnDTO;
		}		
		
		if((dto.getReceivermobilephone()==null || dto.getReceivermobilephone().equals("")) && (dto.getReceivertelephone()==null || dto.getReceivertelephone().equals(""))){
			returnDTO.setMsg("收货人的联系手机和固定电话至少二选一");
			return returnDTO;
		}		
		if (dto.getInvoicerequest()==null || dto.getInvoicerequest().equals("")){
			returnDTO.setMsg("发票要求不能为空");
			return returnDTO;
		}		
		/*
		if (dto.getBusinesstype()==null || dto.getBusinesstype().equals("")){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		*/
		if (dto.getLosedate()==null || dto.getLosedate().equals("")){
			returnDTO.setMsg("挂单失效日期不能为空");
			return returnDTO;
		}		 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}		
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("")){
			returnDTO.setMsg("承运商不能为空");
			return returnDTO;
		}
		
		
		Orderinfo orderinfo=null;
		try {
			orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, dto.getOrderid());
		} catch (OPException e3) {
			returnDTO.setMsg(e3.getMessage());
			return returnDTO;
		} catch (NotFindException e3) {
			returnDTO.setMsg("订单记录不存在,订单编号:"+dto.getOrderid());
			return returnDTO;
		}
		if (orderinfo.getOrderstatus().equals("104")){
			returnDTO.setMsg("订单已完成,不允许修改,订单编号:"+dto.getOrderid());
			return returnDTO;
		}
		Timestamp createddate=orderinfo.getCreateddate();
		Integer createdby=orderinfo.getCreatedby();
		Integer createdorg=orderinfo.getCreatedorg();
		String ls_isplan=orderinfo.getIsplan();
		ClassHelper.copyProperties(dto, orderinfo);
		orderinfo.setOrderstatus("103");  //有效
		orderinfo.setIsplan(ls_isplan);
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		orderinfo.setCreatedby(createdby);
		orderinfo.setCreateddate(createddate);
		orderinfo.setModifydate(now);	
		orderinfo.setModifyby(dto.getCreatedby());		
		orderinfo.setIsvalid("101");
		
		//判断委托方是否存在，不存在则增加
		boolean lb_add_member=false;
		Member member=null;
		String hql=" from Member where companyname='"+dto.getSendcompany()+"'";
		List list=null;
		try {
			list=op.retrieveObjs(hql);
			if (list==null){
				lb_add_member=true;
				member=new Member();
				String ls_memberid=SeqBPO.GetSequence("SEQ_MEMBERID");
				member.setMemberid(new Integer(ls_memberid));
				member.setCompanyname(dto.getSendcompany());
			}else{
				member=(Member)list.get(0);
			}
			orderinfo.setMemberid(member.getMemberid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		
		//分割company:获取承运商列表
		List array_ordercompany_add=new ArrayList();
		String[] array_company=dto.getCompanyid().split(",");  //分割承运商
		for (int i=0;i<array_company.length;i++){
			Ordercompany ordercompany=new Ordercompany();
			ordercompany.setOrdercompanyid(SeqBPO.GetSequence("SEQ_ORDERCOMPANYID"));
			ordercompany.setOrderid(orderinfo.getOrderid());
			ordercompany.setCompanyid(new Integer(array_company[i]));
			array_ordercompany_add.add(ordercompany);
		}
				
		//增加订单操作日志
		Orderlog orderlog=new Orderlog();
		orderlog.setOrderid(orderinfo.getOrderid());
		orderlog.setOrderopertype("101"); //创建订单
		orderlog.setMemo("修改订单信息");
		orderlog.setCreatedby(dto.getCreatedby());
		orderlog.setCreateddate(now);
		//数据保存
		try {
			trans.begin();				
			if (lb_add_member) op.saveObj(member);
			op.updateObj(orderinfo);
			op.removeObjs("delete from Ordercompany where orderid='"+orderinfo.getOrderid()+"'");
			op.saveObjs(array_ordercompany_add.toArray());
			op.saveObj(orderlog);					
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"线下订单录入,订单编号:"+orderinfo.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"线下订单录入,订单编号:"+orderinfo.getOrderid(),dto.getCreatedby());
		returnDTO.setOrderid(orderinfo.getOrderid());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("线下订单修改成功!");
		return returnDTO;
	}

	
	public String ViewOrder(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			dto.setOrderid("-999999");
		}
		return SearchOrder(dto);
	}
	
	//获取订单承运商列表
	public String SearchOrderCompany(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			dto.setOrderid("-999999");
		}
		StringBuffer sb=new StringBuffer("select a.companyid,companyname,mobilephone,telephone,fax,email,zipcode")
		.append(" from ordercompany a,company b where a.companyid=b.companyid and a.orderid='").append(dto.getOrderid()).append("'");
		
		return sb.toString();
	}

	
	public String SearchOrderToPlan(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		dto.setOrderstatus("103");   //只对有效订单进行排期
		dto.setIsplan("100");
		return SearchOrder(dto);
	}

	
	public CommonDTO SaveOrderPlan(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("订单编号不能为空");
			return returnDTO;
		} 
		if (dto.getBoxarrivedate()==null || dto.getBoxarrivedate().equals("")){
			returnDTO.setMsg("预计集装箱到达日期不能为空");
			return returnDTO;
		}
		if (dto.getPackingsdate()==null || dto.getPackingsdate().equals("")){
			returnDTO.setMsg("预计装箱开始日期不能为空");
			return returnDTO;
		}
		if (dto.getPackingedate()==null || dto.getPackingedate().equals("")){
			returnDTO.setMsg("预计装箱结束日期不能为空");
			return returnDTO;
		}
		if (dto.getOutdate()==null || dto.getOutdate().equals("")){
			returnDTO.setMsg("预计运出日期不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){  //数据库中暂未有记载保存
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		
		Orderschedule orderschedule=new Orderschedule();
		ClassHelper.copyProperties(dto, orderschedule);
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		Orderinfo orderinfo=null;
		try {
			orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, dto.getOrderid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("订单记录不存在,订单编号:"+dto.getOrderid());
			return returnDTO;
		}
		orderinfo.setIsplan("101");
		orderinfo.setModifyby(dto.getCreatedby());
		orderinfo.setModifydate(now);
		//数据保存
		try {
			trans.begin();					
			op.saveObj(orderschedule);		
			op.updateObj(orderinfo);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"保存订单排期计划,订单编号:"+dto.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"保存订单排期计划,订单编号:"+dto.getOrderid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("保存订单排期计划成功!");
		return returnDTO;
	}

	
	public CommonDTO ModOrderPlan(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("订单编号不能为空");
			return returnDTO;
		} 
		if (dto.getBoxarrivedate()==null || dto.getBoxarrivedate().equals("")){
			returnDTO.setMsg("预计集装箱到达日期不能为空");
			return returnDTO;
		}
		if (dto.getPackingsdate()==null || dto.getPackingsdate().equals("")){
			returnDTO.setMsg("预计装箱开始日期不能为空");
			return returnDTO;
		}
		if (dto.getPackingedate()==null || dto.getPackingedate().equals("")){
			returnDTO.setMsg("预计装箱结束日期不能为空");
			return returnDTO;
		}
		if (dto.getOutdate()==null || dto.getOutdate().equals("")){
			returnDTO.setMsg("预计运出日期不能为空");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){  //数据库中暂未有记载保存
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		Orderschedule orderschedule=null;
		try {
			orderschedule=(Orderschedule)op.retrieveObj(Orderschedule.class, dto.getOrderid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("订单排期计划记录未找到,订单编号:"+dto.getOrderid());
			return returnDTO;
		}
		ClassHelper.copyProperties(dto, orderschedule);		
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		Orderinfo orderinfo=null;
		try {
			orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, dto.getOrderid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("订单记录不存在,订单编号:"+dto.getOrderid());
			return returnDTO;
		}
		orderinfo.setIsplan("101");
		orderinfo.setModifyby(dto.getCreatedby());
		orderinfo.setModifydate(now);
		//数据保存
		try {
			trans.begin();					
			op.updateObj(orderschedule);		
			op.updateObj(orderinfo);
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"修改订单排期计划,订单编号:"+dto.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"修改订单排期计划,订单编号:"+dto.getOrderid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("修改订单排期计划成功!");
		return returnDTO;
	}

	/**
	 * 订单完成操作
	 * @param msgdata
	 * @return
	 * 如果没有运单不允许完成(火车)
	 * 如果存在未完成的运单不允许进行订单完成操作
	 * 
	 * @throws ApplicationException
	 */
	public CommonDTO OrderFinish(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("订单编号不能为空");
			return returnDTO;
		} 
		if (dto.getFinishdate()==null || dto.getFinishdate().equals("")){
			returnDTO.setMsg("订单完成日期不能为空");
			return returnDTO;
		} 
		if (dto.getFinishdesc()==null || dto.getFinishdesc().equals("")){
			returnDTO.setMsg("订单完成情况说明不能为空");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){  //数据库中暂未有记载保存
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		String hql="select count(*) from Waybill where orderid='"+dto.getOrderid()+"'";
		try {
			if (op.getCount(hql).intValue()<=0){
				returnDTO.setMsg("尚未录入运单信息，不允许进行订单完成操作");
				return returnDTO;
			}
			hql="select count(*) from Waybill where orderid='"+dto.getOrderid()+"' and waybillstatus='100'";
			if (op.getCount(hql).intValue()<=0){
				returnDTO.setMsg("存在尚未完成的运单记录，暂不允许进行订单完成操作");
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		Orderinfo orderinfo =null;
		try {
			orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, dto.getOrderid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("订单记录不存在,订单编号:"+dto.getOrderid());
			return returnDTO;
		}
		if (orderinfo.getOrderstatus().equals("104")){
			returnDTO.setMsg("订单已完成,不需要重复操作，订单编号:"+dto.getOrderid());
			return returnDTO;
		}
		orderinfo.setOrderstatus("104");
		orderinfo.setModifyby(dto.getCreatedby());
		orderinfo.setModifydate(now);
		
		
		Orderfinish orderfinish=new Orderfinish();  //实例化订单完成记录表
		ClassHelper.copyProperties(dto, orderfinish);
		orderfinish.setCreateddate(now);
		orderfinish.setModifyby(dto.getCreatedby());
		orderfinish.setModifydate(now);
		try {
			trans.begin();					
			op.saveObj(orderfinish);
			op.updateObj(orderinfo);			
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"订单完成,订单编号:"+dto.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}		
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"订单完成,订单编号:"+dto.getOrderid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("订单完成操作成功!");
		return returnDTO;
	}

	
	private String SearchOrderPlan(OrderDTO dto) throws ApplicationException {		
		StringBuffer sb=new StringBuffer("select a.orderid,memberid,dbo.sf_get_membername(memberid) membername,ordertype,goodstype,packagetype,carriagetype,qty,boxqty,valuation,expectprice,startareaid,startaddress,endareaid,endaddress,needcarriagesafe")
		.append(",havestartdate,requestfinishdate,invoicerequest,businesstype,consigner,consignermobilephone,consignertelephone,consignerfax,consigneremail,receiver")
		.append(",receivermobilephone,receivertelephone,receiverfax,receiveremail,losedate,orderstatus,orderamount,discount,realamount,payamount,relatedorderid,isvalid,memo")
		.append(",createdby,createdorg,convert(varchar,createddate,120) createddate,modifyby,modifyorg,convert(varchar,modifydate,120) modifydate,membersignstatus,companysignstatus,price,safeprice,publishdeposit,companybidbond,memberbidbond")
		.append(",memberpoundage,companypoundage,memberinsurance,companyinsurance,ifspottrans,safeorder,askpublishdate,agreeqty,agreeprice,agreediscount,agreeamount,extorderid")
		.append(",origin,brand,sourcename,productlevel,yearnum,retriveshop,retrievesdate,retrieveedate,conveyance,invoicetitle,invoiceno,water,impurity,capacity,smell,imperfecttotal")
		.append(",imperfectnum,imperfectnum2,outqty,ordersource,b.companyid,dbo.sf_get_companyname(b.companyid) companyname,convert(varchar,c.boxarrivedate,120) boxarrivedate,convert(varchar,c.packingsdate,120) packingsdate,convert(varchar,c.packingedate,120) packingedate,convert(varchar,c.outdate,120) outdate")
		.append(" from orderinfo a left join ordercompany  b on a.orderid=b.orderid left join orderschedule c on a.orderid=c.orderid where 1=1");
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getOrderstatus()==null || dto.getOrderstatus().equals(""))){
			sb.append(" and orderstatus='").append(dto.getOrderstatus()).append("'");
		}
		if (!(dto.getGoodstype()==null || dto.getGoodstype().equals(""))){
			sb.append(" and goodstype='").append(dto.getGoodstype()).append("'");
		}
		if (!(dto.getPackagetype()==null || dto.getPackagetype().equals(""))){
			sb.append(" and packagetype='").append(dto.getPackagetype()).append("'");
		}
		if (!(dto.getCarriagetype()==null || dto.getCarriagetype().equals(""))){
			sb.append(" and carriagetype='").append(dto.getCarriagetype()).append("'");
		}
		if (!(dto.getMemberid()==null  || dto.getMemberid().intValue()==0)){
			sb.append(" and memberid=").append(dto.getMemberid());
		}
		if (!(dto.getCompanyid()==null  || dto.getCompanyid().equals(""))){
			sb.append(" and b.companyid=").append(dto.getCompanyid());
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}		
		return sb.toString();
	}
	
	public String SearchOrderPlan(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		return SearchOrderPlan(dto);
	}
	
	public String ViewOrderPlan(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			dto.setOrderid("-999999");
		}
		return SearchOrderPlan(dto);
	}

	
	public String ViewOrderProcess(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			dto.setOrderid("-999999");
		}
		StringBuffer sb=new StringBuffer("select outqty,qty,CAST(outqty AS FLOAT)/qty * 100 wcbfb,orderstatus from orderinfo where orderid='")
		.append(dto.getOrderid()).append("'");
		return sb.toString();
	}

	
	/**
	 * 查询待入场的过磅单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchBoxToInPlace(String msgdata)
			throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		StringBuffer sb=new StringBuffer("select weighid,boxid,orderid,goodstype,carnumber,closenumber,conveyance from weigh where weighstatus='101'");
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and conveyance='").append(dto.getConveyance()).append("'");
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
	 * 重箱批量入场确认
	 * 多个过磅单时过磅单号中间用,分割,对应的堆场箱位编号同样采用,分割
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO BoxInPlaceConfirm(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("过磅编号不能为空");
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
		String[] array_weighid=dto.getWeighid().split(",");
		String[] array_placeboxid=dto.getPlaceboxid().split(",");
		if (array_weighid.length!=array_placeboxid.length){
			returnDTO.setMsg("选中的过磅单与堆场箱位编号数量不匹配");
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList();
		List array_boxinplace_add=new ArrayList();
		for (int i=0;i<array_weighid.length;i++){
			String ls_weighid=array_weighid[i];         //获取过磅单号
			String ls_placeboxid=array_placeboxid[i];   //获取堆场箱位编号
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//获取过磅单记录
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单记录不存在,过磅单号:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("101")){
				returnDTO.setMsg("过磅单状态异常,不允许进行入场操作,过磅单号:"+ls_weighid);
				return returnDTO;
			}
			weigh.setPlaceboxid(ls_placeboxid);
			weigh.setWeighstatus("102");  //设置为已入场
			
			//获取堆场箱位记录
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("堆场箱位记录不存在,编号:"+ls_placeboxid);
				return returnDTO;
			}
			Orderinfo orderinfo=null;
			try {
				orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, weigh.getOrderid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单"+weigh.getWeighid()+"对应的订单记录不存,订单编号:"+weigh.getOrderid());
				return returnDTO;
			}
			placeboxinfo.setPlaceboxstatus("104");  //占用
			placeboxinfo.setOrderid(weigh.getOrderid());
			placeboxinfo.setWeighid(weigh.getWeighid());
			placeboxinfo.setBoxid(weigh.getBoxid());
			placeboxinfo.setMemberid(orderinfo.getMemberid());
			placeboxinfo.setCompanyid(weigh.getCompanyid());
			placeboxinfo.setStatusstartdate(now);
			placeboxinfo.setIsempty("100"); //重箱入场
			
			Boxinplace boxinplace=new Boxinplace();
			String ls_seq=SeqBPO.GetSequence("SEQ_BOXINPLACEID");  //获取序列号
			ClassHelper.copyProperties(dto, boxinplace);
			ClassHelper.copyProperties(weigh, boxinplace);
			boxinplace.setInplacetime(now);
			boxinplace.setCreatedby(dto.getCreatedby());
			boxinplace.setCreateddate(now);
			boxinplace.setModifyby(dto.getCreatedby());
			boxinplace.setModifydate(now);
			boxinplace.setBoxinplaceid(ls_seq);
			boxinplace.setIsvalid("101");
			
			array_weigh_mod.add(weigh);
			array_placeboxinfo_mod.add(placeboxinfo);
			array_boxinplace_add.add(boxinplace);
		}
		//数据保存
		try {
			trans.begin();					
			op.updateObjs(array_weigh_mod.toArray());					
			op.updateObjs(array_placeboxinfo_mod.toArray());
			op.saveObjs(array_boxinplace_add.toArray());
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量入场,过磅单号:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量入场,过磅单号:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("保存重箱批量入场成功!");
		return returnDTO;
	}

	/**
	 * 查询待出场的过磅单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchBoxToOutPlace(String msgdata)
			throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		StringBuffer sb=new StringBuffer("select a.weighid,b.boxid,b.orderid,b.goodstype,b.carnumber,b.closenumber,b.conveyance,b.placeboxid,b.companyid,dbo.sf_get_companyname(b.companyid) companyname,b.boxinplaceid from weigh a,boxinplace b where a.weighid=b.weighid and a.weighstatus='102' and b.isvalid='101'");
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and b.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and b.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and b.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}

	/**
	 * 重箱批量出场确认
	 * 注：如果是运输工具为火车时出场同时出园，并生成应收应付待结算数据  ???未完成
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO BoxOutPlaceConfirm(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("过磅编号不能为空");
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
		String[] array_weighid=dto.getWeighid().split(",");
		String[] array_placeboxid=dto.getPlaceboxid().split(",");
		if (array_weighid.length!=array_placeboxid.length ){
			returnDTO.setMsg("选中的过磅单与堆场箱位编号数量不匹配");
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList(); 
		List array_boxoutplace_add=new ArrayList();
		List array_boxoutpark_add=new ArrayList();
		List array_receivable_add=new ArrayList();
		List array_payable_add=new ArrayList();
		for (int i=0;i<array_weighid.length;i++){
			String ls_weighid=array_weighid[i];         //获取过磅单号
			String ls_placeboxid=array_placeboxid[i];   //获取堆场箱位编号
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//获取过磅单记录
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单记录不存在,过磅单号:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("102")){
				returnDTO.setMsg("过磅单状态异常,不允许进行出场操作,过磅单号:"+ls_weighid);
				return returnDTO;
			}
			weigh.setWeighstatus("103");  //设置为已出场
			
			//获取堆场箱位记录
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("堆场箱位记录不存在,单号:"+ls_placeboxid);
				return returnDTO;
			}
			Orderinfo orderinfo=null;
			try {
				orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, weigh.getOrderid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单"+weigh.getWeighid()+"对应的订单记录不存,订单编号:"+weigh.getOrderid());
				return returnDTO;
			}
			placeboxinfo.setPlaceboxstatus("101");  //空闲
			placeboxinfo.setOrderid(weigh.getOrderid());
			placeboxinfo.setWeighid(weigh.getWeighid());
			placeboxinfo.setBoxid(weigh.getBoxid());
			placeboxinfo.setMemberid(orderinfo.getMemberid());
			placeboxinfo.setCompanyid(weigh.getCompanyid());
			placeboxinfo.setStatusenddate(now);
			
			Boxoutplace boxoutplace=new Boxoutplace();
			String ls_seq=SeqBPO.GetSequence("SEQ_BOXOUTPLACEID");  //获取序列号
			ClassHelper.copyProperties(dto, boxoutplace);
			ClassHelper.copyProperties(weigh, boxoutplace);
			boxoutplace.setOutplacetime(now);
			boxoutplace.setCreatedby(dto.getCreatedby());
			boxoutplace.setCreateddate(now);
			boxoutplace.setModifyby(dto.getCreatedby());
			boxoutplace.setModifydate(now);
			boxoutplace.setBoxoutplaceid(ls_seq);
			boxoutplace.setIsvalid("101");
			//boxoutplace.setBoxinplaceid(array_boxinplaceid[i]);	
			
			if (weigh.getConveyance().equals("102")){  //火车则出场即出园
				weigh.setWeighstatus("104");  //设置为已出园
				//增加出园记录及选待结算记录
				Boxoutpark boxoutpark=new Boxoutpark();
				ls_seq=SeqBPO.GetSequence("SEQ_BOXOUTPARKID");  //获取序列号
				ClassHelper.copyProperties(dto, boxoutpark);
				ClassHelper.copyProperties(weigh, boxoutpark);
				//boxoutpark.setBoxoutplaceid(boxoutplace.getBoxoutplaceid()); //设置对应的出场编号
				boxoutpark.setOutparktime(now);
				boxoutpark.setCreatedby(dto.getCreatedby());
				boxoutpark.setCreateddate(now);
				boxoutpark.setModifyby(dto.getCreatedby());
				boxoutpark.setModifydate(now);
				boxoutpark.setBoxoutparkid(ls_seq);
				boxoutpark.setIsvalid("101");			
				
				//产生应收基础数据
				Receivable receivable= new Receivable();
				ClassHelper.copyProperties(weigh, receivable);
				ls_seq=SeqBPO.GetSequence("SEQ_RECEIVABLEID");  //获取序列号
				receivable.setReceivableid(ls_seq);
				receivable.setReceivablestatus("100");  //状态默认为待提报
				receivable.setPrice(new Double(0));
				receivable.setAmount(new Double(0));
				receivable.setCreatedby(dto.getCreatedby());
				receivable.setCreateddate(now);
				receivable.setModifyby(dto.getCreatedby());
				receivable.setModifydate(now);
				
				//产生应付基础数据
				Payable payable= new Payable();
				ClassHelper.copyProperties(weigh, payable);
				ls_seq=SeqBPO.GetSequence("SEQ_PAYABLEID");  //获取序列号
				payable.setPayableid(ls_seq);
				payable.setPayablestatus("100");  //状态默认为待提报
				payable.setPrice(new Double(0));
				payable.setAmount(new Double(0));
				payable.setCreatedby(dto.getCreatedby());
				payable.setCreateddate(now);
				payable.setModifyby(dto.getCreatedby());
				payable.setModifydate(now);
				
				array_boxoutpark_add.add(boxoutpark);
				array_receivable_add.add(receivable);
				array_payable_add.add(payable);
			}
			
			array_weigh_mod.add(weigh);
			array_placeboxinfo_mod.add(placeboxinfo);
			array_boxoutplace_add.add(boxoutplace);
		}
		//数据保存
		try {
			trans.begin();					
			op.updateObjs(array_weigh_mod.toArray());					
			op.updateObjs(array_placeboxinfo_mod.toArray());
			op.saveObjs(array_boxoutplace_add.toArray());
			if (!(array_boxoutpark_add==null || array_boxoutpark_add.size()==0)){
				op.saveObjs(array_boxoutpark_add.toArray());
			}
			if (!(array_receivable_add==null || array_receivable_add.size()==0)){
				op.saveObjs(array_receivable_add.toArray());
			}
			if (!(array_payable_add==null || array_payable_add.size()==0)){
				op.saveObjs(array_payable_add.toArray());
			}
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出场,过磅单号:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出场,过磅单号:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("保存重箱批量出场成功!");
		return returnDTO;
	}

	/**
	 * 查询待出园的过磅单
	 * 注：未入园出园的也可以直接出园
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchBoxToOutPark(String msgdata)
			throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		StringBuffer sb=new StringBuffer("select a.weighid,b.boxid,b.orderid,b.goodstype,b.carnumber,b.closenumber,b.conveyance,b.placeboxid,b.companyid,dbo.sf_get_companyname(b.companyid) companyname,b.boxoutplaceid from weigh a,boxoutplace b where a.weighid=b.weighid and a.weighstatus in('101','102','103') and b.isvalid='101'");
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and b.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and b.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and b.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}

	/**
	 * 重箱批量出园确认
	 * 出园后要产生待收和待付结算单据 
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO BoxOutParkConfirm(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("过磅编号不能为空");
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
		String[] array_weighid=dto.getWeighid().split(",");
		String[] array_placeboxid=dto.getPlaceboxid().split(",");
		if (array_weighid.length!=array_placeboxid.length){
			returnDTO.setMsg("选中的过磅单与堆场箱位编号数量不匹配");
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_weigh_mod=new ArrayList();
		List array_boxoutpark_add=new ArrayList();
		List array_receivable_add=new ArrayList();  //应收
		List array_payable_add=new ArrayList();     //应付
		for (int i=0;i<array_weighid.length;i++){
			String ls_weighid=array_weighid[i];         //获取过磅单号
			String ls_placeboxid=array_placeboxid[i];   //获取堆场箱位编号
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//获取过磅单记录
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单记录不存在,过磅单号:"+ls_weighid);
				return returnDTO;
			} 
			/* 考虑到非出场也可以出园的情况，不进行状态控制验证
			if (!weigh.getWeighstatus().equals("103")){
				returnDTO.setMsg("过磅单状态异常,不允许进行出园操作,过磅单号:"+ls_weighid);
				return returnDTO;
			}
			*/
			weigh.setWeighstatus("104");  //设置为已出园			
			
			Boxoutpark boxoutpark=new Boxoutpark();
			String ls_seq=SeqBPO.GetSequence("SEQ_BOXOUTPARKID");  //获取序列号
			ClassHelper.copyProperties(dto, boxoutpark);
			ClassHelper.copyProperties(weigh, boxoutpark);
			boxoutpark.setOutparktime(now);
			boxoutpark.setCreatedby(dto.getCreatedby());
			boxoutpark.setCreateddate(now);
			boxoutpark.setModifyby(dto.getCreatedby());
			boxoutpark.setModifydate(now);
			boxoutpark.setBoxoutparkid(ls_seq);
			boxoutpark.setIsvalid("101");
			
			//产生应收基础数据
			Receivable receivable= new Receivable();
			ClassHelper.copyProperties(weigh, receivable);
			ls_seq=SeqBPO.GetSequence("SEQ_RECEIVABLEID");  //获取序列号
			receivable.setReceivableid(ls_seq);
			receivable.setReceivablestatus("100");  //状态默认为待提报
			receivable.setIsvalid("101");
			receivable.setPrice(new Double(0));
			receivable.setAmount(new Double(0));
			receivable.setCreatedby(dto.getCreatedby());
			receivable.setCreateddate(now);
			receivable.setModifyby(dto.getCreatedby());
			receivable.setModifydate(now);
			
			//产生应付基础数据
			Payable payable= new Payable();
			ClassHelper.copyProperties(weigh, payable);
			ls_seq=SeqBPO.GetSequence("SEQ_PAYABLEID");  //获取序列号
			payable.setPayableid(ls_seq);
			payable.setPayablestatus("100");  //状态默认为待提报
			payable.setIsvalid("101");
			payable.setPrice(new Double(0));
			payable.setAmount(new Double(0));
			payable.setCreatedby(dto.getCreatedby());
			payable.setCreateddate(now);
			payable.setModifyby(dto.getCreatedby());
			payable.setModifydate(now);
			
			
			array_weigh_mod.add(weigh);
			array_boxoutpark_add.add(boxoutpark);
			array_receivable_add.add(receivable);
			array_payable_add.add(payable);
		}
		//数据保存
		try {
			trans.begin();					
			op.updateObjs(array_weigh_mod.toArray());					
			op.saveObjs(array_boxoutpark_add.toArray());
			op.saveObjs(array_receivable_add.toArray());
			op.saveObjs(array_payable_add.toArray());
			trans.commit();
			NoticeUtil.Noticemsg("102", dto.getCreatedby(), now);
			NoticeUtil.Noticemsg("104", dto.getCreatedby(), now);
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出园,过磅单号:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出园,过磅单号:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("保存重箱批量出园成功!");
		return returnDTO;
	}

	/**
	 * 保存外部导入的过磅单据
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO SaveOutWeigh(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}
		Integer li_createdby=new Integer(0);
		List array_weigh_add = new ArrayList();
		JSONObject obj = JSONObject.fromObject(msgdata); // 解析JSON数据包
		JSONArray jarray = obj.getJSONArray("root");
		for (int i = 0; i < jarray.size(); i++){
            JSONObject array = jarray.getJSONObject(i);
            WeighDTO dto=new WeighDTO();
            ClassHelper.copyProperties(array, dto);  //获取一条外部过磅单据
            if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
            	returnDTO.setMsg("建立者不能为空,行数:"+(i+1));
    			return returnDTO;
            }
            li_createdby=dto.getCreatedby();
            if (dto.getWeighid()==null || dto.getWeighid().equals("")){
            	returnDTO.setMsg("过磅单号不能为空,行数:"+(i+1));
    			return returnDTO;
            }
            if (dto.getOrderid()==null || dto.getOrderid().equals("")){
            	returnDTO.setMsg("订单编号不能为空，过磅单号:"+dto.getWeighid());
    			return returnDTO;
            }
            if (dto.getWeighstatus()==null || dto.getWeighstatus().equals("100")){
            	returnDTO.setMsg("过磅单为临时过磅单，不允许导入，过磅单号:"+dto.getWeighid());
    			return returnDTO;
            }
            try {
				Orderinfo orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, dto.getOrderid());
				if (dto.getMemberid()==null || dto.getMemberid().intValue()==0) dto.setMemberid(orderinfo.getMemberid());
				if (dto.getGoodstype()==null || dto.getGoodstype().equals("")) dto.setGoodstype(orderinfo.getGoodstype());
				if (dto.getProductlevel()==null || dto.getProductlevel().equals("")) dto.setProductlevel(orderinfo.getProductlevel());
				if (dto.getConveyance()==null || dto.getConveyance().equals("")) dto.setConveyance(orderinfo.getConveyance());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
    			return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("订单记录不存在,订单编号:"+dto.getOrderid());
    			return returnDTO;
			}
            if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){  //未设置承运商的情况下主动采用订单中的承运商
            	try {
					String ls_companyid=op.executeMinMaxSQLQuery("select top 1 companyid from ordercompany where orderid='"+dto.getOrderid()+"'");
					dto.setCompanyid(new Integer(ls_companyid));
				} catch (OPException e) {
					returnDTO.setMsg(e.getMessage());
	    			return returnDTO;
				}
            }
            
            dto.setOutweighid(dto.getWeighid());  //设置外部过磅单号
            dto.setWeighid(SeqBPO.GetWeighid());  //设置系统过磅单            
            
            Weigh weigh=new Weigh();
            ClassHelper.copyProperties(dto, weigh);
            array_weigh_add.add(weigh);
        }
		//数据保存
		try {
			trans.begin();					
			op.saveObjs(array_weigh_add.toArray());
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"导入外部过磅单",e1.getMessage(),li_createdby);
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"导入外部过磅单",li_createdby);
		returnDTO.setSuccess("true");
		returnDTO.setMsg("导入外部过磅单成功!");
		return returnDTO;
	}


	private String SearchWeigh(OrderDTO dto) throws ApplicationException {
		StringBuffer sb=new StringBuffer("select weighid,outweighid,businesstype,orderid,carnumber,closenumber,companyid,dbo.sf_get_companyname(companyid) companyname,boxid,boxtype")
		.append(",storeagetime,boxaddr,outwarehouseid,sendcompany,tare,grossweight,netweight,goodstype,productlevel,water,impurity")
		.append(",capacity,smell,imperfecttotal,imperfectnum,imperfectnum2,weighstatus,createdby,createdorg,convert(varchar, createddate,120) createddate,modifyby,modifyorg,")
		.append("convert(varchar,modifydate,120) modifydate,packman,receivecompany,conveyance,memberid,dbo.sf_get_membername(memberid) membername,placeboxid,istwobox ")
		.append(" from weigh where weighstatus<>'100'");
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getOutweighid()==null || dto.getOutweighid().equals(""))){
			sb.append(" and outweighid='").append(dto.getOutweighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and orderid='").append(dto.getOrderid()).append("'");
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
	 * 过磅单查询
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchWeigh(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		return SearchWeigh(dto);
	}

	/**
	 * 查看单条过磅单详情
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String ViewWeigh(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			dto.setWeighid("-999999");
		}
		return SearchWeigh(dto); 
	}

	
	//查询已入场的过磅单=查询待出场的过磅单
	public String SearchInPlaceWeigh(String msgdata)
			throws ApplicationException {
		return SearchBoxToOutPlace(msgdata);
	}

	/**
	 * 过磅单入场确认批量取消
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO CancelInPlaceWeight(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getBoxinplaceid()==null || dto.getBoxinplaceid().equals("")){
			returnDTO.setMsg("入场序号不能为空");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_boxinplaceid=dto.getBoxinplaceid().split(",");
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_boxinplace_mod=new ArrayList();
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList();
		for (int i=0;i<array_boxinplaceid.length;i++){
			String ls_boxinplaceid=array_boxinplaceid[i];  //获取入场编号
			Boxinplace boxinplace=null;
			try {
				boxinplace=(Boxinplace)op.retrieveObj(Boxinplace.class, ls_boxinplaceid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("重箱入场记录不存在,入场单号:"+ls_boxinplaceid);
				return returnDTO;
			}
			boxinplace.setIsvalid("102");  //设置为无效
			boxinplace.setModifyby(dto.getCreatedby());
			boxinplace.setModifydate(now);			
			
			String ls_weighid=boxinplace.getWeighid();         //获取过磅单号
			String ls_placeboxid=boxinplace.getPlaceboxid();   //获取堆场箱位编号
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//获取过磅单记录
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单记录不存在,过磅单号:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("102")){
				returnDTO.setMsg("过磅单状态异常,不允许进行入场确认取消操作,过磅单号:"+ls_weighid);
				return returnDTO;
			}
			weigh.setWeighstatus("101");  //重新设置为已正式过磅
			
			//获取堆场箱位记录
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("堆场箱位记录不存在,单号:"+ls_placeboxid);
				return returnDTO;
			}
			
			placeboxinfo.setPlaceboxstatus("101");  //空闲
			placeboxinfo.setOrderid(null);
			placeboxinfo.setWeighid(null);
			placeboxinfo.setBoxid(null);
			placeboxinfo.setMemberid(null);
			placeboxinfo.setCompanyid(null);
			placeboxinfo.setStatusstartdate(now);
			
			array_boxinplace_mod.add(boxinplace);
			array_weigh_mod.add(weigh);
			array_placeboxinfo_mod.add(placeboxinfo);
			
		}
		//数据保存
		try {
			trans.begin();	
			op.updateObjs(array_boxinplace_mod.toArray());
			op.updateObjs(array_weigh_mod.toArray());					
			op.updateObjs(array_placeboxinfo_mod.toArray());
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量入场取消,过磅单号:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量入场取消,过磅单号:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("保存重箱批量入场取消成功!");
		return returnDTO;
	}

	/**
	 * 查询已出场的过磅单=查询待出园的过磅单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchOutPlaceWeigh(String msgdata)
			throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		StringBuffer sb=new StringBuffer("select a.weighid,b.boxid,b.orderid,b.goodstype,b.carnumber,b.closenumber,b.conveyance,b.placeboxid,b.companyid,dbo.sf_get_companyname(b.companyid) companyname,b.boxoutplaceid from weigh a,boxoutplace b where a.weighid=b.weighid and a.weighstatus='103' and b.isvalid='101'");
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and b.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and b.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and b.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}

	/**
	 * 过磅单出场批量取消
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO CancelOutPlaceWeight(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getBoxoutplaceid()==null || dto.getBoxoutplaceid().equals("")){
			returnDTO.setMsg("出场序号不能为空");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_boxoutplaceid=dto.getBoxoutplaceid().split(",");
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_boxoutplace_mod=new ArrayList();
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList();
		for (int i=0;i<array_boxoutplaceid.length;i++){
			String ls_boxoutplaceid=array_boxoutplaceid[i];  //获取入场编号
			Boxoutplace boxoutplace=null;
			try {
				boxoutplace=(Boxoutplace)op.retrieveObj(Boxoutplace.class, ls_boxoutplaceid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("重箱出场记录不存在,入场单号:"+ls_boxoutplaceid);
				return returnDTO;
			}
			boxoutplace.setIsvalid("102");  //设置为无效
			boxoutplace.setModifyby(dto.getCreatedby());
			boxoutplace.setModifydate(now);			
			
			String ls_weighid=boxoutplace.getWeighid();         //获取过磅单号
			String ls_placeboxid=boxoutplace.getPlaceboxid();   //获取堆场箱位编号
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//获取过磅单记录
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单记录不存在,过磅单号:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("103")){
				returnDTO.setMsg("过磅单状态异常,不允许进行出场确认取消操作,过磅单号:"+ls_weighid);
				return returnDTO;
			}
			weigh.setWeighstatus("102");  //重新设置为已入场未出场			
			
			//获取堆场箱位记录
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("堆场箱位记录不存在,单号:"+ls_placeboxid);
				return returnDTO;
			}
			
			placeboxinfo.setPlaceboxstatus("104");  //占用
			placeboxinfo.setOrderid(weigh.getOrderid());
			placeboxinfo.setWeighid(weigh.getWeighid());
			placeboxinfo.setBoxid(weigh.getBoxid());
			placeboxinfo.setMemberid(weigh.getMemberid());
			placeboxinfo.setCompanyid(weigh.getCompanyid());
			placeboxinfo.setStatusenddate(null);
			
			array_boxoutplace_mod.add(boxoutplace);
			array_weigh_mod.add(weigh);
			array_placeboxinfo_mod.add(placeboxinfo);
			
		}
		//数据保存
		try {
			trans.begin();	
			op.updateObjs(array_boxoutplace_mod.toArray());
			op.updateObjs(array_weigh_mod.toArray());					
			op.updateObjs(array_placeboxinfo_mod.toArray());
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出场取消,过磅单号:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出场取消,过磅单号:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("保存重箱批量出场取消成功!");
		return returnDTO;
	}

	/**
	 * 查询已出园的过磅单
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchOutParkWeigh(String msgdata)
			throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		StringBuffer sb=new StringBuffer("select a.weighid,b.boxid,b.orderid,b.goodstype,b.carnumber,b.closenumber,b.conveyance,b.placeboxid,b.companyid,dbo.sf_get_companyname(b.companyid) companyname,b.boxoutparkid from weigh a,boxoutpark b where a.weighid=b.weighid and a.weighstatus<>'100' and b.isvalid='101'");
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and b.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and b.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getPlaceboxid()==null || dto.getPlaceboxid().equals(""))){
			sb.append(" and b.placeboxid='").append(dto.getPlaceboxid()).append("'");
		}
		if (!(dto.getStarttime()==null || dto.getStarttime().equals(""))){
			sb.append(" and a.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and a.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}

	/**
	 * 出园批量取消
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO CancelOutParkWeight(String msgdata)
			throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getBoxoutparkid()==null || dto.getBoxoutparkid().equals("")){
			returnDTO.setMsg("出园序号不能为空");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String[] array_boxoutparkid=dto.getBoxoutparkid().split(",");
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_boxoutpark_mod=new ArrayList();
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList();
		List array_receivable_mod=new ArrayList();
		List array_payable_mod=new ArrayList();
		for (int i=0;i<array_boxoutparkid.length;i++){
			String ls_boxoutparkid=array_boxoutparkid[i];  //获取出园编号
			Boxoutpark boxoutpark=null;
			try {
				boxoutpark=(Boxoutpark)op.retrieveObj(Boxoutpark.class, ls_boxoutparkid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("重箱出园记录不存在,入场单号:"+ls_boxoutparkid);
				return returnDTO;
			}
			boxoutpark.setIsvalid("102");  //设置为无效
			boxoutpark.setModifyby(dto.getCreatedby());
			boxoutpark.setModifydate(now);			
			
			String ls_weighid=boxoutpark.getWeighid();         //获取过磅单号
			String ls_placeboxid=boxoutpark.getPlaceboxid();   //获取堆场箱位编号
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//获取过磅单记录
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单记录不存在,过磅单号:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("104")){
				returnDTO.setMsg("过磅单状态异常,不允许进行出园确认取消操作,过磅单号:"+ls_weighid);
				return returnDTO;
			}			
			weigh.setWeighstatus("103");  //重新设置为已出场
			
			//取消出园时的待结算数据需要作废  ???
			Receivable receivable=null;
			String hql="from Receivable where weighid='"+ls_weighid+"' and isvalid='101'";
			try {
				List list=op.retrieveObjs(hql);
				if (list==null){
					returnDTO.setMsg("过磅单应收待结数据不存在,过磅单号:"+ls_weighid);
					return returnDTO;
				}
				receivable=(Receivable)list.get(0);
				if (!receivable.getReceivablestatus().equals("100")){
					returnDTO.setMsg("该过磅单出园生成的应收结算单据已经处理,不允许进行出园取消,过磅单号:"+ls_weighid);
					return returnDTO;
				}
				receivable.setIsvalid("102");  //设置为无效
				receivable.setModifyby(dto.getCreatedby());
				receivable.setModifydate(now);
				array_receivable_mod.add(receivable);
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}
			
			Payable payable=null;
			hql="from Payable where weighid='"+ls_weighid+"' and isvalid='101'";
			try {
				List list=op.retrieveObjs(hql);
				if (list==null){
					returnDTO.setMsg("过磅单应付待结数据不存在,过磅单号:"+ls_weighid);
					return returnDTO;
				}
				payable=(Payable)list.get(0);
				if (!payable.getPayablestatus().equals("100")){
					returnDTO.setMsg("该过磅单出园生成的应付结算单据已经处理,不允许进行出园取消,过磅单号:"+ls_weighid);
					return returnDTO;
				}
				payable.setIsvalid("102");  //设置为无效
				payable.setModifyby(dto.getCreatedby());
				payable.setModifydate(now);
				array_payable_mod.add(payable);
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}
			
			//获取堆场箱位记录
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("堆场箱位记录不存在,单号:"+ls_placeboxid);
				return returnDTO;
			}
			
			placeboxinfo.setPlaceboxstatus("101");  //空闲
			placeboxinfo.setOrderid(null);
			placeboxinfo.setWeighid(null);
			placeboxinfo.setBoxid(null);
			placeboxinfo.setMemberid(null);
			placeboxinfo.setCompanyid(null);
			placeboxinfo.setStatusenddate(null);
			
			array_boxoutpark_mod.add(boxoutpark);
			array_weigh_mod.add(weigh);
			array_placeboxinfo_mod.add(placeboxinfo);
			
		}
		//数据保存
		try {
			trans.begin();	
			op.updateObjs(array_boxoutpark_mod.toArray());
			op.updateObjs(array_weigh_mod.toArray());					
			op.updateObjs(array_placeboxinfo_mod.toArray());	
			op.updateObjs(array_receivable_mod.toArray());
			op.updateObjs(array_payable_mod.toArray());
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出园取消,过磅单号:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"重箱批量出园取消,过磅单号:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("保存重箱批量出园取消成功!");
		return returnDTO;
	}

	
	public String SearchNoFinishOrder(String msgdata)
			throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		StringBuffer sb=new StringBuffer("select weighid,a.businesstype,a.orderid,carnumber,closenumber,companyid,dbo.sf_get_companyname(companyid) companyname,boxid,boxtype")
		.append(",storeagetime,boxaddr,outwarehouseid,a.sendcompany,tare,grossweight,netweight,a.goodstype,a.productlevel,a.water,a.impurity")
		.append(",a.capacity,a.smell,a.imperfecttotal,a.imperfectnum,a.imperfectnum2,weighstatus,a.createdby,a.createdorg,convert(varchar, a.createddate,120) createddate,a.modifyby,a.modifyorg,")
		.append("convert(varchar,a.modifydate,120) modifydate,packman,receivecompany,a.conveyance,a.memberid,dbo.sf_get_membername(a.memberid) membername,a.placeboxid,")
		.append("b.sendcontact,b.retrievecontact,b.orderstatus,dbo.sf_get_domainname('ORDERSTATUS',b.orderstatus) statusname")
		.append(" from weigh a,orderinfo b where a.orderid=b.orderid and weighstatus<>'100' and b.orderstatus<>'104'")
		.append(" and not exists(select 1 from waybillweigh c where c.weighid=a.weighid)");
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getOutweighid()==null || dto.getOutweighid().equals(""))){
			sb.append(" and a.outweighid='").append(dto.getOutweighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}	
		if (!(dto.getGoodstype()==null || dto.getGoodstype().equals(""))){
			sb.append(" and a.goodstype='").append(dto.getGoodstype()).append("'");
		}
		if (!(dto.getOrderstatus()==null || dto.getOrderstatus().equals(""))){
			sb.append(" and b.orderstatus='").append(dto.getOrderstatus()).append("'");
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

	/**
	 * 增加运单信息　
	 * 运单编号可批量录入,中间用,分割
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO AddWaybill(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("过磅单号不能为空");
			return returnDTO;
		} 
		if (dto.getStartdate()==null || dto.getStartdate().equals("")){
			returnDTO.setMsg("批次开始日期不能为空");
			return returnDTO;
		}
		if (dto.getEnddate()==null || dto.getEnddate().equals("")){
			returnDTO.setMsg("批次结束日期不能为空");
			return returnDTO;
		}
		if (dto.getBatchno()==null || dto.getBatchno().equals("")){
			returnDTO.setMsg("批次号不能为空");
			return returnDTO;
		}
		if (dto.getPrice()==null || dto.getPrice().doubleValue()==0){
			returnDTO.setMsg("批次单价错误,请检查...");
			return returnDTO;
		}
		if (dto.getWeight()==null || dto.getWeight().doubleValue()==0){
			returnDTO.setMsg("批次重量错误,请检查...");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		String hql="select count(*) from Waybill where batchno='"+dto.getBatchno()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("该运单批次号已经存在,批次号:"+dto.getBatchno());
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		
		Waybill waybill=new Waybill();
		String ls_seq=SeqBPO.GetSequence("SEQ_WAYBILLID");
		ClassHelper.copyProperties(dto, waybill);
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		waybill.setWaybillid(ls_seq);
		waybill.setWaybillstatus("100");  //未完成
		waybill.setIsvalid("101");  //有效
		
		waybill.setAmount(dto.getWeight()*dto.getPrice());
		waybill.setOrderid("");  //先初始订单编号
		waybill.setCreatedby(dto.getCreatedby());
		waybill.setCreateddate(now);
		waybill.setModifyby(dto.getCreatedby());
		waybill.setModifydate(now);
		
		
		String[] array_weigh=dto.getWeighid().split(",");  //分割本批次的过磅单记录		
		List array_waybillweigh_add=new ArrayList();
		for (int i=0;i<array_weigh.length;i++){
			String ls_weighid=array_weigh[i];
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("过磅单记录不存在,过磅单号:"+ls_weighid);
				return returnDTO;
			}
			if (waybill.getOrderid().equals("")){
				waybill.setOrderid(weigh.getOrderid());
			}else{
				if (!waybill.getOrderid().equals(weigh.getOrderid())){
					returnDTO.setMsg("同一批运单中不能包括多个订单,请检查");
					return returnDTO;
				}
			}
			
			Waybillweigh waybillweigh=new Waybillweigh();
			WaybillweighId id=new WaybillweighId();
			id.setWaybillid(ls_seq);
			id.setWeighid(ls_weighid);
			waybillweigh.setId(id);
			array_waybillweigh_add.add(waybillweigh);
		}
		//数据保存
		try {
			trans.begin();	
			op.saveObj(waybill);  //保存运单
			op.saveObjs(array_waybillweigh_add.toArray());  //保存运单明细
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"运单录入，运单编号:"+ls_seq,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"运单录入，运单编号:"+ls_seq,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("运单录入成功!");
		return returnDTO;
	}

	private String SearchWaybill(OrderDTO dto) throws ApplicationException {
		StringBuffer sb=new StringBuffer("select weighid,a.businesstype,a.orderid,carnumber,closenumber,companyid,dbo.sf_get_companyname(companyid) companyname,boxid,boxtype")
		.append(",storeagetime,boxaddr,outwarehouseid,a.sendcompany,tare,grossweight,netweight,a.goodstype,a.productlevel,a.water,a.impurity")
		.append(",a.capacity,a.smell,a.imperfecttotal,a.imperfectnum,a.imperfectnum2,weighstatus,a.createdby,a.createdorg,convert(varchar, c.createddate,120) createddate,a.modifyby,a.modifyorg,")
		.append("convert(varchar,c.modifydate,120) modifydate,packman,receivecompany,a.conveyance,a.memberid,dbo.sf_get_membername(a.memberid) membername,a.placeboxid,")
		.append("b.sendcontact,b.retrievecontact,b.orderstatus,dbo.sf_get_domainname('ORDERSTATUS',b.orderstatus) statusname,c.waybillid,")
		.append("convert(varchar, c.startdate,120) startdate,convert(varchar, c.enddate,120) enddate,c.batchno,c.price,c.weight,c.amount,c.waybillstatus,dbo.sf_get_domainname('WAYBILLSTATUS',c.waybillstatus) waybillstatusname")
		.append(" from weigh a,orderinfo b,waybill c where a.orderid=b.orderid and b.orderid=c.orderid and weighstatus<>'100'");
		if (!(dto.getWaybillid()==null || dto.getWaybillid().equals(""))){
			sb.append(" and c.waybillid='").append(dto.getWaybillid()).append("'");
		}
		if (!(dto.getBatchno()==null || dto.getBatchno().equals(""))){
			sb.append(" and c.batchno='").append(dto.getBatchno()).append("'");
		}
		if (!(dto.getWeighid()==null || dto.getWeighid().equals(""))){
			sb.append(" and a.weighid='").append(dto.getWeighid()).append("'");
		}
		if (!(dto.getOutweighid()==null || dto.getOutweighid().equals(""))){
			sb.append(" and a.outweighid='").append(dto.getOutweighid()).append("'");
		}
		if (!(dto.getBoxid()==null || dto.getBoxid().equals(""))){
			sb.append(" and a.boxid='").append(dto.getBoxid()).append("'");
		}
		if (!(dto.getOrderid()==null || dto.getOrderid().equals(""))){
			sb.append(" and a.orderid='").append(dto.getOrderid()).append("'");
		}
		if (!(dto.getConveyance()==null || dto.getConveyance().equals(""))){
			sb.append(" and a.conveyance='").append(dto.getConveyance()).append("'");
		}
		if (!(dto.getBusinesstype()==null || dto.getBusinesstype().equals(""))){
			sb.append(" and a.businesstype='").append(dto.getBusinesstype()).append("'");
		}	
		if (!(dto.getGoodstype()==null || dto.getGoodstype().equals(""))){
			sb.append(" and a.goodstype='").append(dto.getGoodstype()).append("'");
		}
		if (!(dto.getPackagetype()==null || dto.getPackagetype().equals(""))){
			sb.append(" and b.packagetype='").append(dto.getPackagetype()).append("'");
		}
		if (!(dto.getOrderstatus()==null || dto.getOrderstatus().equals(""))){
			sb.append(" and b.orderstatus='").append(dto.getOrderstatus()).append("'");
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
			sb.append(" and c.createddate >= '").append(dto.getStarttime()).append(" 00:00:00'");				
		}
		if (!(dto.getEndtime()==null || dto.getEndtime().equals(""))){
			sb.append(" and c.createddate <= '").append(dto.getEndtime()).append(" 23:59:59'");
		}
		return sb.toString();
	}
	public String SearchWaybill(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		return SearchWaybill(dto);
	}

	/**
	 * 根据运单编号查询运单明细
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchWaybillDetail(String msgdata)
			throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		if (dto.getWaybillid()==null || dto.getWaybillid().equals("")){
			dto.setWaybillid("-999999");
		}
		StringBuffer sb=new StringBuffer("select a.weighid,b.closenumber,b.carnumber,b.netweight,b.businesstype,")
		.append("dbo.sf_get_domainname('BUSINESSTYPE',b.businesstype) businesstypename,dbo.sf_get_domainname('CONVEYANCE',b.conveyance) conveyancename,")
		.append("convert(varchar, b.createddate,120) createddate from waybillweigh a,weigh b where a.weighid=b.weighid and a.waybillid='")
		.append(dto.getWaybillid()).append("'");
		
		return sb.toString();
	}

	
	public String ViewWaybill(String msgdata) throws ApplicationException {
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);		
		if (dto.getWaybillid()==null || dto.getWaybillid().equals("")){
			dto.setWaybillid("-999999");
		}
		return SearchWaybill(dto);
	}

	/**
	 * 运单资料修改
	 * 只允许修改内容：批次开始日期，批次结束日期，批次号，批次单价
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO ModWaybill(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getWaybillid()==null || dto.getWaybillid().equals("")){
			returnDTO.setMsg("运单编号不能为空");
			return returnDTO;
		}
		 
		if (dto.getStartdate()==null || dto.getStartdate().equals("")){
			returnDTO.setMsg("批次开始日期不能为空");
			return returnDTO;
		}
		if (dto.getEnddate()==null || dto.getEnddate().equals("")){
			returnDTO.setMsg("批次结束日期不能为空");
			return returnDTO;
		}
		if (dto.getBatchno()==null || dto.getBatchno().equals("")){
			returnDTO.setMsg("批次号不能为空");
			return returnDTO;
		}
		if (dto.getPrice()==null || dto.getPrice().doubleValue()==0){
			returnDTO.setMsg("批次单价错误,请检查...");
			return returnDTO;
		}
		/*
		if (dto.getWeight()==null || dto.getWeight().doubleValue()==0){
			returnDTO.setMsg("批次重量错误,请检查...");
			return returnDTO;
		}
		*/
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		Waybill waybill=null;
		try {
			waybill=(Waybill)op.retrieveObj(Waybill.class, dto.getWaybillid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("运单记录不存在,运单编号:"+dto.getWaybillid());
			return returnDTO;
		}
		if (waybill.getWaybillstatus().equals("101")){
			returnDTO.setMsg("运单已完成,不允许修改,运单编号:"+dto.getWaybillid());
			return returnDTO;
		}
		String hql="select count(*) from Waybill where batchno='"+dto.getBatchno()+"' and waybillid<>'"+dto.getWaybillid()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("批次号"+dto.getBatchno()+"已存在");
				return returnDTO;
			}
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		waybill.setStartdate(DateUtil.strToTimestamp(dto.getStartdate()));
		waybill.setEnddate(DateUtil.strToTimestamp(dto.getEnddate()));
		waybill.setBatchno(dto.getBatchno());
		waybill.setPrice(dto.getPrice());
		waybill.setAmount(waybill.getWeight()*dto.getPrice());  //重新设置金额
		waybill.setModifyby(dto.getCreatedby());
		waybill.setModifydate(now);
		
		//数据保存
		try {
			trans.begin();	
			op.updateObj(waybill);  //保存运单
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"运单修改，运单编号:"+dto.getWaybillid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"运单修改，运单编号:"+dto.getWaybillid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("运单修改成功!");
		return returnDTO;
	}

	
	public CommonDTO FinishWaybill(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getWaybillid()==null || dto.getWaybillid().equals("")){
			returnDTO.setMsg("运单编号不能为空");
			return returnDTO;
		}		
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}	
		Waybill waybill=null;
		try {
			waybill=(Waybill)op.retrieveObj(Waybill.class, dto.getWaybillid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("运单记录不存在,运单编号:"+dto.getWaybillid());
			return returnDTO;
		}
		if (waybill.getWaybillstatus().equals("101")){
			returnDTO.setMsg("运单已完成,不允许修改,运单编号:"+dto.getWaybillid());
			return returnDTO;
		}
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		waybill.setWaybillstatus("101");
		waybill.setModifyby(dto.getCreatedby());
		waybill.setModifydate(now);
		
		//数据保存
		try {
			trans.begin();	
			op.updateObj(waybill);  //保存运单
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"运单完成，运单编号:"+dto.getWaybillid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"运单完成，运单编号:"+dto.getWaybillid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("运单完成操作成功!");
		return returnDTO;
	}
	
	/**
	 * 导入订单(自动接收物流平台推送)
	 * @param msgdata
	 * 注：msgdata中json除了包括正常的orderinfo中的字段外，还需要增加company字段，如果有多个company则数据之间用,分割
	 * @return
	 * @throws ApplicationException
	 */
	 
	public ReturnDTO ImportOrder(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//验证
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("订单编号不能为空");
			return returnDTO;
		} 
		if (dto.getGoodstype()==null || dto.getGoodstype().equals("")){
			returnDTO.setMsg("发货品类不能为空");
			return returnDTO;
		} 
			
		if (dto.getPackagetype()==null || dto.getPackagetype().equals("")){
			returnDTO.setMsg("包装形式不能为空");
			return returnDTO;
		}		
		if (dto.getCarriagetype()==null || dto.getCarriagetype().equals("")){
			returnDTO.setMsg("运输方式不能为空");
			return returnDTO;
		}		
		if (dto.getConveyance()==null || dto.getConveyance().equals("")){
			//returnDTO.setMsg("运输工具不能为空");
			//return returnDTO;
			dto.setConveyance("101");  //未设置默认为汽车
		}
		if (dto.getQty()==null || dto.getQty().doubleValue()<=0){
			returnDTO.setMsg("请输入正确的运量(吨)");
			return returnDTO;
		}		
		if (dto.getStartareaid()==null || dto.getStartareaid().equals("")){
			returnDTO.setMsg("发货地不能为空");
			return returnDTO;
		}		
		if(dto.getStartaddress()==null || dto.getStartaddress().equals("")){
			returnDTO.setMsg("发货详细地址不能为空");
			return returnDTO;
		}		
		if (dto.getEndareaid()==null || dto.getEndareaid().equals("")){
			returnDTO.setMsg("收货地不能为空");
			return returnDTO;
		}		
		if(dto.getEndaddress()==null || dto.getEndaddress().equals("")){
			returnDTO.setMsg("发货详细地址不能为空");
			return returnDTO;
		}		
		if(dto.getExpectprice()==null || dto.getExpectprice().equals("")){
			returnDTO.setMsg("预期运价不能为空");
			return returnDTO;
		}		
		if (dto.getNeedcarriagesafe()==null || dto.getNeedcarriagesafe().equals("")){
			returnDTO.setMsg("是否需要开具运输保险不能为空");
			return returnDTO;
		}		
		if(dto.getHavestartdate()==null || dto.getHavestartdate().equals("")){
			returnDTO.setMsg("具备起运日期不能为空");
			return returnDTO;
		}		
		if(dto.getHavestartdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD HH24:MI:SS"))<0){
			returnDTO.setMsg("具备起运日期不能小于当前日期");
			return returnDTO;
		}		
		if(dto.getRequestfinishdate()==null || dto.getRequestfinishdate().equals("")){
			returnDTO.setMsg("要求完成日期不能为空");
			return returnDTO;
		}
		if(dto.getRequestfinishdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD HH24:MI:SS"))<0){
			returnDTO.setMsg("要求完成日期不能小于当前日期");
			return returnDTO;
		}				
		if(dto.getHavestartdate().compareTo(dto.getRequestfinishdate())>0){
			returnDTO.setMsg("要求完成日期不能小于具备起运日期");
			return returnDTO;
		}
		
		if (dto.getSendcompany()==null || dto.getSendcompany().equals("")){
			returnDTO.setMsg("委托单位不能为空");
			return returnDTO;
		}
		if (dto.getSendcontact()==null || dto.getSendcontact().equals("")){
			returnDTO.setMsg("委托联系人不能为空");
			return returnDTO;
		}	
		if (dto.getRetrievecompany()==null || dto.getRetrievecompany().equals("")){
			returnDTO.setMsg("收货单位不能为空");
			return returnDTO;
		}	
		if (dto.getRetrievecontact()==null || dto.getRetrievecontact().equals("")){
			returnDTO.setMsg("收货联系人不能为空");
			return returnDTO;
		}	
		
		if (dto.getConsigner()==null || dto.getConsigner().equals("")){
			returnDTO.setMsg("委托人不能为空");
			return returnDTO;
		}	
		if((dto.getConsignermobilephone()==null || dto.getConsignermobilephone().equals("")) && (dto.getConsignertelephone()==null || dto.getConsignertelephone().equals(""))){
			returnDTO.setMsg("委托人的联系手机和固定电话二选一");
			return returnDTO;
		}		
		if (dto.getReceiver()==null || dto.getReceiver().equals("")){
			returnDTO.setMsg("收货人不能为空");
			return returnDTO;
		}		
		
		if((dto.getReceivermobilephone()==null || dto.getReceivermobilephone().equals("")) && (dto.getReceivertelephone()==null || dto.getReceivertelephone().equals(""))){
			returnDTO.setMsg("收货人的联系手机和固定电话至少二选一");
			return returnDTO;
		}		
		if (dto.getInvoicerequest()==null || dto.getInvoicerequest().equals("")){
			returnDTO.setMsg("发票要求不能为空");
			return returnDTO;
		}		
		/*
		if (dto.getBusinesstype()==null || dto.getBusinesstype().equals("")){
			returnDTO.setMsg("msgdata数据包无效");
			return returnDTO;
		}		
		*/
		if (dto.getLosedate()==null || dto.getLosedate().equals("")){
			returnDTO.setMsg("挂单失效日期不能为空");
			return returnDTO;
		}		
		/*		
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("经办人编码不能为空");
			return returnDTO;
		}
		*/
		dto.setCreatedby(999999);   //默认用户
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("")){
			returnDTO.setMsg("承运商不能为空");
			return returnDTO;
		}
		
		
		//分割company:获取承运商列表
		Orderinfo orderinfo=new Orderinfo();
		ClassHelper.copyProperties(dto, orderinfo);
		orderinfo.setExtorderid(dto.getOrderid());   //将原物流订单编号设置为外部订单编号
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		orderinfo.setCreateddate(now);
		orderinfo.setModifydate(now);	
		orderinfo.setModifyby(dto.getCreatedby());
		//String ls_seq=SeqBPO.GetSequence("SEQ_ORDERID");  //获取序列号
		String ls_seq=SeqBPO.GetOrderid();  //获取订单编号
		if (ls_seq.equals("-1")){
			returnDTO.setMsg("获取订单编号时出错...");
			return returnDTO;
		} 
		orderinfo.setOrderid(ls_seq);
		orderinfo.setOrderstatus("103");  //有效	
		orderinfo.setOrdersource("102");  //物流订单导入
		orderinfo.setIsvalid("101");
		
		//判断委托方是否存在，不存在则增加
		boolean lb_add_member=false;
		Member member=null;
		String hql=" from Member where companyname='"+dto.getSendcompany()+"'";
		List list=null;
		try {
			list=op.retrieveObjs(hql);
			if (list==null){
				lb_add_member=true;
				member=new Member();
				String ls_memberid=SeqBPO.GetSequence("SEQ_MEMBERID");
				member.setMemberid(new Integer(ls_memberid));
				member.setCompanyname(dto.getSendcompany());
			}else{
				member=(Member)list.get(0);
			}
			orderinfo.setMemberid(member.getMemberid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		}		
		List array_ordercompany_add=new ArrayList();
		String[] array_company=dto.getCompanyid().split(",");  //分割承运商
		for (int i=0;i<array_company.length;i++){
			Ordercompany ordercompany=new Ordercompany();
			ordercompany.setOrderid(orderinfo.getOrderid());
			ordercompany.setCompanyid(new Integer(array_company[i]));
			ordercompany.setOrdercompanyid(SeqBPO.GetSequence("SEQ_ORDERCOMPANYID"));
			array_ordercompany_add.add(ordercompany);
		}
				
		//增加订单操作日志
		Orderlog orderlog=new Orderlog();
		orderlog.setOrderid(orderinfo.getOrderid());
		orderlog.setOrderopertype("101"); //创建订单
		orderlog.setMemo("物流订单导入");
		orderlog.setCreatedby(dto.getCreatedby());
		orderlog.setCreateddate(now);
		//数据保存
		try {
			trans.begin();				
			if (lb_add_member) op.saveObj(member);
			op.saveObj(orderinfo);	
			op.saveObjs(array_ordercompany_add.toArray());
			op.saveObj(orderlog);					
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"物流订单导入,订单编号:"+orderinfo.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"物流订单导入,订单编号:"+orderinfo.getOrderid(),dto.getCreatedby());
		returnDTO.setOrderid(orderinfo.getOrderid());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("物流订单导入成功!");
		return returnDTO;
	}
	
	
}
