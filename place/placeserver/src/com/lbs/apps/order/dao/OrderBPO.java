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
		//��ѯ����
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
	 * ���涩��
	 * @param msgdata
	 * ע��msgdata��json���˰���������orderinfo�е��ֶ��⣬����Ҫ����company�ֶΣ�����ж��company������֮����,�ָ�
	 * @return
	 * @throws ApplicationException
	 */
	 
	public ReturnDTO AddOrder(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getGoodstype()==null || dto.getGoodstype().equals("")){
			returnDTO.setMsg("����Ʒ�಻��Ϊ��");
			return returnDTO;
		} 
			
		if (dto.getPackagetype()==null || dto.getPackagetype().equals("")){
			returnDTO.setMsg("��װ��ʽ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getCarriagetype()==null || dto.getCarriagetype().equals("")){
			returnDTO.setMsg("���䷽ʽ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getConveyance()==null || dto.getConveyance().equals("")){
			returnDTO.setMsg("���乤�߲���Ϊ��");
			return returnDTO;
		}
		if (dto.getQty()==null || dto.getQty().doubleValue()<=0){
			returnDTO.setMsg("��������ȷ������(��)");
			return returnDTO;
		}		
		if (dto.getStartareaid()==null || dto.getStartareaid().equals("")){
			returnDTO.setMsg("�����ز���Ϊ��");
			return returnDTO;
		}		
		if(dto.getStartaddress()==null || dto.getStartaddress().equals("")){
			returnDTO.setMsg("������ϸ��ַ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getEndareaid()==null || dto.getEndareaid().equals("")){
			returnDTO.setMsg("�ջ��ز���Ϊ��");
			return returnDTO;
		}		
		if(dto.getEndaddress()==null || dto.getEndaddress().equals("")){
			returnDTO.setMsg("������ϸ��ַ����Ϊ��");
			return returnDTO;
		}		
		if(dto.getExpectprice()==null || dto.getExpectprice().equals("")){
			returnDTO.setMsg("Ԥ���˼۲���Ϊ��");
			return returnDTO;
		}		
		if (dto.getNeedcarriagesafe()==null || dto.getNeedcarriagesafe().equals("")){
			returnDTO.setMsg("�Ƿ���Ҫ�������䱣�ղ���Ϊ��");
			return returnDTO;
		}		
		if(dto.getHavestartdate()==null || dto.getHavestartdate().equals("")){
			returnDTO.setMsg("�߱��������ڲ���Ϊ��");
			return returnDTO;
		}		
		if(dto.getHavestartdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("�߱��������ڲ���С�ڵ�ǰ����");
			return returnDTO;
		}		
		if(dto.getRequestfinishdate()==null || dto.getRequestfinishdate().equals("")){
			returnDTO.setMsg("Ҫ��������ڲ���Ϊ��");
			return returnDTO;
		}
		if(dto.getRequestfinishdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("Ҫ��������ڲ���С�ڵ�ǰ����");
			return returnDTO;
		}				
		if(dto.getHavestartdate().compareTo(dto.getRequestfinishdate())>0){
			returnDTO.setMsg("Ҫ��������ڲ���С�ھ߱���������");
			return returnDTO;
		}		
		
		if (dto.getSendcompany()==null || dto.getSendcompany().equals("")){
			returnDTO.setMsg("ί�е�λ����Ϊ��");
			return returnDTO;
		}
		if (dto.getSendcontact()==null || dto.getSendcontact().equals("")){
			returnDTO.setMsg("ί����ϵ�˲���Ϊ��");
			return returnDTO;
		}	
		if (dto.getRetrievecompany()==null || dto.getRetrievecompany().equals("")){
			returnDTO.setMsg("�ջ���λ����Ϊ��");
			return returnDTO;
		}	
		if (dto.getRetrievecontact()==null || dto.getRetrievecontact().equals("")){
			returnDTO.setMsg("�ջ���ϵ�˲���Ϊ��");
			return returnDTO;
		}	
		
		if (dto.getConsigner()==null || dto.getConsigner().equals("")){
			returnDTO.setMsg("ί���˲���Ϊ��");
			return returnDTO;
		}	
		if((dto.getConsignermobilephone()==null || dto.getConsignermobilephone().equals("")) && (dto.getConsignertelephone()==null || dto.getConsignertelephone().equals(""))){
			returnDTO.setMsg("ί���˵���ϵ�ֻ��͹̶��绰��ѡһ");
			return returnDTO;
		}		
		if (dto.getReceiver()==null || dto.getReceiver().equals("")){
			returnDTO.setMsg("�ջ��˲���Ϊ��");
			return returnDTO;
		}		
		
		if((dto.getReceivermobilephone()==null || dto.getReceivermobilephone().equals("")) && (dto.getReceivertelephone()==null || dto.getReceivertelephone().equals(""))){
			returnDTO.setMsg("�ջ��˵���ϵ�ֻ��͹̶��绰���ٶ�ѡһ");
			return returnDTO;
		}		
		if (dto.getInvoicerequest()==null || dto.getInvoicerequest().equals("")){
			returnDTO.setMsg("��ƱҪ����Ϊ��");
			return returnDTO;
		}		
		/*
		if (dto.getBusinesstype()==null || dto.getBusinesstype().equals("")){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		*/
		if (dto.getLosedate()==null || dto.getLosedate().equals("")){
			returnDTO.setMsg("�ҵ�ʧЧ���ڲ���Ϊ��");
			return returnDTO;
		}		 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}		
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("")){
			returnDTO.setMsg("�����̲���Ϊ��");
			return returnDTO;
		}
		
		
		//�ָ�company:��ȡ�������б�
		Orderinfo orderinfo=new Orderinfo();
		ClassHelper.copyProperties(dto, orderinfo);
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		orderinfo.setCreateddate(now);
		orderinfo.setModifydate(now);	
		orderinfo.setModifyby(dto.getCreatedby());
		//String ls_seq=SeqBPO.GetSequence("SEQ_ORDERID");  //��ȡ���к�
		String ls_seq=SeqBPO.GetOrderid();  //��ȡ�������
		if (ls_seq.equals("-1")){
			returnDTO.setMsg("��ȡ�������ʱ����...");
			return returnDTO;
		} 
		orderinfo.setOrderid(ls_seq);
		orderinfo.setOrderstatus("103");  //��Ч	
		orderinfo.setPayamount(new Double(0));  //����ʵ�ʸ�����
		orderinfo.setSafeprice(new Double(0)); //�������䱣�յ���
		orderinfo.setPublishdeposit(new Double(0)); //������֤��
		orderinfo.setMemberbidbond(new Double(0));  //ί�з���Լ��֤��
		orderinfo.setCompanybidbond(new Double(0)); //��������Լ��֤��
		orderinfo.setOrdersource("101");  //���¶���¼��
		orderinfo.setIsplan("100");   //δ����
		orderinfo.setIsvalid("101");
		//�ж�ί�з��Ƿ���ڣ�������������
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
		String[] array_company=dto.getCompanyid().split(",");  //�ָ������
		for (int i=0;i<array_company.length;i++){
			Ordercompany ordercompany=new Ordercompany();
			ordercompany.setOrderid(orderinfo.getOrderid());
			ordercompany.setCompanyid(new Integer(array_company[i]));
			ordercompany.setOrdercompanyid(SeqBPO.GetSequence("SEQ_ORDERCOMPANYID"));
			array_ordercompany_add.add(ordercompany);
		}
				
		//���Ӷ���������־
		Orderlog orderlog=new Orderlog();
		orderlog.setOrderid(orderinfo.getOrderid());
		orderlog.setOrderopertype("101"); //��������
		orderlog.setMemo("���¶���¼��");
		orderlog.setCreatedby(dto.getCreatedby());
		orderlog.setCreateddate(now);
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���¶���¼��,�������:"+orderinfo.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���¶���¼��,�������:"+orderinfo.getOrderid(),dto.getCreatedby());
		returnDTO.setOrderid(orderinfo.getOrderid());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("���¶�������ɹ�!");
		return returnDTO;
	}

	
	public CommonDTO ModOrder(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getGoodstype()==null || dto.getGoodstype().equals("")){
			returnDTO.setMsg("����Ʒ�಻��Ϊ��");
			return returnDTO;
		} 
			
		if (dto.getPackagetype()==null || dto.getPackagetype().equals("")){
			returnDTO.setMsg("��װ��ʽ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getCarriagetype()==null || dto.getCarriagetype().equals("")){
			returnDTO.setMsg("���䷽ʽ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getConveyance()==null || dto.getConveyance().equals("")){
			returnDTO.setMsg("���乤�߲���Ϊ��");
			return returnDTO;
		}
		if (dto.getQty()==null || dto.getQty().doubleValue()<=0){
			returnDTO.setMsg("��������ȷ������(��)");
			return returnDTO;
		}		
		if (dto.getStartareaid()==null || dto.getStartareaid().equals("")){
			returnDTO.setMsg("�����ز���Ϊ��");
			return returnDTO;
		}		
		if(dto.getStartaddress()==null || dto.getStartaddress().equals("")){
			returnDTO.setMsg("������ϸ��ַ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getEndareaid()==null || dto.getEndareaid().equals("")){
			returnDTO.setMsg("�ջ��ز���Ϊ��");
			return returnDTO;
		}		
		if(dto.getEndaddress()==null || dto.getEndaddress().equals("")){
			returnDTO.setMsg("������ϸ��ַ����Ϊ��");
			return returnDTO;
		}		
		if(dto.getExpectprice()==null || dto.getExpectprice().equals("")){
			returnDTO.setMsg("Ԥ���˼۲���Ϊ��");
			return returnDTO;
		}		
		if (dto.getNeedcarriagesafe()==null || dto.getNeedcarriagesafe().equals("")){
			returnDTO.setMsg("�Ƿ���Ҫ�������䱣�ղ���Ϊ��");
			return returnDTO;
		}		
		if(dto.getHavestartdate()==null || dto.getHavestartdate().equals("")){
			returnDTO.setMsg("�߱��������ڲ���Ϊ��");
			return returnDTO;
		}		
		if(dto.getHavestartdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("�߱��������ڲ���С�ڵ�ǰ����");
			return returnDTO;
		}		
		if(dto.getRequestfinishdate()==null || dto.getRequestfinishdate().equals("")){
			returnDTO.setMsg("Ҫ��������ڲ���Ϊ��");
			return returnDTO;
		}
		if(dto.getRequestfinishdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD"))<0){
			returnDTO.setMsg("Ҫ��������ڲ���С�ڵ�ǰ����");
			return returnDTO;
		}				
		if(dto.getHavestartdate().compareTo(dto.getRequestfinishdate())>0){
			returnDTO.setMsg("Ҫ��������ڲ���С�ھ߱���������");
			return returnDTO;
		}
		
		if (dto.getSendcompany()==null || dto.getSendcompany().equals("")){
			returnDTO.setMsg("ί�е�λ����Ϊ��");
			return returnDTO;
		}
		if (dto.getSendcontact()==null || dto.getSendcontact().equals("")){
			returnDTO.setMsg("ί����ϵ�˲���Ϊ��");
			return returnDTO;
		}	
		if (dto.getRetrievecompany()==null || dto.getRetrievecompany().equals("")){
			returnDTO.setMsg("�ջ���λ����Ϊ��");
			return returnDTO;
		}	
		if (dto.getRetrievecontact()==null || dto.getRetrievecontact().equals("")){
			returnDTO.setMsg("�ջ���ϵ�˲���Ϊ��");
			return returnDTO;
		}	
		
		if (dto.getConsigner()==null || dto.getConsigner().equals("")){
			returnDTO.setMsg("ί���˲���Ϊ��");
			return returnDTO;
		}	
		if((dto.getConsignermobilephone()==null || dto.getConsignermobilephone().equals("")) && (dto.getConsignertelephone()==null || dto.getConsignertelephone().equals(""))){
			returnDTO.setMsg("ί���˵���ϵ�ֻ��͹̶��绰��ѡһ");
			return returnDTO;
		}		
		if (dto.getReceiver()==null || dto.getReceiver().equals("")){
			returnDTO.setMsg("�ջ��˲���Ϊ��");
			return returnDTO;
		}		
		
		if((dto.getReceivermobilephone()==null || dto.getReceivermobilephone().equals("")) && (dto.getReceivertelephone()==null || dto.getReceivertelephone().equals(""))){
			returnDTO.setMsg("�ջ��˵���ϵ�ֻ��͹̶��绰���ٶ�ѡһ");
			return returnDTO;
		}		
		if (dto.getInvoicerequest()==null || dto.getInvoicerequest().equals("")){
			returnDTO.setMsg("��ƱҪ����Ϊ��");
			return returnDTO;
		}		
		/*
		if (dto.getBusinesstype()==null || dto.getBusinesstype().equals("")){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		*/
		if (dto.getLosedate()==null || dto.getLosedate().equals("")){
			returnDTO.setMsg("�ҵ�ʧЧ���ڲ���Ϊ��");
			return returnDTO;
		}		 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}		
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("")){
			returnDTO.setMsg("�����̲���Ϊ��");
			return returnDTO;
		}
		
		
		Orderinfo orderinfo=null;
		try {
			orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, dto.getOrderid());
		} catch (OPException e3) {
			returnDTO.setMsg(e3.getMessage());
			return returnDTO;
		} catch (NotFindException e3) {
			returnDTO.setMsg("������¼������,�������:"+dto.getOrderid());
			return returnDTO;
		}
		if (orderinfo.getOrderstatus().equals("104")){
			returnDTO.setMsg("���������,�������޸�,�������:"+dto.getOrderid());
			return returnDTO;
		}
		Timestamp createddate=orderinfo.getCreateddate();
		Integer createdby=orderinfo.getCreatedby();
		Integer createdorg=orderinfo.getCreatedorg();
		String ls_isplan=orderinfo.getIsplan();
		ClassHelper.copyProperties(dto, orderinfo);
		orderinfo.setOrderstatus("103");  //��Ч
		orderinfo.setIsplan(ls_isplan);
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		orderinfo.setCreatedby(createdby);
		orderinfo.setCreateddate(createddate);
		orderinfo.setModifydate(now);	
		orderinfo.setModifyby(dto.getCreatedby());		
		orderinfo.setIsvalid("101");
		
		//�ж�ί�з��Ƿ���ڣ�������������
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
		
		//�ָ�company:��ȡ�������б�
		List array_ordercompany_add=new ArrayList();
		String[] array_company=dto.getCompanyid().split(",");  //�ָ������
		for (int i=0;i<array_company.length;i++){
			Ordercompany ordercompany=new Ordercompany();
			ordercompany.setOrdercompanyid(SeqBPO.GetSequence("SEQ_ORDERCOMPANYID"));
			ordercompany.setOrderid(orderinfo.getOrderid());
			ordercompany.setCompanyid(new Integer(array_company[i]));
			array_ordercompany_add.add(ordercompany);
		}
				
		//���Ӷ���������־
		Orderlog orderlog=new Orderlog();
		orderlog.setOrderid(orderinfo.getOrderid());
		orderlog.setOrderopertype("101"); //��������
		orderlog.setMemo("�޸Ķ�����Ϣ");
		orderlog.setCreatedby(dto.getCreatedby());
		orderlog.setCreateddate(now);
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���¶���¼��,�������:"+orderinfo.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���¶���¼��,�������:"+orderinfo.getOrderid(),dto.getCreatedby());
		returnDTO.setOrderid(orderinfo.getOrderid());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("���¶����޸ĳɹ�!");
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
	
	//��ȡ�����������б�
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
		dto.setOrderstatus("103");   //ֻ����Ч������������
		dto.setIsplan("100");
		return SearchOrder(dto);
	}

	
	public CommonDTO SaveOrderPlan(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getBoxarrivedate()==null || dto.getBoxarrivedate().equals("")){
			returnDTO.setMsg("Ԥ�Ƽ�װ�䵽�����ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getPackingsdate()==null || dto.getPackingsdate().equals("")){
			returnDTO.setMsg("Ԥ��װ�俪ʼ���ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getPackingedate()==null || dto.getPackingedate().equals("")){
			returnDTO.setMsg("Ԥ��װ��������ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getOutdate()==null || dto.getOutdate().equals("")){
			returnDTO.setMsg("Ԥ���˳����ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){  //���ݿ�����δ�м��ر���
			returnDTO.setMsg("�����˱��벻��Ϊ��");
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
			returnDTO.setMsg("������¼������,�������:"+dto.getOrderid());
			return returnDTO;
		}
		orderinfo.setIsplan("101");
		orderinfo.setModifyby(dto.getCreatedby());
		orderinfo.setModifydate(now);
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���涩�����ڼƻ�,�������:"+dto.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���涩�����ڼƻ�,�������:"+dto.getOrderid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("���涩�����ڼƻ��ɹ�!");
		return returnDTO;
	}

	
	public CommonDTO ModOrderPlan(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getBoxarrivedate()==null || dto.getBoxarrivedate().equals("")){
			returnDTO.setMsg("Ԥ�Ƽ�װ�䵽�����ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getPackingsdate()==null || dto.getPackingsdate().equals("")){
			returnDTO.setMsg("Ԥ��װ�俪ʼ���ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getPackingedate()==null || dto.getPackingedate().equals("")){
			returnDTO.setMsg("Ԥ��װ��������ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getOutdate()==null || dto.getOutdate().equals("")){
			returnDTO.setMsg("Ԥ���˳����ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){  //���ݿ�����δ�м��ر���
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		Orderschedule orderschedule=null;
		try {
			orderschedule=(Orderschedule)op.retrieveObj(Orderschedule.class, dto.getOrderid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�������ڼƻ���¼δ�ҵ�,�������:"+dto.getOrderid());
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
			returnDTO.setMsg("������¼������,�������:"+dto.getOrderid());
			return returnDTO;
		}
		orderinfo.setIsplan("101");
		orderinfo.setModifyby(dto.getCreatedby());
		orderinfo.setModifydate(now);
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�޸Ķ������ڼƻ�,�������:"+dto.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�޸Ķ������ڼƻ�,�������:"+dto.getOrderid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�޸Ķ������ڼƻ��ɹ�!");
		return returnDTO;
	}

	/**
	 * ������ɲ���
	 * @param msgdata
	 * @return
	 * ���û���˵����������(��)
	 * �������δ��ɵ��˵���������ж�����ɲ���
	 * 
	 * @throws ApplicationException
	 */
	public CommonDTO OrderFinish(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getFinishdate()==null || dto.getFinishdate().equals("")){
			returnDTO.setMsg("����������ڲ���Ϊ��");
			return returnDTO;
		} 
		if (dto.getFinishdesc()==null || dto.getFinishdesc().equals("")){
			returnDTO.setMsg("����������˵������Ϊ��");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){  //���ݿ�����δ�м��ر���
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		
		String hql="select count(*) from Waybill where orderid='"+dto.getOrderid()+"'";
		try {
			if (op.getCount(hql).intValue()<=0){
				returnDTO.setMsg("��δ¼���˵���Ϣ����������ж�����ɲ���");
				return returnDTO;
			}
			hql="select count(*) from Waybill where orderid='"+dto.getOrderid()+"' and waybillstatus='100'";
			if (op.getCount(hql).intValue()<=0){
				returnDTO.setMsg("������δ��ɵ��˵���¼���ݲ�������ж�����ɲ���");
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
			returnDTO.setMsg("������¼������,�������:"+dto.getOrderid());
			return returnDTO;
		}
		if (orderinfo.getOrderstatus().equals("104")){
			returnDTO.setMsg("���������,����Ҫ�ظ��������������:"+dto.getOrderid());
			return returnDTO;
		}
		orderinfo.setOrderstatus("104");
		orderinfo.setModifyby(dto.getCreatedby());
		orderinfo.setModifydate(now);
		
		
		Orderfinish orderfinish=new Orderfinish();  //ʵ����������ɼ�¼��
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�������,�������:"+dto.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}		
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�������,�������:"+dto.getOrderid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("������ɲ����ɹ�!");
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
	 * ��ѯ���볡�Ĺ�����
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
	 * ���������볡ȷ��
	 * ���������ʱ���������м���,�ָ�,��Ӧ�Ķѳ���λ���ͬ������,�ָ�
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
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
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
		String[] array_weighid=dto.getWeighid().split(",");
		String[] array_placeboxid=dto.getPlaceboxid().split(",");
		if (array_weighid.length!=array_placeboxid.length){
			returnDTO.setMsg("ѡ�еĹ�������ѳ���λ���������ƥ��");
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList();
		List array_boxinplace_add=new ArrayList();
		for (int i=0;i<array_weighid.length;i++){
			String ls_weighid=array_weighid[i];         //��ȡ��������
			String ls_placeboxid=array_placeboxid[i];   //��ȡ�ѳ���λ���
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//��ȡ��������¼
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("��������¼������,��������:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("101")){
				returnDTO.setMsg("������״̬�쳣,����������볡����,��������:"+ls_weighid);
				return returnDTO;
			}
			weigh.setPlaceboxid(ls_placeboxid);
			weigh.setWeighstatus("102");  //����Ϊ���볡
			
			//��ȡ�ѳ���λ��¼
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("�ѳ���λ��¼������,���:"+ls_placeboxid);
				return returnDTO;
			}
			Orderinfo orderinfo=null;
			try {
				orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, weigh.getOrderid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("������"+weigh.getWeighid()+"��Ӧ�Ķ�����¼����,�������:"+weigh.getOrderid());
				return returnDTO;
			}
			placeboxinfo.setPlaceboxstatus("104");  //ռ��
			placeboxinfo.setOrderid(weigh.getOrderid());
			placeboxinfo.setWeighid(weigh.getWeighid());
			placeboxinfo.setBoxid(weigh.getBoxid());
			placeboxinfo.setMemberid(orderinfo.getMemberid());
			placeboxinfo.setCompanyid(weigh.getCompanyid());
			placeboxinfo.setStatusstartdate(now);
			placeboxinfo.setIsempty("100"); //�����볡
			
			Boxinplace boxinplace=new Boxinplace();
			String ls_seq=SeqBPO.GetSequence("SEQ_BOXINPLACEID");  //��ȡ���к�
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
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���������볡,��������:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���������볡,��������:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�������������볡�ɹ�!");
		return returnDTO;
	}

	/**
	 * ��ѯ�������Ĺ�����
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
	 * ������������ȷ��
	 * ע����������乤��Ϊ��ʱ����ͬʱ��԰��������Ӧ��Ӧ������������  ???δ���
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
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
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
		String[] array_weighid=dto.getWeighid().split(",");
		String[] array_placeboxid=dto.getPlaceboxid().split(",");
		if (array_weighid.length!=array_placeboxid.length ){
			returnDTO.setMsg("ѡ�еĹ�������ѳ���λ���������ƥ��");
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
			String ls_weighid=array_weighid[i];         //��ȡ��������
			String ls_placeboxid=array_placeboxid[i];   //��ȡ�ѳ���λ���
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//��ȡ��������¼
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("��������¼������,��������:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("102")){
				returnDTO.setMsg("������״̬�쳣,��������г�������,��������:"+ls_weighid);
				return returnDTO;
			}
			weigh.setWeighstatus("103");  //����Ϊ�ѳ���
			
			//��ȡ�ѳ���λ��¼
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("�ѳ���λ��¼������,����:"+ls_placeboxid);
				return returnDTO;
			}
			Orderinfo orderinfo=null;
			try {
				orderinfo=(Orderinfo)op.retrieveObj(Orderinfo.class, weigh.getOrderid());
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("������"+weigh.getWeighid()+"��Ӧ�Ķ�����¼����,�������:"+weigh.getOrderid());
				return returnDTO;
			}
			placeboxinfo.setPlaceboxstatus("101");  //����
			placeboxinfo.setOrderid(weigh.getOrderid());
			placeboxinfo.setWeighid(weigh.getWeighid());
			placeboxinfo.setBoxid(weigh.getBoxid());
			placeboxinfo.setMemberid(orderinfo.getMemberid());
			placeboxinfo.setCompanyid(weigh.getCompanyid());
			placeboxinfo.setStatusenddate(now);
			
			Boxoutplace boxoutplace=new Boxoutplace();
			String ls_seq=SeqBPO.GetSequence("SEQ_BOXOUTPLACEID");  //��ȡ���к�
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
			
			if (weigh.getConveyance().equals("102")){  //�����������԰
				weigh.setWeighstatus("104");  //����Ϊ�ѳ�԰
				//���ӳ�԰��¼��ѡ�������¼
				Boxoutpark boxoutpark=new Boxoutpark();
				ls_seq=SeqBPO.GetSequence("SEQ_BOXOUTPARKID");  //��ȡ���к�
				ClassHelper.copyProperties(dto, boxoutpark);
				ClassHelper.copyProperties(weigh, boxoutpark);
				//boxoutpark.setBoxoutplaceid(boxoutplace.getBoxoutplaceid()); //���ö�Ӧ�ĳ������
				boxoutpark.setOutparktime(now);
				boxoutpark.setCreatedby(dto.getCreatedby());
				boxoutpark.setCreateddate(now);
				boxoutpark.setModifyby(dto.getCreatedby());
				boxoutpark.setModifydate(now);
				boxoutpark.setBoxoutparkid(ls_seq);
				boxoutpark.setIsvalid("101");			
				
				//����Ӧ�ջ�������
				Receivable receivable= new Receivable();
				ClassHelper.copyProperties(weigh, receivable);
				ls_seq=SeqBPO.GetSequence("SEQ_RECEIVABLEID");  //��ȡ���к�
				receivable.setReceivableid(ls_seq);
				receivable.setReceivablestatus("100");  //״̬Ĭ��Ϊ���ᱨ
				receivable.setPrice(new Double(0));
				receivable.setAmount(new Double(0));
				receivable.setCreatedby(dto.getCreatedby());
				receivable.setCreateddate(now);
				receivable.setModifyby(dto.getCreatedby());
				receivable.setModifydate(now);
				
				//����Ӧ����������
				Payable payable= new Payable();
				ClassHelper.copyProperties(weigh, payable);
				ls_seq=SeqBPO.GetSequence("SEQ_PAYABLEID");  //��ȡ���к�
				payable.setPayableid(ls_seq);
				payable.setPayablestatus("100");  //״̬Ĭ��Ϊ���ᱨ
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
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������,��������:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������,��������:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�����������������ɹ�!");
		return returnDTO;
	}

	/**
	 * ��ѯ����԰�Ĺ�����
	 * ע��δ��԰��԰��Ҳ����ֱ�ӳ�԰
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
	 * ����������԰ȷ��
	 * ��԰��Ҫ�������պʹ������㵥�� 
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
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
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
		String[] array_weighid=dto.getWeighid().split(",");
		String[] array_placeboxid=dto.getPlaceboxid().split(",");
		if (array_weighid.length!=array_placeboxid.length){
			returnDTO.setMsg("ѡ�еĹ�������ѳ���λ���������ƥ��");
			return returnDTO;
		}
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_weigh_mod=new ArrayList();
		List array_boxoutpark_add=new ArrayList();
		List array_receivable_add=new ArrayList();  //Ӧ��
		List array_payable_add=new ArrayList();     //Ӧ��
		for (int i=0;i<array_weighid.length;i++){
			String ls_weighid=array_weighid[i];         //��ȡ��������
			String ls_placeboxid=array_placeboxid[i];   //��ȡ�ѳ���λ���
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//��ȡ��������¼
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("��������¼������,��������:"+ls_weighid);
				return returnDTO;
			} 
			/* ���ǵ��ǳ���Ҳ���Գ�԰�������������״̬������֤
			if (!weigh.getWeighstatus().equals("103")){
				returnDTO.setMsg("������״̬�쳣,��������г�԰����,��������:"+ls_weighid);
				return returnDTO;
			}
			*/
			weigh.setWeighstatus("104");  //����Ϊ�ѳ�԰			
			
			Boxoutpark boxoutpark=new Boxoutpark();
			String ls_seq=SeqBPO.GetSequence("SEQ_BOXOUTPARKID");  //��ȡ���к�
			ClassHelper.copyProperties(dto, boxoutpark);
			ClassHelper.copyProperties(weigh, boxoutpark);
			boxoutpark.setOutparktime(now);
			boxoutpark.setCreatedby(dto.getCreatedby());
			boxoutpark.setCreateddate(now);
			boxoutpark.setModifyby(dto.getCreatedby());
			boxoutpark.setModifydate(now);
			boxoutpark.setBoxoutparkid(ls_seq);
			boxoutpark.setIsvalid("101");
			
			//����Ӧ�ջ�������
			Receivable receivable= new Receivable();
			ClassHelper.copyProperties(weigh, receivable);
			ls_seq=SeqBPO.GetSequence("SEQ_RECEIVABLEID");  //��ȡ���к�
			receivable.setReceivableid(ls_seq);
			receivable.setReceivablestatus("100");  //״̬Ĭ��Ϊ���ᱨ
			receivable.setIsvalid("101");
			receivable.setPrice(new Double(0));
			receivable.setAmount(new Double(0));
			receivable.setCreatedby(dto.getCreatedby());
			receivable.setCreateddate(now);
			receivable.setModifyby(dto.getCreatedby());
			receivable.setModifydate(now);
			
			//����Ӧ����������
			Payable payable= new Payable();
			ClassHelper.copyProperties(weigh, payable);
			ls_seq=SeqBPO.GetSequence("SEQ_PAYABLEID");  //��ȡ���к�
			payable.setPayableid(ls_seq);
			payable.setPayablestatus("100");  //״̬Ĭ��Ϊ���ᱨ
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
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"����������԰,��������:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"����������԰,��������:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("��������������԰�ɹ�!");
		return returnDTO;
	}

	/**
	 * �����ⲿ����Ĺ�������
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO SaveOutWeigh(String msgdata) throws ApplicationException {
		CommonDTO returnDTO=new CommonDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}
		Integer li_createdby=new Integer(0);
		List array_weigh_add = new ArrayList();
		JSONObject obj = JSONObject.fromObject(msgdata); // ����JSON���ݰ�
		JSONArray jarray = obj.getJSONArray("root");
		for (int i = 0; i < jarray.size(); i++){
            JSONObject array = jarray.getJSONObject(i);
            WeighDTO dto=new WeighDTO();
            ClassHelper.copyProperties(array, dto);  //��ȡһ���ⲿ��������
            if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
            	returnDTO.setMsg("�����߲���Ϊ��,����:"+(i+1));
    			return returnDTO;
            }
            li_createdby=dto.getCreatedby();
            if (dto.getWeighid()==null || dto.getWeighid().equals("")){
            	returnDTO.setMsg("�������Ų���Ϊ��,����:"+(i+1));
    			return returnDTO;
            }
            if (dto.getOrderid()==null || dto.getOrderid().equals("")){
            	returnDTO.setMsg("������Ų���Ϊ�գ���������:"+dto.getWeighid());
    			return returnDTO;
            }
            if (dto.getWeighstatus()==null || dto.getWeighstatus().equals("100")){
            	returnDTO.setMsg("������Ϊ��ʱ���������������룬��������:"+dto.getWeighid());
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
				returnDTO.setMsg("������¼������,�������:"+dto.getOrderid());
    			return returnDTO;
			}
            if (dto.getCompanyid()==null || dto.getCompanyid().intValue()==0){  //δ���ó����̵�������������ö����еĳ�����
            	try {
					String ls_companyid=op.executeMinMaxSQLQuery("select top 1 companyid from ordercompany where orderid='"+dto.getOrderid()+"'");
					dto.setCompanyid(new Integer(ls_companyid));
				} catch (OPException e) {
					returnDTO.setMsg(e.getMessage());
	    			return returnDTO;
				}
            }
            
            dto.setOutweighid(dto.getWeighid());  //�����ⲿ��������
            dto.setWeighid(SeqBPO.GetWeighid());  //����ϵͳ������            
            
            Weigh weigh=new Weigh();
            ClassHelper.copyProperties(dto, weigh);
            array_weigh_add.add(weigh);
        }
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�����ⲿ������",e1.getMessage(),li_createdby);
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�����ⲿ������",li_createdby);
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�����ⲿ�������ɹ�!");
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
	 * ��������ѯ
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
	 * �鿴��������������
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

	
	//��ѯ���볡�Ĺ�����=��ѯ�������Ĺ�����
	public String SearchInPlaceWeigh(String msgdata)
			throws ApplicationException {
		return SearchBoxToOutPlace(msgdata);
	}

	/**
	 * �������볡ȷ������ȡ��
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
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getBoxinplaceid()==null || dto.getBoxinplaceid().equals("")){
			returnDTO.setMsg("�볡��Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		String[] array_boxinplaceid=dto.getBoxinplaceid().split(",");
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_boxinplace_mod=new ArrayList();
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList();
		for (int i=0;i<array_boxinplaceid.length;i++){
			String ls_boxinplaceid=array_boxinplaceid[i];  //��ȡ�볡���
			Boxinplace boxinplace=null;
			try {
				boxinplace=(Boxinplace)op.retrieveObj(Boxinplace.class, ls_boxinplaceid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("�����볡��¼������,�볡����:"+ls_boxinplaceid);
				return returnDTO;
			}
			boxinplace.setIsvalid("102");  //����Ϊ��Ч
			boxinplace.setModifyby(dto.getCreatedby());
			boxinplace.setModifydate(now);			
			
			String ls_weighid=boxinplace.getWeighid();         //��ȡ��������
			String ls_placeboxid=boxinplace.getPlaceboxid();   //��ȡ�ѳ���λ���
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//��ȡ��������¼
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("��������¼������,��������:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("102")){
				returnDTO.setMsg("������״̬�쳣,����������볡ȷ��ȡ������,��������:"+ls_weighid);
				return returnDTO;
			}
			weigh.setWeighstatus("101");  //��������Ϊ����ʽ����
			
			//��ȡ�ѳ���λ��¼
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("�ѳ���λ��¼������,����:"+ls_placeboxid);
				return returnDTO;
			}
			
			placeboxinfo.setPlaceboxstatus("101");  //����
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
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���������볡ȡ��,��������:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"���������볡ȡ��,��������:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�������������볡ȡ���ɹ�!");
		return returnDTO;
	}

	/**
	 * ��ѯ�ѳ����Ĺ�����=��ѯ����԰�Ĺ�����
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
	 * ��������������ȡ��
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
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getBoxoutplaceid()==null || dto.getBoxoutplaceid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		String[] array_boxoutplaceid=dto.getBoxoutplaceid().split(",");
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		String ls_weigh_temp="";
		List array_boxoutplace_mod=new ArrayList();
		List array_weigh_mod=new ArrayList();
		List array_placeboxinfo_mod=new ArrayList();
		for (int i=0;i<array_boxoutplaceid.length;i++){
			String ls_boxoutplaceid=array_boxoutplaceid[i];  //��ȡ�볡���
			Boxoutplace boxoutplace=null;
			try {
				boxoutplace=(Boxoutplace)op.retrieveObj(Boxoutplace.class, ls_boxoutplaceid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("���������¼������,�볡����:"+ls_boxoutplaceid);
				return returnDTO;
			}
			boxoutplace.setIsvalid("102");  //����Ϊ��Ч
			boxoutplace.setModifyby(dto.getCreatedby());
			boxoutplace.setModifydate(now);			
			
			String ls_weighid=boxoutplace.getWeighid();         //��ȡ��������
			String ls_placeboxid=boxoutplace.getPlaceboxid();   //��ȡ�ѳ���λ���
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//��ȡ��������¼
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("��������¼������,��������:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("103")){
				returnDTO.setMsg("������״̬�쳣,��������г���ȷ��ȡ������,��������:"+ls_weighid);
				return returnDTO;
			}
			weigh.setWeighstatus("102");  //��������Ϊ���볡δ����			
			
			//��ȡ�ѳ���λ��¼
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("�ѳ���λ��¼������,����:"+ls_placeboxid);
				return returnDTO;
			}
			
			placeboxinfo.setPlaceboxstatus("104");  //ռ��
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
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������ȡ��,��������:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������ȡ��,��������:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("����������������ȡ���ɹ�!");
		return returnDTO;
	}

	/**
	 * ��ѯ�ѳ�԰�Ĺ�����
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
	 * ��԰����ȡ��
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
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getBoxoutparkid()==null || dto.getBoxoutparkid().equals("")){
			returnDTO.setMsg("��԰��Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
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
			String ls_boxoutparkid=array_boxoutparkid[i];  //��ȡ��԰���
			Boxoutpark boxoutpark=null;
			try {
				boxoutpark=(Boxoutpark)op.retrieveObj(Boxoutpark.class, ls_boxoutparkid);
			} catch (OPException e1) {
				returnDTO.setMsg(e1.getMessage());
				return returnDTO;
			} catch (NotFindException e1) {
				returnDTO.setMsg("�����԰��¼������,�볡����:"+ls_boxoutparkid);
				return returnDTO;
			}
			boxoutpark.setIsvalid("102");  //����Ϊ��Ч
			boxoutpark.setModifyby(dto.getCreatedby());
			boxoutpark.setModifydate(now);			
			
			String ls_weighid=boxoutpark.getWeighid();         //��ȡ��������
			String ls_placeboxid=boxoutpark.getPlaceboxid();   //��ȡ�ѳ���λ���
			ls_weigh_temp=ls_weigh_temp+","+ls_weighid;
			//��ȡ��������¼
			Weigh weigh=null;
			try {
				weigh=(Weigh)op.retrieveObj(Weigh.class, ls_weighid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("��������¼������,��������:"+ls_weighid);
				return returnDTO;
			} 
			if (!weigh.getWeighstatus().equals("104")){
				returnDTO.setMsg("������״̬�쳣,��������г�԰ȷ��ȡ������,��������:"+ls_weighid);
				return returnDTO;
			}			
			weigh.setWeighstatus("103");  //��������Ϊ�ѳ���
			
			//ȡ����԰ʱ�Ĵ�����������Ҫ����  ???
			Receivable receivable=null;
			String hql="from Receivable where weighid='"+ls_weighid+"' and isvalid='101'";
			try {
				List list=op.retrieveObjs(hql);
				if (list==null){
					returnDTO.setMsg("������Ӧ�մ������ݲ�����,��������:"+ls_weighid);
					return returnDTO;
				}
				receivable=(Receivable)list.get(0);
				if (!receivable.getReceivablestatus().equals("100")){
					returnDTO.setMsg("�ù�������԰���ɵ�Ӧ�ս��㵥���Ѿ�����,��������г�԰ȡ��,��������:"+ls_weighid);
					return returnDTO;
				}
				receivable.setIsvalid("102");  //����Ϊ��Ч
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
					returnDTO.setMsg("������Ӧ���������ݲ�����,��������:"+ls_weighid);
					return returnDTO;
				}
				payable=(Payable)list.get(0);
				if (!payable.getPayablestatus().equals("100")){
					returnDTO.setMsg("�ù�������԰���ɵ�Ӧ�����㵥���Ѿ�����,��������г�԰ȡ��,��������:"+ls_weighid);
					return returnDTO;
				}
				payable.setIsvalid("102");  //����Ϊ��Ч
				payable.setModifyby(dto.getCreatedby());
				payable.setModifydate(now);
				array_payable_mod.add(payable);
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}
			
			//��ȡ�ѳ���λ��¼
			Placeboxinfo placeboxinfo=null;
			try {
				placeboxinfo=(Placeboxinfo)op.retrieveObj(Placeboxinfo.class, ls_placeboxid);
			} catch (OPException e) {
				returnDTO.setMsg(e.getMessage());
				return returnDTO;
			} catch (NotFindException e) {
				returnDTO.setMsg("�ѳ���λ��¼������,����:"+ls_placeboxid);
				return returnDTO;
			}
			
			placeboxinfo.setPlaceboxstatus("101");  //����
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
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"����������԰ȡ��,��������:"+ls_weigh_temp,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"����������԰ȡ��,��������:"+ls_weigh_temp,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("��������������԰ȡ���ɹ�!");
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
	 * �����˵���Ϣ��
	 * �˵���ſ�����¼��,�м���,�ָ�
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO AddWaybill(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getWeighid()==null || dto.getWeighid().equals("")){
			returnDTO.setMsg("�������Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getStartdate()==null || dto.getStartdate().equals("")){
			returnDTO.setMsg("���ο�ʼ���ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getEnddate()==null || dto.getEnddate().equals("")){
			returnDTO.setMsg("���ν������ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getBatchno()==null || dto.getBatchno().equals("")){
			returnDTO.setMsg("���κŲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getPrice()==null || dto.getPrice().doubleValue()==0){
			returnDTO.setMsg("���ε��۴���,����...");
			return returnDTO;
		}
		if (dto.getWeight()==null || dto.getWeight().doubleValue()==0){
			returnDTO.setMsg("������������,����...");
			return returnDTO;
		}
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		String hql="select count(*) from Waybill where batchno='"+dto.getBatchno()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("���˵����κ��Ѿ�����,���κ�:"+dto.getBatchno());
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
		waybill.setWaybillstatus("100");  //δ���
		waybill.setIsvalid("101");  //��Ч
		
		waybill.setAmount(dto.getWeight()*dto.getPrice());
		waybill.setOrderid("");  //�ȳ�ʼ�������
		waybill.setCreatedby(dto.getCreatedby());
		waybill.setCreateddate(now);
		waybill.setModifyby(dto.getCreatedby());
		waybill.setModifydate(now);
		
		
		String[] array_weigh=dto.getWeighid().split(",");  //�ָ���εĹ�������¼		
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
				returnDTO.setMsg("��������¼������,��������:"+ls_weighid);
				return returnDTO;
			}
			if (waybill.getOrderid().equals("")){
				waybill.setOrderid(weigh.getOrderid());
			}else{
				if (!waybill.getOrderid().equals(weigh.getOrderid())){
					returnDTO.setMsg("ͬһ���˵��в��ܰ����������,����");
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
		//���ݱ���
		try {
			trans.begin();	
			op.saveObj(waybill);  //�����˵�
			op.saveObjs(array_waybillweigh_add.toArray());  //�����˵���ϸ
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�˵�¼�룬�˵����:"+ls_seq,e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�˵�¼�룬�˵����:"+ls_seq,dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�˵�¼��ɹ�!");
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
	 * �����˵���Ų�ѯ�˵���ϸ
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
	 * �˵������޸�
	 * ֻ�����޸����ݣ����ο�ʼ���ڣ����ν������ڣ����κţ����ε���
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public CommonDTO ModWaybill(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getWaybillid()==null || dto.getWaybillid().equals("")){
			returnDTO.setMsg("�˵���Ų���Ϊ��");
			return returnDTO;
		}
		 
		if (dto.getStartdate()==null || dto.getStartdate().equals("")){
			returnDTO.setMsg("���ο�ʼ���ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getEnddate()==null || dto.getEnddate().equals("")){
			returnDTO.setMsg("���ν������ڲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getBatchno()==null || dto.getBatchno().equals("")){
			returnDTO.setMsg("���κŲ���Ϊ��");
			return returnDTO;
		}
		if (dto.getPrice()==null || dto.getPrice().doubleValue()==0){
			returnDTO.setMsg("���ε��۴���,����...");
			return returnDTO;
		}
		/*
		if (dto.getWeight()==null || dto.getWeight().doubleValue()==0){
			returnDTO.setMsg("������������,����...");
			return returnDTO;
		}
		*/
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		Waybill waybill=null;
		try {
			waybill=(Waybill)op.retrieveObj(Waybill.class, dto.getWaybillid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�˵���¼������,�˵����:"+dto.getWaybillid());
			return returnDTO;
		}
		if (waybill.getWaybillstatus().equals("101")){
			returnDTO.setMsg("�˵������,�������޸�,�˵����:"+dto.getWaybillid());
			return returnDTO;
		}
		String hql="select count(*) from Waybill where batchno='"+dto.getBatchno()+"' and waybillid<>'"+dto.getWaybillid()+"'";
		try {
			if (op.getCount(hql).intValue()>0){
				returnDTO.setMsg("���κ�"+dto.getBatchno()+"�Ѵ���");
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
		waybill.setAmount(waybill.getWeight()*dto.getPrice());  //�������ý��
		waybill.setModifyby(dto.getCreatedby());
		waybill.setModifydate(now);
		
		//���ݱ���
		try {
			trans.begin();	
			op.updateObj(waybill);  //�����˵�
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�˵��޸ģ��˵����:"+dto.getWaybillid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�˵��޸ģ��˵����:"+dto.getWaybillid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�˵��޸ĳɹ�!");
		return returnDTO;
	}

	
	public CommonDTO FinishWaybill(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getWaybillid()==null || dto.getWaybillid().equals("")){
			returnDTO.setMsg("�˵���Ų���Ϊ��");
			return returnDTO;
		}		
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}	
		Waybill waybill=null;
		try {
			waybill=(Waybill)op.retrieveObj(Waybill.class, dto.getWaybillid());
		} catch (OPException e) {
			returnDTO.setMsg(e.getMessage());
			return returnDTO;
		} catch (NotFindException e) {
			returnDTO.setMsg("�˵���¼������,�˵����:"+dto.getWaybillid());
			return returnDTO;
		}
		if (waybill.getWaybillstatus().equals("101")){
			returnDTO.setMsg("�˵������,�������޸�,�˵����:"+dto.getWaybillid());
			return returnDTO;
		}
		
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());
		waybill.setWaybillstatus("101");
		waybill.setModifyby(dto.getCreatedby());
		waybill.setModifydate(now);
		
		//���ݱ���
		try {
			trans.begin();	
			op.updateObj(waybill);  //�����˵�
			trans.commit();
		} catch (OPException e1) {
		    try {
				trans.rollback();
			} catch (OPException e2) {
				returnDTO.setMsg(e2.getMessage());
				return returnDTO;
			}	
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�˵���ɣ��˵����:"+dto.getWaybillid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"�˵���ɣ��˵����:"+dto.getWaybillid(),dto.getCreatedby());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("�˵���ɲ����ɹ�!");
		return returnDTO;
	}
	
	/**
	 * ���붩��(�Զ���������ƽ̨����)
	 * @param msgdata
	 * ע��msgdata��json���˰���������orderinfo�е��ֶ��⣬����Ҫ����company�ֶΣ�����ж��company������֮����,�ָ�
	 * @return
	 * @throws ApplicationException
	 */
	 
	public ReturnDTO ImportOrder(String msgdata) throws ApplicationException {
		ReturnDTO returnDTO=new ReturnDTO();
		returnDTO.setSuccess("false");
		JsonValidator jsonValidator=new JsonValidator();
		if (!jsonValidator.validate(msgdata)){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		OrderDTO dto=new OrderDTO();
		ClassHelper.copyProperties(msgdata, dto);
		//��֤
		if (dto.getOrderid()==null || dto.getOrderid().equals("")){
			returnDTO.setMsg("������Ų���Ϊ��");
			return returnDTO;
		} 
		if (dto.getGoodstype()==null || dto.getGoodstype().equals("")){
			returnDTO.setMsg("����Ʒ�಻��Ϊ��");
			return returnDTO;
		} 
			
		if (dto.getPackagetype()==null || dto.getPackagetype().equals("")){
			returnDTO.setMsg("��װ��ʽ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getCarriagetype()==null || dto.getCarriagetype().equals("")){
			returnDTO.setMsg("���䷽ʽ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getConveyance()==null || dto.getConveyance().equals("")){
			//returnDTO.setMsg("���乤�߲���Ϊ��");
			//return returnDTO;
			dto.setConveyance("101");  //δ����Ĭ��Ϊ����
		}
		if (dto.getQty()==null || dto.getQty().doubleValue()<=0){
			returnDTO.setMsg("��������ȷ������(��)");
			return returnDTO;
		}		
		if (dto.getStartareaid()==null || dto.getStartareaid().equals("")){
			returnDTO.setMsg("�����ز���Ϊ��");
			return returnDTO;
		}		
		if(dto.getStartaddress()==null || dto.getStartaddress().equals("")){
			returnDTO.setMsg("������ϸ��ַ����Ϊ��");
			return returnDTO;
		}		
		if (dto.getEndareaid()==null || dto.getEndareaid().equals("")){
			returnDTO.setMsg("�ջ��ز���Ϊ��");
			return returnDTO;
		}		
		if(dto.getEndaddress()==null || dto.getEndaddress().equals("")){
			returnDTO.setMsg("������ϸ��ַ����Ϊ��");
			return returnDTO;
		}		
		if(dto.getExpectprice()==null || dto.getExpectprice().equals("")){
			returnDTO.setMsg("Ԥ���˼۲���Ϊ��");
			return returnDTO;
		}		
		if (dto.getNeedcarriagesafe()==null || dto.getNeedcarriagesafe().equals("")){
			returnDTO.setMsg("�Ƿ���Ҫ�������䱣�ղ���Ϊ��");
			return returnDTO;
		}		
		if(dto.getHavestartdate()==null || dto.getHavestartdate().equals("")){
			returnDTO.setMsg("�߱��������ڲ���Ϊ��");
			return returnDTO;
		}		
		if(dto.getHavestartdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD HH24:MI:SS"))<0){
			returnDTO.setMsg("�߱��������ڲ���С�ڵ�ǰ����");
			return returnDTO;
		}		
		if(dto.getRequestfinishdate()==null || dto.getRequestfinishdate().equals("")){
			returnDTO.setMsg("Ҫ��������ڲ���Ϊ��");
			return returnDTO;
		}
		if(dto.getRequestfinishdate().compareTo(DateUtil.getCurrentDate_String("YYYY-MM-DD HH24:MI:SS"))<0){
			returnDTO.setMsg("Ҫ��������ڲ���С�ڵ�ǰ����");
			return returnDTO;
		}				
		if(dto.getHavestartdate().compareTo(dto.getRequestfinishdate())>0){
			returnDTO.setMsg("Ҫ��������ڲ���С�ھ߱���������");
			return returnDTO;
		}
		
		if (dto.getSendcompany()==null || dto.getSendcompany().equals("")){
			returnDTO.setMsg("ί�е�λ����Ϊ��");
			return returnDTO;
		}
		if (dto.getSendcontact()==null || dto.getSendcontact().equals("")){
			returnDTO.setMsg("ί����ϵ�˲���Ϊ��");
			return returnDTO;
		}	
		if (dto.getRetrievecompany()==null || dto.getRetrievecompany().equals("")){
			returnDTO.setMsg("�ջ���λ����Ϊ��");
			return returnDTO;
		}	
		if (dto.getRetrievecontact()==null || dto.getRetrievecontact().equals("")){
			returnDTO.setMsg("�ջ���ϵ�˲���Ϊ��");
			return returnDTO;
		}	
		
		if (dto.getConsigner()==null || dto.getConsigner().equals("")){
			returnDTO.setMsg("ί���˲���Ϊ��");
			return returnDTO;
		}	
		if((dto.getConsignermobilephone()==null || dto.getConsignermobilephone().equals("")) && (dto.getConsignertelephone()==null || dto.getConsignertelephone().equals(""))){
			returnDTO.setMsg("ί���˵���ϵ�ֻ��͹̶��绰��ѡһ");
			return returnDTO;
		}		
		if (dto.getReceiver()==null || dto.getReceiver().equals("")){
			returnDTO.setMsg("�ջ��˲���Ϊ��");
			return returnDTO;
		}		
		
		if((dto.getReceivermobilephone()==null || dto.getReceivermobilephone().equals("")) && (dto.getReceivertelephone()==null || dto.getReceivertelephone().equals(""))){
			returnDTO.setMsg("�ջ��˵���ϵ�ֻ��͹̶��绰���ٶ�ѡһ");
			return returnDTO;
		}		
		if (dto.getInvoicerequest()==null || dto.getInvoicerequest().equals("")){
			returnDTO.setMsg("��ƱҪ����Ϊ��");
			return returnDTO;
		}		
		/*
		if (dto.getBusinesstype()==null || dto.getBusinesstype().equals("")){
			returnDTO.setMsg("msgdata���ݰ���Ч");
			return returnDTO;
		}		
		*/
		if (dto.getLosedate()==null || dto.getLosedate().equals("")){
			returnDTO.setMsg("�ҵ�ʧЧ���ڲ���Ϊ��");
			return returnDTO;
		}		
		/*		
		if (dto.getCreatedby()==null || dto.getCreatedby().intValue()==0){
			returnDTO.setMsg("�����˱��벻��Ϊ��");
			return returnDTO;
		}
		*/
		dto.setCreatedby(999999);   //Ĭ���û�
		if (dto.getCompanyid()==null || dto.getCompanyid().equals("")){
			returnDTO.setMsg("�����̲���Ϊ��");
			return returnDTO;
		}
		
		
		//�ָ�company:��ȡ�������б�
		Orderinfo orderinfo=new Orderinfo();
		ClassHelper.copyProperties(dto, orderinfo);
		orderinfo.setExtorderid(dto.getOrderid());   //��ԭ���������������Ϊ�ⲿ�������
		Timestamp now=DateUtil.dateToTimestamp(DateUtil.getCurrentDate());		
		orderinfo.setCreateddate(now);
		orderinfo.setModifydate(now);	
		orderinfo.setModifyby(dto.getCreatedby());
		//String ls_seq=SeqBPO.GetSequence("SEQ_ORDERID");  //��ȡ���к�
		String ls_seq=SeqBPO.GetOrderid();  //��ȡ�������
		if (ls_seq.equals("-1")){
			returnDTO.setMsg("��ȡ�������ʱ����...");
			return returnDTO;
		} 
		orderinfo.setOrderid(ls_seq);
		orderinfo.setOrderstatus("103");  //��Ч	
		orderinfo.setOrdersource("102");  //������������
		orderinfo.setIsvalid("101");
		
		//�ж�ί�з��Ƿ���ڣ�������������
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
		String[] array_company=dto.getCompanyid().split(",");  //�ָ������
		for (int i=0;i<array_company.length;i++){
			Ordercompany ordercompany=new Ordercompany();
			ordercompany.setOrderid(orderinfo.getOrderid());
			ordercompany.setCompanyid(new Integer(array_company[i]));
			ordercompany.setOrdercompanyid(SeqBPO.GetSequence("SEQ_ORDERCOMPANYID"));
			array_ordercompany_add.add(ordercompany);
		}
				
		//���Ӷ���������־
		Orderlog orderlog=new Orderlog();
		orderlog.setOrderid(orderinfo.getOrderid());
		orderlog.setOrderopertype("101"); //��������
		orderlog.setMemo("������������");
		orderlog.setCreatedby(dto.getCreatedby());
		orderlog.setCreateddate(now);
		//���ݱ���
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
		    log.error(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������,�������:"+orderinfo.getOrderid(),e1.getMessage(),dto.getCreatedby());
						returnDTO.setMsg(e1.getMessage());
			return returnDTO;
		}			
		log.doLog(Thread.currentThread() .getStackTrace()[1].getMethodName(),"������������,�������:"+orderinfo.getOrderid(),dto.getCreatedby());
		returnDTO.setOrderid(orderinfo.getOrderid());
		returnDTO.setSuccess("true");
		returnDTO.setMsg("������������ɹ�!");
		return returnDTO;
	}
	
	
}
