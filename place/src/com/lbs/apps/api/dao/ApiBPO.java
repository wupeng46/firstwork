package com.lbs.apps.api.dao;

import net.sf.json.JSONObject;

import com.lbs.apps.common.ApplicationException;
import com.lbs.commons.ClassHelper;
import com.lbs.commons.TransManager;
import com.lbs.commons.log.LogHelper;
import com.lbs.commons.op.OPManager;

public class ApiBPO {
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
				if (!obj.has("companyid")) {
					ls_return = "���ݰ���δ����companyid������Ϣ";
				}
				String ls_companyid = (String) obj.get("companyid");
				if (ls_companyid == null || ls_companyid.equals("")
						|| ls_companyid.equals("0"))
					return "�����̱��벻��Ϊ��";
			} catch (Exception e) {
				ls_return = "msgdata�����ַ����Ƿ�";
			}
		}
		return ls_return;
	}
	
	/**
	 * ��ѯ���պ�ͬ��������ͬ��������ʷ��� 
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchTodayCompanyContractCount(String msgdata)throws ApplicationException {
		ApiDTO dto = new ApiDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid() == null)
			dto.setCompanyid(new Integer(0));
		StringBuffer sb=new StringBuffer("select count(ordercontractid) htbs,isnull(sum(contractqty),0) htyl,isnull(sum(contractprice),0) htje from ordercontract")
		.append(" where ismaster='101' and companyid="+dto.getCompanyid()+" and convert(varchar,companycontracttime,112)>=convert(varchar,getdate(),112)");
		return sb.toString();
	}
	
	/**
	 * ��ѯ���к�ͬ��������ͬ��������ʷ��� 
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchAllCompanyContractCount(String msgdata)throws ApplicationException {
		ApiDTO dto = new ApiDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid() == null)	dto.setCompanyid(new Integer(0));
		StringBuffer sb=new StringBuffer("select count(ordercontractid) htbs,isnull(sum(contractqty),0) htyl,isnull(sum(contractprice),0) htje from ordercontract")
		.append(" where ismaster='101' and companyid="+dto.getCompanyid());
		return sb.toString();
	}	
	
	/**
	 * ��ѯ�������˵���¼�б�
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String GetSql_SearchCompanyWaybill(String msgdata)throws ApplicationException{ 
		ApiDTO dto=new ApiDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getCompanyid() == null) dto.setCompanyid(new Integer(0));
		StringBuffer sb=new StringBuffer();
		sb.append("select a.orderid,ordercontractid,waybillid,batchno,b.memberid,dbo.sf_get_membername(b.memberid) membername,convert(varchar,startdate,120) startdate,convert(varchar,enddate,120) enddate,")
		.append(" a.price,weight,amount,goodstype,packagetype,carriagetype,startareaid,startaddress,endareaid,endaddress")
		.append(" from waybill a,orderinfo b where a.orderid=b.orderid and b.companyid="+dto.getCompanyid()+" and b.orderstatus<>'107'");
		return sb.toString();
	}
	
	/**
	 * �������κŲ�ѯ�˵���Ϣ
	 * @param msgdata
	 * @return
	 * @throws ApplicationException
	 */
	public String SearchWaybillByBatch(String msgdata)throws ApplicationException{
		ApiDTO dto=new ApiDTO();
		ClassHelper.copyProperties(msgdata, dto);
		if (dto.getBatchno()==null || dto.getBatchno().equals("")) dto.setBatchno("-999999");
		StringBuffer sb=new StringBuffer("select weighid,a.businesstype,a.orderid,carnumber,closenumber,companyid,dbo.sf_get_companyname(companyid) companyname,boxid,boxtype")
		.append(",storeagetime,boxaddr,outwarehouseid,a.sendcompany,tare,grossweight,netweight,a.goodstype,a.productlevel,a.water,a.impurity")
		.append(",a.capacity,a.smell,a.imperfecttotal,a.imperfectnum,a.imperfectnum2,weighstatus,a.createdby,a.createdorg,convert(varchar, c.createddate,120) createddate,a.modifyby,a.modifyorg,")
		.append("convert(varchar,c.modifydate,120) modifydate,packman,receivecompany,a.conveyance,a.memberid,dbo.sf_get_membername(a.memberid) membername,a.placeboxid,")
		.append("b.sendcontact,b.retrievecontact,b.orderstatus,dbo.sf_get_domainname('ORDERSTATUS',b.orderstatus) statusname,c.waybillid,")
		.append("convert(varchar, c.startdate,120) startdate,convert(varchar, c.enddate,120) enddate,c.batchno,c.price,c.weight,c.amount,c.waybillstatus,dbo.sf_get_domainname('WAYBILLSTATUS',c.waybillstatus) waybillstatusname")
		.append(" from weigh a,orderinfo b,waybill c where a.orderid=b.orderid and b.orderid=c.orderid and weighstatus<>'100'");
		
		if (!(dto.getBatchno()==null || dto.getBatchno().equals(""))){
			sb.append(" and c.batchno='").append(dto.getBatchno()).append("'");
		}
		return sb.toString();
	}

}
